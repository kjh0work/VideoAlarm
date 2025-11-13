plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.composeCompiler)
    kotlin("kapt")
}

// ★ 최상위 레벨에 배치 (android{} 바깥)
configurations.configureEach {
    exclude(group = "com.intellij", module = "annotations")
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.example.videoalarm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kmf.videoalarm"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures { compose = true }

    // Java 컴파일 타깃도 17로 맞추기
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    packaging { resources.excludes += "/META-INF/{AL2.0,LGPL2.1}" }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Compose (BOM 기반)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material.icons.core)      // Icons 객체
    implementation(libs.androidx.material.icons.extended)  // filled/outlined 등 실제 아이콘들
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.hilt.compiler)

    implementation(libs.coil3)                 // core
    implementation(libs.coil3.compose)         // coil3.compose.AsyncImage
    implementation(libs.coil3.network.okhttp)  // 네트워크 fetcher (OkHttp)
    implementation(libs.coil3.video)           // VideoFrameDecoder

    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)           // ← androidx-junit
    androidTestImplementation(libs.androidx.espresso.core)   // ← androidx-espresso-core
    androidTestImplementation(libs.androidx.ui.test.junit4)  // ← androidx-ui-test-junit4
    debugImplementation(libs.androidx.ui.test.manifest)      // ← androidx-ui-test-manifest


    //implementation("org.jetbrains.kotlin:kotlin-metadata-jvm:2.2.21")
}
