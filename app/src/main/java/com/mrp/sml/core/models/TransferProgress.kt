package com.mrp.sml.core.models

data class TransferProgress(
    val transferredBytes: Long = 0L,
    val totalBytes: Long = 0L,
    val speedBytesPerSecond: Double = 0.0,
    val progressPercent: Float = 0f,
    val currentFileName: String = "",
    val currentFileIndex: Int = 0,
    val totalFiles: Int = 0,
    val etaSeconds: Long = 0L
) {
    val isIndeterminate: Boolean get() = totalBytes <= 0L
    val progressFraction: Float get() = if (totalBytes > 0L) (transferredBytes.toFloat() / totalBytes).coerceIn(0f, 1f) else 0f
}
