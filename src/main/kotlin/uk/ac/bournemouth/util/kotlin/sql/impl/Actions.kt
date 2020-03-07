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
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.ConnectionSource
import uk.ac.bournemouth.util.kotlin.sql.use
import uk.ac.bournemouth.util.kotlin.sql.useTransacted
import uk.ac.bournemouth.util.kotlin.sql.withConnection
import java.sql.ResultSet

interface InternalResultSet

@DslMarker
annotation class DbActionDSL

@DbActionDSL
sealed class DBAction2<out O> {
    @DbActionDSL
    class Select<S: Database.SelectStatement>(val query: S): DBAction2<InternalResultSet>() {
        override fun <R> eval(connection: DBConnection2<*>, action: (InternalResultSet) -> R): R {
            val realAction = action as (ResultSet) -> R

            return connection.prepareStatement(query.toSQL()) {
                query.setParams(this)
                execute(realAction)
            }
        }

        override fun <DB: Database> commit(connectionSource: ConnectionSource<DB>): Nothing {
            throw UnsupportedOperationException("The result of a SELECT statement needs to be consumed for a sensible commit")
        }
    }

    @DbActionDSL
    class Transform<I, out O>(val source: DBAction2<I>, val transform: (I) -> O): DBAction2<O>() {
        override fun <R> eval(connection: DBConnection2<*>, block: (O) -> R): R {
            return source.eval(connection) { input ->
                block(transform(input))
            }
        }
    }

    @DbActionDSL
    class ResultSetTransform<out O>(val source: DBAction2<InternalResultSet>, private val action: (ResultSet) -> O): DBAction2<O>() {
        override fun <R> eval(connection: DBConnection2<*>, block: (O) -> R): R {
            return source.eval(connection, action as ((InternalResultSet) -> R))
        }
    }

    @DbActionDSL
    class Source<out O>(private val s: () -> O): DBAction2<O>() {
        override fun <R> eval(connection: DBConnection2<*>, block: (O) -> R): R = block(s())

        override fun <DB : Database> commit(connectionSource: ConnectionSource<DB>): O = s()
    }

    @DbActionDSL
    class Transaction<out O>(val actions: List<DBAction2<*>>): DBAction2<O>() {

        override fun <R> eval(connection: DBConnection2<*>, block: (O) -> R): R {
            connection.rawConnection.autoCommit = false
            var doCommit = true
            try {
                var last: Any? = Unit
                for (action in actions) {
                    last = action.eval(connection) { it }
                }
                return block(last as O).also {
                    connection.rawConnection.commit()
                }
            } catch (e: Exception) {
                connection.rawConnection.rollback()
                throw e
            }
        }
    }

    protected abstract fun <R> eval(connection: DBConnection2<*>, block: (O) -> R): R

//    abstract fun apply(): R
    fun rollback() = Unit // by default no need to undo anything
    open fun <DB: Database> commit(connectionSource: ConnectionSource<DB>): O {
        return connectionSource.datasource.withConnection(connectionSource.db) { conn ->
            val origAutoCommit = conn.rawConnection.autoCommit
            if (origAutoCommit) conn.rawConnection.autoCommit = false
            eval(conn) { it }.also {
                conn.rawConnection.commit()
                if (origAutoCommit) conn.rawConnection.autoCommit = true
            }
        }
    }
}

fun <S: Database.Select> DBAction2.Select<S>.WHERE(config: Database._Where.() -> Database.WhereClause?): DBAction2.Select<S> {
    val n = query.WHERE(config) as S
    return DBAction2.Select(n)
}


fun <S: Database.Select1<T1, S1, C1>, R,T1:Any, S1: IColumnType<T1, S1, C1>, C1: Column<T1, S1, C1>> DBAction2.Select<S>.map(transform: (Sequence<T1>) -> R): DBAction2<R> {
    return DBAction2.ResultSetTransform(this) { rs ->
        val rsSequence = ResultSetIterator(rs).asSequence()
        transform(rsSequence.map { query.select.col1.fromResultSet(it,1) as T1 })
    }
}

fun <I,O> DBAction2.Transform<*, I>.map(action: (I) -> O): DBAction2<O> {
    return DBAction2.Transform(this, action)
}

fun <I,O> DBAction2.ResultSetTransform<I>.map(action: (I) -> O): DBAction2<O> {
    return DBAction2.Transform(this, action)
}

fun <I,O> DBAction2.Source<I>.map(action: (I) -> O): DBAction2<O> {
    return DBAction2.Transform(this, action)
}
