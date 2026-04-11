package com.mrp.sml.ui;

import com.mrp.sml.domain.model.TransferDirection;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.model.TransferStatus;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferExecutionStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import com.mrp.sml.domain.repository.TransferProgress;
import com.mrp.sml.domain.repository.TransferStatusUpdate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class ViewModelTestDoubles {

    private ViewModelTestDoubles() {
    }

    static class FakeDeviceConnectionRepository implements DeviceConnectionRepository {
        private ConnectionState state = ConnectionState.IDLE;
        private ConnectionStateListener stateListener;
        private DiscoveredDevicesListener devicesListener;

        @Override
        public ConnectionState getCurrentConnectionState() {
            return state;
        }

        @Override
        public void observeConnectionState(ConnectionStateListener listener) {
            this.stateListener = listener;
            listener.onConnectionStateChanged(state);
        }

        @Override
        public void removeConnectionStateObserver(ConnectionStateListener listener) {
            this.stateListener = null;
        }

        @Override
        public void observeDiscoveredDevices(DiscoveredDevicesListener listener) {
            this.devicesListener = listener;
            listener.onDevicesUpdated(Collections.emptyList());
        }

        @Override
        public void removeDiscoveredDevicesObserver(DiscoveredDevicesListener listener) {
            this.devicesListener = null;
        }

        @Override
        public void discoverDevices() {
            state = ConnectionState.DISCOVERING;
            if (stateListener != null) {
                stateListener.onConnectionStateChanged(state);
            }
            if (devicesListener != null) {
                List<DiscoveredDevice> devices = new ArrayList<>();
                devices.add(new DiscoveredDevice("id-1", "Device 1"));
                devicesListener.onDevicesUpdated(devices);
            }
        }

        @Override
        public void connectToDevice(String deviceId) {
            state = ConnectionState.CONNECTED;
            if (stateListener != null) {
                stateListener.onConnectionStateChanged(state);
            }
        }

        @Override
        public void disconnect() {
            state = ConnectionState.DISCONNECTED;
            if (stateListener != null) {
                stateListener.onConnectionStateChanged(state);
            }
        }
    }

    static class FakeFileTransferRepository implements FileTransferRepository {
        private TransferStatusListener statusListener;
        private TransferProgressListener progressListener;

        @Override
        public void observeTransferProgress(TransferProgressListener listener) {
            this.progressListener = listener;
            listener.onProgressUpdated(new TransferProgress(0L, 100L, 0.0, 0f));
        }

        @Override
        public void removeTransferProgressObserver(TransferProgressListener listener) {
            this.progressListener = null;
        }

        @Override
        public void observeTransferStatus(TransferStatusListener listener) {
            this.statusListener = listener;
            listener.onStatusUpdated(new TransferStatusUpdate(TransferExecutionStatus.IDLE, "Idle"));
        }

        @Override
        public void removeTransferStatusObserver(TransferStatusListener listener) {
            this.statusListener = null;
        }

        @Override
        public void sendFile(String sourcePath, String destinationAddress) {
            sendFiles(Collections.singletonList(sourcePath), destinationAddress);
        }

        @Override
        public void sendFiles(List<String> sourcePaths, String destinationAddress) {
            if (statusListener != null) {
                statusListener.onStatusUpdated(new TransferStatusUpdate(TransferExecutionStatus.SENDING, "Sending"));
            }
            if (progressListener != null) {
                progressListener.onProgressUpdated(new TransferProgress(100L, 100L, 1024 * 1024, 100f));
            }
            if (statusListener != null) {
                statusListener.onStatusUpdated(new TransferStatusUpdate(TransferExecutionStatus.COMPLETED, "Done"));
            }
        }

        @Override
        public void receiveFile(String destinationPath) {
            receiveFiles(destinationPath);
        }

        @Override
        public void receiveFiles(String destinationDirectoryPath) {
            if (statusListener != null) {
                statusListener.onStatusUpdated(new TransferStatusUpdate(TransferExecutionStatus.RECEIVING, "Waiting"));
            }
        }

        @Override
        public void cancelTransfer() {
            if (statusListener != null) {
                statusListener.onStatusUpdated(new TransferStatusUpdate(TransferExecutionStatus.FAILED, "Cancelled"));
            }
        }

        @Override
        public void resumeLastTransfer() {
            if (statusListener != null) {
                statusListener.onStatusUpdated(new TransferStatusUpdate(TransferExecutionStatus.RETRYING, "Resumed"));
            }
        }
    }

    static class FakeTransferHistoryRepository implements TransferHistoryRepository {
        private final List<TransferRecord> records = new ArrayList<>();
        private TransferHistoryListener listener;

        @Override
        public List<TransferRecord> getTransferHistory() {
            return new ArrayList<>(records);
        }

        @Override
        public void saveTransferRecord(TransferRecord record) {
            records.add(0, record);
            if (listener != null) {
                listener.onHistoryChanged(new ArrayList<>(records));
            }
        }

        @Override
        public void observeTransferHistory(TransferHistoryListener listener) {
            this.listener = listener;
            listener.onHistoryChanged(new ArrayList<>(records));
        }

        @Override
        public void removeTransferHistoryObserver(TransferHistoryListener listener) {
            this.listener = null;
        }

        void pushSampleRecord() {
            saveTransferRecord(new TransferRecord(
                    1L,
                    "sample.txt",
                    1024L,
                    "text/plain",
                    TransferDirection.SENT,
                    TransferStatus.COMPLETED,
                    System.currentTimeMillis()
            ));
        }
    }
}
