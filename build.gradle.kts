@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.owasp.dependencycheck.reporting.ReportGenerator.Format

plugins {
    id("org.owasp.dependencycheck") version "7.1.0.1"
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "io.github.felipeanchieta"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.mongodb:mongodb-driver-reactivestreams")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

dependencyCheck {
    analyzers.assemblyEnabled = false
    format = Format.ALL
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
    kotlinOptions.jvmTarget = "17"
}
