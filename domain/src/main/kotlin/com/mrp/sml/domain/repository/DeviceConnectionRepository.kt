package com.mrp.sml.domain.repository

import kotlinx.coroutines.flow.Flow

interface DeviceConnectionRepository {
    fun observeConnectionState(): Flow<ConnectionState>

    fun observeDiscoveredDevices(): Flow<List<DiscoveredDevice>>

    suspend fun discoverDevices()

    suspend fun connectToDevice(deviceId: String)

    suspend fun disconnect()
}

data class DiscoveredDevice(
    val id: String,
    val name: String,
)

enum class ConnectionState {
    IDLE,
    DISCOVERING,
    CONNECTED,
    FAILED,
    DISCONNECTED,
}
