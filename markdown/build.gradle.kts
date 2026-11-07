plugins {
    id("kodehighlighter.android.library")
    id("kodehighlighter.android.library.publishing")
}

android {
    namespace = "de.markusressel.kodehighlighter.language.markdown"
}

dependencies {
    api(project(":core"))
    // front matter is highlighted as YAML
    api(project(":yaml"))
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.androidx.compose.ui.text)

    testImplementation(project(":test-support"))
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.core)
}
