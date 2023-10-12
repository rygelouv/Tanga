
buildscript {
    // Core
    extra.set("core_version_ktx", "1.10.0")
    extra.set("core_splashscreen_version", "1.0.0")
    // Activity
    extra.set("activity_version", "1.7.2")
    // Compose
    extra.set("compose_version", "1.5.0")
    // Compose Material
    extra.set("compose_material_version", "1.1.0-beta01")
    // Lifecycle
    extra.set("lifecycle_version", "2.5.1")
    // Navigation
    extra.set("hilt_navigation_compose_version", "1.0.0")
    extra.set("compose_navigation_version", "2.6.0-alpha09")
    // Hilt
    extra.set("hilt_version", "2.47")
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
    // ExoPlayer
    extra.set("exoplayer_media3_version", "1.1.1")

    // Slack Compose Rules
    extra.set("slack_compose_lint_ruleset_version", "1.2.0")

    extra.set("kotlin_immutable_collections_version", "0.3.6")

    dependencies {
        // Google Play Services
        classpath("com.google.gms:google-services:${rootProject.extra.get("google_services")}")
        // Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:${rootProject.extra.get("hilt_version")}")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.1" apply false
}

subprojects {
    // Ktlint Gradle Plugin https://github.com/JLLeitschuh/ktlint-gradle
    project.apply("$rootDir/buildscripts/ktlint.gradle")
    project.apply("$rootDir/buildscripts/detekt.gradle")
}

tasks.register("copyGitHooks", Copy::class.java) {
    description = "Copies the git githooks from /githooks to the .git folder."
    group = "Git Hooks"
    from("$rootDir/githooks/pre-commit-ktlint", "$rootDir/githooks/pre-push-detekt")
    into("$rootDir/.git/hooks/")
    rename("pre-commit-ktlint", "pre-commit")
    rename("pre-push-detekt", "pre-push")
    doLast {
        logger.info("******** Git hook copied successfully. ***********")
    }
}

tasks.register("installGitHooks", Exec::class.java) {
    description = "Installs the pre-commit git hooks from /githooks."
    group = "Git Hooks"
    workingDir = rootDir
    commandLine = listOf("chmod")
    args("-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    doLast {
        logger.info("******** Git hooks installed successfully. *********")
    }
}

tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")
