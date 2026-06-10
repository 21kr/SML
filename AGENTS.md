# SML Project - Instructions for AI Coding Agents

## Build & Verification

- **Build (all modules):** `./gradlew assembleDebug`
- **Lint:** `./gradlew lint`
- **Tests:** `./gradlew test`
- **Single module:** `./gradlew :app:assembleDebug`
- **Clean build:** `./gradlew clean assembleDebug`

## Key Architecture

- **DI:** Dagger Hilt (`@HiltAndroidApp`, `@AndroidEntryPoint`, `@Inject`, `@Module`, `@Provides`, `@Binds`)
- **Navigation:** Jetpack Compose Navigation (`NavHost`, `composable`, `Screen` sealed class in `ui/navigation/Screens.kt`)
- **UI State:** `StateFlow` in ViewModels, collected with `collectAsStateWithLifecycle()`
- **Networking:** TCP sockets (`ServerSocket` / `Socket`) via `FileReceiver`, `FileSender`, `SocketTransferManager`
- **Discovery:** WiFi Direct (`WifiDirectManager`), NFC (`NfcManager`), QR codes (`QrCodeUtils`)
- **Hotspot:** `LocalOnlyHotspot` (API 26+) / `WifiNetworkSpecifier` (API 29+) via `HotspotManager`
- **QR Scanning:** CameraX + ZXing (`QrScannerScreen`)

## Important Conventions

- No `*.md` files except README/PROJECT_ANALYSIS/AGENTS.md — don't create documentation files
- No manual IP entry anywhere; no Bluetooth pairing mode
- Receiver-as-server: receiver opens `ServerSocket` and shows QR, sender connects as TCP client
- `FileSender.sendFiles(destinationAddress)` — non-null = client mode, null = server mode
- `FileReceiver.listenForFiles()` — open `ServerSocket` and wait for incoming connection
- Always run `./gradlew assembleDebug` before committing to verify code compiles

## Pre-existing Issues (do not fix)

These errors exist in the codebase before any changes:
- `WifiDirectManager.kt:233` — unresolved reference `address`
- `QrDisplayScreen.kt:16,130,137,148,158,172,194` — unresolved `Copy`, `fillMaxWidth`, `Context`
