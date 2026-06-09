package com.mrp.sml.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfer_progress")
data class TransferProgressEntity(
    @PrimaryKey
    @ColumnInfo(name = "transfer_id")
    val transferId: String,

    @ColumnInfo(name = "last_chunk_index")
    val lastChunkIndex: Long,

    @ColumnInfo(name = "last_file_index")
    val lastFileIndex: Int = 0,

    @ColumnInfo(name = "transferred_bytes")
    val transferredBytes: Long = 0L,

    @ColumnInfo(name = "total_bytes")
    val totalBytes: Long = 0L
)
