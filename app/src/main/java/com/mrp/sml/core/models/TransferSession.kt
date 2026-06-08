package com.mrp.sml.core.models

data class TransferSession(
    val id: String = java.util.UUID.randomUUID().toString(),
    val deviceId: String,
    val deviceName: String,
    val files: List<TransferFile>,
    val direction: TransferDirection,
    val status: TransferStatus = TransferStatus.PENDING,
    val progress: Float = 0f,
    val speedBytesPerSecond: Double = 0.0,
    val startedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val errorMessage: String? = null
)

enum class TransferDirection { SEND, RECEIVE }

enum class TransferStatus {
    PENDING,
    DISCOVERING,
    CONNECTING,
    TRANSFERRING,
    PAUSED,
    RESUMING,
    COMPLETED,
    FAILED,
    CANCELLED
}
