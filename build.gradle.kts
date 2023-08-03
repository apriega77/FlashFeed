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
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.firebase.app.distribution) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ocean.convention.builder)
    alias(libs.plugins.ocean.convention.spotless) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.jvm) apply false
}

allprojects {
    apply(plugin = rootProject.libs.plugins.ocean.convention.detekt.get().pluginId)
    apply(plugin = rootProject.libs.plugins.ocean.convention.spotless.get().pluginId)
    apply(plugin = rootProject.libs.plugins.dokka.get().pluginId)
}
