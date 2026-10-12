import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

val localProperties = Properties().apply {
    rootProject.file("local.properties").inputStream().use(::load)
}

android {
    namespace = "com.sm.towatchmovies"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.sm.towatchmovies"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "TMDB_API_KEY", "\"${localProperties.getProperty("tmdb.api.key", "")}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    room { schemaDirectory("$projectDir/schemas") }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
