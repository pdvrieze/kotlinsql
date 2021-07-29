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

import uk.ac.bournemouth.kotlinsql.IColumnType
import uk.ac.bournemouth.kotlinsql.metadata.values.Nullable
import uk.ac.bournemouth.kotlinsql.metadata.values.Searchable
import java.sql.ResultSet

@Suppress("unused")
class TypeInfoResults(resultSet: ResultSet) : AbstractMetadataResultSet(resultSet) {
    private val idxTypeName by lazyColIdx("TYPE_NAME")
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxPrecision by lazyColIdx("PRECISION")
    private val idxLiteralPrefix by lazyColIdx("LITERAL_PREFIX")
    private val idxLiteralSuffix by lazyColIdx("LITERAL_SUFFIX")
    private val idxCreateParams by lazyColIdx("CREATE_PARAMS")
    private val idxNullable by lazyColIdx("NULLABLE")
    private val idxCaseSensitive by lazyColIdx("CASE_SENSITIVE")
    private val idxSearchable by lazyColIdx("SEARCHABLE")
    private val idxUnsignedAttribute by lazyColIdx("UNSIGNED_ATTRIBUTE")
    private val idxFixedPrecScale by lazyColIdx("FIXED_PREC_SCALE")
    private val idxAutoIncrement by lazyColIdx("AUTO_INCREMENT")
    private val idxLocalTypeName by lazyColIdx("LOCAL_TYPE_NAME")
    private val idxMinimumScale by lazyColIdx("MINIMUM_SCALE")
    private val idxMaximumScale by lazyColIdx("MAXIMUM_SCALE")
    private val idxNumPrecRadix by lazyColIdx("NUM_PREC_RADIX")

    val typeName: String get() = resultSet.getString(idxTypeName)
    val dataType: IColumnType<*, *, *> get() = IColumnType.fromSqlType(resultSet.getInt(idxDataType))
    val precision: Int get() = resultSet.getInt(idxPrecision)
    val literalPrefix: String? get() = resultSet.getString(idxLiteralPrefix)
    val literalSuffix: String? get() = resultSet.getString(idxLiteralSuffix)
    val createParams: String? get() = resultSet.getString(idxCreateParams)
    val nullable: Nullable get() = Nullable.from(resultSet.getShort(idxNullable))
    val caseSensitive: Boolean get() = resultSet.getBoolean(idxCaseSensitive)
    val searchable: Searchable get() = Searchable.from(resultSet.getShort(idxSearchable))
    val unsignedAttribute: Boolean get() = resultSet.getBoolean(idxUnsignedAttribute)
    val fixedPrecScale: Boolean get() = resultSet.getBoolean(idxFixedPrecScale)
    val autoIncrement: Boolean get() = resultSet.getBoolean(idxAutoIncrement)
    val localTypeName: String get() = resultSet.getString(idxLocalTypeName)
    val minimumScale: Short get() = resultSet.getShort(idxMinimumScale)
    val maximumScale: Short get() = resultSet.getShort(idxMaximumScale)
    val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
}