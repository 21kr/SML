package com.mrp.sml.domain.usecase

import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.domain.repository.TransferRepository
import com.mrp.sml.domain.usecase.transfer.SendFileUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertThrows
import org.junit.Test

class SendFileUseCaseTest {

    private fun makeRepository(): TransferRepository {
        return object : TransferRepository {
            override fun observeTransfers(): Flow<List<TransferModel>> = flowOf(emptyList())
            override suspend fun getTransferById(id: String) = null
            override suspend fun saveTransfer(transfer: TransferModel) {}
            override suspend fun updateTransferStatus(id: String, status: TransferModel.TransferStatus, error: String?) {}
            override suspend fun updateTransferProgress(id: String, progress: Float, speed: Double) {}
            override suspend fun deleteTransfer(id: String) {}
            override suspend fun clearHistory() {}
            override fun sendFiles(filePaths: List<String>, destinationAddress: String, sessionToken: String) {}
            override fun receiveFiles(outputDirectoryPath: String, sessionToken: String) {}
            override fun cancelTransfer() {}
            override fun pauseTransfer() {}
            override fun resumeTransfer() {}
            override fun retryTransfer(sessionId: String) {}
        }
    }

    @Test
    fun invoke_emptyFileList_throwsException() {
        val useCase = SendFileUseCase(
            transferRepository = makeRepository()
        )
        assertThrows(IllegalArgumentException::class.java) {
            useCase(emptyList(), "192.168.1.1", "token")
        }
    }

    @Test
    fun invoke_blankAddress_throwsException() {
        val useCase = SendFileUseCase(
            transferRepository = makeRepository()
        )
        assertThrows(IllegalArgumentException::class.java) {
            useCase(listOf("file.txt"), "  ", "token")
        }
    }
}
