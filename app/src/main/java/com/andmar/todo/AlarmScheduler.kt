package com.andmar.todo

import com.andmar.todo.data.AlarmItem

interface AlarmScheduler {

    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
    fun rebutSchedule(alarmList: List<AlarmItem>)
}
