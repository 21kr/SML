package com.mrp.sml.domain.usecase;

import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import java.util.List;
import javax.inject.Inject;

public class ConnectionUseCase {

    private final DeviceConnectionRepository repository;

    @Inject
    public ConnectionUseCase(DeviceConnectionRepository repository) {
        this.repository = repository;
    }

    public ConnectionState getCurrentConnectionState() {
        return repository.getCurrentConnectionState();
    }

    public void observeConnectionState(DeviceConnectionRepository.ConnectionStateListener listener) {
        repository.observeConnectionState(listener);
    }

    public void removeConnectionStateObserver(DeviceConnectionRepository.ConnectionStateListener listener) {
        repository.removeConnectionStateObserver(listener);
    }

    public void observeDiscoveredDevices(DeviceConnectionRepository.DiscoveredDevicesListener listener) {
        repository.observeDiscoveredDevices(listener);
    }

    public void removeDiscoveredDevicesObserver(DeviceConnectionRepository.DiscoveredDevicesListener listener) {
        repository.removeDiscoveredDevicesObserver(listener);
    }

    public void discoverDevices() {
        repository.discoverDevices();
    }

    public void connectToDevice(String deviceId) {
        if (deviceId == null || deviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Device ID must not be empty");
        }
        repository.connectToDevice(deviceId.trim());
    }

    public void disconnect() {
        repository.disconnect();
    }
}
