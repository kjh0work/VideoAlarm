package com.example.videoalarm.ui.fullScreenNotify

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FullScreenAlarmScreen(
    modifier : Modifier = Modifier,
    viewModel: FullScreenAlarmViewModel = hiltViewModel()
    ){
    Scaffold(

    ) {
        MediaBody(modifier = Modifier.padding(it))
    }
}

@Composable
fun MediaBody(modifier: Modifier){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}