plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "myst.mostafayellowstone.himmel"
    compileSdk = 34

    defaultConfig {
        applicationId = "myst.mostafayellowstone.himmel"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures{
        viewBinding = true
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    val lifecycle_version = "2.8.5"
    // activity & view model lifecycle
    implementation("androidx.activity:activity-ktx:1.9.2")
    // Retrofit dependency
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson Converter for retrofit
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // ViewModel Implementations
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
    // coroutines dependency
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    // logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    // Glide Implementation
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Navigation Components
    implementation("androidx.navigation:navigation-runtime-ktx:2.8.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.3")
    // Custom navigation bottom
    implementation("nl.joery.animatedbottombar:library:1.1.0")
    // indicator
    implementation ("com.tbuonomo:dotsindicator:5.0")
    // hilt dagger
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.intuit.ssp:ssp-android:1.0.6")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.fragment:fragment-ktx:1.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")

}