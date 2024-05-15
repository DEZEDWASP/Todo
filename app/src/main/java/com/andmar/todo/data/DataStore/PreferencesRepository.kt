package com.andmar.todo.data.dataStore

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.andmar.todo.ui.todo.SettingsAppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    
    private companion object {
        val UI_COLOR = stringPreferencesKey("ui_color")
    }
    
    val getUiColor = dataStore.data
        .map { preferences -> 
            return@map SettingsAppState(
                colorName = preferences[UI_COLOR] ?: "Transparent"
            )
        }
    
    suspend fun saveSettingsApp(settingsAppState: SettingsAppState) {
        dataStore.edit { preferences ->
            preferences[UI_COLOR] = settingsAppState.colorName
        }
    }
}