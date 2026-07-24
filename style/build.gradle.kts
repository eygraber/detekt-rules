plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt2")
  id("com.eygraber.conventions-publish-maven-central")
}

dependencies {
  implementation(libs.detektApi)

  testImplementation(projects.commonTest)
  testImplementation(libs.detektTest) {
    // TODO remove when https://github.com/detekt/detekt/pull/9439 is released
    exclude(group = "dev.detekt", module = "detekt-api")
  }
  testImplementation(libs.detektTestAssertj)
  testImplementation(libs.test.assertj)
  testImplementation(libs.test.junit)
}
