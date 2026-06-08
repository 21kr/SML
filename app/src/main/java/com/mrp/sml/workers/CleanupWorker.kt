package com.mrp.sml.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mrp.sml.data.local.db.dao.TransferDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class CleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val transferDao: TransferDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.i("CleanupWorker: removing old transfer records")

        return try {
            val transfers = transferDao.getTransferHistory().let { flow ->
                var list: List<com.mrp.sml.data.local.db.entities.TransferEntity> = emptyList()
                flow.collect { list = it; return@collect }
                list
            }

            val cutoffTime = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000) // 30 days
            transfers
                .filter { it.completedAtMillis != null && it.completedAtMillis!! < cutoffTime }
                .forEach { transferDao.delete(it.id) }

            Timber.i("CleanupWorker completed")
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "CleanupWorker failed")
            Result.failure()
        }
    }

    companion object {
        const val UNIQUE_WORK_NAME = "cleanup_old_transfers"
    }
}
