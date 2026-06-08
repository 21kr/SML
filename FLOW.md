# FLOW.md

## Purpose

Defines the official user flow and protocol behavior for the application.

This file is the **SOURCE OF TRUTH** for:
* User journey (sender and receiver)
* Device discovery behavior
* Connection handshake protocol
* File transfer protocol
* Integrity verification
* Security layer

All UI and data-layer implementations MUST follow this flow.

---

## User Journey

### Sender Flow

1. Open app
2. Tap "Send"
3. Select files (images, videos, documents, apps, folders)
4. App creates WiFi Direct group OR local hotspot
5. QR code generated for pairing
6. Wait for receiver to connect
7. Transfer proceeds automatically

### Receiver Flow

1. Open app
2. Tap "Receive"
3. Scan QR code OR auto-discover nearby devices
4. Connect to sender
5. Receive metadata prompt → Accept
6. Transfer proceeds automatically

---

## Device Discovery

### Primary Method: WiFi Direct

```
Sender creates WiFi Direct Group
Receiver scans nearby peers
Receiver connects
IP assigned automatically
```

### Fallback: Local Hotspot

```
Sender starts hotspot
Receiver joins hotspot
App discovers sender
```

### Secondary: Bluetooth Discovery + WiFi Transfer

```
Bluetooth finds devices
Bluetooth exchanges connection info
Actual file transfer uses WiFi
```

---

## Connection Establishment

After discovery, the following handshake MUST occur:

```
Receiver → Sender: HELLO
Sender → Receiver: Device Info
```

Device Info payload:

```json
{
  "deviceName": "John Phone",
  "deviceId": "abc123",
  "version": "1.0"
}
```

---

## File Selection

Sender may select:
* Images
* Videos
* Documents
* APK files
* Folders

App MUST generate metadata for all selected files.

### Metadata Format

```json
{
  "files": [
    {
      "name": "video.mp4",
      "size": 104857600,
      "hash": "sha256..."
    }
  ]
}
```

---

## Transfer Protocol

### Step 1: Send Metadata First

```
Sender → Receiver: Metadata (JSON)
Receiver → Sender: Accept
```

Metadata MUST be transmitted and acknowledged before any file data.

### Step 2: Chunk-Based Transfer

Files MUST be split into fixed-size chunks.

```
Chunk Size: 1 MB (1048576 bytes)
```

```
Sender   → Receiver: Chunk 1
Sender   → Receiver: Chunk 2
Receiver → Sender:   ACK 1
Sender   → Receiver: Chunk 3
Receiver → Sender:   ACK 2
...
```

**Benefits:**
* Resume support
* Error recovery
* Accurate progress tracking

### Step 3: Integrity Verification

After all chunks are transferred:

```
Sender SHA256 =?= Receiver SHA256
```

If match → Transfer Success
If mismatch → Request missing/corrupt chunks

---

## Resume Feature

Transfer progress MUST be persisted to support resume.

```json
{
  "file": "video.mp4",
  "totalSize": 104857600,
  "receivedChunks": [1, 2, 3, 4, 5],
  "chunkSize": 1048576
}
```

On reconnection:
1. Resume from next unacknowledged chunk
2. Verify integrity of previously received chunks if needed

---

## Security Layer

All data transferred between devices MUST be encrypted.

```
Pair Devices → Exchange Keys → Encrypt Data → Transfer
```

* Algorithm: AES-256
* Session key generated per transfer session
* Prevents nearby devices from reading transferred files

---

## Screens

### Home Screen
```
[ Send Files ]
[ Receive Files ]
```

### Send Screen
```
Select Files
5 Files Selected
[ Start ]
```

### Pairing Screen
```
QR Code Display
Waiting for Receiver...
```

### Receiver Screen
```
Scanning...
Found: John Phone
[ Connect ]
```

### Transfer Screen
```
video.mp4
██████████░░░░ 68%
45 MB/s
ETA 3s
```

### Success Screen
```
Transfer Complete
5 Files
1.2 GB
```

---

## Network Protocol Rules

* Use TCP for all data transfer (reliable, ordered)
* Sender IP: 192.168.49.1 (WiFi Direct default)
* Receiver IP: 192.168.49.101 (WiFi Direct default)
* Port: 8988

---

## Advanced Features (Future)

* Pause/Resave transfers
* QR pairing (primary pairing method)
* Transfer history with retry
* Group sharing (1 sender → many receivers)
* Cross-platform support

---

## Implementation Rules

1. Discovery MUST NOT start automatically — only on user tap (Send/Receive)
2. Metadata MUST be transferred before file data
3. All file transfers MUST use chunk-based streaming
4. SHA-256 verification MUST run after transfer
5. AES-256 encryption MUST be applied to all data
6. Resume state MUST be persisted locally
7. QR code MUST be the primary pairing mechanism
8. TCP MUST be used for all data transfer
9. No file data MAY be loaded entirely into memory
10. Progress MUST be reported to UI during transfer
