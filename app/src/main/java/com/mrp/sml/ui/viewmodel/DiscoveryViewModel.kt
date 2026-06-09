package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewModelScope
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.domain.repository.ConnectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PairingMode {
    WIFI_DIRECT,
    BLUETOOTH,
    HOTSPOT_FALLBACK
}

enum class PairingRole {
    SENDER,
    RECEIVER
}

data class PairingUiState(
    val mode: PairingRole = PairingRole.SENDER,
    val connectionMethod: PairingMode = PairingMode.WIFI_DIRECT,
    val qrPayload: String? = null,
    val discoveredDevices: List<Device> = emptyList(),
    val selectedDevice: Device? = null,
    val connectionState: ConnectionState = ConnectionState.IDLE,
    val isDiscovering: Boolean = false,
    val errorMessage: String? = null,
    val selectedFileSummary: String = ""
)

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val connectionRepository: ConnectionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PairingUiState())
    val uiState: StateFlow<PairingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            connectionRepository.observeConnectionState().collect { state ->
                _uiState.update { it.copy(connectionState = state) }
            }
        }
        viewModelScope.launch {
            connectionRepository.observeDiscoveredDevices().collect { device ->
                _uiState.update { state ->
                    val current = state.discoveredDevices.toMutableList()
                    val existing = current.indexOfFirst { it.id == device.id }
                    if (existing >= 0) current[existing] = device else current.add(device)
                    state.copy(discoveredDevices = current)
                }
            }
        }
    }

    fun setMode(mode: PairingRole) {
        _uiState.update { it.copy(mode = mode) }
    }

    fun setSelectedFileSummary(summary: String) {
        _uiState.update { it.copy(selectedFileSummary = summary) }
    }

    fun startDiscovery() {
        viewModelScope.launch {
            _uiState.update { it.copy(isDiscovering = true, errorMessage = null) }
            connectionRepository.startDiscovery()
        }
    }

    fun stopDiscovery() {
        viewModelScope.launch {
            connectionRepository.stopDiscovery()
            _uiState.update { it.copy(isDiscovering = false) }
        }
    }

    fun connectToDevice(deviceId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(errorMessage = null) }
            connectionRepository.connectToDevice(deviceId)
        }
    }

    fun setConnectionMethod(method: PairingMode) {
        _uiState.update { it.copy(connectionMethod = method) }
    }

    fun generateQrCode(payload: String) {
        _uiState.update { it.copy(qrPayload = payload) }
    }

    fun clearQrCode() {
        _uiState.update { it.copy(qrPayload = null) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            connectionRepository.disconnect()
        }
    }
}
