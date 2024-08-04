package com.example.videoalarm.data

import android.content.Context

interface AppContainer {
    val alarmRepository: AlarmRepository
}

/**
 * [AppContainer] implementation that provides instance of [AlarmRepositoryClass]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [AlarmRepository]
     */
    override val alarmRepository: AlarmRepository by lazy {
        AlarmRepositoryClass(AlarmDatabase.getDatabase(context).alarmDao())
    }
}