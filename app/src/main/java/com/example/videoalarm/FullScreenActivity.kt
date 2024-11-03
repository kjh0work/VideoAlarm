package com.example.videoalarm

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.videoalarm.ui.fullScreenNotify.FullScreenAlarmScreen
import com.example.videoalarm.ui.theme.VideoAlarmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        turnScreenOn()

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

    private fun turnScreenOn(){
        //set flags so activity is showed when phone is off (on lock screen)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
    }
}