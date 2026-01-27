plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
//  id("com.eygraber.conventions-detekt2")
}

dependencies {
  implementation(libs.detektTest)
  implementation(libs.detektTestAssertj)
  implementation(libs.test.assertj)
}
