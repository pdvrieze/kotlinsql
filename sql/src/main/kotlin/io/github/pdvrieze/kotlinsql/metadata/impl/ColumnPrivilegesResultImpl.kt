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
import io.github.pdvrieze.kotlinsql.metadata.AttributeResults
import io.github.pdvrieze.kotlinsql.metadata.ColumnPrivilegesResult
import io.github.pdvrieze.kotlinsql.metadata.optionalBoolean
import java.sql.ResultSet

@OptIn(UnmanagedSql::class)
@Suppress("unused")
internal class ColumnPrivilegesResultImpl @UnmanagedSql constructor(privileges: ResultSet) :
    TableColumnResultBaseImpl<ColumnPrivilegesResult, ColumnPrivilegesResult.Data>(privileges),
    ColumnPrivilegesResult {

    private val idxGrantee: Int by lazyColIdx("GRANTEE")
    private val idxGrantor: Int by lazyColIdx("GRANTOR")
    private val idxPrivilege: Int by lazyColIdx("PRIVILEGE")
    private val idxIsGrantable: Int by lazyColIdx("IS_GRANTABLE")

    override val grantor: String? get() = resultSet.getString(idxGrantor)
    override val grantee: String get() = resultSet.getString(idxGrantee)
    override val privilege: String get() = resultSet.getString(idxPrivilege)

    override val isGrantable
        get():Boolean? = resultSet.optionalBoolean(idxIsGrantable)

    @OptIn(ExperimentalStdlibApi::class)
    override fun toList(): List<ColumnPrivilegesResult.Data> = toListImpl { ColumnPrivilegesResult.Data(it) }

}