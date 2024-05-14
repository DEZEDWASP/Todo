package com.andmar.todo.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: TodoItem)
    
    suspend fun deleteTodo(todo: TodoItem)
    
    suspend fun updateTodo(todo: TodoItem)
    
    fun getAllTodo(): Flow<List<TodoItem>>
    
    fun getIdTodo(id: Int): Flow<TodoItem?>
    
   // suspend fun insertAdditional(todoAdditional: TodoAdditional)
   
   suspend fun insertTodoCategory(todoCategory: TodoCategory)
   
   suspend fun updateTodoCategory(todoCategory: TodoCategory)
   
   fun getCategoryId(id: Int): Flow<TodoCategory>
   
   fun getAllTodoCategory(): Flow<List<TodoCategory>>
   
   suspend fun deleteCategory(todoCategory: TodoCategory)
   
   suspend fun insertTodoAdditional(todoAdditional: TodoAdditional)
   
   suspend fun updateTodoAdditional(todoAdditional: TodoAdditional)
   
   suspend fun deleteTodoAdditional(todoAdditional: TodoAdditional)
   
   fun getAllTodoAdditional(): Flow<List<TodoAdditional>>
   
   fun getTodoAdditionalId(id: Int): Flow<TodoAdditional>
   
   
   // AlarmItem
   
   suspend fun insertAlarm(alarm: AlarmItem)
   
   suspend fun deleteAlarm(alarm: AlarmItem)
   
   suspend fun updateAlarm(alarm: AlarmItem)
   
   fun getAllAlarm(): Flow<List<AlarmItem>>
   
   fun getAlarmList(): List<AlarmItem>
   
   fun getItemIdAlarm(itemId: Int): Flow<AlarmItem>
   
   fun getIdAlarm(id: Int): Flow<AlarmItem>
   
}
