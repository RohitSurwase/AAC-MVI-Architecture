import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

/**
 * NewsApiKey is private ApiKey required to access web-service used in this project.
 * You can get it for free by registering at https://newsapi.org/register.
 * It is a free service for individual and I do not relate with it in any way.
 */
val myNewsApiPrivateKey: String by project

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.rohitss.mvr"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "NewsApiKey", myNewsApiPrivateKey)
        }
        getByName("release") {
            buildConfigField("String", "NewsApiKey", myNewsApiPrivateKey)
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    (kotlinOptions as KotlinJvmOptions).apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(project(":aacmvi"))
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.activity:activity-ktx:1.1.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")

    //Kotlin
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    //Room
    implementation("androidx.room:room-runtime:2.2.3")
    kapt("androidx.room:room-compiler:2.2.3")
    //Coroutines support for Room
    implementation("androidx.room:room-ktx:2.2.3")

    //ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    kapt("androidx.lifecycle:lifecycle-common-java8:2.2.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")

    //Image Loading
    implementation("io.coil-kt:coil:0.8.0")

    //Webservices
    implementation("com.squareup.retrofit2:retrofit:2.6.2")
    implementation("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.2.2")
}
