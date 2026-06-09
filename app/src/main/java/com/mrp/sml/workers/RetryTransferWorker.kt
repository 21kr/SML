package com.mrp.sml.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mrp.sml.data.local.db.dao.TransferDao
import com.mrp.sml.data.remote.sockets.FileReceiver
import com.mrp.sml.data.remote.sockets.FileSender
import com.mrp.sml.data.remote.sockets.SocketTransferManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

@HiltWorker
class RetryTransferWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val transferDao: TransferDao,
    private val fileSender: FileSender,
    private val fileReceiver: FileReceiver,
    private val socketTransferManager: SocketTransferManager
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val transferId = inputData.getString(KEY_TRANSFER_ID)
        if (transferId.isNullOrBlank()) return Result.failure()

        Timber.i("Retrying transfer: $transferId")

        val transfer = transferDao.getTransferById(transferId) ?: return Result.failure()

        return try {
            socketTransferManager.reset()
            socketTransferManager.setSessionToken(transfer.sessionToken)

            when (transfer.direction) {
                "SENT" -> {
                    Result.success()
                }
                "RECEIVED" -> {
                    val dir = File(applicationContext.cacheDir, "sml_received")
                    if (!dir.exists()) dir.mkdirs()
                    val result = fileReceiver.receiveFiles(dir, sessionToken = transfer.sessionToken)
                    result.fold(
                        onSuccess = {
                            transferDao.updateStatus(transferId, "COMPLETED")
                            Result.success()
                        },
                        onFailure = { e ->
                            Timber.e(e, "Retry failed for transfer $transferId")
                            if (runAttemptCount < 3) Result.retry() else Result.failure()
                        }
                    )
                }
                else -> Result.failure()
            }
        } catch (e: Exception) {
            Timber.e(e, "Retry worker exception")
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val KEY_TRANSFER_ID = "transfer_id"

        fun scheduleRetry(context: Context, transferId: String, delayMs: Long = 5000L) {
            val inputData = Data.Builder()
                .putString(KEY_TRANSFER_ID, transferId)
                .build()

            val request = OneTimeWorkRequestBuilder<RetryTransferWorker>()
                .setInputData(inputData)
                .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
                .setBackoffCriteria(
                    androidx.work.BackoffPolicy.EXPONENTIAL,
                    10, TimeUnit.SECONDS
                )
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()

            WorkManager.getInstance(context).enqueue(request)
        }
    }
}
