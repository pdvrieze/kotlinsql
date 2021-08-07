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
import java.sql.DatabaseMetaData
import java.sql.ResultSet

@Suppress("unused", "MemberVisibilityCanBePrivate")
@OptIn(UnmanagedSql::class)
internal abstract class BaseRowResultImpl<R: BaseRowResult<D>, D:BaseRowResult.Data<D>>
@UnmanagedSql
constructor(rs: ResultSet) : AbstractMetadataResultSet<R, D>(rs), BaseRowResult<D> {

    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxTypeName by lazyColIdx("TYPE_NAME")
    private val idxColumnSize by lazyColIdx("COLUMN_SIZE")
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    private val idxPseudoColumn by lazyColIdx("PSEUDO_COLUMN")

    override val columnName: String get() = resultSet.getString(idxColumnName)
    override val dataType: String get() = resultSet.getString(idxDataType)
    override val typeName: String get() = resultSet.getString(idxTypeName)
    override val precision: String get() = resultSet.getString(idxColumnSize)

    override val decimalDigits: Short get() = resultSet.getShort(idxDecimalDigits)

    override val pseudoColumn: BaseRowResult.PseudoColumn = when (resultSet.getShort(idxPseudoColumn).toInt()) {
        DatabaseMetaData.bestRowUnknown   -> BaseRowResult.PseudoColumn.BESTROWUNKNOWN
        DatabaseMetaData.bestRowPseudo    -> BaseRowResult.PseudoColumn.BESTROWPSEUDO
        DatabaseMetaData.bestRowNotPseudo -> BaseRowResult.PseudoColumn.BESTROWNOTPSEUDO
        else                              -> throw IllegalArgumentException(
            "Unexpected pseudoColumn value ${resultSet.getShort(idxPseudoColumn)}")
    }

}