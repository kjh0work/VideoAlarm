package com.example.videoalarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.videoalarm.notification.OnReceiveNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VideoAlarmApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    /**
     * Full screen notification channel 등록
     */
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                OnReceiveNotificationService.FULLSCREEN_CHANNEL_ID,
                "FullScreen",
                NotificationManager.IMPORTANCE_HIGH //high로 설정하지 않으면 무슨일이?
            )
            channel.description = "Used for see Full Screen alarm"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}