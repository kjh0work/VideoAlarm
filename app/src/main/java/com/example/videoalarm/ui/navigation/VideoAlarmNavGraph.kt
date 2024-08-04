package com.example.videoalarm.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.videoalarm.ui.home.HomeDestination
import com.example.videoalarm.ui.home.HomeScreen


@Composable
fun VideoAlarmNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(navController = navController, startDestination = HomeDestination.route, modifier = modifier) {
        composable(route = HomeDestination.route) {
            HomeScreen(navigateToAlarmEntry = { /*TODO*/ }, navigateToAlarmDetail = {})
        }
    }
}