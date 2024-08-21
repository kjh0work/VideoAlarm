package com.example.videoalarm.data

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalTime

@Entity
data class Alarm @OptIn(ExperimentalMaterial3Api::class) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @TypeConverters(TimeConverters::class)
    val clockTime: TimePickerState,
    val isActive: Boolean,
    val daysOfWeek: String,
    val videoPath: String
){
    @OptIn(ExperimentalMaterial3Api::class)
    fun getTime() : String{
        val minute = if (clockTime.minute < 10) "0${clockTime.minute}" else "${clockTime.minute}"
        return if (clockTime.hour == 12) "PM ${clockTime.hour}:$minute"
        else if (clockTime.hour == 0) "AM 00:$minute"
        else if (clockTime.hour < 12) "AM ${clockTime.hour}:$minute"
        else "PM ${clockTime.hour - 12}:$minute"
    }
}

class TimeConverters{
    @OptIn(ExperimentalMaterial3Api::class)
    @TypeConverter
    fun fromClockTime(time: String): TimePickerState {
        val times = time.split(":")
        return TimePickerState(
            times[0].toInt(),
            times[1].toInt(),
            false
        )

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @TypeConverter
    fun stringToClockTime(value: TimePickerState): String {
        return "${value.hour}:${value.minute}"
    }


}

