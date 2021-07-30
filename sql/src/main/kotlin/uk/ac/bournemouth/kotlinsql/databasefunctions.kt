/*
 * Copyright (c) 2017.
 *
 * This file is part of ProcessManager.
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

@file:Suppress("ClassName", "FunctionName", "FunctionName")

package uk.ac.bournemouth.kotlinsql

import uk.ac.bournemouth.kotlinsql.queries.Insert
import uk.ac.bournemouth.kotlinsql.queries.Select1
import uk.ac.bournemouth.kotlinsql.queries.SelectStatement
import uk.ac.bournemouth.kotlinsql.queries.UpdatingStatement
import uk.ac.bournemouth.kotlinsql.sql.DBConnection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource


internal fun <T> SelectStatement.executeHelper(connection: DBConnection<*>,
                                               block: T,
                                               invokeHelper: (ResultSet, T) -> Unit): Boolean {
    return connection.prepareStatement(toSQL()) {
        val select: SelectStatement = this@executeHelper
        select.setParams(this)
        execute { rs ->
            if (rs.next()) {
                do {
                    invokeHelper(rs, block)
                } while (rs.next())
                true
            } else {
                false
            }
        }
    }
}

/**
 * Get a single element or a null value according to the conversion function passed. This will throw if the result count
 * is larger than 1.
 *
 * @param connection The connection to use
 * @param resultHandler The function that transforms the query result into a result object.
 *
 * @throws SQLException If there are more results than expected, or if there is an underlying SQL error.
 */
fun <R> SelectStatement.getSingleListOrNull(connection: DBConnection<*>,
                                            resultHandler: (List<Column<*, *, *>>, List<Any?>) -> R): R? {
    return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute { rs ->
            if (!rs.next()) return null
            val columns = select.columns

            val data = columns.mapIndexed { i, column -> column.type.fromResultSet(rs, i + 1) }
            val result = resultHandler(columns.toList(), data)
            if (rs.next()) throw SQLException("More results than expected")
            return result
        }
    }
}

@Suppress("unused")
        /**
         * Get a single result item, but don't accept an empty result. Otherwise this is the same as [getSingleListOrNull].
         *
         * @see getSingleListOrNull
         */
fun <R> SelectStatement.getSingleList(connection: DBConnection<*>,
                                      resultHandler: (List<Column<*, *, *>>, List<Any?>) -> R): R {
    return getSingleListOrNull(connection, resultHandler) ?: throw NoSuchElementException()
}


private fun ColumnRef<*, *, *>.name(prefixMap: Map<String, String>?): String {
    return prefixMap?.run { get(table._name)?.let { "$it.`$name`" } } ?: "`$name`"
}

private fun TableRef.name(prefixMap: Map<String, String>?): String {
    return prefixMap?.let { map -> map[this._name]?.let { "`$_name` AS $it" } } ?: "`$_name`"
}

@Deprecated("Use companion member", ReplaceWith("IColumnType.fromSqlType(sqlType)"))
fun columnType(sqlType:Int): IColumnType<*,*,*> = IColumnType.fromSqlType(sqlType)
