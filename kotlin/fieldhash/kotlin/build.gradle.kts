import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val kotlinVersion: String by project
val springBootVersion: String by project
val springdocVersion: String by project
val postgresVersion: String by project
val jdbiVersion: String by project
val jacksonMsgpackVersion: String by project
val logbackVersion: String by project
val okhttpVersion: String by project
val nettyVersion: String by project
val arrowVersion: String by project
val ktorVersion: String by project
val androidxLifecycleVersion: String by project
val kotlinxCoroutinesVersion: String by project


val junitVersion: String by project

plugins {
    // version sharing
    kotlin("jvm") apply false
    kotlin("plugin.spring") apply false
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("org.jetbrains.compose") apply false
    kotlin("multiplatform") apply false
    kotlin("plugin.compose") apply false
    kotlin("plugin.serialization") apply false
}

allprojects {
    apply(plugin = "io.spring.dependency-management")

    dependencyManagement {
        imports {
            mavenBom("org.jdbi:jdbi3-bom:$jdbiVersion")
            mavenBom("com.squareup.okhttp3:okhttp-bom:$okhttpVersion")
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
            mavenBom("org.junit:junit-bom:$junitVersion")
        }
        dependencies {
            dependency("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
            dependency("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
            dependency("org.postgresql:postgresql:$postgresVersion")

            dependency("ch.qos.logback:logback-core:$logbackVersion")
            dependency("ch.qos.logback:logback-classic:$logbackVersion")

            dependency("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            dependency("io.arrow-kt:arrow-core:$arrowVersion")
            dependency("io.arrow-kt:arrow-core-serialization:$arrowVersion")

            dependencySet("io.ktor:$ktorVersion") {
                entry("ktor-client-core")
                entry("ktor-client-cio")
                entry("ktor-client-logging")
                entry("ktor-client-encoding")
                entry("ktor-client-auth")
                entry("ktor-client-content-negotiation")
                entry("ktor-client-serialization")
                entry("ktor-serialization-kotlinx-json")
            }

            dependency("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel:$androidxLifecycleVersion")
            dependency("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:$androidxLifecycleVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$kotlinxCoroutinesVersion")
        }
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }

    tasks.withType<BootJar> {
        archiveClassifier.set("all")
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        }
    }
}
