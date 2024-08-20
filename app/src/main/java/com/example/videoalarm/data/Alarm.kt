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
)

class TimeConverters{
    @OptIn(ExperimentalMaterial3Api::class)
    @TypeConverter
    fun fromLocalTime(value: TimePickerState): String {
        return "${value.hour}:${value.minute}"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @TypeConverter
    fun stringToLocalTime(time: String): TimePickerState {
        val times = time.split(":")
        return TimePickerState(
            times[0].toInt(),
            times[1].toInt(),
            false
        )
    }


}

//        val minute = if (time.minute < 10) "0${time.minute}" else "${time.minute}"
//        return if (time.hour == 12) "PM ${time.hour}:$minute"
//        else if (time.hour == 0) "AM 00:$minute"
//        else if (time.hour < 12) "AM ${time.hour}:$minute"
//        else "PM ${time.hour - 12}:$minute"