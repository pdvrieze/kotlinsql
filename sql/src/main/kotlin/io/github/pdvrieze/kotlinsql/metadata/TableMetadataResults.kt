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
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.metadata.impl.TableMetaResultBase
import io.github.pdvrieze.kotlinsql.metadata.impl.TableMetadataResultsImpl
import java.sql.ResultSet

interface TableMetadataResults: TableMetaResultBase<TableMetadataResults.Data> {

    val refGeneration: RefGeneration?
    val remarks: String?
    val selfReferencingColName: String?
    val tableType: String
    val typeCatalog: String?
    val typeScheme: String?
    val typeName: String?

    override fun data(): Data = Data(this)

    @Suppress("unused")
    enum class RefGeneration {
        SYSTEM,
        USER,
        DERIVED
    }

    class Data(data: TableMetadataResults): TableMetaResultBase.Data<Data>(data), TableMetadataResults {
        override val refGeneration: RefGeneration? = data.refGeneration
        override val remarks: String? = data.remarks
        override val selfReferencingColName: String? = data.selfReferencingColName
        override val tableType: String = data.tableType
        override val typeCatalog: String? = data.typeCatalog
        override val typeScheme: String? = data.typeScheme
        override val typeName: String? = data.typeName

        override fun data(): Data = this
    }

    companion object {
        @UnmanagedSql
        operator fun invoke(r: ResultSet): ResultSetWrapper<TableMetadataResults, Data> = TableMetadataResultsImpl(r)
    }

}
