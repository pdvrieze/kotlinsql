/*
 * Copyright (c) 2021.
 *
 * This file is part of kotlinsql.
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

package uk.ac.bournemouth.kotlinsql.monadic

import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.TableRef

fun <DB: Database> ActionContext<DB>.hasTable(tableRef: TableRef): Boolean {
    return withMetaData {
        getTables(null, null, tableRef._name, null).use { rs ->
            rs.next()
        }
    }
}

interface EvaluatableDBTransaction<DB : Database, T> : EmptyDBTransaction<DB, T> {
    fun evaluateNow(): T

    fun commit(): T
}

inline fun <DB : Database, T> EvaluatableDBTransaction<DB, T>.require(
    crossinline condition: (T) -> Boolean,
    crossinline lazyMessage: ActionContext<DB>.() -> String
) = map { ctx, it ->
    require(condition(it)) { ctx.lazyMessage() }
}

inline fun <DB : Database> EvaluatableDBTransaction<DB, Int>.requireNonZero(
    crossinline lazyMessage: ActionContext<DB>.() -> String,
) = map { ctx, it ->
    require(it > 0) { ctx.lazyMessage() }
}
