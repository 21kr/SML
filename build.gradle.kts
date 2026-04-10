buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
    }
}

plugins {
    id("com.android.application") version "8.4.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
