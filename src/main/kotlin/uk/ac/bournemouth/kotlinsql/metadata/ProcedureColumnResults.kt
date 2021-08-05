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
class ProcedureColumnResults(rs: ResultSet) : DataResults(rs) {
    private val idxProcedureCat by lazyColIdx("PROCEDURE_CAT")
    private val idxProcedureSchem by lazyColIdx("PROCEDURE_SCHEM")
    private val idxProcedureName by lazyColIdx("PROCEDURE_NAME")

    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    private val idxColumnType by lazyColIdx("COLUMN_TYPE")
    private val idxPrecision by lazyColIdx("PRECISION")
    private val idxLength by lazyColIdx("LENGTH")
    private val idxScale by lazyColIdx("SCALE")
    private val idxRadix by lazyColIdx("RADIX")

    private val idxColumnDef by lazyColIdx("COLUMN_DEF")
    private val idxSpecificName by lazyColIdx("SPECIFIC_NAME")


    val procedureCatalog: String? get() = resultSet.getString(idxProcedureCat)
    val procedureScheme: String? get() = resultSet.getString(idxProcedureSchem)
    val procedureName: String get() = resultSet.getString(idxProcedureName)
    val columnName: String get() = resultSet.getString(idxColumnName)
    val columnType: String get() = resultSet.getString(idxColumnType)
    val precision: Int get() = resultSet.getInt(idxPrecision)
    val length: Int get() = resultSet.getInt(idxLength)
    val scale: Short? get() = resultSet.getShort(idxScale).let { result -> if (resultSet.wasNull()) null else result }
    val radix: Short get() = resultSet.getShort(idxRadix)

    val columnDef: String? get() = resultSet.getString(idxColumnDef)

    val specificName: String get() = resultSet.getString(idxSpecificName)

}