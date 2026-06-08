package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.data.local.preferences.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _saveHistory = MutableStateFlow(true)
    val saveHistory: StateFlow<Boolean> = _saveHistory.asStateFlow()

    init {
        viewModelScope.launch {
            settingsManager.saveHistory.collect { value ->
                _saveHistory.value = value
            }
        }
    }

    fun setSaveHistory(enabled: Boolean) {
        viewModelScope.launch {
            settingsManager.setSaveHistory(enabled)
        }
    }
}
