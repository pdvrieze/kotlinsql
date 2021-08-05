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

package io.github.pdvrieze.kotlinsql.test.helpers

import java.sql.ResultSetMetaData

class AbstractDummyResultSetMetaData(
    val resultSet: AbstractDummyResultSet
) : ResultSetMetaData {

    override fun getCatalogName(column: Int): String = TODO("not implemented")

    override fun getColumnClassName(column: Int): String = TODO("not implemented")

    override fun getColumnCount(): Int = resultSet.columns.size

    override fun getColumnDisplaySize(column: Int): Int = TODO("not implemented")

    override fun getColumnLabel(column: Int): String = TODO("not implemented")

    override fun getColumnName(column: Int): String = resultSet.columns[column-1]

    override fun getColumnType(column: Int): Int = TODO("not implemented")

    override fun getColumnTypeName(column: Int): String = TODO("not implemented")

    override fun getPrecision(column: Int): Int = TODO("not implemented")

    override fun getScale(column: Int): Int = TODO("not implemented")

    override fun getSchemaName(column: Int): String = TODO("not implemented")

    override fun getTableName(column: Int): String = TODO("not implemented")


    override fun isAutoIncrement(column: Int): Boolean = TODO("not implemented")
    override fun isCaseSensitive(column: Int): Boolean = TODO("not implemented")
    override fun isCurrency(column: Int): Boolean = TODO("not implemented")
    override fun isDefinitelyWritable(column: Int): Boolean = TODO("not implemented")
    override fun isNullable(column: Int): Int = TODO("not implemented")
    override fun isReadOnly(column: Int): Boolean = TODO("not implemented")
    override fun isSearchable(column: Int): Boolean = TODO("not implemented")
    override fun isSigned(column: Int): Boolean = TODO("not implemented")
    override fun isWrapperFor(iface: Class<*>?): Boolean = iface == AbstractDummyResultSetMetaData::class.java
    override fun isWritable(column: Int): Boolean = TODO("not implemented")

    override fun <T : Any?> unwrap(iface: Class<T>?): T? = when (iface) {
        AbstractDummyResultSetMetaData::class.java -> this as T?
        else -> null
    }
}
