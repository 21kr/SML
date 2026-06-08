package com.mrp.sml.domain.usecase.transfer

import com.mrp.sml.domain.repository.TransferRepository
import javax.inject.Inject

class CancelTransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository
) {
    operator fun invoke() {
        transferRepository.cancelTransfer()
    }
}
