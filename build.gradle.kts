import com.eygraber.conventions.tasks.deleteRootBuildDirWhenCleaning
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

buildscript {
  dependencies {
    classpath(libs.buildscript.detekt)
    classpath(libs.buildscript.dokka)
    classpath(libs.buildscript.kotlin)
    classpath(libs.buildscript.publish)
  }
}

plugins {
  base
  alias(libs.plugins.conventions)
}

project.deleteRootBuildDirWhenCleaning()

gradleConventionsDefaults {
  kotlin {
    jvmTargetVersion = JvmTarget.JVM_11
    explicitApiMode = ExplicitApiMode.Strict
  }
}
