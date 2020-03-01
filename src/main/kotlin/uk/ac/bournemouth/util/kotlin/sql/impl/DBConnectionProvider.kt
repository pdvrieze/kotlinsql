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

import uk.ac.bournemouth.kotlinsql.Column
import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.IColumnType
import uk.ac.bournemouth.util.kotlin.sql.DBContext
import javax.sql.DataSource

interface ConnectionSource<DB : Database> : DBContext<DB> {

    val datasource: DataSource

    fun <T1:Any, S1: IColumnType<T1, S1, C1>, C1: Column<T1, S1, C1>> SELECT(col1: C1): DBAction2.Select<Database.Select1<T1, S1, C1>>

    fun <O> DBAction2<O>.commit() {
        commit(this@ConnectionSource)
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

private class ConnectionSourceImpl<DB : Database>(
    override val db: DB,
    override val datasource: DataSource
                                                 ) : ConnectionSource<DB> {
    override fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> SELECT(col1: C1): DBAction2.Select<Database.Select1<T1, S1, C1>> {
        return DBAction2.Select(Database._Select1(col1))
    }
}

fun <DB : Database, R> DB.withSource(datasource: DataSource, action: ConnectionSource<DB>.() -> R): R {
    return ConnectionSourceImpl(this, datasource).action()
}