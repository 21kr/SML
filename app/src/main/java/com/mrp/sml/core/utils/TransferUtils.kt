package com.mrp.sml.core.utils

object TransferUtils {

    fun formatSpeed(bytesPerSecond: Double): String {
        return when {
            bytesPerSecond < 1024 -> "%.0f B/s".format(bytesPerSecond)
            bytesPerSecond < 1024 * 1024 -> "%.1f KB/s".format(bytesPerSecond / 1024)
            else -> "%.2f MB/s".format(bytesPerSecond / (1024 * 1024))
        }
    }

    fun formatEta(seconds: Long): String {
        return when {
            seconds < 0 -> "Calculating..."
            seconds < 60 -> "ETA: ${seconds}s"
            seconds < 3600 -> "ETA: ${seconds / 60}m ${seconds % 60}s"
            else -> "ETA: ${seconds / 3600}h ${(seconds % 3600) / 60}m"
        }
    }

    fun calculateEta(remainingBytes: Long, speedBytesPerSecond: Double): Long {
        if (speedBytesPerSecond <= 0) return -1L
        return (remainingBytes / speedBytesPerSecond).toLong()
    }
}
