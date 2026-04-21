# CONSTRAINTS.md

## Purpose

Defines strict system-level constraints for:

* Performance
* Memory
* Networking
* Concurrency
* Runtime safety

These constraints are NON-NEGOTIABLE.

---

## ⚡ Performance Constraints

* MUST support file transfers > 1GB
* MUST use buffered streams (InputStream / OutputStream)
* MUST use chunk-based transfer
* MUST NOT load entire file into memory

---

## 🧠 Memory Constraints

* Do NOT hold large files in memory
* Stream files directly from storage
* Release references after use
* Avoid memory leaks (use WeakReference where appropriate)

---

## 🌐 Networking Constraints

### General

* MUST validate connection before transfer
* MUST handle disconnections safely

### TCP Socket Rules

* Use TCP sockets for file transfer
* MUST run on background threads
* MUST implement:

  * Connection timeout
  * Retry logic (max 3 attempts)

---

## ⚙️ Concurrency Constraints

* No blocking operations on main thread
* File operations MUST run on IO thread
* UI updates MUST occur on main thread
* Use:

  * ExecutorService
  * RxJava
  * Handlers (for UI thread)

---

## 📂 File Handling Constraints

* Validate file existence before sending
* Handle file read/write exceptions (try-catch)
* Supported file types:

  * Images
  * Videos
  * Documents
  * APK files

---

## 🛑 Runtime Safety Constraints

System MUST prevent crashes caused by:

* Invalid file paths
* Network interruptions
* Missing permissions

System MUST:

* Validate inputs
* Catch exceptions
* Provide user-friendly error messages

---

## 🔁 Failure Handling Constraints

* No silent failures
* All errors MUST:

  * Be logged
  * Be propagated to UI

---

## 🛑 Hard Stop Conditions

Implementation MUST STOP and FIX if:

* Build fails (`./gradlew build`)
* App crashes during execution
* File transfer fails repeatedly
* Memory usage becomes unsafe

---

## Trade-off Rules

When trade-offs are required:

1. Stability > Performance
2. Correctness > Speed
3. Maintainability > Complexity

---

## Final Rule

If any implementation violates these constraints:

→ STOP
→ FIX
→ DO NOT proceed
