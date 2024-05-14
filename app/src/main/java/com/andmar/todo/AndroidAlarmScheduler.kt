package com.andmar.todo

import android.util.Log
import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.andmar.todo.data.AppContainer
import com.andmar.todo.data.AppTodoContainer
import com.andmar.todo.data.AlarmItem
import com.andmar.todo.data.TodoRepository
// Notification
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import java.time.ZoneId


class AndroidAlarmScheduler(
    private val context: Context
): AlarmScheduler {
    
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, MyBroadcastReceiver::class.java).apply {
            putExtra("aboba", alarmItem)
        } 
        val triggerAtMillis = alarmItem.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
        
        val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
    }
    
    override fun cancel(alarmItem: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                Intent(context, MyBroadcastReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
    
    override fun rebutSchedule(alarmList: List<AlarmItem>) {
        alarmList.forEach { alarmItem ->
            val intent = Intent(context, MyBroadcastReceiver::class.java).apply {
                putExtra("aboba", alarmItem)
            } 
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmItem.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                PendingIntent.getBroadcast(
                    context,
                    alarmItem.id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }
}
