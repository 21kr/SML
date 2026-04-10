04.10 7:46 AM
BUILD.md
BUILD.md
Build Instructions (MANDATORY)
Codex must ensure the project builds successfully after each phase.
---
Environment Requirements
- JDK 21
- Android SDK installed
- Gradle Wrapper included ("./gradlew")
---
Project Configuration
- Language: Kotlin
- Build System: Gradle Kotlin DSL (".kts")
- Minimum SDK: 24
- Target SDK: 35
- Compile SDK: 35
---
Build Commands
Build Project
./gradlew build
Clean Project
./gradlew clean
---
Testing
Run Unit Tests
./gradlew test
---
Lint Check
./gradlew lint
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
- Use Kotlin-compatible libraries only
- Avoid deprecated APIs
---
Compose Setup Requirements
- Enable Compose in Gradle
- Use Material 3
- Ensure Compose compiler compatibility with Kotlin version
---
Hilt Setup Requirements
- Add Hilt dependencies
- Configure Application class with "@HiltAndroidApp"
- Ensure proper Gradle plugins are applied
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
- Build passes
- App launches successfully
- No runtime crashes on startup

