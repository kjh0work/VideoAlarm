package com.example.videoalarm.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videoalarm.ui.alarm.AddNewAlarmPage
import com.example.videoalarm.R
import com.example.videoalarm.VideoAlarmTopAppBar
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.ui.AppViewModelProvider
import com.example.videoalarm.ui.alarm.AlarmDetails
import com.example.videoalarm.ui.navigation.NavigationDestination
import com.example.videoalarm.ui.theme.VideoAlarmTheme
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object HomeDestination : NavigationDestination {
    override val route: String
        get() = "home"
    override val titleRes: Int
        get() = R.string.app_name

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToAlarmEntry: () -> Unit,
    navigateToAlarmDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()

    BackHandler(enabled = viewModel.isEditMode) {
        viewModel.changeEditMode()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            VideoAlarmTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                editButtonClick = {
                    IconButton(onClick = { viewModel.changeEditMode() }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAlarmEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "go to AlarmEntry Screen"
                )
            }
        },
        bottomBar = {
            if(viewModel.checkedAlarmList.isNotEmpty()){
                BottomAppBar{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = { viewModel.deleteAlarm() }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete alarms")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        HomeBody(
            modifier = modifier.fillMaxSize(),
            alarmList = homeUiState.alarmList,
            contentPadding = innerPadding,
            switchChange = {alarm,switchChanged ->
                           viewModel.updateAlarm(alarm.copy(isActive = switchChanged))
            },
            isEditMode = viewModel.isEditMode,
            editCheck = { alarm, b ->
                if(b) viewModel.checkedAlarmList.add(alarm)
                else viewModel.checkedAlarmList.remove(alarm)
            },
            checkedAlarmList = viewModel.checkedAlarmList
        )
    }
}

@Composable
fun HomeBody(
    alarmList: List<Alarm>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    switchChange: (Alarm, Boolean) -> Unit,
    isEditMode: Boolean,
    editCheck : (Alarm, Boolean) -> Unit,
    checkedAlarmList : SnapshotStateList<Alarm>
){
    if(alarmList.isEmpty()){
        Box(
            modifier = modifier.padding(contentPadding).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(text = stringResource(id = R.string.no_alarm))
        }
    }
    else{
        AlarmList(
            alarmList = alarmList,
            contentPadding = contentPadding,
            switchChange = switchChange,
            isEditMode = isEditMode,
            editCheck = editCheck,
            checkedAlarmList = checkedAlarmList
        )
    }
}

@Composable
fun AlarmList(
    modifier: Modifier = Modifier,
    alarmList: List<Alarm>,
    contentPadding: PaddingValues,
    switchChange: (Alarm, Boolean) -> Unit,
    isEditMode: Boolean,
    editCheck : (Alarm, Boolean) -> Unit,
    checkedAlarmList : SnapshotStateList<Alarm>
){
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = alarmList, key = {it.id}){ item ->
            AlarmItem(
                item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { },
                switchChange = switchChange,
                isEditMode = isEditMode,
                editCheck = editCheck,
                checkedAlarmList = checkedAlarmList
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmItem(
    item: Alarm,
    modifier: Modifier,
    switchChange: (Alarm, Boolean) -> Unit,
    isEditMode: Boolean,
    editCheck : (Alarm, Boolean) -> Unit,
    checkedAlarmList : SnapshotStateList<Alarm>
){

    Card(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp),
        onClick = {}
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
            ) {
            if(isEditMode){
                Checkbox(checked = checkedAlarmList.contains(item), onCheckedChange = {editCheck(item, it)})
            }

            Column(
                modifier = Modifier
                    .padding(start = 3.dp)
                    .weight(0.4f)
            ) { //name, time
                Text(text = item.name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(vertical = 3.dp))
                Text(text = "${item.clockTime.hour}:${item.clockTime.minute}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 3.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f),
                verticalAlignment = Alignment.CenterVertically
            ) {//days, isActive
                if(item.videoPath.isNotEmpty()){
                    Column(
                        modifier = Modifier.weight(0.4f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = item.daysOfWeek)
                        Image(imageVector = Icons.Default.PlayArrow, contentDescription = "video thumbnail",
                            )
                    }
                }
                else{
                    Text(text = item.daysOfWeek, modifier = Modifier.weight(0.4f), textAlign = TextAlign.Center)
                }

                Switch(
                    checked = item.isActive, onCheckedChange = {switchChange(item, it)},
                    modifier = Modifier.padding(end = 10.dp)
                    )
            }
        }
    }

}


