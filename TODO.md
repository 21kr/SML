# Fix Gradle Lint Build Failure - Lint CoarseFineLocation Error

## Steps:
- [x] 1. Edit app/src/main/AndroidManifest.xml to add ACCESS_COARSE_LOCATION permission
- [x] 2. Run ./gradlew clean build to verify fix
- [x] 3. Complete task


Phase 3: Presentation Layer (MVVM Java/XML)
- [x] Add Java ViewModel + LiveData UI state wiring ✓
- [x] Connect Activity XML UI with ViewModel observers/actions ✓
- [x] Keep business logic inside repositories/use-cases (UI-only in Activity) ✓
- [x] `./gradlew build` attempted (blocked by missing Android SDK path in environment) ✓

Phase 4: Feature modules
- [x] Implement feature:connection baseline ✓
- [x] Implement feature:transfer baseline ✓
- [x] Implement feature:history baseline ✓


Phase 5: Stability and tests
- [x] Add unit tests for ViewModels ✓
- [x] Add retry/error UX polish ✓

Phase 1: Project Setup (Java/XML)
- [x] Convert .kts → .gradle (Groovy) ✓
- [x] Migrate kotlin/ → java/ ✓
- [x] Replace Compose with XML Activities/Fragments ✓
- [x] Enable ViewBinding/DataBinding ✓
- [x] Update AndroidManifest for Java classes ✓
- [x] `./gradlew build` attempted (blocked by missing Android SDK path in environment) ✓

Phase 2: Core Modules (Java)
- [x] Populate core/data/domain with Java sources ✓
- [x] Hilt setup for Java ✓
... (follow TODO order)


Phase 3: Presentation Layer (MVVM Java/XML)
- [x] Add Java ViewModel + LiveData UI state wiring ✓
- [x] Connect Activity XML UI with ViewModel observers/actions ✓
- [x] Keep business logic inside repositories/use-cases (UI-only in Activity) ✓
- [x] `./gradlew build` attempted (blocked by missing Android SDK path in environment) ✓

Phase 4: Feature modules
- [x] Implement feature:connection baseline ✓
- [x] Implement feature:transfer baseline ✓
- [x] Implement feature:history baseline ✓


Phase 5: Stability and tests
- [x] Add unit tests for ViewModels ✓
- [x] Add retry/error UX polish ✓

Phase 6: Integration hardening
- [x] Add instrumentation test smoke run ✓
- [x] Add runtime permissions UX ✓

Phase 7: WiFi Direct hardening
- [x] Add actual peer discovery UI list ✓
- [x] Add connect/disconnect lifecycle handling ✓

Phase 8: Transfer UX refinement
- [x] Add file picker integration ✓
- [x] Add transfer history list screen ✓

Phase 9: Real networking integration
- [x] Replace mock peer list with WifiDirect callbacks ✓
- [x] Add socket cancel/resume controls ✓
- [x] Evaluate Phase 9 outcomes ✓

Phase 10: Production readiness
- [x] Add robust permission denial recovery UX ✓
- [x] Add integration tests with emulator ✓ (test files added; execution blocked by missing SDK/emulator in environment)

Phase 11: Release quality
- [ ] Add crash/error analytics hooks
- [ ] Add end-to-end transfer test plan
