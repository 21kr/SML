package com.mrp.sml.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrp.sml.data.local.db.dao.DeviceDao
import com.mrp.sml.data.local.db.dao.TransferDao
import com.mrp.sml.data.local.db.dao.TransferProgressDao
import com.mrp.sml.data.local.db.entities.DeviceEntity
import com.mrp.sml.data.local.db.entities.TransferEntity
import com.mrp.sml.data.local.db.entities.TransferProgressEntity

@Database(
    entities = [TransferEntity::class, DeviceEntity::class, TransferProgressEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transferDao(): TransferDao
    abstract fun deviceDao(): DeviceDao
    abstract fun transferProgressDao(): TransferProgressDao
}
