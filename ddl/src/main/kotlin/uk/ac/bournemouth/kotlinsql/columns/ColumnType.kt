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

@file:Suppress("ClassName", "unused")

package uk.ac.bournemouth.kotlinsql.columns

import uk.ac.bournemouth.kotlinsql.BoundedType
import uk.ac.bournemouth.kotlinsql.Column
import uk.ac.bournemouth.kotlinsql.IColumnType
import java.io.ByteArrayInputStream
import java.math.BigDecimal
import java.sql.*
import kotlin.reflect.KClass

@Suppress("ClassName")
sealed class ColumnType<T : Any, S : ColumnType<T, S, C>, C : Column<T, S, C>>(
    override val typeName: String,
    override val type: KClass<T>,
    override val javaType: Int,
) : IColumnType<T, S, C> {

    @Suppress("UNCHECKED_CAST")
    fun asS() = this as S

    override fun toString() = "ColumnType: $typeName ($type)"
}

interface INumericColumnType<T : Any, S : INumericColumnType<T, S, C>, C : INumericColumn<T, S, C>> :
    IColumnType<T, S, C>

sealed class NumericColumnType<T : Any, S : NumericColumnType<T, S>>(typeName: String, type: KClass<T>, javaType: Int) :
    ColumnType<T, S, NumericColumn<T, S>>(typeName, type, javaType), INumericColumnType<T, S, NumericColumn<T, S>> {

    object TINYINT_T : NumericColumnType<Byte, TINYINT_T>("TINYINT", Byte::class, Types.TINYINT) {
        override fun fromResultSet(rs: ResultSet, pos: Int) = rs.getByte(pos).let { if (rs.wasNull()) null else it }
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Byte?) {
            statementHelper.setByte(pos, javaType, value)
        }
    }

    object SMALLINT_T : NumericColumnType<Short, SMALLINT_T>("SMALLINT", Short::class, Types.SMALLINT) {
        override fun fromResultSet(rs: ResultSet, pos: Int) = rs.getShort(pos).let { if (rs.wasNull()) null else it }
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Short?) {
            statementHelper.setShort(pos, javaType, value)
        }
    }

    object MEDIUMINT_T : NumericColumnType<Int, MEDIUMINT_T>("MEDIUMINT", Int::class, Types.OTHER) {
        override fun fromResultSet(rs: ResultSet, pos: Int) = rs.getInt(pos).let { if (rs.wasNull()) null else it }
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Int?) {
            statementHelper.setInt(pos, javaType, value)
        }
    }

    object INT_T : NumericColumnType<Int, INT_T>("INT", Int::class, Types.INTEGER) {
        override fun fromResultSet(rs: ResultSet, pos: Int) = rs.getInt(pos).let { if (rs.wasNull()) null else it }
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Int?) {
            statementHelper.setInt(pos, javaType, value)
        }
    }

    object BIGINT_T : NumericColumnType<Long, BIGINT_T>("BIGINT", Long::class, Types.BIGINT) {
        override fun fromResultSet(rs: ResultSet, pos: Int) = rs.getLong(pos).let { if (rs.wasNull()) null else it }
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Long?) {
            statementHelper.setLong(pos, javaType, value)
        }
    }

    object FLOAT_T : NumericColumnType<Float, FLOAT_T>("FLOAT", Float::class, Types.FLOAT) {
        override fun fromResultSet(rs: ResultSet, pos: Int) = rs.getFloat(pos).let { if (rs.wasNull()) null else it }
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Float?) {
            statementHelper.setFloat(pos, javaType, value)
        }
    }

    object DOUBLE_T : NumericColumnType<Double, DOUBLE_T>("DOUBLE", Double::class, Types.DOUBLE) {
        override fun fromResultSet(rs: ResultSet, pos: Int) = rs.getDouble(pos).let { if (rs.wasNull()) null else it }
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Double?) {
            statementHelper.setDouble(pos, javaType, value)
        }
    }

    override fun newConfiguration(refColumn: NumericColumn<T, S>) =
        NumberColumnConfiguration(asS(), refColumn.name)

}

sealed class DecimalColumnType<S : DecimalColumnType<S>>(typeName: String, type: KClass<BigDecimal>, javaType: Int) :
    ColumnType<BigDecimal, S, DecimalColumn<S>>(typeName, type, javaType),
    INumericColumnType<BigDecimal, S, DecimalColumn<S>> {

    override fun fromResultSet(rs: ResultSet, pos: Int): BigDecimal? = rs.getBigDecimal(pos)

    object DECIMAL_T : DecimalColumnType<DECIMAL_T>("DECIMAL", BigDecimal::class, Types.DECIMAL) {
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: BigDecimal?) {
            statementHelper.setDecimal(pos, javaType, value)
        }
    }

    object NUMERIC_T : DecimalColumnType<NUMERIC_T>("NUMERIC", BigDecimal::class, Types.NUMERIC) {
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: BigDecimal?) {
            statementHelper.setNumeric(pos, javaType, value)
        }
    }

    override fun newConfiguration(refColumn: DecimalColumn<S>): AbstractColumnConfiguration<BigDecimal, S, DecimalColumn<S>, *> {
        return DecimalColumnConfiguration(asS(), refColumn.name, refColumn.precision, refColumn.scale)
    }
}

