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

package io.github.pdvrieze.kotlinsql.monadic.actions

import io.github.pdvrieze.kotlinsql.ddl.Column
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.dml.*
import io.github.pdvrieze.kotlinsql.dml.impl._Where
import io.github.pdvrieze.kotlinsql.monadic.*
import io.github.pdvrieze.kotlinsql.monadic.actions.impl.LazyDataActionImpl
import io.github.pdvrieze.kotlinsql.monadic.actions.impl.SelectActionImpl
import io.github.pdvrieze.kotlinsql.monadic.actions.impl.TransformActionImpl


@Suppress("FunctionName")
fun <DB : Database, S : Select> SelectAction<DB, S>.WHERE(config: _Where.() -> WhereClause?): SelectAction<DB, S> {
    @Suppress("UNCHECKED_CAST")
    val n = query.WHERE(config) as S
    return SelectActionImpl(n)
}


fun <DB : Database, S : Select1<T1, S1, C1>, R, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        SelectAction<DB, S>.mapSeq(transform: (Sequence<T1?>) -> R): DBAction<DB, R> {

    @OptIn(UnmanagedSql::class)
    return map { rs ->
        transform(sequence {
            while (rs.next()) {
                yield(rs.rowData.value(query.select.col1, 1))
            }
        })
    }
}

fun <DB : Database, S : Select1<T1, S1, C1>, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        SelectAction<DB, S>.transform(): DBAction<DB, List<T1?>> {
    return mapSeq { it.toList() }
}
