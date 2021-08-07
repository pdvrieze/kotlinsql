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

package io.github.pdvrieze.kotlinsql.metadata.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.metadata.MetadataResultSet
import io.github.pdvrieze.kotlinsql.metadata.WrappedResultSetMetaData
import io.github.pdvrieze.kotlinsql.metadata.values.FetchDirection
import io.github.pdvrieze.kotlinsql.metadata.values.ResultSetType
import io.github.pdvrieze.kotlinsql.util.Holdability
import io.github.pdvrieze.kotlinsql.util.WarningIterator
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.SQLWarning

internal abstract class AbstractMetadataResultSet<R : ResultSetRow<D>, D>
@UnmanagedSql constructor(protected val resultSet: ResultSet) :
    MetadataResultSet<R, D>, ResultSetRow<D> {

    @UnmanagedSql
    override fun beforeFirst() = resultSet.beforeFirst()

    override fun close() = resultSet.close()

    override fun getWarnings(): List<SQLWarning> = WarningIterator(resultSet.warnings).asSequence().toList()

    override val isFirst: Boolean get() = resultSet.isFirst

    override val isLast: Boolean get() = resultSet.isLast

    @UnmanagedSql
    override fun last() = resultSet.last()

    override val isAfterLast: Boolean get() = resultSet.isAfterLast

    @UnmanagedSql
    fun relative(rows: Int) = resultSet.relative(rows)

    @UnmanagedSql
    fun absolute(row: Int) = resultSet.absolute(row)

    @UnmanagedSql
    override fun next() = resultSet.next()

    @UnmanagedSql
    override fun first() = resultSet.first()

    override val currentRow: Int get() = resultSet.row

    @Suppress("UNCHECKED_CAST")
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
    override var fetchSize: Int
        get() = resultSet.fetchSize
        set(rows) {
            resultSet.fetchSize = rows
        }

    override val holdability: Holdability get() = Holdability.fromJdbc(resultSet.holdability)

    @UnmanagedSql
    override fun previous() = resultSet.previous()

    val isClosed: Boolean get() = resultSet.isClosed

    fun getCursorName(): String = resultSet.cursorName

    override val isBeforeFirst: Boolean get() = resultSet.isBeforeFirst

    @UnmanagedSql
    fun refreshRow() = resultSet.refreshRow()

    override val concurrency: Int get() = resultSet.concurrency

    @UnmanagedSql
    fun moveToCurrentRow() = resultSet.moveToCurrentRow()

    @UnmanagedSql
    fun clearWarnings() = resultSet.clearWarnings()

    override val metaData: WrappedResultSetMetaData get() = WrappedResultSetMetaData(resultSet.metaData)

    @set:UnmanagedSql
    override var fetchDirection: FetchDirection
        get() = FetchDirection.fromJdbc(resultSet.fetchDirection)
        set(value) {
            resultSet.fetchDirection = value.sqlValue
        }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun lazyColIdx(name: String) = lazy { resultSet.findColumn(name) }

    override fun toList(): List<D> {
        return toListImpl { it.data() }
    }

    @OptIn(ExperimentalStdlibApi::class, UnmanagedSql::class)
    internal inline fun toListImpl(createElem: (R) -> D): List<D> = buildList {
        while (next()) {
            add(createElem(rowData))
        }
    }
}