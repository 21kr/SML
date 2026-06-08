package com.mrp.sml.core.models

import android.net.Uri

data class TransferFile(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val size: Long,
    val mimeType: String = "application/octet-stream",
    val uri: Uri? = null,
    val path: String? = null,
    val sha256Hash: String = ""
) {
    val isReady: Boolean get() = uri != null || path != null
}
