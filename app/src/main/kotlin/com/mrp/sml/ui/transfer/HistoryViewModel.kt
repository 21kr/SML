package com.mrp.sml.ui.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.domain.model.TransferRecord
import com.mrp.sml.domain.repository.TransferHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transferHistoryRepository: TransferHistoryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransferHistoryUiState())
    val uiState: StateFlow<TransferHistoryUiState> = _uiState.asStateFlow()

    init {
        observeTransferHistory()
    }

    private fun observeTransferHistory() {
        viewModelScope.launch {
            transferHistoryRepository.observeTransferHistory().collect { history ->
                _uiState.update {
                    TransferHistoryUiState(records = history.map { it.toUiModel() })
                }
            }
        }
    }
}

data class TransferHistoryUiState(
    val records: List<TransferHistoryItemUiModel> = emptyList(),
)

data class TransferHistoryItemUiModel(
    val id: Long,
    val fileName: String,
    val direction: String,
    val status: String,
    val sizeText: String,
    val dateText: String,
)

private fun TransferRecord.toUiModel(): TransferHistoryItemUiModel {
    return TransferHistoryItemUiModel(
        id = id,
        fileName = fileName,
        direction = direction.name,
        status = status.name,
        sizeText = formatBytes(fileSizeBytes),
        dateText = formatTimestamp(timestampEpochMillis),
    )
}

private fun formatBytes(sizeBytes: Long): String {
    if (sizeBytes <= 0L) return "0 B"

    val units = listOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (kotlin.math.log10(sizeBytes.toDouble()) / kotlin.math.log10(1024.0)).toInt()
    val scaled = sizeBytes / Math.pow(1024.0, digitGroups.toDouble())
    return String.format(Locale.US, "%.2f %s", scaled, units[digitGroups])
}

private fun formatTimestamp(timestampEpochMillis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
    return formatter.format(Date(timestampEpochMillis))
}
