package com.mrp.sml;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.mrp.sml.databinding.ActivityMainBinding;
import com.mrp.sml.ui.main.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setupObservers();
        setupListeners();
    }

    private void setupObservers() {
        viewModel.getConnectionStateText().observe(this, text -> binding.connectionStateText.setText(text));
        viewModel.getTransferStatusText().observe(this, text -> binding.transferStatusText.setText(text));
        viewModel.getTransferProgressText().observe(this, text -> binding.transferProgressText.setText(text));
        viewModel.getHistorySummaryText().observe(this, text -> binding.historySummaryText.setText(text));
    }

    private void setupListeners() {
        binding.discoverButton.setOnClickListener(view -> viewModel.discoverDevices());
        binding.sendButton.setOnClickListener(view -> viewModel.sendSample());
        binding.receiveButton.setOnClickListener(view -> viewModel.receiveSample());
    }
}
