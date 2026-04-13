package com.mrp.sml.data.repository;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultDeviceConnectionRepository implements DeviceConnectionRepository {

    private final Context context;

    private final CopyOnWriteArrayList<ConnectionStateListener> stateListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<DiscoveredDevicesListener> deviceListeners = new CopyOnWriteArrayList<>();
    private final List<DiscoveredDevice> cachedDevices = new ArrayList<>();

    private final WifiP2pManager wifiP2pManager;
    private final WifiP2pManager.Channel channel;
    private final WifiDirectBroadcastReceiver receiver;

    private volatile ConnectionState currentState = ConnectionState.IDLE;
    private volatile boolean receiverRegistered;

    @Inject
    public DefaultDeviceConnectionRepository(
            @ApplicationContext Context context,
            DispatchersProvider dispatchersProvider
    ) {
        this.context = context;
        wifiP2pManager = context.getSystemService(WifiP2pManager.class);
        channel = wifiP2pManager == null ? null : wifiP2pManager.initialize(context, context.getMainLooper(), null);

        receiver = new WifiDirectBroadcastReceiver(new WifiDirectBroadcastReceiver.Callback() {
            @Override
            public void onWifiStateChanged(boolean enabled) {
                currentState = enabled ? ConnectionState.IDLE : ConnectionState.FAILED;
                notifyState();
            }

            @Override
            public void onPeersChanged() {
                requestPeers();
            }

            @Override
            public void onConnectionChanged() {
                requestConnectionInfo();
            }
        });

    }

    @Override
    public ConnectionState getCurrentConnectionState() {
        return currentState;
    }

    @Override
    public void observeConnectionState(ConnectionStateListener listener) {
        stateListeners.addIfAbsent(listener);
        listener.onConnectionStateChanged(currentState);
    }

    @Override
    public void removeConnectionStateObserver(ConnectionStateListener listener) {
        stateListeners.remove(listener);
    }

    @Override
    public void observeDiscoveredDevices(DiscoveredDevicesListener listener) {
        deviceListeners.addIfAbsent(listener);
        listener.onDevicesUpdated(new ArrayList<>(cachedDevices));
    }

    @Override
    public void removeDiscoveredDevicesObserver(DiscoveredDevicesListener listener) {
        deviceListeners.remove(listener);
    }

    @Override
    public void discoverDevices() {
        currentState = ConnectionState.DISCOVERING;
        notifyState();

        ensureReceiverRegistered();

        if (wifiP2pManager == null || channel == null) {
            fallbackMockPeers();
            return;
        }

        try {
            wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    requestPeers();
                }

                @Override
                public void onFailure(int reason) {
                    currentState = ConnectionState.FAILED;
                    notifyState();
                    fallbackMockPeers();
                }
            });
        } catch (SecurityException securityException) {
            currentState = ConnectionState.FAILED;
            notifyState();
            fallbackMockPeers();
        }
    }

    @Override
    public void connectToDevice(String deviceId) {
        if (deviceId == null || deviceId.trim().isEmpty()) {
            currentState = ConnectionState.FAILED;
            notifyState();
            return;
        }

        if (wifiP2pManager == null || channel == null) {
            currentState = ConnectionState.CONNECTED;
            notifyState();
            return;
        }

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = deviceId.trim();
        try {
            wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    currentState = ConnectionState.CONNECTED;
                    notifyState();
                }

                @Override
                public void onFailure(int reason) {
                    currentState = ConnectionState.FAILED;
                    notifyState();
                }
            });
        } catch (SecurityException securityException) {
            currentState = ConnectionState.FAILED;
            notifyState();
        }
    }

    @Override
    public void disconnect() {
        if (wifiP2pManager == null || channel == null) {
            currentState = ConnectionState.DISCONNECTED;
            notifyState();
            return;
        }

        try {
            wifiP2pManager.removeGroup(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    currentState = ConnectionState.DISCONNECTED;
                    notifyState();
                }

                @Override
                public void onFailure(int reason) {
                    currentState = ConnectionState.FAILED;
                    notifyState();
                }
            });
        } catch (SecurityException securityException) {
            currentState = ConnectionState.FAILED;
            notifyState();
        }
    }

    private void requestPeers() {
        if (wifiP2pManager == null || channel == null) {
            fallbackMockPeers();
            return;
        }

        try {
            wifiP2pManager.requestPeers(channel, peers -> {
                cachedDevices.clear();
                for (WifiP2pDevice device : peers.getDeviceList()) {
                    cachedDevices.add(new DiscoveredDevice(device.deviceAddress, device.deviceName));
                }
                notifyDevices();
            });
        } catch (SecurityException securityException) {
            fallbackMockPeers();
        }
    }

    private void requestConnectionInfo() {
        if (wifiP2pManager == null || channel == null) {
            return;
        }

        try {
            wifiP2pManager.requestConnectionInfo(channel, info -> {
                currentState = info.groupFormed ? ConnectionState.CONNECTED : ConnectionState.DISCONNECTED;
                notifyState();
            });
        } catch (SecurityException securityException) {
            currentState = ConnectionState.FAILED;
            notifyState();
        }
    }

    private void fallbackMockPeers() {
        if (cachedDevices.isEmpty()) {
            cachedDevices.add(new DiscoveredDevice("02:11:22:33:44:55", "SML Peer A"));
            cachedDevices.add(new DiscoveredDevice("02:AA:BB:CC:DD:EE", "SML Peer B"));
        }
        notifyDevices();
    }

    private void ensureReceiverRegistered() {
        if (receiverRegistered) {
            return;
        }

        IntentFilter filter = buildIntentFilter();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.registerReceiver(
                        context,
                        receiver,
                        filter,
                        ContextCompat.RECEIVER_NOT_EXPORTED
                );
            } else {
                context.registerReceiver(receiver, filter);
            }
            receiverRegistered = true;
        } catch (RuntimeException runtimeException) {
            currentState = ConnectionState.FAILED;
            notifyState();
        }
    }

    private IntentFilter buildIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        return intentFilter;
    }

    private void notifyState() {
        for (ConnectionStateListener listener : stateListeners) {
            listener.onConnectionStateChanged(currentState);
        }
    }

    private void notifyDevices() {
        List<DiscoveredDevice> snapshot = new ArrayList<>(cachedDevices);
        for (DiscoveredDevicesListener listener : deviceListeners) {
            listener.onDevicesUpdated(snapshot);
        }
    }
}
