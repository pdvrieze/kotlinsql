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
import java.sql.ResultSet

@OptIn(UnmanagedSql::class)
internal abstract class TableMetaResultBaseImpl<R: TableMetaResultBase<D>, D: TableMetaResultBase.Data<D>>
    @UnmanagedSql
    constructor(rs: ResultSet) : SchemaResultsBaseImpl<R, D>(rs), TableMetaResultBase<D> {

    private val idxTableName by lazyColIdx("TABLE_NAME")

    override val tableName: String get() = resultSet.getString(idxTableName)
}