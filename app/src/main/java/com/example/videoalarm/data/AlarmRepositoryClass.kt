package com.example.videoalarm.data

import com.example.videoalarm.data.Alarm
import com.example.videoalarm.data.AlarmDao
import com.example.videoalarm.data.AlarmRepository
import kotlinx.coroutines.flow.Flow

/*
* 해당 앱에서는 사용하는 db가 alarm하나뿐이기 때문에
* 굳이 따로 repository Interface를 만들지 않음.
* */
class AlarmRepositoryClass (private val alarmDao : AlarmDao) : AlarmRepository {
    override fun getAllAlarmStream():Flow<List<Alarm>> = alarmDao.getAllAlarms()

    override fun getAlarmStream(id : Int) : Flow<Alarm> = alarmDao.getAlarm(id)

    override suspend fun insertItem(alarm: Alarm) = alarmDao.insert(alarm)

    override suspend fun deleteItem(alarm: Alarm) = alarmDao.delete(alarm)

    override suspend fun updateItem(alarm: Alarm) = alarmDao.update(alarm)

}