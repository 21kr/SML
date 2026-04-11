package com.mrp.sml;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.mrp.sml.databinding.ActivityMainBinding;
import com.mrp.sml.ui.connection.ConnectionViewModel;
import com.mrp.sml.ui.history.HistoryViewModel;
import com.mrp.sml.ui.transfer.TransferViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ConnectionViewModel connectionViewModel;
    private TransferViewModel transferViewModel;
    private HistoryViewModel historyViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        connectionViewModel = new ViewModelProvider(this).get(ConnectionViewModel.class);
        transferViewModel = new ViewModelProvider(this).get(TransferViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        setupObservers();
        setupListeners();
    }

    private void setupObservers() {
        connectionViewModel.getConnectionStateText().observe(this,
                text -> binding.connectionStateText.setText(text));
        connectionViewModel.getDiscoveredDevicesText().observe(this,
                text -> binding.discoveredDevicesText.setText(text));

        transferViewModel.getTransferStatusText().observe(this,
                text -> binding.transferStatusText.setText(text));
        transferViewModel.getTransferProgressText().observe(this,
                text -> binding.transferProgressText.setText(text));

        historyViewModel.getHistorySummaryText().observe(this,
                text -> binding.historySummaryText.setText(text));
    }

    private void setupListeners() {
        binding.discoverButton.setOnClickListener(view -> connectionViewModel.discoverDevices());
        binding.connectButton.setOnClickListener(view ->
                connectionViewModel.connectToDevice(binding.deviceIdInput.getText().toString()));
        binding.disconnectButton.setOnClickListener(view -> connectionViewModel.disconnect());

        binding.sendButton.setOnClickListener(view ->
                transferViewModel.sendFile(
                        binding.filePathInput.getText().toString(),
                        binding.destinationAddressInput.getText().toString()));
        binding.receiveButton.setOnClickListener(view ->
                transferViewModel.receiveFiles(binding.outputDirectoryInput.getText().toString()));
    }
}
