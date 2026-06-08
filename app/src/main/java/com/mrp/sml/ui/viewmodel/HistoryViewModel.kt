package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.domain.repository.TransferRepository
import com.mrp.sml.ui.screens.history.HistoryFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryUiState(
    val allTransfers: List<TransferModel> = emptyList(),
    val filter: HistoryFilter = HistoryFilter.ALL,
    val filteredTransfers: List<TransferModel> = emptyList()
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transferRepository: TransferRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            transferRepository.observeTransfers().collect { transfers ->
                _uiState.update { state ->
                    state.copy(
                        allTransfers = transfers,
                        filteredTransfers = applyFilter(transfers, state.filter)
                    )
                }
            }
        }
    }

    fun setFilter(filter: HistoryFilter) {
        _uiState.update { state ->
            state.copy(
                filter = filter,
                filteredTransfers = applyFilter(state.allTransfers, filter)
            )
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            transferRepository.clearHistory()
        }
    }

    fun retryTransfer(sessionId: String) {
        viewModelScope.launch {
            transferRepository.retryTransfer(sessionId)
        }
    }

    private fun applyFilter(transfers: List<TransferModel>, filter: HistoryFilter): List<TransferModel> {
        return when (filter) {
            HistoryFilter.ALL -> transfers
            HistoryFilter.SENT -> transfers.filter { it.direction == TransferModel.TransferDirection.SENT }
            HistoryFilter.RECEIVED -> transfers.filter { it.direction == TransferModel.TransferDirection.RECEIVED }
            HistoryFilter.FAILED -> transfers.filter { it.status == TransferModel.TransferStatus.FAILED || it.status == TransferModel.TransferStatus.CANCELLED }
        }
    }
}
