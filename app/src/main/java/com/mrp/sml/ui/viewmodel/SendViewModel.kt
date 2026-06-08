package com.mrp.sml.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.core.models.TransferFile
import com.mrp.sml.core.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SendUiState(
    val selectedFiles: List<TransferFile> = emptyList(),
    val totalSize: Long = 0L,
    val validationErrors: List<String> = emptyList(),
    val canContinue: Boolean = false
)

@HiltViewModel
class SendViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SendUiState())
    val uiState: StateFlow<SendUiState> = _uiState.asStateFlow()

    fun addFiles(uris: List<Uri>) {
        viewModelScope.launch {
            val files = uris.mapNotNull { uri ->
                val name = FileUtils.getFileName(context, uri)
                val size = FileUtils.getFileSize(context, uri)
                if (name.isNotBlank()) {
                    TransferFile(name = name, size = size, uri = uri)
                } else null
            }
            _uiState.update { state ->
                val updated = state.selectedFiles + files
                val total = updated.sumOf { it.size }
                val errors = validateFiles(updated)
                state.copy(
                    selectedFiles = updated,
                    totalSize = total,
                    validationErrors = errors,
                    canContinue = errors.isEmpty() && updated.isNotEmpty()
                )
            }
        }
    }

    fun removeFile(file: TransferFile) {
        _uiState.update { state ->
            val updated = state.selectedFiles - file
            val total = updated.sumOf { it.size }
            val errors = validateFiles(updated)
            state.copy(
                selectedFiles = updated,
                totalSize = total,
                validationErrors = errors,
                canContinue = errors.isEmpty() && updated.isNotEmpty()
            )
        }
    }

    fun clearFiles() {
        _uiState.value = SendUiState()
    }

    fun getFilePathsForDiscovery(): List<String> {
        return _uiState.value.selectedFiles.mapNotNull { it.uri?.toString() }
    }

    private fun validateFiles(files: List<TransferFile>): List<String> {
        val errors = mutableListOf<String>()
        if (files.any { it.size <= 0L }) {
            errors.add("Some files appear to be empty")
        }
        val totalSize = files.sumOf { it.size }
        if (totalSize > 5L * 1024 * 1024 * 1024) {
            errors.add("Total size exceeds 5 GB limit")
        }
        return errors
    }
}
