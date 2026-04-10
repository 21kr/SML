plugins {
    id("com.android.application") version "8.4.2" apply false
    id("com.android.library") version "8.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.25" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
    }
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}

