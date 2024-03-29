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

package io.github.pdvrieze.kotlinsql.direct.impl

import io.github.pdvrieze.kotlinsql.ddl.columns.PreparedStatementHelper
import java.io.InputStream
import java.math.BigDecimal
import java.sql.*
import java.util.ArrayList

@Suppress("NOTHING_TO_INLINE")
class DDLPreparedStatementHelper constructor(val statement: PreparedStatement, private val queryString:String) :
    PreparedStatementHelper {
    /**
     * Get raw access to the underlying prepared statement. This should normally not be needed, but is available when it is.
     */
    inline fun <R> raw(block: (PreparedStatement) -> R): R = block(statement)

    private fun warningsIterator(): Iterator<SQLWarning> = object : AbstractIterator<SQLWarning>() {
        override fun computeNext() {
            val w = statement.warnings
            if (w != null) {
                setNext(w)
            } else {
                done()
            }
        }
    }

    fun setParam(index: Int, value: Int?) = when (value) {
        null -> statement.setNull(index, Types.INTEGER)
        else -> statement.setInt(index, value)
    }

    fun setParam(index: Int, value: Long?) = when (value) {
        null -> statement.setNull(index, Types.BIGINT)
        else -> statement.setLong(index, value)
    }

    fun setParam(index: Int, value: CharSequence?) = when (value) {
        null -> statement.setNull(index, Types.VARCHAR)
        else -> statement.setString(index, value.toString())
    }

    fun setParam(index: Int, value: Boolean?) = when (value) {
        null -> statement.setNull(index, Types.BOOLEAN)
        else -> statement.setBoolean(index, value)
    }

    fun setParam(index: Int, value: Byte?) = when (value) {
        null -> statement.setNull(index, Types.TINYINT)
        else -> statement.setByte(index, value)
    }

    fun setParam(index: Int, value: Short?) = when (value) {
        null -> statement.setNull(index, Types.SMALLINT)
        else -> statement.setShort(index, value)
    }

    @Suppress("unused", "NOTHING_TO_INLINE")
    class ParamHelper_(val sh: DDLPreparedStatementHelper) {

        var index = 2
        inline operator fun Int?.unaryPlus(): Unit = sh.setParam(index++, this)
        inline operator fun Long?.unaryPlus(): Unit = sh.setParam(index++, this)
        inline operator fun String?.unaryPlus(): Unit = sh.setParam(index++, this)
        inline operator fun Boolean?.unaryPlus(): Unit = sh.setParam(index++, this)
        inline operator fun Byte?.unaryPlus(): Unit = sh.setParam(index++, this)
        inline operator fun Short?.unaryPlus(): Unit = sh.setParam(index++, this)

        inline operator fun plus(value: Int?): ParamHelper_ { sh.setParam(index++, value); return this }
        inline operator fun plus(value: Long?): ParamHelper_ { sh.setParam(index++, value); return this }
        inline operator fun plus(value: String?): ParamHelper_ { sh.setParam(index++, value); return this }
        inline operator fun plus(value: Boolean?): ParamHelper_ { sh.setParam(index++, value); return this }
        inline operator fun plus(value: Byte?): ParamHelper_ { sh.setParam(index++, value); return this }
        inline operator fun plus(value: Short?): ParamHelper_ { sh.setParam(index++, value); return this }

        inline operator fun plus(intValue: Int): ParamHelper_ { sh.statement.setInt(index++, intValue); return this }
        inline operator fun plus(longValue: Long): ParamHelper_ { sh.statement.setLong(index++, longValue); return this }
        @JvmName("plusNotNull")
        inline operator fun plus(stringValue: String): ParamHelper_ { sh.statement.setString(index++, stringValue); return this }
        inline operator fun plus(booleanValue: Boolean): ParamHelper_ { sh.statement.setBoolean(index++, booleanValue); return this }
        inline operator fun plus(byteValue: Byte): ParamHelper_ { sh.statement.setByte(index++, byteValue); return this }
        inline operator fun plus(shortValue: Short): ParamHelper_ { sh.statement.setShort(index++, shortValue); return this }
    }

//  inline fun <R> params(block: ParamHelper_.() -> R) = ParamHelper_(this).block()

    @Suppress("UNUSED_PARAMETER")
    override fun setArray(pos:Int, sqlType: Int, value: BooleanArray?) {
        throw UnsupportedOperationException("Setting bit arrays is not yet supported")
    }

    override fun setBlob(pos:Int, sqlType: Int, value: InputStream?) {
        if (value ==null) statement.setNull(pos, Types.BOOLEAN) else statement.setBlob(pos, value)
    }

    override fun setBoolean(pos:Int, sqlType: Int, value: Boolean?) {
        if (value ==null) statement.setNull(pos, Types.BOOLEAN) else statement.setBoolean(pos, value)
    }

    override fun setByte(pos:Int, sqlType: Int, value: Byte?) {
        if (value ==null) statement.setNull(pos, Types.TINYINT) else statement.setByte(pos, value)
    }

    override fun setBytes(pos:Int, sqlType: Int, value: ByteArray?) {
        if (value ==null) statement.setNull(pos, sqlType) else statement.setBytes(pos, value)
    }

    override fun setDecimal(pos:Int, sqlType: Int, value: BigDecimal?) {
        if (value ==null) statement.setNull(pos, Types.DECIMAL) else statement.setBigDecimal(pos, value)
    }

    override fun setDate(pos:Int, sqlType: Int, value: Date?) {
        if (value ==null) statement.setNull(pos, Types.DATE) else statement.setDate(pos, value)
    }

    override fun setDouble(pos:Int, sqlType: Int, value: Double?) {
        if (value ==null) statement.setNull(pos, Types.DOUBLE) else statement.setDouble(pos, value)
    }

    override fun setFloat(pos:Int, sqlType: Int, value: Float?) {
        if (value ==null) statement.setNull(pos, Types.FLOAT) else statement.setFloat(pos, value)
    }

    override fun setInt(pos:Int, sqlType: Int, value: Int?) {
        if (value ==null) statement.setNull(pos, Types.INTEGER) else statement.setInt(pos, value)
    }

    override fun setLong(pos:Int, sqlType: Int, value: Long?) {
        if (value ==null) statement.setNull(pos, Types.BIGINT) else statement.setLong(pos, value)
    }

    override fun setNumeric(pos:Int, sqlType: Int, value: BigDecimal?) {
        if (value ==null) statement.setNull(pos, Types.NUMERIC) else statement.setBigDecimal(pos, value)
    }

    override fun setShort(pos:Int, sqlType: Int, value: Short?) {
        if (value ==null) statement.setNull(pos, Types.SMALLINT) else statement.setShort(pos, value)
    }

    override fun setString(pos:Int, sqlType: Int, value: String?) {
        if (value ==null) statement.setNull(pos, Types.VARCHAR) else statement.setString(pos, value)
    }

    override fun setTime(pos:Int, sqlType: Int, value: Time?) {
        if (value ==null) statement.setNull(pos, Types.TIME) else statement.setTime(pos, value)
    }

    override fun setTimestamp(pos:Int, sqlType: Int, value: Timestamp?) {
        if (value ==null) statement.setNull(pos, Types.TIMESTAMP) else statement.setTimestamp(pos, value)
    }



    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(value:Int?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(value:Long?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(value:String?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(value:Boolean?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(value:Byte?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(value:Short?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }

    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(intValue:Int):ParamHelper_ { statement.setInt(1, intValue); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(longValue:Long):ParamHelper_ { statement.setLong(1, longValue); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    @JvmName("paramsNotNull")
    inline fun params(stringValue:String):ParamHelper_ { statement.setString(1, stringValue); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(booleanValue:Boolean):ParamHelper_ { statement.setBoolean(1, booleanValue); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(byteValue:Byte):ParamHelper_ { statement.setByte(1, byteValue); return ParamHelper_(this) }
    /** Start setting parameters. This returns a helper for adding the next ones. */
    inline fun params(shortValue:Short):ParamHelper_ { statement.setShort(1, shortValue); return ParamHelper_(this) }

    @Suppress("MemberVisibilityCanBePrivate")
    inline fun <R> withResultSet(block: (ResultSet) -> R) = statement.resultSet.use(block)

    inline fun <R> withGeneratedKeys(block: (ResultSet) -> R) = statement.generatedKeys.use(block)


    inline val Int.i get() = this
    inline val Double.d get() = this
    @Suppress("PropertyName")
    inline val Long.L get() = this
    inline val Float.f get() = this

    inline fun <R> execute(block: (ResultSet) -> R) = executeQuery().use(block)

    /**
     * Execute the block for every result in the ResultSet. This is [execute] and [Sequence.forEach] combined.
     */
    inline fun <R> executeEach(block: (ResultSet) -> R): List<R> = execute { resultSet ->
        ArrayList<R>().apply {
            add(block(resultSet))
        }
    }

    /**
     * Get a a string as the single return column.
     */
    fun stringResult(): String? {
        return execute { rs ->
            if(rs.next()) {
                return rs.getString(1)
            }
            return null
        }
    }

    fun executeHasRows(): Boolean = execute() && withResultSet { it.next() }

    fun execute(): Boolean {
        try {
            return statement.execute()
        } catch (e:SQLException) {
            throw SQLException("Error executing statement: $queryString", e)
        }
    }

    fun executeUpdate(): Int {
        try {
            return statement.executeUpdate()
        } catch (e:SQLException) {
            throw SQLException("Error executing statement: $queryString", e)
        }
    }

    fun executeQuery(): ResultSet {
        try {
            return statement.executeQuery()
        } catch (e:SQLException) {
            throw SQLException("Error executing statement: $queryString", e)
        }
    }

    fun addBatch(){
        try {
            statement.addBatch()
        } catch (e:SQLException) {
            throw SQLException("Error executing statement: $queryString", e)
        }
    }

    fun executeBatch(): IntArray {
        try {
            return statement.executeBatch()
        } catch (e:SQLException) {
            throw SQLException("Error executing statement: $queryString", e)
        }
    }

}