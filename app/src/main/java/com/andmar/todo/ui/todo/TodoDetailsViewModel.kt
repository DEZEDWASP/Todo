package com.andmar.todo.ui.todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import com.andmar.todo.data.TodoItem
import com.andmar.todo.data.TodoAdditional
import com.andmar.todo.data.TodoRepository
import com.andmar.todo.data.AlarmItem
import com.andmar.todo.AndroidAlarmScheduler
import com.andmar.todo.VibratorService

public class TodoDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository,
    private val schedular: AndroidAlarmScheduler,
    private val vibrator: VibratorService
): ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[TodoDetailsDestination.itemIdArg])
    
    val uiState: StateFlow<TodoDetailsState> = 
        todoRepository.getIdTodo(itemId)
            .filterNotNull()
            .map {
                TodoDetailsState(todoDetails = it.toTodoDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TodoDetailsState()
            )
    
       
    
     fun deleteTodo(todo: TodoItem) = viewModelScope.launch {
        todoRepository.deleteTodo(todo)
    }
    
    fun updateTodo(todo: TodoItem) = viewModelScope.launch {
        todoRepository.updateTodo(todo)
    }
    
    fun deleteTodoAdditional(todo: TodoAdditional) = viewModelScope.launch {
        todoRepository.deleteTodoAdditional(todo)
    }
    
    fun deleteTodoAdditionals(additionalsList: MutableList<TodoAdditional>) = viewModelScope.launch {
        try {
            additionalsList.forEach { item ->
                todoRepository.deleteTodoAdditional(item)
            }
        } catch(e: Exception) {
            
        } 
        additionalsList.clear()
    }
    
    fun importanteTodoAdditionals(additionalsList: MutableList<TodoAdditional>) = viewModelScope.launch {
        try {
            additionalsList.forEach { item ->
                todoRepository.updateTodoAdditional(item.copy(isImportante = true))
            }
        } catch(e: Exception) {
            
        } 
        additionalsList.clear()
    }
    
    fun doneTodoAdditionals(additionalsList: MutableList<TodoAdditional>) = viewModelScope.launch {
        try {
            additionalsList.forEach { item ->
                todoRepository.updateTodoAdditional(item.copy(isDone = true))
            }
        } catch(e: Exception) {
            
        } 
        additionalsList.clear()
    }
    
    val todoAdditioanlList: StateFlow<TodoAdditionalList> = 
        todoRepository.getAllTodoAdditional().map { TodoAdditionalList(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TodoAdditionalList()
            )
    
    fun updateAdditional(additional: TodoAdditional) = viewModelScope.launch {
        todoRepository.updateTodoAdditional(additional)
    }        
   
   val alarmList: StateFlow<AlarmList> =
        todoRepository.getAllAlarm().map { AlarmList(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = AlarmList()
            )
            
    fun deleteAlarm(alarmItem: AlarmItem) = viewModelScope.launch {
        todoRepository.deleteAlarm(alarmItem)
        alarmItem.let(schedular::cancel)
    }      
    
    fun vibrate() {
        vibrator.vibrate()
    }  
            
   companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }      
}

data class TodoDetailsState(
    val todoDetails: TodoDetails = TodoDetails()
)

data class TodoAdditionalList(val todoAdditionalList: List<TodoAdditional> = listOf())

data class AlarmList(val alarmList: List<AlarmItem> = listOf())