package com.example.videoalarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val localTime: String,
    val isActive : Boolean,
    val daysOfWeek : String
)