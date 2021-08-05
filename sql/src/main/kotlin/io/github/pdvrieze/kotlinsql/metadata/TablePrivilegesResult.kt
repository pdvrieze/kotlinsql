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
import io.github.pdvrieze.kotlinsql.metadata.impl.TableMetaResultBase
import java.sql.ResultSet

@OptIn(UnmanagedSql::class)
@Suppress("unused")
class TablePrivilegesResult
@UnmanagedSql
    constructor(privileges: ResultSet) : TableMetaResultBase(privileges) {
    private val idxGrantee: Int by lazyColIdx("GRANTEE")
    private val idxGrantor: Int by lazyColIdx("GRANTOR")
    private val idxIsGrantable: Int by lazyColIdx("IS_GRANTABLE")
    private val idxPrivilege: Int by lazyColIdx("PRIVILEGE")

    val grantor get():String? = resultSet.getString(idxGrantor)
    val grantee get(): String = resultSet.getString(idxGrantee)
    val isGrantable get(): Boolean = resultSet.getBoolean(idxIsGrantable)
    val privilege get():String = resultSet.getString(idxPrivilege)

    @OptIn(ExperimentalStdlibApi::class)
    override fun toList(): List<TableMetaResultBase.Data> = buildList {
        while (next()) add(Data(this@TablePrivilegesResult))
    }

    class Data(data: TablePrivilegesResult): TableMetaResultBase.Data(data) {
        val grantor:String? = data.grantor
        val grantee: String = data.grantee
        val isGrantable: Boolean = data.isGrantable
        val privilege:String = data.privilege
    }
}