@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    id("kotlin")
    alias(libs.plugins.detekt) apply false
}

apply(from = "${project.rootDir}/buildscripts/jacoco.gradle.kts")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.detekt.api)
    compileOnly(libs.lint.api)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.mockk)
}

tasks.jar {
    manifest {
        // Format is
        // attributes(mapOf("Lint-Registry-v2" to "<fully-qualified-class-name-of-your-issue-registry>"))
        attributes(mapOf("Lint-Registry-v2" to "app.books.tanga.codechecks.registry.LintRegistry"))
    }
}
