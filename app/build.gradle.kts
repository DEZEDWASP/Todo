
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
   // id("com.google.gms.google-services")
}

android {
    namespace = "com.andmar.todo"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.andmar.todo"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
}



dependencies {

    implementation(platform("androidx.compose:compose-bom:2022.10.00"))

    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.ui:ui:1.6.5")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.compose.ui:ui-graphics:1.6.5")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.foundation:foundation:1.6.5")
    debugImplementation("androidx.compose.ui:ui-tooling")
    
    implementation("androidx.compose.foundation:foundation-layout:1.1.0")
    
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    
    implementation("androidx.compose.animation:animation:1.6.5")
    
    // Navigate
    val nav_version = "2.7.7"
    
    implementation("androidx.navigation:navigation-compose:$nav_version") 
    
    //Volley
    implementation("com.android.volley:volley:1.1.1")
    
    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    //OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    
    // Dagger
    implementation("com.google.dagger:dagger-android:2.44")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")
    
    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    // Firebase
   // implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
   // implementation("com.google.firebase:firebase-analytics")
}