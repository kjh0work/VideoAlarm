package com.example.videoalarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalTime

@Entity
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val localTime: String,
    val isActive : Boolean,
    val daysOfWeek : String,
    val videoPath : String
)

//class Converters{
//    @TypeConverter
//    fun fromLocalTime(value: String?): LocalTime? {
//        return
//    }
//
//    @TypeConverter
//    fun datetoLocalTime(date: LocalTime?): String? {
//        return date?.toString();
//    }
//
//}