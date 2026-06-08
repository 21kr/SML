package com.mrp.sml.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transfer_history",
    indices = [Index(value = ["session_token"], unique = false)]
)
data class TransferEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "file_name")
    val fileName: String,

    @ColumnInfo(name = "file_size_bytes")
    val fileSizeBytes: Long,

    @ColumnInfo(name = "mime_type")
    val mimeType: String = "application/octet-stream",

    @ColumnInfo(name = "direction")
    val direction: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "progress")
    val progress: Float = 0f,

    @ColumnInfo(name = "session_token")
    val sessionToken: String = "",

    @ColumnInfo(name = "timestamp_epoch_millis")
    val timestampEpochMillis: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "completed_at_millis")
    val completedAtMillis: Long? = null,

    @ColumnInfo(name = "error_message")
    val errorMessage: String? = null
)
