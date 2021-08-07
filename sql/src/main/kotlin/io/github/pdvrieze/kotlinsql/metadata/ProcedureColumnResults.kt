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
import io.github.pdvrieze.kotlinsql.metadata.impl.KeysResultImpl
import io.github.pdvrieze.kotlinsql.metadata.impl.ProcedureColumnResultsImpl
import java.sql.ResultSet

interface ProcedureColumnResults: DataResults<ProcedureColumnResults.Data> {
    val columnDef: String?
    val columnName: String
    val columnType: String
    val length: Int
    val precision: Int
    val procedureCatalog: String?
    val procedureName: String
    val procedureScheme: String?
    val radix: Short
    val scale: Short?
    val specificName: String

    override fun data(): Data = Data(this)

    class Data(result: ProcedureColumnResults) : DataResults.Data<Data>(result), ProcedureColumnResults {
        override val columnDef: String? = result.columnDef
        override val columnName: String = result.columnName
        override val columnType: String = result.columnType
        override val length: Int = result.length
        override val precision: Int = result.precision
        override val procedureCatalog: String? = result.procedureCatalog
        override val procedureName: String = result.procedureName
        override val procedureScheme: String? = result.procedureScheme
        override val radix: Short = result.radix
        override val scale: Short? = result.scale
        override val specificName: String = result.specificName

        override fun data(): Data = this
    }

    companion object {
        @UnmanagedSql
        operator fun invoke(r: ResultSet): ResultSetWrapper<ProcedureColumnResults, Data> = ProcedureColumnResultsImpl(r)
    }

}
