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
import io.github.pdvrieze.kotlinsql.metadata.BestRowIdentifierResult
import java.sql.DatabaseMetaData
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
internal class BestRowIdentifierResultImpl @UnmanagedSql constructor(rs: ResultSet) :
    BaseRowResultImpl<BestRowIdentifierResult, BestRowIdentifierResult.Data>(rs), BestRowIdentifierResult {

    private val idxScope by lazyColIdx("SCOPE")

    override val scope: BestRowIdentifierResult.Scope
        get() = when (resultSet.getShort(idxScope).toInt()) {
            DatabaseMetaData.bestRowTemporary   -> BestRowIdentifierResult.Scope.BESTROWTEMPORARY
            DatabaseMetaData.bestRowTransaction -> BestRowIdentifierResult.Scope.BESTROWTRANSACTION
            DatabaseMetaData.bestRowSession     -> BestRowIdentifierResult.Scope.BESTROWSESSION
            else                                -> throw IllegalArgumentException(
                    "Unexpected scope value ${resultSet.getShort(idxScope)}")
        }

    @OptIn(ExperimentalStdlibApi::class)
    override fun toList(): List<BestRowIdentifierResult.Data> = toListImpl { BestRowIdentifierResult.Data(it) }

}