/*
 * Copyright (c) 2017.
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

@file:Suppress("PropertyName", "FunctionName", "unused")

package uk.ac.bournemouth.kotlinsql

import uk.ac.bournemouth.kotlinsql.columns.*
import uk.ac.bournemouth.kotlinsql.columns.CharColumnType.*
import uk.ac.bournemouth.kotlinsql.columns.DecimalColumnType.DECIMAL_T
import uk.ac.bournemouth.kotlinsql.columns.DecimalColumnType.NUMERIC_T
import uk.ac.bournemouth.kotlinsql.columns.LengthCharColumnType.CHAR_T
import uk.ac.bournemouth.kotlinsql.columns.LengthCharColumnType.VARCHAR_T
import uk.ac.bournemouth.kotlinsql.columns.LengthColumnType.*
import uk.ac.bournemouth.kotlinsql.columns.NumericColumnType.*
import uk.ac.bournemouth.kotlinsql.columns.SimpleColumnType.*
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

/**
 * An interface that provides various methods and "properties" to provide column configurations based on type
 */
interface SqlTypesMixin {

    // @formatter:off
    fun BIT(name:String? = null, block: NormalColumnConfiguration<Boolean, BIT_T>.() -> Unit) = NormalColumnConfiguration(BIT_T, name).apply(block)
    fun BIT(length: Int, noblock: BaseLengthColumnConfiguration<BooleanArray, BITFIELD_T, LengthColumn<BooleanArray, BITFIELD_T>>.() -> Unit) = BIT(name = null, length = length, block = noblock)
    fun BIT(name:String? = null, length: Int, block: BaseLengthColumnConfiguration<BooleanArray, BITFIELD_T, LengthColumn<BooleanArray, BITFIELD_T>>.() -> Unit) = LengthColumnConfiguration(name, BITFIELD_T, length).apply(block)
    fun TINYINT(name:String? = null, block: NumberColumnConfiguration<Byte, TINYINT_T>.() -> Unit) = NumberColumnConfiguration(TINYINT_T, name).apply(block)
    fun SMALLINT(name:String? = null, block: NumberColumnConfiguration<Short, SMALLINT_T>.() -> Unit) = NumberColumnConfiguration(SMALLINT_T, name).apply(block)
    fun MEDIUMINT(name:String? = null, block: NumberColumnConfiguration<Int, MEDIUMINT_T>.() -> Unit) = NumberColumnConfiguration(MEDIUMINT_T, name).apply(block)
    fun INT(name:String? = null, block: NumberColumnConfiguration<Int, INT_T>.() -> Unit) = NumberColumnConfiguration(INT_T, name).apply(block)
    fun BIGINT(name:String? = null, block: NumberColumnConfiguration<Long, BIGINT_T>.() -> Unit) = NumberColumnConfiguration(BIGINT_T, name).apply(block)
    fun FLOAT(name:String? = null, block: NumberColumnConfiguration<Float, FLOAT_T>.() -> Unit) = NumberColumnConfiguration(FLOAT_T, name).apply(block)
    fun DOUBLE(name:String? = null, block: NumberColumnConfiguration<Double, DOUBLE_T>.() -> Unit) = NumberColumnConfiguration(DOUBLE_T, name).apply(block)
    fun DECIMAL(precision: Int = -1, scale: Int = -1, noblock: DecimalColumnConfiguration<DECIMAL_T>.() -> Unit) = DECIMAL(name = null, precision = precision, scale = scale, block = noblock)
    fun DECIMAL(name:String? = null, precision: Int = -1, scale: Int = -1, block: DecimalColumnConfiguration<DECIMAL_T>.() -> Unit) = DecimalColumnConfiguration(DECIMAL_T, name, precision, scale).apply(block)
    fun NUMERIC(precision: Int = -1, scale: Int = -1, noblock: DecimalColumnConfiguration<NUMERIC_T>.() -> Unit) = NUMERIC(name = null, precision = precision, scale = scale, block = noblock)
    fun NUMERIC(name:String? = null, precision: Int = -1, scale: Int = -1, block: DecimalColumnConfiguration<NUMERIC_T>.() -> Unit) = DecimalColumnConfiguration(NUMERIC_T, name, precision, scale).apply(block)
    fun DATE(name:String? = null, block: NormalColumnConfiguration<Date, DATE_T>.() -> Unit) = NormalColumnConfiguration(DATE_T, name).apply(block)
    fun TIME(name:String? = null, block: NormalColumnConfiguration<Time, TIME_T>.() -> Unit) = NormalColumnConfiguration(TIME_T, name).apply(block)
    fun TIMESTAMP(name:String? = null, block: NormalColumnConfiguration<Timestamp, TIMESTAMP_T>.() -> Unit) = NormalColumnConfiguration(TIMESTAMP_T, name).apply(block)
    fun DATETIME(name:String? = null, block: NormalColumnConfiguration<Timestamp, DATETIME_T>.() -> Unit) = NormalColumnConfiguration(DATETIME_T, name).apply(block)
    fun YEAR(name:String? = null, block: NormalColumnConfiguration<Date, YEAR_T>.() -> Unit) = NormalColumnConfiguration(YEAR_T, name).apply(block)
    fun CHAR(length: Int = -1, block: LengthCharColumnConfiguration<CHAR_T>.() -> Unit) = CHAR(name = null, length = length, block = block)
    fun CHAR(name:String? = null, length: Int = -1, block: LengthCharColumnConfiguration<CHAR_T>.() -> Unit) = LengthCharColumnConfiguration(CHAR_T, name, length).apply(block)
    fun VARCHAR(length: Int, noblock: LengthCharColumnConfiguration<VARCHAR_T>.() -> Unit) = VARCHAR(name = null, length = length, block = noblock)
    fun VARCHAR(name:String? = null, length: Int, block: LengthCharColumnConfiguration<VARCHAR_T>.() -> Unit) = LengthCharColumnConfiguration(VARCHAR_T, name, length).apply(block)
    fun BINARY(length: Int, noblock: BaseLengthColumnConfiguration<ByteArray, BINARY_T, LengthColumn<ByteArray, BINARY_T>>.() -> Unit) = BINARY(name = null, length = length, block = noblock)
    fun BINARY(name:String? = null, length: Int, block: BaseLengthColumnConfiguration<ByteArray, BINARY_T, LengthColumn<ByteArray, BINARY_T>>.() -> Unit) = LengthColumnConfiguration(name, BINARY_T, length).apply(block)
    fun VARBINARY(length: Int, noblock: BaseLengthColumnConfiguration<ByteArray, VARBINARY_T, LengthColumn<ByteArray, VARBINARY_T>>.() -> Unit) = VARBINARY(name = null, length = length, block = noblock)
    fun VARBINARY(name:String? = null, length: Int, block: BaseLengthColumnConfiguration<ByteArray, VARBINARY_T, LengthColumn<ByteArray, VARBINARY_T>>.() -> Unit) = LengthColumnConfiguration(name, VARBINARY_T, length).apply(block)
    fun TINYBLOB(name:String? = null, block: NormalColumnConfiguration<ByteArray, TINYBLOB_T>.() -> Unit) = NormalColumnConfiguration(TINYBLOB_T, name).apply(block)
    fun BLOB(name:String? = null, block: NormalColumnConfiguration<ByteArray, BLOB_T>.() -> Unit) = NormalColumnConfiguration(BLOB_T, name).apply(block)
    fun MEDIUMBLOB(name:String? = null, block: NormalColumnConfiguration<ByteArray, MEDIUMBLOB_T>.() -> Unit) = NormalColumnConfiguration(MEDIUMBLOB_T, name).apply(block)
    fun LONGBLOB(name:String? = null, block: NormalColumnConfiguration<ByteArray, LONGBLOB_T>.() -> Unit) = NormalColumnConfiguration(LONGBLOB_T, name).apply(block)
    fun TINYTEXT(name:String? = null, block: CharColumnConfiguration<TINYTEXT_T>.() -> Unit) = CharColumnConfiguration(TINYTEXT_T, name).apply(block)
    fun TEXT(name:String? = null, block: CharColumnConfiguration<TEXT_T>.() -> Unit) = CharColumnConfiguration(TEXT_T, name).apply(block)
    fun MEDIUMTEXT(name:String? = null, block: CharColumnConfiguration<MEDIUMTEXT_T>.() -> Unit) = CharColumnConfiguration(MEDIUMTEXT_T, name).apply(block)
    fun LONGTEXT(name:String? = null, block: CharColumnConfiguration<LONGTEXT_T>.() -> Unit) = CharColumnConfiguration(LONGTEXT_T, name).apply(block)

