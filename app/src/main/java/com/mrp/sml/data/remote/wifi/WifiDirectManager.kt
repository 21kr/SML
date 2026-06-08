package com.mrp.sml.data.remote.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.MacAddress
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pGroup
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.core.models.DeviceType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class WifiDirectManager @Inject constructor(
    private val context: Context
) {
    private val manager: WifiP2pManager? by lazy {
        context.getSystemService(Context.WIFI_P2P_SERVICE) as? WifiP2pManager
    }
    private val channel: WifiP2pManager.Channel? by lazy {
        manager?.initialize(context, context.mainLooper, null)
    }

    private val _connectionState = MutableStateFlow(ConnectionState.IDLE)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _discoveredDevices = MutableSharedFlow<Device>(replay = 1)
    val discoveredDevices: SharedFlow<Device> = _discoveredDevices.asSharedFlow()

    private val receiver = WifiP2PReceiver()
    private var isRegistered = false

    fun registerReceiver() {
        if (isRegistered) return
        val filter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }
        context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        isRegistered = true
    }

    fun unregisterReceiver() {
        if (isRegistered) {
            try { context.unregisterReceiver(receiver) } catch (e: Exception) { Timber.e(e) }
            isRegistered = false
        }
    }

    suspend fun discoverPeers(): Result<Unit> = suspendCancellableCoroutine { cont ->
        val mgr = manager ?: run {
            cont.resume(Result.failure(Exception("WiFi P2P not available")))
            return@suspendCancellableCoroutine
        }
        val ch = channel ?: run {
            cont.resume(Result.failure(Exception("WiFi P2P channel not available")))
            return@suspendCancellableCoroutine
        }

        _connectionState.value = ConnectionState.DISCOVERING
        mgr.discoverPeers(ch, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Timber.i("Peer discovery started")
                cont.resume(Result.success(Unit))
            }

            override fun onFailure(reason: Int) {
                Timber.w("Peer discovery failed: $reason")
                _connectionState.value = ConnectionState.FAILED
                cont.resume(Result.failure(Exception("Discovery failed with code: $reason")))
            }
        })
    }

    suspend fun connectToDevice(deviceAddress: String): Result<Unit> = suspendCancellableCoroutine { cont ->
        val mgr = manager ?: run {
            cont.resume(Result.failure(Exception("WiFi P2P not available")))
            return@suspendCancellableCoroutine
        }
        val ch = channel ?: run {
            cont.resume(Result.failure(Exception("WiFi P2P channel not available")))
            return@suspendCancellableCoroutine
        }

        _connectionState.value = ConnectionState.CONNECTING
        val config = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            WifiP2pConfig.Builder()
                .setDeviceAddress(MacAddress.fromString(deviceAddress))
                .build()
        } else {
            WifiP2pConfig().apply {
                this.deviceAddress = deviceAddress
            }
        }
        config.groupOwnerIntent = 15
        mgr.connect(ch, config, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Timber.i("Connection request sent to $deviceAddress")
                cont.resume(Result.success(Unit))
            }

            override fun onFailure(reason: Int) {
                Timber.w("Connection failed: $reason")
                _connectionState.value = ConnectionState.FAILED
                cont.resume(Result.failure(Exception("Connection failed with code: $reason")))
            }
        })
    }

    suspend fun requestPeers(): Result<List<Device>> = suspendCancellableCoroutine { cont ->
        val mgr = manager ?: run {
            cont.resume(Result.success(emptyList()))
            return@suspendCancellableCoroutine
        }
        val ch = channel ?: run {
            cont.resume(Result.success(emptyList()))
            return@suspendCancellableCoroutine
        }

        mgr.requestPeers(ch, object : WifiP2pManager.PeerListListener {
            override fun onPeersAvailable(peerList: WifiP2pDeviceList) {
                val devices = peerList.deviceList.map { it.toDevice() }
                cont.resume(Result.success(devices))
            }
        })
    }

    suspend fun disconnect(): Result<Unit> = suspendCancellableCoroutine { cont ->
        val mgr = manager ?: run {
            cont.resume(Result.success(Unit))
            return@suspendCancellableCoroutine
        }
        val ch = channel ?: run {
            cont.resume(Result.success(Unit))
            return@suspendCancellableCoroutine
        }
        mgr.removeGroup(ch, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                _connectionState.value = ConnectionState.DISCONNECTED
                cont.resume(Result.success(Unit))
            }

            override fun onFailure(reason: Int) {
                _connectionState.value = ConnectionState.DISCONNECTED
                cont.resume(Result.success(Unit))
            }
        })
    }

    private fun WifiP2pDevice.toDevice(): Device {
        return Device(
            id = deviceAddress,
            name = deviceName.ifBlank { "Unknown Device" },
            deviceType = when (primaryDeviceType) {
                "10-0050F204-5" -> DeviceType.PHONE
                "10-0050F204-1" -> DeviceType.LAPTOP
                "10-0050F204-2" -> DeviceType.TABLET
                else -> DeviceType.UNKNOWN
            }
        )
    }

    private inner class WifiP2PReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                    val enabled = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1) == WifiP2pManager.WIFI_P2P_STATE_ENABLED
                    Timber.i("WiFi P2P state changed: enabled=$enabled")
                }

                WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                    Timber.i("WiFi P2P peers changed")
                    channel?.let { ch ->
                        manager?.requestPeers(ch) { peers ->
                            peers.deviceList.forEach { device ->
                                _discoveredDevices.tryEmit(device.toDevice())
                            }
                        }
                    }
                }

                WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                    val group = intent.getParcelableExtra<WifiP2pGroup>(WifiP2pManager.EXTRA_WIFI_P2P_GROUP)
                    if (group != null && group.isGroupOwner) {
                        _connectionState.value = ConnectionState.PAIRED
                    } else if (group != null) {
                        _connectionState.value = ConnectionState.CONNECTED
                    } else {
                        _connectionState.value = ConnectionState.DISCONNECTED
                    }
                }
            }
        }
    }
}
