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

package io.github.pdvrieze.jdbc.recorder

import io.github.pdvrieze.jdbc.recorder.actions.*
import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.Table
import java.sql.*
import java.util.*
import java.util.concurrent.Executor

class RecordingConnection(delegate: Connection, val db: Database? = null) : WrappingActionRecorder<Connection>(delegate), Connection {
    val actions = mutableListOf<Action>()

/*
    var tables: List<Table> = db?.run { _tables.toList() } ?: emptyList()
        set(value) {
            field = value.toList()
        }

    private var isClosed = false
    private var autoCommit = true
    private var nextSavePoint = 1
*/

    override fun recordAction(action: Action) {
        actions.add(action)
    }

    override fun prepareStatement(sql: String): PreparedStatement {
        return RecordingPreparedStatement(delegate.prepareStatement(sql), sql).also { actions.add(it) }
    }

    override fun prepareStatement(sql: String, resultSetType: Int, resultSetConcurrency: Int): PreparedStatement {
        return RecordingPreparedStatement(
            delegate.prepareStatement(sql, resultSetType, resultSetConcurrency),
            sql
        ).also { actions.add(it) }
    }

    override fun prepareStatement(
        sql: String,
        resultSetType: Int,
        resultSetConcurrency: Int,
        resultSetHoldability: Int
    ): PreparedStatement {
        return RecordingPreparedStatement(
            delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
            sql
        ).also { actions.add(it) }
    }

    override fun prepareStatement(sql: String, autoGeneratedKeys: Int): PreparedStatement {
        return RecordingPreparedStatement(
            delegate.prepareStatement(sql, autoGeneratedKeys),
            sql
        ).also { actions.add(it) }
    }

    override fun prepareStatement(sql: String, columnIndexes: IntArray): PreparedStatement {
        return RecordingPreparedStatement(
            delegate.prepareStatement(sql, columnIndexes),
            sql
        ).also { actions.add(it) }
    }

    override fun prepareStatement(sql: String, columnNames: Array<out String>): PreparedStatement {
        return RecordingPreparedStatement(
            delegate.prepareStatement(sql, columnNames),
            sql
        ).also { actions.add(it) }
    }

    override fun rollback() {
        actions.add(Rollback)
        delegate.rollback()
    }

    override fun rollback(savepoint: Savepoint) {
        actions.add(Rollback(savepoint))
        delegate.rollback(savepoint)
    }

    override fun getHoldability(): Int {
        record()
        return delegate.holdability
    }

    override fun setNetworkTimeout(executor: Executor?, milliseconds: Int) {
        record(executor, milliseconds)
        delegate.setNetworkTimeout(executor, milliseconds)
    }

    override fun commit() {
        actions.add(Commit)
        delegate.commit()
    }

    override fun setTransactionIsolation(level: Int) {
        record(level)
        delegate.transactionIsolation = level
    }

    override fun setAutoCommit(autoCommit: Boolean) {
        actions.add(SetAutoCommit(autoCommit))
        delegate.autoCommit = autoCommit
    }

    override fun abort(executor: Executor?) {
        record(executor)
        delegate.abort(executor)
    }

    override fun prepareCall(sql: String): CallableStatement {
        return record(sql) {
            RecordingCallableStatement(delegate.prepareCall(sql), sql)
        }
    }

    override fun prepareCall(sql: String, resultSetType: Int, resultSetConcurrency: Int): CallableStatement = record(sql, resultSetType, resultSetConcurrency) {
        RecordingCallableStatement(
            delegate.prepareCall(sql, resultSetType, resultSetConcurrency),
            sql
        )

    }

    override fun prepareCall(sql: String, resultSetType: Int, resultSetConcurrency: Int, resultSetHoldability: Int): CallableStatement =
        record(sql, resultSetType, resultSetConcurrency, resultSetHoldability) {
            RecordingCallableStatement(
                delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
                sql
            )
        }

    override fun getClientInfo(name: String?): String = record(name) {
        delegate.getClientInfo(name)
    }

    override fun getClientInfo(): Properties = record {
        delegate.clientInfo
    }

    override fun getAutoCommit(): Boolean {
        return delegate.autoCommit.also { actions.add(StringAction("getAutoCommit() -> $it")) }
    }

    override fun setCatalog(catalog: String?) {
        record(catalog)
        delegate.catalog = catalog
    }

    override fun getWarnings(): SQLWarning = record {
        delegate.warnings
    }

    override fun getCatalog(): String = record {
        delegate.catalog
    }

    override fun setHoldability(holdability: Int) {
        record(holdability)
        delegate.holdability = holdability
    }

