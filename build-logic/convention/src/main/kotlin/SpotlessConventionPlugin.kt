import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SpotlessConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.diffplug.spotless")

            configure<SpotlessExtension> {
                format("misc") {
                    target("**/*.md", "**/.gitignore")
                    endWithNewline()
                }
                kotlin {
                    target("src/**/*.kt")
                    ktlint().setEditorConfigPath(rootProject.file(".editorconfig"))
                    trimTrailingWhitespace()
                    endWithNewline()
                    targetExclude("spotless/*.kt")
                }
                format("kts") {
                    target("**/*.kts")
                    targetExclude("**/build/**/*.kts", "spotless/*.kts")
                    licenseHeaderFile(
                        rootProject.file("spotless/copyright.kts"),
                        "(^(?![\\/ ]\\*).*\$)",
                    )
                }
            }

        }
    }
}
