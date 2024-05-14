package com.andmar.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoItem)
    
    @Delete
    suspend fun delete(todo: TodoItem)
    
    @Update
    suspend fun update(todo: TodoItem)
    
    @Query("SELECT * from todo")
    fun allTodo(): Flow<List<TodoItem>>
    
    @Query("SELECT * from todo WHERE id = :id")
    fun idTodo(id: Int): Flow<TodoItem>
    
  //  @Insert
  //  suspend fun insertAdditional(todoAdditional: TodoAdditional)
  
  // TodoCategory
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTodoCategory(todoCategory: TodoCategory)
  
  @Update
  suspend fun updateTodoCategory(todoCategory: TodoCategory)
  
  @Delete
  suspend fun deleteCategory(todoCategory: TodoCategory)
  
  @Query("SELECT * from todo_category WHERE categoryId = :id")
  fun getCategoryId(id: Int): Flow<TodoCategory>
  
  @Query("SELECT * from todo_category")
  fun getAllTodoCategory(): Flow<List<TodoCategory>>
  
  
  
  
  // TodoAdditionl
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTodoAdditional(todoAdditional: TodoAdditional)
  
  @Update
  suspend fun updateTodoAdditional(todoAdditional: TodoAdditional)
  
  @Delete
  suspend fun deleteTodoAdditional(todoAdditional: TodoAdditional)
  
  @Query("SELECT * from todo_additional")
  fun getAllTodoAdditional(): Flow<List<TodoAdditional>>
  
  @Query("SELECT * from todo_additional WHERE additionalId = :id")
  fun getTodoAdditionalId(id: Int): Flow<TodoAdditional>
  
  
  // AlarmItem
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAlarm(alarm: AlarmItem)
  
  @Delete
  suspend fun deleteAlarm(alarm: AlarmItem)
  
  @Update
  suspend fun updateAlarm(alarm: AlarmItem) 
  
  @Query("SELECT * from alarm_item")
  fun getAllAlarm(): Flow<List<AlarmItem>>
  
  @Query("SELECT * from alarm_item")
  fun getAlarmList(): List<AlarmItem>
  
  @Query("SELECT * from alarm_item WHERE todoItemId = :itemId")
  fun getItemIdAlarm(itemId: Int): Flow<AlarmItem>
  
  @Query("SELECT * from alarm_item WHERE id = :id")
  fun getIdAlarm(id: Int): Flow<AlarmItem>
  
}