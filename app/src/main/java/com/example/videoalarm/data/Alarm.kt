package com.example.videoalarm.data

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
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
    @TypeConverters(DateConverters::class)
    val date: DatePickerState,
    val isActive: Boolean,
    @TypeConverters(DaysOfWeekConverters::class)
    val daysOfWeek: MutableList<Boolean>,
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

    fun getDays(): String {
        var str : String = ""
        for(b in daysOfWeek){
            str = if(b) str.plus("1")
            else str.plus("0")
        }
        return str
    }
}
@OptIn(ExperimentalMaterial3Api::class)
class DateConverters{
    @TypeConverter
    fun fromDate(date: Long) : DatePickerState{
        return DatePickerState(CalendarLocale.KOREA, initialSelectedDateMillis = if(date == 0L) null else date)
    }

    @TypeConverter
    fun DatePickerStateToDate(datePickerState: DatePickerState) : Long {
        return datePickerState.selectedDateMillis ?: 0L
    }
}

class DaysOfWeekConverters{
    @TypeConverter
    fun fromDaysOfWeek(days: String) : MutableList<Boolean>{
        val list = mutableListOf(false,false,false,false,false,false,false)
        for(i in days.indices){
            val ch = days[i]
            if(ch == '1') list[i] = true
        }
        return list
    }

    @TypeConverter
    fun MutableListToDaysOfWeek(list: MutableList<Boolean>) : String {
        var str : String = ""
        for(b in list){
            str = if(b) str.plus("1")
                  else str.plus("0")
        }
        return str
    }
}

class TimeConverters{
    @OptIn(ExperimentalMaterial3Api::class)
    @TypeConverter
    fun fromClockTime(time: Int): TimePickerState {
        val hour = time/60
        val minute = time%60
        return TimePickerState(
            hour,
            minute,
            false
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @TypeConverter
    fun TimePickerStateToClockTime(value: TimePickerState): Int {
        return value.hour*60+value.minute;
    }

    /*
    //String <-> TimePickerState
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
    fun TimePickerStateToClockTime(value: TimePickerState): String {
        return "${value.hour}:${value.minute}"
    }
     */
}

