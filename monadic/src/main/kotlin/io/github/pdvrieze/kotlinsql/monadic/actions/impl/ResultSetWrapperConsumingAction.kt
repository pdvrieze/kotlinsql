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
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.monadic.MonadicDBConnection

internal class ResultSetWrapperConsumingAction<DB : Database, M, RS : ResultSetRow, O>(
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