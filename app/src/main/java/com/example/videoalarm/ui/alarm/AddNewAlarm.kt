package com.example.videoalarm.ui.alarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.videoalarm.R
import com.example.videoalarm.ui.theme.VideoAlarmTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAlarmPage(modifier: Modifier = Modifier, navController: NavController
){
    val context = LocalContext.current

    var alarmName by remember { mutableStateOf("") }
    
    val state = rememberTimePickerState()

    //TimePicker(state = state, modifier = Modifier.padding(16.dp))
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeInput(state = state, modifier = Modifier.padding(16.dp))
        TextField(value = alarmName, onValueChange = { alarmName = it }, placeholder = { Text(text = "알람 이름을 입력하세요")} ,
            label = { Text(text = "Alarm name") })
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            //일반 버튼이 아닌 radio buttun으로 후에 교체
            Button(onClick = { /*TODO*/ }) {
                Text(text = "벨소리")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Youtube")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "동영상")
            }
        }
        //when ( ring, youtube, video ) 각각에 따라서
        //composable
        Image(painter = painterResource(id = R.drawable.hamstar_modified), contentDescription = "후에 영상이나 벨소리냐에 따라서 코딩")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 10.dp)) {
                Text(text = "취소")
            }
            Button(onClick = {
//                CoroutineScope(Dispatchers.IO).launch {
//                    val  newAlarm = Alarm(
//                        uid = 0,
//                        name = alarmName,
//                        localTime = "${state.hour}:${state.minute}",
//                        daysOfWeek = "월화수목금토일",
//                        isActive = true
//                    )
//                    db.alarmDao().insert(newAlarm)
 //               }
            }) {
                Text(text = "완료")
            }
        }

    }

}




@Preview(name="AddAlarmPagePreview",showBackground = true, heightDp = 640, widthDp = 320)
@Composable
fun AddAlarmPreview(){
    val navController = rememberNavController()
    VideoAlarmTheme {
        Surface (color = MaterialTheme.colorScheme.background){
            AddNewAlarmPage(navController = navController)
        }
    }
}