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
import io.github.pdvrieze.kotlinsql.monadic.actions.DBAction
import io.github.pdvrieze.kotlinsql.monadic.actions.GroupAction

internal class GroupActionImpl<DB : Database, I, out O>(
    private val before: DBActionImpl<DB, *, I>,
    private val actionProducer: (I) -> Iterable<DBAction<DB, O>>,
) : DBActionImpl<DB, Unit, List<O>>(), GroupAction<DB, O> {

    override fun doBegin(connection: MonadicDBConnection<DB>) {}

    override fun doEval(connection: MonadicDBConnection<DB>, initResult: Unit): List<O> {
        val actions = before.eval(connection, actionProducer)

        return actions.map {
            val ac = it as DBActionImpl<DB, *, O>
            ac.eval(connection) { it }
        }
    }
}