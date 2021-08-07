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
import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.metadata.impl.TypeInfoResultsImpl
import io.github.pdvrieze.kotlinsql.metadata.values.Nullable
import io.github.pdvrieze.kotlinsql.metadata.values.Searchable
import java.sql.ResultSet

interface TypeInfoResults: ResultSetRow<TypeInfoResults.Data> {
    val autoIncrement: Boolean
    val caseSensitive: Boolean
    val createParams: String?
    val dataType: IColumnType<*, *, *>
    val fixedPrecScale: Boolean
    val literalPrefix: String?
    val literalSuffix: String?
    val localTypeName: String
    val maximumScale: Short
    val minimumScale: Short
    val nullable: Nullable
    val numPrecRadix: Int
    val precision: Int
    val searchable: Searchable
    val typeName: String
    val unsignedAttribute: Boolean

    override fun data(): Data = Data(this)

    class Data(data: TypeInfoResults): TypeInfoResults {
        override val autoIncrement: Boolean = data.autoIncrement
        override val caseSensitive: Boolean = data.caseSensitive
        override val createParams: String? = data.createParams
        override val dataType: IColumnType<*, *, *> = data.dataType
        override val fixedPrecScale: Boolean = data.fixedPrecScale
        override val literalPrefix: String? = data.literalPrefix
        override val literalSuffix: String? = data.literalSuffix
        override val localTypeName: String = data.localTypeName
        override val maximumScale: Short = data.maximumScale
        override val minimumScale: Short = data.minimumScale
        override val nullable: Nullable = data.nullable
        override val numPrecRadix: Int = data.numPrecRadix
        override val precision: Int = data.precision
        override val searchable: Searchable = data.searchable
        override val typeName: String = data.typeName
        override val unsignedAttribute: Boolean = data.unsignedAttribute

        override fun data(): Data = this
    }

    companion object {
        @UnmanagedSql
        operator fun invoke(r: ResultSet): ResultSetWrapper<TypeInfoResults, Data> = TypeInfoResultsImpl(r)
    }
}
