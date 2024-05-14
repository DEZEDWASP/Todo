package com.andmar.todo.ui.additional

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import com.andmar.todo.data.TodoAdditional
import com.andmar.todo.data.TodoRepository

class EntryAdditionalViewModel(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository
): ViewModel() {
    
    private val itemId: Int = checkNotNull(savedStateHandle[EntryAdditionalDestination.itemIdArg])
    
    var todoAdditionalState by mutableStateOf(TodoAdditionalState())
        private set

    fun updateAdditionalState(additionalDetails: TodoAdditionalDetails) {
        todoAdditionalState = TodoAdditionalState(additionalDetails = additionalDetails.copy(todoItemId = itemId), isShowButton = showButton(additionalDetails))
    }
    
    fun insertAdditional() = viewModelScope.launch {
        todoRepository.insertTodoAdditional(todoAdditionalState.additionalDetails.toTodoAdditional())
    }
    
    fun showButton(additionalDetails: TodoAdditionalDetails): Boolean {
        return with(additionalDetails) {
            todoAdditional.isNotBlank()
        }
    }
}

data class TodoAdditionalState(
    val additionalDetails: TodoAdditionalDetails = TodoAdditionalDetails(),
    val isShowButton: Boolean = false
)

data class TodoAdditionalDetails(
    val additionalId: Int = 0,
    val todoAdditional: String = "",
    val isDone: Boolean = false,
    val isImportante: Boolean = false,
    val todoItemId: Int = 0
)

fun TodoAdditionalDetails.toTodoAdditional() = TodoAdditional(
    additionalId = additionalId,
    todoAdditional = todoAdditional,
    isDone = isDone,
    isImportante = isImportante,
    todoItemId = todoItemId
)

fun TodoAdditional.toTodoAdditionalDetails() = TodoAdditionalDetails(
    additionalId = additionalId,
    todoAdditional = todoAdditional,
    isDone = isDone,
    isImportante = isImportante,
    todoItemId = todoItemId
)

fun TodoAdditional.toTodoAdditionalState() = TodoAdditionalState(
    additionalDetails = this.toTodoAdditionalDetails()
)