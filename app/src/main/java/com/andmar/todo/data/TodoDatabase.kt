package com.andmar.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        TodoItem::class,
        TodoAdditional::class,
        TodoCategory::class,
        AlarmItem::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class, TimeUnitConverter::class)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao
    
    companion object {
        @Volatile
        private var Instance: TodoDatabase? = null
    
        fun getDatabase(context: Context): TodoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TodoDatabase::class.java, "todoDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            } 
        }
    }
}
