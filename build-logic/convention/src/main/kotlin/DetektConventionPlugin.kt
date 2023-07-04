import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            val detektConfigPath = "$rootDir/config/detekt/detekt.yml"

            configure<DetektExtension> {
                config.setFrom(file(detektConfigPath))
                buildUponDefaultConfig = true
            }
        }
    }
}
