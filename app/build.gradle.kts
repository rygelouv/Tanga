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

apply(from = "${project.rootDir}/buildscripts/jacoco.gradle.kts")

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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug") // Configures the signing with the release key
            isDebuggable = false // Disables debugging for security reasons
            isShrinkResources = true // Further reduces the size of the APK by removing unused resources
            // versionNameSuffix = "-release" // Optional: Adds a suffix to the version name for differentiation
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
    // Project Dependencies
    implementation(project(":core-ui"))

    // Core Libraries
    implementation(libs.splashscreen)
    implementation(libs.activity.compose)

    // Lifecycle
    implementation(libs.viewmodel.ktx)
    implementation(libs.viewmodel.compose)
    implementation(libs.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.viewmodel.savedstate)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.runtime.compose)

    // Navigation and DI
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // DataStore
    implementation(libs.datastore.preferences)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.storage.ktx)

    // UI Libraries
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // Kotlin Coroutines
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.play.services)

    // Google Play Services
    implementation(libs.android.gms.play.services.auth)

    // Media and Logging
    implementation(libs.media3.exoplayer)
    implementation(libs.timber)

    // Kotlin Immutable Collections
    implementation(libs.kotlin.immutable.collections)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)

    // Android Testing
    androidTestImplementation(libs.android.test.junit)
    androidTestImplementation(libs.android.espresso.core)
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.navigation.testing)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.kotlin)

    // Lint Rules
    lintChecks(libs.slack.compose.lint.checks)
}

sentry {
    // this will upload our source code to Sentry to show it as part of the stack traces
    // we will disable if we don't want to expose our sources anymore
    includeSourceContext.set(true)
}
