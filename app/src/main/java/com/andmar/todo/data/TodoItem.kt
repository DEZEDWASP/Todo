package com.andmar.todo.data

import androidx.room.Entity
import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import java.time.ZoneOffset
import java.io.Serializable

@Entity(tableName = "todo_category")
data class TodoCategory(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    val nameCategory: String,
    val isImportante: Boolean
)

@Entity(tableName = "todo",
    foreignKeys = [
        ForeignKey(
            entity = TodoCategory::class,
            parentColumns = ["categoryId"],
            childColumns = ["todoCategoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("todoCategoryId")]
)
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val todoCategoryId: Int = 0,
    val isDone: Boolean,
    val isImportante: Boolean,
    val todoTitle: String,
    val todoInfo: String
)

@Entity(tableName = "todo_additional",
    foreignKeys = [
        ForeignKey(
            entity = TodoItem::class,
            parentColumns = ["id"],
            childColumns = ["todoItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("todoItemId")]
)
data class TodoAdditional(
    @PrimaryKey(autoGenerate = true)
    val additionalId: Int = 0,
    val todoAdditional: String,
    val isDone: Boolean,
    val isImportante: Boolean,
    val todoItemId: Int = 0
)

@Entity(tableName = "alarm_item",
    foreignKeys = [
        ForeignKey(
            entity = TodoItem::class,
            parentColumns = ["id"],
            childColumns = ["todoItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("todoItemId")]
)
data class AlarmItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(LocalDateTimeConverter::class)
    val time: LocalDateTime,
    val isRepeat: Boolean,
    @TypeConverters(TimeUnitConverter::class)
    val timeUnit: TimeUnit,
    val interval: Long,
    val message: String,
    val todoItemId: Int = 0,
    val timeReminderMessage: String
) : Serializable



class LocalDateTimeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let{ LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }
    
    @TypeConverter
    fun toTimestamp(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)
    }
}

class TimeUnitConverter {
    
    @TypeConverter
    fun toString(timeUnit: TimeUnit): String {
        return timeUnit.name
    }
    
    @TypeConverter
    fun toTimeUnit(value: String): TimeUnit? {
        return TimeUnit.valueOf(value)
    }
}