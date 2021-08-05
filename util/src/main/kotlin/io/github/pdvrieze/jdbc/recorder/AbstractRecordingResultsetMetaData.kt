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

package io.github.pdvrieze.jdbc.recorder

import io.github.pdvrieze.jdbc.recorder.RecordingConnection.RecordingResultSet
import java.sql.DatabaseMetaData
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.RowIdLifetime

abstract class AbstractRecordingResultsetMetaData(delegate: ResultSetMetaData) : WrappingActionRecorder<ResultSetMetaData>(delegate), ResultSetMetaData {
    override fun getColumnCount(): Int = record() {
        delegate.getColumnCount()
    }

    override fun getColumnDisplaySize(column: Int): Int = record(column) {
        delegate.getColumnDisplaySize(column)
    }

    override fun getColumnLabel(column: Int): String = record(column) {
        delegate.getColumnLabel(column)
    }

    override fun getColumnName(column: Int): String = record(column) {
        delegate.getColumnName(column)
    }

    override fun getSchemaName(column: Int): String = record(column) {
        delegate.getSchemaName(column)
    }

    override fun getPrecision(column: Int): Int = record(column) {
        delegate.getPrecision(column)
    }

    override fun getScale(column: Int): Int = record(column) {
        delegate.getScale(column)
    }

    override fun getTableName(column: Int): String = record(column) {
        delegate.getTableName(column)
    }

    override fun getCatalogName(column: Int): String = record(column) {
        delegate.getCatalogName(column)
    }

    override fun getColumnType(column: Int): Int = record(column) {
        delegate.getColumnType(column)
    }

    override fun getColumnTypeName(column: Int): String = record(column) {
        delegate.getColumnTypeName(column)
    }

    override fun getColumnClassName(column: Int): String = record(column) {
        delegate.getColumnClassName(column)
    }

    override fun isAutoIncrement(column: Int): Boolean = record(column) {
        delegate.isAutoIncrement(column)
    }

    override fun isCaseSensitive(column: Int): Boolean = record(column) {
        delegate.isCaseSensitive(column)
    }

    override fun isSearchable(column: Int): Boolean = record(column) {
        delegate.isSearchable(column)
    }

    override fun isCurrency(column: Int): Boolean = record(column) {
        delegate.isCurrency(column)
    }

    override fun isNullable(column: Int): Int = record(column) {
        delegate.isNullable(column)
    }

    override fun isSigned(column: Int): Boolean = record(column) {
        delegate.isSigned(column)
    }

    override fun isReadOnly(column: Int): Boolean = record(column) {
        delegate.isReadOnly(column)
    }

    override fun isWritable(column: Int): Boolean = record(column) {
        delegate.isWritable(column)
    }

    override fun isDefinitelyWritable(column: Int): Boolean = record(column) {
        delegate.isDefinitelyWritable(column)
    }
}
