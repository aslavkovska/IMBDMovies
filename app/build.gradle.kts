plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.di)
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.martin.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.martin.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


        buildConfigField("String", "MOVIES_ACCESS_KEY", project.findProperty("movies_access_key") as String)
        buildConfigField("String", "BASE_URL", project.findProperty("base_url") as String)
        buildConfigField("String", "IMAGE_BASE_URL", project.findProperty("image_base_url") as String)


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
        buildConfig = true
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
    implementation("androidx.compose.material:material:1.6.8")
    implementation ("androidx.compose.ui:ui:1.0.0")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha01")
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation("com.google.dagger:hilt-android:2.44")
    implementation(libs.androidx.runtime.livedata)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.20")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("androidx.compose.runtime:runtime:1.4.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.4.0")
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.7")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.7")
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.tls)
    implementation(libs.okhttp.interceptor)
    implementation(libs.slack.eithernet)
    implementation(libs.moshi)
    kapt(libs.moshi.codegen)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}