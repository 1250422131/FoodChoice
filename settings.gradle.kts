pluginManagement {
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "FoodChoice"
include(":app")
include(":core:common")
include(":core:network")
include(":core:ui")
include(":core:model")
include(":core:designsystem")
include(":core:database")
include(":feature:cook")
include(":core:data")
