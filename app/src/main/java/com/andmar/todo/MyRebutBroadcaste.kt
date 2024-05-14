package com.andmar.todo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.AlarmManager
import android.app.Application
import android.os.Build
import com.andmar.todo.data.AppContainer
import com.andmar.todo.data.AppTodoContainer
import com.andmar.todo.data.AlarmItem
import com.andmar.todo.data.TodoRepository
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import java.time.ZoneId

@AndroidEntryPoint
class MyRebutBroadcaste: BroadcastReceiver() {

    @Inject
    lateinit var todoRepository: TodoRepository
            
    
    
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
        
            val androidAlarmScheduler = AndroidAlarmScheduler(context)
          //  val alarmManager = context.getSystemService(AlarmManager::class.java)
            
            CoroutineScope(Dispatchers.IO).launch {
                val alarmList = todoRepository.getAlarmList()
            
                delay(4000)
                androidAlarmScheduler.rebutSchedule(alarmList)
            }
        }
    }
}