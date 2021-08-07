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
import io.github.pdvrieze.kotlinsql.metadata.impl.AttributeResultsImpl
import java.sql.ResultSet

interface AttributeResults : DataResults<AttributeResults.Data> {
    val attrDefault: String?
    val attrName: String
    val attrSize: Int
    val attrTypeName: String?
    val decimalDigits: Int
    val numPrecRadix: Int
    val typeCatalog: String?
    val typeScheme: String?

    override fun data(): Data = Data(this)

    class Data(result: AttributeResults): DataResults.Data<Data>(result), AttributeResults {
        override val attrDefault: String? = result.attrDefault
        override val attrName: String = result.attrName
        override val attrSize: Int = result.attrSize
        override val attrTypeName: String? = result.attrTypeName
        override val decimalDigits: Int = result.decimalDigits
        override val numPrecRadix: Int = result.numPrecRadix
        override val typeCatalog: String? = result.typeCatalog
        override val typeScheme: String? = result.typeScheme

        override fun data(): Data = Data(this)
    }

    companion object {
        @UnmanagedSql
        operator fun invoke(r: ResultSet): ResultSetWrapper<AttributeResults, Data> = AttributeResultsImpl(r)
    }
}

