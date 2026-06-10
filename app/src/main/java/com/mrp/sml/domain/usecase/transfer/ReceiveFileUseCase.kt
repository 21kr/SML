package com.mrp.sml.domain.usecase.transfer

import com.mrp.sml.domain.repository.TransferRepository
import javax.inject.Inject

class ReceiveFileUseCase @Inject constructor(
    private val transferRepository: TransferRepository
) {
    operator fun invoke(outputDirectoryPath: String, sessionToken: String, senderIp: String = "") {
        require(outputDirectoryPath.isNotBlank()) { "Output directory is required" }
        transferRepository.receiveFiles(outputDirectoryPath.trim(), sessionToken.trim(), senderIp.trim())
    }
}
