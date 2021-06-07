import com.vanniktech.maven.publish.SonatypeHost
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.5.10"
  id("io.gitlab.arturbosch.detekt") version("1.17.1")
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

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")
  detektPlugins("com.eygraber.detekt.rules:formatting:1.0.0")
}

detekt {
  toolVersion = "1.17.1"

  autoCorrect = true
  parallel = true

  buildUponDefaultConfig = true

  config = project.files("${project.rootDir}/detekt.yml")
}

tasks.withType<Detekt>().configureEach {
  jvmTarget = "11"
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = "11"
    allWarningsAsErrors = true
  }
}

mavenPublish {
  sonatypeHost = SonatypeHost.S01
}
