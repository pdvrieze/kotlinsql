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

package io.github.pdvrieze.kotlinsql.metadata.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.metadata.AbstractMetadataResultSet
import java.sql.DatabaseMetaData
import java.sql.ResultSet

@Suppress("unused", "MemberVisibilityCanBePrivate")
@OptIn(UnmanagedSql::class)
abstract class AbstractRowResult<R: AbstractRowResult<R>>
@UnmanagedSql
constructor(rs: ResultSet) : AbstractMetadataResultSet<R>(rs) {

    enum class PseudoColumn {
        BESTROWUNKNOWN,
        BESTROWNOTPSEUDO,
        BESTROWPSEUDO
    }

    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxTypeName by lazyColIdx("TYPE_NAME")
    private val idxColumnSize by lazyColIdx("COLUMN_SIZE")
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    private val idxPseudoColumn by lazyColIdx("PSEUDO_COLUMN")

    val columnName: String get() = resultSet.getString(idxColumnName)
    val dataType: String get() = resultSet.getString(idxDataType)
    val typeName: String get() = resultSet.getString(idxTypeName)
    val precision: String get() = resultSet.getString(idxColumnSize)

    @Deprecated("Use precision as the column name, this is an alias", ReplaceWith("precision"))
    inline val columnSize
        get() = precision
    val decimalDigits: Short get() = resultSet.getShort(idxDecimalDigits)

    val pseudoColumn: PseudoColumn = when (resultSet.getShort(idxPseudoColumn).toInt()) {
        DatabaseMetaData.bestRowUnknown   -> PseudoColumn.BESTROWUNKNOWN
        DatabaseMetaData.bestRowPseudo    -> PseudoColumn.BESTROWPSEUDO
        DatabaseMetaData.bestRowNotPseudo -> PseudoColumn.BESTROWNOTPSEUDO
        else                              -> throw IllegalArgumentException(
            "Unexpected pseudoColumn value ${resultSet.getShort(idxPseudoColumn)}")
    }

    open class Data(data: AbstractRowResult<*>) {
        val columnName: String = data.columnName
        val dataType: String = data.dataType
        val typeName: String = data.typeName
        val precision: String = data.precision
        val decimalDigits: Short = data.decimalDigits
        val pseudoColumn: PseudoColumn = data.pseudoColumn
    }

}