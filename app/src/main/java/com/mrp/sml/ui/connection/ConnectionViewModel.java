package com.mrp.sml.ui.connection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import com.mrp.sml.domain.usecase.ConnectionUseCase;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class ConnectionViewModel extends ViewModel {

    private final ConnectionUseCase connectionUseCase;

    private final MutableLiveData<String> connectionStateText = new MutableLiveData<>("Connection: IDLE");
    private final MutableLiveData<String> discoveredDevicesText = new MutableLiveData<>("Discovered devices: 0");
    private final MutableLiveData<List<DiscoveredDevice>> discoveredDevices = new MutableLiveData<>(new ArrayList<>());

    private final DeviceConnectionRepository.ConnectionStateListener stateListener =
            state -> connectionStateText.postValue("Connection: " + state.name());

    private final DeviceConnectionRepository.DiscoveredDevicesListener devicesListener =
            this::updateDiscoveredDevices;

    @Inject
    public ConnectionViewModel(ConnectionUseCase connectionUseCase) {
        this.connectionUseCase = connectionUseCase;
        this.connectionUseCase.observeConnectionState(stateListener);
        this.connectionUseCase.observeDiscoveredDevices(devicesListener);
    }

    public LiveData<String> getConnectionStateText() {
        return connectionStateText;
    }

    public LiveData<String> getDiscoveredDevicesText() {
        return discoveredDevicesText;
    }

    public LiveData<List<DiscoveredDevice>> getDiscoveredDevices() {
        return discoveredDevices;
    }

    public void discoverDevices() {
        connectionUseCase.discoverDevices();
    }

    public void connectToDevice(String deviceId) {
        try {
            connectionUseCase.connectToDevice(deviceId);
        } catch (IllegalArgumentException e) {
            connectionStateText.postValue("Connection: FAILED (missing device id)");
        }
    }

    public void connectToDevice(DiscoveredDevice device) {
        if (device == null) {
            return;
        }
        connectToDevice(device.getId());
    }

    public void disconnect() {
        connectionUseCase.disconnect();
    }

    private void updateDiscoveredDevices(List<DiscoveredDevice> devices) {
        int count = devices == null ? 0 : devices.size();
        discoveredDevicesText.postValue("Discovered devices: " + count);
        discoveredDevices.postValue(devices == null ? new ArrayList<>() : new ArrayList<>(devices));
    }

    @Override
    protected void onCleared() {
        connectionUseCase.removeConnectionStateObserver(stateListener);
        connectionUseCase.removeDiscoveredDevicesObserver(devicesListener);
        super.onCleared();
    }
}
