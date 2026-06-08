package com.mrp.sml.ui.pairing;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import com.mrp.sml.domain.usecase.ConnectionUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PairingViewModel extends ViewModel {

    public enum PairingMode { IDLE, SENDING, RECEIVING, PAIRED, TRANSFERRING }

    private final ConnectionUseCase connectionUseCase;

    private final MutableLiveData<PairingMode> pairingMode = new MutableLiveData<>(PairingMode.IDLE);
    private final MutableLiveData<String> statusText = new MutableLiveData<>("Ready");
    private final MutableLiveData<Bitmap> qrCodeBitmap = new MutableLiveData<>(null);
    private final MutableLiveData<String> qrContentText = new MutableLiveData<>("");
    private final MutableLiveData<List<DiscoveredDevice>> discoveredDevices = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> scannedSessionToken = new MutableLiveData<>("");

    private volatile String myIpAddress;
    private volatile String sessionToken;

    private final DeviceConnectionRepository.ConnectionStateListener stateListener =
            state -> {
                switch (state) {
                    case PAIRED:
                        pairingMode.postValue(PairingMode.PAIRED);
                        statusText.postValue("Paired with device");
                        break;
                    case CONNECTED:
                        statusText.postValue("Connected to device");
                        break;
                    case DISCOVERING:
                        statusText.postValue("Discovering devices...");
                        break;
                    case FAILED:
                        statusText.postValue("Connection failed");
                        break;
                    case DISCONNECTED:
                        statusText.postValue("Disconnected");
                        break;
                }
            };

    private final DeviceConnectionRepository.DiscoveredDevicesListener devicesListener =
            devices -> discoveredDevices.postValue(
                    devices == null ? new ArrayList<>() : new ArrayList<>(devices));

    @Inject
    public PairingViewModel(ConnectionUseCase connectionUseCase) {
        this.connectionUseCase = connectionUseCase;
        this.connectionUseCase.observeConnectionState(stateListener);
        this.connectionUseCase.observeDiscoveredDevices(devicesListener);
    }

    public LiveData<PairingMode> getPairingMode() { return pairingMode; }
    public LiveData<String> getStatusText() { return statusText; }
    public LiveData<Bitmap> getQrCodeBitmap() { return qrCodeBitmap; }
    public LiveData<String> getQrContentText() { return qrContentText; }
    public LiveData<List<DiscoveredDevice>> getDiscoveredDevices() { return discoveredDevices; }
    public LiveData<String> getScannedSessionToken() { return scannedSessionToken; }

    public void prepareSenderQr(String localIpAddress) {
        this.myIpAddress = localIpAddress;
        this.sessionToken = "session_" + System.currentTimeMillis();

        pairingMode.postValue(PairingMode.SENDING);

        String qrData = buildQrContent(localIpAddress, sessionToken);
        qrContentText.postValue(qrData);
        generateQrCode(qrData);

        statusText.postValue("Waiting for receiver to connect...");
    }

    public void prepareReceiverScan() {
        this.sessionToken = "session_" + System.currentTimeMillis();
        pairingMode.postValue(PairingMode.RECEIVING);
        statusText.postValue("Scan QR code or discover nearby devices");
    }

    public void onQrScanned(String qrContent) {
        if (qrContent == null || qrContent.isEmpty()) {
            statusText.postValue("Invalid QR code");
            return;
        }
        String token = parseSessionFromQr(qrContent);
        if (token != null && !token.isEmpty()) {
            scannedSessionToken.postValue(token);
        }

        String deviceAddress = parseIpFromQr(qrContent);
        if (deviceAddress != null && !deviceAddress.isEmpty()) {
            statusText.postValue("Connecting to " + deviceAddress + "...");
            connectionUseCase.connectToDevice(deviceAddress);
        } else {
            statusText.postValue("Connecting to default address...");
            connectionUseCase.discoverDevices();
        }
    }

    public void connectToDevice(String deviceId) {
        if (deviceId == null || deviceId.trim().isEmpty()) {
            statusText.postValue("No device selected");
            return;
        }
        statusText.postValue("Connecting to " + deviceId + "...");
        connectionUseCase.connectToDevice(deviceId.trim());
    }

    public void connectToDevice(DiscoveredDevice device) {
        if (device == null) return;
        connectToDevice(device.getId());
    }

    public void discoverDevices() {
        statusText.postValue("Discovering...");
        connectionUseCase.discoverDevices();
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void updateStatus(String status) {
        statusText.postValue(status);
    }

    public void disconnect() {
        connectionUseCase.disconnect();
    }

    private String buildQrContent(String ipAddress, String token) {
        return "{\"ip\":\"" + (ipAddress == null ? "" : ipAddress)
                + "\",\"port\":8988,\"session\":\"" + (token == null ? "" : token)
                + "\",\"deviceName\":\"SML Device\"}";
    }

    private String parseIpFromQr(String qrContent) {
        String key = "\"ip\":\"";
        int start = qrContent.indexOf(key);
        if (start < 0) return null;
        start += key.length();
        int end = qrContent.indexOf("\"", start);
        if (end < 0) return null;
        String ip = qrContent.substring(start, end);
        return ip.isEmpty() ? null : ip;
    }

    private String parseSessionFromQr(String qrContent) {
        String key = "\"session\":\"";
        int start = qrContent.indexOf(key);
        if (start < 0) return null;
        start += key.length();
        int end = qrContent.indexOf("\"", start);
        if (end < 0) return null;
        String token = qrContent.substring(start, end);
        return token.isEmpty() ? null : token;
    }

    private void generateQrCode(String content) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            int size = 512;
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size);
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bitmap.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrCodeBitmap.postValue(bitmap);
        } catch (WriterException e) {
            statusText.postValue("Failed to generate QR code");
        }
    }

    @Override
    protected void onCleared() {
        connectionUseCase.removeConnectionStateObserver(stateListener);
        connectionUseCase.removeDiscoveredDevicesObserver(devicesListener);
        super.onCleared();
    }
}
