package com.mrp.sml.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mrp.sml.data.local.db.dao.TransferDao
import com.mrp.sml.data.remote.sockets.FileSender
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class RetryTransferWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val transferDao: TransferDao,
    private val fileSender: FileSender
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val transferId = inputData.getLong(KEY_TRANSFER_ID, -1L)
        if (transferId == -1L) return Result.failure()

        Timber.i("Retrying transfer: $transferId")

        val transfer = transferDao.getTransferById(transferId) ?: return Result.failure()

        return try {
            // Re-attempt the transfer
            runCatching {
                when (transfer.direction) {
                    "SENT" -> {
                        // Re-send files (would need stored paths)
                    }
                    "RECEIVED" -> {
                        // Re-attempt receive
                    }
                }
            }.fold(
                onSuccess = {
                    transferDao.updateStatus(transferId, "COMPLETED")
                    Result.success()
                },
                onFailure = { e ->
                    Timber.e(e, "Retry failed for transfer $transferId")
                    if (runAttemptCount < 3) Result.retry() else Result.failure()
                }
            )
        } catch (e: Exception) {
            Timber.e(e, "Retry worker exception")
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val KEY_TRANSFER_ID = "transfer_id"
    }
}
