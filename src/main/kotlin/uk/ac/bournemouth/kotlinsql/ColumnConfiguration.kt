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

import kotlin.reflect.KProperty

@Suppress("FunctionName")
interface ColumnConfiguration<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>, out CONF_T : Any> {
    var name: String?
    val type: S
    var notnull: Boolean?
    var unique: Boolean
    var autoincrement: Boolean
    var default: T?
    var comment: String?
    var columnFormat: ColumnFormat?
    var storageFormat: StorageFormat?
    var references: ColsetRef?

    val NULL: Unit
        get() {
            notnull = false
        }

    val NOT_NULL: Unit
        get() {
            notnull = true
        }
    val AUTO_INCREMENT: Unit
        get() {
            autoincrement = true
        }
    val UNIQUE: Unit
        get() {
            unique = true
        }

    fun DEFAULT(value: T) {
        default = value
    }

    fun COMMENT(comment: String) {
        this.comment = comment
    }

    fun COLUMN_FORMAT(format: ColumnFormat) {
        columnFormat = format
    }

    fun STORAGE(format: StorageFormat) {
        storageFormat = format
    }

    fun REFERENCES(table: TableRef, col1: ColumnRef<*, *, *>, vararg columns: ColumnRef<*, *, *>) {
        references = ColsetRef(table, col1, *columns)
    }

    operator fun provideDelegate(thisRef: MutableTable, property: KProperty<*>): Table.FieldAccessor<T, S, C> {
        if (name.isNullOrBlank()) {
            name = property.name
        }
        return thisRef.add(this.newColumn(thisRef))
    }

    fun copy(newName: String? = null): CONF_T

    fun newColumn(table: TableRef): C

    enum class ColumnFormat { FIXED, MEMORY, DEFAULT }

    enum class StorageFormat { DISK, MEMORY, DEFAULT }
}