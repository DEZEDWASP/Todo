package com.andmar.todo.ui.additional

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.andmar.todo.data.TodoRepository

class EditAdditionalViewModel(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository
): ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[EditAdditionalDestination.itemIdArg])

    var additionalUiState by mutableStateOf(TodoAdditionalState())
        private set
        
    init {
        viewModelScope.launch {
            additionalUiState = todoRepository.getTodoAdditionalId(itemId)
                .filterNotNull()
                .first()
                .toTodoAdditionalState()
        }
    }    
    
    fun updateAdditionalUiState(todoAdditional: TodoAdditionalDetails) {
        additionalUiState = TodoAdditionalState(additionalDetails = todoAdditional)
    }  
    
    fun insertAdditional() = viewModelScope.launch {
        todoRepository.updateTodoAdditional(additionalUiState.additionalDetails.toTodoAdditional())
    }
}
