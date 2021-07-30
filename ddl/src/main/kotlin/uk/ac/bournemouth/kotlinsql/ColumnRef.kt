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

package uk.ac.bournemouth.kotlinsql

/**
 * A reference to a column.
 */
interface ColumnRef<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>> {
    /** The table the column is a part of */
    val table: TableRef

    /** The name of the column */
    val name: String

    /** The [IColumnType] of the column. */
    val type: S

    fun name(prefixMap: Map<String, String>?): String {
        return prefixMap?.run { get(table._name)?.let { "$it.`$name`" } } ?: "`$name`"
    }
}