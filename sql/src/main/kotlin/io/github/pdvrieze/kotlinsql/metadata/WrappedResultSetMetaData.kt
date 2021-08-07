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

import java.sql.ResultSetMetaData

@Suppress("unused")
class WrappedResultSetMetaData(private val metadata: ResultSetMetaData) {
    
    val columns: List<ColumnMetadataWrapper> = List(metadata.columnCount) { ColumnMetadataWrapper(it) }

    inner class ColumnMetadataWrapper internal constructor(private val idx: Int) {

        val isAutoIncrement: Boolean get() = metadata.isAutoIncrement(idx)

        val isCaseSensitive: Boolean get() = metadata.isCaseSensitive(idx)

        val isSearchable: Boolean get() = metadata.isSearchable(idx)

        val isCurrency: Boolean get() = metadata.isCurrency(idx)

        val isNullable: Int get() = metadata.isNullable(idx)

        val isSigned: Boolean get() = metadata.isSigned(idx)

        val columnDisplaySize: Int get() = metadata.getColumnDisplaySize(idx)

        val columnLabel: String get() = metadata.getColumnLabel(idx)

        val columnName: String get() = metadata.getColumnName(idx)

        val schemaName: String get() = metadata.getSchemaName(idx)

        val precision: Int get() = metadata.getPrecision(idx)

        val scale: Int get() = metadata.getScale(idx)

        val tableName: String get() = metadata.getTableName(idx)

        val catalogName: String get() = metadata.getCatalogName(idx)

        val columnType: Int get() = metadata.getColumnType(idx)

        val columnTypeName: String get() = metadata.getColumnTypeName(idx)

        val isReadOnly: Boolean get() = metadata.isReadOnly(idx)

        val isWritable: Boolean get() = metadata.isWritable(idx)

        val isDefinitelyWritable: Boolean get() = metadata.isDefinitelyWritable(idx)

        val columnClassName: String get() = metadata.getColumnClassName(idx)
    }
}