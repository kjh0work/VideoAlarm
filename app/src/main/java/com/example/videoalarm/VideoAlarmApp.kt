package com.example.videoalarm

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.videoalarm.navigation.VideoAlarmNavHost

//Top level composable
//initialize NavController
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun VideoAlarmApp(navController: NavHostController = rememberNavController()){
    VideoAlarmNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoAlarmTopAppBar(
    title : String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    editButtonClick : @Composable() (RowScope.() -> Unit) = {}
){
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = {
            if( canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_button))
                }
            }
        },
        scrollBehavior = scrollBehavior,
        actions = editButtonClick
    )
}


