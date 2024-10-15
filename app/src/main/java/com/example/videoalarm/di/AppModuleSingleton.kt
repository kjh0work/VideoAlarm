package com.example.videoalarm.di

import com.example.videoalarm.alarmSystem.AlarmScheduler
import com.example.videoalarm.alarmSystem.AndroidAlarmScheduler
import com.example.videoalarm.data.AlarmRepository
import com.example.videoalarm.data.AlarmRepositoryClass
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleSingleton {

    @Binds
    @Singleton
    abstract fun bindAlarmRepository(impl : AlarmRepositoryClass) : AlarmRepository

    @Binds
    @Singleton
    abstract fun bindAlarmScheduler(impl : AndroidAlarmScheduler) : AlarmScheduler

}