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
import io.github.pdvrieze.kotlinsql.metadata.impl.ColumnsResultsImpl
import java.sql.ResultSet

interface ColumnsResults: DataResults<ColumnsResults.Data> {
    val columnDefault: String?
    val columnName: String
    val columnSize: Int
    val decimalDigits: Int
    val isAutoIncrement: Boolean?
    val isGeneratedColumn: Boolean?
    val numPrecRadix: Int
    val tableCatalog: String?
    val tableName: String
    val tableScheme: String?

    override fun data(): Data = Data(this)

    class Data(result: ColumnsResults): DataResults.Data<Data>(result), ColumnsResults {
        override val columnDefault: String? = result.columnDefault
        override val columnName: String = result.columnName
        override val columnSize: Int = result.columnSize
        override val decimalDigits: Int = result.decimalDigits
        override val isAutoIncrement: Boolean? = result.isAutoIncrement
        override val isGeneratedColumn: Boolean? = result.isGeneratedColumn
        override val numPrecRadix: Int = result.numPrecRadix
        override val tableCatalog: String? = result.tableCatalog
        override val tableName: String = result.tableName
        override val tableScheme: String? = result.tableScheme

        override fun data(): Data = this
    }

    companion object {
        @UnmanagedSql
        operator fun invoke(r: ResultSet): ResultSetWrapper<ColumnsResults, Data> = ColumnsResultsImpl(r)
    }
}

