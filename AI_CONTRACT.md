# AI_CONTRACT.md

## Purpose

Defines strict behavioral rules that Codex MUST follow when modifying or generating code.

These rules override default AI behavior.

---

## Priority Rules (MANDATORY)

Codex MUST always follow:

1. ARCHITECTURE.md
2. BUILD.md
3. CONSTRAINTS.md
4. DECISIONS.md
5. TODO.md

If conflicts occur, follow this priority order.

---

## Task Execution Rules

* Follow TODO.md strictly in order
* Do NOT skip phases
* Do NOT implement future features early
* Complete current phase before moving on

---

## Code Modification Rules

* Do NOT modify existing files unless explicitly required
* Do NOT rename classes, methods, or packages
* Do NOT remove existing functionality
* Do NOT refactor unrelated code

---

## File Creation Rules

* Only create files when necessary
* Do NOT generate duplicate or unused files
* Follow existing project structure strictly

---

## Architecture Enforcement

* MUST follow Clean Architecture
* MUST follow MVVM pattern
* MUST respect layer boundaries

### STRICTLY FORBIDDEN:

* Moving logic across layers
* Adding business logic to UI
* Accessing DB/network from UI

---

## Technology Restrictions

* Language: Java ONLY
* UI: XML layouts ONLY
* DO NOT use:

  * Kotlin
  * Jetpack Compose
  * Alternative frameworks

---

## Networking Rules

* Use TCP sockets for file transfer
* Use WiFi Direct for discovery
* MUST use buffered streams
* MUST support large files (>1GB)

---

## Build Enforcement Rules

* MUST run `./gradlew build` after every implementation
* MUST fix all build errors before continuing
* MUST NOT proceed if build fails

---

## Output Rules

When generating code:

* Return ONLY modified or new files
* Do NOT output unrelated files
* Include explanation after code
* Keep responses focused and minimal

---

## Code Quality Rules

* Follow Google Java Style
* Use meaningful naming
* Avoid duplicate logic
* Keep functions reasonably small (<60 lines recommended)

---

## Error Handling Rules

* Always handle exceptions (try-catch)
* Do NOT allow silent failures
* Provide meaningful error messages

---

## Safety Rules

* Do NOT hallucinate APIs, classes, or libraries
* Use only valid Android/Java APIs
* If unsure → ASK instead of guessing

---

## Testing Rules

* Ensure ViewModels are testable
* Do NOT introduce Android dependencies into Domain layer
* Use dependency injection (Hilt)

---

## Hard Stop Conditions

Codex MUST STOP and FIX if:

* Build fails
* Architecture rules are violated
* App crashes on startup
* File transfer is unsafe or broken

---

## Behavior Under Uncertainty

If unclear:

1. Choose safest implementation
2. Choose most maintainable approach
3. Avoid assumptions
4. Ask for clarification if needed

---

## Final Rule

Codex is NOT allowed to:

* Improvise architecture
* Introduce new patterns
* Break existing system design

Codex MUST behave as a disciplined engineer following strict project rules.
