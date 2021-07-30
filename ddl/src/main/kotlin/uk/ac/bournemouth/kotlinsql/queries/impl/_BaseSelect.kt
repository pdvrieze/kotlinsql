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
import uk.ac.bournemouth.kotlinsql.columns.PreparedStatementHelper
import uk.ac.bournemouth.kotlinsql.queries.Select

abstract class _BaseSelect(override vararg val columns: Column<*, *, *>) : Select {
    override val select: Select get() = this

    override fun toSQL(): String = toSQL(createTablePrefixMap())

    override fun toSQL(prefixMap: Map<String, String>?): String {
        return when {
            prefixMap != null -> toSQLMultipleTables(prefixMap)
            else              -> columns.joinToString("`, `",
                                                      "SELECT `",
                                                      "` FROM ${columns[0].table._name}") { it.name }
        }
    }

    override fun setParams(statementHelper: PreparedStatementHelper, first: Int) = first

    private fun tableNames() = columns.map { it.table._name }.toSortedSet()

    private fun toSQLMultipleTables(prefixMap: Map<String, String>): String {

        return buildString {
            append("SELECT ")
            columns.joinTo(this, ", ") { column -> column.name(prefixMap) }
            append(" FROM ")
            prefixMap.entries.joinTo(this, ", `", "`") { pair -> "${pair.key}` AS ${pair.value}" }
        }
    }


    override fun createTablePrefixMap(): Map<String, String>? {
        val tableNames = tableNames()

        fun uniquePrefix(string: String, usedPrefixes: Set<String>): String {
            for (i in 1..(string.length - 1)) {
                string.substring(0, i).let {
                    if (it !in usedPrefixes) return it
                }
            }
            for (i in 1..Int.MAX_VALUE) {
                (string + i.toString()).let {
                    if (it !in usedPrefixes) return it
                }
            }
            throw IllegalArgumentException("No unique prefix could be found")
        }

        if (tableNames.size <= 1) return null

        return tableNames.let {
            val seen = mutableSetOf<String>()
            it.associateTo(sortedMapOf<String, String>()) { name ->
                name to uniquePrefix(name, seen).apply {
                    seen.add(this)
                }
            }
        }
    }

}