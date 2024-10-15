package com.example.videoalarm.di

import android.content.Context
import androidx.room.Room
import com.example.videoalarm.data.AlarmDao
import com.example.videoalarm.data.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AlarmDatabase {
        return Room.databaseBuilder(context, AlarmDatabase::class.java, "alarm_database")
                .fallbackToDestructiveMigration() //이건 스프링의 create로 설정하는 것과 같다.
                .build()
    }

    @Provides
    fun provideAlarmDao(database: AlarmDatabase) : AlarmDao {
        return database.alarmDao();
    }

}