package com.mrp.sml.core.models

data class Device(
    val id: String,
    val name: String,
    val ipAddress: String = "",
    val deviceType: DeviceType = DeviceType.UNKNOWN,
    val isGroupOwner: Boolean = false,
    val signalStrength: Int = 0
)

enum class DeviceType {
    PHONE,
    TABLET,
    LAPTOP,
    UNKNOWN
}
