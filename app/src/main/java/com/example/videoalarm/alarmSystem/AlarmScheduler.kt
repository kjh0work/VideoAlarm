package com.example.videoalarm.alarmSystem

import com.example.videoalarm.data.Alarm

interface AlarmScheduler
{
    fun schedule(item: Alarm)
    fun cancel(item: Alarm)
}