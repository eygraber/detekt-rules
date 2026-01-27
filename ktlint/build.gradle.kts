plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt2")
  id("com.eygraber.conventions-publish-maven-central")
}

dependencies {
  implementation(libs.ktlintRuleEngine)
  compileOnly(libs.ktlintCliRulesetCore)

  testImplementation(libs.ktlintTest)
  testImplementation(libs.test.assertj)
  testImplementation(libs.test.junit)
}
