package com.mrp.sml.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrp.sml.data.local.db.entities.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM paired_devices ORDER BY last_connected_at DESC")
    fun getPairedDevices(): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM paired_devices WHERE device_id = :deviceId LIMIT 1")
    suspend fun getDeviceById(deviceId: String): DeviceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(device: DeviceEntity): Long

    @Query("DELETE FROM paired_devices WHERE device_id = :deviceId")
    suspend fun delete(deviceId: String)

    @Query("DELETE FROM paired_devices")
    suspend fun clearAll()
}
