package com.example.videoalarm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.videoalarm.data.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Insert
    suspend fun insert(alarm: Alarm)

    @Update
    suspend fun update(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)


    @Query("SELECT * FROM Alarm ORDER BY clockTime ASC")
    fun getAllAlarms(): Flow<List<Alarm>>

    @Query("SELECT * FROM Alarm WHERE id = :id")
    fun getAlarm(id : Long) : Flow<Alarm>


}

//@Query("delete from Alarm where id = :id")
//    fun deleteAlarm(id : Long)