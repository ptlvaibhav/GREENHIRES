

plugins {
    alias(libs.plugins.android.application) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        //maven("https://aws-sdk-android-maven.s3.amazonaws.com/repo") // For AWS SDK
    }
    dependencies {
        //classpath("com.google.gms:google-services:4.3.15") // Added for Firebase

    }
}
