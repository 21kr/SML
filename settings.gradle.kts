pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.android") version "1.9.24"
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SML"
include(":app")
include(":core")
include(":domain")
include(":data")
