plugins {
    id("kodehighlighter.android.library")
}

android {
    namespace = "de.markusressel.kodehighlighter.testsupport"
}

dependencies {
    api(project(":core"))
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.junit4)

    testImplementation(libs.junit4)
}
