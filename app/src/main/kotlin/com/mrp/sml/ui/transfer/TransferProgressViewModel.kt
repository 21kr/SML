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

    fun setDirection(direction: TransferDirection) {
        _uiState.update { current ->
            current.copy(direction = direction)
        }
    }

    init {
        observeTransferProgress()
        observeTransferStatus()
    }

    private fun observeTransferProgress() {
        viewModelScope.launch {
            fileTransferRepository.observeTransferProgress().collect { progress ->
                _uiState.update { current ->
                    current.copy(
                        progressPercent = progress.progressPercent,
                        progressPercentText = String.format(Locale.US, "%.1f%%", progress.progressPercent),
                        speedText = "%.2f MB/s".format(progress.speedBytesPerSecond / 1_000_000.0),
                    )
                }
            }
        }
    }

    private fun observeTransferStatus() {
        viewModelScope.launch {
            fileTransferRepository.observeTransferStatus().collect { statusUpdate ->
                _uiState.update { current ->
                    current.copy(
                        status = statusUpdate.status.name,
                        userMessage = statusUpdate.userMessage,
                    )
                }
            }
        }
    }
}

data class TransferProgressUiState(
    val progressPercent: Float = 0f,
    val progressPercentText: String = "0.0%",
    val speedText: String = "0.00 MB/s",
    val direction: TransferDirection = TransferDirection.SENDING,
    val status: String = "IDLE",
    val userMessage: String? = null,
)

enum class TransferDirection {
    SENDING,
    RECEIVING
}
