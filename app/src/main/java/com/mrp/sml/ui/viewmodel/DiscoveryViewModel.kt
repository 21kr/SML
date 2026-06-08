package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PairingMode {
    WIFI_DIRECT,
    HOTSPOT_FALLBACK,
    MANUAL_IP
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
    private val discoveryManager: DeviceDiscoveryManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PairingUiState())
    val uiState: StateFlow<PairingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            discoveryManager.connectionState.collect { state ->
                _uiState.update { it.copy(connectionState = state) }
            }
        }
        viewModelScope.launch {
            discoveryManager.discoveredDevices.collect { devices ->
                _uiState.update { state ->
                    state.copy(discoveredDevices = devices)
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
            discoveryManager.startDiscovery()
        }
    }

    fun stopDiscovery() {
        viewModelScope.launch {
            discoveryManager.stopDiscovery()
            _uiState.update { it.copy(isDiscovering = false) }
        }
    }

    fun connectToDevice(deviceId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(errorMessage = null) }
            discoveryManager.connectToDevice(deviceId)
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
            discoveryManager.stopDiscovery()
        }
    }
}
