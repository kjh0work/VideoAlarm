package com.example.videoalarm.ui.home

import androidx.lifecycle.ViewModel
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.data.AlarmRepository

class HomeViewModel(alarmRepository: AlarmRepository) : ViewModel(){



}

data class HomeUiState(val alarmList: List<Alarm> = listOf())