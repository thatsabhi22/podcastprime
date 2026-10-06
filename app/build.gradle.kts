import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
    id("com.google.devtools.ksp")
    id ("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.app.pro.podcastprime"
    compileSdk {
        version = release(37)
    }

    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    defaultConfig {
        applicationId = "com.app.pro.podcastprime"
        minSdk = 28
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        android.buildFeatures.buildConfig = true

        buildConfigField(
            "String",
            "API_KEY",
            "\"${localProperties.getProperty("API_KEY")}\""
        )
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.32"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Compose Accompanist
    implementation(libs.accompanist.insets)
    //implementation(libs.accompanist.coil)
    implementation(libs.coil.compose)

    // Hilt core dependencies
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation (libs.androidx.hilt.lifecycle.viewmodel)
    ksp(libs.androidx.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)

    // ExoPlayer
    implementation(libs.exoplayer)
    implementation(libs.extension.mediasession)

    // Glide image loading
    implementation(libs.glide)

    // Palette API - Selecting colors
    // implementation 'com.android.support:palette-v7:28.0.0'
    implementation(libs.androidx.palette.ktx)

}