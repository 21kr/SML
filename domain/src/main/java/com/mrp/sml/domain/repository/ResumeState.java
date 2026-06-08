package com.mrp.sml.domain.repository;

import java.util.ArrayList;
import java.util.List;

public class ResumeState {
    private final String fileName;
    private final long totalSize;
    private final List<Integer> receivedChunks;
    private final int chunkSize;

    public ResumeState(String fileName, long totalSize, List<Integer> receivedChunks, int chunkSize) {
        this.fileName = fileName;
        this.totalSize = totalSize;
        this.receivedChunks = receivedChunks == null ? new ArrayList<>() : new ArrayList<>(receivedChunks);
        this.chunkSize = chunkSize;
    }

    public String getFileName() { return fileName; }
    public long getTotalSize() { return totalSize; }
    public List<Integer> getReceivedChunks() { return receivedChunks; }
    public int getChunkSize() { return chunkSize; }

    public long getResumedOffset() {
        if (receivedChunks.isEmpty()) return 0L;
        int maxChunk = 0;
        for (int c : receivedChunks) {
            if (c > maxChunk) maxChunk = c;
        }
        return (long) (maxChunk + 1) * chunkSize;
    }
}
