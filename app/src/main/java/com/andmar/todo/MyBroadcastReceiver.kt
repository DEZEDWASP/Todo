package com.andmar.todo

import android.util.Log
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andmar.todo.data.AlarmItem
import com.andmar.todo.data.TodoRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MyBroadcastReceiver: BroadcastReceiver() {

    lateinit var notificationService: CountNotificationService
    
    @Inject
    lateinit var todoRepository: TodoRepository
    
    override fun onReceive(context: Context, intent: Intent) {
        
        notificationService = CountNotificationService(context)
        
        val alarmItem = intent.getSerializableExtra("aboba") as AlarmItem
        
        notificationService.showNotification(alarmItem.message, alarmItem.id)
        
        CoroutineScope(Dispatchers.IO).launch {
            todoRepository.deleteAlarm(alarmItem)
        }
    }
}
