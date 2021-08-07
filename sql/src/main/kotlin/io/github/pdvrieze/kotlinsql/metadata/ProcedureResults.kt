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
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.metadata.impl.ProcedureResultsImpl
import java.sql.DatabaseMetaData
import java.sql.ResultSet

interface ProcedureResults: ResultSetRow<ProcedureResults.Data> {
    val procedureCatalog: String?
    val procedureName: String
    val procedureScheme: String?
    val procedureType: ProcedureType
    val remarks: String?
    val specificName: String

    override fun data(): Data = Data(this)

    @Suppress("unused")
    enum class ProcedureType(val sqlValue: Short) {
        PROCEDURE_RESULT_UNKNOWN(DatabaseMetaData.procedureResultUnknown.toShort()),
        PROCEDURE_NO_RESULT(DatabaseMetaData.procedureNoResult.toShort()),
        PROCEDURE_RETURNS_RESULT(DatabaseMetaData.procedureReturnsResult.toShort())
    }

    class Data(data: ProcedureResults): ProcedureResults {
        override val procedureCatalog: String? = data.procedureCatalog
        override val procedureName: String = data.procedureName
        override val procedureScheme: String? = data.procedureScheme
        override val procedureType: ProcedureType = data.procedureType
        override val remarks: String? = data.remarks
        override val specificName: String = data.specificName

        override fun data(): Data = this
    }

    companion object {
        @UnmanagedSql
        operator fun invoke(r: ResultSet): ResultSetWrapper<ProcedureResults, Data> = ProcedureResultsImpl(r)
    }
}
