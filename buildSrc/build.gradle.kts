import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	`kotlin-dsl`
}

repositories {
	mavenCentral()
	jcenter()
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}