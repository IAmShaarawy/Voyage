plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.5.31"
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}
val composeVersion = extra.get("compose_version") as String
val hiltVersion = extra.get("hilt_version") as String
val coroutinesVersion = extra.get("coroutines_version") as String
val pagingVersion = extra.get("paging_version") as String

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "dev.shaarawy.voyage"
        minSdk = 26
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "dev.shaarawy.voyage.HiltApplicationRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    signingConfigs {
        create("release") {
            val password = obtainKeyStorePassword()
            storeFile = file("../keystore/release.jks")
            storePassword = password
            keyAlias = "release"
            keyPassword = password
        }
        getByName("debug") {
            storeFile = file("../keystore/debug.jks")
            storePassword = "voyage"
            keyAlias = "debug"
            keyPassword = "voyage"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation("com.squareup.logcat:logcat:0.1")
    implementation("androidx.startup:startup-runtime:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    testImplementation("com.google.truth:truth:1.1.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    testImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    testImplementation("org.robolectric:robolectric:4.6")
    implementation("androidx.core:core-splashscreen:1.0.0-beta01")
    implementation("androidx.paging:paging-runtime:$pagingVersion")
    testImplementation("androidx.paging:paging-common:$pagingVersion")
    implementation("androidx.paging:paging-compose:1.0.0-alpha14")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.20.0")
}

fun obtainKeyStorePassword(): String {

    val propertiesFile = file("../passwords.properties")
    val isCircleCI = System.getenv("CIRCLECI").toBoolean()

    if (propertiesFile.exists()) {
        return groovy.util.ConfigSlurper().parse(propertiesFile.toURI().toURL()).toProperties()
            .run {
                getProperty("KEY_STORE_PASS")
            }
    }

    if (isCircleCI) {
        return System.getenv("KEY_STORE_PASS")
    }

    throw GradleException("No signing credentials provided")
}