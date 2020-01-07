package com.example.observer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.observer.fragment.AddItemFragment
import com.example.observer.fragment.ItemListFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.Network
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

            }
        }
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
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


}
