buildscript {
    extra.set("activity_version", "1.6.1")
    extra.set("compose_version", "1.3.3")
    extra.set("compose_material_version", "1.3.1")
    extra.set("accompanist_version", "0.24.7-alpha")
    extra.set("lifecycle_version", "2.5.1")
    extra.set("coroutinesVersion", "1.6.4")
    extra.set("firebase_bom_version", "31.2.0")
    extra.set("google_services", "4.3.15")
    extra.set("gradle_version", "7.1.3")
    extra.set("google_services_version", "4.3.10")
    extra.set("hilt_version", "2.44.2")
    extra.set("datastore_version", "1.0.0")
    extra.set("core_version", "1.9.0")
    extra.set("appcompat_version", "1.4.1")
    extra.set("hilt_navigation_compose_version", "1.0.0")
    extra.set("play_services_version", "1.6.0")
    extra.set("play_services_auth_version", "20.4.1")
    extra.set("coil_compose_version", "2.2.2")

    dependencies {
        // Google Play Services
        classpath("com.google.gms:google-services:${rootProject.extra.get("google_services")}")
        // Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:${rootProject.extra.get("hilt_version")}")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}