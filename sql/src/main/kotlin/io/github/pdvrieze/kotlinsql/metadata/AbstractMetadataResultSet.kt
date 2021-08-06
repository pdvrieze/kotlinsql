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

package io.github.pdvrieze.kotlinsql.metadata

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.metadata.values.FetchDirection
import io.github.pdvrieze.kotlinsql.metadata.values.ResultSetType
import io.github.pdvrieze.kotlinsql.util.WarningIterator
import java.io.Closeable
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException
import java.sql.SQLWarning

abstract class AbstractMetadataResultSet<R: ResultSetRow> @UnmanagedSql constructor(
    protected val resultSet: ResultSet,
) : Closeable, AutoCloseable, ResultSetWrapper<R>, ResultSetRow {
    @UnmanagedSql
    fun beforeFirst() = resultSet.beforeFirst()

    override fun close() = resultSet.close()

    fun getWarnings(): Iterator<SQLWarning> = WarningIterator(resultSet.warnings)

    override val isFirst: Boolean get() = resultSet.isFirst

    override val isLast: Boolean get() = resultSet.isLast

    @UnmanagedSql
    fun last() = resultSet.last()

    override val isAfterLast: Boolean get() = resultSet.isAfterLast

    @UnmanagedSql
    fun relative(rows: Int) = resultSet.relative(rows)

    @UnmanagedSql
    fun absolute(row: Int) = resultSet.absolute(row)

    @UnmanagedSql
    override fun next() = resultSet.next()

    @UnmanagedSql
    fun first() = resultSet.first()

    val currentRow: Int get() = resultSet.row

    override val rowData: R
        get() = this as R

    val type: ResultSetType
        get() = when (resultSet.type) {
            ResultSet.TYPE_FORWARD_ONLY       -> ResultSetType.TYPE_FORWARD_ONLY
            ResultSet.TYPE_SCROLL_INSENSITIVE -> ResultSetType.TYPE_SCROLL_INSENSITIVE
            ResultSet.TYPE_SCROLL_SENSITIVE   -> ResultSetType.TYPE_SCROLL_SENSITIVE
            else                              -> throw SQLException("Unexpected type found")
        }

    @UnmanagedSql
    fun afterLast() = resultSet.afterLast()

    @set:UnmanagedSql
    var fetchSize: Int
        get() = resultSet.fetchSize
        set(rows) {
            resultSet.fetchSize = rows
        }

    val holdability: Int get() = resultSet.holdability

    @UnmanagedSql
    fun previous() = resultSet.previous()

    val isClosed: Boolean get() = resultSet.isClosed

    fun getCursorName(): String = resultSet.cursorName

    override val isBeforeFirst: Boolean get() = resultSet.isBeforeFirst

    @UnmanagedSql
    fun refreshRow() = resultSet.refreshRow()

    val concurrency: Int get() = resultSet.concurrency

    @UnmanagedSql
    fun moveToCurrentRow() = resultSet.moveToCurrentRow()

    @UnmanagedSql
    fun clearWarnings() = resultSet.clearWarnings()

    val metaData: ResultSetMetaData get() = resultSet.metaData

    @set:UnmanagedSql
    var fetchDirection: FetchDirection
        get() = FetchDirection.fromSqlValue(resultSet.fetchDirection)
        set(value) {
            resultSet.fetchDirection = value.sqlValue
        }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun lazyColIdx(name: String) = lazy { resultSet.findColumn(name) }
}

@OptIn(UnmanagedSql::class)
inline fun <RS : AbstractMetadataResultSet<Row>, Row: ResultSetRow> RS.closingForEach(body: (Row) -> Unit) {
    use { rs ->
        while (rs.next()) {
            body(rs.rowData)
        }
    }
}

@OptIn(UnmanagedSql::class)
inline fun <RS : AbstractMetadataResultSet<Row>, Row: ResultSetRow, R> RS.closingMap(body: (Row) -> R): List<R> {
    return mutableListOf<R>().also { r ->
        closingForEach { rs -> r.add(body(rs)) }
    }
}
