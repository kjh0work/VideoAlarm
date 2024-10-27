package com.example.videoalarm

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.videoalarm.ui.fullScreenNotify.FullScreenAlarmScreen
import com.example.videoalarm.ui.theme.VideoAlarmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmId = intent.getLongExtra("alarmId",-1L)
        Log.d("alarmId","In FullScreenActivity : $alarmId")
        if (alarmId == -1L) {
            finish()
            return
        }
        Log.d("alarmId","In FullScreenActivity : $alarmId")
        setContent {
            VideoAlarmTheme {
                FullScreenAlarmScreen(alarmId = alarmId)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        turnScreenOnAndKeyguardOff()
    }

    /**
     * 기기 화면 잠금 & 화면 꺼짐 컨트롤
     */
    private fun turnScreenOnAndKeyguardOff() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
        }
        else{
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED    // deprecated api 27
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD     // deprecated api 26
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON   // deprecated api 27
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
        }

        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            keyguardManager.requestDismissKeyguard(this, null)
        }
    }
}