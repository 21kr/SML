04.10 7:44 AM
AGENTS.md
AGENTS.md
Project Overview
Build a peer-to-peer Android file sharing application similar to ShareIt.
- App name: SML
- Package name: "com.mrp.sml"
- Platform: Android
- Language: Kotlin
The app enables devices to transfer files directly over a local hotspot connection (WiFi Direct or hotspot + TCP sockets), without internet.
---
Agent Instructions (IMPORTANT)
- Always follow this file before making decisions
- Follow "TODO.md" strictly in order
- Do NOT skip phases
- Do NOT implement features before project setup
- Always ensure the project builds successfully before proceeding
- Create a Pull Request after completing each phase
---
Tech Stack
- Kotlin
- Jetpack Compose
- MVVM Architecture
- Room Database
- Coroutines + Flow
- Hilt (Dependency Injection)
---
Architecture Rules
Clean Architecture (Strict)
Presentation Layer
- Jetpack Compose UI
- ViewModels
- StateFlow for UI state
Domain Layer
- Use cases only
- No Android dependencies
Data Layer
- Repository implementations
- Room database
- Network (WiFi Direct / sockets)
---
Module Structure
- "app/"
- "core/"
- "data/"
- "domain/"
- "feature_connection/"
- "feature_transfer/"
- "feature_history/"
---
Networking Rules
Preferred
- WiFi Direct (P2P)
Fallback
- Hotspot + TCP sockets
Requirements
- Use coroutines for background work
- Use buffered streams (DO NOT load full file into memory)
- Support large files (>1GB)
- Handle disconnections safely
---
UI Rules
- Use Jetpack Compose ONLY (no XML)
- Follow Material 3
- Support dark mode
---
Permissions
- Nearby devices / WiFi
- Storage access
- Location (for WiFi Direct)
---
Coding Standards
- Follow Kotlin conventions
- No business logic in UI
- Use repository pattern strictly
- Use coroutines for async work
---
Testing Rules
- Unit tests for ViewModels
- Test use cases independently
- Mock repositories
---
Build Rules
- Project must compile successfully after each phase
- Fix all build errors before continuing
- Use Gradle Kotlin DSL ("build.gradle.kts")
---
Definition of Done
- Devices connect locally
- Files transfer successfully
- Progress is accurate
- History is stored
- No crashes for large files

