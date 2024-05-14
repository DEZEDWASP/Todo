package com.andmar.todo.ui.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.andmar.todo.data.TodoRepository
import com.andmar.todo.data.TodoCategory

class EntryCategoryViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {

    var todoCategoryUiState by mutableStateOf(CategoryState())
        
    fun updateCategoryState(categoryDetails: CategoryDetails) {
        todoCategoryUiState = CategoryState(categoryDetails = categoryDetails, isShowButton = showButton(categoryDetails) )
    }
    
    fun saveCategory() = viewModelScope.launch {
        todoRepository.insertTodoCategory(todoCategoryUiState.categoryDetails.toTodoCategory())
    }
    
    fun showButton(todoDetails: CategoryDetails): Boolean {
        return with(todoDetails) {
            nameCategory.isNotBlank()
        }
    }
}

data class CategoryState(
    val categoryDetails: CategoryDetails = CategoryDetails(),
    val isShowButton: Boolean = false
)

data class CategoryDetails(
    val categoryId: Int = 0,
    val nameCategory: String = "",
    val isImportante: Boolean = false
)

fun CategoryDetails.toTodoCategory(): TodoCategory = TodoCategory(
     categoryId = categoryId,
    nameCategory = nameCategory,
    isImportante = isImportante
)

fun TodoCategory.toCategoryDetails(): CategoryDetails = CategoryDetails(
    categoryId = categoryId,
    nameCategory = nameCategory,
    isImportante = isImportante
)

fun TodoCategory.toTodoCategoryState(isShowButton: Boolean = false): CategoryState = CategoryState(
    categoryDetails = this.toCategoryDetails(),
    isShowButton = isShowButton
)