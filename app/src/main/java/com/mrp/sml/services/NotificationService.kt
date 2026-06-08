package com.mrp.sml.services

import android.app.NotificationManager
import android.content.Context
import com.mrp.sml.core.constants.AppConstants
import com.mrp.sml.core.utils.NotificationUtils

class NotificationService {

    companion object {
        fun initializeChannels(context: Context) {
            NotificationUtils.createTransferChannel(context)
            NotificationUtils.createDiscoveryChannel(context)
        }

        fun clearTransferNotification(context: Context) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancel(AppConstants.NOTIFICATION_ID_TRANSFER)
        }

        fun clearAllNotifications(context: Context) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancelAll()
        }
    }
}
