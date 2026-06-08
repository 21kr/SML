package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.core.models.TransferProgress
import com.mrp.sml.core.models.TransferStatus
import com.mrp.sml.data.remote.sockets.FileReceiver
import com.mrp.sml.data.remote.sockets.FileSender
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.domain.repository.TransferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val transferRepository: TransferRepository,
    private val fileSender: FileSender,
    private val fileReceiver: FileReceiver
) : ViewModel() {

    private val _transferStatus = MutableStateFlow(TransferStatus.PENDING)
    val transferStatus: StateFlow<TransferStatus> = _transferStatus.asStateFlow()

    private val _transferProgress = MutableStateFlow(TransferProgress())
    val transferProgress: StateFlow<TransferProgress> = _transferProgress.asStateFlow()

    private val _currentFileName = MutableStateFlow("")
    val currentFileName: StateFlow<String> = _currentFileName.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _transferHistory = MutableStateFlow<List<TransferModel>>(emptyList())
    val transferHistory: StateFlow<List<TransferModel>> = _transferHistory.asStateFlow()

    init {
        viewModelScope.launch {
            transferRepository.observeTransfers().collect { transfers ->
                _transferHistory.value = transfers
            }
        }
    }

    fun sendFiles(filePaths: List<String>, destinationAddress: String, sessionToken: String) {
        _transferStatus.value = TransferStatus.TRANSFERRING
        _currentFileName.value = filePaths.firstOrNull()?.let {
            it.substringAfterLast('/')
        } ?: "Unknown"

        viewModelScope.launch {
            transferRepository.sendFiles(filePaths, destinationAddress, sessionToken)
        }

        viewModelScope.launch {
            fileSender.progress.collect { progress ->
                _transferProgress.value = progress
            }
        }
    }

    fun receiveFiles(outputDirectoryPath: String, sessionToken: String) {
        _transferStatus.value = TransferStatus.TRANSFERRING

        viewModelScope.launch {
            transferRepository.receiveFiles(outputDirectoryPath, sessionToken)
        }

        viewModelScope.launch {
            fileReceiver.progress.collect { progress ->
                _transferProgress.value = progress
            }
        }
    }

    fun cancelTransfer() {
        transferRepository.cancelTransfer()
        _transferStatus.value = TransferStatus.CANCELLED
    }

    fun resumeTransfer() {
        transferRepository.resumeTransfer()
        _transferStatus.value = TransferStatus.RESUMING
    }

    fun clearHistory() {
        viewModelScope.launch {
            transferRepository.clearHistory()
        }
    }

    override fun onCleared() {
        super.onCleared()
        transferRepository.cancelTransfer()
    }
}
