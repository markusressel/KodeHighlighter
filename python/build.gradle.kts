plugins {
    id("kodehighlighter.android.library")
    id("kodehighlighter.android.library.publishing")
}

android {
    namespace = "de.markusressel.kodehighlighter.language.python"
}

dependencies {
    api(project(":core"))
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.androidx.compose.ui.text)
}