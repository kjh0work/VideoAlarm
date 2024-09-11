package com.example.videoalarm.alarmSystem

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.videoalarm.AlarmActivity
import com.example.videoalarm.notification.OnReceiveNotificationService

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("checkOnReceive","Successfully onReceive")

        if (context != null) {
            val notificationService = OnReceiveNotificationService(context)
            notificationService.showNotification()
        }



        //안드로이드 10버전 부터 백그라운드에서 Acitivity 실행이 제한되었다. -> Notification을 적용
//        context?.let {
//            Log.d("checkOnReceive","try to start activity")
//            val alarmIntent = Intent(it, AlarmActivity::class.java)
//            Log.d("checkOnReceive","initicalize intent clear")
//            alarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
//            Log.d("checkOnReceive","set flag clear")
//            it.startActivity(alarmIntent)
//            Log.d("checkOnReceive","여기 까지 오는게 맞나?")
//        }

    }
}