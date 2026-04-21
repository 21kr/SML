package com.mrp.sml.domain.usecase;

import com.mrp.sml.domain.repository.FileTransferRepository;
import java.util.List;
import javax.inject.Inject;

public class SendFilesUseCase {
    private final FileTransferRepository fileTransferRepository;

    @Inject
    public SendFilesUseCase(FileTransferRepository fileTransferRepository) {
        this.fileTransferRepository = fileTransferRepository;
    }

    public void execute(List<String> sourcePaths, String destinationAddress) {
        fileTransferRepository.sendFiles(sourcePaths, destinationAddress);
    }
}
