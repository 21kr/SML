04.10 7:44 AM
AGENTS.md
AGENTS.md
Project Overview

Build a peer-to-peer Android file sharing application similar to ShareIt.

- App name: SML
- Package name: "com.mrp.sml"
- Platform: Android
- Language: Java
- UI Framework: XML Layouts

The app enables devices to transfer files directly over a local hotspot connection (WiFi Direct or hotspot + TCP sockets), without internet.

---

Agent Instructions (IMPORTANT)

- Always follow this file before making decisions
- Follow "TODO.md" strictly in order
- Do NOT skip phases
- Do NOT implement features before project setup
- Always ensure the project builds successfully before proceeding

---

Tech Stack

- Java
- XML Layouts
- MVVM Architecture
- Room Database
- RxJava / Executors + Handlers for async
- Hilt (Dependency Injection)
- Gradle Groovy DSL

---

Architecture Rules

Clean Architecture (Strict)

**Presentation Layer**
- XML Layouts with Activities/Fragments
- ViewModels
- LiveData for UI state

**Domain Layer**
- Use cases only
- No Android dependencies
- Pure Java

**Data Layer**
- Repository implementations
- Room database
- Network (WiFi Direct / sockets)

---

Module Structure

- app/
- core/
- data/
- domain/
- feature:connection/ (future)
- feature:transfer/ (future)
- feature:history/ (future)

---

Networking Rules

**Preferred**
- WiFi Direct (P2P)

**Fallback**
- Hotspot + TCP sockets

**Requirements**
- Use Executors / AsyncTask for background work
- Use buffered streams (DO NOT load full file into memory)
- Support large files (>1GB)
- Handle disconnections safely

---

UI Rules

- Use XML layouts ONLY (ViewBinding / DataBinding)
- Follow Material Design guidelines
- Support dark mode (themes.xml)
- Responsive layouts

---

Permissions

- Nearby devices / WiFi
- Storage access
- Location (for WiFi Direct)

---

Coding Standards

- Follow Java conventions (Google Java Style)
- No business logic in UI (Activities/Fragments)
- Use repository pattern strictly
- Use LiveData / RxJava for async updates

---

Testing Rules

- Unit tests for ViewModels (JUnit + Mockito)
- Test use cases independently
- Mock repositories

---

Build Rules

- Project must compile successfully after each phase (`./gradlew build`)
- Fix all build errors before continuing
- Use Gradle Groovy DSL ("build.gradle")

---

Definition of Done

- Devices connect locally
- Files transfer successfully
- Progress is accurate (LiveData updates)
- History is stored (Room)
- No crashes for large files

