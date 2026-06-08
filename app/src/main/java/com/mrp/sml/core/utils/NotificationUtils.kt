package com.mrp.sml.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mrp.sml.MainActivity
import com.mrp.sml.R
import com.mrp.sml.core.constants.AppConstants

object NotificationUtils {

    fun createTransferChannel(context: Context) {
        val channel = NotificationChannel(
            AppConstants.NOTIFICATION_CHANNEL_TRANSFER,
            "File Transfers",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Shows active file transfer progress"
            setShowBadge(false)
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    fun createDiscoveryChannel(context: Context) {
        val channel = NotificationChannel(
            AppConstants.NOTIFICATION_CHANNEL_DISCOVERY,
            "Device Discovery",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Shows device discovery status"
            setShowBadge(false)
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    fun createTransferNotification(
        context: Context,
        fileName: String,
        progress: Int,
        isOngoing: Boolean = true
    ): NotificationCompat.Builder {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, AppConstants.NOTIFICATION_CHANNEL_TRANSFER)
            .setSmallIcon(android.R.drawable.stat_sys_upload)
            .setContentTitle("Transferring file")
            .setContentText(fileName)
            .setContentIntent(pendingIntent)
            .setOngoing(isOngoing)
            .setProgress(100, progress, progress == 0)
            .setPriority(NotificationCompat.PRIORITY_LOW)
    }
}
