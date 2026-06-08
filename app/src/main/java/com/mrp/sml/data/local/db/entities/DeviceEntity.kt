package com.mrp.sml.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "paired_devices",
    indices = [Index(value = ["device_id"], unique = true)]
)
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "device_id")
    val deviceId: String,

    @ColumnInfo(name = "device_name")
    val deviceName: String,

    @ColumnInfo(name = "last_connected_at")
    val lastConnectedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "ip_address")
    val ipAddress: String = ""
)
