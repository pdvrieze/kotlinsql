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
import io.github.pdvrieze.kotlinsql.metadata.ColumnsResults
import io.github.pdvrieze.kotlinsql.metadata.optionalBoolean
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
internal class ColumnsResultsImpl @UnmanagedSql constructor(rs: ResultSet) :
    DataResultsImpl<ColumnsResults, ColumnsResults.Data>(rs),
    ColumnsResults {

    //TableMetaResultBase
    private val idxColumnDef by lazyColIdx("COLUMN_DEF")
    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    private val idxColumnSize by lazyColIdx("COLUMN_SIZE")
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    private val idxIsAutoIncrement by lazyColIdx("IS_AUTOINCREMENT")
    private val idxIsGeneratedColumn by lazyColIdx("IS_GENERATEDCOLUMN")
    private val idxNumPrecRadix by lazyColIdx("NUM_PREC_RADIX")
    private val idxTableCat by lazyColIdx("TABLE_CAT")
    private val idxTableName by lazyColIdx("TABLE_NAME")
    private val idxTableSchem by lazyColIdx("TABLE_SCHEM")

    override val columnDefault: String? get() = resultSet.getString(idxColumnDef)
    override val columnName: String get() = resultSet.getString(idxColumnName)
    override val columnSize: Int get() = resultSet.getInt(idxColumnSize)
    override val decimalDigits: Int get() = resultSet.getInt(idxDecimalDigits)
    override val isAutoIncrement: Boolean? get() = resultSet.optionalBoolean(idxIsAutoIncrement)
    override val isGeneratedColumn: Boolean? get() = resultSet.optionalBoolean(idxIsGeneratedColumn)
    override val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
    override val tableCatalog: String? get() = resultSet.getString(idxTableCat)
    override val tableName: String get() = resultSet.getString(idxTableName)
    override val tableScheme: String? get() = resultSet.getString(idxTableSchem)

    @OptIn(ExperimentalStdlibApi::class)
    override fun toList(): List<ColumnsResults.Data> = toListImpl { ColumnsResults.Data(it) }


}