package com.example.videoalarm.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.videoalarm.VideoAlarmApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {

    }
}

fun CreationExtras.videoAlarmApplication(): VideoAlarmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VideoAlarmApplication)