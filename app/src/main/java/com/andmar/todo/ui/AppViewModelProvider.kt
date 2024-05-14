package com.andmar.todo.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras  
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.andmar.todo.TodoApplication
import com.andmar.todo.ui.main.MainViewModel
import com.andmar.todo.ui.todo.TodoEntryViewModel
import com.andmar.todo.ui.todo.TodoEditViewModel
import com.andmar.todo.ui.todo.TodoDetailsViewModel
import com.andmar.todo.ui.todo.TodoReminderViewModel
import com.andmar.todo.ui.todo.TodoSettingsViewModel
import com.andmar.todo.ui.category.CategorySettingsViewModel
import com.andmar.todo.ui.category.EntryCategoryViewModel
import com.andmar.todo.ui.category.EditCategoryViewModel
import com.andmar.todo.ui.additional.EntryAdditionalViewModel
import com.andmar.todo.ui.additional.EditAdditionalViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        
        
        initializer {
            MainViewModel(
                todoApplication().container.todoRepository,
                todoApplication().vibrator
            )
        }
        
        initializer {
            TodoEntryViewModel(
                this.createSavedStateHandle(),
                todoApplication().container.todoRepository
            )
        }
        
        initializer {
            TodoEditViewModel(
                this.createSavedStateHandle(),
                todoApplication().container.todoRepository
            )
        }
        
        initializer {
            TodoDetailsViewModel(
                this.createSavedStateHandle(),
                todoApplication().container.todoRepository,
                todoApplication().schedular,
                todoApplication().vibrator
            )
        }
        
        initializer {
            TodoReminderViewModel(
                this.createSavedStateHandle(),
                todoApplication().container.todoRepository,
                todoApplication().schedular
            )
        }
        
        initializer {
            TodoSettingsViewModel(
                todoApplication().preferencesRepository
            )
        }
        
        initializer {
            CategorySettingsViewModel(
                todoApplication().container.todoRepository
            )
        }
        
        initializer {
            EntryCategoryViewModel(
                todoApplication().container.todoRepository
            )
        }
        
        initializer {
            EditCategoryViewModel(
                this.createSavedStateHandle(),
                todoApplication().container.todoRepository
            )
        }
        
        initializer {
            EntryAdditionalViewModel(
                this.createSavedStateHandle(),
                todoApplication().container.todoRepository
            )
        }
        
        initializer {
            EditAdditionalViewModel(
                this.createSavedStateHandle(),
                todoApplication().container.todoRepository
            )
        }
    }
}

fun CreationExtras.todoApplication(): TodoApplication = 
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TodoApplication)