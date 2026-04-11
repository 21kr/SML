package com.mrp.sml;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.mrp.sml.databinding.ActivityMainBinding;
import com.mrp.sml.ui.connection.ConnectionDeviceAdapter;
import com.mrp.sml.ui.connection.ConnectionViewModel;
import com.mrp.sml.ui.history.HistoryActivity;
import com.mrp.sml.ui.history.HistoryViewModel;
import com.mrp.sml.ui.transfer.TransferViewModel;
import dagger.hilt.android.AndroidEntryPoint;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final int RUNTIME_PERMISSION_REQUEST_CODE = 301;

    private ActivityMainBinding binding;
    private ConnectionDeviceAdapter connectionDeviceAdapter;

    private ConnectionViewModel connectionViewModel;
    private TransferViewModel transferViewModel;
    private HistoryViewModel historyViewModel;

    private final ActivityResultLauncher<String[]> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), this::handlePickedFile);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        connectionViewModel = new ViewModelProvider(this).get(ConnectionViewModel.class);
        transferViewModel = new ViewModelProvider(this).get(TransferViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        setupConnectionList();
        setupObservers();
        setupListeners();
        ensureRuntimePermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectionViewModel.discoverDevices();
    }

    @Override
    protected void onStop() {
        connectionViewModel.disconnect();
        super.onStop();
    }

    private void setupConnectionList() {
        connectionDeviceAdapter = new ConnectionDeviceAdapter(device -> {
            binding.deviceIdInput.setText(device.getId());
            connectionViewModel.connectToDevice(device);
        });
        binding.discoveredDevicesList.setLayoutManager(new LinearLayoutManager(this));
        binding.discoveredDevicesList.setAdapter(connectionDeviceAdapter);
    }

    private void setupObservers() {
        connectionViewModel.getConnectionStateText().observe(this,
                text -> binding.connectionStateText.setText(text));
        connectionViewModel.getDiscoveredDevicesText().observe(this,
                text -> binding.discoveredDevicesText.setText(text));
        connectionViewModel.getDiscoveredDevices().observe(this,
                devices -> connectionDeviceAdapter.submitList(devices));

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

        binding.pickFileButton.setOnClickListener(view -> filePickerLauncher.launch(new String[]{"*/*"}));
        binding.openHistoryButton.setOnClickListener(view ->
                startActivity(new Intent(this, HistoryActivity.class)));

        binding.sendButton.setOnClickListener(view ->
                transferViewModel.sendFile(
                        binding.filePathInput.getText().toString(),
                        binding.destinationAddressInput.getText().toString()));
        binding.receiveButton.setOnClickListener(view ->
                transferViewModel.receiveFiles(binding.outputDirectoryInput.getText().toString()));
        binding.cancelTransferButton.setOnClickListener(view -> transferViewModel.cancelTransfer());
        binding.resumeTransferButton.setOnClickListener(view -> transferViewModel.resumeTransfer());
    }

    private void handlePickedFile(Uri uri) {
        if (uri == null) {
            return;
        }
        try {
            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (SecurityException ignored) {
            // Ignore if persistable permission is not available.
        }

        String cachedPath = copyUriToCache(uri);
        if (cachedPath == null) {
            Toast.makeText(this, R.string.file_pick_failed, Toast.LENGTH_LONG).show();
            return;
        }
        binding.filePathInput.setText(cachedPath);
        Toast.makeText(this, R.string.file_pick_success, Toast.LENGTH_SHORT).show();
    }

    private String copyUriToCache(Uri uri) {
        String fileName = "picked_" + System.currentTimeMillis();
        File targetFile = new File(getCacheDir(), fileName);

        try (InputStream input = getContentResolver().openInputStream(uri);
             FileOutputStream output = new FileOutputStream(targetFile)) {
            if (input == null) {
                return null;
            }
            byte[] buffer = new byte[32 * 1024];
            int read;
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
            output.flush();
            return targetFile.getAbsolutePath();
        } catch (IOException ioException) {
            return null;
        }
    }

    private void ensureRuntimePermissions() {
        List<String> pendingPermissions = new ArrayList<>();
        addPermissionIfMissing(pendingPermissions, Manifest.permission.ACCESS_FINE_LOCATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            addPermissionIfMissing(pendingPermissions, Manifest.permission.NEARBY_WIFI_DEVICES);
        } else {
            addPermissionIfMissing(pendingPermissions, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (pendingPermissions.isEmpty()) {
            binding.permissionStatusText.setText(getString(R.string.permissions_granted));
            return;
        }

        binding.permissionStatusText.setText(getString(R.string.permissions_required));
        ActivityCompat.requestPermissions(
                this,
                pendingPermissions.toArray(new String[0]),
                RUNTIME_PERMISSION_REQUEST_CODE
        );
    }

    private void addPermissionIfMissing(List<String> list, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            list.add(permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != RUNTIME_PERMISSION_REQUEST_CODE) {
            return;
        }

        boolean allGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }

        if (allGranted) {
            binding.permissionStatusText.setText(getString(R.string.permissions_granted));
            Toast.makeText(this, R.string.permissions_granted, Toast.LENGTH_SHORT).show();
            connectionViewModel.discoverDevices();
        } else {
            binding.permissionStatusText.setText(getString(R.string.permissions_denied));
            Toast.makeText(this, R.string.permissions_denied, Toast.LENGTH_LONG).show();
        }
    }
}
