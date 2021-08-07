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
import io.github.pdvrieze.kotlinsql.metadata.SchemaResults
import io.github.pdvrieze.kotlinsql.metadata.SchemaResultsBase
import java.sql.ResultSet

@OptIn(UnmanagedSql::class)
internal abstract class SchemaResultsBaseImpl<R : SchemaResultsBase<D>, D:SchemaResultsBase.Data<D>>
    @UnmanagedSql
    constructor(rs: ResultSet) : AbstractMetadataResultSet<R, D>(rs), SchemaResultsBase<D> {
    private val idxTableCat by lazyColIdx("TABLE_CAT")
    private val idxTableSchem by lazyColIdx("TABLE_SCHEM")

    override val tableCatalog: String? get() = resultSet.getString(idxTableCat)
    override val tableScheme: String? get() = resultSet.getString(idxTableSchem)

}