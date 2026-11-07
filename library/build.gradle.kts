plugins {
    id("kodehighlighter.android.library")
    id("kodehighlighter.android.library.compose")
    id("kodehighlighter.android.library.publishing")
}

android {
    namespace = "de.markusressel.kodehighlighter"
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.material)

    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.core)
}
