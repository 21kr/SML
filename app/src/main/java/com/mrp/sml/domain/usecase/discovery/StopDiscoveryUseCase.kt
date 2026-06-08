package com.mrp.sml.domain.usecase.discovery

import com.mrp.sml.domain.repository.ConnectionRepository
import javax.inject.Inject

class StopDiscoveryUseCase @Inject constructor(
    private val connectionRepository: ConnectionRepository
) {
    suspend operator fun invoke() {
        connectionRepository.stopDiscovery()
    }
}
