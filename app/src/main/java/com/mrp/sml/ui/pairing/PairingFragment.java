package com.mrp.sml.ui.pairing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mrp.sml.R;
import com.mrp.sml.databinding.FragmentPairingBinding;
import com.mrp.sml.ui.connection.ConnectionDeviceAdapter;
import com.mrp.sml.ui.transfer.TransferFragment;
import com.mrp.sml.ui.transfer.TransferViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PairingFragment extends Fragment {

    public static final String ARG_MODE = "pairing_mode";
    public static final String ARG_FILE_PATHS = "file_paths";
    public static final String ARG_LOCAL_IP = "local_ip";

    public static final int MODE_SEND = 1;
    public static final int MODE_RECEIVE = 2;

    private FragmentPairingBinding binding;
    private PairingViewModel pairingViewModel;
    private TransferViewModel transferViewModel;
    private ConnectionDeviceAdapter deviceAdapter;

    private int launchMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPairingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pairingViewModel = new ViewModelProvider(requireActivity()).get(PairingViewModel.class);
        transferViewModel = new ViewModelProvider(requireActivity()).get(TransferViewModel.class);

        setupDeviceList();
        setupObservers();
        setupListeners();

        Bundle args = getArguments();
        if (args != null) {
            launchMode = args.getInt(ARG_MODE, MODE_SEND);
            if (launchMode == MODE_SEND) {
                ArrayList<String> filePaths = args.getStringArrayList(ARG_FILE_PATHS);
                String localIp = args.getString(ARG_LOCAL_IP, "");
                setupSenderMode(filePaths, localIp);
            } else {
                setupReceiverMode();
            }
        }
    }

    private void setupSenderMode(ArrayList<String> filePaths, String localIp) {
        binding.senderSection.setVisibility(View.VISIBLE);
        binding.receiverSection.setVisibility(View.GONE);
        binding.pairingTitleText.setText(R.string.pairing_title_send);
        binding.pairingSubtitleText.setText(R.string.pairing_subtitle_send);

        pairingViewModel.prepareSenderQr(localIp);

        if (filePaths != null && !filePaths.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String f : filePaths) {
                String name = f.substring(f.lastIndexOf('/') + 1);
                if (sb.length() > 0) sb.append("\n");
                sb.append("• ").append(name);
            }
            binding.selectedFilesText.setText(sb.toString());

            String sessionToken = pairingViewModel.getSessionToken();
            for (String path : filePaths) {
                transferViewModel.sendFile(path, localIp, sessionToken);
            }
        }
    }

    private void setupReceiverMode() {
        binding.senderSection.setVisibility(View.GONE);
        binding.receiverSection.setVisibility(View.VISIBLE);
        binding.pairingTitleText.setText(R.string.pairing_title_receive);
        binding.pairingSubtitleText.setText(R.string.pairing_subtitle_receive);

        pairingViewModel.prepareReceiverScan();
    }

    private void setupDeviceList() {
        deviceAdapter = new ConnectionDeviceAdapter(device -> {
            pairingViewModel.connectToDevice(device);
            startReceiveTransfer();
        });
        binding.discoveredDevicesList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.discoveredDevicesList.setAdapter(deviceAdapter);
    }

    private void setupObservers() {
        pairingViewModel.getQrCodeBitmap().observe(getViewLifecycleOwner(),
                bitmap -> {
                    if (bitmap != null) {
                        binding.qrCodeImage.setImageBitmap(bitmap);
                    }
                });

        pairingViewModel.getQrContentText().observe(getViewLifecycleOwner(),
                text -> binding.qrContentDisplay.setText(text));

        pairingViewModel.getDiscoveredDevices().observe(getViewLifecycleOwner(),
                devices -> deviceAdapter.submitList(devices));

        pairingViewModel.getStatusText().observe(getViewLifecycleOwner(),
                text -> {
                    binding.commonStatusText.setText(text);
                    binding.statusCard.setVisibility(
                            text != null && !text.equals("Ready") ? View.VISIBLE : View.GONE);
                    if (binding.senderSection.getVisibility() == View.VISIBLE) {
                        binding.senderStatusText.setText(text);
                    } else {
                        binding.receiverStatusText.setText(text);
                    }
                });

        transferViewModel.getTransferStatusText().observe(getViewLifecycleOwner(),
                status -> {
                    if (status != null && (status.contains("SENDING")
                            || status.contains("RECEIVING")
                            || status.contains("COMPLETED"))) {
                        navigateToTransfer();
                    }
                });
    }

    private void setupListeners() {
        binding.scanQrCard.setOnClickListener(v -> startQrScanner());

        binding.discoverButton.setOnClickListener(v -> pairingViewModel.discoverDevices());

        binding.cancelPairingButton.setOnClickListener(v -> {
            transferViewModel.cancelTransfer();
            pairingViewModel.disconnect();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void startQrScanner() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt(getString(R.string.pairing_scan_prompt));
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                pairingViewModel.onQrScanned(result.getContents());
                startReceiveTransfer();
            } else {
                pairingViewModel.updateStatus("QR scan cancelled");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startReceiveTransfer() {
        String sessionToken = pairingViewModel.getScannedSessionToken().getValue();
        if (sessionToken == null || sessionToken.isEmpty()) {
            sessionToken = "qr_session_" + System.currentTimeMillis();
        }
        transferViewModel.receiveFiles(
                requireContext().getFilesDir().getAbsolutePath(),
                sessionToken);
    }

    private void navigateToTransfer() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragmentContainer, new TransferFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
