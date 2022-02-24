// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    allprojects {
        extra.apply {
            set("compose_version", "1.1.0-rc01")
            set("hilt_version", "2.39.1")
            set("coroutines_version", "1.5.2")
            set("paging_version", "3.0.1")
        }
    }

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}