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

package uk.ac.bournemouth.kotlinsql.columns

import uk.ac.bournemouth.kotlinsql.*

@Suppress("MemberVisibilityCanBePrivate")
class CustomColumnConfiguration<U : Any,
        T : Any,
        S : IColumnType<T, S, C>,
        C : Column<T, S, C>,
        CONF_T : ColumnConfiguration<T, S, C, CONF_T>>
constructor(
    val baseConfiguration: CONF_T,
    override val type: CustomColumnType<U, T, S, C, CONF_T>
) : ColumnConfiguration<U, CustomColumnType<U, T, S, C, CONF_T>, CustomColumnType<U, T, S, C, CONF_T>.CustomColumn, CustomColumnConfiguration<U, T, S, C, CONF_T>> {

    override fun newColumn(table: TableRef) = type.CustomColumn(table, baseConfiguration)

    override fun copy(newName: String?) = CustomColumnConfiguration(baseConfiguration.copy(newName), type)

    override var name: String?
        get() = baseConfiguration.name
        set(value) {
            baseConfiguration.name = value
        }

    override var notnull: Boolean?
        get() = baseConfiguration.notnull
        set(value) {
            baseConfiguration.notnull = value
        }

    override var unique: Boolean
        get() = baseConfiguration.unique
        set(value) {
            baseConfiguration.unique = value
        }

    override var autoincrement: Boolean
        get() = baseConfiguration.autoincrement
        set(value) {
            baseConfiguration.autoincrement = value
        }

    override var default: U?
        get() = baseConfiguration.default?.let { type.fromDb(it) }
        set(value) {
            baseConfiguration.default = value?.let { type.toDB(it) }
        }

    override var comment: String?
        get() = baseConfiguration.comment
        set(value) {
            baseConfiguration.comment = value
        }

    override var columnFormat: ColumnConfiguration.ColumnFormat?
        get() = baseConfiguration.columnFormat
        set(value) {
            baseConfiguration.columnFormat = value
        }

    override var storageFormat: ColumnConfiguration.StorageFormat?
        get() = baseConfiguration.storageFormat
        set(value) {
            baseConfiguration.storageFormat = value
        }

    override var references: ColsetRef?
        get() = baseConfiguration.references
        set(value) {
            baseConfiguration.references = value
        }
}