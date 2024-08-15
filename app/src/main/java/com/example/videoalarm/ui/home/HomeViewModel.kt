package com.example.videoalarm.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.data.AlarmRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val alarmRepository: AlarmRepository) : ViewModel(){

    var isEditMode by mutableStateOf(false)
        private set

    //DB와 연동하는 것이기 때문에 StateOf가 아닌 StateFlow를 사용
    val homeUiState : StateFlow<HomeUiState> =
        alarmRepository.getAllAlarmStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), //5초 지나고 db를 구독하지 않는다면 종료
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5000L;
    }

    fun toggleEditMode() {
        isEditMode = true
    }

    fun updateAlarm(updatedAlarm : Alarm){
        viewModelScope.launch {
            alarmRepository.updateItem(updatedAlarm)
        }
    }

}

data class HomeUiState(
    val alarmList: List<Alarm> = listOf()
)