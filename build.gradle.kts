buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
}

//dependencies {
//    classpath("org.jetbrains.dokka:dokka-gradle-plugin")
//    classpath('com.github.dcendents:android-maven-gradle-plugin:2.1')
//}
