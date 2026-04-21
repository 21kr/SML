# PROMPT_TEMPLATE.md

## Purpose

Reusable prompt to ensure Codex follows all project rules and architecture.

---

## Base Prompt

Task:
[DESCRIBE THE TASK CLEARLY]

---

## Context (MANDATORY)

Before doing anything, read and understand these files:

* AGENTS.md
* ARCHITECTURE.md
* AI_CONTRACT.md
* DECISIONS.md
* BUILD.md
* CONSTRAINTS.md
* TODO.md

These files define ALL rules and MUST be followed strictly.

---

## Execution Steps (MANDATORY)

Step 1: Read Files

* Read all listed control files
* Do NOT skip any

Step 2: Extract Rules

* Summarize:

  * Architecture constraints
  * Build rules
  * Performance constraints
  * Coding restrictions

Step 3: Plan Implementation

* Explain how the solution will:

  * Follow Clean Architecture
  * Respect layer boundaries
  * Handle large files safely
  * Avoid main thread blocking

Step 4: Validate Plan

* Ensure plan does NOT violate:

  * ARCHITECTURE.md
  * CONSTRAINTS.md
  * BUILD.md

Step 5: Implement

* Write code following all rules

Step 6: Build Validation

* Run: `./gradlew build`
* Fix ALL errors until build SUCCESSFUL

---

## Constraints (MANDATORY)

* Use Java ONLY
* Use XML layouts ONLY
* Follow MVVM strictly
* Use Repository pattern
* Use buffered streams for file transfer
* Support files >1GB
* NO business logic in UI
* NO blocking main thread

---

## Output Rules

* Return ONLY modified or new files
* Do NOT include unrelated code
* Provide short explanation after code

---

## Hard Stop Conditions

STOP immediately if:

* Build fails
* Architecture is violated
* Memory usage is unsafe
* File transfer logic is incorrect

Fix issues BEFORE continuing.

---

## Final Rule

Do NOT guess.
Do NOT assume.
Follow the control files strictly.

If unsure → ASK before implementing.
