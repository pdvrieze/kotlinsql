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

package uk.ac.bournemouth.kotlinsql.metadata

import java.sql.ResultSet

@Suppress("unused")
class ColumnsResults(rs: ResultSet) : DataResults(rs) {
    public override fun data(): Data = Data(this)

    //TableMetaResultBase
    private val idxBufferLength by lazyColIdx("BUFFER_LENGTH")
    private val idxColumnDef by lazyColIdx("COLUMN_DEF")
    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    private val idxColumnSize by lazyColIdx("COLUMN_SIZE")
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    private val idxIsAutoIncrement by lazyColIdx("IS_AUTOINCREMENT")
    private val idxIsGeneratedColumn by lazyColIdx("IS_GENERATEDCOLUMN")
    private val idxNumPrecRadix by lazyColIdx("NUM_REC_RADIX")
    private val idxTableCat by lazyColIdx("TABLE_CAT")
    private val idxTableName by lazyColIdx("TABLE_NAME")
    private val idxTableSchem by lazyColIdx("TABLE_SCHEM")
    private val idxTableType by lazyColIdx("TABLE_TYPE")

    val bufferLength: String get() = resultSet.getString(idxBufferLength)
    val columnDefault: String? get() = resultSet.getString(idxColumnDef)
    val columnName: String get() = resultSet.getString(idxColumnName)
    val columnSize: Int get() = resultSet.getInt(idxColumnSize)
    val decimalDigits: Int get() = resultSet.getInt(idxDecimalDigits)
    val isAutoIncrement: Boolean? get() = resultSet.optionalBoolean(idxIsAutoIncrement)
    val isGeneratedColumn: Boolean? get() = resultSet.optionalBoolean(idxIsGeneratedColumn)
    val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
    val tableCatalog: String? get() = resultSet.getString(idxTableCat)
    val tableName: String get() = resultSet.getString(idxTableName)
    val tableScheme: String? get() = resultSet.getString(idxTableSchem)
    val tableType: String get() = resultSet.getString(idxTableType)

    class Data(result: ColumnsResults): DataResults.Data(result) {
        val bufferLength: String = result.bufferLength
        val columnDefault: String? = result.columnDefault
        val columnName: String = result.columnName
        val columnSize: Int = result.columnSize
        val decimalDigits: Int = result.decimalDigits
        val isAutoIncrement: Boolean? = result.isAutoIncrement
        val isGeneratedColumn: Boolean? = result.isGeneratedColumn
        val numPrecRadix: Int = result.numPrecRadix
        val tableCatalog: String? = result.tableCatalog
        val tableName: String = result.tableName
        val tableScheme: String? = result.tableScheme
        val tableType: String = result.tableType
    }


}