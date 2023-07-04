@file:Suppress("UnstableApiUsage")

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildFlavorsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            androidAppConfiguration {
                buildFeatures {
                    buildConfig = true
                }
                this.apply {
                    flavorDimensions.add("default")
                    productFlavors {
                        create("dev") {
                            applicationId = AppConfig.APPLICATION_ID
                            applicationIdSuffix = ".dev"
                            buildConfigField(
                                "boolean",
                                "ENABLE_SECURITY",
                                "${AppConfig.ENABLE_SECURITY}",
                            )
                            versionName = AppConfig.generateVersionBuild()
                        }
                        create("sit") {
                            applicationId = AppConfig.APPLICATION_ID
                            applicationIdSuffix = ".sit"
                            buildConfigField(
                                "boolean",
                                "ENABLE_SECURITY",
                                "${AppConfig.ENABLE_SECURITY}",
                            )
                            versionName = AppConfig.generateVersionBuild()
                        }
                        create("uat") {
                            applicationId = AppConfig.APPLICATION_ID
                            applicationIdSuffix = ".uat"
                            buildConfigField(
                                "boolean",
                                "ENABLE_SECURITY",
                                "${AppConfig.ENABLE_SECURITY}",
                            )
                        }
                        create("prod") {
                            applicationId = AppConfig.APPLICATION_ID
                            buildConfigField("boolean", "ENABLE_SECURITY", "${true}")
                        }
                    }
                }
            }
        }
    }
}
