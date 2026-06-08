package com.mrp.sml.data.remote.discovery

import android.content.Context
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.core.utils.DeviceUtils
import com.mrp.sml.data.remote.nearby.NearbyManager
import com.mrp.sml.data.remote.wifi.WifiDirectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceDiscoveryManager @Inject constructor(
    private val context: Context,
    private val wifiDirectManager: WifiDirectManager,
    private val nearbyManager: NearbyManager
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _connectionState = MutableStateFlow(ConnectionState.IDLE)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _discoveredDevices = MutableStateFlow<List<Device>>(emptyList())
    val discoveredDevices: StateFlow<List<Device>> = _discoveredDevices.asStateFlow()

    suspend fun startDiscovery() {
        _connectionState.value = ConnectionState.DISCOVERING
        _discoveredDevices.value = emptyList()

        wifiDirectManager.registerReceiver()
        val wifiResult = wifiDirectManager.discoverPeers()

        // Fallback: try Nearby Connections
        if (wifiResult.isFailure) {
            Timber.i("WiFi Direct discovery failed, trying Nearby Connections")
            nearbyManager.startDiscovery()
        }

        // Start advertising as discoverable
        nearbyManager.startAdvertising(DeviceUtils.getDeviceName())

        scope.launch {
            wifiDirectManager.connectionState.collect { state ->
                _connectionState.value = state
            }
        }

        scope.launch {
            wifiDirectManager.discoveredDevices.collect { device ->
                val current = _discoveredDevices.value.toMutableList()
                if (current.none { it.id == device.id }) {
                    current.add(device)
                    _discoveredDevices.value = current
                }
            }
        }
    }

    suspend fun stopDiscovery() {
        wifiDirectManager.unregisterReceiver()
        wifiDirectManager.disconnect()
        nearbyManager.stopDiscovery()
        nearbyManager.stopAdvertising()
        _connectionState.value = ConnectionState.IDLE
    }

    suspend fun connectToDevice(deviceId: String) {
        _connectionState.value = ConnectionState.CONNECTING
        wifiDirectManager.connectToDevice(deviceId)
    }

    fun cleanup() {
        wifiDirectManager.unregisterReceiver()
        nearbyManager.cleanup()
    }
}
