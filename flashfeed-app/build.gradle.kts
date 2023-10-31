/*
 * Copyright 2023 <a href="mailto:apriega77@gmail.com">Apriega77</a>
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
import catalog.findApi
import catalog.findKapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.apriega77.flashfeed.buildFlavorsPlugin")
    //id("com.apriega77.flashfeedfirebasePlugin")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = AppConfig.APPLICATION_ID
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        versionCode = AppConfig.VERSION_CODE
    }
    packaging {
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/license.txt")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/notice.txt")
        resources.excludes.add("META-INF/ASL2.0")
        resources.excludes.add("META-INF/*.kotlin_module")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    kotlinOptions {
        jvmTarget = AppConfig.JVM_TARGET.toString()
    }
}

dependencies {
    findKapt("hilt.compiler")
    findApi("hilt.android")
    rootProject.childProjects[ProjectConfig.PROJECT_NAME.lowercase()]?.childProjects?.get("presentation")?.childProjects?.forEach {
        api(project(it.value.path))
    }
    rootProject.childProjects[ProjectConfig.PROJECT_NAME.lowercase()]?.childProjects?.get("data")?.childProjects?.forEach {
        implementation(project(it.value.path))
    }

    implementation(project(mapOf("path" to ":flashfeed:hub")))
    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(project(":flashfeed:abstraction:core"))
    implementation(project(":flashfeed:model:core"))
}