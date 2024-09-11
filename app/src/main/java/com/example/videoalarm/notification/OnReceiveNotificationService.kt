package com.example.videoalarm.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.videoalarm.MainActivity
import com.example.videoalarm.R

class OnReceiveNotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        Log.d("checkOnReceive","Successfully start showNotification")
        val activityIntent = Intent(context, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, ONRECEIVE_CHANNEL_ID)
            .setSmallIcon(R.drawable.hamstar_modified)
            .setContentTitle("OnReceive occur")
            .setContentText("sdfkjalsdjflkads")
            .setContentIntent(activityPendingIntent)
            //.setFullScreenIntent(activityPendingIntent, true)
            //.setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val ONRECEIVE_CHANNEL_ID = "onReceive_channel"
    }
}