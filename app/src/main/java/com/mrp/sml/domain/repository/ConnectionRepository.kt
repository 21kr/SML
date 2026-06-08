package com.mrp.sml.domain.repository

import com.mrp.sml.core.models.ConnectionState
import kotlinx.coroutines.flow.Flow

interface ConnectionRepository {

    fun observeConnectionState(): Flow<ConnectionState>

    fun observeDiscoveredDevices(): Flow<com.mrp.sml.core.models.Device>

    suspend fun startDiscovery()

    suspend fun stopDiscovery()

    suspend fun connectToDevice(deviceId: String)

    suspend fun disconnect()

    suspend fun getConnectionState(): ConnectionState

    suspend fun getLocalIpAddress(): String?
}
