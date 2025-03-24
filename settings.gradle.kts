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
        google() // <--- Certifique-se de que esse repositório está incluído
        mavenCentral()
    }
}

rootProject.name = "CalculadoraGorjeta"
include(":app")
 