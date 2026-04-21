# ARCHITECTURE.md

## Architecture Style (MANDATORY)

* Clean Architecture (STRICT)
* MVVM pattern in Presentation Layer

This structure MUST NOT be changed.

---

## Layer Definitions

### 1. Presentation Layer (app module)

**Contains:**

* Activities
* Fragments
* ViewModels
* XML Layouts (ViewBinding/DataBinding)

**Responsibilities:**

* Handle UI rendering
* Observe LiveData
* Forward user actions to ViewModel

**MUST:**

* Use ViewModel for state management
* Use LiveData for UI updates

**MUST NOT:**

* Contain business logic
* Access database directly
* Access network directly
* Perform file operations

---

### 2. Domain Layer

**Contains:**

* UseCases (business logic)
* Domain models (pure Java)

**Responsibilities:**

* Execute application logic
* Coordinate data between layers

**MUST:**

* Be pure Java (NO Android dependencies)
* Be independent of frameworks

**MUST NOT:**

* Import Android SDK classes
* Access UI components
* Handle database or network directly

---

### 3. Data Layer

**Contains:**

* Repository implementations
* Room database (DAOs, Entities)
* Network layer (WiFi Direct, TCP sockets)

**Responsibilities:**

* Handle all data sources
* Manage local storage and network communication

**MUST:**

* Implement repository interfaces from Domain layer
* Handle IO operations

**MUST NOT:**

* Contain UI logic
* Expose framework-specific models to Domain

---

## Data Flow (STRICT)

UI → ViewModel → UseCase → Repository → Data Source

Reverse flow for results:

Data Source → Repository → UseCase → ViewModel → UI

Violation of this flow is NOT allowed.

---

## Module Structure

* `app/` → Presentation Layer
* `domain/` → Business logic
* `data/` → Data handling
* `core/` → Shared utilities

Future modules:

* `feature:connection/`
* `feature:transfer/`
* `feature:history/`

---

## Dependency Rules

* app → depends on domain + data
* data → depends on domain
* domain → depends on NOTHING

No circular dependencies allowed.

---

## Networking Architecture

**Discovery Layer**

* WiFi Direct (primary)

**Transfer Layer**

* TCP sockets

**Rules:**

* Networking handled ONLY in Data layer
* UI must interact via ViewModel → UseCase

---

## File Transfer Architecture

* Use streaming (InputStream / OutputStream)
* Chunk-based transfer
* No full file loading in memory

Handled ONLY in Data layer.

---

## Threading Model

* UI thread → UI rendering only
* Background threads → all IO operations

**Allowed tools:**

* ExecutorService
* RxJava

**MUST NOT:**

* Block main thread
* Perform network/file IO on UI thread

---

## State Management

* ViewModel holds UI state
* LiveData used for updates
* UI observes LiveData

---

## Error Handling Flow

Errors must propagate:

Data → Domain → ViewModel → UI

UI displays user-friendly messages.

---

## Strict Prohibitions

* NO business logic in UI layer
* NO direct DB access from UI
* NO direct socket usage in UI
* NO cross-layer shortcuts
* NO architecture deviations

---

## Final Rule

If implementation conflicts with this architecture:

→ STOP
→ Fix design
→ Do NOT proceed with violation
