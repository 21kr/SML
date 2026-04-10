package com.mrp.sml.domain.repository

import kotlinx.coroutines.flow.Flow

interface DeviceConnectionRepository {
    fun observeConnectionState(): Flow<ConnectionState>

    suspend fun discoverDevices()

    suspend fun connectToDevice(deviceId: String)

    suspend fun disconnect()
}

enum class ConnectionState {
    IDLE,
    DISCOVERING,
    CONNECTED,
    FAILED,
    DISCONNECTED,
}
