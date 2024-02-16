import Config.Dependencies.androidX
import Config.Dependencies.comGoogle
import Config.Dependencies.faker
import Config.Dependencies.picasso
import Config.Dependencies.spannedGridLayoutManager

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.uogames.innowise"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.uogames.innowise"
		minSdk = 26
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		viewBinding = true
		buildConfig = true
	}
}

tasks.register("version"){
	println(android.defaultConfig.versionName)
}

dependencies {

	androidX()
	comGoogle()
	picasso()
	spannedGridLayoutManager()

	implementation("net.datafaker:datafaker:1.9.0")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}