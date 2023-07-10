package catalog

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.androidBaseLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>()
        .named("androidBaseLibs")

fun Project.findVersion(alias: String): String {
    return androidBaseLibs.findVersion(alias).get().toString()
}

fun Project.findKapt(alias: String) {
    dependencies {
        add("kapt", libs.findLibrary(alias).get())
    }
}

fun Project.findApi(alias: String) {
    dependencies {
        add("api", libs.findLibrary(alias).get())
    }
}

fun Project.findApi(vararg alias: String) {
    alias.forEach {
        findApi(it)
    }
}

fun Project.findImplementation(alias: String) {
    dependencies {
        add("implementation", libs.findLibrary(alias).get())
    }
}

fun Project.findImplementation(vararg alias: String) {
    alias.forEach {
        findImplementation(it)
    }
}
