plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")// for firebase
}

android {
    namespace = "com.agriconnect.agrilink"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.agriconnect.agrilink"
        minSdk = 24
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase BOM and Authentication
    implementation(platform("com.google.firebase:firebase-bom:32.1.0")) // Added for Firebase BOM
    implementation("com.google.firebase:firebase-auth-ktx") // Added for Firebase Auth KTX
    implementation("com.google.firebase:firebase-firestore-ktx") // Firebase Firestore

}