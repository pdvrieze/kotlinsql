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
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.dml.SelectStatement
import io.github.pdvrieze.kotlinsql.monadic.MonadicDBConnection
import io.github.pdvrieze.kotlinsql.monadic.actions.SelectAction
import io.github.pdvrieze.kotlinsql.monadic.impl.SelectResultsetRow
import io.github.pdvrieze.util.kotlin.sql.PreparedStatementHelperImpl
import java.sql.ResultSet

internal class SelectActionImpl<DB : Database, S : SelectStatement>(override val query: S) :
    ResultSetWrapperProducingActionImpl<DB, Pair<PreparedStatementHelperImpl, ResultSet>, SelectResultsetRow<S>>(
        {
            try {
                second.close()
            } finally {
                first.statement.close()
            }
        }
    ),
    SelectAction<DB, S> {

    @OptIn(UnmanagedSql::class)
    override fun getCloseableResource(connection: MonadicDBConnection<DB>): Pair<PreparedStatementHelperImpl, ResultSet> {
        val s = connection.prepareStatement(query.toSQL())
        try {
            query.setParams(s)
            val q = s.statement.executeQuery()
            return Pair(s, q)
        } catch (e: Exception) {
            s.statement.close()
            throw e
        }
    }

    override fun wrapResultSet(initResult: Pair<PreparedStatementHelperImpl, ResultSet>):
            ResultSetWrapper<SelectResultsetRow<S>, Nothing> {
        return SelectResultsetRow(initResult.second, query)
    }
}