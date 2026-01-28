plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt2")
  id("com.eygraber.conventions-publish-maven-central")
}

dependencies {
  implementation(libs.detektApi)

  testImplementation(projects.commonTest)
  testImplementation(libs.detektTest)
  testImplementation(libs.detektTestAssertj)
  testImplementation(libs.test.assertj)
  testImplementation(libs.test.junit)
}
