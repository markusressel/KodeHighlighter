plugins {
    id("kodehighlighter.android.library")
    id("kodehighlighter.android.library.publishing")
}

android {
    namespace = "de.markusressel.kodehighlighter.languages"
}

dependencies {
    api(project(":core"))
    api(project(":c"))
    api(project(":cpp"))
    api(project(":csharp"))
    api(project(":dart"))
    api(project(":diff"))
    api(project(":go"))
    api(project(":java"))
    api(project(":json"))
    api(project(":kotlin"))
    api(project(":markdown"))
    api(project(":ocaml"))
    api(project(":properties"))
    api(project(":python"))
    api(project(":rust"))
    api(project(":swift"))
    api(project(":toml"))
    api(project(":xml"))
    api(project(":yaml"))
    implementation(libs.kotlin.stdlib.jdk8)

    testImplementation(libs.junit4)
}
