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

package io.github.pdvrieze.kotlinsql.monadic

import io.github.pdvrieze.kotlinsql.ddl.Column
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.metadata.AbstractMetadataResultSet
import io.github.pdvrieze.kotlinsql.metadata.SafeDatabaseMetaData
import io.github.pdvrieze.kotlinsql.monadic.impl.ResultSetIterator
import io.github.pdvrieze.kotlinsql.dml.*
import io.github.pdvrieze.kotlinsql.dml.impl._Where
import io.github.pdvrieze.kotlinsql.direct.use
import java.sql.ResultSet
import javax.sql.DataSource

@DslMarker
annotation class DbActionDSL

// TODO handle evaluation with transactions a bit bettter
@DbActionDSL
sealed class DBAction<DB : Database, out O> {

    // The eval function has a block that allows wrapping actions within a try block
    internal abstract fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R

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

    fun <R> map(transform: (O) -> R): DBAction<DB, R> = TransformAction(this, transform)

    fun <R> flatMap(actionProducer: (O) -> Iterable<DBAction<DB, R>>): DBAction<DB, List<R>> =
        GroupAction(this, actionProducer)

    private companion object {

        private inline fun <C : MonadicDBConnection<*>, R> C.use(block: (C) -> R): R = useHelper({ it.close() }, block)

//        /**
//         * Executes the given [block] function on this resource and then closes it down correctly whether an exception
//         * is thrown or not.
//         *
//         * @param block a function to process this closable resource.
//         * @return the result of [block] function on this closable resource.
//         */
//        inline fun <T : Connection, R> T.use(block: (T) -> R) = useHelper({ it.close() }, block)


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

        private inline fun <DB : Database, R> DataSource.withConnection(
            db: DB,
            block: (MonadicDBConnection<DB>) -> R,
        ): R {
            val conn: MonadicDBConnection<DB>
            try {
                conn = MonadicDBConnection(connection, db)
            } catch (e: Exception) {
                try {
                    connection.close()
                } catch (f: Exception) {
                    e.addSuppressed(f)
                }
                throw e
            }
            return conn.use(block)
        }

    }
}

class ValueMetadataAction<DB : Database, O> internal constructor(private val producer: (SafeDatabaseMetaData) -> O) :
    DBAction<DB, O>() {
    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
        val metadata = SafeDatabaseMetaData(connection.rawConnection.metaData)
        return block(producer(metadata))
    }
}


@DbActionDSL
class ResultSetMetadataAction<DB : Database, RS : AbstractMetadataResultSet>
internal constructor(private val provider: (SafeDatabaseMetaData) -> RS) : ResultSetWrapperProducingAction<DB, RS>() {

    @OptIn(UnmanagedSql::class)
    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (RS) -> R): R {
        val metadata = SafeDatabaseMetaData(connection.rawConnection.metaData)
        val resultSetWrapper = provider(metadata)
        try {
            return block(resultSetWrapper)
        } finally {
            resultSetWrapper.close()
        }
    }

}

@DbActionDSL
class SelectAction<DB : Database, S : SelectStatement>(internal val query: S) : DBAction<DB, ResultSet>() {

    fun <T> mapEach(transform: (ResultSet) -> T): DBAction<DB, List<T>> =
        TransformAction<DB, ResultSet, List<T>>(this) { resultSet ->
            mutableListOf<T>().also { result ->
                for (rs in ResultSetIterator(resultSet)) {
                    result.add(transform(rs))
                }
            }
        }

    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (ResultSet) -> R): R {
//            val realAction = block as (ResultSet) -> R
        // TODO fix this to be better
        return connection.prepareStatement(query.toSQL()) {
            query.setParams(this)
            statement.executeQuery().use {
                block(it)
            }
        }
    }

    override fun commit(connectionSource: ConnectionSource<DB>): Nothing {
        throw UnsupportedOperationException("The result of a SELECT statement needs to be consumed for a sensible commit")
    }
}

interface InsertActionCommon<DB : Database, S : Insert> {
    val insert: S
}

@DbActionDSL
class ValuelessInsertAction<DB : Database, S : Insert>(override val insert: S) : InsertActionCommon<DB, S>

@DbActionDSL
class InsertAction<DB : Database, S : Insert>(override val insert: S) : DBAction<DB, IntArray>(),
                                                                        InsertActionCommon<DB, S> {
    override fun <R> eval(connection: MonadicDBConnection<DB>, action: (IntArray) -> R): R {
        return connection.prepareStatement(insert.toSQL()) {
            for (dataRow in insert.batch) {
                dataRow.setParams(this)
                statement.addBatch()
            }

            action(statement.executeBatch())
        }
    }
}

@DbActionDSL
class TransformAction<DB : Database, I, out O>(val source: DBAction<DB, I>, val transform: (I) -> O) :
    DBAction<DB, O>() {
    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
        return source.eval(connection) { input ->
            block(transform(input))
        }
    }
}

