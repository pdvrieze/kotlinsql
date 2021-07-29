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

import uk.ac.bournemouth.kotlinsql.ConnectionMetadata
import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.TableRef
import uk.ac.bournemouth.kotlinsql.impl.WarningIterator
import java.sql.ResultSet
import java.sql.SQLWarning

interface ActionContext<DB : Database>:
    EmptyDBTransaction<DB, Unit> {
    override val db: DB
    val connection: MonadicDBConnection<DB>

    // fun commit() // should intermediate commit be allowed?
    fun rollback()

    fun <T> EvaluatableDBTransaction<DB, T>.get(): T

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

    fun hasTable(tableRef: TableRef): Boolean {
        return withMetaData {
            getTables(null, null, tableRef._name, null).use { rs ->
                rs.next()
            }
        }
    }
}