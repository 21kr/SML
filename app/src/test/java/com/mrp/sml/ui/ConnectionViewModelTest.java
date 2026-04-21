package com.mrp.sml.ui;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.mrp.sml.ui.connection.ConnectionViewModel;
import org.junit.Rule;
import org.junit.Test;

public class ConnectionViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void discoverDevices_updatesConnectionAndCount() {
        ViewModelTestDoubles.FakeDeviceConnectionRepository repository =
                new ViewModelTestDoubles.FakeDeviceConnectionRepository();

        ConnectionViewModel viewModel = new ConnectionViewModel(repository);
        viewModel.discoverDevices();

        assertEquals("Connection: DISCOVERING", viewModel.getConnectionStateText().getValue());
        assertEquals("Discovered devices: 1", viewModel.getDiscoveredDevicesText().getValue());
    }
}
