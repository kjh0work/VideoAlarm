package com.example.videoalarm.ui

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.videoalarm.VideoAlarmApplication
import com.example.videoalarm.ui.alarm.AlarmEntryViewModel
import com.example.videoalarm.ui.home.HomeViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                videoAlarmApplication().container.alarmRepository
            )
        }

        initializer {
            AlarmEntryViewModel(
                videoAlarmApplication().container.alarmRepository
            )

        }
    }
}

fun CreationExtras.videoAlarmApplication(): VideoAlarmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VideoAlarmApplication)