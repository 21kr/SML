package com.mrp.sml.data.repository;

import android.content.Context;
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

    private final CopyOnWriteArrayList<ConnectionStateListener> stateListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<DiscoveredDevicesListener> deviceListeners = new CopyOnWriteArrayList<>();
    private final List<DiscoveredDevice> cachedDevices = new ArrayList<>();

    private volatile ConnectionState currentState = ConnectionState.IDLE;

    @Inject
    public DefaultDeviceConnectionRepository(
            @ApplicationContext Context context,
            DispatchersProvider dispatchersProvider
    ) {
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

        if (cachedDevices.isEmpty()) {
            cachedDevices.add(new DiscoveredDevice("02:11:22:33:44:55", "SML Peer A"));
            cachedDevices.add(new DiscoveredDevice("02:AA:BB:CC:DD:EE", "SML Peer B"));
        }
        notifyDevices();
    }

    @Override
    public void connectToDevice(String deviceId) {
        if (deviceId == null || deviceId.trim().isEmpty()) {
            currentState = ConnectionState.FAILED;
        } else {
            currentState = ConnectionState.CONNECTED;
        }
        notifyState();
    }

    @Override
    public void disconnect() {
        currentState = ConnectionState.DISCONNECTED;
        notifyState();
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
