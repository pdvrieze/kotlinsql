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

package uk.ac.bournemouth.kotlinsql.monadic

import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.columns.PreparedStatementHelper
import uk.ac.bournemouth.kotlinsql.metadata.TransactionIsolation
import uk.ac.bournemouth.kotlinsql.sql.use
import uk.ac.bournemouth.util.kotlin.sql.PreparedStatementHelperImpl
import java.sql.*
import java.util.*
import java.util.concurrent.Executor

@Suppress("MemberVisibilityCanBePrivate", "unused", "PropertyName")
open class MonadicDBConnection<DB : Database> constructor(val rawConnection: Connection, val db: DB) {

    init {
        rawConnection.autoCommit = false
    }

    fun close() {
        rawConnection.close()
    }

    @Throws(SQLException::class)
    fun isClosed(): Boolean = rawConnection.isClosed

    //======================================================================
    // Advanced features:

    @Throws(SQLException::class)
    fun setReadOnly(readOnly: Boolean) {
        rawConnection.isReadOnly = readOnly
    }

    @Throws(SQLException::class)
    fun isReadOnly(): Boolean = rawConnection.isReadOnly

    @Throws(SQLException::class)
    fun setCatalog(catalog: String) {
        rawConnection.catalog = catalog
    }

    @Throws(SQLException::class)
    fun getCatalog(): String = rawConnection.catalog

    @Suppress("DEPRECATION")
    fun transactionIsolation(jdbcValue: Int): TransactionIsolation {
        return TransactionIsolation.values().first { it.intValue == jdbcValue }
    }

    @Suppress("DEPRECATION")
    var transactionIsolation: TransactionIsolation
        get() = transactionIsolation(rawConnection.transactionIsolation)
        set(value) {
            rawConnection.transactionIsolation = value.intValue
        }


    //--------------------------JDBC 2.0-----------------------------
    /**
     * Retrieves the Map object associated with this Connection object.
     * @see [Connection.getTypeMap]
     */
    var typeMap: Map<String, Class<*>>
        get() = rawConnection.typeMap
        set(value) {
            rawConnection.typeMap = value
        }

    //--------------------------JDBC 3.0-----------------------------

    fun holdability(jdbc: Int): Holdability {
        return Holdability.values().first { it.jdbc == jdbc }
    }

    enum class Holdability(val jdbc: Int, @Suppress("UNUSED_PARAMETER") dummy: Unit) {
        HOLD_CURSORS_OVER_COMMIT(ResultSet.HOLD_CURSORS_OVER_COMMIT, Unit),
        CLOSE_CURSORS_AT_COMMIT(ResultSet.CLOSE_CURSORS_AT_COMMIT, Unit);
    }

    /**
     * @see [Connection.getHoldability]
     */
    var holdability: Holdability
        get() = when (rawConnection.holdability) {
            ResultSet.HOLD_CURSORS_OVER_COMMIT -> Holdability.HOLD_CURSORS_OVER_COMMIT
            ResultSet.CLOSE_CURSORS_AT_COMMIT  -> Holdability.CLOSE_CURSORS_AT_COMMIT
            else                               -> throw IllegalArgumentException()
        }
        set(value) {
            rawConnection.holdability = value.jdbc
        }

    @Throws(SQLException::class)
    fun isValid(timeout: Int): Boolean = rawConnection.isValid(timeout)

    @Throws(SQLClientInfoException::class)
    fun setClientInfo(name: String, value: String) = rawConnection.setClientInfo(name, value)

    @Throws(SQLClientInfoException::class)
    fun setClientInfo(properties: Properties) {
        rawConnection.clientInfo = properties
    }

    @Throws(SQLException::class)
    fun getClientInfo(name: String): String = rawConnection.getClientInfo(name)

    @Throws(SQLException::class)
    fun getClientInfo(): Properties? = rawConnection.clientInfo

    //--------------------------JDBC 4.1 -----------------------------

    @Throws(SQLException::class)
    fun setSchema(schema: String) {
        rawConnection.schema = schema
    }

    @Throws(SQLException::class)
    fun getSchema(): String = rawConnection.schema

    @Throws(SQLException::class)
    fun abort(executor: Executor) = rawConnection.abort(executor)

    @Throws(SQLException::class)
    fun setNetworkTimeout(executor: Executor, milliseconds: Int) =
        rawConnection.setNetworkTimeout(executor, milliseconds)


    @Throws(SQLException::class)
    fun getNetworkTimeout(): Int = rawConnection.networkTimeout

    /**
     * @see [Connection.prepareStatement]
     */
    internal inline fun <R> prepareStatement(sql: String, block: PreparedStatementHelperImpl.() -> R): R =
        rawConnection.prepareStatement(sql).use {
            PreparedStatementHelperImpl(it, sql).block()
        }

    @Throws(SQLException::class)
    internal inline fun <R> prepareStatement(
        sql: String, resultSetType: Int,
        resultSetConcurrency: Int, block: PreparedStatementHelper.() -> R,
    ): R {
        return rawConnection.prepareStatement(sql, resultSetType, resultSetConcurrency)
            .use { PreparedStatementHelperImpl(it, sql).block() }
    }

    @Throws(SQLException::class)
    internal inline fun <R> prepareStatement(
        sql: String, resultSetType: Int,
        resultSetConcurrency: Int, resultSetHoldability: Int, block: PreparedStatementHelperImpl.() -> R,
    ): R {
        return rawConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability)
            .use { PreparedStatementHelperImpl(it, sql).block() }
    }

    @Throws(SQLException::class)
    internal inline fun <R> prepareStatement(
        sql: String,
        autoGeneratedKeys: Boolean,
        block: PreparedStatementHelperImpl.() -> R,
    ): R {
        val autoGeneratedKeysFlag =
            if (autoGeneratedKeys) Statement.RETURN_GENERATED_KEYS else Statement.NO_GENERATED_KEYS
        return rawConnection.prepareStatement(sql, autoGeneratedKeysFlag)
            .use { PreparedStatementHelperImpl(it, sql).block() }
    }

    @Throws(SQLException::class)
    internal inline fun <R> prepareStatement(
        sql: String,
        autoGeneratedKeys: Int,
        block: PreparedStatementHelperImpl.() -> R,
    ): R {
        return rawConnection.prepareStatement(sql, autoGeneratedKeys)
            .use { PreparedStatementHelperImpl(it, sql).block() }
    }

    @Throws(SQLException::class)
    internal fun <R> prepareStatement(
        sql: String,
        columnIndexes: IntArray,
        block: PreparedStatementHelperImpl.() -> R,
    ): R {
        return rawConnection.prepareStatement(sql, columnIndexes)
            .use { PreparedStatementHelperImpl(it, sql).block() }
    }

    @Throws(SQLException::class)
    internal fun <R> prepareStatement(
        sql: String,
        columnNames: Array<out String>,
        block: PreparedStatementHelperImpl.() -> R,
    ): R {
        return rawConnection.prepareStatement(sql, columnNames)
            .use { PreparedStatementHelperImpl(it, sql).block() }
    }

    private class RollbackException : Exception()

}


