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
import io.github.pdvrieze.kotlinsql.metadata.impl.TableColumnResultBase
import io.github.pdvrieze.kotlinsql.metadata.impl.TableMetaResultBase
import java.sql.ResultSet

@OptIn(UnmanagedSql::class)
@Suppress("unused")
class PrimaryKeyResults
@UnmanagedSql
constructor(rs: ResultSet) : TableColumnResultBase(rs) {
    private val idxKeySeq by lazyColIdx("KEY_SEQ")
    private val idxPkName by lazyColIdx("PK_NAME")

    val keySeq: Short get() = resultSet.getShort(idxKeySeq)
    val pkName: String? get() = resultSet.getString(idxPkName)

    @OptIn(ExperimentalStdlibApi::class)
    override fun toList(): List<TableMetaResultBase.Data> = buildList {
        while(next()) add(Data(this@PrimaryKeyResults))
    }

    class Data(data: PrimaryKeyResults): TableColumnResultBase.Data(data) {
        val keySeq: Short = data.keySeq
        val pkName: String? = data.pkName
    }
}