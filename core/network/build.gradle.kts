plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33

    namespace = "com.imcys.core.network"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    api(libs.com.squareup.retrofit2.retrofit)
    api(libs.com.squareup.retrofit2.converter.moshi)
    api("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
