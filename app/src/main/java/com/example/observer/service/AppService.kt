package com.example.observer.service

import android.content.Intent
import android.os.IBinder
import android.app.*
import android.content.Context
import androidx.core.app.NotificationCompat
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.observer.MainActivity
import com.example.observer.R


class AppService : Service {



    private val notifyId = 2

    lateinit var notifyTitle:String
    lateinit var notifyContent:String




    constructor():super()


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()
        return START_NOT_STICKY
    }

    private fun startForeground() {

        notifyTitle = resources.getString(R.string.app_background_notification_title_text)
        notifyContent =resources.getString(R.string.app_background_notification_content_text)

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

        startForeground(notifyId, NotificationCompat.Builder(this, channelId)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(notifyTitle)
                .setContentText(notifyContent)
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