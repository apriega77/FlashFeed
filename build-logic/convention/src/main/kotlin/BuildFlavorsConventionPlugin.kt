@file:Suppress("UnstableApiUsage")

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildFlavorsConventionPlugin : Plugin<Project> {
    //test
    override fun apply(target: Project) {
        with(target) {
            androidAppConfiguration {
                buildFeatures {
                    buildConfig = true
                }
                this.apply {
                    flavorDimensions.add("default")
                    productFlavors {
                        create(FlavorConfig.DEV.flavorName) {
                            applicationId = AppConfig.APPLICATION_ID
                            applicationIdSuffix = ".${FlavorConfig.DEV.flavorName}"
                            buildConfigField(
                                "boolean",
                                "ENABLE_SECURITY",
                                "${AppConfig.ENABLE_SECURITY}",
                            )
                            buildConfigField(
                                "String",
                                TesterConfig.GROUP_KEY,
                                "\"${TesterConfig.GROUP_TESTER_DEV}\"",
                            )
                            versionName = AppConfig.generateVersionBuild()
                            resValue("string", "app_name", "${ProjectConfig.PROJECT_NAME}-dev")
                        }
                        create(FlavorConfig.PROD.flavorName) {
                            applicationId = AppConfig.APPLICATION_ID
                            buildConfigField("boolean", "ENABLE_SECURITY", "${true}")
                            resValue("string", "app_name", ProjectConfig.PROJECT_NAME)
                        }
                    }
                }
            }
        }
    }
}

//123