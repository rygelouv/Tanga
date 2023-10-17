
buildscript {
    // Hilt*/
    extra.set("hilt_version", "2.47")
    // Google Services
    extra.set("google_services", "4.3.15")

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
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.junit5) apply false
    alias(libs.plugins.kover) apply false
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
