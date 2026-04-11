package com.mrp.sml.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultDeviceConnectionRepository implements DeviceConnectionRepository {

    private final MutableLiveData<ConnectionState> connectionState = new MutableLiveData<>(ConnectionState.IDLE);
    private final MutableLiveData<List<DiscoveredDevice>> discoveredDevices = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public DefaultDeviceConnectionRepository(@ApplicationContext Context context, DispatchersProvider dispatchersProvider) {
    }

    @Override
    public LiveData<ConnectionState> observeConnectionState() {
        return connectionState;
    }

    @Override
    public LiveData<List<DiscoveredDevice>> observeDiscoveredDevices() {
        return discoveredDevices;
    }

    @Override
    public void discoverDevices() {
        connectionState.postValue(ConnectionState.DISCOVERING);
    }

    @Override
    public void connectToDevice(String deviceId) {
        connectionState.postValue(ConnectionState.CONNECTED);
    }

    @Override
    public void disconnect() {
        connectionState.postValue(ConnectionState.DISCONNECTED);
    }
}
