package com.mrp.sml.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrp.sml.data.local.db.dao.DeviceDao
import com.mrp.sml.data.local.db.dao.TransferDao
import com.mrp.sml.data.local.db.entities.DeviceEntity
import com.mrp.sml.data.local.db.entities.TransferEntity

@Database(
    entities = [TransferEntity::class, DeviceEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transferDao(): TransferDao
    abstract fun deviceDao(): DeviceDao
}
