@file:Suppress("UnstableApiUsage")

import catalog.libs
import modules.AbstractionModules
import modules.DataModules
import modules.ModelModules
import modules.PresentationModules
import modules.UsecaseModules
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import java.io.File

class BuilderConventionPlugin : Plugin<Project> {
    companion object {
        private const val PROJECT_NAME = "skeleton"

        private const val ABSTRACTION_LAYER = "abstraction"
        private const val DATA_LAYER = "data"
        private const val MODEL_LAYER = "model"
        private const val PRESENTATION_LAYER = "presentation"
        private const val USECASE_LAYER = "usecase"
    }

    override fun apply(target: Project) {
        target.childProjects.values.forEach {
            if (it.name == PROJECT_NAME) {
                handleProject(it)
            }
        }
    }

    private fun handleProject(project: Project) {
        createGeneratedClass(project)
        project.childProjects.values.forEach { moduleType ->
            when (moduleType.name) {
                ABSTRACTION_LAYER,
                DATA_LAYER,
                MODEL_LAYER,
                PRESENTATION_LAYER,
                USECASE_LAYER,
                -> {
                    moduleType.childProjects.values.forEach { feature ->
                        feature.handlePlugins(moduleType.name)
                        feature.beforeEvaluate {
                            feature.configureAndroid(moduleType.name)
                            feature.addDependencies(moduleType.name)
                            feature.generateGradleDependencyRules(moduleType.name)
                        }
                    }
                }
            }
        }
    }

    private fun Project.handlePlugins(name: String) {
        plugins.apply {
            apply("com.android.library")
            apply("kotlin-android")
            when (name) {
                MODEL_LAYER -> {
                    apply("kotlin-parcelize")
                }

                DATA_LAYER -> {
                    apply("kotlin-parcelize")
                    apply("kotlin-kapt")
                    apply("dagger.hilt.android.plugin")
                }

                PRESENTATION_LAYER -> {
                    apply("kotlin-parcelize")
                    apply("kotlin-kapt")
                    apply("dagger.hilt.android.plugin")
                }
            }
        }
    }

    private fun Project.configureAndroid(name: String) {
        val currentName = this.name
        androidLibConfiguration {
            tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
                kotlinOptions.languageVersion = "1.9"
            }
            namespace = "$PROJECT_NAME.$name.$currentName"
            compileSdk = AppConfig.COMPILE_SDK

            defaultConfig {
                minSdk = AppConfig.MIN_SDK

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro",
                    )
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
            if (name == PRESENTATION_LAYER) {
                buildFeatures {
                    compose = true
                    viewBinding = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion =
                        libs.findVersion("androidxComposeCompiler").get().toString()
                }
            }
        }
    }

    private fun Project.addDependencies(moduleType: String) {
        dependencies {
            when (moduleType) {
                ABSTRACTION_LAYER -> {
                    if (name == "base") {
                        api(project(":ocean-android-base:abstraction"))
                    } else {
                        abstraction(AbstractionModules.Base, true)
                    }
                    model(findModule())
                }

                DATA_LAYER -> {
                    if (name == "base") {
                        api(project(":ocean-android-base:data"))
                    } else {
                        data(DataModules.Base)
                    }
                    abstraction(findModule(), true)
                    usecase(findModule())
                    model(findModule())
                    add("kapt", libs.findLibrary("hilt.compiler").get())
                    add("api", libs.findLibrary("hilt.android").get())
                }

                MODEL_LAYER -> {
                    if (name == "base") {
                        api(project(":ocean-android-base:model"))
                    } else {
                        model(ModelModules.Base)
                    }
                }

                PRESENTATION_LAYER -> {
                    if (name == "base") {
                        api(project(":ocean-android-base:presentation"))
                    } else {
                        presentation(PresentationModules.Base)
                    }
                    usecase(findModule())
                    model(findModule())
                    add("kapt", libs.findLibrary("hilt.compiler").get())
                    add("api", libs.findLibrary("hilt.android").get())
                }

                USECASE_LAYER -> {
                    if (name == "base") {
                        api(project(":ocean-android-base:usecase"))
                    } else {
                        usecase(UsecaseModules.Base)
                    }
                    abstraction(findModule())
                    model(findModule())
                }
            }
        }
    }

    private fun createGeneratedClass(project: Project) {
        project.childProjects.values.forEach {
            when (it.name) {
                ABSTRACTION_LAYER,
                DATA_LAYER,
                MODEL_LAYER,
                PRESENTATION_LAYER,
                USECASE_LAYER,
                -> createFile(it)
            }
        }
    }

    private fun createFile(module: Project) {
        val moduleName = module.name.capitalized()
        val file = File(
            "${module.rootProject.rootDir}/build-logic/convention/src/main/kotlin/modules",
            "${moduleName}Modules.kt",
        )
        val size = module.childProjects.size
        val firstLine = file.useLines {
            it.firstOrNull()
        }
        if (firstLine != null && firstLine.startsWith("//")) {
            val existingSize = firstLine.substring(2).toInt()
            if (size == existingSize) return
        }
        file.writeText(
            "//$size\n" +
                "package modules\n" +
                "\n" +
                "object ${moduleName}Modules {\n" +
                "\n" +
                module.generateChildClass() +
                "\n}" +
                "\n",
        )
    }

    private fun Project.generateChildClass(): String {

        val listClass: List<String> = this.childProjects.map {
            val name = it.value.name.capitalized()
            val path = ":$PROJECT_NAME:${this.name}:${it.key}"
            "\tobject $name : ${this.name.capitalized()}(\"$path\",\"${it.key}\")"
        }
        return listClass.sorted().joinToString(separator = "\n") { it }
    }

    private fun Project.generateGradleDependencyRules(moduleType: String) {
        val file = this.buildFile
        val firstLine = file.useLines {
            it.firstOrNull()
        }
        if (firstLine == null) {
            file.writeText(
                "dependencies {\n" +
                    "    /*\n" +
                    "    * Configures the dependency notations for this project in version catalog.\n" +
                    "    * Version catalog located in \"gradle/libs.versions.toml\"\n" +
                    "    * Example:\n" +
                    "    *\n" +
                    "    *    dependencies {\n" +
                    "    *        api(libs.bundles.$PROJECT_NAME.$moduleType.$name.api)\n" +
                    "    *    }\n" +
                    "    *\n" +
                    "    * After you modified that file, make sure your dependencies sync properly\n" +
                    "    */\n" +
                    "}\n",
            )
        }
    }
}
