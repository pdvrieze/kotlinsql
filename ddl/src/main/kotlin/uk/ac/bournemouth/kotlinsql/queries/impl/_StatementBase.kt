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

import uk.ac.bournemouth.kotlinsql.columns.PreparedStatementHelper
import uk.ac.bournemouth.kotlinsql.queries.ParameterizedStatement
import uk.ac.bournemouth.kotlinsql.queries.SelectStatement
import uk.ac.bournemouth.kotlinsql.queries.WhereClause

/**
 * @param where
 */
abstract class _StatementBase(private val where: WhereClause) : SelectStatement {
    override fun toSQL() = toSQL(createTablePrefixMap())

    override fun createTablePrefixMap() = select.createTablePrefixMap()

    override fun toSQL(prefixMap: Map<String, String>?): String {
        return "${select.toSQL(prefixMap)} WHERE ${where.toSQL(prefixMap)}"
    }

    override fun setParams(statementHelper: PreparedStatementHelper, first: Int) =
        where.setParameters(statementHelper, first)
}