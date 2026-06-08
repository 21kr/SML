package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.data.local.preferences.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val deviceName: String = "",
    val saveHistory: Boolean = true,
    val saveLocation: String = "Downloads/SML"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsManager.saveHistory.collect { value ->
                _uiState.update { it.copy(saveHistory = value) }
            }
        }
        viewModelScope.launch {
            settingsManager.deviceName.collect { value ->
                _uiState.update { it.copy(deviceName = value) }
            }
        }
    }

    fun setSaveHistory(enabled: Boolean) {
        viewModelScope.launch {
            settingsManager.setSaveHistory(enabled)
        }
    }

    fun setDeviceName(name: String) {
        viewModelScope.launch {
            settingsManager.setDeviceName(name)
            _uiState.update { it.copy(deviceName = name) }
        }
    }
}
