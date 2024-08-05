package com.example.videoalarm.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videoalarm.ui.alarm.AddNewAlarmPage
import com.example.videoalarm.R
import com.example.videoalarm.VideoAlarmTopAppBar
import com.example.videoalarm.data.Alarm
import com.example.videoalarm.ui.navigation.NavigationDestination
import com.example.videoalarm.ui.theme.VideoAlarmTheme
import java.time.Duration
import java.time.LocalDateTime

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
    modifier: Modifier = Modifier
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            VideoAlarmTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
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
        }
    ) { innerPadding ->
        Column (modifier = Modifier.padding(innerPadding)){
            Text(text = "home page")
        }

    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier){
    val now = LocalDateTime.now()
    val alarmTime = now.plusHours(5).plusMinutes(30)
    val durationUntilAlarm = Duration.between(now, alarmTime)

    Surface (

        color = MaterialTheme.colorScheme.background
    ) {
        Column() {
            TopInfo(modifier = Modifier, timeUntilNextAlarm = durationUntilAlarm)
            //AlarmCard(modifier = Modifier, alarm = Alarm(LocalDateTime.now()) )
        }
    }


}

@Composable
fun AlarmCard(modifier: Modifier, alarm : Alarm){

    var checked by remember {
        mutableStateOf(alarm.isActive)
    }

    Card (

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = alarm.name)
                Row {
                    Text(text = alarm.localTime)

                }
            }
            Text(text = "날짜")
            Switch(checked = checked, onCheckedChange = {checked = it},
                modifier = Modifier.padding(end = 20.dp),
                thumbContent = {
                    if(checked) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize))
                    } else {
                        null
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.secondary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.tertiary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            )
        }


    }
}


//1. 다음 알람까지 몇 분 남았는지
//2. 알람 추가 버튼과 편집 버튼
@Composable
fun TopInfo(modifier: Modifier, timeUntilNextAlarm: Duration){
    val hours = timeUntilNextAlarm.toHours() % 24
    val minutes = timeUntilNextAlarm.toMinutes() % 60
    Surface(
        //color = MaterialTheme.colorScheme.secondary
    ) {
        Column {
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .paddingFromBaseline(top = 40.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${hours}시간 ${minutes}분 뒤 알람이 울립니다")

            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adding an Alarm",
                    modifier = Modifier.clickable { })
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Remove an Alarm",
                    modifier = Modifier.clickable {  })

            }
            Divider(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }

    }

}





@Preview(showBackground = true, heightDp = 640, widthDp = 320)
@Composable
fun MyAppPreview(){
    val navController = rememberNavController()
    VideoAlarmTheme {
        MyApp()
    }
}