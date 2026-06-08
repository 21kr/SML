package com.mrp.sml.ui;

import com.mrp.sml.domain.model.DeviceInfo;
import com.mrp.sml.domain.model.FileMetadata;
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
        private DeviceInfo connectedDeviceInfo;

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
            connectedDeviceInfo = null;
            if (stateListener != null) {
                stateListener.onConnectionStateChanged(state);
            }
        }

        @Override
        public void performHandshake(String deviceAddress) {
            state = ConnectionState.PAIRED;
            connectedDeviceInfo = new DeviceInfo("RemoteDevice", "remote-id", "1.0");
            if (stateListener != null) {
                stateListener.onConnectionStateChanged(state);
            }
        }

        @Override
        public void setDeviceInfo(DeviceInfo deviceInfo) {
        }

        @Override
        public DeviceInfo getConnectedDeviceInfo() {
            return connectedDeviceInfo;
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
        public void sendFiles(List<String> sourcePaths, String destinationAddress, String sessionToken) {
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
        public void receiveFiles(String destinationDirectoryPath, String sessionToken) {
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

        @Override
        public void sendMetadata(List<String> sourcePaths, String destinationAddress, String sessionToken) {
        }

        @Override
        public FileMetadata receiveMetadata(String sessionToken) {
            return null;
        }

        @Override
        public void acceptTransfer(String sessionToken) {
        }

        @Override
        public void rejectTransfer(String sessionToken) {
        }

        @Override
        public void sendChunk(String filePath, String destinationAddress, int chunkIndex, int chunkSize, String sessionToken) {
        }

        @Override
        public boolean receiveChunkAck(String sessionToken, int chunkIndex) {
            return false;
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
