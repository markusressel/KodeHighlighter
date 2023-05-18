plugins {
    id("kodehighlighter.android.application")
    id("kodehighlighter.android.application.compose")
    id("kodehighlighter.android.application.flavors")
}

android {
    defaultConfig {
        applicationId = "de.markusressel.kodehighlighter.demo"
        versionCode = 1
        versionName = "3.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        setProperty("archivesBaseName", "KodeHighlighter_v${versionName}_(${versionCode})")
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.addAll(
                listOf("LICENSE.txt", "META-INF/DEPENDENCIES", "META-INF/ASL2.0", "META-INF/NOTICE", "META-INF/LICENSE")
            )
            pickFirsts.add("META-INF/proguard/androidx-annotations.pro")
        }
    }

    namespace = "de.markusressel.kodehighlighter.demo"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":java"))
    implementation(project(":kotlin"))
    implementation(project(":markdown"))
    implementation(project(":python"))
    implementation(project(":json"))
    implementation(project(":ocaml"))

    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.android.support.constraint:constraint-layout:2.0.4")


    // Animations
    implementation("androidx.compose.animation:animation:1.4.3")


    implementation("androidx.compose.runtime:runtime:1.4.3@aar")
    // Compose
    implementation("androidx.compose.ui:ui:1.4.3")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.4.3")
    // Material Design
    implementation("androidx.compose.material:material:1.4.3")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.7.1")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // When using a MDC theme
    implementation("com.google.android.material:compose-theme-adapter:1.2.1")

    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
