package com.mrp.sml.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfer_history")
data class TransferEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val fileName: String,
    val fileSizeBytes: Long,
    val mimeType: String,
    val direction: String,
    val status: String,
    val timestampEpochMillis: Long,
)
