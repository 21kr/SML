package com.mrp.sml.ui.history;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.mrp.sml.databinding.ActivityHistoryBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private HistoryRecordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new HistoryRecordAdapter();
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.historyRecyclerView.setAdapter(adapter);

        HistoryListViewModel viewModel = new ViewModelProvider(this).get(HistoryListViewModel.class);
        viewModel.getRows().observe(this, rows -> adapter.submitRows(rows));
    }
}
