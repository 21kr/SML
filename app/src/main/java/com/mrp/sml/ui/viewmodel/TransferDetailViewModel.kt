package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.domain.repository.TransferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TransferDetailUiState(
    val transfer: TransferModel? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class TransferDetailViewModel @Inject constructor(
    private val transferRepository: TransferRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransferDetailUiState())
    val uiState: StateFlow<TransferDetailUiState> = _uiState.asStateFlow()

    fun loadTransfer(transferId: String) {
        viewModelScope.launch {
            transferRepository.observeTransfers().collect { transfers ->
                val transfer = transfers.find { it.id == transferId }
                _uiState.update {
                    it.copy(transfer = transfer, isLoading = false)
                }
                if (transfer != null) return@collect
            }
        }
    }

    fun retryTransfer(sessionId: String) {
        viewModelScope.launch {
            transferRepository.retryTransfer(sessionId)
        }
    }
}
