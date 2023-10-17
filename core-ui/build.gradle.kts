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

    api(libs.core.ktx)
    api(libs.compose.ui)
    api(libs.compose.preview)
    api(libs.compose.material)
    api(libs.accompanist.systemuicontroller)

    /************** Image loading *****************
     * For now we are using both Glide and Coil because Coil still doesn't support
     * Firebase Cloud Storage Images. Once it does, we can remove Glide.
     */
    api(libs.coil.compose)
    // Glide
    api(libs.glide)
    // Glide Compose
    api(libs.glide.compose)
    /************** End Image loading *****************/

    implementation(libs.kotlin.immutable.collections)

    // Debug dependencies
    debugApi(libs.compose.tooling)
    debugApi(libs.compose.test.manifest)
    lintChecks(libs.slack.compose.lint.checks)
}

kover {
    useJacoco()
}
