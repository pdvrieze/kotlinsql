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

import uk.ac.bournemouth.kotlinsql.DBContext
import uk.ac.bournemouth.kotlinsql.Database

/**
 * Base interface for the database transaction monad that does not allow evaluation by virtue of being empty.
 */
interface EmptyDBTransaction<DB : Database, T>: DBContext<DB> {

    fun <Output> map(action: DBAction<DB, T, Output>): EvaluatableDBTransaction<DB, Output>

    fun <Output> flatmap(actions: DBContext<DB>.() -> Iterable<DBAction<DB, Unit, Output>>): EvaluatableDBTransaction<DB, Output> {
        @Suppress("UNCHECKED_CAST")
        val iterable = actions() as Iterable<DBAction<DB, Any?, T>>

        @Suppress("UNCHECKED_CAST")
        return iterable.fold(this) { accum, act ->
            accum.map(act)
        } as EvaluatableDBTransaction<DB, Output>
    }

    /**
     * Apply a single action to the transaction. This should commit if at top level,
     * but not if done from a context
     */
    fun <Output> apply(action: DBAction<DB, T, Output>): Output {
        return map(action).commit()
    }

}