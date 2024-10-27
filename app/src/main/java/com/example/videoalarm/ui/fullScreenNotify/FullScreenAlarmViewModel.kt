package com.example.videoalarm.ui.fullScreenNotify

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.data.AlarmRepository
import com.example.videoalarm.ui.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullScreenAlarmViewModel @Inject constructor(
    private val repository: AlarmRepository,
    val player: Player
) : ViewModel() {

    //private val _alarm = MutableStateFlow<Alarm?>(null)
    //val alarm : StateFlow<Alarm?> = _alarm.asStateFlow()

    fun loadAlarm(alarmId : Long){
        Log.d("alarmId", "loadAlarm called with alarmId: $alarmId")
        viewModelScope.launch {
            try {
                Log.d("alarmId", "Starting to collect from getAlarmStream")
                repository.getAlarmStream(alarmId).collect { alarm ->
                    Log.d("alarmId", "Collected alarm: $alarm")
                    alarm.videoUri?.let {
                        player.setMediaItem(MediaItem.fromUri(alarm.videoUri))
                        player.prepare()
                        player.play()
                    }
                }
            } catch (e: Exception) {
                Log.e("alarmId", "Error collecting alarm", e)
            }
        }
//        viewModelScope.launch {
//            repository.getAlarmStream(alarmId).collect{ it ->
//                it.videoUri?.let {
//                    Log.d("alarmId", it.toString())
//                    player.setMediaItem(MediaItem.fromUri(it))
//                    player.prepare()
//                    player.play()
//                }
//            }
//        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
