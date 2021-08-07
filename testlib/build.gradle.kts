/*
 * Copyright (c) 2018.
 *
 * This file is part of ProcessManager.
 *
 * This file is licenced to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You should have received a copy of the license with the source distribution.
 * Alternatively, you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

plugins {
    kotlin("jvm")
    idea
}

ext {
    if (!rootProject.hasProperty("myJavaVersion")) {
        set("myJavaVersion", JavaVersion.VERSION_1_8)
    }
}

group = "io.github.pdvrieze.kotlinsql"
description = "Support module for testing"

base {
    archivesBaseName = "kotlinsql-testlib"
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    sourceSets.all {
        languageSettings.apply {
            languageVersion = "1.5"
            apiVersion = "1.5"
            useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
    }
}

val kotlin_version: String by project
val jupiterVersion: String by project

dependencies {
    implementation(project(":sql"))
    api(project(":util"))

    api("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
}

idea {
    module {
        isDownloadSources = true
    }
}
