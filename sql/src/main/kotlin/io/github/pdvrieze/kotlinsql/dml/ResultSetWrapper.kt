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

package io.github.pdvrieze.kotlinsql.dml

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.metadata.WrappedResultSetMetaData
import io.github.pdvrieze.kotlinsql.metadata.values.FetchDirection
import io.github.pdvrieze.kotlinsql.util.Holdability
import java.io.Closeable
import java.sql.ResultSetMetaData
import java.sql.SQLWarning

interface ResultSetWrapper<R: ResultSetRow<D>, D>: Closeable {

    val currentRow: Int

    val fetchSize: Int
    val holdability: Holdability

    val isAfterLast: Boolean
    val isBeforeFirst: Boolean
    val isFirst: Boolean
    val isLast: Boolean

    val rowData: R
    val concurrency: Int
    val metaData: WrappedResultSetMetaData

    val fetchDirection: FetchDirection

    @UnmanagedSql
    fun beforeFirst()

    @UnmanagedSql
    fun first(): Boolean

    @UnmanagedSql
    fun last(): Boolean

    @UnmanagedSql
    fun next(): Boolean

    @UnmanagedSql
    fun previous(): Boolean

    fun getWarnings(): List<SQLWarning>

    fun toList(): List<D>

}