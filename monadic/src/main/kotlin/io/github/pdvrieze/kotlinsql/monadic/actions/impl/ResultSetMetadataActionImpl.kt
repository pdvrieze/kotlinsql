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

import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.metadata.AbstractMetadataResultSet
import io.github.pdvrieze.kotlinsql.monadic.MonadicDBConnection
import io.github.pdvrieze.kotlinsql.monadic.SafeDatabaseMetaData
import io.github.pdvrieze.kotlinsql.monadic.actions.ResultSetMetadataAction

internal class ResultSetMetadataActionImpl<DB : Database, Row : ResultSetRow>
internal constructor(private val provider: (SafeDatabaseMetaData) -> AbstractMetadataResultSet<Row>) :
    ResultSetWrapperProducingActionImpl<DB, AbstractMetadataResultSet<Row>, Row>({ close() }),
    ResultSetMetadataAction<DB, Row> {

    override fun getCloseableResource(connection: MonadicDBConnection<DB>): AbstractMetadataResultSet<Row> {
        return provider(SafeDatabaseMetaData(connection.rawConnection.metaData))
    }

    override fun wrapResultSet(initResult: AbstractMetadataResultSet<Row>): ResultSetWrapper<Row> {
        return initResult
    }

/*
    @OptIn(UnmanagedSql::class)
    fun eval(connection: MonadicDBConnection<DB>): RS {
        val metadata = SafeDatabaseMetaData(connection.rawConnection.metaData)
        val resultSetWrapper = provider(metadata)
        try {
            return block(resultSetWrapper)
        } finally {
            resultSetWrapper.close()
        }
    }
*/
}