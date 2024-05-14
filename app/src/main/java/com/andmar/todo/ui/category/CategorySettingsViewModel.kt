package com.andmar.todo.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.andmar.todo.data.TodoCategory
import com.andmar.todo.data.TodoRepository

class CategorySettingsViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {

    val categoryList: StateFlow<CategoryUiState> = 
        todoRepository.getAllTodoCategory().map { CategoryUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CategoryUiState()
            )
    
    fun insertCategory(todoCategory: TodoCategory) = viewModelScope.launch {
        todoRepository.insertTodoCategory(todoCategory)
    }

    fun deleteCategory(todoCategory: TodoCategory) = viewModelScope.launch {
        todoRepository.deleteCategory(todoCategory)
    }
    
    fun updateCategory(todoCategory: TodoCategory) = viewModelScope.launch {
        todoRepository.updateTodoCategory(todoCategory)
    }
    
    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }      
}

data class CategoryUiState(val categoryList: List<TodoCategory> = listOf())