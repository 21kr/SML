package com.mrp.sml.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.core.models.TransferProgress
import com.mrp.sml.core.models.TransferStatus
import com.mrp.sml.data.remote.sockets.SocketTransferManager
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.domain.repository.TransferRepository
import com.mrp.sml.services.TransferForegroundService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TransferUiState(
    val sessionId: String = "",
    val direction: String = "",
    val peerDevice: String = "",
    val currentFileName: String = "",
    val currentFileIndex: Int = 0,
    val totalFiles: Int = 0,
    val transferredBytes: Long = 0L,
    val totalBytes: Long = 0L,
    val progressPercent: Float = 0f,
    val speed: Double = 0.0,
    val eta: Long = 0L,
    val status: TransferStatus = TransferStatus.PENDING,
    val errorMessage: String? = null,
    val retryAttempt: Int = 0,
    val canPause: Boolean = false,
    val canResume: Boolean = false
)

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val transferRepository: TransferRepository,
    private val socketTransferManager: SocketTransferManager,
    @ApplicationContext private val context: android.content.Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransferUiState())
    val uiState: StateFlow<TransferUiState> = _uiState.asStateFlow()

    private val _transferHistory = MutableStateFlow<List<TransferModel>>(emptyList())
    val transferHistory: StateFlow<List<TransferModel>> = _transferHistory.asStateFlow()

    init {
        viewModelScope.launch {
            transferRepository.observeTransfers().collect { transfers ->
                _transferHistory.value = transfers
            }
        }
        viewModelScope.launch {
            socketTransferManager.progress.collect { progress ->
                updateFromProgress(progress)
                val fileName = progress.currentFileName.ifBlank { "Transferring..." }
                TransferForegroundService.updateProgress(context, fileName, progress.progressPercent.toInt())
            }
        }
        viewModelScope.launch {
            socketTransferManager.state.collect { state ->
                _uiState.update {
                    when (state) {
                        com.mrp.sml.data.remote.sockets.TransferState.PAUSED -> it.copy(
                            status = TransferStatus.PAUSED, canPause = false, canResume = true
                        )
                        com.mrp.sml.data.remote.sockets.TransferState.CANCELLED -> it.copy(
                            status = TransferStatus.CANCELLED, canPause = false, canResume = false
                        )
                        com.mrp.sml.data.remote.sockets.TransferState.TRANSFERRING -> it.copy(
                            status = TransferStatus.TRANSFERRING, canPause = true, canResume = false
                        )
                        com.mrp.sml.data.remote.sockets.TransferState.COMPLETED -> it.copy(
                            status = TransferStatus.COMPLETED, canPause = false, canResume = false
                        )
                        else -> it
                    }
                }
            }
        }
    }

    fun sendFiles(filePaths: List<String>, destinationAddress: String, sessionToken: String) {
        val fileName = filePaths.firstOrNull()?.substringAfterLast('/') ?: "Unknown"
        _uiState.update {
            it.copy(
                status = TransferStatus.TRANSFERRING,
                sessionId = sessionToken,
                direction = "SENT",
                peerDevice = destinationAddress,
                currentFileName = fileName,
                totalFiles = filePaths.size,
                canPause = true,
                canResume = false
            )
        }

        TransferForegroundService.start(context, fileName)

        viewModelScope.launch {
            transferRepository.sendFiles(filePaths, destinationAddress, sessionToken)
            TransferForegroundService.stop(context)
        }
    }

    fun receiveFiles(outputDirectoryPath: String, sessionToken: String, senderIp: String = "") {
        _uiState.update {
            it.copy(
                status = TransferStatus.TRANSFERRING,
                sessionId = sessionToken,
                direction = "RECEIVED",
                canPause = true,
                canResume = false
            )
        }

        TransferForegroundService.start(context, "Receiving...")

        viewModelScope.launch {
            transferRepository.receiveFiles(outputDirectoryPath, sessionToken, senderIp)
            TransferForegroundService.stop(context)
        }
    }

    fun listenForFiles(outputDirectoryPath: String, sessionToken: String) {
        _uiState.update {
            it.copy(
                status = TransferStatus.TRANSFERRING,
                sessionId = sessionToken,
                direction = "RECEIVED",
                canPause = true,
                canResume = false
            )
        }

        TransferForegroundService.start(context, "Waiting for sender...")

        viewModelScope.launch {
            transferRepository.listenForFiles(outputDirectoryPath, sessionToken)
            TransferForegroundService.stop(context)
        }
    }

    private fun updateFromProgress(progress: TransferProgress) {
        _uiState.update {
            it.copy(
                transferredBytes = progress.transferredBytes,
                totalBytes = progress.totalBytes,
                progressPercent = progress.progressPercent,
                speed = progress.speedBytesPerSecond,
                eta = progress.etaSeconds,
                currentFileName = progress.currentFileName.ifBlank { it.currentFileName },
                currentFileIndex = progress.currentFileIndex,
                totalFiles = progress.totalFiles
            )
        }
    }

    fun pauseTransfer() {
        transferRepository.pauseTransfer()
    }

    fun resumeTransfer() {
        transferRepository.resumeTransfer()
    }

    fun cancelTransfer() {
        transferRepository.cancelTransfer()
        _uiState.update { it.copy(status = TransferStatus.CANCELLED, canPause = false, canResume = false) }
    }

    fun retryTransfer(sessionId: String) {
        viewModelScope.launch {
            val attempt = _uiState.value.retryAttempt + 1
            _uiState.update { it.copy(status = TransferStatus.PENDING, errorMessage = null, retryAttempt = attempt) }
            transferRepository.retryTransfer(sessionId)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            transferRepository.clearHistory()
        }
    }

    override fun onCleared() {
        super.onCleared()
        transferRepository.cancelTransfer()
        TransferForegroundService.stop(context)
    }
}
