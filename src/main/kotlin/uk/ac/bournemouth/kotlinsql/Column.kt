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

import uk.ac.bournemouth.kotlinsql.columns.LengthColumn
import java.sql.ResultSet

interface Column<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>> : ColumnRef<T, S, C> {
    fun ref(): ColumnRef<T, S, C>
    val notnull: Boolean?
    val unique: Boolean
    val autoincrement: Boolean
    val default: T?
    val comment: String?
    val columnFormat: ColumnConfiguration.ColumnFormat?
    val storageFormat: ColumnConfiguration.StorageFormat?
    val references: ColsetRef?

    fun toDDL(): CharSequence

    fun copyConfiguration(newName: String? = null, owner: Table): ColumnConfiguration<T, S, C, Any>

    /** Delegating function here as that means that */
    fun fromResultSet(rs: ResultSet, pos: Int): T? = type.fromResultSet(rs, pos)

    /**
     * Determine whether has the given properties
     */
    fun matches(
        typeName: String,
        size: Int,
        notNull: Boolean?,
        autoincrement: Boolean?,
        default: String?,
        comment: String?
    ): Boolean =
        this.type.typeName == typeName &&
                ((this !is LengthColumn) || length < 0 || length == size) &&
                if (this.notnull == null) (notNull == null || notNull == false) else (this.notnull == notNull) &&
                        this.autoincrement == autoincrement &&
                        this.default == default &&
                        this.comment == comment

}