import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.movamovieapp"
    compileSdk = 36

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.movamovieapp"
        minSdk = 28
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.firebase.auth)
    val room_version = "2.7.2"
    val nav_version = "2.9.3"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


     //Navigation
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    //dagger
    implementation("com.google.dagger:hilt-android:2.56.2")
    kapt("com.google.dagger:hilt-android-compiler:2.56.2")


     //lottie
    implementation ("com.airbnb.android:lottie:6.6.7")
    implementation ("com.airbnb.android:lottie:3.4.0")


//Room
    implementation ("androidx.room:room-ktx:$room_version")
    implementation( "androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")

    //GLIDE
    implementation ("com.github.bumptech.glide:glide:4.16.0")

//DotsIndicator
    implementation("com.tbuonomo:dotsindicator:5.1.0")

    implementation("com.tbuonomo:dotsindicator:4.3")


//    Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.7.2")
    implementation( "com.squareup.retrofit2:converter-gson:2.7.2")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.google.android.material:material:1.11.0")
    implementation( "androidx.appcompat:appcompat:1.7.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

//tablLayout
    implementation ("com.google.android.material:material:1.9.0")
   implementation(  "androidx.core:core-ktx:1.12.0")


//    youtube

     implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.1")


    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.31")



    //bottomsheetdialog
    implementation( "com.google.android.material:material:1.12.0")


//    Prefix
    implementation("com.hbb20:ccp:2.5.4")



}