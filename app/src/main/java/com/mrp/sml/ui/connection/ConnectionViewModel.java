package com.mrp.sml.ui.connection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class ConnectionViewModel extends ViewModel {

    private final DeviceConnectionRepository deviceConnectionRepository;

    private final MutableLiveData<String> connectionStateText = new MutableLiveData<>("Connection: IDLE");
    private final MutableLiveData<String> discoveredDevicesText = new MutableLiveData<>("Discovered devices: 0");

    private final DeviceConnectionRepository.ConnectionStateListener stateListener =
            state -> connectionStateText.postValue("Connection: " + state.name());

    private final DeviceConnectionRepository.DiscoveredDevicesListener devicesListener =
            this::updateDiscoveredDevicesText;

    @Inject
    public ConnectionViewModel(DeviceConnectionRepository deviceConnectionRepository) {
        this.deviceConnectionRepository = deviceConnectionRepository;
        this.deviceConnectionRepository.observeConnectionState(stateListener);
        this.deviceConnectionRepository.observeDiscoveredDevices(devicesListener);
    }

    public LiveData<String> getConnectionStateText() {
        return connectionStateText;
    }

    public LiveData<String> getDiscoveredDevicesText() {
        return discoveredDevicesText;
    }

    public void discoverDevices() {
        deviceConnectionRepository.discoverDevices();
    }

    public void connectToDevice(String deviceId) {
        if (deviceId == null || deviceId.trim().isEmpty()) {
            connectionStateText.postValue("Connection: FAILED (missing device id)");
            return;
        }
        deviceConnectionRepository.connectToDevice(deviceId.trim());
    }

    public void disconnect() {
        deviceConnectionRepository.disconnect();
    }

    private void updateDiscoveredDevicesText(List<DiscoveredDevice> devices) {
        int count = devices == null ? 0 : devices.size();
        discoveredDevicesText.postValue("Discovered devices: " + count);
    }

    @Override
    protected void onCleared() {
        deviceConnectionRepository.removeConnectionStateObserver(stateListener);
        deviceConnectionRepository.removeDiscoveredDevicesObserver(devicesListener);
        super.onCleared();
    }
}