sealed class SimpleColumnType<T : Any, S : SimpleColumnType<T, S>>(typeName: String, type: KClass<T>, javaType: Int) :
    ColumnType<T, S, SimpleColumn<T, S>>(typeName, type, javaType) {

    object BIT_T : SimpleColumnType<Boolean, BIT_T>("BIT", Boolean::class, Types.BIT), BoundedType {
        override val maxLen = 64
        override fun fromResultSet(rs: ResultSet, pos: Int) = rs.getBoolean(pos).let { if (rs.wasNull()) null else it }
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Boolean?) {
            statementHelper.setBoolean(pos, javaType, value)
        }
    }

    object DATE_T : SimpleColumnType<Date, DATE_T>("DATE", Date::class, Types.DATE) {
        override fun fromResultSet(rs: ResultSet, pos: Int): Date? = rs.getDate(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Date?) {
            statementHelper.setDate(pos, javaType, value)
        }
    }

    object TIME_T : SimpleColumnType<Time, TIME_T>("TIME", Time::class, Types.TIME) {
        override fun fromResultSet(rs: ResultSet, pos: Int): Time? = rs.getTime(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Time?) {
            statementHelper.setTime(pos, javaType, value)
        }
    }

    object TIMESTAMP_T : SimpleColumnType<Timestamp, TIMESTAMP_T>("TIMESTAMP", Timestamp::class, Types.TIMESTAMP) {
        override fun fromResultSet(rs: ResultSet, pos: Int): Timestamp? = rs.getTimestamp(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Timestamp?) {
            statementHelper.setTimestamp(pos, javaType, value)
        }
    }

    object DATETIME_T : SimpleColumnType<Timestamp, DATETIME_T>("DATETIME", Timestamp::class, Types.OTHER) {
        override fun fromResultSet(rs: ResultSet, pos: Int): Timestamp? = rs.getTimestamp(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Timestamp?) {
            statementHelper.setTimestamp(pos, javaType, value)
        }
    }

    object YEAR_T : SimpleColumnType<Date, YEAR_T>("YEAR", Date::class, Types.OTHER) {
        override fun fromResultSet(rs: ResultSet, pos: Int): Date? = rs.getDate(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: Date?) {
            statementHelper.setDate(pos, javaType, value)
        }
    }

    object TINYBLOB_T : SimpleColumnType<ByteArray, TINYBLOB_T>("TINYBLOB", ByteArray::class, Types.OTHER),
                        BoundedType {
        override val maxLen = 255
        override fun fromResultSet(rs: ResultSet, pos: Int): ByteArray? = rs.getBytes(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: ByteArray?) {
            statementHelper.setBlob(pos, javaType, if (value == null) null else ByteArrayInputStream(value))
        }
    }

    object BLOB_T : SimpleColumnType<ByteArray, BLOB_T>("BLOB", ByteArray::class, Types.BLOB), BoundedType {
        override val maxLen = 0xffff
        override fun fromResultSet(rs: ResultSet, pos: Int): ByteArray? = rs.getBytes(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: ByteArray?) {
            statementHelper.setBlob(pos, javaType, if (value == null) null else ByteArrayInputStream(value))
        }
    }

    object MEDIUMBLOB_T : SimpleColumnType<ByteArray, MEDIUMBLOB_T>("MEDIUMBLOB", ByteArray::class, Types.OTHER),
                          BoundedType {
        override val maxLen = 0xffffff
        override fun fromResultSet(rs: ResultSet, pos: Int): ByteArray? = rs.getBytes(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: ByteArray?) {
            statementHelper.setBlob(pos, javaType, if (value == null) null else ByteArrayInputStream(value))
        }
    }

    object LONGBLOB_T : SimpleColumnType<ByteArray, LONGBLOB_T>("LONGBLOB", ByteArray::class, Types.OTHER),
                        BoundedType {
        override val maxLen = Int.MAX_VALUE /*Actually it would be more*/
        override fun fromResultSet(rs: ResultSet, pos: Int): ByteArray? = rs.getBytes(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: ByteArray?) {
            statementHelper.setBlob(pos, javaType, if (value == null) null else ByteArrayInputStream(value))
        }
    }

    override fun newConfiguration(refColumn: SimpleColumn<T, S>) =
        NormalColumnConfiguration<T, S>(asS(), refColumn.name)

}

