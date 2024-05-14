package com.andmar.todo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import com.andmar.todo.data.TodoItem
import com.andmar.todo.data.TodoCategory
import com.andmar.todo.data.TodoRepository
import com.andmar.todo.VibratorService

class MainViewModel(
    private val todoRepository: TodoRepository,
    private val vibrator: VibratorService
): ViewModel() {

    val mainUiState: StateFlow<MainUiState> = 
        todoRepository.getAllTodo().map { MainUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MainUiState()
            )
            
    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }        

    val mainCategoryState: StateFlow<MainCategoryState> = 
        todoRepository.getAllTodoCategory().map { MainCategoryState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MainCategoryState()
            )
    
    fun updateTodo(todo: TodoItem) = viewModelScope.launch {
        todoRepository.updateTodo(todo)
    }
    
    fun deleteTodoItems(todoItemsList: MutableList<TodoItem>) = viewModelScope.launch {
        
        try {
            todoItemsList.forEach { item ->
                todoRepository.deleteTodo(item)
            }
        } catch(e: Exception) {
                
        }
        todoItemsList.clear()
    }
    
    fun importanteTodoItems(todoItemsList: MutableList<TodoItem>) = viewModelScope.launch {
        
        try {
            todoItemsList.forEach { item ->
                todoRepository.updateTodo(item.copy(isImportante = true))
            }
        } catch(e: Exception) {
                
        }
        todoItemsList.clear()
    }
    
    fun deleteCategory(todoCategory: TodoCategory) = viewModelScope.launch {
        todoRepository.deleteCategory(todoCategory)
    }
    
    fun updateCategory(todoCategory: TodoCategory) = viewModelScope.launch {
        todoRepository.updateTodoCategory(todoCategory)
    }
    
    fun vibrateLongClick() {
        vibrator.vibrate()
    }        
}

data class MainUiState(val todoList: List<TodoItem> = listOf())

data class MainCategoryState(val todoCategoryList: List<TodoCategory> = listOf())