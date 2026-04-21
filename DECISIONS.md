# DECISIONS.md

## Purpose

This file defines FINAL technical decisions for the project.

These decisions MUST NOT be changed unless explicitly updated here.

Codex MUST respect all decisions.

---

## Language Decision

* Language: Java ONLY

**Reason:**

* Project requirement
* Consistency across modules
* Avoid Kotlin/Java mixing complexity

---

## UI Framework Decision

* UI: XML Layouts ONLY
* Use ViewBinding / DataBinding

**Explicitly NOT allowed:**

* Jetpack Compose

**Reason:**

* Better control over layouts
* Simpler integration with existing architecture

---

## Architecture Decision

* Clean Architecture (STRICT)
* MVVM for Presentation Layer

**Reason:**

* Clear separation of concerns
* Scalable and maintainable structure

---

## Networking Strategy

### Device Discovery

* WiFi Direct (Primary)

### File Transfer

* TCP Sockets

**Reason:**

* Works offline
* High-speed peer-to-peer transfer
* Reliable for large files

---

## File Transfer Strategy

* Stream-based transfer (InputStream / OutputStream)
* Chunk-based processing

**Explicitly NOT allowed:**

* Loading entire file into memory

**Reason:**

* Required for large file support (>1GB)
* Prevent memory crashes

---

## Database Decision

* Room Database

**Reason:**

* Structured local storage
* Easy integration with Android
* Suitable for transfer history

---

## Asynchronous Processing

* ExecutorService / RxJava

**Reason:**

* Control over threading
* Avoid blocking main thread
* Scalable async handling

---

## Dependency Injection

* Hilt (Dagger Hilt)

**Requirements:**

* @HiltAndroidApp in Application class
* Proper module setup

**Reason:**

* Simplifies dependency management
* Improves testability

---

## Build System Decision

* Gradle (Groovy DSL)

**Explicitly NOT allowed:**

* Kotlin DSL (.kts)

**Reason:**

* Project consistency
* Simpler configuration for current setup

---

## Minimum Requirements

* minSdk: 24
* targetSdk: 35
* compileSdk: 35

**Source of truth: BUILD.md**

---

## Testing Strategy

* Unit tests: JUnit + Mockito
* Focus on:

  * ViewModels
  * UseCases

**Reason:**

* Ensures business logic correctness
* Keeps tests independent of Android framework

---

## Error Handling Strategy

* Use try-catch for all IO operations
* Propagate errors to UI via ViewModel
* Show user-friendly messages

**Reason:**

* Prevent crashes
* Improve UX

---

## Performance Strategy

* Use streaming for file transfers
* Use background threads for IO
* Avoid memory-heavy operations

**Reason:**

* Stability during large transfers
* Smooth UI performance

---

## Final Rule

All decisions in this file are FINAL.

Codex MUST NOT:

* Suggest alternatives
* Replace technologies
* Introduce conflicting approaches

If a change is required:
→ Update this file FIRST
→ Then apply changes
