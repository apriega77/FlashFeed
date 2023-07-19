import catalog.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import java.io.File

class FirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.firebase.appdistribution")
                apply("com.google.firebase.crashlytics")
                apply("com.google.firebase.firebase-perf")
                apply("com.google.gms.google-services")
            }

            androidAppConfiguration {
                productFlavors.forEach { flavor ->
                    updateReleaseNotes()
                    firebaseAppDistributionConfiguration {
                        groups = "ocean"
                        releaseNotesFile = "${rootProject.projectDir}/RELEASE_NOTES.txt"
                        flavor.versionName = AppConfig.generateVersionBuild()
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

    private fun updateReleaseNotes() {
        val releaseNotesFile = File("RELEASE_NOTES.txt")
        val maxCommitMessages = 9
        val commitMessages = getLatestCommitMessages(maxCommitMessages)
        val existingNotes =
            if (releaseNotesFile.exists()) releaseNotesFile.readLines() else emptyList()
        val combinedNotes = (commitMessages + existingNotes).take(maxCommitMessages)
        releaseNotesFile.writeText(combinedNotes.joinToString("\n"))
        releaseNotesFile.appendText("\n")
    }

    private fun getLatestCommitMessages(count: Int): List<String> {
        val process = ProcessBuilder("git", "log", "--format=%B", "-n", count.toString())
            .start()
        return process.inputStream.bufferedReader().use { reader ->
            reader.lines().toList()
        }
    }
}
