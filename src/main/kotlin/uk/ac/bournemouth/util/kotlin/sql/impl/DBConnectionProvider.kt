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
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.ConnectionSource
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.invoke as genInvoke
import uk.ac.bournemouth.util.kotlin.sql.use
import javax.sql.DataSource

interface ConnectionSourceBase<DB : Database> {
    val datasource: DataSource
    val db: DB
    fun ensureTables()
}


inline fun <DB: Database, R> ConnectionSource<DB>.transaction(config: TransactionBuilder<DB>.() -> DBAction2<R>): DBAction2.Transaction<R> {
    return TransactionBuilder(db).run {
        toTransaction(config())
    }
}

inline fun <DB: Database> ConnectionSource<DB>.unitTransaction(config: TransactionBuilder<DB>.() -> Unit): DBAction2.Transaction<*> {
    return TransactionBuilder(db).apply { config() }.toTransaction()
}

class TransactionBuilder<DB: Database>(val db: DB) {
    private val actions = mutableListOf<DBAction2<Any?>>()

    @PublishedApi
    internal fun <R> toTransaction(lastAction: DBAction2<R>): DBAction2.Transaction<R> {
        val actions = actions.toMutableList<DBAction2<*>>()
        if (actions.last()!=lastAction)
            actions.add(lastAction)
        return DBAction2.Transaction(actions)
    }

    @PublishedApi
    internal fun toTransaction(): DBAction2.Transaction<*> {
        return DBAction2.Transaction<Any?>(actions)
    }


}

internal abstract class ConnectionSourceImplBase<DB : Database>: ConnectionSource<DB> {
    override fun ensureTables() {
        unitTransaction {
            Unit
        }
        datasource.connection.use { conn ->
            conn.autoCommit = false

            conn.commit()
        }
    }
}

internal inline fun <DB: Database, R> ConnectionSource<DB>.use(action: DBConnection2<DB>.() -> R):R {
    var doCommit = true
    val conn = DBConnection2(datasource.connection, db)
    try {
        return conn.action()
    } catch (e: Exception) {
        try {
            conn.rawConnection.rollback()
        } finally {
            conn.close()
            doCommit = false
        }
        throw e
    } finally {
        if (doCommit) conn.rawConnection.commit()
        conn.close()
    }
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <DB : Database, R> DB.invoke(datasource: DataSource, noinline action: ConnectionSource<DB>.() -> R): R {
    return genInvoke(datasource, action)
}
