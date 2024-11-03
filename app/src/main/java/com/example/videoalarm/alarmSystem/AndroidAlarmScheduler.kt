package com.example.videoalarm.alarmSystem

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.videoalarm.data.Alarm
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun schedule(item: Alarm) {

        /**
         * AlarmReceiver를 수행
         * PendingIntent로 설정을 통해 나중에 수행
         */
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", item.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        var alarmTime = 0L

        /**
         * 현재 알람이 요일 vs 날짜 인지 체크
         *
         * 요일로 설정되었다면 반복 알람을 저장해야 한다.
         */
        if(item.daysOfWeek.contains(true)){ //요일로 설정된 경우
            val calendar = Calendar.getInstance().apply {
                // 현재 시간을 기준으로 설정
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, item.clockTime.hour)
                set(Calendar.MINUTE, item.clockTime.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val today = calendar.get(Calendar.DAY_OF_WEEK) // 1 (일요일)부터 7 (토요일)까지
            val daysOfWeek = item.daysOfWeek
            var weekSet = false

            //현재 시간과 요일에 맞는 다음 알람 설정
            for(i in 0..6){
                val dayIndex = (today - 1 + i) % 7 // 0: 일요일 | 6 : 토요일
                if(daysOfWeek[dayIndex]){
                    if( i > 0 || calendar.timeInMillis > System.currentTimeMillis()){
                        calendar.add(Calendar.DAY_OF_YEAR, i)
                        weekSet = true
                        break
                    }
                }
            }

            //만약 요일이 하나고 오늘과 같으며 시간이 이미 지난 경우엔 다음주로 설정
            if(!weekSet){
                calendar.add(Calendar.DAY_OF_YEAR, 7)
            }
            alarmTime = calendar.timeInMillis
        }
        else{
            val calendar = Calendar.getInstance().apply {
                timeInMillis = item.date.selectedDateMillis!!
                set(Calendar.HOUR_OF_DAY, item.clockTime.hour)
                set(Calendar.MINUTE, item.clockTime.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            alarmTime = calendar.timeInMillis
        }

        //실제 알람 설정
        if(alarmManager.canScheduleExactAlarms()){
            //alarmManager.cancel(pendingIntent)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                pendingIntent
            )
        }


    }

    override fun cancel(item: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id.toInt(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}
