package com.example.videoalarm

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.videoalarm.ui.theme.VideoAlarmTheme

class MainActivity : ComponentActivity() {

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // 권한이 허용되면 여기서 알림 로직을 처리
        } else {
            // 권한이 거부되면 사용자에게 설정으로 유도
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
                startActivity(this)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VideoAlarmTheme {
                VideoAlarmApp()
            }
        }
    }

    //@RequiresApi(Build.VERSION_CODES.S)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        //권한 설정 동의 Dialog가 필요
        if(!checkAlarmRemainderPermission()){
            showSettingAlarmReminder()
        }
        //알'림' 허용 확인
        checkAndRequestNotificationPermission()
    }

    //알람 설정이 허용되어 있는지 확인
    private fun checkAlarmRemainderPermission() : Boolean{
        var result = false
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if(alarmManager.canScheduleExactAlarms()) result = true
        }
        else result = true

        return result
    }
    //사용자에게 직접 설정에서 알람 및 리마인더 허용 요청
    @RequiresApi(Build.VERSION_CODES.S)
    fun showSettingAlarmReminder() {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:${packageName}"))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkAndRequestNotificationPermission() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            requestNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}