interface ICharColumnType<S : ICharColumnType<S, C>, C : ICharColumn<S, C>> : IColumnType<String, S, C>

sealed class CharColumnType<S : CharColumnType<S>>(typeName: String, type: KClass<String>, javaType: Int) :
    ColumnType<String, S, CharColumn<S>>(typeName, type, javaType), ICharColumnType<S, CharColumn<S>> {
    override fun fromResultSet(rs: ResultSet, pos: Int): String? = rs.getString(pos)

    object TINYTEXT_T : CharColumnType<TINYTEXT_T>("TINYTEXT", String::class, Types.OTHER), BoundedType {
        override val maxLen: Int get() = 255
    }

    object TEXT_T : CharColumnType<TEXT_T>("TEXT", String::class, Types.OTHER), BoundedType {
        override val maxLen: Int get() = 0xffff
    }

    object MEDIUMTEXT_T : CharColumnType<MEDIUMTEXT_T>("MEDIUMTEXT", String::class, Types.OTHER), BoundedType {
        override val maxLen: Int get() = 0xffffff
    }

    object LONGTEXT_T : CharColumnType<LONGTEXT_T>("LONGTEXT", String::class, Types.OTHER), BoundedType {
        override val maxLen: Int get() = Int.MAX_VALUE /*Actually it would be more*/
    }

    @Suppress("UNCHECKED_CAST")
    override fun newConfiguration(refColumn: CharColumn<S>) =
        CharColumnConfiguration(this as S, refColumn.name)

    override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: String?) {
        statementHelper.setString(pos, javaType, value)
    }
}

interface ILengthColumnType<T : Any, S : ILengthColumnType<T, S, C>, C : ILengthColumn<T, S, C>> : IColumnType<T, S, C>

sealed class LengthColumnType<T : Any, S : LengthColumnType<T, S>>(typeName: String, type: KClass<T>, javaType: Int) :
    ColumnType<T, S, LengthColumn<T, S>>(typeName, type, javaType), ILengthColumnType<T, S, LengthColumn<T, S>> {

    object BITFIELD_T : LengthColumnType<BooleanArray, BITFIELD_T>("BIT", BooleanArray::class, Types.OTHER) {
        @Suppress("UNCHECKED_CAST")
        override fun fromResultSet(rs: ResultSet, pos: Int) =
            (rs.getArray(pos).array as Array<Any>).let { array -> BooleanArray(array.size) { i -> array[i] as Boolean } }

        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: BooleanArray?) {
            statementHelper.setArray(pos, javaType, value)
        }
    }

    object BINARY_T : LengthColumnType<ByteArray, BINARY_T>("BINARY", ByteArray::class, Types.BINARY), BoundedType {
        override val maxLen = 255
        override fun fromResultSet(rs: ResultSet, pos: Int): ByteArray? = rs.getBytes(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: ByteArray?) {
            statementHelper.setBytes(pos, javaType, value)
        }
    }

    object VARBINARY_T : LengthColumnType<ByteArray, VARBINARY_T>("VARBINARY", ByteArray::class, Types.VARBINARY),
                         BoundedType {
        override val maxLen = 0xffff
        override fun fromResultSet(rs: ResultSet, pos: Int): ByteArray? = rs.getBytes(pos)
        override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: ByteArray?) {
            statementHelper.setBytes(pos, javaType, value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun newConfiguration(refColumn: LengthColumn<T, S>) =
        LengthColumnConfiguration(refColumn.name, this as S, refColumn.length)
}

sealed class LengthCharColumnType<S : LengthCharColumnType<S>>(typeName: String, type: KClass<String>, javaType: Int) :
    ColumnType<String, S, LengthCharColumn<S>>(typeName, type, javaType),
    ILengthColumnType<String, S, LengthCharColumn<S>>, ICharColumnType<S, LengthCharColumn<S>> {

    object CHAR_T : LengthCharColumnType<CHAR_T>("CHAR", String::class, Types.CHAR), BoundedType {
        override val maxLen: Int get() = 255
    }

    object VARCHAR_T : LengthCharColumnType<VARCHAR_T>("VARCHAR", String::class, Types.VARCHAR), BoundedType {
        override val maxLen: Int get() = 0xffff
    }

    override fun newConfiguration(refColumn: LengthCharColumn<S>) =
        LengthCharColumnConfiguration(asS(), refColumn.name, refColumn.length)

    override fun fromResultSet(rs: ResultSet, pos: Int): String? = rs.getString(pos)

    override fun setParam(statementHelper: PreparedStatementHelper, pos: Int, value: String?) {
        statementHelper.setString(pos, javaType, value)
    }

}

// @formatter:on
