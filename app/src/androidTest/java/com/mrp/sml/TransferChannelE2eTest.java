package com.mrp.sml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.mrp.sml.core.common.DefaultDispatchersProvider;
import com.mrp.sml.data.repository.DefaultFileTransferRepository;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.repository.TransferExecutionStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TransferChannelE2eTest {

    @Test
    public void senderReceiver_happyPath_matchingToken_transfersFile() throws Exception {
        File workingDir = new File(InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getCacheDir(), "e2e_happy");
        if (!workingDir.exists()) {
            assertTrue(workingDir.mkdirs());
        }

        File input = writeSizedFile(new File(workingDir, "input.bin"), 256 * 1024);
        File outputDir = new File(workingDir, "receiver");
        assertTrue(outputDir.exists() || outputDir.mkdirs());

        DefaultFileTransferRepository sender = newRepository();
        DefaultFileTransferRepository receiver = newRepository();

        CountDownLatch senderDone = new CountDownLatch(1);
        CountDownLatch receiverDone = new CountDownLatch(1);
        sender.observeTransferStatus(update -> {
            if (update.getStatus() == TransferExecutionStatus.COMPLETED) {
                senderDone.countDown();
            }
        });
        receiver.observeTransferStatus(update -> {
            if (update.getStatus() == TransferExecutionStatus.COMPLETED) {
                receiverDone.countDown();
            }
        });

        receiver.receiveFiles(outputDir.getAbsolutePath(), "gate-token");
        Thread.sleep(250L);
        sender.sendFile(input.getAbsolutePath(), "127.0.0.1", "gate-token");

        assertTrue(senderDone.await(20, TimeUnit.SECONDS));
        assertTrue(receiverDone.await(20, TimeUnit.SECONDS));

        File received = new File(outputDir, input.getName());
        assertTrue(received.exists());
        assertEquals(input.length(), received.length());
    }

    @Test
    public void senderReceiver_tokenMismatch_failsAuthentication() throws Exception {
        File workingDir = new File(InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getCacheDir(), "e2e_token_mismatch");
        if (!workingDir.exists()) {
            assertTrue(workingDir.mkdirs());
        }

        File input = writeSizedFile(new File(workingDir, "input.bin"), 64 * 1024);
        File outputDir = new File(workingDir, "receiver");
        assertTrue(outputDir.exists() || outputDir.mkdirs());

        DefaultFileTransferRepository sender = newRepository();
        DefaultFileTransferRepository receiver = newRepository();

        CountDownLatch receiverFailed = new CountDownLatch(1);
        receiver.observeTransferStatus(update -> {
            if (update.getStatus() == TransferExecutionStatus.FAILED
                    && update.getUserMessage() != null
                    && update.getUserMessage().toLowerCase().contains("auth")) {
                receiverFailed.countDown();
            }
        });

        receiver.receiveFiles(outputDir.getAbsolutePath(), "receiver-token");
        Thread.sleep(250L);
        sender.sendFile(input.getAbsolutePath(), "127.0.0.1", "sender-token");

        assertTrue(receiverFailed.await(20, TimeUnit.SECONDS));
    }

    @Test
    public void senderReceiver_interruptionAndResume_completesAfterRetry() throws Exception {
        File workingDir = new File(InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getCacheDir(), "e2e_resume");
        if (!workingDir.exists()) {
            assertTrue(workingDir.mkdirs());
        }

        File input = writeSizedFile(new File(workingDir, "large.bin"), 8 * 1024 * 1024);
        File outputDir = new File(workingDir, "receiver");
        assertTrue(outputDir.exists() || outputDir.mkdirs());

        DefaultFileTransferRepository sender = newRepository();
        DefaultFileTransferRepository receiver = newRepository();

        CountDownLatch senderCancelled = new CountDownLatch(1);
        CountDownLatch senderCompleted = new CountDownLatch(1);
        sender.observeTransferStatus(update -> {
            if (update.getStatus() == TransferExecutionStatus.FAILED) {
                senderCancelled.countDown();
            }
            if (update.getStatus() == TransferExecutionStatus.COMPLETED) {
                senderCompleted.countDown();
            }
        });

        receiver.receiveFiles(outputDir.getAbsolutePath(), "resume-token");
        Thread.sleep(250L);
        sender.sendFile(input.getAbsolutePath(), "127.0.0.1", "resume-token");
        Thread.sleep(30L);
        sender.cancelTransfer();

        assertTrue(senderCancelled.await(10, TimeUnit.SECONDS));

        receiver.receiveFiles(outputDir.getAbsolutePath(), "resume-token");
        Thread.sleep(250L);
        sender.resumeLastTransfer();

        assertTrue(senderCompleted.await(20, TimeUnit.SECONDS));
        File received = new File(outputDir, input.getName());
        assertTrue(received.exists());
        assertEquals(input.length(), received.length());
    }

    private DefaultFileTransferRepository newRepository() {
        return new DefaultFileTransferRepository(new DefaultDispatchersProvider(), new InMemoryTransferHistoryRepository());
    }

    private File writeSizedFile(File file, int sizeBytes) throws IOException {
        byte[] buffer = new byte[32 * 1024];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) (i % 97);
        }

        try (FileOutputStream output = new FileOutputStream(file)) {
            int remaining = sizeBytes;
            while (remaining > 0) {
                int write = Math.min(buffer.length, remaining);
                output.write(buffer, 0, write);
                remaining -= write;
            }
            output.flush();
        }
        return file;
    }

    private static class InMemoryTransferHistoryRepository implements TransferHistoryRepository {
        private final CopyOnWriteArrayList<TransferRecord> records = new CopyOnWriteArrayList<>();
        private final CopyOnWriteArrayList<TransferHistoryListener> listeners = new CopyOnWriteArrayList<>();

        @Override
        public List<TransferRecord> getTransferHistory() {
            return new ArrayList<>(records);
        }

        @Override
        public void saveTransferRecord(TransferRecord record) {
            records.add(0, record);
            List<TransferRecord> snapshot = new ArrayList<>(records);
            for (TransferHistoryListener listener : listeners) {
                listener.onHistoryChanged(snapshot);
            }
        }

        @Override
        public void observeTransferHistory(TransferHistoryListener listener) {
            listeners.addIfAbsent(listener);
            listener.onHistoryChanged(new ArrayList<>(records));
        }

        @Override
        public void removeTransferHistoryObserver(TransferHistoryListener listener) {
            listeners.remove(listener);
        }
    }
}
