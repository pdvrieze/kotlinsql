import net.devrieze.gradlecodegen.GenerateSpec

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

group = "io.github.pdvrieze.kotlinsql"
description = "A utility library for working with databases in kotlin"

base {
    archivesName.set("kotlinsql-sql")
}

sourceSets {
    main {
        val depth = 10
        generate {
            val databaseFunctions by registering {
                output = "io/github/pdvrieze/kotlinsql/dml/DatabaseMethods.kt"
                generator = "kotlinsql.builder.GenerateDatabaseBaseKt"
                input = depth
            }
            val selects by registering {
                output = "io/github/pdvrieze/kotlinsql/dml/impl/selectImpls.kt"
                generator = "kotlinsql.builder.GenerateSelectClasses"
                input = depth
            }
            val statements by registering {
                output = "io/github/pdvrieze/kotlinsql/dml/impl/statementImpls.kt"
                generator = "kotlinsql.builder.GenerateStatementsKt"
                input = depth
            }
            val inserts by registering {
                output = "io/github/pdvrieze/kotlinsql/dml/impl/insertImpls.kt"
                generator = "kotlinsql.builder.GenerateInsertsKt"
                input = depth
            }
/*
                val monadAccess by registering {
                    output = "uk/ac/bournemouth/kotlinsql/monads/impl/gen/Monads.kt"
                    generator = "kotlinsql.builder.GenerateConnectionSource"
                    input = depth
                }
*/

//            }
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
        sourceSets.all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId="kotlinsql-core"
        }
    }
}

val kotlin_version: String by project
val jupiterVersion: String by project

dependencies {
    generatorsImplementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
}

idea {
    module {
        isDownloadSources = true
    }
}
