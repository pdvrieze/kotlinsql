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

import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.metadata.values.Nullable
import java.sql.ResultSet

@Suppress("unused")
abstract class DataResults(rs: ResultSet) : AbstractMetadataResultSet(rs) {

    private val idxCharOctetLength by lazyColIdx("CHAR_OCTET_LENGTH")
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxIsNullable by lazyColIdx("IS_NULLABLE")
    private val idxNullable by lazyColIdx("NULLABLE")
    private val idxOrdinalPosition by lazyColIdx("ORDINAL_POSITION")
    private val idxRemarks by lazyColIdx("REMARKS")
    private val idxTypeName by lazyColIdx("TYPE_NAME")

    val charOctetLength: Int get() = resultSet.getInt(idxCharOctetLength)
    val dataType: IColumnType<*, *, *> get() = IColumnType.fromSqlType(resultSet.getInt(idxDataType))
    val isNullable: Boolean? get() = resultSet.optionalBoolean(idxIsNullable)
    val nullable: Nullable get() = Nullable.from(resultSet.getInt(idxNullable).toShort())
    val ordinalPosition: Int get() = resultSet.getInt(idxOrdinalPosition)
    val remarks: String? get() = resultSet.getString(idxRemarks).let { if (it.isNullOrEmpty()) null else it }
    val typeName: String get() = resultSet.getString(idxTypeName)

    protected abstract fun data(): Data

    open class Data(results: DataResults) {
        val charOctetLength: Int = results.charOctetLength
        val dataType: IColumnType<*, *, *> = results.dataType
        val isNullable: Boolean? = results.isNullable
        val nullable: Nullable = results.nullable
        val ordinalPosition: Int = results.ordinalPosition
        val remarks: String? = results.remarks
        val typeName: String = results.typeName
    }
}