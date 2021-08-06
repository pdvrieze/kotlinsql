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
import io.github.pdvrieze.kotlinsql.dml.*
import io.github.pdvrieze.kotlinsql.dml.impl._Where
import io.github.pdvrieze.kotlinsql.monadic.impl.SelectResultsetRow
import io.github.pdvrieze.util.kotlin.sql.PreparedStatementHelperImpl
import java.sql.ResultSet
import javax.sql.DataSource

@DslMarker
annotation class DbActionDSL

sealed interface DBAction<DB : Database, out O> {
    fun rollback()

    fun commit(connectionSource: ConnectionSource<DB>): O

    fun eval(connection: MonadicDBConnection<DB>): O

    fun <R> map(transform: (O) -> R): DBAction<DB, R>

    fun <R> flatMap(actionProducer: (O) -> Iterable<DBAction<DB, R>>): DBAction<DB, List<R>>
}

// TODO handle evaluation with transactions a bit bettter
@DbActionDSL
sealed class DBActionImpl<DB : Database, M, out O> : DBAction<DB, O> {

    // The eval function has a block that allows wrapping actions within a try block
    protected abstract fun doEval(connection: MonadicDBConnection<DB>, initResult: M): O

    override fun eval(connection: MonadicDBConnection<DB>): O {
        val initResult: M = doBegin(connection)
        try {
            return doEval(connection, initResult)
        } finally {
            doClose(connection, initResult)
        }
    }

    internal inline fun <R> eval(connection: MonadicDBConnection<DB>, block: (O) -> R): R {
        val initResult: M = doBegin(connection)
        try {
            return block(doEval(connection, initResult))
        } finally {
            doClose(connection, initResult)
        }
    }

    protected abstract fun doBegin(connection: MonadicDBConnection<DB>): M

    protected open fun doClose(connection: MonadicDBConnection<DB>, initResult: M) {}

    //    abstract fun apply(): R
    override fun rollback() = Unit // by default no need to undo anything
    override fun commit(connectionSource: ConnectionSource<DB>): O {
        return connectionSource.datasource.withConnection(connectionSource.db) { conn ->
            val origAutoCommit = conn.rawConnection.autoCommit
            if (origAutoCommit) conn.rawConnection.autoCommit = false
            eval(conn) { it }.also {
                conn.rawConnection.commit()
                if (origAutoCommit) conn.rawConnection.autoCommit = true
            }
        }
    }

    override fun <R> map(transform: (O) -> R): DBAction<DB, R> = TransformAction(this, transform)

