package com.mrp.sml.services

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.mrp.sml.MainActivity
import com.mrp.sml.R
import com.mrp.sml.core.constants.AppConstants
import com.mrp.sml.core.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TransferForegroundService : Service() {

    @Inject
    lateinit var notificationUtils: NotificationUtils

    private var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate() {
        super.onCreate()
        NotificationUtils.createTransferChannel(this)
        acquireWakeLock()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification("Preparing transfer...", 0, true)
        startForeground(AppConstants.NOTIFICATION_ID_TRANSFER, notification)

        intent?.let {
            when (it.action) {
                ACTION_START_TRANSFER -> handleStartTransfer(it)
                ACTION_UPDATE_PROGRESS -> handleUpdateProgress(it)
                ACTION_STOP_TRANSFER -> handleStopTransfer()
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        releaseWakeLock()
        super.onDestroy()
    }

    private fun handleStartTransfer(intent: Intent) {
        val fileName = intent.getStringExtra(EXTRA_FILE_NAME) ?: "Unknown"
        Timber.i("Transfer started: $fileName")
        updateNotification(fileName, 0)
    }

    private fun handleUpdateProgress(intent: Intent) {
        val fileName = intent.getStringExtra(EXTRA_FILE_NAME) ?: "Transferring..."
        val progress = intent.getIntExtra(EXTRA_PROGRESS, 0)
        updateNotification(fileName, progress)
    }

    private fun handleStopTransfer() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotification(text: String, progress: Int, isOngoing: Boolean) =
        NotificationCompat.Builder(this, AppConstants.NOTIFICATION_CHANNEL_TRANSFER)
            .setSmallIcon(android.R.drawable.stat_sys_upload_done)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(text)
            .setContentIntent(
                PendingIntent.getActivity(
                    this, 0,
                    Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setOngoing(isOngoing)
            .setProgress(100, progress, progress == 0)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

    private fun updateNotification(text: String, progress: Int) {
        val notification = createNotification(text, progress, true)
        val manager = getSystemService(android.app.NotificationManager::class.java)
        manager.notify(AppConstants.NOTIFICATION_ID_TRANSFER, notification)
    }

    private fun acquireWakeLock() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "SML:TransferWakeLock"
        ).apply {
            acquire(10 * 60 * 1000L)
        }
    }

    private fun releaseWakeLock() {
        wakeLock?.let {
            if (it.isHeld) it.release()
        }
        wakeLock = null
    }

    companion object {
        const val ACTION_START_TRANSFER = "com.mrp.sml.action.START_TRANSFER"
        const val ACTION_UPDATE_PROGRESS = "com.mrp.sml.action.UPDATE_PROGRESS"
        const val ACTION_STOP_TRANSFER = "com.mrp.sml.action.STOP_TRANSFER"
        const val EXTRA_FILE_NAME = "extra_file_name"
        const val EXTRA_PROGRESS = "extra_progress"

        fun start(context: Context, fileName: String) {
            val intent = Intent(context, TransferForegroundService::class.java).apply {
                action = ACTION_START_TRANSFER
                putExtra(EXTRA_FILE_NAME, fileName)
            }
            context.startForegroundService(intent)
        }

        fun updateProgress(context: Context, fileName: String, progress: Int) {
            val intent = Intent(context, TransferForegroundService::class.java).apply {
                action = ACTION_UPDATE_PROGRESS
                putExtra(EXTRA_FILE_NAME, fileName)
                putExtra(EXTRA_PROGRESS, progress)
            }
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, TransferForegroundService::class.java).apply {
                action = ACTION_STOP_TRANSFER
            }
            context.startService(intent)
        }
    }
}
