04.10 7:44 AM
TODO.md
TODO.md
Execution Rules (MANDATORY)
- Always follow tasks in order
- Do NOT skip phases
- Do NOT implement features before Phase 1 is complete
- Ensure project builds successfully after each phase
- Fix all errors before moving forward
- Create a Pull Request after completing each phase
---
Phase 1: Project Initialization (CRITICAL FIRST STEP)
- [x] Create Android project with:
  - Package name: "com.mrp.sml"
  - Language: Kotlin
  - Minimum SDK: 24
  - Target SDK: 35
  - Compile SDK: 35
- [x] Generate project files:
  - settings.gradle.kts
  - root build.gradle.kts
  - gradle.properties
  - app/build.gradle.kts
- [x] Setup Jetpack Compose
- [x] Setup Material 3
- [x] Configure Hilt dependency injection
- [x] Create base structure:
  - MainActivity.kt
  - Application class (Hilt enabled)
  - Basic Compose UI (Hello Screen)
- [x] Verify project builds successfully using Gradle
⚠️ STOP after this phase and create a PR
---
Phase 2: Core Architecture Setup
- [x] Create modules:
  - core
  - data
  - domain
- [x] Setup clean architecture structure
- [x] Define base interfaces for repositories
- [x] Setup dependency injection across modules
- [x] Verify build success
---
Phase 3: Device Connection (WiFi Direct)
- [x] Implement WiFi Direct broadcast receiver
- [x] Discover nearby devices
- [x] Connect to selected device
- [x] Handle connection states:
  - Connected
  - Failed
  - Disconnected
- [x] Verify connection works between devices ✓
---
Phase 4: File Transfer Engine
- [ ] Implement TCP server (receiver)
- [ ] Implement TCP client (sender)
- [ ] Send single file
- [ ] Support multiple file transfers
- [ ] Implement:
  - Buffered streams
  - Progress tracking (percentage)
  - Speed calculation (MB/s)
- [ ] Handle large files (>1GB)
---
Phase 5: File Picker UI
- [ ] Build file browser using Compose
- [ ] Allow multi-file selection
- [ ] Display:
  - File name
  - File size
  - File type
---
Phase 6: Transfer UI
- [ ] Create transfer progress screen
- [ ] Display:
  - Progress bar
  - Transfer speed
  - Status (sending/receiving/completed)
---
Phase 7: Database (Room)
- [ ] Create TransferEntity
- [ ] Setup Room database
- [ ] Save transfer history
- [ ] Retrieve and display history
---
Phase 8: Error Handling
- [ ] Handle connection drops
- [ ] Retry failed transfers
- [ ] Show user-friendly error messages
---
Phase 9: Optimization
- [ ] Optimize transfer performance
- [ ] Improve memory usage
- [ ] Ensure stability for large files
---
Completion Criteria
- App builds successfully
- Devices can connect locally
- Files transfer correctly
- UI updates in real-time
- History is stored and displayed
- No crashes during large transfers
---

