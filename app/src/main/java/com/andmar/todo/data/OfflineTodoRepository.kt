package com.andmar.todo.data

import kotlinx.coroutines.flow.Flow

class OfflineTodoRepository(private val todoDao: TodoDao): TodoRepository {

    override suspend fun insertTodo(todo: TodoItem) = todoDao.insert(todo)
    
    override suspend fun deleteTodo(todo: TodoItem) = todoDao.delete(todo)
    
    override suspend fun updateTodo(todo: TodoItem) = todoDao.update(todo)
    
    override fun getAllTodo(): Flow<List<TodoItem>> = todoDao.allTodo()
    
    override fun getIdTodo(id: Int): Flow<TodoItem?> = todoDao.idTodo(id)
    
   // override suspend fun insertAdditional(todoAdditional: TodoAdditional) = todoDao.insertAdditional(todoAdditional)
   
   override suspend fun insertTodoCategory(todoCategory: TodoCategory) = todoDao.insertTodoCategory(todoCategory)
   
   override suspend fun updateTodoCategory(todoCategory: TodoCategory) = todoDao.updateTodoCategory(todoCategory)
   
   override fun getCategoryId(id: Int): Flow<TodoCategory> = todoDao.getCategoryId(id)
   
   override fun getAllTodoCategory(): Flow<List<TodoCategory>> = todoDao.getAllTodoCategory()
   
   override suspend fun deleteCategory(todoCategory: TodoCategory) = todoDao.deleteCategory(todoCategory)
   
   override suspend fun insertTodoAdditional(todoAdditional: TodoAdditional) = todoDao.insertTodoAdditional(todoAdditional)
   
   override suspend fun updateTodoAdditional(todoAdditional: TodoAdditional) = todoDao.updateTodoAdditional(todoAdditional)
   
   override suspend fun deleteTodoAdditional(todoAdditional: TodoAdditional) = todoDao.deleteTodoAdditional(todoAdditional)
   
   override fun getAllTodoAdditional(): Flow<List<TodoAdditional>> = todoDao.getAllTodoAdditional()
   
   override fun getTodoAdditionalId(id: Int): Flow<TodoAdditional> = todoDao.getTodoAdditionalId(id)
  
  
    // AlarmItem
    
    override suspend fun insertAlarm(alarm: AlarmItem) = todoDao.insertAlarm(alarm)
    
    override suspend fun deleteAlarm(alarm: AlarmItem) = todoDao.deleteAlarm(alarm)
    
    override suspend fun updateAlarm(alarm: AlarmItem) = todoDao.updateAlarm(alarm)
    
    override fun getAllAlarm(): Flow<List<AlarmItem>> = todoDao.getAllAlarm()
    
    override fun getAlarmList(): List<AlarmItem> = todoDao.getAlarmList()
    
    override fun getItemIdAlarm(itemId: Int): Flow<AlarmItem> = todoDao.getItemIdAlarm(itemId)
    
    override fun getIdAlarm(id: Int): Flow<AlarmItem> = todoDao.getIdAlarm(id)
}
