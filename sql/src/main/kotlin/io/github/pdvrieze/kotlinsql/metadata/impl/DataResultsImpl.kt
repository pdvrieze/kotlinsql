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

package io.github.pdvrieze.kotlinsql.metadata.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.metadata.DataResults
import io.github.pdvrieze.kotlinsql.metadata.optionalBoolean
import io.github.pdvrieze.kotlinsql.metadata.values.Nullable
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
internal abstract class DataResultsImpl<R: DataResults<D>, D: DataResults.Data<D>>
@UnmanagedSql
constructor(rs: ResultSet) : AbstractMetadataResultSet<R, D>(rs), DataResults<D> {

    private val idxCharOctetLength by lazyColIdx("CHAR_OCTET_LENGTH")
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxIsNullable by lazyColIdx("IS_NULLABLE")
    private val idxNullable by lazyColIdx("NULLABLE")
    private val idxOrdinalPosition by lazyColIdx("ORDINAL_POSITION")
    private val idxRemarks by lazyColIdx("REMARKS")
    private val idxTypeName by lazyColIdx("TYPE_NAME")

    override val charOctetLength: Int get() = resultSet.getInt(idxCharOctetLength)
    override val dataType: IColumnType<*, *, *> get() = IColumnType.fromSqlType(resultSet.getInt(idxDataType))
    override val isNullable: Boolean? get() = resultSet.optionalBoolean(idxIsNullable)
    override val nullable: Nullable get() = Nullable.from(resultSet.getInt(idxNullable).toShort())
    override val ordinalPosition: Int get() = resultSet.getInt(idxOrdinalPosition)
    override val remarks: String? get() = resultSet.getString(idxRemarks).let { if (it.isNullOrEmpty()) null else it }
    override val typeName: String get() = resultSet.getString(idxTypeName)

}