    override fun getSchema(): String = record {
        delegate.schema
    }

    override fun isValid(timeout: Int): Boolean = record(timeout) {
        delegate.isValid(timeout)
    }

    override fun close() {
        recordAction(ConnectionClose)
        delegate.close()
    }

    override fun isClosed(): Boolean = record {
        delegate.isClosed
    }

    override fun createNClob(): NClob = record {
        delegate.createNClob()
    }

    override fun createBlob(): Blob = record {
        delegate.createBlob()
    }

    override fun createArrayOf(typeName: String?, elements: Array<out Any>?): java.sql.Array = record(typeName, elements) {
        delegate.createArrayOf(typeName, elements)
    }

    override fun setReadOnly(readOnly: Boolean) {
        record(readOnly)
        delegate.isReadOnly = readOnly
    }

    override fun nativeSQL(sql: String?): String = record(sql) {
        delegate.nativeSQL(sql)
    }

    override fun createStruct(typeName: String?, attributes: Array<out Any>?): Struct = record(typeName, attributes) {
        delegate.createStruct(typeName, attributes)
    }

    override fun setClientInfo(name: String?, value: String?) {
        record(name, value)
        delegate.setClientInfo(name, value)
    }

    override fun setClientInfo(properties: Properties?) {
        record(properties)
        delegate.clientInfo = properties
    }

    override fun releaseSavepoint(savepoint: Savepoint) {
        actions.add(ReleaseSavepoint(savepoint))
    }

    override fun createClob(): Clob = record {
        delegate.createClob()
    }

    override fun isReadOnly(): Boolean = record {
        delegate.isReadOnly
    }

    override fun createStatement(): Statement = record {
        delegate.createStatement()
    }

    override fun createStatement(resultSetType: Int, resultSetConcurrency: Int): Statement = record(resultSetType, resultSetConcurrency) {
        delegate.createStatement(resultSetType, resultSetConcurrency)
    }

    override fun createStatement(resultSetType: Int, resultSetConcurrency: Int, resultSetHoldability: Int): Statement = record(resultSetType, resultSetConcurrency, resultSetHoldability) {
        delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability)
    }

    override fun setSavepoint(): Savepoint = record {
        delegate.setSavepoint()
    }

    override fun setSavepoint(name: String?): Savepoint = record {
        delegate.setSavepoint(name)
    }

    override fun getTypeMap(): MutableMap<String, Class<*>> = record {
        delegate.typeMap
    }

    override fun clearWarnings() {
        record()
        delegate.clearWarnings()
    }

    override fun getMetaData(): DatabaseMetaData = record {
        RecordingMetaData(delegate.metaData)
    }

    override fun getTransactionIsolation(): Int = record {
        delegate.transactionIsolation
    }

    override fun setSchema(schema: String?) {
        record(schema)
        delegate.schema = schema
    }

    override fun getNetworkTimeout(): Int = record {
        delegate.networkTimeout
    }

    override fun setTypeMap(map: MutableMap<String, Class<*>>?) {
        record(map)
        delegate.typeMap = map
    }

    override fun createSQLXML(): SQLXML = record {
        delegate.createSQLXML()
    }

    override fun toString(): String {
        return "RecordingConnection($db)"
    }

    inner class RecordingCallableStatement(delegate: CallableStatement, sql: String) :
        AbstractRecordingCallableStatement(this, delegate, sql), Action {

        override fun recordAction(action: Action) {
            actions.add(action)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is RecordingCallableStatement) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        override fun toString(): String {
            return "RecordingCallableStatement(\"$sql\")"
        }


    }

    inner class RecordingPreparedStatement(
        delegate: PreparedStatement,
        sql: String
    ) : AbstractRecordingPreparedStatement<PreparedStatement>(this, delegate, sql),
        Action {
        override fun recordAction(action: Action) {
            actions.add(action)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is RecordingPreparedStatement) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        override fun toString(): String {
            return "RecordingPreparedStatement(\"$sql\")"
        }


    }

    inner class RecordingResultSet(
        delegate: ResultSet,
        query: String,
        columns: Array<String>,
        data: List<Array<out Any?>>
    ) : AbstractRecordingResultSet(delegate, query), Action {
        constructor(delegate: ResultSet, query: String = "") : this(delegate, query, emptyArray(), emptyList())

        override fun recordAction(action: Action) {
            actions.add(action)
        }
    }

    inner class RecordingMetaData(delegate: DatabaseMetaData) : AbstractRecordingMetadata(delegate) {
        override fun recordAction(action: Action) {
            actions.add(action)
        }

        override fun getConnection(): RecordingConnection {
            return this@RecordingConnection
        }
    }

}