    /* Versions without configuration closure */
    val BIT get()=BIT()
    fun BIT(name:String? = null) = NormalColumnConfiguration(BIT_T, name)
    fun BIT(length:Int) = BIT(name = null, length = length)
    fun BIT(name:String? = null, length:Int) = LengthColumnConfiguration(name, BITFIELD_T, length)

    val TINYINT get()=TINYINT()
    fun TINYINT(name:String? = null) = NumberColumnConfiguration(TINYINT_T, name)
    val SMALLINT get()=SMALLINT()
    fun SMALLINT(name:String? = null) = NumberColumnConfiguration(SMALLINT_T, name)
    val MEDIUMINT get()=MEDIUMINT()
    fun MEDIUMINT(name:String? = null) = NumberColumnConfiguration(MEDIUMINT_T, name)
    val INT get()=INT()
    fun INT(name:String? = null) = NumberColumnConfiguration(INT_T, name)
    val BIGINT get()=BIGINT()
    fun BIGINT(name:String? = null) = NumberColumnConfiguration(BIGINT_T, name)

    val FLOAT get()=FLOAT()
    fun FLOAT(name:String? = null) = NumberColumnConfiguration(FLOAT_T, name)
    val DOUBLE get()=INT()
    fun DOUBLE(name:String? = null) = NumberColumnConfiguration(DOUBLE_T, name)
    val DECIMAL get()=DECIMAL()
    fun DECIMAL(precision:Int=-1, scale:Int=-1) = DECIMAL(name = null, precision = precision, scale = scale)
    fun DECIMAL(name:String? = null, precision:Int=-1, scale:Int=-1) = DecimalColumnConfiguration(DECIMAL_T, name, precision, scale)

