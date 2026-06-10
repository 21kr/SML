package com.mrp.sml.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrp.sml.core.constants.TransferConstants
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.core.models.TransferFile
import com.mrp.sml.core.utils.QrCodeUtils
import com.mrp.sml.core.utils.WifiUtils
import com.mrp.sml.data.remote.hotspot.HotspotManager
import com.mrp.sml.domain.repository.ConnectionRepository
import com.mrp.sml.domain.repository.TransferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ReceiveUiState(
    val connectionState: ConnectionState = ConnectionState.IDLE,
    val discoveredDevices: List<Device> = emptyList(),
    val isScanning: Boolean = false,
    val incomingRequest: IncomingTransferRequest? = null,
    val qrPayload: String? = null,
    val qrBitmap: Bitmap? = null,
    val usingHotspot: Boolean = false,
    val hotspotSsid: String = "",
    val hotspotPassword: String = "",
    val errorMessage: String? = null
)

data class IncomingTransferRequest(
    val deviceName: String,
    val files: List<TransferFile>,
    val sessionId: String
)

@HiltViewModel
class ReceiveViewModel @Inject constructor(
    private val connectionRepository: ConnectionRepository,
    private val transferRepository: TransferRepository,
    private val hotspotManager: HotspotManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReceiveUiState())
    val uiState: StateFlow<ReceiveUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            connectionRepository.observeConnectionState().collect { state ->
                _uiState.update { it.copy(connectionState = state) }
            }
        }
        viewModelScope.launch {
            connectionRepository.observeDiscoveredDevices().collect { device ->
                _uiState.update { state ->
                    val current = state.discoveredDevices.toMutableList()
                    val existing = current.indexOfFirst { it.id == device.id }
                    if (existing >= 0) current[existing] = device else current.add(device)
                    state.copy(discoveredDevices = current)
                }
            }
        }
    }

    fun startListening() {
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true, errorMessage = null) }
            connectionRepository.startDiscovery()
            generateReceiverQrCode(useHotspot = false)
        }
    }

    fun startHotspotAndListen() {
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true, errorMessage = null, usingHotspot = true) }

            val result = hotspotManager.startHotspot()
            result.onSuccess { info ->
                _uiState.update {
                    it.copy(
                        hotspotSsid = info.ssid,
                        hotspotPassword = info.password
                    )
                }
                generateReceiverQrCode(useHotspot = true)
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        errorMessage = "Failed to start hotspot: ${e.message}",
                        usingHotspot = false
                    )
                }
            }
        }
    }

    fun stopListening() {
        viewModelScope.launch {
            connectionRepository.stopDiscovery()
            hotspotManager.stopHotspot()
            _uiState.update { it.copy(isScanning = false, usingHotspot = false) }
        }
    }

    fun connectToDevice(deviceId: String) {
        viewModelScope.launch {
            connectionRepository.connectToDevice(deviceId)
        }
    }

    private fun generateReceiverQrCode(useHotspot: Boolean) {
        viewModelScope.launch {
            val ip = connectionRepository.getLocalIpAddress() ?: "192.168.43.1"
            val sessionToken = UUID.randomUUID().toString()
            val ssid = if (useHotspot) _uiState.value.hotspotSsid else ""
            val password = if (useHotspot) _uiState.value.hotspotPassword else ""

            val payload = QrCodeUtils.buildQrPayload(
                deviceName = android.os.Build.MODEL,
                ipAddress = ip,
                port = TransferConstants.TRANSFER_PORT,
                sessionToken = sessionToken,
                role = "receiver",
                ssid = ssid,
                password = password
            )
            val bitmap = QrCodeUtils.generateQrCode(payload)
            _uiState.update { it.copy(qrPayload = payload, qrBitmap = bitmap) }

            val outputDir = android.os.Environment.getExternalStorageDirectory().absolutePath + "/SML/received"
            transferRepository.listenForFiles(outputDir, sessionToken)
        }
    }

    fun acceptTransfer(sessionId: String) {
        _uiState.update { it.copy(incomingRequest = null) }
    }

    fun rejectTransfer() {
        _uiState.update { it.copy(incomingRequest = null) }
    }

    fun setIncomingRequest(request: IncomingTransferRequest) {
        _uiState.update { it.copy(incomingRequest = request) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            connectionRepository.stopDiscovery()
            hotspotManager.stopHotspot()
        }
    }
}
