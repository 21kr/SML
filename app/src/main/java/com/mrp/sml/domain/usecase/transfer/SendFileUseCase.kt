package com.mrp.sml.domain.usecase.transfer

import com.mrp.sml.domain.repository.TransferRepository
import javax.inject.Inject

class SendFileUseCase @Inject constructor(
    private val transferRepository: TransferRepository
) {
    operator fun invoke(filePaths: List<String>, destinationAddress: String, sessionToken: String) {
        require(filePaths.isNotEmpty()) { "At least one file is required" }
        require(destinationAddress.isNotBlank()) { "Destination address is required" }
        transferRepository.sendFiles(filePaths, destinationAddress.trim(), sessionToken.trim())
    }
}