    override fun <R> flatMap(actionProducer: (O) -> Iterable<DBAction<DB, R>>): DBAction<DB, List<R>> =
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

/** Base class for all query (select) actions (that create resultsets or wrappers). */
sealed interface DBQueryAction<DB : Database, out O> : DBAction<DB, O>

/**
 * Base interface for actions that update the database (insert/update/delete).
 */
sealed interface DBUpdateAction<DB : Database, out O> : DBAction<DB, O>

/**
 * Base class for actions that update the database (insert/update/delete).
 */
sealed class DBUpdateActionImpl<DB : Database, out O>() :
    DBActionImpl<DB, PreparedStatementHelperImpl, O>(),
    DBUpdateAction<DB, O> {

    override fun doClose(connection: MonadicDBConnection<DB>, initResult: PreparedStatementHelperImpl) {
        initResult.statement.close()
    }
}

/**
 * Base class for actions that use raw sql.
 */
sealed class DBRawSqlActionImpl<DB : Database, out O>() : DBActionImpl<DB, Unit, O>()

/**
 * Base interface for actions that do not involve the database.
 */
sealed interface NoQueryAction<DB : Database, out O> : DBAction<DB, O>

/**
 * Base class for actions that do not involve the database.
 */
sealed class NoQueryActionImpl<DB : Database, out O>() : DBActionImpl<DB, Unit, O>(), NoQueryAction<DB, O> {
    final override fun doBegin(connection: MonadicDBConnection<DB>) {}
}


class ValueMetadataAction<DB : Database, out O> internal constructor(private val producer: (SafeDatabaseMetaData) -> O) :
    NoQueryActionImpl<DB, O>() {

    override fun doEval(connection: MonadicDBConnection<DB>, initResult: Unit): O {
        return producer(SafeDatabaseMetaData(connection.rawConnection.metaData))
    }
}

interface ResultSetMetadataAction<DB : Database, Row : ResultSetRow>
    : ResultSetWrapperProducingAction<DB, Row>

@DbActionDSL
class ResultSetMetadataActionImpl<DB : Database, Row : ResultSetRow>
internal constructor(private val provider: (SafeDatabaseMetaData) -> AbstractMetadataResultSet<Row>) :
    ResultSetWrapperProducingActionImpl<DB, AbstractMetadataResultSet<Row>, Row>({ close() }),
    ResultSetMetadataAction<DB, Row> {

    override fun getCloseableResource(connection: MonadicDBConnection<DB>): AbstractMetadataResultSet<Row> {
        return provider(SafeDatabaseMetaData(connection.rawConnection.metaData))
    }

    override fun wrapResultSet(initResult: AbstractMetadataResultSet<Row>): ResultSetWrapper<Row> {
        return initResult
    }

/*
    @OptIn(UnmanagedSql::class)
    fun eval(connection: MonadicDBConnection<DB>): RS {
        val metadata = SafeDatabaseMetaData(connection.rawConnection.metaData)
        val resultSetWrapper = provider(metadata)
        try {
            return block(resultSetWrapper)
        } finally {
            resultSetWrapper.close()
        }
    }
*/
}

interface SelectAction<DB : Database, S : SelectStatement>:
    ResultSetWrapperProducingAction<DB, SelectResultsetRow<S>> {
    val query: S
}

internal class SelectActionImpl<DB : Database, S : SelectStatement>(override val query: S) :
    ResultSetWrapperProducingActionImpl<DB, Pair<PreparedStatementHelperImpl, ResultSet>, SelectResultsetRow<S>>(
        {
            try {
                second.close()
            } finally {
                first.statement.close()
            }
        }
    ),
    SelectAction<DB, S> {

    @OptIn(UnmanagedSql::class)
    override fun getCloseableResource(connection: MonadicDBConnection<DB>): Pair<PreparedStatementHelperImpl, ResultSet> {
        val s = connection.prepareStatement(query.toSQL())
        try {
            query.setParams(s)
            val q = s.statement.executeQuery()
            return Pair(s, q)
        } catch (e: Exception) {
            s.statement.close()
            throw e
        }
    }

    override fun wrapResultSet(initResult: Pair<PreparedStatementHelperImpl, ResultSet>):
            ResultSetWrapper<SelectResultsetRow<S>> {
        return SelectResultsetRow(initResult.second, query)
    }
}

interface InsertActionCommon<DB : Database, S : Insert> {
    val insert: S
}

@DbActionDSL
class ValuelessInsertAction<DB : Database, S : Insert>(override val insert: S) : InsertActionCommon<DB, S> {
    // TODO check VALUES
}

@DbActionDSL
class InsertAction<DB : Database, S : Insert>(override val insert: S) : DBUpdateActionImpl<DB, IntArray>(),
                                                                        InsertActionCommon<DB, S> {
    @OptIn(UnmanagedSql::class)
    override fun doBegin(connection: MonadicDBConnection<DB>): PreparedStatementHelperImpl {
        return connection.prepareStatement(insert.toSQL())
    }

    override fun doEval(connection: MonadicDBConnection<DB>, initResult: PreparedStatementHelperImpl): IntArray {
        for (dataRow in insert.batch) {
            dataRow.setParams(initResult)
            initResult.statement.addBatch()
        }
        return initResult.statement.executeBatch()
    }
}

@DbActionDSL
class TransformAction<DB : Database, I, out O>(val source: DBActionImpl<DB, *, I>, val transform: (I) -> O) :
    DBActionImpl<DB, Unit, O>() {

    override fun doBegin(connection: MonadicDBConnection<DB>) = Unit

    override fun doEval(connection: MonadicDBConnection<DB>, initResult: Unit): O {
        return source.eval(connection) { input ->
            transform(input)
        }
    }
}

class ResultSetWrapperConsumingAction<DB : Database, M, RS : ResultSetRow, O>(
    val input: ResultSetWrapperProducingActionImpl<DB, M, RS>,
    val close: M.() -> Unit,
    val transform: (ResultSetWrapper<RS>) -> O,
) : DBActionImpl<DB, M, O>() {
    override fun doEval(connection: MonadicDBConnection<DB>, initResult: M): O {
        val wrapper = input.wrapResultSet(initResult)
        return transform(wrapper)
    }

    override fun doBegin(connection: MonadicDBConnection<DB>): M {
        return input.getCloseableResource(connection)
    }

    override fun doClose(connection: MonadicDBConnection<DB>, initResult: M) {
        initResult.close()
    }
}

interface ResultSetWrapperProducingAction<DB : Database, R : ResultSetRow> {
    @OptIn(UnmanagedSql::class, kotlin.ExperimentalStdlibApi::class)
    fun <T> mapEach(transform: (R) -> T): DBAction<DB, List<T>>
    fun <T> map(transform: (ResultSetWrapper<R>) -> T): DBAction<DB, T>

    @OptIn(UnmanagedSql::class)
    fun isEmpty(): DBAction<DB, Boolean>

