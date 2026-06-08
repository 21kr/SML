package com.mrp.sml.data.mapper

import com.mrp.sml.core.models.Device
import com.mrp.sml.core.models.DeviceType
import com.mrp.sml.data.local.db.entities.DeviceEntity
import com.mrp.sml.domain.model.DeviceModel

object DeviceMapper {

    fun coreToDomain(device: Device): DeviceModel {
        return DeviceModel(
            id = device.id,
            name = device.name,
            ipAddress = device.ipAddress,
            type = device.deviceType.name,
            isGroupOwner = device.isGroupOwner,
            signalStrength = device.signalStrength
        )
    }

    fun domainToCore(model: DeviceModel): Device {
        return Device(
            id = model.id,
            name = model.name,
            ipAddress = model.ipAddress,
            deviceType = try { DeviceType.valueOf(model.type) } catch (e: Exception) { DeviceType.UNKNOWN },
            isGroupOwner = model.isGroupOwner,
            signalStrength = model.signalStrength
        )
    }

    fun entityToDomain(entity: DeviceEntity): DeviceModel {
        return DeviceModel(
            id = entity.deviceId,
            name = entity.deviceName,
            ipAddress = entity.ipAddress
        )
    }

    fun domainToEntity(model: DeviceModel): DeviceEntity {
        return DeviceEntity(
            deviceId = model.id,
            deviceName = model.name,
            ipAddress = model.ipAddress
        )
    }
}
