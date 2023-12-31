plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.imcys.core.designsystem"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
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
}

dependencies {
    api(libs.androidx.material3.window.size)

    // 网络图片加载
    api("io.coil-kt:coil-compose:2.4.0")
    // systemuicontroller沉浸式实现
    api("com.google.accompanist:accompanist-insets:0.28.0")
    api("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    // kotlin依赖
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    api("androidx.activity:activity-compose:1.7.2")

    // compose
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui.ui)
    api(libs.androidx.compose.ui.ui.graphics)
    api(libs.androidx.compose.ui.ui.tooling.preview)
    api(libs.androidx.compose.material.material.icons.extended)
    api(libs.androidx.compose.material3.material3)
    api(libs.com.airbnb.android.lottie)

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
