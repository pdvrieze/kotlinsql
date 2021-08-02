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

package io.github.pdvrieze.kotlinsql.dml.impl

import io.github.pdvrieze.kotlinsql.ddl.Column
import io.github.pdvrieze.kotlinsql.ddl.ColumnRef
import io.github.pdvrieze.kotlinsql.ddl.Table
import io.github.pdvrieze.kotlinsql.ddl.columns.NumericColumn
import io.github.pdvrieze.kotlinsql.ddl.columns.NumericColumnType
import io.github.pdvrieze.kotlinsql.ddl.columns.impl.CountColumn
import io.github.pdvrieze.kotlinsql.dml.Delete
import io.github.pdvrieze.kotlinsql.dml.Select
import io.github.pdvrieze.kotlinsql.dml.Update

interface DatabaseMethodsBase {

    @Suppress("FunctionName")
    fun DELETE_FROM(table: Table): Delete = _Delete(table)

    @Suppress("FunctionName")
    fun UPDATE(config: _UpdateBuilder.() -> Unit): Update = _UpdateBuilder().apply(config).build()

    @Suppress("FunctionName")
    fun COUNT(col: ColumnRef<*, *, *>): NumericColumn<Int, NumericColumnType.INT_T> {
        return CountColumn(col)
    }

    @Suppress("FunctionName")
    fun SELECT(columns: List<Column<*, *, *>>): Select = _ListSelect(columns)

}