plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "app.books.tanga"
    compileSdk = 33

    defaultConfig {
        applicationId = "app.books.tanga"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(project(":core-ui"))

    // Core
    implementation("androidx.core:core-ktx:${rootProject.extra.get("core_version_ktx")}")
    implementation("androidx.core:core-splashscreen:${rootProject.extra.get("core_splashscreen_version")}")

    // Activity Compose
    implementation("androidx.activity:activity-compose:${rootProject.extra.get("activity_version")}")

    //KTX
    implementation("androidx.activity:activity-ktx:${rootProject.extra.get("activity_version")}")

    // Lifecycle and ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra.get("lifecycle_version")}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.extra.get("lifecycle_version")}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra.get("lifecycle_version")}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra.get("lifecycle_version")}") // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${rootProject.extra.get("lifecycle_version")}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${rootProject.extra.get("lifecycle_version")}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${rootProject.extra.get("lifecycle_version")}")

    // Navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.extra.get("compose_navigation_version")}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra.get("hilt_navigation_compose_version")}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("hilt_version")}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra.get("hilt_version")}")

    implementation("androidx.datastore:datastore-preferences:${rootProject.extra.get("datastore_version")}")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:${rootProject.extra.get("firebase_bom_version")}"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:${rootProject.extra.get("accompanist_version")}")
    implementation("com.google.accompanist:accompanist-pager-indicators:${rootProject.extra.get("accompanist_version")}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${rootProject.extra.get("accompanist_systemuicontroller_version")}")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra.get("coroutinesVersion")}")

    // Play Services
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${rootProject.extra.get("play_services_version")}")
    implementation("com.google.android.gms:play-services-auth:${rootProject.extra.get("play_services_auth_version")}")

    //Coil
    implementation("io.coil-kt:coil-compose:${rootProject.extra.get("coil_compose_version")}")

    // Unit tests
    testImplementation("junit:junit:${rootProject.extra.get("junit_version")}")

    // UI tests
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra.get("androidx_test_junit_version")}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra.get("espresso_version")}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra.get("compose_version")}")

    // Debug dependencies
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra.get("compose_version")}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra.get("compose_version")}")

}