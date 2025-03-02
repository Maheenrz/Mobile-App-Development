plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.easycalculator"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.easycalculator"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.filament.android)
    testImplementation(libs.junit)
    implementation("com.faendir.rhino:rhino-android:1.5.2")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}