@file:Suppress("UnstableApiUsage")

import ProjectConfig.ABSTRACTION_LAYER
import ProjectConfig.DATA_LAYER
import ProjectConfig.MODEL_LAYER
import ProjectConfig.PRESENTATION_LAYER
import ProjectConfig.PROJECT_NAME
import ProjectConfig.USECASE_LAYER
import catalog.findApi
import catalog.findKapt
import catalog.findVersion
import modules.AbstractionModules
import modules.DataModules
import modules.ModelModules
import modules.PresentationModules
import modules.UsecaseModules
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import java.io.File

class BuilderConventionPlugin : Plugin<Project> {

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
                sourceCompatibility = AppConfig.JVM_TARGET
                targetCompatibility = AppConfig.JVM_TARGET
            }
            if (name == PRESENTATION_LAYER) {
                buildFeatures {
                    compose = true
                    viewBinding = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion =
                        findVersion("androidxComposeCompiler")
                }
            }
        }
    }

    private fun Project.addDependencies(moduleType: String) {
        dependencies {
            when (moduleType) {
                ABSTRACTION_LAYER -> {
                    if (name != "base") {
                        abstraction(AbstractionModules.Base, true)
                    }
                    model(findModule())
                }

                DATA_LAYER -> {
                    if (name != "base") {
                        data(DataModules.Base)
                    }
                    abstraction(findModule(), true)
                    usecase(findModule())
                    model(findModule())
                    findKapt("hilt.compiler")
                    findApi("hilt.android")
                }

                MODEL_LAYER -> {
                    if (name != "base") {
                        model(ModelModules.Base)
                    }
                }

                PRESENTATION_LAYER -> {
                    if (name != "base") {
                        presentation(PresentationModules.Base)
                    }
                    usecase(findModule())
                    model(findModule())
                    findKapt("hilt.compiler")
                    findApi("hilt.android")
                }

                USECASE_LAYER -> {
                    if (name != "base") {
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
}
