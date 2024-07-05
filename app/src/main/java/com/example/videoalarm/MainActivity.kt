package com.example.videoalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.videoalarm.ui.theme.VideoAlarmTheme
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoAlarmTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
            }


        }
    }
}

@Composable
fun MyApp(modifier: Modifier){
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

//-1. Alarm이라는 객체를 하나 만들고 DB와 연결시켜야 한다.
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
                    Text(text = alarm.time.format(DateTimeFormatter.ofPattern("a hh:mm")))

                }
            }
            Text(text = alarm.dateListToString())
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
fun TopInfo(modifier: Modifier, timeUntilNextAlarm: Duration ){
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
                    modifier = Modifier.clickable {  })
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
    VideoAlarmTheme {
        MyApp(modifier = Modifier)
    }
}


@Preview
@Composable
fun AlarmCardPreview(){
    var alarm: Alarm = Alarm("firstAlarm", LocalDateTime.now(), true)
    AlarmCard(modifier = Modifier, alarm)
}

@Preview(name = "TopInfo", showBackground = true)
@Composable
fun TopInfoPreview(){
    // 현재 시간과 알람 시간 설정
    val now = LocalDateTime.now()
    val alarmTime = now.plusHours(5).plusMinutes(30) // 예시: 현재로부터 5시간 30분 후에 알람 설정

    // Duration 계산
    val durationUntilAlarm = Duration.between(now, alarmTime)
    TopInfo(modifier = Modifier, timeUntilNextAlarm = durationUntilAlarm)
}
