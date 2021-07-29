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
    //TableMetaResultBase
    private val idxTableCat by lazyColIdx("TABLE_CAT")
    val tableCatalog: String? get() = resultSet.getString(idxTableCat)

    private val idxTableSchem by lazyColIdx("TABLE_SCHEM")
    val tableScheme: String? get() = resultSet.getString(idxTableSchem)
    private val idxTableName by lazyColIdx("TABLE_NAME")
    val tableName: String get() = resultSet.getString(idxTableName)
    private val idxTableType by lazyColIdx("TABLE_TYPE")
    val tableType: String get() = resultSet.getString(idxTableType)
    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    val columnName: String get() = resultSet.getString(idxColumnName)
    private val idxColumnSize by lazyColIdx("COLUMN_SIZE")
    val columnSize: Int get() = resultSet.getInt(idxColumnSize)
    private val idxBufferLength by lazyColIdx("BUFFER_LENGTH")
    val bufferLength: String get() = resultSet.getString(idxBufferLength)
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    val decimalDigits: Int get() = resultSet.getInt(idxDecimalDigits)
    private val idxNumPrecRadix by lazyColIdx("NUM_REC_RADIX")
    val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
    private val idxColumnDef by lazyColIdx("COLUMN_DEF")
    val columnDefault: String? get() = resultSet.getString(idxColumnDef)
    private val idxIsAutoIncrement by lazyColIdx("IS_AUTOINCREMENT")
    val isAutoIncrement: Boolean? get() = resultSet.optionalBoolean(idxIsAutoIncrement)
    private val idxIsGeneratedColumn by lazyColIdx("IS_GENERATEDCOLUMN")
    val isGeneratedColumn: Boolean? get() = resultSet.optionalBoolean(idxIsGeneratedColumn)
}