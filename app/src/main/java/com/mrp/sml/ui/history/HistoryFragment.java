package com.mrp.sml.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mrp.sml.databinding.FragmentHistoryBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private HistoryRecordAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new HistoryRecordAdapter();
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.historyRecyclerView.setAdapter(adapter);

        HistoryListViewModel viewModel = new ViewModelProvider(this).get(HistoryListViewModel.class);
        viewModel.getRows().observe(getViewLifecycleOwner(), rows -> adapter.submitRows(rows));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
