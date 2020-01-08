package com.example.observer

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.observer.fragment.AddItemFragment
import com.example.observer.fragment.ItemListFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.Context
import android.net.Network
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.example.observer.db.AppDatabase
import com.example.observer.db.GetDbInstance
import com.example.observer.presenter.IOnInternetPresenter
import com.example.observer.presenter.OnInternetPresenter
import com.example.observer.service.AppService
import com.example.observer.view.IOnInternetView
import es.dmoral.toasty.Toasty


class MainActivity : AppCompatActivity(),IOnInternetView {

    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent_run_in_background = Intent(this,AppService::class.java)
        this.startService(intent_run_in_background)


        val internetPresenter:IOnInternetPresenter = OnInternetPresenter(this)

        var networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network?) {
                Log.d(TAG, "onLost invoked")
            }

            override fun onUnavailable() {
                Log.d(TAG, "onUnavailable invoked")
            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                Log.d(TAG, "onLosing invoked")
            }

            override fun onAvailable(network: Network?) {
                Log.d(TAG, "onAvailable invoked")
                internetPresenter.onAvailable(GetDbInstance.getDb(applicationContext))
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
            if (floatingActionButton3.isOrWillBeHidden) floatingActionButton3.show()

            supportFragmentManager.popBackStack()
        }

    }

    override fun onPriceChanged(item_name: String,new_price:Float,uid:Int) {
        val db =  GetDbInstance.getDb(applicationContext)

        Thread(Runnable {
            db.allegroItemDao().updatePrice(new_price,uid)
        }).start()
        //todo list update if on ItemListFragment

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "comexampleobserverChannel1"

        var builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentTitle("Item price changed")
            .setContentText(item_name+" price changed to "+new_price+" zł.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(item_name+" price changed to "+new_price+" zł."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationChannel: NotificationChannel = NotificationChannel(channelId, "channelname",
            NotificationManager.IMPORTANCE_DEFAULT)


        notificationManager?.createNotificationChannel(notificationChannel)
        builder.setChannelId(channelId)
        notificationManager.notify(1,builder.build())
    }

    override fun onError(msg: String) {
        Toasty.error(this,msg,Toasty.LENGTH_SHORT).show()
    }

}
