package com.example.videoalarm

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.getSystemService
import com.example.videoalarm.ui.alarm.AlarmActivityScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("checkOnReceive","AlarmActivity is onCreate")
        setContent {
            AlarmActivityScreen()
        }
    }
}