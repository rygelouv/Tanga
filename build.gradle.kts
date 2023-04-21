buildscript {
    // Core
    extra.set("core_version_ktx", "1.10.0")
    extra.set("core_splashscreen_version", "1.0.0")
    // Activity
    extra.set("activity_version", "1.6.1")
    // Compose
    extra.set("compose_version", "1.4.2")
    // Compose Material
    extra.set("compose_material_version", "1.1.0-beta01")
    // Lifecycle
    extra.set("lifecycle_version", "2.5.1")
    // Navigation
    extra.set("hilt_navigation_compose_version", "1.0.0")
    extra.set("compose_navigation_version", "2.6.0-alpha09")
    // Hilt
    extra.set("hilt_version", "2.44.2")
    // Firebase
    extra.set("firebase_bom_version", "31.2.0")
    // DataStore
    extra.set("datastore_version", "1.0.0")
    // Accompanist
    extra.set("accompanist_version", "0.24.7-alpha")
    extra.set("accompanist_systemuicontroller_version", "0.28.0")
    // Kotlin Coroutines
    extra.set("coroutines_version", "1.6.4")
    // Play Services
    extra.set("play_services_auth_version", "20.5.0")
    // Coil
    extra.set("coil_compose_version", "2.2.2")
    // Unit Tests
    extra.set("junit_version", "4.13.2")
    // Instrumentation Tests
    extra.set("androidx_test_junit_version", "1.1.5")
    extra.set("espresso_version", "3.5.1")

    // Gradle
    extra.set("gradle_version", "7.1.3")
    // Google Services
    extra.set("google_services", "4.3.15")

    dependencies {
        // Google Play Services
        classpath("com.google.gms:google-services:${rootProject.extra.get("google_services")}")
        // Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:${rootProject.extra.get("hilt_version")}")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.0" apply false
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}