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

import uk.ac.bournemouth.kotlinsql.metadata.impl.TableColumnResultBase
import java.sql.ResultSet

@Suppress("unused")
class ColumnPrivilegesResult(privileges: ResultSet) : TableColumnResultBase(privileges) {
    private val idxGrantor: Int by lazyColIdx("GRANTOR")
    val grantor get():String? = resultSet.getString(idxGrantor)
    private val idxGrantee: Int by lazyColIdx("GRANTEE")
    val grantee get(): String = resultSet.getString(idxGrantee)
    private val idxPrivilege: Int by lazyColIdx("PRIVILEGE")
    val privilege get():String = resultSet.getString(idxPrivilege)

    private val idxIsGrantable: Int by lazyColIdx("IS_GRANTABLE")

    val isGrantable
        get():Boolean? = resultSet.getString(
                idxIsGrantable)?.let { if (it == "YES") true else if (it == "NO") false else null }
}