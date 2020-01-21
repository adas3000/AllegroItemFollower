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
import com.example.observer.db.GetDbInstance
import com.example.observer.model.AllegroItem
import com.example.observer.presenter.IOnInternetPresenter
import com.example.observer.presenter.OnInternetPresenter
import com.example.observer.service.AppService
import com.example.observer.util.CatchTheItem
import com.example.observer.util.ItemAdded
import com.example.observer.view.IOnInternetView
import es.dmoral.toasty.Toasty


class MainActivity : AppCompatActivity(),IOnInternetView,ItemAdded {

    private val TAG = "MainActivity"
    private var addingFinished = true
    private var notify_id = 1

    override fun setAdded(added: Boolean) {
        addingFinished = added
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent_run_in_background = Intent(this,AppService::class.java)
        this.startService(intent_run_in_background)


        val internetPresenter:IOnInternetPresenter = OnInternetPresenter(this)

        //todo fix : internet exceptions when click adding multiple times in short period of time

        var networkCallback = object : ConnectivityManager.NetworkCallback() {

            //var catchTheItem:CatchTheItem = CatchTheItem(internetPresenter,GetDbInstance.getDb(applicationContext))
            //comment till problem with app stopping won't be fixed
            override fun onLost(network: Network?) {
                Log.d(TAG, "onLost invoked")
                //catchTheItem.do_run = false
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
                //catchTheItem = CatchTheItem(internetPresenter,GetDbInstance.getDb(applicationContext),true)
               // Thread(catchTheItem).start()
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
            fab_fragmentTransition.replace(R.id.frameLayout_fragmentKeeper, AddItemFragment.newInstance(this))
            fab_fragmentTransition.addToBackStack(null)
            fab_fragmentTransition.commit()
        }

    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if(!addingFinished) return

        if (count == 0) {
            super.onBackPressed()
        } else {
            if (floatingActionButton3.isOrWillBeHidden) floatingActionButton3.show()

            supportFragmentManager.popBackStack()
        }

    }

    override fun onPriceChanged(allegroItem: AllegroItem) {
        val db =  GetDbInstance.getDb(applicationContext)

        Thread(Runnable {
            db.allegroItemDao().update(allegroItem)
        }).start()
        //todo list update if on ItemListFragment

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "ObserverAllegroItems"

        var msg:String = getString(R.string.msg_price_changed_text,allegroItem.itemName,allegroItem.itemPrice)
        if(allegroItem.expiredIn!=null)
            msg +=  getString(R.string.msg_expiredin_text,allegroItem.expiredIn)


        var builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("Item price changed")
            .setContentText(msg)
            .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationChannel: NotificationChannel = NotificationChannel(channelId, "Item price changed",
            NotificationManager.IMPORTANCE_DEFAULT)

        builder.setContentIntent(PendingIntent.getActivity(applicationContext,0,
            Intent(Intent.ACTION_VIEW, Uri.parse(allegroItem.itemURL)),0))

        notificationManager.createNotificationChannel(notificationChannel)
        builder.setChannelId(channelId)
        notificationManager.notify(notify_id++,builder.build())
    }

    override fun onPriceDidNotChanged(allegroItem: AllegroItem) {
        val db =  GetDbInstance.getDb(applicationContext)

        Thread(Runnable {
            db.allegroItemDao().update(allegroItem)
        }).start()

    }

    override fun onError(msg: String) {
        Toasty.error(this,msg,Toasty.LENGTH_SHORT).show()
    }

}
