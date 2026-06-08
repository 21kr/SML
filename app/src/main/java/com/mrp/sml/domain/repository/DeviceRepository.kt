package com.mrp.sml.domain.repository

import com.mrp.sml.domain.model.DeviceModel
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {

    fun observeDiscoveredDevices(): Flow<List<DeviceModel>>

    suspend fun connectToDevice(deviceId: String)

    suspend fun disconnect()

    suspend fun getConnectedDevice(): DeviceModel?

    fun isConnected(): Boolean
}
