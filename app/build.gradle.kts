plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
    id("io.sentry.android.gradle") version "3.13.0"
    id("de.mannodermaus.android-junit5")
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "app.books.tanga"
    compileSdk = 34

    defaultConfig {
        applicationId = "app.books.tanga"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.5.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(project(":core-ui"))

    // Core https://developer.android.com/jetpack/androidx/releases/core
    implementation("androidx.core:core-splashscreen:${rootProject.extra.get("core_splashscreen_version")}")

    // Activity KTX Compose https://developer.android.com/jetpack/androidx/releases/activity
    implementation("androidx.activity:activity-compose:${rootProject.extra.get("activity_version")}")
    implementation("androidx.activity:activity-ktx:${rootProject.extra.get("activity_version")}")

    // Lifecycle and ViewModel https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra.get("lifecycle_version")}"
    ) // ViewModel
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.extra.get("lifecycle_version")}"
    ) // ViewModel with Compose
    implementation(
        "androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra.get("lifecycle_version")}"
    ) // optional - LiveData
    implementation(
        "androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra.get("lifecycle_version")}"
    ) // Lifecycles only (without ViewModel or LiveData)
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${rootProject.extra.get("lifecycle_version")}"
    ) // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-common-java8:${rootProject.extra.get("lifecycle_version")}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${rootProject.extra.get("lifecycle_version")}")

    // Navigation https://developer.android.com/jetpack/androidx/releases/navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.extra.get("compose_navigation_version")}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra.get("hilt_navigation_compose_version")}")

    // Hilt https://developer.android.com/jetpack/androidx/releases/hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("hilt_version")}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra.get("hilt_version")}")

    // DataStore https://developer.android.com/jetpack/androidx/releases/datastore
    implementation("androidx.datastore:datastore-preferences:${rootProject.extra.get("datastore_version")}")

    // Firebase https://firebase.google.com/support/release-notes/android
    implementation(platform("com.google.firebase:firebase-bom:${rootProject.extra.get("firebase_bom_version")}"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    // implementation("com.google.firebase:firebase-storage-ktx")

    // Accompanist https://github.com/google/accompanist/releases
    implementation("com.google.accompanist:accompanist-pager:${rootProject.extra.get("accompanist_version")}")
    implementation(
        "com.google.accompanist:accompanist-pager-indicators:${rootProject.extra.get("accompanist_version")}"
    )

    // Kotlin Coroutines https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra.get("coroutines_version")}")
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${rootProject.extra.get("coroutines_version")}"
    )

    // Play Services https://developers.google.com/android/guides/releases
    implementation("com.google.android.gms:play-services-auth:${rootProject.extra.get("play_services_auth_version")}")

    // ExoPlayer Media3 https://developer.android.com/guide/topics/media/exoplayer
    implementation("androidx.media3:media3-exoplayer:${rootProject.extra.get("exoplayer_media3_version")}")

    // Timber https://github.com/JakeWharton/timber/releases
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Kotlin Immutable Collections: https://github.com/Kotlin/kotlinx.collections.immutable/releases
    implementation(
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:${rootProject.extra.get(
            "kotlin_immutable_collections_version"
        )}"
    )

    // Unit tests https://junit.org/junit5/
    // JUnit 5 (Required) Writing and executing Unit Tests on the JUnit Platform
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra.get("junit_version")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra.get("junit_version")}")
    // For "Parameterized Tests"
    testImplementation("org.junit.jupiter:junit-jupiter-params:${rootProject.extra.get("junit_version")}")
    // MockK https://github.com/mockk/mockk/releases
    testImplementation("io.mockk:mockk:${rootProject.extra.get("mockk_version")}")
    // Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra.get("coroutines_version")}")

    // UI Instrumentation tests https://developer.android.com/jetpack/androidx/releases/test
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra.get("androidx_test_junit_version")}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra.get("espresso_version")}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra.get("compose_version")}")

    // Slack Compose Lint Rule set https://github.com/slackhq/compose-lints/releases
    lintChecks(
        "com.slack.lint.compose:compose-lint-checks:${rootProject.extra.get("slack_compose_lint_ruleset_version")}"
    )
}

sentry {
    // this will upload your source code to Sentry to show it as part of the stack traces
    // disable if you don't want to expose your sources
    includeSourceContext.set(true)
}

kover {
    useJacoco()
}
