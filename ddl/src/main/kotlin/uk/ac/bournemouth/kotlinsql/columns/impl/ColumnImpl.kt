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

package uk.ac.bournemouth.kotlinsql.columns.impl

import uk.ac.bournemouth.kotlinsql.*
import uk.ac.bournemouth.kotlinsql.columns.ColumnType
import uk.ac.bournemouth.kotlinsql.impl.columnListToDDL

/**
 * Implementation for the database API
 */
internal abstract class ColumnImpl<T : Any, S : ColumnType<T, S, C>, C : Column<T, S, C>>
internal constructor(
    override val table: TableRef,
    override val type: S,
    override val name: String,
    override val notnull: Boolean?,
    override val unique: Boolean,
    override val autoincrement: Boolean,
    override val default: T?,
    override val comment: String?,
    override val columnFormat: ColumnConfiguration.ColumnFormat?,
    override val storageFormat: ColumnConfiguration.StorageFormat?,
    override val references: ColsetRef?,
    val unsigned: Boolean = false,
    val zerofill: Boolean = false,
    val displayLength: Int = -1,
    val precision: Int = -1,
    val scale: Int = -1,
    val charset: String? = null,
    val collation: String? = null,
    val binary: Boolean = false,
    val length: Int = -1
) : Column<T, S, C> {

    @Suppress("UNCHECKED_CAST")
    override fun ref(): C {
        return this as C
    }

    override fun toDDL(): CharSequence {
        val result = StringBuilder()
        result.apply {
            append('`').append(name).append("` ").append(type.typeName)
            if (this@ColumnImpl.length > 0) append('(').append(this@ColumnImpl.length).append(')')
            else if (displayLength > 0) append('(').append(displayLength).append(')')
            if (unsigned) append(" UNSIGNED")
            if (zerofill) append(" ZEROFILL")
            if (binary) append(" BINARY")
            charset?.let { append(" CHARACTER SET ").append(it) }
            collation?.let { append(" COLLATE ").append(it) }
            notnull?.let { append(if (it) " NOT NULL" else " NULL") }
            default?.let {
                append(" DEFAULT ")
                if (it is CharSequence)
                    append('\'').append(it).append('\'')
                else append(it)
            }
            if (autoincrement) append(" AUTO_INCREMENT")
            if (unique) append(" UNIQUE")
            comment?.let { append(" '").append(comment).append('\'') }
            columnFormat?.let { append(" COLUMN_FORMAT ").append(it.name) }
            storageFormat?.let { append(" STORAGE ").append(it.name) }
            references?.let { append(" REFERENCES ").append(
                columnListToDDL(
                    it.table._name,
                    it.columns
                )
            ) }

        }

        return result
    }

    override fun toString(): String {
        return "${javaClass.simpleName}: ${toDDL()}"
    }

    override fun matches(
        typeName: String,
        size: Int,
        notNull: Boolean?,
        autoincrement: Boolean?,
        default: String?,
        comment: String?
    ): Boolean {
        return this.type.typeName == typeName &&
                (this.length < 0 || this.length == size) &&
                this.notnull == notNull &&
                this.autoincrement == autoincrement &&
                this.default == default &&
                this.comment == comment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ColumnImpl<*, *, *>

        if (table != other.table) return false
        if (type != other.type) return false
        if (name != other.name) return false
        if (notnull != other.notnull) return false
        if (unique != other.unique) return false
        if (autoincrement != other.autoincrement) return false
        if (default != other.default) return false
        if (comment != other.comment) return false
        if (columnFormat != other.columnFormat) return false
        if (storageFormat != other.storageFormat) return false
        if (references != other.references) return false
        if (unsigned != other.unsigned) return false
        if (zerofill != other.zerofill) return false
        if (displayLength != other.displayLength) return false
        if (precision != other.precision) return false
        if (scale != other.scale) return false
        if (charset != other.charset) return false
        if (collation != other.collation) return false
        if (binary != other.binary) return false
        if (length != other.length) return false

        return true
    }

    override fun hashCode(): Int {
        var result = table.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (notnull?.hashCode() ?: 0)
        result = 31 * result + unique.hashCode()
        result = 31 * result + autoincrement.hashCode()
        result = 31 * result + (default?.hashCode() ?: 0)
        result = 31 * result + (comment?.hashCode() ?: 0)
        result = 31 * result + (columnFormat?.hashCode() ?: 0)
        result = 31 * result + (storageFormat?.hashCode() ?: 0)
        result = 31 * result + (references?.hashCode() ?: 0)
        result = 31 * result + unsigned.hashCode()
        result = 31 * result + zerofill.hashCode()
        result = 31 * result + displayLength
        result = 31 * result + precision
        result = 31 * result + scale
        result = 31 * result + (charset?.hashCode() ?: 0)
        result = 31 * result + (collation?.hashCode() ?: 0)
        result = 31 * result + binary.hashCode()
        result = 31 * result + length
        return result
    }


}