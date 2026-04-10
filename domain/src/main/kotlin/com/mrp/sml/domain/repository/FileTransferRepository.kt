package com.mrp.sml.domain.repository

import kotlinx.coroutines.flow.Flow

interface FileTransferRepository {
    fun observeTransferProgress(): Flow<TransferProgress>

    fun observeTransferStatus(): Flow<TransferStatusUpdate>

    suspend fun sendFile(sourcePath: String, destinationAddress: String)

    suspend fun sendFiles(sourcePaths: List<String>, destinationAddress: String)

    suspend fun receiveFile(destinationPath: String)

    suspend fun receiveFiles(destinationDirectoryPath: String)
}

data class TransferProgress(
    val transferredBytes: Long,
    val totalBytes: Long,
    val speedBytesPerSecond: Double,
    val progressPercent: Float,
) {
    val speedMegaBytesPerSecond: Double
        get() = speedBytesPerSecond / BYTES_PER_MEGABYTE

    companion object {
        private const val BYTES_PER_MEGABYTE = 1024.0 * 1024.0
    }
}

data class TransferStatusUpdate(
    val status: TransferExecutionStatus,
    val userMessage: String? = null,
)

enum class TransferExecutionStatus {
    IDLE,
    SENDING,
    RECEIVING,
    RETRYING,
    COMPLETED,
    FAILED,
}
