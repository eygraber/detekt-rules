import io.gitlab.arturbosch.detekt.Detekt

plugins {
  id("io.gitlab.arturbosch.detekt")
}

detekt {
  toolVersion = detektVersion

  autoCorrect = true
  parallel = true

  buildUponDefaultConfig = true

  config = project.files("${project.rootDir}/detekt.yml")
}

tasks.withType<Detekt>().configureEach {
  jvmTarget = "11"
}

dependencies {
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${detektVersion}")
  detektPlugins("com.eygraber.detekt.rules:formatting:1.0.1")
  detektPlugins("com.eygraber.detekt.rules:style:1.0.1")
}
