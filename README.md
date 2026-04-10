SML File Share 📲

A peer-to-peer Android file sharing application that enables fast, offline file transfers between devices using WiFi Direct or hotspot connections.

---

📌 Project Overview

SML File Share is designed to transfer files directly between Android devices without requiring an internet connection.

- Package Name: "com.mrp.sml"
- Platform: Android
- Language: Kotlin

The app uses local network communication (WiFi Direct or hotspot + TCP sockets) to achieve high-speed transfers similar to ShareIt.

---

🚀 Core Features

🔗 Device Connection

- Discover nearby devices using WiFi Direct
- Connect via hotspot or direct peer-to-peer
- Manual IP connection fallback

📁 File Transfer

- Send and receive:
  
    - Images
      - Videos
        - Documents
          - APK files

          - Real-time transfer information:
            
              - Progress (%)
                - Speed (MB/s)
                  - Status (sending, receiving, completed, failed)

                  📂 File Manager

                  - Browse device storage
                  - Select single or multiple files
                  - View file metadata

                  🕓 Transfer History

                  - Store transfer logs locally using Room Database
                  - View previous transfers

                  ---

                  🛠 Tech Stack

                  - Kotlin
                  - Jetpack Compose
                  - MVVM Architecture
                  - Room Database
                  - Coroutines + Flow
                  - Hilt (Dependency Injection)

                  ---

                  🧱 Architecture

                  This project follows Clean Architecture principles.

                  Layers

                  Presentation Layer

                  - Jetpack Compose UI
                  - ViewModels
                  - StateFlow for UI state

                  Domain Layer

                  - Use cases (business logic)
                  - Pure Kotlin models

                  Data Layer

                  - Repository implementations
                  - Room database
                  - Network handling (WiFi Direct / sockets)

                  ---

                  📦 Project Structure

                  app/
                  core/
                  data/
                  domain/
                  feature_connection/
                  feature_transfer/
                  feature_history/

                  ---

                  🌐 Networking Strategy

                  Primary Method

                  - WiFi Direct (Peer-to-Peer)

                  Fallback Method

                  - Hotspot + TCP socket communication

                  Requirements

                  - Supports large file transfers (>1GB)
                  - Uses buffered streams
                  - Handles connection failures and retries

                  ---

                  🎨 UI Design

                  - Material 3 design
                  - Dark mode support
                  - Responsive layouts

                  Main Screens

                  - Home (Send / Receive)
                  - Device Discovery
                  - File Picker
                  - Transfer Progress
                  - Transfer History

                  ---

                  🔐 Permissions Required

                  - Nearby devices / WiFi access
                  - Storage access
                  - Location (required for WiFi Direct)

                  ---

                  ⚙️ Build & Run

                  Follow instructions in:

                  👉 "BUILD.md"

                  ---

                  📋 Development Workflow

                  Codex and contributors must follow:

                  1. "AGENTS.md" → Rules and architecture
                  2. "TODO.md" → Task execution order
                  3. "CONSTRAINTS.md" → Implementation guardrails
                  4. "BUILD.md" → Build and validation process
                  - SDK Configuration Source of Truth: `BUILD.md` (minSdk 24, targetSdk 35, compileSdk 35)

                  ---

                  ✅ Definition of Done

                  - Devices connect successfully
                  - Files transfer without errors
                  - Progress updates in real-time
                  - Transfer history is stored and retrievable
                  - App handles large files reliably
                  - No crashes during operation

                  ---

                  🔮 Future Improvements

                  - QR code-based connection
                  - Internet-based transfer
                  - Cross-platform support
                  - End-to-end encryption

                  ---

                  📄 License

                  This project is intended for educational and development purposes.

                  ---

                  👨‍💻 Author

                  Developed by MRP
