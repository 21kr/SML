package com.mrp.sml.core.extensions

import android.content.Context
import android.net.Uri
import android.widget.Toast
import java.io.File

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.cacheDirFile(fileName: String): File {
    return File(cacheDir, fileName)
}

fun Context.getFileName(uri: Uri): String {
    var name = "unknown"
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
        if (nameIndex >= 0 && cursor.moveToFirst()) {
            name = cursor.getString(nameIndex)
        }
    }
    return name
}

fun Context.getFileSize(uri: Uri): Long {
    var size = 0L
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val sizeIndex = cursor.getColumnIndex(android.provider.OpenableColumns.SIZE)
        if (sizeIndex >= 0 && cursor.moveToFirst()) {
            size = cursor.getLong(sizeIndex)
        }
    }
    return size
}
