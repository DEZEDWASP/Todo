package com.andmar.todo.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.andmar.todo.data.dataStore.PreferencesRepository

class TodoSettingsViewModel(
    private val dataStore: PreferencesRepository
): ViewModel() {

    val settingsAppState: StateFlow<SettingsAppState> = 
        dataStore.getUiColor.map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = SettingsAppState()
            )
    
    fun saveSettings(settingsApp: SettingsAppState) = viewModelScope.launch {
        dataStore.saveSettingsApp(settingsApp)
    }
}

data class SettingsAppState(
    val colorText: String = "Blue"
)
