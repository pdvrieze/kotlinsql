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
import uk.ac.bournemouth.kotlinsql.queries.BooleanWhereValue
import uk.ac.bournemouth.kotlinsql.queries.WhereClause

internal class WhereBooleanUnary(val rel: String, val base: WhereClause) : BooleanWhereValue() {
    override fun toSQL(prefixMap: Map<String, String>?) = "$rel ${base.toSQL(prefixMap)}"

    override fun setParameters(statementHelper: PreparedStatementHelper, first: Int): Int {
        return base.setParameters(statementHelper, first)
    }
}