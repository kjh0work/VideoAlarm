package com.example.videoalarm.alarmSystem

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.videoalarm.data.AlarmRepository
import com.example.videoalarm.notification.OnReceiveNotificationService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var notificationService: OnReceiveNotificationService
    @Inject
    lateinit var alarmRepository: AlarmRepository

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    /**
     * AlarmManager를 통해 시스템으로 부터 정확한 시간에 Broadcast호출
     * Notification을 호출
     */
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null && intent != null) {
            val alarmId = intent.getLongExtra("ALARM_ID", -1L)
            Log.d("alarmId","In AlarmReceiver : $alarmId")
            notificationService.showFullScreenNotification(alarmId)

            //현재 알람이 울린 상태이며 요일 알람일 경우 다음 알람을 설정.
            CoroutineScope(Dispatchers.IO).launch {
                val alarm = alarmRepository.getAlarmStream(alarmId).firstOrNull()
                alarm?.let {
                    if(alarm.daysOfWeek.contains(true)){
                        alarmScheduler.schedule(alarm)
                    }
                    else{
                        val newAlarm = alarm.copy(isActive = false)
                        alarmRepository.updateItem(newAlarm)
                    }

                }
            }
        }

    }
}