package com.example.videoalarm.ui.alarm

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.videoalarm.R
import com.example.videoalarm.VideoAlarmTopAppBar
import com.example.videoalarm.daysList_en
import com.example.videoalarm.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object AlarmEntryDestination : NavigationDestination{
    override val route: String
        get() = "alarmEntry"
    override val titleRes: Int
        get() = R.string.alarmEntry
}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmEntryScreen(
    navigateBack: () -> Unit,
    navigateUp : () -> Unit,
    modifier : Modifier = Modifier,
    viewModel : AlarmEntryViewModel = hiltViewModel()
){
    val coroutineScope = rememberCoroutineScope()

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
                        viewModel.dateNullCheck()
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
            modifier = Modifier.padding(innerPadding),
            timePickerState = viewModel.alarmEntryUiState.alarmDetails.clockTime,
            isClicked = viewModel.alarmEntryUiState.alarmDetails.daysOfWeek,
            daysPick = {
                viewModel.updateDaysOfWeek(it)
            },
            openDatePickDialog = viewModel.alarmEntryUiState.openDatePickDialog,
            datePickerState = viewModel.alarmEntryUiState.alarmDetails.date,
            updateOpenDatePickDialog = {
                viewModel.updateOpenDatePickDialog()
            },
            clearSelectedDate = {
                viewModel.clearSelectedDate()
            },
            alarmName = viewModel.alarmEntryUiState.alarmDetails.name,
            alarmNameChange = {
                viewModel.alarmNameChange(it)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmEntryBody(
    modifier: Modifier,
    timePickerState : TimePickerState,
    isClicked : MutableList<Boolean>,
    daysPick : (Int) -> Unit,
    openDatePickDialog: Boolean,
    datePickerState: DatePickerState,
    updateOpenDatePickDialog : () -> Unit,
    clearSelectedDate : () -> Unit,
    alarmName: String,
    alarmNameChange : (String) -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(), //상위 modifier를 사용하면 topAppbor를 제외한 범위
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimePick(timePickerState = timePickerState)
        if(!isClicked.contains(true)){
            DatePick(openDatePickDialog = openDatePickDialog,datePickerState = datePickerState, updateOpenDatePickDialog = updateOpenDatePickDialog)
        }
        else{
            clearSelectedDate()
        }
        WeekPick(
            isClicked = isClicked,
            daysPick = daysPick
        )
        NamePick(
            alarmName = alarmName,
            alarmNameChange = alarmNameChange
        )
        ResourceSet(

        )
    }
}


@Composable
fun NamePick(
    alarmName : String,
    alarmNameChange : (String) -> Unit
){
    TextField(value = alarmName,
        onValueChange = {alarmNameChange(it)},
        placeholder = {
            Text(
                text = stringResource(id = R.string.Please_Enter_AlarmName_en),
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
            )
        },
        modifier = Modifier.padding(vertical = 10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.DarkGray
        )
    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePick(
    openDatePickDialog : Boolean,
    datePickerState: DatePickerState,
    updateOpenDatePickDialog : () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)+1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        if(datePickerState.selectedDateMillis == null){
            if(hour < 6) Text(text = "${month}/${day}  (${stringResource(id = daysList_en[dayOfWeek-1])})", modifier = Modifier.padding(start = 30.dp))
            else{
                val cal2 = Calendar.getInstance()
                cal2.add(Calendar.DAY_OF_MONTH, 1)
                val day2 = cal2.get(Calendar.DAY_OF_MONTH)
                val month2 = cal2.get(Calendar.MONTH)+1
                val dayOfWeek2 = cal2.get(Calendar.DAY_OF_WEEK)
                Text(text = "${month2}/${day2}  (${stringResource(id = daysList_en[dayOfWeek2-1])})", modifier = Modifier.padding(start = 30.dp))
            }

        }
        else Text(text = SimpleDateFormat("MM/dd (EEE)", Locale.US).format(Date(datePickerState.selectedDateMillis!!)), modifier = Modifier.padding(start = 30.dp))
        //Text(text = DateFormat.getDateInstance().format(datePickerState.selectedDateMillis).toString(), modifier = Modifier.padding(start = 30.dp))
        IconButton(onClick = { updateOpenDatePickDialog() }) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date", modifier = Modifier.fillMaxHeight())
        }
    }

    if(openDatePickDialog){
        DatePickerDialog(datePickerState = datePickerState, updateOpenDatePickDialog = updateOpenDatePickDialog)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    datePickerState: DatePickerState,
    updateOpenDatePickDialog : () -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH,-1)
    val currentMillis = calendar.timeInMillis
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null && datePickerState.selectedDateMillis!! > currentMillis}
    }

    DatePickerDialog(
        onDismissRequest = {
            updateOpenDatePickDialog() //openDatePickDialog를 false로 해서 DatePickerDialog가 사라지도록 한다.
        },
        confirmButton = {
            TextButton(
                onClick = {
                        updateOpenDatePickDialog()
                },
                enabled = confirmEnabled.value,
                colors = if(confirmEnabled.value) ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary) else ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { updateOpenDatePickDialog() }) { Text("Cancel") }
        },

    ) {
        DatePicker(state = datePickerState)
    }

}

@Composable
fun WeekPick(
    isClicked : MutableList<Boolean>,
    daysPick : (Int) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for(week in 0..6){
            Box(
                modifier = Modifier
                    .clickable(
                        onClick = { daysPick(week) }
                    )
                    .border(
                        BorderStroke(
                            1.dp,
                            if (isClicked[week]) Color(134, 52, 235) else Color.Transparent
                        ), CircleShape
                    )
                    .padding(7.dp)
            ) {
                Text(text = stringResource(id = daysList_en[week]), color = if(isClicked[week]) MaterialTheme.colorScheme.primary else Color.White )
            }
        }

    }
}


@Preview(showBackground = true, backgroundColor = 1)
@Composable
fun WeekPickPreview(){
    WeekPick(mutableListOf(true,true,false,false,false,true,true),{})
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