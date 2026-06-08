package com.mrp.sml;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.mrp.sml.databinding.ActivityMainBinding;
import com.mrp.sml.ui.connection.ConnectionViewModel;
import com.mrp.sml.ui.history.HistoryFragment;
import com.mrp.sml.ui.history.HistoryViewModel;
import com.mrp.sml.ui.home.HomeFragment;
import com.mrp.sml.ui.transfer.TransferFragment;
import com.mrp.sml.ui.transfer.TransferViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final int RUNTIME_PERMISSION_REQUEST_CODE = 301;

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

        setupBottomNavigation(savedInstanceState);
        ensureRuntimePermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasRequiredPermissions()) {
            connectionViewModel.discoverDevices();
        }
    }

    @Override
    protected void onStop() {
        connectionViewModel.disconnect();
        super.onStop();
    }

    private void setupBottomNavigation(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment())
                    .commit();
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (itemId == R.id.nav_transfer) {
                fragment = new TransferFragment();
            } else if (itemId == R.id.nav_history) {
                fragment = new HistoryFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                return true;
            }
            return false;
        });
    }

    private boolean hasRequiredPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.NEARBY_WIFI_DEVICES)
                    == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    private void ensureRuntimePermissions() {
        List<String> pendingPermissions = new ArrayList<>();
        addPermissionIfMissing(pendingPermissions, Manifest.permission.ACCESS_FINE_LOCATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            addPermissionIfMissing(pendingPermissions, Manifest.permission.NEARBY_WIFI_DEVICES);
            addPermissionIfMissing(pendingPermissions, Manifest.permission.READ_MEDIA_IMAGES);
            addPermissionIfMissing(pendingPermissions, Manifest.permission.READ_MEDIA_VIDEO);
            addPermissionIfMissing(pendingPermissions, Manifest.permission.READ_MEDIA_AUDIO);
        } else {
            addPermissionIfMissing(pendingPermissions, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (pendingPermissions.isEmpty()) {
            return;
        }

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
            connectionViewModel.discoverDevices();
        } else {
            showPermissionRecoveryDialog();
        }
    }

    private void showPermissionRecoveryDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permissions_dialog_title)
                .setMessage(R.string.permissions_dialog_message)
                .setPositiveButton(R.string.open_settings, (dialog, which) -> openAppSettings())
                .setNegativeButton(R.string.retry_permissions, (dialog, which) -> ensureRuntimePermissions())
                .setNeutralButton(android.R.string.cancel, null)
                .show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }
}
