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

package io.github.pdvrieze.kotlinsql.monadic.actions.impl

import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.monadic.ConnectionSource
import io.github.pdvrieze.kotlinsql.monadic.MonadicDBConnection
import io.github.pdvrieze.kotlinsql.monadic.actions.DBAction
import io.github.pdvrieze.kotlinsql.monadic.actions.GroupAction
import io.github.pdvrieze.kotlinsql.monadic.actions.TransformAction
import javax.sql.DataSource

// TODO handle evaluation with transactions a bit bettter
internal abstract class DBActionImpl<DB : Database, M, out O> : DBAction<DB, O> {

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

    override fun <R> map(transform: (O) -> R): TransformAction<DB, R> = TransformActionImpl(this, transform)

    override fun <R> flatMap(actionProducer: (O) -> Iterable<DBAction<DB, R>>): GroupAction<DB, R> =
        GroupActionImpl(this, actionProducer)

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