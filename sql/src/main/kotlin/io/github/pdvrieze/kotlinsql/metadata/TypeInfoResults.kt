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
import io.github.pdvrieze.kotlinsql.metadata.values.Nullable
import io.github.pdvrieze.kotlinsql.metadata.values.Searchable
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
class TypeInfoResults
@UnmanagedSql
constructor(resultSet: ResultSet) : AbstractMetadataResultSet<TypeInfoResults>(resultSet) {
    private val idxAutoIncrement by lazyColIdx("AUTO_INCREMENT")
    private val idxCaseSensitive by lazyColIdx("CASE_SENSITIVE")
    private val idxCreateParams by lazyColIdx("CREATE_PARAMS")
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxFixedPrecScale by lazyColIdx("FIXED_PREC_SCALE")
    private val idxLiteralPrefix by lazyColIdx("LITERAL_PREFIX")
    private val idxLiteralSuffix by lazyColIdx("LITERAL_SUFFIX")
    private val idxLocalTypeName by lazyColIdx("LOCAL_TYPE_NAME")
    private val idxMaximumScale by lazyColIdx("MAXIMUM_SCALE")
    private val idxMinimumScale by lazyColIdx("MINIMUM_SCALE")
    private val idxNumPrecRadix by lazyColIdx("NUM_PREC_RADIX")
    private val idxNullable by lazyColIdx("NULLABLE")
    private val idxPrecision by lazyColIdx("PRECISION")
    private val idxTypeName by lazyColIdx("TYPE_NAME")
    private val idxSearchable by lazyColIdx("SEARCHABLE")
    private val idxUnsignedAttribute by lazyColIdx("UNSIGNED_ATTRIBUTE")

    val autoIncrement: Boolean get() = resultSet.getBoolean(idxAutoIncrement)
    val caseSensitive: Boolean get() = resultSet.getBoolean(idxCaseSensitive)
    val createParams: String? get() = resultSet.getString(idxCreateParams)
    val dataType: IColumnType<*, *, *> get() = IColumnType.fromSqlType(resultSet.getInt(idxDataType))
    val fixedPrecScale: Boolean get() = resultSet.getBoolean(idxFixedPrecScale)
    val literalPrefix: String? get() = resultSet.getString(idxLiteralPrefix)
    val literalSuffix: String? get() = resultSet.getString(idxLiteralSuffix)
    val localTypeName: String get() = resultSet.getString(idxLocalTypeName)
    val maximumScale: Short get() = resultSet.getShort(idxMaximumScale)
    val minimumScale: Short get() = resultSet.getShort(idxMinimumScale)
    val nullable: Nullable get() = Nullable.from(resultSet.getShort(idxNullable))
    val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
    val precision: Int get() = resultSet.getInt(idxPrecision)
    val searchable: Searchable get() = Searchable.from(resultSet.getShort(idxSearchable))
    val typeName: String get() = resultSet.getString(idxTypeName)
    val unsignedAttribute: Boolean get() = resultSet.getBoolean(idxUnsignedAttribute)

    @OptIn(ExperimentalStdlibApi::class)
    fun toList(): List<Data> = buildList {
        while (next()) add(Data(this@TypeInfoResults))
    }

    class Data(data: TypeInfoResults) {
        val autoIncrement: Boolean = data.autoIncrement
        val caseSensitive: Boolean = data.caseSensitive
        val createParams: String? = data.createParams
        val dataType: IColumnType<*, *, *> = data.dataType
        val fixedPrecScale: Boolean = data.fixedPrecScale
        val literalPrefix: String? = data.literalPrefix
        val literalSuffix: String? = data.literalSuffix
        val localTypeName: String = data.localTypeName
        val maximumScale: Short = data.maximumScale
        val minimumScale: Short = data.minimumScale
        val nullable: Nullable = data.nullable
        val numPrecRadix: Int = data.numPrecRadix
        val precision: Int = data.precision
        val searchable: Searchable = data.searchable
        val typeName: String = data.typeName
        val unsignedAttribute: Boolean = data.unsignedAttribute
    }
}