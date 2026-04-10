package com.mrp.sml.domain.model

data class TransferRecord(
    val id: Long = 0L,
    val fileName: String,
    val fileSizeBytes: Long,
    val mimeType: String,
    val direction: TransferDirection,
    val status: TransferStatus,
    val timestampEpochMillis: Long,
)

enum class TransferDirection {
    SENT,
    RECEIVED,
}

enum class TransferStatus {
    COMPLETED,
    FAILED,
}
