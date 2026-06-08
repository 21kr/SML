package com.mrp.sml.data.repository;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import com.mrp.sml.data.BuildConfig;
import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.model.DeviceInfo;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultDeviceConnectionRepository implements DeviceConnectionRepository {

    private static final int HANDSHAKE_PORT = 8989;

    private final Context context;
    private final DispatchersProvider dispatchersProvider;

    private final CopyOnWriteArrayList<ConnectionStateListener> stateListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<DiscoveredDevicesListener> deviceListeners = new CopyOnWriteArrayList<>();
    private final List<DiscoveredDevice> cachedDevices = new ArrayList<>();

    private final WifiP2pManager wifiP2pManager;
    private final WifiP2pManager.Channel channel;
    private final WifiDirectBroadcastReceiver receiver;

    private volatile ConnectionState currentState = ConnectionState.IDLE;
    private volatile boolean receiverRegistered;
    private volatile DeviceInfo myDeviceInfo;
    private volatile DeviceInfo connectedDeviceInfo;
    private volatile String groupOwnerAddress;

    @Inject
    public DefaultDeviceConnectionRepository(
            @ApplicationContext Context context,
            DispatchersProvider dispatchersProvider
    ) {
        this.context = context;
        this.dispatchersProvider = dispatchersProvider;

        myDeviceInfo = new DeviceInfo(
                android.os.Build.MODEL,
                android.os.Build.DEVICE + "_" + System.currentTimeMillis(),
                "1.0"
        );

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
        connectedDeviceInfo = null;
        groupOwnerAddress = null;

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

    @Override
    public void performHandshake(String deviceAddress) {
        dispatchersProvider.ioExecutor().execute(() -> {
            try {
                currentState = ConnectionState.PAIRING;
                notifyState();

                Socket socket = new Socket(deviceAddress, HANDSHAKE_PORT);
                socket.setSoTimeout(10000);
                try (DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                    output.writeUTF("HELLO");
                    output.writeUTF(myDeviceInfo.toJson());

                    String response = input.readUTF();
                    if ("HELLO_ACK".equals(response)) {
                        String remoteInfo = input.readUTF();
                        connectedDeviceInfo = DeviceInfo.fromJson(remoteInfo);
                        currentState = ConnectionState.PAIRED;
                        notifyState();
                    } else {
                        currentState = ConnectionState.FAILED;
                        notifyState();
                    }
                }
            } catch (Exception e) {
                currentState = ConnectionState.FAILED;
                notifyState();
            }
        });
    }

    @Override
    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.myDeviceInfo = deviceInfo;
    }

    @Override
    public DeviceInfo getConnectedDeviceInfo() {
        return connectedDeviceInfo;
    }

    public String getGroupOwnerAddress() {
        if (groupOwnerAddress != null) return groupOwnerAddress;
        groupOwnerAddress = resolveLocalIpv4Address();
        return groupOwnerAddress;
    }

    public void startHandshakeServer() {
        dispatchersProvider.ioExecutor().execute(() -> {
            try (ServerSocket serverSocket = new ServerSocket(HANDSHAKE_PORT)) {
                serverSocket.setReuseAddress(true);
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(10000);
                try (DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                    String hello = input.readUTF();
                    if (!"HELLO".equals(hello)) {
                        return;
                    }
                    String remoteInfo = input.readUTF();
                    connectedDeviceInfo = DeviceInfo.fromJson(remoteInfo);

                    output.writeUTF("HELLO_ACK");
                    output.writeUTF(myDeviceInfo.toJson());

                    currentState = ConnectionState.PAIRED;
                    notifyState();
                }
            } catch (Exception ignored) {
            }
        });
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
                boolean connected = info.groupFormed;
                currentState = connected ? ConnectionState.CONNECTED : ConnectionState.DISCONNECTED;
                if (connected && info.isGroupOwner) {
                    groupOwnerAddress = info.groupOwnerAddress.getHostAddress();
                } else if (connected) {
                    groupOwnerAddress = resolveLocalIpv4Address();
                }
                notifyState();
            });
        } catch (SecurityException securityException) {
            currentState = ConnectionState.FAILED;
            notifyState();
        }
    }

    private String resolveLocalIpv4Address() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces != null && interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.isUp() || networkInterface.isLoopback()) continue;
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof java.net.Inet4Address
                            && !address.isLoopbackAddress()
                            && !address.isLinkLocalAddress()) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private void fallbackMockPeers() {
        if (!BuildConfig.DEBUG) {
            currentState = ConnectionState.FAILED;
            notifyState();
            return;
        }
        if (cachedDevices.isEmpty()) {
            cachedDevices.add(new DiscoveredDevice("02:11:22:33:44:55", "SML Peer A"));
            cachedDevices.add(new DiscoveredDevice("02:AA:BB:CC:DD:EE", "SML Peer B"));
        }
        notifyDevices();
    }

    private void ensureReceiverRegistered() {
        if (receiverRegistered) return;

        IntentFilter filter = buildIntentFilter();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
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
