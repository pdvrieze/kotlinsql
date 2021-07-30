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
    id("net.devrieze.gradlecodegen")
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

base {
    archivesBaseName = "kotlinsql-sql"
}

sourceSets {
    val generators = named("main") {
        val depth = 10
        withConvention(GenerateSourceSet::class) {

            generate {
/*
                val databaseFunctions by registering {
                    output = "uk/ac/bournemouth/kotlinsql/impl/gen/DatabaseMethods.kt"
                    generator = "kotlinsql.builder.GenerateDatabaseBaseKt"
                    input = depth
                }
*/
/*
                val selects by registering {
                    output = "uk/ac/bournemouth/kotlinsql/impl/gen/selectImpls.kt"
                    generator = "kotlinsql.builder.GenerateSelectClasses"
                    input = depth
                }
*/
/*
                val statements by registering {
                    output = "uk/ac/bournemouth/kotlinsql/impl/gen/statementImpls.kt"
                    generator = "kotlinsql.builder.GenerateStatementsKt"
                    input = depth
                }
*/
/*
                val inserts by registering {
                    output = "uk/ac/bournemouth/kotlinsql/impl/gen/Inserts.kt"
                    generator = "kotlinsql.builder.GenerateInsertsKt"
                    input = depth
                }
*/
/*
                val monadAccess by registering {
                    output = "uk/ac/bournemouth/util/kotlin/sql/impl/gen/Monads.kt"
                    generator = "kotlinsql.builder.GenerateConnectionSource"
                    input = depth
                }
*/

            }
        }
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

tasks.named<Test>("test") {
    doFirst {
        logger.lifecycle("Test classpath: ${classpath.joinToString()}")
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
    generatorsImplementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

    implementation(project(":ddl"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation(project(":util"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
}

idea {
    module {
        isDownloadSources = true
    }
}
