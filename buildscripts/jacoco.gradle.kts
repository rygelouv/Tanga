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
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(
        fileTree(
            mapOf(
                "dir" to buildDir,
                "includes" to listOf(
                    // "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec" - we collect onnly androidTest coverage
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
