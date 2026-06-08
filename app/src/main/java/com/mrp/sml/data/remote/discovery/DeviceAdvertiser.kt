package com.mrp.sml.data.remote.discovery

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager
import com.mrp.sml.core.constants.NetworkConstants
import com.mrp.sml.core.utils.DeviceUtils
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceAdvertiser @Inject constructor(
    private val context: Context
) {
    private val wifiP2pManager: WifiP2pManager? by lazy {
        context.getSystemService(Context.WIFI_P2P_SERVICE) as? WifiP2pManager
    }
    private val channel: WifiP2pManager.Channel? by lazy {
        wifiP2pManager?.initialize(context, context.mainLooper, null)
    }

    fun createGroup(): Result<Unit> {
        return try {
            channel?.let { ch ->
                wifiP2pManager?.createGroup(ch, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() {
                        Timber.i("WiFi Direct group created")
                    }

                    override fun onFailure(reason: Int) {
                        Timber.w("Failed to create group: $reason")
                    }
                })
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun removeGroup() {
        try {
            channel?.let { ch ->
                wifiP2pManager?.removeGroup(ch, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() = Timber.i("Group removed")
                    override fun onFailure(reason: Int) = Timber.w("Failed to remove group: $reason")
                })
            }
        } catch (e: Exception) {
            Timber.e(e, "Error removing group")
        }
    }
}
