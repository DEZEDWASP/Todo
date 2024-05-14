package com.andmar.todo

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.ui.graphics.vector.ImageVector
import com.andmar.todo.data.AppContainer
import com.andmar.todo.data.AppTodoContainer
// Notification
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CountNotificationService(private val context: Context?) {


    fun showNotification(notificationText: String, notifId: Int) {
    
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = notifId

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }    
        
        val pendingIntent = PendingIntent.getActivity(
            context, 
            0, 
            intent, 
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE
            } else 0
        )
        
        val builder = NotificationCompat.Builder(context, COUNTER_CHENNEL_ID)
            .setSmallIcon(R.drawable.notif_icon)
            .setContentTitle("Напоминание")
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent).build()
            
            notificationManager.notify(
                notificationId, builder
            )
    }
    
    companion object {
        const val COUNTER_CHENNEL_ID = "aboba"
    }
}
