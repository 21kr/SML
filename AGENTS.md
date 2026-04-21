# AGENTS.md

## Project Overview

SML is a peer-to-peer Android file sharing application.

* Package: com.mrp.sml
* Platform: Android
* Language: Java
* UI: XML Layouts

The app transfers files directly between devices using WiFi Direct or hotspot + TCP sockets, without internet.

---

## Execution Rules (MANDATORY)

* Follow `TODO.md` strictly in order
* Do NOT skip phases
* Do NOT implement future features early
* Validate each phase before proceeding
* Always ensure project builds successfully

---

## Control System (MANDATORY)

All tasks MUST follow:

* `ARCHITECTURE.md` → structural rules
* `AI_CONTRACT.md` → behavior rules
* `DECISIONS.md` → locked tech choices
* `BUILD.md` → build + environment validation
* `CONSTRAINTS.md` → runtime and performance limits

If any conflict occurs:

1. BUILD.md overrides execution
2. CONSTRAINTS.md overrides implementation
3. ARCHITECTURE.md overrides structure
4. AI_CONTRACT.md overrides behavior

---

## Build Enforcement

* After EVERY implementation:

  * Run: `./gradlew build`
* If build fails:

  * Fix errors immediately
  * Rebuild until SUCCESS
* NEVER proceed with a failing build

---

## Phase Execution Rules

Each phase MUST include:

* Implementation complete
* Build passes (`./gradlew build`)
* No runtime crash on launch

A phase is NOT complete unless all conditions are satisfied.

---

## Coding Rules (High-Level)

* Follow Clean Architecture strictly
* Use MVVM for UI layer
* No business logic in Activities/Fragments
* Use Repository pattern
* Use LiveData / RxJava for async updates

---

## Task Execution Pattern

For every task:

1. Understand requirements
2. Follow all control files
3. Implement minimal correct solution
4. Run build validation
5. Fix issues if any
6. Mark task complete ONLY if validation passes

---

## Failure Handling

If issues occur:

* Stop implementation
* Identify root cause
* Fix before continuing

DO NOT:

* Ignore errors
* Continue with broken state
* Apply hacks to bypass issues

---

## Definition of Done (GLOBAL)

A feature is complete only if:

* Devices can connect successfully
* File transfer works reliably
* Progress updates correctly
* History is stored and retrievable
* No crashes occur
* Large files (>1GB) are handled safely

---

## Final Rule

If uncertain:

* Do NOT guess
* Do NOT assume
* Follow the safest, most maintainable solution
* Ask or stop instead of proceeding incorrectly
