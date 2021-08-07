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

package io.github.pdvrieze.kotlinsql.monadic.actions.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.ddl.columns.PreparedStatementHelper
import io.github.pdvrieze.kotlinsql.monadic.MonadicDBConnection
import io.github.pdvrieze.util.kotlin.sql.PreparedStatementHelperImpl

internal abstract class DBBaseSqlAction<DB : Database, out O> : DBActionImpl<DB, PreparedStatementHelperImpl, O>() {
    @OptIn(UnmanagedSql::class)
    override fun doBegin(connection: MonadicDBConnection<DB>): PreparedStatementHelperImpl {
        return connection.prepareStatement(getQuery())
    }

    abstract fun getQuery(): String

    @OptIn(UnmanagedSql::class)
    override fun doClose(connection: MonadicDBConnection<DB>, initResult: PreparedStatementHelperImpl) {
        initResult.close()
    }
}