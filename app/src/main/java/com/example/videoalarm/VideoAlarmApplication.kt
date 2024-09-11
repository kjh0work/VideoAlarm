package com.example.videoalarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.example.videoalarm.data.AppContainer
import com.example.videoalarm.data.AppDataContainer
import com.example.videoalarm.notification.OnReceiveNotificationService

class VideoAlarmApplication : Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                OnReceiveNotificationService.ONRECEIVE_CHANNEL_ID,
                "OnReceive",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Used for see OnReceive broadcast"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}