package com.mrp.sml.domain.repository;

import java.util.List;

public interface DeviceConnectionRepository {
    ConnectionState getCurrentConnectionState();

    void observeConnectionState(ConnectionStateListener listener);
    void removeConnectionStateObserver(ConnectionStateListener listener);

    void observeDiscoveredDevices(DiscoveredDevicesListener listener);
    void removeDiscoveredDevicesObserver(DiscoveredDevicesListener listener);

    void discoverDevices();
    void connectToDevice(String deviceId);
    void disconnect();

    interface ConnectionStateListener {
        void onConnectionStateChanged(ConnectionState state);
    }

    interface DiscoveredDevicesListener {
        void onDevicesUpdated(List<DiscoveredDevice> devices);
    }
}
