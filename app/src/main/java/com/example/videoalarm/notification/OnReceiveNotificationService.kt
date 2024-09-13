package com.example.videoalarm.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.videoalarm.FullScreenActivity
import com.example.videoalarm.MainActivity
import com.example.videoalarm.R

class OnReceiveNotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /**
     * head up 알림
     * 테스트 용
     */
    fun showNotification() {
        Log.d("checkOnReceive","Successfully start showNotification")
        val activityIntent = Intent(context, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            3,
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

        notificationManager.notify(2, notification)
    }

    /**
     * 기기의 화면이 꺼진 경우, 알람이 울리면 전체 화면으로 notification을 띄움
     *
     */
    fun showFullScreenNotification(){
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val fullScreenActivityIntent = Intent(context, FullScreenActivity::class.java)

        /**
         * PendingIntent의 flag를 Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         * 위처럼 해야 하나 그냥 immutable로 해야 하나.. 각각이 의미하는게 정확히 뭔지 알아보자
         */
        val fullScreenActivityPendingIntent = PendingIntent.getActivity(
            context,
            2,
            fullScreenActivityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, FULLSCREEN_CHANNEL_ID)
            .setSmallIcon(R.drawable.hamstar_modified)
            .setContentTitle("알람 울림")
            .setContentText("Full screen alarm")
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(activityPendingIntent)
            .setFullScreenIntent(fullScreenActivityPendingIntent, true)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }


    companion object {
        const val ONRECEIVE_CHANNEL_ID = "onReceive_channel"
        const val FULLSCREEN_CHANNEL_ID = "FullScreen_channel"
    }
}