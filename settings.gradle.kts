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
if (System.getenv("JITPACK").toBoolean().not())
    include(":app")
include(":core")
include(":java")
include(":json")
include(":kotlin")
include(":markdown")
include(":python")
include(":ocaml")
include(":xml")
include(":yaml")
include(":toml")
include(":properties")
include(":diff")
include(":c")
include(":cpp")
include(":csharp")
include(":go")
include(":rust")
include(":swift")
include(":dart")
include(":test-support")
