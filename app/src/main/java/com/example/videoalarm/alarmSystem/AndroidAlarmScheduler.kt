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
//        val intent = Intent(context, AlarmReceiver::class.java).let {
//            intent -> PendingIntent.getBroadcast(context,item.id.toInt(),intent,
//            PendingIntent.FLAG_IMMUTABLE)
//        }
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", item.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


//        val date = SimpleDateFormat("MM/dd", Locale.getDefault()).format(Date(item.date.selectedDateMillis!!))
//        val month = date.substring(0,2)
//        val day = date.substring(3,5)
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
//            set(Calendar.DAY_OF_MONTH, day.toInt())
//            set(Calendar.MONTH, month.toInt())
            set(Calendar.HOUR_OF_DAY, item.clockTime.hour)
            set(Calendar.MINUTE, item.clockTime.minute)
            set(Calendar.SECOND,0)
        }

        //Todo
        //반복을 설정한 경우 setRepeat?을 사용해야 한다.
        //

        //한번만 울리도록 설정됨.
        if(alarmManager.canScheduleExactAlarms()){
            alarmManager.cancel(pendingIntent)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
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