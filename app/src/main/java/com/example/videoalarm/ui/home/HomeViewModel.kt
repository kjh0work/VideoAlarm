package com.example.videoalarm.ui.home

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoalarm.alarmSystem.AlarmScheduler
import com.example.videoalarm.alarmSystem.AndroidAlarmScheduler
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.data.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel(){

    var isEditMode by mutableStateOf(false)
        private set

    val checkedAlarmList = mutableStateListOf<Alarm>()

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

    fun changeEditMode() {
        isEditMode = !isEditMode
    }

    /**
     * home에서 switch를 통해서 알람을 끄거나 재설정하는 메서드
     *
     * isActive가 변화함에 따라서 알람을 다시 설정하거나 해제
     */
    @OptIn(ExperimentalMaterial3Api::class)
    fun updateAlarmIsActive(updatedAlarm : Alarm){
        viewModelScope.launch {
            alarmRepository.updateItem(updatedAlarm)

            //1. isActive가 False로 바뀌었다면, 설정된 schedule을 삭제해야 한다.
            //2. isActive가 True라면, 다시 schedule을 해야 한다.
            if(updatedAlarm.isActive){
                if(updatedAlarm.daysOfWeek.contains(true)){ // 알람이 요일인 경우
                    alarmScheduler.schedule(updatedAlarm)
                }
                else{ //알람이 정확한 날짜인 경우..
                    val calendar = Calendar.getInstance().apply {
                        timeInMillis = updatedAlarm.date.selectedDateMillis!!
                        set(Calendar.HOUR_OF_DAY, updatedAlarm.clockTime.hour)
                        set(Calendar.MINUTE, updatedAlarm.clockTime.minute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    val alarmTime = calendar.timeInMillis

                    if(alarmTime > System.currentTimeMillis()){
                        alarmScheduler.schedule(updatedAlarm)
                    }
                    else{
                        //현재 설정된 알람 기준으로 다음날.
                        val newDateMillis = updatedAlarm.date.selectedDateMillis!! + TimeUnit.DAYS.toMillis(1)

                        val newDatePickerState = DatePickerState(initialSelectedDateMillis = newDateMillis, locale = Locale.KOREA)
                        val nextDayAlarm = updatedAlarm.copy(date = newDatePickerState)
                        alarmRepository.updateItem(nextDayAlarm)
                        alarmScheduler.schedule(nextDayAlarm)
                    }

                }
            }
            else alarmScheduler.cancel(updatedAlarm)
        }
    }

    fun deleteAlarm() {
        for(alarm:Alarm in checkedAlarmList){
            viewModelScope.launch {
                alarmRepository.deleteItem(alarm)
                alarmScheduler.cancel(alarm)
            }
        }
        checkedAlarmList.clear()
        changeEditMode()
    }


}

data class HomeUiState(
    val alarmList: List<Alarm> = listOf()
)