package com.example.videoalarm.ui.fullScreenNotify

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.ui.PlayerView

@Composable
fun FullScreenAlarmScreen(
    modifier : Modifier = Modifier,
    viewModel: FullScreenAlarmViewModel = hiltViewModel(),
    alarmId : Long
    ){

    LaunchedEffect(key1 = alarmId) {
        viewModel.loadAlarm(alarmId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(factory = { it ->
            PlayerView(it).also {
                it.player = viewModel.player
            }
        })
    }

}
