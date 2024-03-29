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

package io.github.pdvrieze.kotlinsql.ddl

import io.github.pdvrieze.kotlinsql.ddl.columns.*
import io.github.pdvrieze.kotlinsql.ddl.columns.PreparedStatementHelper
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types
import kotlin.reflect.KClass

/**
 * A meta value for columns. Many columns can share a single type.
 * @param T The user type that this [IColumnType] exposes
 * @param S The column type, basically the implementation itself
 * @param C The type of the column that results from the type
 */
interface IColumnType<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>> {
    /**
     * The name of the type as used in SQL
     */
    val typeName: String

    /**
     * The class object for the type
     */
    val type: KClass<T>

    /**
     * The type value according to [Types] assigned to this type if any. On unknown types it is [Types.OTHER]
     */
    val javaType: Int

    fun cast(column: Column<*, *, *>): C {
        if (column.type.typeName == typeName) {
            @Suppress("UNCHECKED_CAST")
            return column as C
        } else {
            throw TypeCastException("The given column is not of the correct type")
        }
    }

    fun cast(value: Any): T = type.javaObjectType.cast(value)

    /**
     * Cast the given value to the type of the column for SQL. Null values are fine, incompatible values will
     * throw a ClassCastException
     */
    fun maybeCast(value: Any?): T? = value?.let { type.javaObjectType.cast(it) }

    fun newConfiguration(refColumn: C): ColumnConfiguration<T, S, C, *>

    fun fromResultSet(rs: ResultSet, pos: Int): T?

    /**
     * Set the parameter for the given input.
     */
    fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: T?)

    /**
     * Helper that sets the parameter based upon the input.
     */
    fun castSetParam(statementHelper: PreparedStatementHelper, pos: Int, value: Any?) {
        setParam(statementHelper, pos, maybeCast(value))
    }

    companion object {
        @JvmStatic

        fun fromSqlType(sqlType:Int): IColumnType<*, *, *> {
            return when (sqlType) {
                Types.BIGINT      -> NumericColumnType.BIGINT_T
                Types.BINARY      -> LengthColumnType.BINARY_T
                Types.BIT         -> SimpleColumnType.BIT_T
                Types.TINYINT     -> NumericColumnType.TINYINT_T
                Types.SMALLINT    -> NumericColumnType.SMALLINT_T
                Types.INTEGER     -> NumericColumnType.INT_T
                Types.FLOAT       -> NumericColumnType.FLOAT_T
                Types.DOUBLE      -> NumericColumnType.DOUBLE_T
                Types.NUMERIC     -> DecimalColumnType.NUMERIC_T
                Types.DECIMAL     -> DecimalColumnType.DECIMAL_T
                Types.CHAR        -> LengthCharColumnType.CHAR_T
                Types.VARCHAR     -> LengthCharColumnType.VARCHAR_T
                Types.LONGVARCHAR -> CharColumnType.TEXT_T
                Types.DATE        -> SimpleColumnType.DATE_T
                Types.TIME        -> SimpleColumnType.TIME_T
                Types.TIMESTAMP   -> SimpleColumnType.TIMESTAMP_T
                Types.VARBINARY   -> LengthColumnType.VARBINARY_T
                Types.BLOB        -> SimpleColumnType.BLOB_T
                Types.CLOB        -> SimpleColumnType.BLOB_T
                Types.BOOLEAN     -> SimpleColumnType.BIT_T
                else              -> throw SQLException("Unsupported column type")
            }
        }

    }
}
