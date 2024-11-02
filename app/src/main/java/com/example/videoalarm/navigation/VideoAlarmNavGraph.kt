package com.example.videoalarm.navigation

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.videoalarm.PermissionComposable
import com.example.videoalarm.PermissionDestination
import com.example.videoalarm.ui.alarm.AlarmEntryDestination
import com.example.videoalarm.ui.alarm.AlarmEntryScreen

import com.example.videoalarm.ui.home.HomeDestination
import com.example.videoalarm.ui.home.HomeScreen


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun VideoAlarmNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(navController = navController, startDestination = PermissionDestination.route, modifier = modifier) {

        composable(route = PermissionDestination.route) {
            PermissionComposable(
                onPermissionsGranted = {
                    // 권한이 허용되면 HomeScreen으로 이동
                    navController.navigate(HomeDestination.route) {
                        // 이전의 permission 화면을 백스택에서 제거
                        popUpTo(PermissionDestination.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToAlarmEntry = { navController.navigate( AlarmEntryDestination.route) },
                navigateToAlarmDetail = {})
        }

        composable(route = AlarmEntryDestination.route){
            AlarmEntryScreen(
                navigateBack = {navController.popBackStack()},
                navigateUp = {navController.navigateUp()},
                //navigateToVideoSelect = { navController.navigate(AlarmVideoSelectDestination.route) }
            )
        }

//        composable(route = AlarmVideoSelectDestination.route){
//            AlarmVideoSelectScreen(
//                navigateBack = {navController.popBackStack()},
//                navigateUp = {navController.navigateUp()},
//            )
//        }
    }
}