package com.mrp.sml.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Timber.i("Device booted - SML ready")
            // Schedule periodic cleanup
            androidx.work.WorkManager.getInstance(context).let { workManager ->
                val request = androidx.work.OneTimeWorkRequestBuilder<com.mrp.sml.workers.CleanupWorker>()
                    .build()
                workManager.enqueue(request)
            }
        }
    }
}