// TODO change this to take a "save variant of the resultset rather than the raw one"
abstract class ResultSetWrapperProducingAction<DB : Database, RS : AbstractMetadataResultSet> : DBAction<DB, RS>() {
    @OptIn(UnmanagedSql::class)
    fun <T> mapEach(transform: (RS) -> T): DBAction<DB, List<T>> = TransformAction<DB, RS, List<T>>(this) { resultSet ->
        mutableListOf<T>().also { result ->
            while (resultSet.next()) {
                result.add(transform(resultSet))
            }
        }
    }

    @OptIn(UnmanagedSql::class)
    fun isEmpty(): DBAction<DB, Boolean> = TransformAction(this) {
        !it.next()
    }

    @OptIn(UnmanagedSql::class)
    fun isNotEmpty(): DBAction<DB, Boolean> = TransformAction(this) {
        it.next()
    }
}

// TODO change this to take a "save variant of the resultset rather than the raw one"
abstract class ResultSetProducingAction<DB : Database, RS : ResultSet> : DBAction<DB, RS>() {

    fun <T> mapEach(transform: (RS) -> T): DBAction<DB, List<T>> = TransformAction<DB, RS, List<T>>(this) { resultSet ->
        mutableListOf<T>().also { result ->
            ResultSetIterator(resultSet).forEach { result.add(transform(it)) }
        }
    }

    @OptIn(UnmanagedSql::class)
    fun isEmpty(): DBAction<DB, Boolean> = TransformAction(this) {
        !it.next()
    }

    @OptIn(UnmanagedSql::class)
    fun isNotEmpty(): DBAction<DB, Boolean> = TransformAction(this) {
        it.next()
    }
}

@DbActionDSL
class ResultSetTransformAction<DB : Database, out O>(
    val source: DBAction<DB, ResultSet>,
    private val action: (ResultSet) -> O,
) : DBAction<DB, O>() {
    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
        return block(source.eval(connection, action))
    }
}

@DbActionDSL
class SourceAction<DB : Database, out O>(private val s: () -> O) : DBAction<DB, O>() {
    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R = block(s())

    override fun commit(connectionSource: ConnectionSource<DB>): O = s()
}

@DbActionDSL
class ConstantAction<DB : Database, out O>(private val data: O) : DBAction<DB, O>() {
    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R = block(data)

    override fun commit(connectionSource: ConnectionSource<DB>): O = data
}

class GroupAction<DB : Database, I, out O>(
    val before: DBAction<DB, I>,
    val actionProducer: (I) -> Iterable<DBAction<DB, O>>,
) : DBAction<DB, List<O>>() {
    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (List<O>) -> R): R {
        val actions = before.eval(connection, actionProducer)
        return block(actions.map { it.eval(connection) { it } })
    }
}

@DbActionDSL
class TransactionAction<DB : Database, out O>(val action: TransactionBuilder<DB>.() -> O) : DBAction<DB, O>() {

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
    val action: DBContext<DB>.(MonadicDBConnection<DB>) -> O,
) : DBAction<DB, O>() {

    override fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
        val context = SimpleDBContext(connection.db)
        return block(context.action(connection))
    }
}

private class SimpleDBContext<DB : Database>(override val db: DB) : DBContext<DB>

fun <DB : Database, S : Select> SelectAction<DB, S>.WHERE(config: _Where.() -> WhereClause?): SelectAction<DB, S> {
    val n = query.WHERE(config) as S
    return SelectAction(n)
}


fun <DB : Database, S : Select1<T1, S1, C1>, R, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> SelectAction<DB, S>.map(
    transform: (Sequence<T1>) -> R,
): DBAction<DB, R> {
    return ResultSetTransformAction(this) { rs ->
        val rsSequence: Sequence<ResultSet> = ResultSetIterator(rs).asSequence()
        transform(rsSequence.map { rs -> query.select.col1.fromResultSet(rs, 1) as T1 })
    }
}

fun <DB : Database, S : Select1<T1, S1, C1>, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> SelectAction<DB, S>.transform(): DBAction<DB, List<T1>> {
    return ResultSetTransformAction(this) { rs ->
        val rsSequence: Sequence<ResultSet> = ResultSetIterator(rs).asSequence()
        rsSequence.map { rs -> query.select.col1.fromResultSet(rs, 1) as T1 }.toList()
    }
}

fun <DB : Database, I, O> TransformAction<DB, *, I>.map(action: (I) -> O): DBAction<DB, O> {
    return TransformAction(this, action)
}

fun <DB : Database, I, O> ResultSetTransformAction<DB, I>.map(action: (I) -> O): DBAction<DB, O> {
    return TransformAction(this, action)
}

fun <DB : Database, I, O> SourceAction<DB, I>.map(action: (I) -> O): DBAction<DB, O> {
    return TransformAction(this, action)
}
