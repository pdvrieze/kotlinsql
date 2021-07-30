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

import uk.ac.bournemouth.kotlinsql.ColumnRef
import uk.ac.bournemouth.kotlinsql.IColumnType
import uk.ac.bournemouth.kotlinsql.columns.PreparedStatementHelper
import uk.ac.bournemouth.kotlinsql.impl.SqlComparisons
import uk.ac.bournemouth.kotlinsql.queries.BooleanWhereValue

internal class WhereCmpCol<S1 : IColumnType<*, S1, *>, S2 : IColumnType<*, S2, *>>(val col1: ColumnRef<*, S1, *>,
                                                                                   val cmp: SqlComparisons,
                                                                                   val col2: ColumnRef<*, S2, *>) : BooleanWhereValue() {

    override fun toSQL(prefixMap: Map<String, String>?) = "${col1.name(prefixMap)} $cmp ${col2.name(prefixMap)}"

    override fun setParameters(statementHelper: PreparedStatementHelper, first: Int) = first
}