plugins {
    id("kodehighlighter.android.library")
}

android {
    namespace = "de.markusressel.kodehighlighter.language.ocaml"
}

dependencies {
    api(project(":core"))
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.androidx.compose.ui.text)
}
