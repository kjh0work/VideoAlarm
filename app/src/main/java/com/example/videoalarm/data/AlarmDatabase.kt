package com.example.videoalarm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
@TypeConverters(TimeConverters::class, DaysOfWeekConverters::class, DateConverters::class)
abstract class AlarmDatabase : RoomDatabase(){

    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var Instance: AlarmDatabase? = null

        fun getDatabase(context: Context): AlarmDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlarmDatabase::class.java, "alarm_database")
                    .fallbackToDestructiveMigration() //이건 스프링의 create로 설정하는 것과 같다.
                    .build()
                    .also { Instance = it }
            }
        }
    }
}