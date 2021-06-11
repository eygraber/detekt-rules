plugins {
  `rules-project`
  detekt
  publish
}

dependencies {
  testImplementation(projects.commonTest)
}
