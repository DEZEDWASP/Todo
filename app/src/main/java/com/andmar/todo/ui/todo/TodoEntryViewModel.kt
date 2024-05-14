package com.andmar.todo.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import com.andmar.todo.data.TodoItem
import com.andmar.todo.data.TodoRepository

public class TodoEntryViewModel(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository
): ViewModel() {

    var todoUiState by mutableStateOf(TodoUiState())
        private set
        
    fun updateUiState(todoDetails: TodoDetails) {
        todoUiState = TodoUiState(todoDetails = todoDetails, isShowButton = showButton(todoDetails))
    }
    
    fun showButton(todoDetails: TodoDetails = todoUiState.todoDetails): Boolean {
        return with(todoDetails) {
            todoTitle.isNotBlank() || todoInfo.isNotBlank()
        }
    }
    
    suspend fun saveTodo() {
        todoRepository.insertTodo(todoUiState.todoDetails.toTodoItem())
    }    
    
    
    val categoryId: Int = checkNotNull(savedStateHandle[TodoEntryDestination.itemIdArg])
}

data class TodoUiState(
    val todoDetails: TodoDetails = TodoDetails(),
    val isShowButton: Boolean = false
)

data class TodoDetails(
    val id: Int = 0,
    val todoCategoryId: Int = 0,
    val isDone: Boolean = false,
    val isImportante: Boolean = false,
    val todoTitle: String = "",
    val todoInfo: String = ""
)

fun TodoDetails.toTodoItem(): TodoItem = TodoItem(
    id = id,
    todoCategoryId = todoCategoryId,
    isDone = isDone,
    isImportante = isImportante,
    todoTitle = todoTitle,
    todoInfo = todoInfo
)

fun TodoItem.toTodoDetails(): TodoDetails = TodoDetails(
    id = id,
    todoCategoryId = todoCategoryId,
    isDone = isDone,
    isImportante = isImportante,
    todoTitle = todoTitle,
    todoInfo = todoInfo
)

fun TodoItem.toTodoUiState(isShowButton: Boolean = false): TodoUiState = TodoUiState(
    todoDetails = this.toTodoDetails(),
    isShowButton = isShowButton
)