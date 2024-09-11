package com.example.videoalarm.ui.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.videoalarm.VideoAlarmTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmActivityScreen(){
    Scaffold(
        topBar = {
            VideoAlarmTopAppBar(
                title = "알람 울리는 페이지",
                canNavigateBack = false
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "알람 울리기 성공")
        }
    }
}