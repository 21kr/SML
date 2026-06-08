package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val discoveryManager: DeviceDiscoveryManager
) : ViewModel() {

    val connectionState: StateFlow<ConnectionState> = discoveryManager.connectionState

    val discoveredDevices: StateFlow<List<Device>> = discoveryManager.discoveredDevices

    private val _isDiscovering = MutableStateFlow(false)
    val isDiscovering: StateFlow<Boolean> = _isDiscovering.asStateFlow()

    fun startDiscovery() {
        viewModelScope.launch {
            _isDiscovering.value = true
            discoveryManager.startDiscovery()
        }
    }

    fun stopDiscovery() {
        viewModelScope.launch {
            discoveryManager.stopDiscovery()
            _isDiscovering.value = false
        }
    }

    fun connectToDevice(deviceId: String) {
        viewModelScope.launch {
            discoveryManager.connectToDevice(deviceId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            discoveryManager.stopDiscovery()
        }
    }
}
