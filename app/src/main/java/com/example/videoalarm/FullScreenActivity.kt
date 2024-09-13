package com.example.videoalarm

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.videoalarm.ui.theme.VideoAlarmTheme

class FullScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoAlarmTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "This is Fullscreen")

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        turnScreenOnAndKeyguardOff()
    }

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