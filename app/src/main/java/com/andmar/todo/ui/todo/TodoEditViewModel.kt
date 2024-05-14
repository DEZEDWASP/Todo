package com.andmar.todo.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.andmar.todo.data.TodoRepository

public class TodoEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository
): ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[TodoEditDestination.itemIdArg])
    
    var itemUiState by mutableStateOf(TodoUiState())
        private set
        
    init {
        viewModelScope.launch {
            itemUiState = todoRepository.getIdTodo(itemId)
                .filterNotNull()
                .first()
                .toTodoUiState(true)
        }
    }
    
    fun updateUiState(todoDetails: TodoDetails) {
        itemUiState = TodoUiState(todoDetails = todoDetails, isShowButton = true)
    }
    
    suspend fun saveTodo() {
        if(showButton(itemUiState.todoDetails)) {
            todoRepository.updateTodo(itemUiState.todoDetails.toTodoItem())
        }
    }
    
    fun showButton(todoDetails: TodoDetails): Boolean {
        return with(todoDetails) {
            todoTitle.isNotBlank()
        }
    }
}
