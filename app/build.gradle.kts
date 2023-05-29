plugins {
    id("kodehighlighter.android.application")
    id("kodehighlighter.android.application.compose")
    id("kodehighlighter.android.application.flavors")
}

android {
    defaultConfig {
        applicationId = "de.markusressel.kodehighlighter.demo"
        versionCode = 1
        versionName = "4.0.0"
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

    implementation(libs.androidx.appcompat)

    // Animations
    implementation(libs.androidx.compose.animation)


    implementation(libs.androidx.compose.runtime)
    // Compose
    implementation(libs.androidx.compose.ui)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.androidx.compose.foundation)
    // Material Design
    implementation(libs.androidx.compose.material)
    // Integration with activities
    implementation(libs.androidx.activity.compose)
    // Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // When using a MDC theme
    implementation(libs.android.material.compose.theme.adapter)

    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
