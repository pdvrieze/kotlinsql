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
import io.github.pdvrieze.kotlinsql.metadata.ProcedureColumnResults
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
internal class ProcedureColumnResultsImpl @UnmanagedSql constructor(rs: ResultSet) :
    DataResultsImpl<ProcedureColumnResults, ProcedureColumnResults.Data>(rs),
    ProcedureColumnResults {

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

    override val columnDef: String? get() = resultSet.getString(idxColumnDef)
    override val columnName: String get() = resultSet.getString(idxColumnName)
    override val columnType: String get() = resultSet.getString(idxColumnType)
    override val length: Int get() = resultSet.getInt(idxLength)
    override val precision: Int get() = resultSet.getInt(idxPrecision)
    override val procedureCatalog: String? get() = resultSet.getString(idxProcedureCat)
    override val procedureName: String get() = resultSet.getString(idxProcedureName)
    override val procedureScheme: String? get() = resultSet.getString(idxProcedureSchem)
    override val radix: Short get() = resultSet.getShort(idxRadix)
    override val scale: Short? get() = resultSet.getShort(idxScale).let { result -> if (resultSet.wasNull()) null else result }
    override val specificName: String get() = resultSet.getString(idxSpecificName)

    @OptIn(ExperimentalStdlibApi::class)
    override fun toList(): List<ProcedureColumnResults.Data> = toListImpl { ProcedureColumnResults.Data(it) }

}