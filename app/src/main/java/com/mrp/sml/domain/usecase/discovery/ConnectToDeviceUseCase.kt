package com.mrp.sml.domain.usecase.discovery

import com.mrp.sml.domain.repository.ConnectionRepository
import javax.inject.Inject

class ConnectToDeviceUseCase @Inject constructor(
    private val connectionRepository: ConnectionRepository
) {
    suspend operator fun invoke(deviceId: String) {
        require(deviceId.isNotBlank()) { "Device ID must not be blank" }
        connectionRepository.connectToDevice(deviceId.trim())
    }
}
