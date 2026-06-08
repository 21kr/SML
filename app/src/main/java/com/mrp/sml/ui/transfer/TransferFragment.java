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
                    updateTransferProgressIndicator(text);
                });
        transferViewModel.getTransferProgressText().observe(getViewLifecycleOwner(),
                text -> binding.transferProgressText.setText(text));
    }

    private void setupListeners() {
        binding.cancelTransferButton.setOnClickListener(v -> transferViewModel.cancelTransfer());
        binding.resumeTransferButton.setOnClickListener(v -> transferViewModel.resumeTransfer());
    }

    private void updateTransferProgressIndicator(String statusText) {
        boolean active = statusText.contains("SENDING")
                || statusText.contains("RECEIVING")
                || statusText.contains("RETRYING");
        binding.transferProgressIndicator.setVisibility(active ? View.VISIBLE : View.GONE);
        boolean indeterminate = statusText.contains("RETRYING") || statusText.contains("IDLE");
        binding.transferProgressIndicator.setIndeterminate(indeterminate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
