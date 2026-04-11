SML File Share 📲

A peer-to-peer Android file sharing application that enables fast, offline file transfers between devices using WiFi Direct or hotspot connections.

---

📌 Project Overview

SML File Share is designed to transfer files directly between Android devices without requiring an internet connection.

- Package Name: "com.mrp.sml"
- Platform: Android
- Language: Java
- UI: XML Layouts

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

- Java
- XML Layouts (ViewBinding/DataBinding)
- MVVM Architecture
- Room Database
- RxJava / Executors for async
- Hilt (Dependency Injection)
- Gradle (Groovy DSL)

---

🧱 Architecture

This project follows Clean Architecture principles.

Layers:

**Presentation Layer**
- XML-based Activities/Fragments
- ViewModels
- LiveData for UI state

**Domain Layer**
- Use cases (business logic)
- Pure Java models

**Data Layer**
- Repository implementations
- Room database
- Network handling (WiFi Direct / sockets)

---

📂 File Structure Map

```
SML File Share Project Structure (/workspaces/SML)
├── app/
│   ├── build.gradle (Groovy)
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── kotlin/ → java/ (com/mrp/sml/)
│   │   │   ├── MainActivity.java
│   │   │   ├── SmlApplication.java
│   │   │   └── ui/ (Activities/Fragments/ViewModels)
│   │   └── res/ (XML layouts, drawables, values)
│   └── build/ (outputs)
├── core/ (common utils)
│   ├── build.gradle
│   └── src/main/java/
├── data/ (repositories, Room)
│   ├── build.gradle
│   └── src/main/java/
├── domain/ (use cases, models)
│   ├── build.gradle
│   └── src/main/java/
├── gradle/
│   └── libs.versions.toml
├── build.gradle (root, Groovy)
├── settings.gradle
├── gradle.properties
├── AGENTS.md, README.md, etc.
└── gradlew / gradlew.bat
```

(Note: feature modules like feature_connection/ to be added later)

---

🌐 Networking Strategy

**Primary Method**
- WiFi Direct (Peer-to-Peer)

**Fallback Method**
- Hotspot + TCP socket communication

**Requirements**
- Supports large file transfers (>1GB)
- Uses buffered streams
- Handles connection failures and retries

---

🎨 UI Design

- Material Design with XML layouts
- Dark mode support (themes.xml)
- Responsive layouts

**Main Screens**
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

SDK Configuration Source of Truth: `BUILD.md` (minSdk 24, targetSdk 35, compileSdk 35)

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
