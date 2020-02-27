/*
 * Copyright (c) 2018.
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

@file:Suppress("unused")

package uk.ac.bournemouth.util.kotlin.sql

import uk.ac.bournemouth.kotlinsql.*
import uk.ac.bournemouth.util.kotlin.sql.impl.DBConnection2
import java.sql.*
import javax.sql.DataSource

/**
 * Create a new connection to the database and execute the block body on that connection. Close it after use.
 */
@Suppress("unused", "DEPRECATION")
@Deprecated("Use new version", ReplaceWith("this.withConnection(db, block)", "uk.ac.bournemouth.util.kotlin.sql.withConnection"))
inline fun <R> DataSource.connection(db: Database, block: (DBConnection) -> R): R =
    this.connection.use {
        return DBConnection(connection, db).let(block)
    }

inline fun <DB : Database, R> DataSource.withConnection(db: DB, block: (DBConnection2<DB>) -> R): R =
    this.connection.use {
        return DBConnection2(connection, db).let(block)
    }

interface DBContext<DB: Database> {
    val db: DB
}

interface DBTransactionBase<DB : Database, T>: DBContext<DB> {

    fun <Output> map(action: DBAction<DB, in T, Output>): DBTransaction<DB, Output>

    fun <Output> flatmap(actions: DBContext<DB>.() -> Iterable<DBAction<DB, Unit, Output>>): DBTransaction<DB, Output>  {
        @Suppress("UNCHECKED_CAST")
        val iterable = actions() as Iterable<DBAction<DB, Any?, T>>

        return iterable.fold(this) { accum, act ->
            accum.map(act)
        } as DBTransaction<DB, Output>
    }

    @Deprecated("At the very least it is the wrong name")
    fun <Output> flatmapOld(action: DBAction<DB, T, DBTransaction<DB,Output>>): DBTransaction<DB, Output> = map {
        action(it).evaluateNow()
    }

    /**
     * Apply a single action to the transaction. This should commit if at top level,
     * but not if done from a context
     */
    fun <Output> apply(action: DBAction<DB, T, Output>): Output {
        return map(action).commit()
    }

    interface ActionContext<DB : Database>: DBTransactionBase<DB, Unit> {
        override val db: DB
        val connection: DBConnection2<DB>

        // fun commit() // should intermediate commit be allowed?
        fun rollback()

        fun <T> DBTransaction<DB, T>.get(): T

        fun onCommit(action: ActionContext<DB>.() -> Unit)
        fun onRollback(action: ActionContext<DB>.() -> Unit)

        fun <R> withMetaData(action: ConnectionMetadata.() -> R): R {
            return ConnectionMetadata(connection.rawConnection.metaData).action()
        }

        fun onFinish(action: ActionContext<DB>.() -> Unit) {
            onCommit(action)
            onRollback(action)
        }

        fun closeOnFinish(resultSet: ResultSet) = onFinish { resultSet.close() }

        val warningsIt: Iterator<SQLWarning> get() = WarningIterator(connection.rawConnection.warnings)
        val warnings: Sequence<SQLWarning>
            get() = object : Sequence<SQLWarning> {
                override fun iterator(): Iterator<SQLWarning> = warningsIt
            }

        fun DB.ensuretables(retainExtraColumns: Boolean = true) {
            this.ensureTables(connection, retainExtraColumns)
        }

    }

}


fun <DB: Database> DBTransactionBase.ActionContext<DB>.hasTable(tableRef: TableRef): Boolean {
    return withMetaData {
        getTables(null, null, tableRef._name, null).use { rs ->
            rs.next()
        }
    }
}

interface DBTransaction<DB : Database, T> : DBTransactionBase<DB, T> {
    fun evaluateNow(): T

    fun commit(): T
}

inline fun <DB : Database, T> DBTransaction<DB, T>.require(crossinline condition: (T)-> Boolean, crossinline lazyMessage: DBTransactionBase.ActionContext<DB>.()-> String) = map {
    require(condition(it)) { lazyMessage() }
}

