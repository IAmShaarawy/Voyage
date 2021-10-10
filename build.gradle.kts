// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    allprojects {
        extra.apply {
            set("compose_version", "1.0.3")
            set("hilt_version", "2.38.1")
        }
    }

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}