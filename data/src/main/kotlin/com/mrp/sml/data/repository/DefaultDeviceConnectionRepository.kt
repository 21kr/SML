package com.mrp.sml.data.repository

import com.mrp.sml.domain.repository.ConnectionState
import com.mrp.sml.domain.repository.DeviceConnectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultDeviceConnectionRepository @Inject constructor() : DeviceConnectionRepository {
    private val connectionState = MutableStateFlow(ConnectionState.IDLE)

    override fun observeConnectionState(): Flow<ConnectionState> = connectionState.asStateFlow()

    override suspend fun discoverDevices() {
        connectionState.value = ConnectionState.DISCOVERING
    }

    override suspend fun connectToDevice(deviceId: String) {
        connectionState.value = if (deviceId.isBlank()) {
            ConnectionState.FAILED
        } else {
            ConnectionState.CONNECTED
        }
    }

    override suspend fun disconnect() {
        connectionState.value = ConnectionState.DISCONNECTED
    }
}
