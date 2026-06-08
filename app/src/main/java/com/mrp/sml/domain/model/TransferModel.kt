package com.mrp.sml.domain.model

data class TransferModel(
    val id: String,
    val fileName: String,
    val fileSize: Long,
    val mimeType: String = "application/octet-stream",
    val direction: TransferDirection,
    val status: TransferStatus,
    val progress: Float = 0f,
    val speedBytesPerSecond: Double = 0.0,
    val startedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val errorMessage: String? = null,
    val sessionToken: String = "",
    val peerDeviceName: String = "",
    val totalFiles: Int = 1
) {
    enum class TransferDirection { SENT, RECEIVED }

    enum class TransferStatus {
        PENDING, TRANSFERRING, PAUSED, COMPLETED, FAILED, CANCELLED
    }
}
