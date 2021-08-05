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

package io.github.pdvrieze.kotlinsql.ddl.columns.impl

import io.github.pdvrieze.kotlinsql.ddl.columns.*
import io.github.pdvrieze.kotlinsql.ddl.*
import io.github.pdvrieze.kotlinsql.metadata.ColumnsResults

class CountColumn(private val colRef: ColumnRef<*, *, *>) : NumericColumn<Int, NumericColumnType.INT_T> {
    override val table: TableRef
        get() = colRef.table
    override val name: String
        get() = "COUNT( ${colRef.name} )"
    override val type: NumericColumnType.INT_T
        get() = NumericColumnType.INT_T
    override val notnull: Boolean? get() = null
    override val unique: Boolean get() = false
    override val autoincrement: Boolean get() = false
    override val default: Int? get() = null
    override val comment: String? get() = null
    override val columnFormat: ColumnConfiguration.ColumnFormat? get() = null
    override val storageFormat: ColumnConfiguration.StorageFormat? get() = null
    override val references: ColsetRef? get() = null
    override val unsigned: Boolean get() = true
    override val zerofill: Boolean get() = false
    override val displayLength: Int get() = 11

    override fun copyConfiguration(
        newName: String?,
        owner: Table,
    ): NumberColumnConfiguration<Int, NumericColumnType.INT_T> {
        throw UnsupportedOperationException()
    }

    override fun ref(): ColumnRef<Int, NumericColumnType.INT_T, NumericColumn<Int, NumericColumnType.INT_T>> {
        throw UnsupportedOperationException()
    }

    override fun toDDL(): CharSequence {
        throw UnsupportedOperationException("A table column cannot be a pure function.")
    }

    override fun matches(
        typeName: String,
        size: Int,
        notNull: Boolean?,
        autoincrement: Boolean?,
        default: String?,
        comment: String?,
    ): Boolean {
        return false
    }

    override fun matches(columnData: ColumnsResults.Data): Boolean = false
}