inline fun <DB : Database> DBTransaction<DB, Int>.requireNonZero(crossinline lazyMessage: DBTransactionBase.ActionContext<DB>.() -> String) = map {
    require(it > 0) { lazyMessage() }
}

//inline fun <R> DataSource.connection(username: String, password: String, block: (DBConnection) -> R) = getConnection(username, password).use { connection(it, block) }

@Deprecated("Use DBConnection2 with monadic access", ReplaceWith("DBConnection2<*>", "uk.ac.bournemouth.util.kotlin.sql.impl.DBConnection2"))
open class DBConnection constructor(rawConnection: Connection, db: Database) :
    DBConnection2<Database>(rawConnection, db) {
    @Suppress("DEPRECATION")
    fun <R> use(block: (DBConnection) -> R): R = useHelper({ it.rawConnection.close() }) {
        return transaction(block)
    }

    fun <R> raw(block: (Connection) -> R): R = block(rawConnection)

    @Suppress("DEPRECATION")
    fun <R> transaction(block: (DBConnection) -> R): R {
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

    @Throws(SQLException::class)
    @Deprecated("Use the typed version", ReplaceWith("this.holdability = Holdability(holdability)"))
    fun setHoldability(holdability: Int) {
        rawConnection.holdability = holdability
    }

    @Throws(SQLException::class)
    @Deprecated("Use the typed version", ReplaceWith("this.holdability.jdbc"))
    fun getHoldability() = rawConnection.holdability

    @Deprecated(
        "Don't use this, just use Connection's version",
        replaceWith = ReplaceWith("Connection.TRANSACTION_NONE", "java.sql.Connection")
               )
    val TRANSACTION_NONE = Connection.TRANSACTION_NONE

    @Deprecated(
        "Don't use this, just use Connection's version",
        replaceWith = ReplaceWith("Connection.TRANSACTION_READ_UNCOMMITTED", "java.sql.Connection")
               )
    val TRANSACTION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED

    @Deprecated(
        "Don't use this, just use Connection's version",
        replaceWith = ReplaceWith("Connection.TRANSACTION_READ_COMMITTED", "java.sql.Connection")
               )
    val TRANSACTION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED

    @Deprecated(
        "Don't use this, just use Connection's version",
        replaceWith = ReplaceWith("Connection.TRANSACTION_REPEATABLE_READ", "java.sql.Connection")
               )
    val TRANSACTION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ

    @Deprecated(
        "Don't use this, just use Connection's version",
        replaceWith = ReplaceWith("Connection.TRANSACTION_SERIALIZABLE", "java.sql.Connection")
               )
    val TRANSACTION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE
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

internal typealias DBAction<DB, In, Out> = DBTransactionBase.ActionContext<DB>.(In) -> Out
internal typealias DBActionObj<DB, In, Out> = Function2<DBTransactionBase.ActionContext<DB>, In, Out>

/**
 * Executes the given [block] function on this resource and then closes it down correctly whether an exception
 * is thrown or not.
 *
 * @param block a function to process this closable resource.
 * @return the result of [block] function on this closable resource.
 */
inline fun <T : Connection, R> T.use(block: (T) -> R) = useHelper({ it.close() }, block)

@Suppress("unused")
inline fun <T : Connection, R> T.useTransacted(block: (T) -> R): R = useHelper({ it.close() }) {
    it.autoCommit = false
    try {
        val result = block(it)
        it.commit()
        return result
    } catch (e: Exception) {
        it.rollback()
        throw e
    }

}


inline fun <T, R> T.useHelper(close: (T) -> Unit, block: (T) -> R): R {
    var closed = false
    try {
        return block(this)
    } catch (e: Exception) {
        closed = true
        try {
            close(this)
        } catch (closeException: Exception) {
            // drop for now.
        }
        throw e
    } finally {
        if (!closed) {
            close(this)
        }
    }
}