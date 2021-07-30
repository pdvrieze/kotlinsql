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

package uk.ac.bournemouth.kotlinsql.impl

import uk.ac.bournemouth.kotlinsql.Column
import uk.ac.bournemouth.kotlinsql.ColumnRef
import uk.ac.bournemouth.kotlinsql.Table
import uk.ac.bournemouth.kotlinsql.columns.NumericColumn
import uk.ac.bournemouth.kotlinsql.columns.NumericColumnType
import uk.ac.bournemouth.kotlinsql.columns.impl.CountColumn
import uk.ac.bournemouth.kotlinsql.queries.Delete
import uk.ac.bournemouth.kotlinsql.queries.Select
import uk.ac.bournemouth.kotlinsql.queries.Update
import uk.ac.bournemouth.kotlinsql.queries.impl._Delete
import uk.ac.bournemouth.kotlinsql.queries.impl._ListSelect
import uk.ac.bournemouth.kotlinsql.queries.impl._UpdateBuilder

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