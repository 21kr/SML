package com.mrp.sml.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;

public interface DeviceConnectionRepository {
    LiveData<ConnectionState> observeConnectionState();
    LiveData<List<DiscoveredDevice>> observeDiscoveredDevices();
    void discoverDevices();
    void connectToDevice(String deviceId);
    void disconnect();
}
