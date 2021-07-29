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

package uk.ac.bournemouth.kotlinsql.metadata

import uk.ac.bournemouth.kotlinsql.metadata.values.FetchDirection
import uk.ac.bournemouth.kotlinsql.metadata.values.ResultSetType
import uk.ac.bournemouth.kotlinsql.impl.WarningIterator
import java.io.Closeable
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException
import java.sql.SQLWarning

abstract class AbstractMetadataResultSet(protected val resultSet: ResultSet) : Closeable, AutoCloseable {
    fun beforeFirst() = resultSet.beforeFirst()

    override fun close() = resultSet.close()

    fun getWarnings(): Iterator<SQLWarning> = WarningIterator(resultSet.warnings)

    val isFirst: Boolean get() = resultSet.isFirst

    val isLast: Boolean get() = resultSet.isLast

    fun last() = resultSet.last()

    val isAfterLast: Boolean get() = resultSet.isAfterLast

    fun relative(rows: Int) = resultSet.relative(rows)

    fun absolute(row: Int) = resultSet.absolute(row)

    fun next() = resultSet.next()

    fun first() = resultSet.first()

    val row: Int get() = resultSet.row

    val type: ResultSetType
        get() = when (resultSet.type) {
            ResultSet.TYPE_FORWARD_ONLY       -> ResultSetType.TYPE_FORWARD_ONLY
            ResultSet.TYPE_SCROLL_INSENSITIVE -> ResultSetType.TYPE_SCROLL_INSENSITIVE
            ResultSet.TYPE_SCROLL_SENSITIVE   -> ResultSetType.TYPE_SCROLL_SENSITIVE
            else                              -> throw SQLException("Unexpected type found")
        }

    fun afterLast() = resultSet.afterLast()

    var fetchSize: Int
        get() = resultSet.fetchSize
        set(rows) {
            resultSet.fetchSize = rows
        }

    val holdability: Int get() = resultSet.holdability

    fun previous() = resultSet.previous()

    val isClosed: Boolean get() = resultSet.isClosed

    fun getCursorName(): String = resultSet.cursorName

    val isBeforeFirst: Boolean get() = resultSet.isBeforeFirst

    fun refreshRow() = resultSet.refreshRow()

    val concurrency: Int get() = resultSet.concurrency

    fun moveToCurrentRow() = resultSet.moveToCurrentRow()

    fun clearWarnings() = resultSet.clearWarnings()

    val metaData: ResultSetMetaData get() = resultSet.metaData

    var fetchDirection: FetchDirection
        get() = FetchDirection.fromSqlValue(resultSet.fetchDirection)
        set(value) {
            resultSet.fetchDirection = value.sqlValue
        }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun lazyColIdx(name: String) = lazy { resultSet.findColumn(name) }
}