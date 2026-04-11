package com.mrp.sml.ui;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.mrp.sml.ui.transfer.TransferViewModel;
import org.junit.Rule;
import org.junit.Test;

public class TransferViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void sendFile_withoutPath_reportsValidationError() {
        TransferViewModel viewModel =
                new TransferViewModel(new ViewModelTestDoubles.FakeFileTransferRepository());

        viewModel.sendFile("", "192.168.49.1");

        assertTrue(viewModel.getTransferStatusText().getValue().contains("file path is required"));
    }
}
