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
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("androidBaseLibs") {
            from(files("ocean-android-base/gradle/libs.versions.toml"))
        }
    }
}
rootProject.name = "skeleton"
include(":skeleton-app")
include(":ocean-android-base:abstraction")
include(":ocean-android-base:data")
include(":ocean-android-base:hub")
include(":ocean-android-base:model")
include(":ocean-android-base:presentation")
include(":ocean-android-base:usecase")

include("skeleton:abstraction:base")
include("skeleton:data:base")
include("skeleton:model:base")
include("skeleton:presentation:base")
include("skeleton:usecase:base")
include("skeleton:hub")
include(":ocean-android-base:unit-test")
include(":skeleton:presentation:core")
include(":skeleton:model:core")
include(":skeleton:data:core")
include(":skeleton:usecase:core")
include(":skeleton:abstraction:core")
include(":ocean-ui-component")
include(":ocean-ui-component:widget")
include(":ocean-ui-component:theme")
include(":skeleton:presentation:onboarding")
include(":skeleton:usecase:onboarding")
include(":skeleton:abstraction:onboarding")
include(":skeleton:data:onboarding")
include(":skeleton:model:onboarding")
include(":skeleton:abstraction:authentication")
include(":skeleton:data:authentication")
include(":skeleton:model:authentication")
include(":skeleton:presentation:authentication")
include(":skeleton:usecase:authentication")
