04.10 7:47 AM
CONSTRAINTS.md
CONSTRAINTS.md
Purpose

This file defines strict rules and limitations Codex must follow to ensure performance, stability, and correct architecture.

Codex MUST respect all constraints when implementing features.

---

General Restrictions

- Do NOT skip Clean Architecture layers
- Do NOT mix UI logic with business logic
- Do NOT write everything inside a single file
- Do NOT block the main thread (use background threads)
- Use XML layouts (ViewBinding/DataBinding preferred)

---

⚡ Performance Constraints

- MUST support file transfers larger than 1GB
- MUST use buffered streams (InputStream / OutputStream)
- MUST NOT load entire file into memory
- Use chunk-based file transfer

---

Networking Constraints

**WiFi Direct (Preferred)**
- Must be implemented for device discovery
- Handle connection lifecycle properly

**Socket Communication (Fallback)**
- Use TCP sockets
- Use background threads (ExecutorService / AsyncTask)
- Handle:
  - Connection timeout
  - Disconnections
  - Retry logic (max 3 attempts)

---

🧠 Memory Constraints

- Avoid memory leaks (use WeakReferences if needed)
- Do not keep large file references in memory
- Stream files directly from storage

---

Concurrency Rules

- Use ExecutorService, AsyncTask, or RxJava
- Use IO thread pool for file operations
- Use LiveData / RxJava for UI updates
- Avoid blocking calls on main thread (use Handler)

---

🧱 Architecture Constraints

**MUST follow:**
- MVVM pattern
- Repository pattern
- Clean Architecture separation

**MUST NOT:**
- Access database directly from UI
- Access network directly from UI
- Put business logic inside Activity/Fragment

---

UI Constraints

- Use XML layouts ONLY
- Follow Material Design guidelines (themes.xml)
- Support dark mode
- UI must react to LiveData changes
- Use ViewBinding/DataBinding for efficiency

---

File Handling Constraints

- Support:
  - Images
  - Videos
  - Documents
  - APK files
- Validate file existence before sending
- Handle file read/write errors safely (try-catch)

---

Safety Constraints

- Prevent crashes from invalid file paths
- Validate network connections before transfer
- Handle permission denial gracefully (show dialogs)

---

🧪 Testing Constraints

- ViewModels must be testable (no Android context)
- Use dependency injection (Hilt) for testability
- Mock repositories in tests (Mockito)

---

Failure Handling Rules

- Always handle exceptions (try-catch)
- Do not allow silent failures (log + user message)
- Provide meaningful error messages (Toast/Dialog)

---

✅ Code Quality Rules

- Follow Java conventions (Google Java Format)
- Write clean, modular code
- Use meaningful naming
- Avoid duplicate logic (extract methods)

---

🛑 Hard Stop Conditions

Codex MUST stop and fix issues if:

- Project does not compile (`./gradlew build`)
- File transfer crashes
- Memory usage becomes unsafe
- Architecture rules are violated

---

Final Rule

If unsure:
- Choose the safest option
- Choose the most maintainable
- Choose the most scalable solution

