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
import uk.ac.bournemouth.kotlinsql.Table
import uk.ac.bournemouth.kotlinsql.columns.PreparedStatementHelper
import uk.ac.bournemouth.kotlinsql.queries.Insert
import uk.ac.bournemouth.kotlinsql.queries.InsertValues

abstract class _BaseInsert(val table: Table, private val update: Boolean, vararg val columns: ColumnRef<*, *, *>) :
    Insert {
    override fun createTablePrefixMap(): Map<String, String>? {
        return null
    }

    override val batch: MutableList<_BaseInsertValues> = mutableListOf()

    override fun toSQL() =
        toSQL(createTablePrefixMap())

    override fun toSQL(prefixMap: Map<String, String>?): String {
        return buildString {
            columns.joinTo(this, ", ", "INSERT INTO ${table.name(prefixMap)} (", ")") { col -> col.name(prefixMap) }
//            columns.joinTo(this, ", ", "INSERT INTO ${table.name(prefixMap)} (", ")") { col -> col.name(prefixMap) }
            (1..columns.size).joinTo(this, prefix = " VALUES (", postfix = ")") { "?" }
            if (update) {
                val primaryKey = table._primaryKey
                val updateColumns = columns.asSequence()
                    .filter { primaryKey?.let { pk -> it !in pk } ?: true }.toList()
                if (updateColumns.isEmpty()) {
                    throw UnsupportedOperationException(
                        "An insert that only touches the primary key cannot be an update."); }
                append(" ON DUPLICATE KEY UPDATE ")
                updateColumns.asSequence()
                    .map { it.name(prefixMap) }
                    .joinTo(this) { "$it = VALUES($it)" }
            }
        }
    }

    abstract inner class _BaseInsertValues(private vararg val values: Any?): InsertValues {

        override fun setParams(statementHelper: PreparedStatementHelper, first: Int): Int {
            for (i in columns.indices) {
                columns[i].type.castSetParam(statementHelper, first + i, values[i])
            }
            return first + columns.size
        }

        override fun toString() = values.joinToString(prefix = "( ", postfix = " )", transform = Any?::toString)
    }
}