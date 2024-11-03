package com.example.videoalarm.ui.fullScreenNotify

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.data.AlarmRepository
import com.example.videoalarm.getFileFromExternalStorage
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

    init {
        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Log.e("ExoPlayer", "Playback error: ${error.message}")
            }
        })
    }

    fun loadAlarm(alarmId : Long, context: Context){
        Log.d("alarmId", "loadAlarm called with alarmId: $alarmId")
        viewModelScope.launch {
            try {
                Log.d("alarmId", "Starting to collect from getAlarmStream")
                repository.getAlarmStream(alarmId).collect { alarm ->
                    Log.d("alarmId", "Collected alarm: $alarm")
                    alarm.fileName.let {filename ->
                        val file = getFileFromExternalStorage(context, filename)
                        val uri = Uri.fromFile(file)
                        player.setMediaItem(MediaItem.fromUri(uri))
                        player.prepare()
                        player.play()
                    }
                }
            } catch (e: Exception) {
                Log.e("alarmId", "Error collecting alarm", e)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
