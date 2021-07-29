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

import uk.ac.bournemouth.kotlinsql.metadata.impl.TableMetaResultBase
import java.sql.ResultSet

@Suppress("unused")
class TableMetadataResults(rs: ResultSet) : TableMetaResultBase(rs) {
    enum class RefGeneration {
        SYSTEM,
        USER,
        DERIVED
    }

    private val idxRefGeneration by lazyColIdx("REF_GENERATION")
    private val idxRemarks by lazyColIdx("REMARKS")
    private val idxSelfReferencingColName by lazyColIdx("SELF_REFERENCING_COL_NAME")
    private val idxTableType by lazyColIdx("TABLE_TYPE")
    private val idxTypeCat by lazyColIdx("TYPE_CAT")
    private val idxTypeSchem by lazyColIdx("TYPE_SCHEM")
    private val idxTypeName by lazyColIdx("TYPE_NAME")

    val refGeneration: RefGeneration? get() = resultSet.getString(idxRefGeneration)?.let { RefGeneration.valueOf(it) }
    val remarks: String? get() = resultSet.getString(idxRemarks)
    val selfReferencingColName: String? get() = resultSet.getString(idxSelfReferencingColName)
    val tableType: String get() = resultSet.getString(idxTableType)
    val typeCatalog: String? get() = resultSet.getString(idxTypeCat)
    val typeScheme: String? get() = resultSet.getString(idxTypeSchem)
    val typeName: String? get() = resultSet.getString(idxTypeName)
}