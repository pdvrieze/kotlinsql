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

import uk.ac.bournemouth.kotlinsql.*
import uk.ac.bournemouth.kotlinsql.metadata.AbstractMetadataResultSet
import uk.ac.bournemouth.kotlinsql.DBContext
import uk.ac.bournemouth.kotlinsql.monadic.MonadicDBConnection
import uk.ac.bournemouth.kotlinsql.sql.NonMonadicApi
import uk.ac.bournemouth.kotlinsql.sql.ResultSetIterator
import uk.ac.bournemouth.kotlinsql.sql.withConnection
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.ConnectionSource
import java.sql.ResultSet

interface InternalResultSet

@DslMarker
annotation class DbActionDSL

@DbActionDSL
sealed class DBAction2<DB : Database, out O> {


    protected abstract fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R

    //    abstract fun apply(): R
    fun rollback() = Unit // by default no need to undo anything
    open fun commit(connectionSource: ConnectionSource<DB>): O {
        return connectionSource.datasource.withConnection(connectionSource.db) { conn ->
            val origAutoCommit = conn.rawConnection.autoCommit
            if (origAutoCommit) conn.rawConnection.autoCommit = false
            eval(conn) { it }.also {
                conn.rawConnection.commit()
                if (origAutoCommit) conn.rawConnection.autoCommit = true
            }
        }
    }

    class ValueMetadata<DB : Database, O> internal constructor(private val producer: (ConnectionMetadata) -> O) :
        DBAction2<DB, O>() {
        override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
            val metadata = ConnectionMetadata(connection.rawConnection.metaData)
            return block(producer(metadata))
        }
    }


    @DbActionDSL
    class ResultSetMetadata<DB : Database, RS : AbstractMetadataResultSet>
    internal constructor(private val provider: (ConnectionMetadata) -> RS) :
        DBAction2<DB, RS>() {

        override fun <R> eval(connection: MonadicDBConnection<DB>, block: (RS) -> R): R {
            val metadata = ConnectionMetadata(connection.rawConnection.metaData)
            val resultSetWrapper = provider(metadata)
            try {
                return block(resultSetWrapper)
            } finally {
                resultSetWrapper.close()
            }
        }

    }

    @DbActionDSL
    class Select<DB : Database, S : Database.SelectStatement>(val query: S) : DBAction2<DB, InternalResultSet>() {
        @OptIn(NonMonadicApi::class)
        override fun <R> eval(connection: MonadicDBConnection<DB>, block: (InternalResultSet) -> R): R {
            val realAction = block as (ResultSet) -> R

            return connection.prepareStatement(query.toSQL()) {
                query.setParams(this)
                execute(realAction)
            }
        }

        override fun commit(connectionSource: ConnectionSource<DB>): Nothing {
            throw UnsupportedOperationException("The result of a SELECT statement needs to be consumed for a sensible commit")
        }
    }

    interface InsertCommon<DB : Database, S : Database.Insert> {
        val insert: S
    }

    @DbActionDSL
    class InsertStart<DB : Database, S : Database.Insert>(override val insert: S) : InsertCommon<DB, S>

    @DbActionDSL
    class Insert<DB : Database, S : Database.Insert>(override val insert: S) : DBAction2<DB, Int>(),
                                                                               InsertCommon<DB, S> {
        override fun <R> eval(connection: MonadicDBConnection<DB>, action: (Int) -> R): R {
            return action(insert.executeUpdate(connection))
        }
    }

    @DbActionDSL
    class Transform<DB : Database, I, out O>(val source: DBAction2<DB, I>, val transform: (I) -> O) :
        DBAction2<DB, O>() {
        override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
            return source.eval(connection) { input ->
                block(transform(input))
            }
        }
    }

    @DbActionDSL
    class ResultSetTransform<DB : Database, out O>(
        val source: DBAction2<DB, InternalResultSet>,
        private val action: (ResultSet) -> O
                                                  ) : DBAction2<DB, O>() {
        override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
            return source.eval(connection, action as ((InternalResultSet) -> R))
        }
    }

    @DbActionDSL
    class Source<DB : Database, out O>(private val s: () -> O) : DBAction2<DB, O>() {
        override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R = block(s())

        override fun commit(connectionSource: ConnectionSource<DB>): O = s()
    }

    @DbActionDSL
    class Transaction<DB : Database, out O>(val action: TransactionBuilder<DB>.() -> O) : DBAction2<DB, O>() {

        override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
            connection.rawConnection.autoCommit = false
            var doCommit = true
            try {
                val builder = TransactionBuilder(connection)
                val result = block(builder.action())
                builder.commitIfNeeded()
                return result
            } catch (e: Exception) {
                connection.rawConnection.rollback()
                throw e
            }
        }
    }

    class GenericAction<DB : Database, out O>(
        val action: DBContext<DB>.(MonadicDBConnection<DB>) -> O
                                             ) : DBAction2<DB, O>() {

        override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
            val context = SimpleDBContext(connection.db)
            return block(context.action(connection))
        }
    }
}

private class SimpleDBContext<DB: Database>(override val db: DB): DBContext<DB>

fun <DB : Database, S : Database.Select> DBAction2.Select<DB, S>.WHERE(config: Database._Where.() -> Database.WhereClause?): DBAction2.Select<DB, S> {
    val n = query.WHERE(config) as S
    return DBAction2.Select(n)
}


fun <DB : Database, S : Database.Select1<T1, S1, C1>, R, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> DBAction2.Select<DB, S>.map(
    transform: (Sequence<T1>) -> R
                                                                                                                                                     ): DBAction2<DB, R> {
    return DBAction2.ResultSetTransform(this) { rs ->
        val rsSequence = ResultSetIterator(rs).asSequence()
        transform(rsSequence.map { query.select.col1.fromResultSet(it, 1) as T1 })
    }
}

fun <DB : Database, I, O> DBAction2.Transform<DB, *, I>.map(action: (I) -> O): DBAction2<DB, O> {
    return DBAction2.Transform(this, action)
}

fun <DB : Database, I, O> DBAction2.ResultSetTransform<DB, I>.map(action: (I) -> O): DBAction2<DB, O> {
    return DBAction2.Transform(this, action)
}

fun <DB : Database, I, O> DBAction2.Source<DB, I>.map(action: (I) -> O): DBAction2<DB, O> {
    return DBAction2.Transform(this, action)
}
