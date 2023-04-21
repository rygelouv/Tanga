plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "app.books.tanga.core_ui"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // Core https://developer.android.com/jetpack/androidx/releases/core
    api("androidx.core:core-ktx:${rootProject.extra.get("core_version_ktx")}")

    // Compose https://developer.android.com/jetpack/androidx/releases/compose
    api("androidx.compose.ui:ui:${rootProject.extra.get("compose_version")}")
    api("androidx.compose.ui:ui-tooling-preview:${rootProject.extra.get("compose_version")}")
    api("androidx.compose.material3:material3:${rootProject.extra.get("compose_material_version")}")

    // Accompanist https://google.github.io/accompanist/
    implementation("com.google.accompanist:accompanist-systemuicontroller:${rootProject.extra.get("accompanist_systemuicontroller_version")}")

    //Coil https://github.com/coil-kt/coil/releases
    api("io.coil-kt:coil-compose:2.2.2")

    // Debug dependencies
    // https://developer.android.com/jetpack/androidx/releases/compose-ui#debugging
    debugApi("androidx.compose.ui:ui-tooling:${rootProject.extra.get("compose_version")}")
    debugApi("androidx.compose.ui:ui-test-manifest:${rootProject.extra.get("compose_version")}")
}