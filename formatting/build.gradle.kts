import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.dokka") version "1.4.32"
    id("com.vanniktech.maven.publish") version "0.15.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.gitlab.arturbosch.detekt:detekt-api:1.17.1")
    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.17.1")
    testImplementation("junit:junit:4.13")
    testImplementation("org.assertj:assertj-core:3.19.0")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
        allWarningsAsErrors = true
    }
}

mavenPublish {
    sonatypeHost = SonatypeHost.S01

    nexus {
        stagingProfile = null
    }
}
