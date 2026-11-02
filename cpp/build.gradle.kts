plugins {
    id("kodehighlighter.android.library")
    id("kodehighlighter.android.library.publishing")
}

android {
    namespace = "de.markusressel.kodehighlighter.language.cpp"
}

dependencies {
    api(project(":core"))
    implementation(libs.kotlin.stdlib.jdk8)

    testImplementation(project(":test-support"))
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.core)
}
