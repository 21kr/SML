# BUILD.md

## Purpose

Defines build, environment, and validation rules.

This file is the **SOURCE OF TRUTH** for:

* SDK configuration
* Build validation
* Execution readiness

---

## SDK Configuration (FINAL)

* minSdk = 24
* targetSdk = 35
* compileSdk = 35

These values MUST NOT be changed.

---

## Environment Requirements

* JDK 21 installed
* Android SDK installed
* Gradle Wrapper included (`./gradlew`)

---

## Local Configuration

A `local.properties` file MUST exist:

```
sdk.dir=/path/to/android/sdk
```

Examples:

**Windows**

```
sdk.dir=C:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
```

**Mac/Linux**

```
sdk.dir=/Users/yourname/Library/Android/sdk
```

---

## Build System Rules

* Use Gradle Groovy DSL ONLY (`.gradle`)
* Java project ONLY (no Kotlin)
* Use Gradle Wrapper (`./gradlew`) only

---

## Mandatory Build Cycle (STRICT)

After EVERY implementation:

1. Run:

   ```
   ./gradlew build
   ```

2. If build FAILS:

   * Identify ALL errors
   * Fix errors
   * Re-run build

3. Repeat until:

   ```
   BUILD SUCCESSFUL
   ```

4. ONLY proceed when build passes

🚫 Skipping this process is NOT allowed

---

## Clean Build

```
./gradlew clean
```

Use when:

* Dependencies change
* Build cache issues occur

---

## Testing

Run:

```
./gradlew test
```

Rules:

* All tests MUST pass
* Fix failing tests before proceeding

---

## Lint Check

Run:

```
./gradlew lint
```

Rules:

* Fix critical issues
* Do NOT ignore fatal warnings

---

## Dependency Rules

* Use stable versions ONLY
* Must be Java-compatible
* Avoid deprecated APIs
* Do NOT introduce experimental libraries

---

## ViewBinding / DataBinding

* MUST be enabled in Gradle
* Use ViewBinding or DataBinding ONLY
* Jetpack Compose is NOT allowed

---

## Hilt Configuration

MUST include:

* `@HiltAndroidApp` in Application class
* Hilt Gradle plugin applied
* Proper dependency setup

Do NOT:

* Use manual dependency injection

---

## Failure Handling (STRICT)

If build fails repeatedly:

1. Revert last changes
2. Simplify implementation
3. Rebuild incrementally

🚫 Do NOT continue with broken build

---

## Completion Criteria (MANDATORY)

A phase is COMPLETE ONLY IF:

* `./gradlew build` → SUCCESS
* No compilation errors
* No unresolved dependencies
* App launches successfully
* No crash on startup

---

## Runtime Validation

After build success:

* Launch app on emulator/device
* Verify no startup crash
* Validate basic navigation

---

## Hard Stop Rule

If ANY of the following occur:

* Build fails
* App crashes on launch
* Dependencies unresolved

→ STOP
→ FIX
→ REBUILD

---

## Final Rule

Build success is NOT optional.

If it does not build:
→ The feature does NOT exist
