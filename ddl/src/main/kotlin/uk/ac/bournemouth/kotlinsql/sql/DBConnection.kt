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

package uk.ac.bournemouth.kotlinsql.sql

import uk.ac.bournemouth.kotlinsql.ConnectionMetadata
import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.TableRef
import uk.ac.bournemouth.kotlinsql.impl.WarningIterator
import java.sql.*

@Deprecated(
    "Use DBConnection2 with monadic access",
    ReplaceWith("DBConnection2<*>", "uk.ac.bournemouth.util.kotlin.sql.impl.DBConnection2")
)
@NonMonadicApi
open class DBConnection<DB: Database> constructor(val rawConnection: Connection, val db: DB) {

    @Suppress("DEPRECATION")
    fun <R> use(block: (DBConnection<DB>) -> R): R = useHelper({ it.rawConnection.close() }) {
        return transaction(block)
    }

    fun <R> raw(block: (Connection) -> R): R = block(rawConnection)

    @Suppress("DEPRECATION")
    fun <R> transaction(block: (DBConnection<DB>) -> R): R {
        rawConnection.autoCommit = false
        val savePoint = rawConnection.setSavepoint()
        try {
            return block(this).apply { commit() }
        } catch (e: Exception) {
            rawConnection.rollback(savePoint)
            throw e
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun prepareCall(sql: String) = rawConnection.prepareCall(sql)

    /**
     * @see [Connection.commit]
     */
    fun commit() = rawConnection.commit()

    fun getMetaData() = ConnectionMetadata(rawConnection.metaData)

    @Throws(SQLException::class)
    fun rollback() = rawConnection.rollback()

    /**
     * Clears all warnings reported for this `Connection` object.
     * After a call to this method, the method `getWarnings`
     * returns `null` until a new warning is
     * reported for this `Connection` object.

     * @exception SQLException SQLException if a database access error occurs
     * * or this method is called on a closed connection
     */
    @Throws(SQLException::class)
    fun clearWarnings() = rawConnection.clearWarnings()

    @Throws(SQLException::class)
    fun setSavepoint(): Savepoint = rawConnection.setSavepoint()

    @Throws(SQLException::class)
    fun setSavepoint(name: String): Savepoint = rawConnection.setSavepoint(name)

    @Throws(SQLException::class)
    fun rollback(savepoint: Savepoint) = rawConnection.rollback(savepoint)

    @Throws(SQLException::class)
    fun releaseSavepoint(savepoint: Savepoint) = rawConnection.releaseSavepoint(savepoint)

    @Throws(SQLException::class)
    fun createClob(): Clob = rawConnection.createClob()

    @Throws(SQLException::class)
    fun createBlob(): Blob = rawConnection.createBlob()

    @Throws(SQLException::class)
    fun createNClob(): NClob = rawConnection.createNClob()

    @Throws(SQLException::class)
    fun createSQLXML(): SQLXML = rawConnection.createSQLXML()

    @Throws(SQLException::class)
    fun createArrayOf(typeName: String, elements: Array<Any>): java.sql.Array? =
        rawConnection.createArrayOf(typeName, elements)

    @Throws(SQLException::class)
    fun createStruct(typeName: String, attributes: Array<Any>): Struct =
        rawConnection.createStruct(typeName, attributes)

    fun hasTable(tableRef: TableRef): Boolean {
        return getMetaData().getTables(null, null, tableRef._name, null).use { rs ->
            rs.next()
        }
    }

    /** @see [Connection.getAutoCommit] */
    var autoCommit: Boolean
        get() = rawConnection.autoCommit
        set(value) {
            rawConnection.autoCommit = value
        }

    /**
     * Retrieves the first warning reported by calls on this
     * `Connection` object.  If there is more than one
     * warning, subsequent warnings will be chained to the first one
     * and can be retrieved by calling the method
     * `SQLWarning.getNextWarning` on the warning
     * that was retrieved previously.
     *
     * This method may not be
     * called on a closed connection; doing so will cause an
     * `SQLException` to be thrown.

     * Note: Subsequent warnings will be chained to this
     * SQLWarning.

     * @return the first `SQLWarning` object or `null`
     * *         if there are none
     *
     * @exception SQLException if a database access error occurs or
     * *            this method is called on a closed connection
     *
     * @see SQLWarning
     */
    val warningsIt: Iterator<SQLWarning> get() = WarningIterator(rawConnection.warnings)
    val warnings: Sequence<SQLWarning>
        get() = object : Sequence<SQLWarning> {
            override fun iterator(): Iterator<SQLWarning> = warningsIt
        }

    enum class TransactionIsolation constructor(val intValue: Int, @Suppress("UNUSED_PARAMETER") dummy: Unit) {
        TRANSACTION_NONE(Connection.TRANSACTION_NONE, Unit),
        TRANSACTION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED, Unit),
        TRANSACTION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED, Unit),
        TRANSACTION_REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ, Unit),
        TRANSACTION_SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE, Unit),
    }

}