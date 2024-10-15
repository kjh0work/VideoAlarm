package com.example.videoalarm.ui.alarm

import androidx.lifecycle.ViewModel
import com.example.videoalarm.data.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmActivityViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : ViewModel() {

}