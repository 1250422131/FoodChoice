plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = 34

    namespace = "com.imcys.core.common"
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // hilt库，实现依赖注入
    api("com.google.dagger:hilt-android:2.44")
    api("androidx.hilt:hilt-navigation-compose:1.0.0")

    kapt("com.google.dagger:hilt-compiler:2.44")
    // 协程
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    // 路由
    api("androidx.navigation:navigation-compose:2.5.3")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
