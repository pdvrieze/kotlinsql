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
import java.sql.DatabaseMetaData
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
class ProcedureResults
@UnmanagedSql
constructor(attributes: ResultSet) : AbstractMetadataResultSet(attributes) {

    private fun procedureType(sqlValue: Short) = ProcedureType.values().first { it.sqlValue == sqlValue }

    private val idxProcedureCat by lazyColIdx("PROCEDURE_CAT")
    private val idxProcedureSchem by lazyColIdx("PROCEDURE_SCHEM")
    private val idxProcedureName by lazyColIdx("PROCEDURE_NAME")
    private val idxProcedureType by lazyColIdx("PROCEDURE_TYPE")
    private val idxRemarks by lazyColIdx("REMARKS")
    private val idxSpecificName by lazyColIdx("SPECIFIC_NAME")

    val procedureCatalog: String? get() = resultSet.getString(idxProcedureCat)
    val procedureName: String get() = resultSet.getString(idxProcedureName)
    val procedureScheme: String? get() = resultSet.getString(idxProcedureSchem)
    val procedureType: ProcedureType get() = procedureType(resultSet.getShort(idxProcedureType))
    val remarks: String? get() = resultSet.getString(idxRemarks)
    val specificName: String get() = resultSet.getString(idxSpecificName)

    enum class ProcedureType(val sqlValue: Short) {
        PROCEDURE_RESULT_UNKNOWN(DatabaseMetaData.procedureResultUnknown.toShort()),
        PROCEDURE_NO_RESULT(DatabaseMetaData.procedureNoResult.toShort()),
        PROCEDURE_RETURNS_RESULT(DatabaseMetaData.procedureReturnsResult.toShort())
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun toList(): List<Data>  = buildList {
        while(next()) add(Data(this@ProcedureResults))
    }

    class Data(data: ProcedureResults) {
        val procedureCatalog: String? = data.procedureCatalog
        val procedureName: String = data.procedureName
        val procedureScheme: String? = data.procedureScheme
        val procedureType: ProcedureType = data.procedureType
        val remarks: String? = data.remarks
        val specificName: String = data.specificName
    }
}
