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
import io.github.pdvrieze.kotlinsql.monadic.MonadicDBConnection
import io.github.pdvrieze.kotlinsql.monadic.TransactionBuilder
import io.github.pdvrieze.kotlinsql.monadic.actions.TransactionAction

internal class TransactionActionImpl<DB : Database, out O>(val action: TransactionBuilder<DB>.() -> O) :
    DBActionImpl<DB, Unit, O>(), TransactionAction<DB, O> {
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