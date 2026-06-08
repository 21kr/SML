package com.mrp.sml.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mrp.sml.R;
import com.mrp.sml.databinding.FragmentHomeBinding;
import com.mrp.sml.ui.pairing.PairingFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private final ActivityResultLauncher<String[]> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), this::handlePickedFile);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();
    }

    private void setupListeners() {
        binding.sendCard.setOnClickListener(v -> {
            filePickerLauncher.launch(new String[]{"*/*"});
        });

        binding.receiveCard.setOnClickListener(v -> {
            navigateToPairingReceive();
        });
    }

    private void handlePickedFile(Uri uri) {
        if (uri == null) {
            return;
        }
        try {
            requireContext().getContentResolver().takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (SecurityException ignored) {
        }

        String cachedPath = copyUriToCache(uri);
        if (cachedPath == null) {
            Toast.makeText(getContext(), R.string.file_pick_failed, Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getContext(), R.string.file_selected, Toast.LENGTH_SHORT).show();

        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(cachedPath);
        navigateToPairingSend(filePaths);
    }

    private void navigateToPairingSend(ArrayList<String> filePaths) {
        String localIp = resolveLocalIpv4Address();
        Bundle args = new Bundle();
        args.putInt(PairingFragment.ARG_MODE, PairingFragment.MODE_SEND);
        args.putStringArrayList(PairingFragment.ARG_FILE_PATHS, filePaths);
        args.putString(PairingFragment.ARG_LOCAL_IP, localIp == null ? "" : localIp);

        PairingFragment fragment = new PairingFragment();
        fragment.setArguments(args);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String copyUriToCache(Uri uri) {
        String fileName = "picked_" + System.currentTimeMillis();
        File targetFile = new File(requireContext().getCacheDir(), fileName);

        try (InputStream input = requireContext().getContentResolver().openInputStream(uri);
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

    private void navigateToPairingReceive() {
        Bundle args = new Bundle();
        args.putInt(PairingFragment.ARG_MODE, PairingFragment.MODE_RECEIVE);

        PairingFragment fragment = new PairingFragment();
        fragment.setArguments(args);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Nullable
    private String resolveLocalIpv4Address() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces != null && interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.isUp() || networkInterface.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet4Address
                            && !address.isLoopbackAddress()
                            && !address.isLinkLocalAddress()) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (IOException ignored) {
            return null;
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
