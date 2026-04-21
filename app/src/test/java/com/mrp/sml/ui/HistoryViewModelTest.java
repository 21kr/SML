package com.mrp.sml.ui;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.mrp.sml.ui.history.HistoryViewModel;
import org.junit.Rule;
import org.junit.Test;

public class HistoryViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void whenHistoryChanges_summaryIncludesLatestFileName() {
        ViewModelTestDoubles.FakeTransferHistoryRepository repository =
                new ViewModelTestDoubles.FakeTransferHistoryRepository();
        HistoryViewModel viewModel = new HistoryViewModel(repository);

        repository.pushSampleRecord();

        assertTrue(viewModel.getHistorySummaryText().getValue().contains("sample.txt"));
    }
}
