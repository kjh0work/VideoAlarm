package com.example.videoalarm.ui.alarm

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.data.AlarmRepository
import kotlinx.coroutines.launch

class AlarmEntryViewModel(private val alarmRepository: AlarmRepository) : ViewModel(){

    var alarmEntryUiState by mutableStateOf(AlarmEntryUiState())
        private set

    fun saveAlarm() {
        viewModelScope.launch {
            alarmRepository.insertItem(alarmEntryUiState.alarmDetails.toAlarm())
        }
    }

    fun updateAlarmDetail(alarmDetails: AlarmDetails){
        alarmEntryUiState = alarmEntryUiState.copy(alarmDetails = alarmDetails)
    }

    private fun validateInput(alarmDetail:AlarmDetails = alarmEntryUiState.alarmDetails) : Boolean{
        //현재 알람 설정 특성상 기본 설정이 있고,
        //그 이외에 값은 체크 값이기 때문에 상관 없다.
        return true;
    }

    suspend fun saveDummyAlarm() {
        alarmRepository.insertItem(alarmEntryUiState.alarmDetails.toAlarmDummy())
    }


}

fun AlarmDetails.toAlarmDummy() : Alarm = Alarm(
    id = id,
    name = "wake up",
    localTime = "오전 10:30",
    isActive = true,
    daysOfWeek = "월수금",
    videoPath = "video/path"
)

fun AlarmDetails.toAlarm() : Alarm = Alarm(
    id = id,
    name = name,
    localTime = localTime,
    isActive = isActive,
    daysOfWeek = daysOfWeek,
    videoPath = videoPath
)

/**
 * Alarm을 추가하는 화면
 * 1. 사용자가 입력한 값이 올바른 값인지 판별 : isEntryValid
 * 2. 사용자가 입력한 값 : AlarmDetails
 */
data class AlarmEntryUiState(
    val isEntryValid : Boolean = false,
    val alarmDetails : AlarmDetails = AlarmDetails()
)

data class AlarmDetails(
    val id: Long = 0,
    val name: String = "default",
    val localTime: String = "",
    val isActive : Boolean = false,
    val daysOfWeek : String = "",
    val videoPath : String = ""
)