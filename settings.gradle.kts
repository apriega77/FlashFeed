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
            from(files("flashfeed/gradle/libs.versions.toml"))
        }
    }
}
rootProject.name = "flashfeed"
include(":flashfeed-app")

include("flashfeed:abstraction:base")
include("flashfeed:data:base")
include("flashfeed:model:base")
include("flashfeed:presentation:base")
include("flashfeed:usecase:base")
include("flashfeed:hub")
include(":flashfeed:presentation:core")
include(":flashfeed:model:core")
include(":flashfeed:data:core")
include(":flashfeed:usecase:core")
include(":flashfeed:abstraction:core")
include(":flashfeed:presentation:onboarding")
include(":flashfeed:presentation:home")
include(":flashfeed:model:home")
include(":flashfeed:abstraction:home")
include(":flashfeed:data:home")
include(":flashfeed:usecase:home")
