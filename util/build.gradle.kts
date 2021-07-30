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

import net.devrieze.gradlecodegen.GenerateSourceSet

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
    idea
}

ext {
    if (!rootProject.hasProperty("myJavaVersion")) {
        set("myJavaVersion", JavaVersion.VERSION_1_8)
    }
}

version = "0.8.0-SNAPSHOT"
group = "io.github.pdvrieze.kotlinsql"
description = "A utility library for working with databases in kotlin"

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}


/*
publishing {
    publications {
        KotlinSqlPublication(MavenPublication) {
            from components.java
            groupId group
            artifactId 'kotlinsql'

            artifact sourceJar {
                classifier "sources"
            }
        }
    }
}
*/

val kotlin_version: String by project
val jupiterVersion: String by project

dependencies {

    implementation(project(":ddl"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
}

idea {
    module {
        isDownloadSources = true
    }
}
