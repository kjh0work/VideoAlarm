package com.example.videoalarm.ui.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videoalarm.R
import com.example.videoalarm.VideoAlarmTopAppBar
import com.example.videoalarm.ui.AppViewModelProvider
import com.example.videoalarm.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

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

    Scaffold (
        topBar = {VideoAlarmTopAppBar(title = stringResource(id = R.string.alarmEntry), canNavigateBack = true,
            navigateUp = navigateUp)},
    ) {
        innerPadding ->
        AlarmEntryBody(
            alarmUiState = viewModel.alarmEntryUiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            onSaveClick = {
                coroutineScope.launch {
                    //viewModel.saveAlarm()
                    viewModel.saveDummyAlarm()
                    navigateBack()
                }
            }
        )
    }
}

@Composable
fun AlarmEntryBody(
    alarmUiState : AlarmEntryUiState,
    modifier: Modifier = Modifier,
    onSaveClick : () -> Unit
){
    Column(
        modifier = modifier
    ) {
        Text(text = "show me")

        //AlarmInputForm
        Button(onClick = onSaveClick) {
            Text(text = "Save")
        }
    }
}

@Preview
@Composable
fun AlarmEntryBodyPreview(
){
    val coroutineScope = rememberCoroutineScope()
    AlarmEntryBody(
        alarmUiState = AlarmEntryUiState(
        ),
        onSaveClick = {
        }
    )
}


