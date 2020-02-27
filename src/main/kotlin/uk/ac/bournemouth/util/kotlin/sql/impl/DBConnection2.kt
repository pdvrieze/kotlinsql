/*
 * Copyright (c) 2020.
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

package uk.ac.bournemouth.util.kotlin.sql.impl

import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.util.kotlin.sql.*
import uk.ac.bournemouth.util.kotlin.sql.DBAction
import uk.ac.bournemouth.util.kotlin.sql.DBActionObj
import java.sql.*
import java.util.*
import java.util.concurrent.Executor

@Suppress("MemberVisibilityCanBePrivate", "unused", "PropertyName")
open class DBConnection2<DB : Database> constructor(val rawConnection: Connection, override val db: DB) :
    DBTransactionBase<DB, Unit> {

    init {
        rawConnection.autoCommit = false
    }

    fun close() {
        rawConnection.close()
    }

    override fun <O> map(action: DBTransactionBase.ActionContext<DB>.(Unit) -> O): DBTransaction<DB, O> {
        return DBTransactionImpl(
            this,
            db,
            action
                                                                                                        )
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
    fun transactionIsolation(jdbcValue: Int): DBConnection.TransactionIsolation {
        return DBConnection.TransactionIsolation.values().first { it.intValue == jdbcValue }
    }

    @Suppress("DEPRECATION")
    var transactionIsolation: DBConnection.TransactionIsolation
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
    inline fun <R> prepareStatement(sql: String, block: StatementHelper.() -> R) =
        rawConnection.prepareStatement(sql).use {
            StatementHelper(it, sql).block()
        }

    @Throws(SQLException::class)
    inline fun <R> prepareStatement(
        sql: String, resultSetType: Int,
        resultSetConcurrency: Int, block: StatementHelper.() -> R
                            ): R {
        return rawConnection.prepareStatement(sql, resultSetType, resultSetConcurrency)
            .use { StatementHelper(it, sql).block() }
    }

    @Throws(SQLException::class)
    inline fun <R> prepareStatement(
        sql: String, resultSetType: Int,
        resultSetConcurrency: Int, resultSetHoldability: Int, block: StatementHelper.() -> R
                            ): R {
        return rawConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability)
            .use { StatementHelper(it, sql).block() }
    }

    @Throws(SQLException::class)
    inline fun <R> prepareStatement(sql: String, autoGeneratedKeys: Boolean, block: StatementHelper.() -> R): R {
        val autoGeneratedKeysFlag =
            if (autoGeneratedKeys) Statement.RETURN_GENERATED_KEYS else Statement.NO_GENERATED_KEYS
        return rawConnection.prepareStatement(sql, autoGeneratedKeysFlag).use { StatementHelper(
            it,
            sql
                                                                                                                                                    )
            .block() }
    }

    @Throws(SQLException::class)
    inline fun <R> prepareStatement(sql: String, autoGeneratedKeys: Int, block: StatementHelper.() -> R): R {
        return rawConnection.prepareStatement(sql, autoGeneratedKeys).use { StatementHelper(
            it,
            sql
                                                                                                                                                )
            .block() }
    }

    @Throws(SQLException::class)
    fun <R> prepareStatement(sql: String, columnIndexes: IntArray, block: StatementHelper.() -> R): R {
        return rawConnection.prepareStatement(sql, columnIndexes).use { StatementHelper(
            it,
            sql
                                                                                                                                            )
            .block() }
    }

    @Throws(SQLException::class)
    fun <R> prepareStatement(sql: String, columnNames: Array<out String>, block: StatementHelper.() -> R): R {
        return rawConnection.prepareStatement(sql, columnNames).use { StatementHelper(
            it,
            sql
                                                                                                                                          )
            .block() }
    }

    private class RollbackException: Exception()

    private class ContextImpl<DB : Database>(
        override val connection: DBConnection2<DB>,
        override val db: DB,
        action: DBActionObj<DB, Unit, *>
                                                                                           ) :
        DBTransactionBase.ActionContext<DB> {
        private val actions = ArrayDeque<DBActionObj<DB, *, *>>()
            .apply { add(action) }
        private val onCommitActions = mutableListOf<DBTransactionBase.ActionContext<DB>.() -> Unit>()
        private val onRollbackActions = mutableListOf<DBTransactionBase.ActionContext<DB>.() -> Unit>()
        var data: Any? = Unit

        override fun rollback(): Nothing {
            throw RollbackException()
        }

        override fun <Output> map(action: DBAction<DB, Unit, Output>): DBTransaction<DB, Output> {
            return DBTransactionImpl(
                connection,
                db,
                action
                                                                                                            )
        }

        /**
         * In this context, don't commit yet
         */
        override fun <Output> apply(action: DBAction<DB, Unit, Output>): Output {
            return map(action).evaluateNow()
        }

        override fun <T> DBTransaction<DB, T>.get(): T {
            return evaluateNow()
        }

        private inline fun evalImpl(afterEval: (Connection, Boolean) -> Unit) {
            val rawConnection = connection.rawConnection
            val oldAutoCommit = rawConnection.autoCommit
            if (oldAutoCommit) { rawConnection.autoCommit = false }
            var savePoint = rawConnection.setSavepoint()
            try {
                while(actions.isNotEmpty()) {
                    val nextAction = actions.removeFirst() as DBAction<DB, Any?, Any?>

                    data = nextAction(this, data)
                }
                afterEval(rawConnection, oldAutoCommit)
                rawConnection.releaseSavepoint(savePoint)
                savePoint = null
            } catch (e: Exception) {
                for (action in onRollbackActions) {
                    try {
                        action()
                    } catch (f: Exception) {
                        e.addSuppressed(f)
                    }
                }
                try {
                    rawConnection.rollback(savePoint)
                } catch (f: Exception) {
//                    rawConnection.rollback() // Savepoint is invalid
                    e.addSuppressed(f)
                    throw e
                }
                throw e
            } finally {
                if (oldAutoCommit) rawConnection.autoCommit = oldAutoCommit
            }
            onRollbackActions.clear()
        }

        fun evaluateNow(): Any? {
            evalImpl { rawConnection, oldAutoCommit ->
                // If we were in an autocommit context, commit otherwise it breaks a lot.
                if (oldAutoCommit) rawConnection.commit()
            }
            return data
        }

        fun commit(): Any? {
            evalImpl { rawConnection, _ ->
                for (action in onCommitActions) {
                    action()
                }
                rawConnection.commit()
            }
            onCommitActions.clear()

            return when (val data = data) { // special case sequences
                is Sequence<*> -> data.toList().asSequence()
                else -> data
            }
        }

        override fun onCommit(action: DBTransactionBase.ActionContext<DB>.() -> Unit) {
            onCommitActions.add(action)
        }

        override fun onRollback(action: DBTransactionBase.ActionContext<DB>.() -> Unit) {
            onRollbackActions.add(action)
        }

        fun addAction(action: DBAction<DB, *, *>) {
            actions.add(action)
        }

        fun prependActions(sourceContext: ContextImpl<DB>) {
            for(action in sourceContext.actions) {
                actions.addFirst(action)
            }
            sourceContext.actions.clear()
        }
    }

    private class DBTransactionImpl<DB : Database, T>(
        connection: DBConnection2<DB>,
        db: DB,
        action: DBAction<DB, Unit, T>
                                                                                                    ) :
        DBTransaction<DB, T> {
        private val context =
            ContextImpl(connection, db, action)

        override val db: DB get() = context.db

        override fun evaluateNow(): T {
            return context.evaluateNow() as T
        }

        override fun commit(): T {
            @Suppress("UNCHECKED_CAST")
            return context.commit() as T
        }

        override fun <O> map(action: DBAction<DB, T, O>): DBTransaction<DB, O> {
            context.addAction(action)
            @Suppress("UNCHECKED_CAST")
            return this as DBTransaction<DB, O>
        }

        override fun <Output> flatmap(actions: DBContext<DB>.() -> Iterable<DBAction<DB, Unit, Output>>): DBTransaction<DB, Output> {
            actions().forEach { context.addAction(it) }
            @Suppress("UNCHECKED_CAST")
            return this as DBTransaction<DB, Output>
        }

        override fun <Output> flatmapOld(action: DBAction<DB, T, DBTransaction<DB, Output>>): DBTransaction<DB, Output> = map {
            when (val actionResult = action(it)) {
                is DBTransactionImpl -> {
                    context.prependActions(actionResult.context)
                    Unit as Output // We have just injected further actions, but they have unit as input
                }
                else                                                                                         -> actionResult.evaluateNow()
            } //as DBTransaction<DB, Output>
        }
    }

}