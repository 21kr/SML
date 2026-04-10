package com.mrp.sml.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

internal class WifiDirectBroadcastReceiver(
    private val onWifiP2pStateChanged: (Boolean) -> Unit,
    private val onPeersChanged: () -> Unit,
    private val onConnectionChanged: () -> Unit,
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                val state = intent.getIntExtra(
                    android.net.wifi.p2p.WifiP2pManager.EXTRA_WIFI_STATE,
                    android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_STATE_DISABLED,
                )
                onWifiP2pStateChanged(state == android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_STATE_ENABLED)
            }

            android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                onPeersChanged()
            }

            android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                onConnectionChanged()
            }
        }
    }
}
