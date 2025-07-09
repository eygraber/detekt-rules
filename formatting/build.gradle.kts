plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-publish-maven-central")
}

dependencies {
  implementation(libs.detektApi)
  implementation(libs.ktlintCore)

  testImplementation(projects.commonTest)
  testImplementation(libs.detektTest)
  testImplementation(libs.test.assertj)
  testImplementation(libs.test.junit)
}
