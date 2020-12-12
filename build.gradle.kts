plugins {
	id("maven-publish")
	id("io.gitlab.arturbosch.detekt").version(Dependencies.detektVersion)
}

// Versioning
allprojects {
	group = "org.jellyfin.apiclient"
	version = getProperty("jellyfin.version")?.removePrefix("v") ?: "SNAPSHOT"
}

buildscript {
	repositories {
		google()
		jcenter()
	}

	dependencies {
		classpath(Dependencies.Android.buildTools)
		classpath(Dependencies.Kotlin.gradlePlugin)
	}
}

allprojects {
	repositories {
		google()
		jcenter()
	}

	// Publishing
	plugins.apply("maven-publish")
	publishing.repositories.jellyfinBintray(this)

	// Detekt
	plugins.apply("io.gitlab.arturbosch.detekt")
	detekt {
		parallel = true
		buildUponDefaultConfig = true
		ignoreFailures = true
		config = files("$rootDir/detekt.yml")
	}
}
