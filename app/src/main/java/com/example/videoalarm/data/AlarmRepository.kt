package com.example.videoalarm.data

import com.example.videoalarm.data.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAllAlarmStream(): Flow<List<Alarm>>

    fun getAlarmStream(id : Long) : Flow<Alarm>

    //fun deleteAlarm(id : Long)

    suspend fun insertItem(alarm: Alarm) : Long

    suspend fun deleteItem(alarm: Alarm)

    suspend fun updateItem(alarm: Alarm)
}