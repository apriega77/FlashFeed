/*
 * Copyright 2023 PT Samudra Inovasi Teknologi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    `kotlin-dsl`
    `kotlin-dsl-base`
    `java-gradle-plugin`
    `embedded-kotlin`
    `kotlin-dsl-precompiled-script-plugins`
    kotlin("jvm") version libs.versions.kotlin
}

repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.google.com")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.firebase.app.distribution.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("builder") {
            id = "co.id.samudrainovasi.builderPlugin"
            implementationClass = "BuilderConventionPlugin"
        }
        register("detekt") {
            id = "co.id.samudrainovasi.detektPlugin"
            implementationClass = "DetektConventionPlugin"
        }
        register("firebase") {
            id = "co.id.samudrainovasi.firebasePlugin"
            implementationClass = "FirebaseConventionPlugin"
        }
        register("flavors") {
            id = "co.id.samudrainovasi.buildFlavorsPlugin"
            implementationClass = "BuildFlavorsConventionPlugin"
        }
        register("spotless") {
            id = "co.id.samudrainovasi.spotlessPlugin"
            implementationClass = "SpotlessConventionPlugin"
        }
    }
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.languageVersion = "1.9"
}
