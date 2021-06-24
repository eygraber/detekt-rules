plugins {
  `kotlin-dsl`
}

apply(from = project.file("versions.gradle"))
val detektVersion = project.findProperty("detektVersion") as String

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("gradle-plugin", "1.5.10"))
  implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
  implementation("com.vanniktech:gradle-maven-publish-plugin:0.16.0")
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.4.32")
}
