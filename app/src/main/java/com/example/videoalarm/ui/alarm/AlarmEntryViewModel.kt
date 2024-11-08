package com.example.videoalarm.ui.alarm

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoalarm.alarmSystem.AlarmScheduler
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.data.AlarmRepository
import com.example.videoalarm.saveFileToExternalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmEntryViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler,
) : ViewModel() {

    var alarmEntryUiState by mutableStateOf(AlarmEntryUiState())
        private set


    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.S)
    fun saveAlarm(context: Context) {
        viewModelScope.launch {
            val videoUri = alarmEntryUiState.videoUri
            Log.d("AlarmEntry",videoUri.toString())
            videoUri?.let {uri ->
                val filename = saveFileToExternalStorage(context, uri)
//                val copiedUri = copyVideoToInternalStorage(context, videoUri)
//                alarmEntryUiState = alarmEntryUiState.copy(alarmDetails = alarmEntryUiState.alarmDetails.copy(videoUri = filename))
                  alarmEntryUiState = alarmEntryUiState.copy(alarmDetails = alarmEntryUiState.alarmDetails.copy(fileName = filename))
            }

            val alarmId:Long = alarmRepository.insertItem(alarmEntryUiState.alarmDetails.toAlarm())
            alarmEntryUiState = alarmEntryUiState.copy(alarmDetails = alarmEntryUiState.alarmDetails.copy(
                id = alarmId
            ))
            alarmScheduler.schedule(alarmEntryUiState.alarmDetails.toAlarm())
        }
    }


    private fun validateInput(alarmDetail: AlarmDetails = alarmEntryUiState.alarmDetails): Boolean {
        //현재 알람 설정 특성상 기본 설정이 있고,
        //그 이외에 값은 체크 값이기 때문에 상관 없다.
        return true;
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun updateDaysOfWeek(day: Int) {
        val list = alarmEntryUiState.alarmDetails.daysOfWeek.toMutableStateList()
        list[day] = !list[day]

        alarmEntryUiState = alarmEntryUiState.copy(
            alarmDetails = alarmEntryUiState.alarmDetails.copy(daysOfWeek = list)
        )
    }

    fun updateOpenDatePickDialog() {
        alarmEntryUiState =
            alarmEntryUiState.copy(openDatePickDialog = !alarmEntryUiState.openDatePickDialog)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun clearSelectedDate() {
        alarmEntryUiState = alarmEntryUiState.copy(
            alarmDetails = alarmEntryUiState.alarmDetails.copy(
                date = DatePickerState(CalendarLocale.KOREA, initialSelectedDateMillis = null)
            )
        )
        Log.d("isRecomposition","yes_in clearSelectedDate")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun dateNullCheck() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val setHour = alarmEntryUiState.alarmDetails.clockTime.hour
        val setMinute = alarmEntryUiState.alarmDetails.clockTime.minute

        //local time > alarm setting time
        if (hour > setHour || (hour == setHour && minute >= setMinute)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        //요일과 날짜를 둘 다 선택하지 않았다면
        if (!alarmEntryUiState.alarmDetails.daysOfWeek.contains(true) && alarmEntryUiState.alarmDetails.date.selectedDateMillis == null) {
            updateAlarmDate(calendar.timeInMillis)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun updateAlarmDate(newDateMillis: Long) {
        val currentState = alarmEntryUiState
        alarmEntryUiState = currentState.copy(
            alarmDetails = currentState.alarmDetails.copy(
                date = DatePickerState(
                    CalendarLocale.KOREA,
                    initialSelectedDateMillis = newDateMillis
                )
            )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun alarmNameChange(newName: String) {
        alarmEntryUiState = alarmEntryUiState.copy(
            alarmDetails = alarmEntryUiState.alarmDetails.copy(name = newName)
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun updateVideoUri(uri : Uri){
        alarmEntryUiState = alarmEntryUiState.copy(
            //alarmDetails = alarmEntryUiState.alarmDetails.copy(videoUri = uri)
            videoUri = uri
        )
    }

}
@OptIn(ExperimentalMaterial3Api::class)
fun AlarmDetails.toAlarm() : Alarm = Alarm(
    id = id,
    name = name,
    clockTime = clockTime,
    date = date,
    isActive = isActive,
    daysOfWeek = daysOfWeek,
    //videoUri = videoUri,
    fileName = fileName
)

/**
 * Alarm을 추가하는 화면
 * 1. 사용자가 입력한 값이 올바른 값인지 판별 : isEntryValid
 * 2. 사용자가 입력한 값 : AlarmDetails
 */
data class AlarmEntryUiState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val isEntryValid : Boolean = false,
    val alarmDetails : AlarmDetails = AlarmDetails(),
    val openDatePickDialog : Boolean = false,
    val videoUri : Uri? = null
)

data class AlarmDetails @OptIn(ExperimentalMaterial3Api::class) constructor(
    val id: Long = 0,
    val name: String = "",
    val clockTime: TimePickerState = TimePickerState(6,0,false),
    val date: DatePickerState = DatePickerState(CalendarLocale.KOREA, initialSelectedDateMillis = null),
    val isActive : Boolean = true,
    val daysOfWeek : MutableList<Boolean> = mutableListOf(false,false,false,false,false,false,false),
    //val videoUri: Uri? = null,
    val fileName : String = ""
)