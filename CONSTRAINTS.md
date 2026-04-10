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
- Do NOT use XML layouts (Compose only)
- Do NOT block the main thread
---
⚡ Performance Constraints
- MUST support file transfers larger than 1GB
- MUST use buffered streams (InputStream / OutputStream)
- MUST NOT load entire file into memory
- Use chunk-based file transfer
---
 Networking Constraints
WiFi Direct (Preferred)
- Must be implemented for device discovery
- Handle connection lifecycle properly
Socket Communication (Fallback)
- Use TCP sockets
- Use background threads (coroutines)
- Handle:
  - Connection timeout
  - Disconnections
  - Retry logic
---
🧠 Memory Constraints
- Avoid memory leaks
- Do not keep large file references in memory
- Stream files directly from storage
---
 Concurrency Rules
- Use Kotlin Coroutines
- Use Dispatchers.IO for file operations
- Use StateFlow / Flow for UI updates
- Avoid blocking calls
---
🧱 Architecture Constraints
MUST follow:
- MVVM pattern
- Repository pattern
- Clean Architecture separation
MUST NOT:
- Access database directly from UI
- Access network directly from UI
- Put business logic inside Activity/Composable
---
 UI Constraints
- Use Jetpack Compose ONLY
- Follow Material 3 guidelines
- Support dark mode
- UI must react to StateFlow changes
---
 File Handling Constraints
- Support:
  - Images
  - Videos
  - Documents
  - APK files
- Validate file existence before sending
- Handle file read/write errors safely
---
 Safety Constraints
- Prevent crashes from invalid file paths
- Validate network connections before transfer
- Handle permission denial gracefully
---
🧪 Testing Constraints
- ViewModels must be testable
- Use dependency injection for testability
- Mock repositories in tests
---
 Failure Handling Rules
- Always handle exceptions
- Do not allow silent failures
- Provide meaningful error messages
---
✅ Code Quality Rules
- Follow Kotlin conventions
- Write clean, modular code
- Use meaningful naming
- Avoid duplicate logic
---
🛑 Hard Stop Conditions
Codex MUST stop and fix issues if:
- Project does not compile
- File transfer crashes
- Memory usage becomes unsafe
- Architecture rules are violated
---
Final Rule
If unsure:
- Choose the safest
- Choose the most maintainable
- Choose the most scalable solution

