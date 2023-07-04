import catalog.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.firebase.appdistribution")
                apply("com.google.firebase.crashlytics")
                apply("com.google.firebase.firebase-perf")
                //apply when firebase google.service.json is available//
                // apply("com.google.gms.google-services")
            }

            androidAppConfiguration {
                productFlavors.forEach { flavor ->
                    firebaseAppDistributionConfiguration {
                        groups = "//todo:support qa"
                        releaseNotesFile = "${rootProject.projectDir}/RELEASE_NOTES.txt"
                        flavor.versionCode = AppConfig.VERSION_CODE
                    }
                }
            }

            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                "implementation"(libs.findLibrary("firebase.analytics").get())
                "implementation"(libs.findLibrary("firebase.performance").get())
                "implementation"(libs.findLibrary("firebase.crashlytics").get())
            }
        }
    }
}
