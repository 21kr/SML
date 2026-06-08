package com.mrp.sml.ui.transfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mrp.sml.databinding.FragmentTransferBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TransferFragment extends Fragment {

    private FragmentTransferBinding binding;
    private TransferViewModel transferViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTransferBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transferViewModel = new ViewModelProvider(requireActivity()).get(TransferViewModel.class);

        setupObservers();
        setupListeners();
    }

    private void setupObservers() {
        transferViewModel.getTransferStatusText().observe(getViewLifecycleOwner(),
                text -> {
                    binding.transferStatusText.setText(text);
                    updateVisibility(text);
                });

        transferViewModel.getTransferProgressText().observe(getViewLifecycleOwner(),
                text -> binding.transferProgressText.setText(text));

        transferViewModel.getCurrentFileName().observe(getViewLifecycleOwner(),
                name -> binding.fileNameText.setText(name));

        transferViewModel.getProgressValue().observe(getViewLifecycleOwner(),
                value -> {
                    binding.transferProgressIndicator.setProgressCompat(
                            (int) (value * 100), true);
                });

        transferViewModel.getSpeedText().observe(getViewLifecycleOwner(),
                text -> binding.speedText.setText(text));

        transferViewModel.getEtaText().observe(getViewLifecycleOwner(),
                text -> binding.etaText.setText(text));

        transferViewModel.getIsComplete().observe(getViewLifecycleOwner(),
                complete -> {
                    binding.successCard.setVisibility(complete ? View.VISIBLE : View.GONE);
                    binding.activeTransferSection.setVisibility(complete ? View.GONE : View.VISIBLE);
                });

        transferViewModel.getSuccessSummary().observe(getViewLifecycleOwner(),
                summary -> binding.successSummaryText.setText(summary));
    }

    private void setupListeners() {
        binding.cancelTransferButton.setOnClickListener(v -> transferViewModel.cancelTransfer());
        binding.resumeTransferButton.setOnClickListener(v -> transferViewModel.resumeTransfer());
    }

    private void updateVisibility(String statusText) {
        boolean active = statusText != null
                && (statusText.contains("SENDING")
                    || statusText.contains("RECEIVING")
                    || statusText.contains("RETRYING")
                    || statusText.contains("IDLE"));

        binding.activeTransferSection.setVisibility(active ? View.VISIBLE : View.GONE);
        binding.idleStatusText.setVisibility(active ? View.GONE : View.VISIBLE);

        boolean indeterminate = statusText != null
                && (statusText.contains("RETRYING") || statusText.contains("IDLE"));
        binding.transferProgressIndicator.setIndeterminate(indeterminate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
