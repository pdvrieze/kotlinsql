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

package uk.ac.bournemouth.kotlinsql.queries.impl

import uk.ac.bournemouth.kotlinsql.Column
import uk.ac.bournemouth.kotlinsql.IColumnType
import uk.ac.bournemouth.kotlinsql.columns.PreparedStatementHelper
import uk.ac.bournemouth.kotlinsql.impl.gen._Statement1
import uk.ac.bournemouth.kotlinsql.queries.Select
import uk.ac.bournemouth.kotlinsql.queries.Select1
import uk.ac.bournemouth.kotlinsql.queries.WhereClause

class _Select1<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>(override val col1: C1) : Select,
                                                                                                         Select1<T1, S1, C1> {
    override val select: _Select1<T1, S1, C1> get() = this

    override val columns: Array<out Column<*, *, *>> get() = arrayOf<Column<*, *, *>>(col1)

    override fun WHERE(config: _Where.() -> WhereClause?): Select1<T1, S1, C1> {
        return _Where().config()?.let { _Statement1(this, it) } ?: this
    }

    override fun createTablePrefixMap(): Map<String, String>? = null

    override fun toSQL(): String = "SELECT `${col1.name}` FROM `${col1.table._name}`"
    override fun toSQL(prefixMap: Map<String, String>?) = toSQL()

    override fun setParams(statementHelper: PreparedStatementHelper, first: Int) = first
}