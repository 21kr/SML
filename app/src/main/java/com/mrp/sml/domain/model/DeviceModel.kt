package com.mrp.sml.domain.model

data class DeviceModel(
    val id: String,
    val name: String,
    val ipAddress: String = "",
    val type: String = "UNKNOWN",
    val isGroupOwner: Boolean = false,
    val signalStrength: Int = 0
)
