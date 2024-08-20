package com.example.videoalarm.ui.alarm

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videoalarm.R
import com.example.videoalarm.VideoAlarmTopAppBar
import com.example.videoalarm.ui.AppViewModelProvider
import com.example.videoalarm.ui.navigation.NavigationDestination

object AlarmEntryDestination : NavigationDestination{
    override val route: String
        get() = "alarmEntry"
    override val titleRes: Int
        get() = R.string.alarmEntry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmEntryScreen(
    navigateBack: () -> Unit,
    navigateUp : () -> Unit,
    modifier : Modifier = Modifier,
    viewModel : AlarmEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    //timePickerState.hour는 24시 기준으로 출력된다.
    val timePickerState = rememberTimePickerState(
        initialHour = 6,
        initialMinute = 0,
        is24Hour = false
    )

    Scaffold (
        topBar = {VideoAlarmTopAppBar(title = stringResource(id = R.string.alarmEntry), canNavigateBack = true,
            navigateUp = navigateUp)},
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = navigateBack, modifier = Modifier.weight(0.5f)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
                    }
                    IconButton(onClick = {
                        viewModel.updateAlarmDetail(viewModel.alarmEntryUiState.alarmDetails.copy(clockTime = timePickerState))
                        viewModel.saveAlarm()
                        navigateUp()
                    }, modifier = Modifier.weight(0.5f)) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "save")
                    }
                }
            }
        }
    ) {
        innerPadding ->
        AlarmEntryBody(
            alarmUiState = viewModel.alarmEntryUiState,
            modifier = Modifier.padding(innerPadding),
            timePickerState = timePickerState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmEntryBody(
    alarmUiState : AlarmEntryUiState,
    modifier: Modifier,
    timePickerState : TimePickerState
){
    Column(
        modifier = modifier.fillMaxWidth(), //상위 modifier를 사용하면 topAppbor를 제외한 범위
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimePick(timePickerState = timePickerState)
        WeekPick()
        ResourceSet(

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePick(
    timePickerState: TimePickerState
){
    Box {
       TimePicker(
           state = timePickerState,
           colors = TimePickerDefaults.colors(
               clockDialColor = Color(112, 113, 125),
               timeSelectorSelectedContainerColor = Color(112, 113, 125)
            )
           )
    }
}

@Composable
fun WeekPick(){

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 200)
@Composable
fun TimePickPreview(){
    val timePickerState = rememberTimePickerState(
        initialHour = 6,
        initialMinute = 0,
        is24Hour = false
    )
    TimePick(timePickerState)
}

@Composable
fun ResourceSet(){

}