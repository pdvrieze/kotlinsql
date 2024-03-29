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

import io.github.pdvrieze.kotlinsql.ddl.columns.PreparedStatementHelper
import io.github.pdvrieze.kotlinsql.dml.BooleanWhereValue
import io.github.pdvrieze.kotlinsql.dml.WhereClause

internal class WhereCombine(val left: WhereClause, val rel: String, val right: WhereClause) : BooleanWhereValue() {
    override fun toSQL(prefixMap: Map<String, String>?): String {
        return "( ${left.toSQL(prefixMap)} $rel ${right.toSQL(prefixMap)} )"
    }

    override fun setParameters(statementHelper: PreparedStatementHelper, first: Int): Int {
        val i = left.setParameters(statementHelper, first)
        return right.setParameters(statementHelper, i)
    }
}