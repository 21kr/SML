# TODO.md

## Purpose

Defines the execution plan for the project.

All phases MUST be completed in order.
A phase is ONLY complete if all validation steps pass.

---

## ⚠️ Phase 0: Environment Setup (BLOCKING)

* [ ] Install Android SDK
* [ ] Configure `local.properties` (sdk.dir)
* [ ] Ensure JDK 21 is installed
* [ ] Setup emulator or physical device

### Validation (MANDATORY)

* [ ] `./gradlew build` → SUCCESS
* [ ] App launches on emulator/device

🚫 DO NOT PROCEED until this phase passes

---

## Phase 1: Project Setup (Java/XML)

* [x] Convert `.kts` → `.gradle`
* [x] Migrate `kotlin/` → `java/`
* [x] Replace Compose with XML Activities/Fragments
* [x] Enable ViewBinding/DataBinding
* [x] Update AndroidManifest for Java classes

### Validation

* [ ] `./gradlew build` → SUCCESS
* [ ] No compilation errors

---

## Phase 2: Core Modules

* [x] Setup `core/`, `data/`, `domain/`
* [x] Implement base repository structure
* [x] Setup Room database
* [x] Configure Hilt (DI)

### Validation

* [ ] `./gradlew build` → SUCCESS
* [ ] No dependency issues

---

## Phase 3: Presentation Layer (MVVM)

* [x] Implement ViewModels (Java)
* [x] Setup LiveData state management
* [x] Connect XML UI with ViewModel
* [x] Ensure UI contains no business logic

### Validation

* [ ] `./gradlew build` → SUCCESS
* [ ] App launches without crash

---

## Phase 4: Feature Modules (Baseline)

* [x] Implement `feature:connection`
* [x] Implement `feature:transfer`
* [x] Implement `feature:history`

### Validation

* [ ] `./gradlew build` → SUCCESS
* [ ] Basic navigation works

---

## Phase 5: Stability and Tests

* [x] Add unit tests for ViewModels
* [x] Implement retry/error handling UX

### Validation

* [ ] `./gradlew test` → PASS
* [ ] `./gradlew build` → SUCCESS

---

## Phase 6: Integration Hardening

* [x] Add instrumentation test (smoke)
* [x] Implement runtime permissions UX

### Validation

* [ ] App runs without permission crashes
* [ ] `./gradlew build` → SUCCESS

---

## Phase 7: WiFi Direct Hardening

* [x] Implement peer discovery UI
* [x] Add connect/disconnect lifecycle handling

### Validation

* [ ] Devices can discover each other
* [ ] Connection lifecycle stable

---

## Phase 8: Transfer UX Refinement

* [x] Integrate file picker
* [x] Add transfer history UI screen

### Validation

* [ ] File selection works
* [ ] History displays correctly

---

## Phase 9: Real Networking Integration

* [x] Replace mock peer list with real WiFi Direct callbacks
* [x] Implement socket transfer controls (cancel/resume)

### Validation

* [ ] Real device connection works
* [ ] File transfer completes successfully

---

## Phase 10: Production Readiness

* [x] Add permission denial recovery UX
* [x] Add emulator-based integration tests

### Validation

* [ ] App handles denied permissions safely
* [ ] No crash on startup
* [ ] `./gradlew build` → SUCCESS

---

## Phase 11: Release Quality

* [ ] Add crash/error analytics hooks
* [ ] Create end-to-end transfer test plan

### Validation

* [ ] Large file transfer (>1GB) works
* [ ] No crash during transfer
* [ ] Stable user experience

---

## Global Rules

* NEVER skip phases
* NEVER mark complete without validation
* ALWAYS fix build errors before continuing
* ALWAYS follow all control files

---

## Final Rule

If any validation fails:

→ STOP
→ FIX
→ RE-VALIDATE

Progress is ONLY valid if validated.
