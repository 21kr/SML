Build Instructions (MANDATORY)

Codex must ensure the project builds successfully after each phase.

---

Environment Requirements

- JDK 21
- Android SDK installed
- Gradle Wrapper included ("./gradlew")

---

Project Configuration

- Language: Java
- Build System: Gradle Groovy DSL (".gradle")
- SDK Source of Truth: This file (BUILD.md)
- Minimum SDK: 24
- Target SDK: 35
- Compile SDK: 35

---

Build Commands

**Build Project**
```
./gradlew build
```

**Clean Project**
```
./gradlew clean
```

---

Testing

**Run Unit Tests**
```
./gradlew test
```

---

Lint Check

```
./gradlew lint
```

---

Critical Build Rules

- The project MUST compile successfully before proceeding to next phase
- If build fails:
  - Identify errors
  - Fix code
  - Rebuild until successful
- Do NOT continue if build is broken

---

Dependency Rules

- Use latest stable versions unless specified
- Use Java-compatible libraries
- Avoid deprecated APIs

---

ViewBinding / DataBinding Setup

- Enable ViewBinding in gradle.properties or build.gradle
- Use `viewBinding true` or DataBinding in modules
- No Compose compiler needed

---

Hilt Setup Requirements

- Add Hilt dependencies to build.gradle
- Configure Application class with `@HiltAndroidApp`
- Apply Hilt Gradle plugin in modules
- Entry points for Activities/Fragments

---

Output Requirements

After each phase:
- Project builds successfully
- No compilation errors
- No unresolved dependencies

---

Failure Handling

If build fails repeatedly:
- Simplify implementation
- Remove unstable code
- Rebuild incrementally

---

Final Validation

Before marking any phase complete:
- `./gradlew build` passes
- App launches successfully on emulator/device
- No runtime crashes on startup

