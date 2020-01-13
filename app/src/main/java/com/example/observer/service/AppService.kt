package com.example.observer.service

import android.content.Intent
import android.os.IBinder
import android.R
import android.app.*
import android.content.Context
import androidx.core.app.NotificationCompat
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
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

        val channelId =
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createNotificationChannel("allegro_item_follower_service x0x"
                        ,"allegro_item_follower_background_service")
                }
            else ""




        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )

        startForeground(NOTIF_ID, NotificationCompat.Builder(this, channelId)
                .setOngoing(true)
                .setSmallIcon(R.drawable.btn_default)
                .setContentTitle("Content")
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId:String,channelName:String):String{

        val chan = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_NONE)

        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }



}