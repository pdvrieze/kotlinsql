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

package uk.ac.bournemouth.util.kotlin.sql

import uk.ac.bournemouth.kotlinsql.ConnectionMetadata
import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.TableRef
import uk.ac.bournemouth.kotlinsql.WarningIterator
import uk.ac.bournemouth.util.kotlin.sql.impl.DBConnection2
import java.sql.ResultSet
import java.sql.SQLWarning

interface DBTransactionBase<DB : Database, T>:
    DBContext<DB> {

    fun <Output> map(action: DBAction<DB, in T, Output>): DBTransaction<DB, Output>

    fun <Output> flatmap(actions: DBContext<DB>.() -> Iterable<DBAction<DB, Unit, Output>>): DBTransaction<DB, Output> {
        @Suppress("UNCHECKED_CAST")
        val iterable = actions() as Iterable<DBAction<DB, Any?, T>>

        return iterable.fold(this) { accum, act ->
            accum.map(act)
        } as DBTransaction<DB, Output>
    }

    @Deprecated("At the very least it is the wrong name")
    fun <Output> flatmapOld(action: DBAction<DB, T, DBTransaction<DB, Output>>): DBTransaction<DB, Output> = map {
        action(it).evaluateNow()
    }

    /**
     * Apply a single action to the transaction. This should commit if at top level,
     * but not if done from a context
     */
    fun <Output> apply(action: DBAction<DB, T, Output>): Output {
        return map(action).commit()
    }

    interface ActionContext<DB : Database>:
        DBTransactionBase<DB, Unit> {
        override val db: DB
        val connection: DBConnection2<DB>

        // fun commit() // should intermediate commit be allowed?
        fun rollback()

        fun <T> DBTransaction<DB, T>.get(): T

        fun onCommit(action: ActionContext<DB>.() -> Unit)
        fun onRollback(action: ActionContext<DB>.() -> Unit)

        fun <R> withMetaData(action: ConnectionMetadata.() -> R): R {
            return ConnectionMetadata(connection.rawConnection.metaData)
                .action()
        }

        fun onFinish(action: ActionContext<DB>.() -> Unit) {
            onCommit(action)
            onRollback(action)
        }

        fun closeOnFinish(resultSet: ResultSet) = onFinish { resultSet.close() }

        val warningsIt: Iterator<SQLWarning> get() = WarningIterator(
            connection.rawConnection.warnings
                                                                    )
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

interface DBTransaction<DB : Database, T> :
    DBTransactionBase<DB, T> {
    fun evaluateNow(): T

    fun commit(): T
}

inline fun <DB : Database, T> DBTransaction<DB, T>.require(crossinline condition: (T)-> Boolean, crossinline lazyMessage: DBTransactionBase.ActionContext<DB>.()-> String) = map {
    require(condition(it)) { lazyMessage() }
}

inline fun <DB : Database> DBTransaction<DB, Int>.requireNonZero(crossinline lazyMessage: DBTransactionBase.ActionContext<DB>.() -> String) = map {
    require(it > 0) { lazyMessage() }
}

internal typealias DBAction<DB, In, Out> = DBTransactionBase.ActionContext<DB>.(In) -> Out
internal typealias DBActionObj<DB, In, Out> = Function2<DBTransactionBase.ActionContext<DB>, In, Out>