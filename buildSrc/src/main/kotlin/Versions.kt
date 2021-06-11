import org.gradle.api.Project

val Project.detektVersion get() = findProperty("detektVersion") as String
