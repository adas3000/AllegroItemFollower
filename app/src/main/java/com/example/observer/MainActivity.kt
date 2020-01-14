package com.example.observer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.observer.fragment.AddItemFragment
import com.example.observer.fragment.ItemListFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.Context
import android.net.*
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.room.Room
import com.example.observer.db.AppDatabase
import com.example.observer.db.GetDbInstance
import com.example.observer.presenter.IOnInternetPresenter
import com.example.observer.presenter.OnInternetPresenter
import com.example.observer.service.AppService
import com.example.observer.util.CatchTheItem
import com.example.observer.view.IOnInternetView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.add_item_fragment_layout.*


class MainActivity : AppCompatActivity(),IOnInternetView {

    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent_run_in_background = Intent(this,AppService::class.java)
        this.startService(intent_run_in_background)


        val internetPresenter:IOnInternetPresenter = OnInternetPresenter(this)



        var networkCallback = object : ConnectivityManager.NetworkCallback() {

            var catchTheItem:CatchTheItem = CatchTheItem(internetPresenter,GetDbInstance.getDb(applicationContext))

            override fun onLost(network: Network?) {
                Log.d(TAG, "onLost invoked")
                catchTheItem.do_run = false
            }

            override fun onUnavailable() {
                Log.d(TAG, "onUnavailable invoked")
            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                Log.d(TAG, "onLosing invoked")
            }

            override fun onAvailable(network: Network?) {
                Log.d(TAG, "onAvailable invoked")
                catchTheItem = CatchTheItem(internetPresenter,GetDbInstance.getDb(applicationContext),true)
                Thread(catchTheItem).start()
            }

        }

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)


        val supportFragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransition.add(R.id.frameLayout_fragmentKeeper, ItemListFragment())
        fragmentTransition.commit()

        floatingActionButton3.setOnClickListener {
            val fab_fragmentTransition = supportFragmentManager.beginTransaction()
            floatingActionButton3.hide()
            fab_fragmentTransition.replace(R.id.frameLayout_fragmentKeeper, AddItemFragment())
            fab_fragmentTransition.addToBackStack(null)
            fab_fragmentTransition.commit()
        }

    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
        } else {
            if(progressBar_addItem != null && progressBar_addItem.isVisible) return
            if (floatingActionButton3.isOrWillBeHidden) floatingActionButton3.show()

            supportFragmentManager.popBackStack()
        }

    }

    override fun onPriceChanged(item_name:String,new_price:Float,item_url:String,uid:Int) {
        val db =  GetDbInstance.getDb(applicationContext)

        Thread(Runnable {
            db.allegroItemDao().updatePrice(new_price,uid)
        }).start()
        //todo list update if on ItemListFragment

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "comexampleobserverChannel1"

        var builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("Item price changed")
            .setContentText(item_name+" price changed to "+new_price+" zł.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(item_name+" price changed to "+new_price+" zł."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationChannel: NotificationChannel = NotificationChannel(channelId, "channelname",
            NotificationManager.IMPORTANCE_DEFAULT)


        builder.setContentIntent(PendingIntent.getActivity(applicationContext,0,
            Intent(Intent.ACTION_VIEW, Uri.parse(item_url)),0))

        notificationManager?.createNotificationChannel(notificationChannel)
        builder.setChannelId(channelId)
        notificationManager.notify(1,builder.build())
    }

    override fun onError(msg: String) {
        Toasty.error(this,msg,Toasty.LENGTH_SHORT).show()
    }

}
