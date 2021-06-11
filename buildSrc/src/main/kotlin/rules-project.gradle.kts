import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
}

apply(from = rootProject.file("buildSrc/versions.gradle"))

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.gitlab.arturbosch.detekt:detekt-api:${detektVersion}")
  implementation("com.pinterest.ktlint:ktlint-core:0.41.0")
  testImplementation("io.gitlab.arturbosch.detekt:detekt-test:${detektVersion}")
  testImplementation("junit:junit:4.13")
  testImplementation("org.assertj:assertj-core:3.19.0")
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = "11"
    allWarningsAsErrors = true
  }
}
