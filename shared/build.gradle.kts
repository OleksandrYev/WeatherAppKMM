import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.devtools)
    alias(libs.plugins.nativeCoroutines)
    kotlin("plugin.serialization").version(libs.versions.kotlin)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
    
    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.kstore)
            implementation(libs.kstore)
            implementation(libs.kstore.file)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
    }
}

android {
    namespace = "org.weatherapp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

buildkonfig {
    packageName = libs.versions.android.applicationId.get()
    exposeObjectWithName = "BuildConfig"

    defaultConfigs {
        buildConfigField(
            STRING,
            "COUNTRY_BASE_API",
            "https://restcountries.com/v3.1"
        )
        buildConfigField(
            STRING,
            "WEATHER_BASE_API",
            "https://api.openweathermap.org/data/2.5"
        )
        buildConfigField(
            STRING,
            "WEATHER_API_KEY",
            gradleLocalProperties(project.rootDir).getProperty("weatherApiKey")
        )
    }
}
