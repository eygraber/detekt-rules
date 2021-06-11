plugins {
  `rules-project`
  detekt
}

dependencies {
  implementation("io.gitlab.arturbosch.detekt:detekt-test:${detektVersion}")
}
