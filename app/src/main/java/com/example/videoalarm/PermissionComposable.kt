package com.example.videoalarm

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.videoalarm.navigation.NavigationDestination

object PermissionDestination : NavigationDestination {
    override val route: String
        get() = "permission"
    override val titleRes: Int
        get() = R.string.permission

}
/**
 * 권한 요청 용 composable
 */
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun PermissionComposable(
    onPermissionsGranted : () -> Unit
){
    val context = LocalContext.current
    val notificationPermission = Manifest.permission.POST_NOTIFICATIONS


    val alarmManager = context.getSystemService(AlarmManager::class.java)
    val lifecycleOwner = LocalLifecycleOwner.current

    var notificationPermissionGranted by remember { mutableStateOf(ContextCompat.checkSelfPermission(context, notificationPermission) == PackageManager.PERMISSION_GRANTED) }
    var videoPermissionsGranted by remember { mutableStateOf(checkVideoPermission(context)) }

    //알림 권한
    val notificationPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        isGranted ->
        notificationPermissionGranted = isGranted
        if (notificationPermissionGranted && videoPermissionsGranted && alarmManager.canScheduleExactAlarms()) {
            onPermissionsGranted()
        }

    }


    // 비디오 권한 리스트 구성
    val videoPermissions = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
            arrayOf(
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            )
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
            arrayOf(Manifest.permission.READ_MEDIA_VIDEO)
        }
        else -> {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
    val videoPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
        isGranted ->
        videoPermissionsGranted = isGranted.values.all { it }
        if (notificationPermissionGranted && videoPermissionsGranted && alarmManager.canScheduleExactAlarms()) {
            onPermissionsGranted()
        }
    }

    //1. 정확한 알람 권한 받기
    if(!alarmManager.canScheduleExactAlarms()){
        showAlarmPermissionExplanationDialog(context)
    }


    //2. 앱 외부 설정으로 나갔다가 들어오면 실행 (앱 초기에도 실행되긴 함)
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                //첫번째로 정확한 알람 권한을 얻은 상태라면.
                if(alarmManager.canScheduleExactAlarms()){
                    videoPermissionLauncher.launch(videoPermissions)
                    //3. 알림 권한
                    if(ContextCompat.checkSelfPermission(context, notificationPermission) == PackageManager.PERMISSION_DENIED){
                        notificationPermissionLauncher.launch(notificationPermission)
                    }
                    //4. 비디오 권한
                    if(!checkVideoPermission(context)){
                        videoPermissionLauncher.launch(videoPermissions)
                    }
                }

            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


}


fun checkVideoPermission(context : Context) : Boolean{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_VIDEO,
        ) == PackageManager.PERMISSION_GRANTED)
    ) {
        true
    } else if (
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && (
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
        ) == PackageManager.PERMISSION_GRANTED
        || (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_VIDEO,
        ) == PackageManager.PERMISSION_GRANTED))
    ) {
        true
    } else {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}

fun showAlarmPermissionExplanationDialog(context: Context){
    AlertDialog.Builder(context)
        .setTitle("알람 설정 권한 필요")
        .setMessage("정확한 알람을 설정하기 위한 권한이 필요합니다. 설정에서 권한을 허용해주세요.")
        .setPositiveButton("설정으로 이동"){ _, _ ->
            permissionScheduleExactAlarm(context)
        }
        .setNegativeButton("취소",null)
        .create()
        .show()
}

fun permissionScheduleExactAlarm(context : Context){
    context.startActivity(
        Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).setData(
            Uri.parse("package:${context.packageName}")
        )
    )
}