package com.example.videoalarm

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.videoalarm.ui.navigation.VideoAlarmNavHost

//Top level composable
//initialize NavController
@Composable
fun VideoAlarmApp(navController: NavHostController = rememberNavController()){
    VideoAlarmNavHost(navController = navController)
}



