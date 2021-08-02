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

package io.github.pdvrieze.kotlinsql.dml

import io.github.pdvrieze.kotlinsql.ddl.Column
import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.dml.impl._Select1

interface Select1<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> : SelectStatement {
    override val select: _Select1<T1, S1, C1>

    val col1: C1 get() = select.col1

/*
    @NonMonadicApi
    fun getSingle(connection: MonadicDBConnection<*>): T1

    @NonMonadicApi
    fun getSingleOrNull(connection: MonadicDBConnection<*>): T1?

    @NonMonadicApi
    fun getList(connection: MonadicDBConnection<*>): List<T1?>

    @Deprecated("This can be done outside the function", ReplaceWith("getList(connection).filterNotNull()"),
                DeprecationLevel.ERROR)
    @NonMonadicApi
    fun getSafeList(connection: MonadicDBConnection<*>): List<T1> = getList(connection).filterNotNull()

    @NonMonadicApi
    fun execute(connection: MonadicDBConnection<*>, block: (T1?) -> Unit): Boolean

    @NonMonadicApi
    fun hasRows(connection: MonadicDBConnection<*>): Boolean
*/

}