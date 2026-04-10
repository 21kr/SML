package com.mrp.sml.domain.repository

import kotlinx.coroutines.flow.Flow

interface FileTransferRepository {
    fun observeTransferProgress(): Flow<TransferProgress>

    suspend fun sendFile(sourcePath: String, destinationAddress: String)

    suspend fun receiveFile(destinationPath: String)
}

data class TransferProgress(
    val transferredBytes: Long,
    val totalBytes: Long,
    val speedBytesPerSecond: Double,
)
