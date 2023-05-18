pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "kodehighlighter"
include(":app")
include(":core")
include(":java")
include(":json")
include(":kotlin")
include(":markdown")
include(":python")
include(":ocaml")
