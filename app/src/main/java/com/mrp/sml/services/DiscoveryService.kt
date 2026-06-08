package com.mrp.sml.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DiscoveryService : Service() {

    @Inject
    lateinit var discoveryManager: DeviceDiscoveryManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i("DiscoveryService started")

        when (intent?.action) {
            ACTION_START -> {
                kotlinx.coroutines.MainScope().launch {
                    discoveryManager.startDiscovery()
                }
            }
            ACTION_STOP -> {
                kotlinx.coroutines.MainScope().launch {
                    discoveryManager.stopDiscovery()
                }
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        discoveryManager.cleanup()
        super.onDestroy()
    }

    companion object {
        const val ACTION_START = "com.mrp.sml.action.START_DISCOVERY"
        const val ACTION_STOP = "com.mrp.sml.action.STOP_DISCOVERY"
    }
}
