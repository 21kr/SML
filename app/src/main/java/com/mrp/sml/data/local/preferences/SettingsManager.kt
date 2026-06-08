package com.mrp.sml.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sml_settings")

@Singleton
class SettingsManager @Inject constructor(
    private val context: Context
) {
    private object Keys {
        val DEVICE_NAME = stringPreferencesKey("device_name")
        val SAVE_HISTORY = booleanPreferencesKey("save_history")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val CHUNK_SIZE = intPreferencesKey("chunk_size")
        val THEME_COLOR = stringPreferencesKey("theme_color")
    }

    val deviceName: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.DEVICE_NAME] ?: android.os.Build.MODEL
    }

    val saveHistory: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.SAVE_HISTORY] ?: true
    }

    val darkMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.DARK_MODE] ?: false
    }

    val chunkSize: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[Keys.CHUNK_SIZE] ?: 1048576
    }

    val themeColor: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.THEME_COLOR] ?: "#2563EB"
    }

    suspend fun setDeviceName(name: String) {
        context.dataStore.edit { it[Keys.DEVICE_NAME] = name }
    }

    suspend fun setSaveHistory(enabled: Boolean) {
        context.dataStore.edit { it[Keys.SAVE_HISTORY] = enabled }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.DARK_MODE] = enabled }
    }

    suspend fun setChunkSize(size: Int) {
        context.dataStore.edit { it[Keys.CHUNK_SIZE] = size }
    }

    suspend fun setThemeColor(color: String) {
        context.dataStore.edit { it[Keys.THEME_COLOR] = color }
    }
}
