package com.example.videoalarm.data

import android.content.Context
import com.example.videoalarm.alarmSystem.AndroidAlarmScheduler

interface AppContainer {
    val alarmRepository: AlarmRepository
    val alarmScheduler: AndroidAlarmScheduler
}

/**
 * [AppContainer] implementation that provides instance of [AlarmRepositoryClass]
 */
class AppDataContainer(private val context: Context
) : AppContainer {

    private val _alarmScheduler by lazy { AndroidAlarmScheduler(context) }
    /**
     * Implementation for [AlarmRepository]
     */
    override val alarmRepository: AlarmRepository by lazy {
        AlarmRepositoryClass(AlarmDatabase.getDatabase(context).alarmDao())
    }

    override val alarmScheduler: AndroidAlarmScheduler
        get() = _alarmScheduler
}