package com.andmar.todo

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.andmar.todo.data.AppContainer
import com.andmar.todo.data.AppTodoContainer
import com.andmar.todo.data.TodoRepository
import com.andmar.todo.data.dataStore.PreferencesRepository
import dagger.hilt.android.HiltAndroidApp
//import dagger.android.support.DaggerApplication
// Notification
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext


private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
   private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
   )

@HiltAndroidApp
class TodoApplication: Application() {

    lateinit var container: AppContainer
    lateinit var schedular: AndroidAlarmScheduler
    lateinit var vibrator: VibratorService
    lateinit var preferencesRepository: PreferencesRepository
   
    override fun onCreate() {
        super.onCreate()
        
        container = AppTodoContainer(this)
        schedular = AndroidAlarmScheduler(this)
        vibrator = VibratorService(this)
        preferencesRepository = PreferencesRepository(dataStore)
        
        
        createNotificationChannel()
        
    }
    
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TEST"
            val descriptionText = "TEST"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
            CountNotificationService.COUNTER_CHENNEL_ID,
            name,
            importance
            
            ).apply {
                description = descriptionText
            }
            
             val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
             notificationManager.createNotificationChannel(channel)
        }
    }
}