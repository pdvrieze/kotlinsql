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
import io.github.pdvrieze.kotlinsql.metadata.ProcedureResults
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
internal class ProcedureResultsImpl @UnmanagedSql constructor(attributes: ResultSet) :
    AbstractMetadataResultSet<ProcedureResults, ProcedureResults.Data>(attributes),
    ProcedureResults {

    private fun procedureType(sqlValue: Short) = ProcedureResults.ProcedureType.values().first { it.sqlValue == sqlValue }

    private val idxProcedureCat by lazyColIdx("PROCEDURE_CAT")
    private val idxProcedureSchem by lazyColIdx("PROCEDURE_SCHEM")
    private val idxProcedureName by lazyColIdx("PROCEDURE_NAME")
    private val idxProcedureType by lazyColIdx("PROCEDURE_TYPE")
    private val idxRemarks by lazyColIdx("REMARKS")
    private val idxSpecificName by lazyColIdx("SPECIFIC_NAME")

    override val procedureCatalog: String? get() = resultSet.getString(idxProcedureCat)
    override val procedureName: String get() = resultSet.getString(idxProcedureName)
    override val procedureScheme: String? get() = resultSet.getString(idxProcedureSchem)
    override val procedureType: ProcedureResults.ProcedureType get() = procedureType(resultSet.getShort(idxProcedureType))
    override val remarks: String? get() = resultSet.getString(idxRemarks)
    override val specificName: String get() = resultSet.getString(idxSpecificName)

    @OptIn(ExperimentalStdlibApi::class)
    override fun toList(): List<ProcedureResults.Data> = toListImpl { ProcedureResults.Data(it) }

}