package com.andmar.todo.ui.category

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

class EditCategoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository
): ViewModel(){

    val itemId: Int = checkNotNull(savedStateHandle[EditCategoryDestination.itemIdArg])
    
    var categoryUiState by mutableStateOf(CategoryState())
        private set
        
    init {
        viewModelScope.launch {
            categoryUiState = todoRepository.getCategoryId(itemId)
                .filterNotNull()
                .first()
                .toTodoCategoryState(true)
        }
    }
    
    fun updateCategoryState(category: CategoryDetails) {
        categoryUiState = CategoryState(categoryDetails = category, isShowButton = showButton(category))
    }
    
    fun seveCategory() = viewModelScope.launch {
        todoRepository.updateTodoCategory(categoryUiState.categoryDetails.toTodoCategory())
    }
    
    fun showButton(category: CategoryDetails): Boolean {
        return with(category) {
            nameCategory.isNotBlank()
        }
    }
}
