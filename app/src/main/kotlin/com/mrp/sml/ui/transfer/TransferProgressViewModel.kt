package com.mrp.sml.ui.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.domain.repository.FileTransferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TransferProgressViewModel @Inject constructor(
    private val fileTransferRepository: FileTransferRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransferProgressUiState())
    val uiState: StateFlow<TransferProgressUiState> = _uiState.asStateFlow()

    init {
        observeTransferProgress()
    }

    fun setDirection(direction: TransferDirection) {
        _uiState.update { current ->
            current.copy(
                direction = direction,
                status = current.status.refreshWith(direction = direction, progressPercent = current.progressPercent),
            )
        }
    }

    private fun observeTransferProgress() {
        viewModelScope.launch {
            fileTransferRepository.observeTransferProgress().collect { progress ->
                _uiState.update { current ->
                    val status = determineStatus(
                        progressPercent = progress.progressPercent,
                        direction = current.direction,
                    )
                    current.copy(
                        progressPercent = progress.progressPercent,
                        progressPercentText = String.format(Locale.US, "%.1f%%", progress.progressPercent),
                        speedText = String.format(Locale.US, "%.2f MB/s", progress.speedMegaBytesPerSecond),
                        status = status,
                    )
                }
            }
        }
    }

    private fun determineStatus(progressPercent: Float, direction: TransferDirection): TransferStatus {
        if (progressPercent <= 0f) {
            return TransferStatus.IDLE
        }
        if (progressPercent >= 100f) {
            return TransferStatus.COMPLETED
        }

        return if (direction == TransferDirection.SENDING) {
            TransferStatus.SENDING
        } else {
            TransferStatus.RECEIVING
        }
    }

    private fun TransferStatus.refreshWith(direction: TransferDirection, progressPercent: Float): TransferStatus {
        return determineStatus(progressPercent = progressPercent, direction = direction)
    }
}

data class TransferProgressUiState(
    val progressPercent: Float = 0f,
    val progressPercentText: String = "0.0%",
    val speedText: String = "0.00 MB/s",
    val direction: TransferDirection = TransferDirection.SENDING,
    val status: TransferStatus = TransferStatus.IDLE,
) {
    val statusLabel: String
        get() = status.label
}

enum class TransferDirection {
    SENDING,
    RECEIVING,
}

enum class TransferStatus(val label: String) {
    IDLE("Idle"),
    SENDING("Sending"),
    RECEIVING("Receiving"),
    COMPLETED("Completed"),
}
