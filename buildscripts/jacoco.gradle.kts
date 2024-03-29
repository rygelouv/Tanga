// A Jacoco configuration for Android projects. This task is used to generate code coverage reports.

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("createDebugCoverageReport")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf(
        // android
        "**/R.class",
        "**/R$*.class",
        "R\$drawable.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        // kotlin
        "**/*MapperImpl*.*",
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        "**/BuildConfig.*",
        "**/*Component*.*",
        "**/*BR*.*",
        "**/Manifest*.*",
        "**/*\$Lambda$*.*",
        "**/*Companion*.*",
        "**/*Module*.*",
        "**/*Dagger*.*",
        "**/*Hilt*.*",
        "**/*MembersInjector*.*",
        "**/*_MembersInjector.class",
        "**/*_Factory*.*",
        "**/*_Provide*Factory*.*",
        "**/*Extensions*.*",
        // sealed and data classes
        "**/*\$Result.*",
        "**/*\$Result$*.*",
    )

    val debugTree = fileTree(mapOf("dir" to "$buildDir/intermediates/classes/debug", "excludes" to fileFilter))
    val javaTree = fileTree(mapOf("dir" to "$buildDir/intermediates/javac/debug/classes", "excludes" to fileFilter))
    val kotlinTree = fileTree(mapOf("dir" to "$buildDir/tmp/kotlin-classes/debug", "excludes" to fileFilter))

    val appSrc = "${project.rootDir}/app/src/main/java"
    val coreUiSrc = "${project.rootDir}/core-ui/src/main/java"

    val appClassDirs = files(debugTree, javaTree, kotlinTree)
    val coreUiClassDirs = files(
        fileTree(mapOf("dir" to "${project.rootDir}/core-ui/build/intermediates/javac/debug/classes", "excludes" to fileFilter)),
        fileTree(mapOf("dir" to "${project.rootDir}/core-ui/build/tmp/kotlin-classes/debug", "excludes" to fileFilter)),
        fileTree(mapOf("dir" to "${project.rootDir}/core-ui/build/intermediates/classes/debug", "excludes" to fileFilter))
    )

    sourceDirectories.setFrom(files(appSrc, coreUiSrc))
    classDirectories.setFrom(files(appClassDirs, coreUiClassDirs))

    executionData.setFrom(
        fileTree(
            mapOf(
                "dir" to buildDir,
                "includes" to listOf(
                    "outputs/code_coverage/debugAndroidTest/connected/*coverage.ec"
                )
            )
        )
    )

    doLast {
        println("Wrote HTML coverage report to ${reports.html.entryPoint}")
        println("Wrote XML coverage report to ${reports.xml.name}")
    }

}
