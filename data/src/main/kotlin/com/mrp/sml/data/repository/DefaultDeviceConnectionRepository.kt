package com.mrp.sml.data.repository

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pManager
import com.mrp.sml.core.common.DispatchersProvider
import com.mrp.sml.domain.repository.ConnectionState
import com.mrp.sml.domain.repository.DeviceConnectionRepository
import com.mrp.sml.domain.repository.DiscoveredDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class DefaultDeviceConnectionRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatchersProvider: DispatchersProvider,
) : DeviceConnectionRepository {
    private val connectionState = MutableStateFlow(ConnectionState.IDLE)
    private val discoveredDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())

    private val wifiP2pManager: WifiP2pManager? = context.getSystemService(WifiP2pManager::class.java)
    private val channel: WifiP2pManager.Channel? = wifiP2pManager?.initialize(context, context.mainLooper, null)

    private val receiver = WifiDirectBroadcastReceiver(
        onWifiP2pStateChanged = { enabled ->
            if (!enabled) {
                connectionState.value = ConnectionState.FAILED
            }
        },
        onPeersChanged = { requestPeers() },
        onConnectionChanged = { requestConnectionInfo() },
    )

    init {
        context.registerReceiver(receiver, buildIntentFilter())
    }

    override fun observeConnectionState(): Flow<ConnectionState> = connectionState.asStateFlow()

    override fun observeDiscoveredDevices(): Flow<List<DiscoveredDevice>> = discoveredDevices.asStateFlow()

    override suspend fun discoverDevices() {
        connectionState.value = ConnectionState.DISCOVERING
        val manager = wifiP2pManager
        val p2pChannel = channel

        if (manager == null || p2pChannel == null) {
            connectionState.value = ConnectionState.FAILED
            return
        }

        val success = withContext(dispatchersProvider.main) {
            suspendCancellableCoroutine { continuation ->
                manager.discoverPeers(
                    p2pChannel,
                    object : WifiP2pManager.ActionListener {
                        override fun onSuccess() {
                            continuation.resume(true)
                        }

                        override fun onFailure(reason: Int) {
                            continuation.resume(false)
                        }
                    },
                )
            }
        }

        if (!success) {
            connectionState.value = ConnectionState.FAILED
        }
    }

    override suspend fun connectToDevice(deviceId: String) {
        val manager = wifiP2pManager
        val p2pChannel = channel

        if (manager == null || p2pChannel == null || deviceId.isBlank()) {
            connectionState.value = ConnectionState.FAILED
            return
        }

        val config = WifiP2pConfig().apply {
            deviceAddress = deviceId
        }

        val success = withContext(dispatchersProvider.main) {
            suspendCancellableCoroutine { continuation ->
                manager.connect(
                    p2pChannel,
                    config,
                    object : WifiP2pManager.ActionListener {
                        override fun onSuccess() {
                            continuation.resume(true)
                        }

                        override fun onFailure(reason: Int) {
                            continuation.resume(false)
                        }
                    },
                )
            }
        }

        connectionState.value = if (success) {
            ConnectionState.CONNECTED
        } else {
            ConnectionState.FAILED
        }
    }

    override suspend fun disconnect() {
        val manager = wifiP2pManager
        val p2pChannel = channel

        if (manager == null || p2pChannel == null) {
            connectionState.value = ConnectionState.FAILED
            return
        }

        val success = withContext(dispatchersProvider.main) {
            suspendCancellableCoroutine { continuation ->
                manager.removeGroup(
                    p2pChannel,
                    object : WifiP2pManager.ActionListener {
                        override fun onSuccess() {
                            continuation.resume(true)
                        }

                        override fun onFailure(reason: Int) {
                            continuation.resume(false)
                        }
                    },
                )
            }
        }

        connectionState.value = if (success) {
            ConnectionState.DISCONNECTED
        } else {
            ConnectionState.FAILED
        }
    }

    private fun requestPeers() {
        val manager = wifiP2pManager
        val p2pChannel = channel

        if (manager == null || p2pChannel == null) {
            connectionState.value = ConnectionState.FAILED
            return
        }

        manager.requestPeers(p2pChannel) { peerList ->
            discoveredDevices.value = peerList.deviceList.map { device ->
                DiscoveredDevice(
                    id = device.deviceAddress,
                    name = device.deviceName ?: device.deviceAddress,
                )
            }
        }
    }

    private fun requestConnectionInfo() {
        val manager = wifiP2pManager
        val p2pChannel = channel

        if (manager == null || p2pChannel == null) {
            connectionState.value = ConnectionState.FAILED
            return
        }

        manager.requestConnectionInfo(p2pChannel) { info ->
            connectionState.value = if (info.groupFormed) {
                ConnectionState.CONNECTED
            } else {
                ConnectionState.DISCONNECTED
            }
        }
    }

    private fun buildIntentFilter(): IntentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
    }
}
