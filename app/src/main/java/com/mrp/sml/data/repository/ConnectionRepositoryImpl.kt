package com.mrp.sml.data.repository

import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.core.utils.WifiUtils
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager
import com.mrp.sml.data.remote.wifi.WifiDirectManager
import com.mrp.sml.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionRepositoryImpl @Inject constructor(
    private val deviceDiscoveryManager: DeviceDiscoveryManager,
    private val wifiDirectManager: WifiDirectManager
) : ConnectionRepository {

    override fun observeConnectionState(): Flow<ConnectionState> {
        return deviceDiscoveryManager.connectionState
    }

    override fun observeDiscoveredDevices(): Flow<Device> {
        return wifiDirectManager.discoveredDevices
    }

    override fun observeGroupOwnerIp(): Flow<String?> {
        return wifiDirectManager.groupOwnerIp
    }

    override suspend fun startDiscovery() {
        deviceDiscoveryManager.startDiscovery()
    }

    override suspend fun stopDiscovery() {
        deviceDiscoveryManager.stopDiscovery()
    }

    override suspend fun connectToDevice(deviceId: String) {
        deviceDiscoveryManager.connectToDevice(deviceId)
    }

    override suspend fun disconnect() {
        wifiDirectManager.disconnect()
        deviceDiscoveryManager.stopDiscovery()
    }

    override suspend fun getConnectionState(): ConnectionState {
        return deviceDiscoveryManager.connectionState.value
    }

    override suspend fun getLocalIpAddress(): String? {
        return WifiUtils.getLocalIpAddress()
    }

    override suspend fun getGroupOwnerIp(): String? {
        return wifiDirectManager.groupOwnerIp.value
    }
}
