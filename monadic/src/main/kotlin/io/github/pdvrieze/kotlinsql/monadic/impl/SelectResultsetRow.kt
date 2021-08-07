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

package io.github.pdvrieze.kotlinsql.monadic.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.ddl.Column
import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.dml.SelectStatement
import io.github.pdvrieze.kotlinsql.metadata.WrappedResultSetMetaData
import io.github.pdvrieze.kotlinsql.metadata.values.FetchDirection
import io.github.pdvrieze.kotlinsql.util.Holdability
import io.github.pdvrieze.kotlinsql.util.WarningIterator
import java.sql.ResultSet
import java.sql.SQLWarning

class SelectResultsetRow<S: SelectStatement>(val rawResultSet: ResultSet, val select: S):
    ResultSetWrapper<SelectResultsetRow<S>, Nothing>,
    ResultSetRow<Nothing> {

    override val currentRow: Int get() = rawResultSet.row
    override val concurrency: Int get() = rawResultSet.concurrency
    override val fetchSize: Int get() = rawResultSet.fetchSize
    override val holdability: Holdability get() = Holdability.fromJdbc(rawResultSet.holdability)
    override val metaData: WrappedResultSetMetaData get() = WrappedResultSetMetaData(rawResultSet.metaData)
    override val fetchDirection: FetchDirection get() = FetchDirection.fromJdbc(rawResultSet.fetchDirection)

    override val rowData: SelectResultsetRow<S> get() = this

    override val isBeforeFirst: Boolean get() = rawResultSet.isBeforeFirst
    override val isFirst: Boolean get() = rawResultSet.isFirst
    override val isLast: Boolean get() = rawResultSet.isLast
    override val isAfterLast: Boolean get() = rawResultSet.isAfterLast

    @UnmanagedSql
    override fun beforeFirst() = rawResultSet.beforeFirst()

    override fun close() {
        rawResultSet.close()
    }

    override fun data(): Nothing {
        throw UnsupportedOperationException("Select resultsets have no row data")
    }

    @UnmanagedSql
    override fun first(): Boolean = rawResultSet.first()

    override fun getWarnings(): List<SQLWarning> {
        return WarningIterator(rawResultSet.warnings).asSequence().toList()
    }

    @UnmanagedSql
    override fun last(): Boolean = rawResultSet.last()

    @UnmanagedSql
    override fun next(): Boolean = rawResultSet.next()

    @UnmanagedSql
    override fun previous(): Boolean = rawResultSet.previous()

    override fun toList(): Nothing {
        throw UnsupportedOperationException("")
    }

    fun <T: Any, S: IColumnType<T, S, C>, C: Column<T, S, C>> value(column: C, pos: Int): T? {
        return column.type.fromResultSet(rawResultSet, pos)
    }
}