package com.mrp.sml.data.repository

import com.mrp.sml.data.local.db.dao.DeviceDao
import com.mrp.sml.data.mapper.DeviceMapper
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager
import com.mrp.sml.domain.model.DeviceModel
import com.mrp.sml.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepositoryImpl @Inject constructor(
    private val deviceDiscoveryManager: DeviceDiscoveryManager,
    private val deviceDao: DeviceDao
) : DeviceRepository {

    override fun observeDiscoveredDevices(): Flow<List<DeviceModel>> {
        return deviceDiscoveryManager.discoveredDevices.map { devices ->
            devices.map { DeviceMapper.coreToDomain(it) }
        }
    }

    override suspend fun connectToDevice(deviceId: String) {
        deviceDiscoveryManager.connectToDevice(deviceId)
    }

    override suspend fun disconnect() {
        deviceDiscoveryManager.stopDiscovery()
    }

    override suspend fun getConnectedDevice(): DeviceModel? {
        return null
    }

    override fun isConnected(): Boolean {
        return false
    }
}
