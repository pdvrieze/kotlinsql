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

package io.github.pdvrieze.kotlinsql.monadic.actions

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper

interface ResultSetWrapperProducingAction<DB : Database, R : ResultSetRow<*>> {
    @OptIn(UnmanagedSql::class, ExperimentalStdlibApi::class)
    fun <T> mapEach(transform: (R) -> T): DBAction<DB, List<T>>
    fun <T> map(transform: (ResultSetWrapper<R, *>) -> T): DBAction<DB, T>

    @OptIn(UnmanagedSql::class)
    fun isEmpty(): DBAction<DB, Boolean>

    @OptIn(UnmanagedSql::class)
    fun isNotEmpty(): DBAction<DB, Boolean>

}