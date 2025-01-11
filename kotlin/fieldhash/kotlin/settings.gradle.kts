rootProject.name = "kotlin"

pluginManagement {

    val kotlinVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val springBootVersion: String by settings
    val composeVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.spring") version kotlinVersion apply false
        kotlin("android") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.springframework.boot") version springBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        id("org.jetbrains.compose") version composeVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.compose") version kotlinVersion apply false
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

include(
    ":backend",
    ":desktop",
    ":api-model",
    ":shared",
)
