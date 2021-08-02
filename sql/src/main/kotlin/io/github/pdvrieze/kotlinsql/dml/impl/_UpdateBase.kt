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

import io.github.pdvrieze.kotlinsql.ddl.TableRef
import io.github.pdvrieze.kotlinsql.ddl.columns.PreparedStatementHelper
import io.github.pdvrieze.kotlinsql.dml.ParameterizedStatement
import io.github.pdvrieze.kotlinsql.dml.UpdatingStatement
import io.github.pdvrieze.kotlinsql.dml.WhereClause

abstract class _UpdateBase internal constructor(val table: TableRef) : UpdatingStatement, ParameterizedStatement {
    override fun createTablePrefixMap() = null

    fun WHERE(config: _Where.() -> WhereClause?) = createUpdateStatement(this, _Where().config())

    override fun setParams(statementHelper: PreparedStatementHelper, first: Int) = first

    private fun createUpdateStatement(update: _UpdateBase, where: WhereClause?): UpdatingStatement =
        if (where == null) update else _UpdateStatement(update, where)

}