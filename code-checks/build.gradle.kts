@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    id("kotlin")
    alias(libs.plugins.detekt) apply false
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.detekt.api)
    compileOnly(libs.lint.api)
}

tasks.jar {
    manifest {
        // Format is
        // attributes(mapOf("Lint-Registry-v2" to "<fully-qualified-class-name-of-your-issue-registry>"))
        attributes(mapOf("Lint-Registry-v2" to "app.books.tanga.codechecks.registry.LintRegistry"))
    }
}
