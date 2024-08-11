package com.example.videoalarm

import android.app.Application
import android.util.Log
import com.example.videoalarm.data.AppContainer
import com.example.videoalarm.data.AppDataContainer

class VideoAlarmApplication : Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        Log.d("dddd", "Application Created, Container Initialized")
    }
}