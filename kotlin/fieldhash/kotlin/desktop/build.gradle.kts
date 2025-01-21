import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
}

group = "com.wrathenn"
version = "1.0-SNAPSHOT"

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation("org.jetbrains.compose.runtime:runtime")
            implementation("org.jetbrains.compose.foundation:foundation")
            implementation("org.jetbrains.compose.material:material")
            implementation("org.jetbrains.compose.ui:ui")
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose")
            implementation(project(":api-model"))

            // web client
            implementation("io.ktor:ktor-client-core")
            implementation("io.ktor:ktor-client-cio")
            implementation("io.ktor:ktor-client-logging")
            implementation("io.ktor:ktor-client-encoding")
            implementation("io.ktor:ktor-client-auth")
            implementation("io.ktor:ktor-client-content-negotiation")
            implementation("io.ktor:ktor-client-serialization")
            implementation("io.ktor:ktor-serialization-kotlinx-json")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
        }
    }
}

compose.desktop {

    application {
        mainClass = "com.wrathenn.fieldhash.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "desktop"
            packageVersion = "1.0.0"
        }
    }
}
