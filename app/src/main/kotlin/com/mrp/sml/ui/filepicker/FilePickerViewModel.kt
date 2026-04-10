package com.mrp.sml.ui.filepicker

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FilePickerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(FilePickerUiState())
    val uiState: StateFlow<FilePickerUiState> = _uiState.asStateFlow()

    fun onFilesSelected(context: Context, uris: List<Uri>) {
        val selectedFiles = uris.distinct().map { uri ->
            mapUriToFileInfo(context = context, uri = uri)
        }

        _uiState.value = FilePickerUiState(selectedFiles = selectedFiles)
    }

    private fun mapUriToFileInfo(context: Context, uri: Uri): SelectedFileUiModel {
        val contentResolver = context.contentResolver
        var name = "Unknown file"
        var size = 0L

        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameColumn = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndex(OpenableColumns.SIZE)

            if (cursor.moveToFirst()) {
                if (nameColumn >= 0) {
                    name = cursor.getString(nameColumn) ?: name
                }
                if (sizeColumn >= 0) {
                    size = cursor.getLong(sizeColumn)
                }
            }
        }

        val mimeType = contentResolver.getType(uri) ?: "Unknown"
        return SelectedFileUiModel(
            uri = uri.toString(),
            name = name,
            sizeBytes = size,
            readableSize = size.toReadableFileSize(),
            type = mimeType,
        )
    }
}

data class FilePickerUiState(
    val selectedFiles: List<SelectedFileUiModel> = emptyList(),
)

data class SelectedFileUiModel(
    val uri: String,
    val name: String,
    val sizeBytes: Long,
    val readableSize: String,
    val type: String,
)

private fun Long.toReadableFileSize(): String {
    if (this <= 0L) {
        return "0 B"
    }

    val units = listOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (kotlin.math.log10(this.toDouble()) / kotlin.math.log10(1024.0)).toInt()
    val size = this / Math.pow(1024.0, digitGroups.toDouble())
    return String.format(Locale.US, "%.2f %s", size, units[digitGroups])
}
