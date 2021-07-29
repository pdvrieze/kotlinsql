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
import uk.ac.bournemouth.kotlinsql.monadic.MonadicDBConnection
import uk.ac.bournemouth.kotlinsql.monadic.MonadicMetadata
import uk.ac.bournemouth.kotlinsql.monadic.impl.MonadicMetadataImpl
import uk.ac.bournemouth.kotlinsql.monadic.use
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.ConnectionSource
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.DBActionReceiver
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.invoke as genInvoke
import javax.sql.DataSource

interface ConnectionSourceBase<DB : Database> {
    val datasource: DataSource
    val db: DB
    fun ensureTables(retainExtraColumns: Boolean = true)
}


fun <DB: Database, R> ConnectionSource<DB>.transaction(body: TransactionBuilder<DB>.() -> R): R {
    return DBAction2.Transaction(body).commit(this)
}

@DbActionDSL
class TransactionBuilder<DB: Database>(val connection: MonadicDBConnection<DB>): DBActionReceiver<DB> {

    override val db: DB get() = connection.db

    @Suppress("UNCHECKED_CAST")
    override fun metadata(): MonadicMetadata<DB> = metadataInstance as MonadicMetadata<DB>

    private var commitPending = false
    fun commit() {
        commitPending = true
    }

    internal fun commitIfNeeded() {
        if (commitPending) {
            connection.rawConnection.commit()
        }
    }
}

internal abstract class ConnectionSourceImplBase<DB : Database>: ConnectionSource<DB> {
    override fun ensureTables(retainExtraColumns: Boolean) {
        MonadicDBConnection(datasource.connection, db).use { _ ->
            DBAction2.GenericAction<DB, Unit> { conn ->
                db.ensureTables(conn, retainExtraColumns)
            }.commit()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun metadata(): MonadicMetadata<DB> = metadataInstance as MonadicMetadata<DB>

}

private val metadataInstance = MonadicMetadataImpl<Database>()

internal inline fun <DB: Database, R> ConnectionSource<DB>.use(action: MonadicDBConnection<DB>.() -> R):R {
    var doCommit = true
    val conn = MonadicDBConnection(datasource.connection, db)
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
