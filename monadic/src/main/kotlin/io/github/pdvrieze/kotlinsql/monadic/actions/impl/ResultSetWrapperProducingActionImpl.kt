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

package io.github.pdvrieze.kotlinsql.monadic.actions.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.monadic.MonadicDBConnection
import io.github.pdvrieze.kotlinsql.monadic.actions.DBAction
import io.github.pdvrieze.kotlinsql.monadic.actions.ResultSetWrapperProducingAction

// TODO change this to take a "save variant of the resultset rather than the raw one"
internal abstract class ResultSetWrapperProducingActionImpl<DB : Database, M, R : ResultSetRow<*>>(
    private val close: M.() -> Unit,
) : ResultSetWrapperProducingAction<DB, R> {

    abstract fun wrapResultSet(initResult: M): ResultSetWrapper<R, *>

    abstract fun getCloseableResource(connection: MonadicDBConnection<DB>): M

    @OptIn(UnmanagedSql::class, ExperimentalStdlibApi::class)
    override fun <T> mapEach(transform: (R) -> T): DBAction<DB, List<T>> {
        return ResultSetWrapperConsumingAction(this, close) { resultSetWrapper ->
            buildList {
                while (resultSetWrapper.next()) add(transform(resultSetWrapper.rowData))
            }
        }
    }

    override fun <T> map(transform: (ResultSetWrapper<R, *>) -> T): DBAction<DB, T> {
        return ResultSetWrapperConsumingAction(this, close) { transform(it) }
    }

    @OptIn(UnmanagedSql::class)
    override fun isEmpty(): DBAction<DB, Boolean> = map { !it.next() }

    @OptIn(UnmanagedSql::class)
    override fun isNotEmpty(): DBAction<DB, Boolean> = map { it.next() }
}