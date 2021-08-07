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
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.metadata.impl.BaseRowResult
import io.github.pdvrieze.kotlinsql.metadata.impl.VersionColumnsResultImpl
import java.sql.ResultSet

interface VersionColumnsResult : BaseRowResult<VersionColumnsResult.Data> {
    val bufferSize: Int

    override fun data(): Data = Data(this)

    class Data(data: VersionColumnsResult) : BaseRowResult.Data<Data>(data),
                                             VersionColumnsResult {
        override val bufferSize = data.bufferSize

        override fun data(): Data = this
    }

    companion object {
        @UnmanagedSql
        operator fun invoke(r: ResultSet): ResultSetWrapper<VersionColumnsResult, Data> = VersionColumnsResultImpl(r)
    }
}
