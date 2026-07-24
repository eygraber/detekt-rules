plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt2")
}

dependencies {
  // TODO remove when https://github.com/detekt/detekt/pull/9439 is released
  implementation(libs.detektApi)
  implementation(libs.detektTest) {
    // TODO remove when https://github.com/detekt/detekt/pull/9439 is released
    exclude(group = "dev.detekt", module = "detekt-api")
  }
  implementation(libs.detektTestAssertj)
  implementation(libs.test.assertj)
}
