# SML File Share — Project Analysis

## Overview

**SML File Share** is a peer-to-peer Android file sharing app (like ShareIt) that uses WiFi Direct, Nearby Connections, and TCP sockets for offline file transfers between devices. It has a Compose UI, MVVM architecture with Clean Architecture layers, Room DB, Hilt DI, and AES-256 encrypted chunked transfers.

- **Package:** `com.mrp.sml`
- **Platform:** Android
- **Language:** Kotlin (app module) + Java (core/data/domain modules)
- **UI:** Jetpack Compose (no XML layouts exist despite README claims)
- **Architecture:** MVVM + Clean Architecture (presentation, domain, data layers)

---

## Current State

The project is caught in an **awkward transitional state**. The `TODO.md` and `README.md` describe a Java/XML migration that was never executed. There are **two parallel codebases** living side-by-side:

| Module | Language | Status |
|---|---|---|
| `app/` | Kotlin + Compose | Fully built with 9 screens, ViewModels, DI, services |
| `core/` | Java | Compiled as AAR, but not wired into app DI |
| `data/` | Java | Syntax error in build.gradle; dead code |
| `domain/` | Java | Pure interfaces; shadowed by Kotlin equivalents in `app/` |

### What works (Kotlin stack)
- 9 Compose screens (Splash, Permissions, Home, Send, Receive, Discovery, Transfer, TransferDetail, History, Settings)
- Full NavHost navigation with argument passing
- 8 ViewModels with Hilt injection and StateFlow
- WiFi Direct peer discovery and connection
- Google Nearby Connections as fallback
- Chunked AES-256-GCM encrypted file transfer via TCP sockets
- Room DB for transfer history (Flow-based DAO)
- DataStore for preferences
- Foreground service with wake lock and notifications
- WorkManager workers (CleanupWorker, RetryTransferWorker)

### What's broken or missing

| Area | Issue |
|---|---|
| **Pause/Resume** | `pauseTransfer()` calls `cancel()` on sockets — not a true pause. `resumeTransfer()` branches are empty. |
| **QR Code** | Only stores a payload string; no QR generation or camera scanning. |
| **SaveSettingsUseCase** | Empty stub — does nothing. |
| **Transfer ID mismatch** | Room uses auto-generated Long IDs, domain uses UUID strings — `toLongOrNull()` always returns null. |
| **RetryTransferWorker** | Empty branches for SENT/RECEIVED — does nothing. |
| **Architecture** | ViewModels bypass repository layer (e.g., `DiscoveryViewModel` → `DeviceDiscoveryManager` directly). |
| **SocketTransferManager** | Instantiated as both singleton and fresh instance in `FileSender` — progress on the wrong instance is never observed. |
| **JSON serialization** | `FileSender.buildMetadata` uses manual string concatenation — fragile with special chars in filenames. |
| **Dependency versions** | Hilt 2.48 vs 2.57, Room 2.6.1 vs 2.8.4 across modules. |
| **data/build.gradle** | Syntax error in `buildFeatures` block. |
| **Version catalog** | `gradle/libs.versions.toml` is empty but present. |
| **No tests** | Zero unit or instrumentation tests despite TODO claiming otherwise. |
| **No APK output** | `app/build/outputs/apk/` does not exist — full build may not succeed. |
| **Security** | `security-crypto:1.1.0-alpha07` is an unstable alpha release. |

---

## Recommended Improvements

### 1. Resolve the Dual Codebase
Decide once: Kotlin/Compose or Java/XML. The code is overwhelmingly Kotlin/Compose. If keeping that direction:
- Delete the Java `core/`, `data/`, `domain/` modules (dead code).
- Update `TODO.md` and `README.md` to reflect reality.

### 2. Fix Build System
- Fix `data/build.gradle` syntax error.
- Align dependency versions across modules.
- Either populate or remove the empty version catalog.

### 3. Fix Broken Features
- Implement true pause/resume for transfers.
- Implement QR code generation and camera scanning.
- Fix transfer ID type mismatch (UUID ↔ Long).
- Implement `SaveSettingsUseCase` and `RetryTransferWorker`.

### 4. Fix Architectural Violations
- Route ViewModels through the repository layer, not directly to managers.
- Fix `SocketTransferManager` singleton vs instance issue.

### 5. Add Tests
- Unit tests for ViewModels, UseCases, Repositories.
- Instrumentation tests for UI flows.
- End-to-end transfer test plan.

### 6. Production Readiness
- Add crash analytics (Firebase Crashlytics / Sentry).
- Replace `security-crypto:1.1.0-alpha07` with a stable release.
- Validate large file (>1GB) transfers.
- Verify APK builds and runs on API 24+.
- Test multi-device discovery and connection in real conditions.

### 7. Features Still Missing
- QR code connection — not implemented.
- End-to-end encryption — partially done (AES-256) but incomplete verification.
- Cross-platform support — Android only.
- Internet-based transfer fallback.
