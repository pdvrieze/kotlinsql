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

package io.github.pdvrieze.kotlinsql.metadata

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
class ProcedureColumnResults
@UnmanagedSql
constructor(rs: ResultSet) : DataResults<ProcedureColumnResults>(rs) {
    private val idxColumnDef by lazyColIdx("COLUMN_DEF")
    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    private val idxColumnType by lazyColIdx("COLUMN_TYPE")
    private val idxLength by lazyColIdx("LENGTH")
    private val idxPrecision by lazyColIdx("PRECISION")
    private val idxProcedureCat by lazyColIdx("PROCEDURE_CAT")
    private val idxProcedureName by lazyColIdx("PROCEDURE_NAME")
    private val idxProcedureSchem by lazyColIdx("PROCEDURE_SCHEM")
    private val idxRadix by lazyColIdx("RADIX")
    private val idxScale by lazyColIdx("SCALE")
    private val idxSpecificName by lazyColIdx("SPECIFIC_NAME")

    val columnDef: String? get() = resultSet.getString(idxColumnDef)
    val columnName: String get() = resultSet.getString(idxColumnName)
    val columnType: String get() = resultSet.getString(idxColumnType)
    val length: Int get() = resultSet.getInt(idxLength)
    val precision: Int get() = resultSet.getInt(idxPrecision)
    val procedureCatalog: String? get() = resultSet.getString(idxProcedureCat)
    val procedureName: String get() = resultSet.getString(idxProcedureName)
    val procedureScheme: String? get() = resultSet.getString(idxProcedureSchem)
    val radix: Short get() = resultSet.getShort(idxRadix)
    val scale: Short? get() = resultSet.getShort(idxScale).let { result -> if (resultSet.wasNull()) null else result }
    val specificName: String get() = resultSet.getString(idxSpecificName)

    public override fun data(): Data = Data(this)

    @OptIn(ExperimentalStdlibApi::class)
    fun toList(): List<Data> = buildList {
        while(next()) add(Data(this@ProcedureColumnResults))
    }

    class Data(result: ProcedureColumnResults) : DataResults.Data(result) {
        val columnDef: String? = result.columnDef
        val columnName: String = result.columnName
        val columnType: String = result.columnType
        val length: Int = result.length
        val precision: Int = result.precision
        val procedureCatalog: String? = result.procedureCatalog
        val procedureName: String = result.procedureName
        val procedureScheme: String? = result.procedureScheme
        val radix: Short = result.radix
        val scale: Short? = result.scale
        val specificName: String = result.specificName
    }
}