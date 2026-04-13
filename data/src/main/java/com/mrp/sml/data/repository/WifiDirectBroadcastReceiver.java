package com.mrp.sml.data.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    interface Callback {
        void onWifiStateChanged(boolean enabled);

        void onPeersChanged();

        void onConnectionChanged();
    }

    private final Callback callback;

    WifiDirectBroadcastReceiver(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(
                    WifiP2pManager.EXTRA_WIFI_STATE,
                    WifiP2pManager.WIFI_P2P_STATE_DISABLED
            );
            callback.onWifiStateChanged(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED);
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            callback.onPeersChanged();
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            callback.onConnectionChanged();
        }
    }
}
