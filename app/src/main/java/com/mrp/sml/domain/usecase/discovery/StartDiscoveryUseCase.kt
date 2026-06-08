package com.mrp.sml.domain.usecase.discovery

import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartDiscoveryUseCase @Inject constructor(
    private val connectionRepository: ConnectionRepository
) {
    suspend operator fun invoke() {
        connectionRepository.startDiscovery()
    }

    fun observeConnectionState(): Flow<ConnectionState> {
        return connectionRepository.observeConnectionState()
    }

    fun observeDiscoveredDevices(): Flow<com.mrp.sml.core.models.Device> {
        return connectionRepository.observeDiscoveredDevices()
    }
}