    val NUMERIC get() = NUMERIC()
    fun NUMERIC(precision: Int = -1, scale: Int = -1) = NUMERIC(name = null, precision = precision, scale = scale)
    fun NUMERIC(name:String? = null, precision: Int = -1, scale: Int = -1) = DecimalColumnConfiguration(NUMERIC_T, name, precision, scale)

    val DATE get() = DATE()
    fun DATE(name: String? = null) = NormalColumnConfiguration(
        DATE_T, name)
    val TIME get()=TIME()
    fun TIME(name:String? = null) = NormalColumnConfiguration(TIME_T, name)
    val TIMESTAMP get()=TIMESTAMP()
    fun TIMESTAMP(name:String? = null) = NormalColumnConfiguration(TIMESTAMP_T, name)
    val DATETIME get()=DATETIME()
    fun DATETIME(name:String? = null) = NormalColumnConfiguration(DATETIME_T, name)
    val YEAR get()=YEAR()
    fun YEAR(name:String? = null) = NormalColumnConfiguration(YEAR_T, name)

    val CHAR get()=CHAR()
    fun CHAR(length: Int = -1) = CHAR(name = null, length = length)
    fun CHAR(name:String? = null, length: Int = -1) = LengthCharColumnConfiguration(CHAR_T, name, length)
    val VARCHAR get()=VARCHAR()
    fun VARCHAR(length: Int = -1) = VARCHAR(name = null, length = length)
    fun VARCHAR(name:String? = null, length: Int) = LengthCharColumnConfiguration(VARCHAR_T, name, length)

    val BINARY get()=BINARY()
    fun BINARY(length: Int = -1) = BINARY(name = null, length = length)
    fun BINARY(name:String? = null, length: Int) = LengthColumnConfiguration(name, BINARY_T, length)
    val VARBINARY get()=VARBINARY()
    fun VARBINARY(length: Int = -1) = VARBINARY(name = null, length = length)
    fun VARBINARY(name:String? = null, length: Int) = LengthColumnConfiguration(name, VARBINARY_T, length)

    val TINYBLOB get()=TINYBLOB()
    fun TINYBLOB(name:String? = null) = NormalColumnConfiguration(TINYBLOB_T, name)
    val BLOB get()=BLOB()
    fun BLOB(name:String? = null) = NormalColumnConfiguration(BLOB_T, name)
    val MEDIUMBLOB get()=MEDIUMBLOB()
    fun MEDIUMBLOB(name:String? = null) = NormalColumnConfiguration(MEDIUMBLOB_T, name)
    fun LONGBLOB(name:String? = null) = NormalColumnConfiguration(LONGBLOB_T, name)
    val TINYTEXT get()=TINYTEXT()
    fun TINYTEXT(name:String? = null) = CharColumnConfiguration(TINYTEXT_T, name)
    val TEXT get()=TEXT()
    fun TEXT(name:String? = null) = CharColumnConfiguration(TEXT_T, name)
    val MEDIUMTEXT get()=MEDIUMTEXT()
    fun MEDIUMTEXT(name:String? = null) = CharColumnConfiguration(MEDIUMTEXT_T, name)
    val LONGTEXT get()=LONGTEXT()
    fun LONGTEXT(name:String? = null) = CharColumnConfiguration(LONGTEXT_T, name)
    // @formatter:on

}