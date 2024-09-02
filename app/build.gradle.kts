plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.hiltInject)
}

android {
    namespace = "com.example.verodigitaltask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.verodigitaltask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation (libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.foundation.android)
    //Hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
//retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //room
    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.room.compiler)
    kapt (libs.androidx.room.room.compiler)
    implementation (libs.androidx.room.ktx)
    //coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //worker
    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)
    // optional - RxJava2 support
    implementation(libs.androidx.work.rxjava2)
    // optional - GCMNetworkManager support
    implementation(libs.androidx.work.gcm)
    // (Java only)
    implementation(libs.androidx.work.runtime)
    // optional - Multiprocess support
    implementation(libs.androidx.work.multiprocess)


    implementation (libs.core) // QR kod tarayıcı için
    implementation (libs.zxing.android.embedded)

    implementation (libs.androidx.swiperefreshlayout)
    implementation (libs.accompanist.swiperefresh) // Kütüphane versiyonunu kontrol edin


}