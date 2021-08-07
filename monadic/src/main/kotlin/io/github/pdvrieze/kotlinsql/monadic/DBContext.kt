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

package io.github.pdvrieze.kotlinsql.monadic

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.ddl.Column
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.dml.impl._Statement1Base
import io.github.pdvrieze.kotlinsql.monadic.actions.DBAction
import io.github.pdvrieze.kotlinsql.monadic.actions.SelectAction
import java.sql.SQLException
import java.util.NoSuchElementException

interface DBContext<DB : Database> {
    val db: DB

    @OptIn(UnmanagedSql::class)
    fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
            SelectAction<DB, _Statement1Base<T1, S1, C1>>.getSingleOrNull(): DBAction<DB, T1?> {
        return map { rs ->
            when {
                rs.next() -> {
                    if (!rs.isLast) throw SQLException("Multiple results found, where only one or none expected")
                    rs.rowData.value(query.col1, 1)
                }
                else      -> null

            }
        }
    }

    fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
            SelectAction<DB, _Statement1Base<T1, S1, C1>>.getSingle(): DBAction<DB, T1> {
        return with(this@DBContext) {
            this@getSingle.getSingleOrNull().map { it ?: throw NoSuchElementException() }
        }
    }

    @Deprecated("Use isNotEmpty", ReplaceWith("this.isNotEmpty()"))
    fun SelectAction<DB, *>.hasRows(): DBAction<DB, Boolean> {
        return this.isNotEmpty()
    }

}