package com.example.observer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.R
import androidx.core.app.NotificationCompat
import android.app.PendingIntent
import com.example.observer.MainActivity



class AppService : Service {

    private val NOTIF_ID = 2
    private val NOTIF_CHANNEL_ID = "ServiceChannel_Id"


    constructor() : super()


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()
        return Service.START_NOT_STICKY
    }

    private fun startForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )

        startForeground(NOTIF_ID, NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.btn_default)
                .setContentTitle("Content")
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build()
        )
    }

}