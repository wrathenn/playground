val jdbiVersion: String by project
val jacksonVersion: String by project
val arrowVersion: String by project
val springdocVersion: String by project

plugins {
    kotlin("jvm")
}

group = "com.wrathenn"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.core:jackson-annotations")

    implementation("io.arrow-kt:arrow-core")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
