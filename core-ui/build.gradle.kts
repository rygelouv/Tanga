plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "app.books.tanga.coreui"
    compileSdk = 34

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
        kotlinCompilerExtensionVersion = "1.5.1"
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
    api(
        "com.google.accompanist:accompanist-systemuicontroller:${
            rootProject.extra.get(
                "accompanist_systemuicontroller_version"
            )
        }"
    )

    /************** Image loading *****************
     * For now we are using both Glide and Coil because Coil still doesn't support
     * Firebase Cloud Storage Images. Once it does, we can remove Glide.
     */
    // Coil https://github.com/coil-kt/coil/releases
    api("io.coil-kt:coil-compose:2.2.2")
    // Glide
    api("com.github.bumptech.glide:glide:4.16.0")
    // Glide Compose
    api("com.github.bumptech.glide:compose:1.0.0-alpha.5")
    /************** End Image loading *****************/

    // Kotlin Immutable Collections: https://github.com/Kotlin/kotlinx.collections.immutable/releases
    implementation(
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:${
            rootProject.extra.get(
                "kotlin_immutable_collections_version"
            )
        }"
    )

    // Debug dependencies
    // https://developer.android.com/jetpack/androidx/releases/compose-ui#debugging
    debugApi("androidx.compose.ui:ui-tooling:${rootProject.extra.get("compose_version")}")
    debugApi("androidx.compose.ui:ui-test-manifest:${rootProject.extra.get("compose_version")}")

    // Slack Compose Lint Rule set https://github.com/slackhq/compose-lints/releases
    lintChecks(
        "com.slack.lint.compose:compose-lint-checks:${rootProject.extra.get("slack_compose_lint_ruleset_version")}"
    )
}

kover {
    useJacoco()
}
