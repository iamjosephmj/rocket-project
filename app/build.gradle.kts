plugins {
    id("com.android.application")
    kotlin("kapt")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = 34
    namespace = "com.example.rocketproject"

    defaultConfig {
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    kotlinOptions {
        jvmTarget = "19"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)

    // Network
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.retrofit.library)

    // DI
    implementation(libs.dagger.hilt.android.library)
    kapt(libs.dagger.hilt.android.compiler)

    // ktx and activity
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)

    // Lottie
    implementation(libs.lottie.compose)

    // Compose
    implementation(enforcedPlatform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    // Unit tests
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)

    // Preview
    debugImplementation(libs.compose.ui.tooling)
}