    @OptIn(UnmanagedSql::class)
    fun isNotEmpty(): DBAction<DB, Boolean>

}

// TODO change this to take a "save variant of the resultset rather than the raw one"
abstract class ResultSetWrapperProducingActionImpl<DB : Database, M, R : ResultSetRow>(
    private val close: M.() -> Unit,
) : ResultSetWrapperProducingAction<DB, R> {

    abstract fun wrapResultSet(initResult: M): ResultSetWrapper<R>

    abstract fun getCloseableResource(connection: MonadicDBConnection<DB>): M

    @OptIn(UnmanagedSql::class, kotlin.ExperimentalStdlibApi::class)
    override fun <T> mapEach(transform: (R) -> T): DBAction<DB, List<T>> {
        return ResultSetWrapperConsumingAction(this, close) { resultSetWrapper ->
            buildList {
                while (resultSetWrapper.next()) add(transform(resultSetWrapper.rowData))
            }
        }
    }

    override fun <T> map(transform: (ResultSetWrapper<R>) -> T): DBAction<DB, T> {
        return ResultSetWrapperConsumingAction(this, close) { transform(it) }
    }

    @OptIn(UnmanagedSql::class)
    override fun isEmpty(): DBAction<DB, Boolean> = map { !it.next() }

    @OptIn(UnmanagedSql::class)
    override fun isNotEmpty(): DBAction<DB, Boolean> = map { it.next() }
}

@DbActionDSL
class LazyDataActionImpl<DB : Database, O>(private val s: () -> O) : NoQueryActionImpl<DB, O>() {
    override fun doEval(connection: MonadicDBConnection<DB>, initResult: Unit): O = s()

    override fun commit(connectionSource: ConnectionSource<DB>): O = s()
}

@DbActionDSL
class ConstantAction<DB : Database, out O>(private val data: O) : NoQueryActionImpl<DB, O>() {
    override fun doEval(connection: MonadicDBConnection<DB>, initResult: Unit): O = data

    override fun commit(connectionSource: ConnectionSource<DB>): O = data
}

class GroupAction<DB : Database, I, out O>(
    private val before: DBActionImpl<DB, *, I>,
    private val actionProducer: (I) -> Iterable<DBAction<DB, O>>,
) : DBActionImpl<DB, Unit, List<O>>() {

    override fun doBegin(connection: MonadicDBConnection<DB>) {}

    override fun doEval(connection: MonadicDBConnection<DB>, initResult: Unit): List<O> {
        val actions = before.eval(connection, actionProducer)

        return actions.map {
            val ac = it as DBActionImpl<DB, *, O>
            ac.eval(connection) { it }
        }
    }
}

@DbActionDSL
class TransactionAction<DB : Database, out O>(val action: TransactionBuilder<DB>.() -> O) :
    DBActionImpl<DB, Unit, O>() {
    override fun doBegin(connection: MonadicDBConnection<DB>) {}

    override fun doEval(connection: MonadicDBConnection<DB>, initResult: Unit): O {
        connection.rawConnection.autoCommit = false
        var doCommit = true
        try {
            val builder = TransactionBuilder(connection)
            val result = builder.action()
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
) : DBActionImpl<DB, Unit, O>() {
    override fun doEval(connection: MonadicDBConnection<DB>, initResult: Unit): O {
        val context = SimpleDBContext(connection.db)
        return context.action(connection)
    }

    override fun doBegin(connection: MonadicDBConnection<DB>) {}
}

private class SimpleDBContext<DB : Database>(override val db: DB) : DBContext<DB>

fun <DB : Database, S : Select> SelectAction<DB, S>.WHERE(config: _Where.() -> WhereClause?): SelectAction<DB, S> {
    val n = query.WHERE(config) as S
    return SelectActionImpl(n)
}


@OptIn(UnmanagedSql::class)
fun <DB : Database, S : Select1<T1, S1, C1>, R, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> SelectAction<DB, S>.mapSeq(
    transform: (Sequence<T1?>) -> R,
): DBAction<DB, R> {
    return map { rs ->
        transform(sequence {
            while (rs.next()) {
                yield(rs.rowData.value(query.select.col1, 1))
            }
        })
    }
}

fun <DB : Database, S : Select1<T1, S1, C1>, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> SelectAction<DB, S>.transform(): DBAction<DB, List<T1?>> {
    return mapSeq { it.toList() }
}

fun <DB : Database, I, O> TransformAction<DB, *, I>.map(action: (I) -> O): DBAction<DB, O> {
    return TransformAction(this, action)
}
/*

fun <DB : Database, I, O> ResultSetTransformAction<DB, *, I>.map(action: (I) -> O): DBAction<DB, O> {
    return TransformAction(this, action)
}
*/

fun <DB : Database, I, O> LazyDataActionImpl<DB, I>.map(action: (I) -> O): DBAction<DB, O> {
    return TransformAction(this, action)
}
