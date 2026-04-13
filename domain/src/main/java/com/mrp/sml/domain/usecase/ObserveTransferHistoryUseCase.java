package com.mrp.sml.domain.usecase;

import com.mrp.sml.domain.repository.TransferHistoryRepository;
import javax.inject.Inject;

public class ObserveTransferHistoryUseCase {
    private final TransferHistoryRepository transferHistoryRepository;

    @Inject
    public ObserveTransferHistoryUseCase(TransferHistoryRepository transferHistoryRepository) {
        this.transferHistoryRepository = transferHistoryRepository;
    }

    public void execute(TransferHistoryRepository.TransferHistoryListener listener) {
        transferHistoryRepository.observeTransferHistory(listener);
    }
}
