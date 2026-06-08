package com.mrp.sml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.domain.repository.ConnectionRepository
import com.mrp.sml.domain.repository.TransferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val permissionStatus: Boolean = false,
    val wifiStatus: String = "Not connected",
    val deviceName: String = "",
    val lastTransferSummary: String = ""
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val connectionRepository: ConnectionRepository,
    private val transferRepository: TransferRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            transferRepository.observeTransfers().collect { transfers ->
                val last = transfers.sortedByDescending { it.completedAt ?: it.startedAt }.firstOrNull()
                _uiState.update {
                    it.copy(
                        lastTransferSummary = if (last != null)
                            "${if (last.direction == TransferModel.TransferDirection.SENT) "Sent" else "Received"} ${last.fileName}"
                        else ""
                    )
                }
            }
        }
    }

    fun updateDeviceName(name: String) {
        _uiState.update { it.copy(deviceName = name) }
    }

    fun updateWifiStatus(status: String) {
        _uiState.update { it.copy(wifiStatus = status) }
    }

    fun updatePermissionStatus(granted: Boolean) {
        _uiState.update { it.copy(permissionStatus = granted) }
    }
}
