import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.internal.impldep.org.apache.maven.model.Dependency

object Config {


	object Dependencies {


		private fun DependencyHandler.implementation(any: Any) = add("implementation", any)

		private fun DependencyHandler.testImplementation(any: Any) = add("testImplementation", any)

		private fun DependencyHandler.androidTestImplementation(any: Any) = add("androidTestImplementation", any)


		fun DependencyHandler.androidX() {
			implementation("androidx.core:core-ktx:1.12.0")
			implementation("androidx.appcompat:appcompat:1.6.1")
			implementation("androidx.constraintlayout:constraintlayout:2.1.4")
			implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
			implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
			implementation("androidx.palette:palette-ktx:1.0.0")
			implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

		}

		fun DependencyHandler.comGoogle() {
			implementation("com.google.android.material:material:1.11.0")
			implementation("com.google.code.gson:gson:2.10.1")
		}

		fun DependencyHandler.javaRX() {
			implementation("io.reactivex.rxjava3:rxjava:3.1.8")
		}

		fun DependencyHandler.retrofit() {
			implementation("com.squareup.retrofit2:retrofit:2.9.0")
			implementation("com.squareup.retrofit2:converter-gson:2.9.0")
			implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
		}

		fun DependencyHandler.okHttp() {
			implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
			implementation("com.squareup.okhttp3:okhttp")
			implementation("com.squareup.okhttp3:logging-interceptor")
		}

		fun DependencyHandler.picasso() {
			implementation("com.squareup.picasso:picasso:2.8")
		}

		fun DependencyHandler.faker() {
			implementation("net.datafaker:datafaker:2.1.0")
		}

		fun DependencyHandler.spannedGridLayoutManager() {
			implementation("io.github.joeiot:spannedgridlayoutmanager:4.0.0")
		}

	}


}
