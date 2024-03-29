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

package io.github.pdvrieze.kotlinsql.test.helpers

import io.github.pdvrieze.jdbc.recorder.actions.Action
import java.io.InputStream
import java.io.Reader
import java.math.BigDecimal
import java.net.URL
import java.sql.*
import java.sql.Date
import java.util.*

abstract class AbstractDummyPreparedStatement(
    connection: DummyConnection,
    val sql: String,
    resultSetType: Int = ResultSet.TYPE_SCROLL_INSENSITIVE,
    resultSetConcurrency: Int = ResultSet.CONCUR_UPDATABLE,
    resultSetHoldability: Int = ResultSet.CLOSE_CURSORS_AT_COMMIT
) : AbstractDummyStatement(connection, resultSetType, resultSetConcurrency), PreparedStatement {

    private var curParamRow = 0
    val params = mutableListOf<MutableList<Any?>>()

    override fun close() {
        isClosed = true
    }

    override fun setRef(parameterIndex: Int, x: Ref?) {}

    override fun setBlob(parameterIndex: Int, x: Blob?) {}

    override fun setBlob(parameterIndex: Int, inputStream: InputStream?, length: Long) {}

    override fun setBlob(parameterIndex: Int, inputStream: InputStream?) {}

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?, length: Int) {}

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?, length: Long) {}

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?) {}

    override fun setArray(parameterIndex: Int, x: java.sql.Array?) {}

    override fun setDate(parameterIndex: Int, x: Date?) {}

    override fun setDate(parameterIndex: Int, x: Date?, cal: Calendar?) {}

    override fun clearParameters() {}

    override fun setObject(parameterIndex: Int, x: Any?, targetSqlType: Int) {}

    override fun setObject(parameterIndex: Int, x: Any?) {}

    override fun setObject(parameterIndex: Int, x: Any?, targetSqlType: Int, scaleOrLength: Int) {}

    override fun setBytes(parameterIndex: Int, x: ByteArray?) {}

    override fun setLong(parameterIndex: Int, x: Long) {}

    override fun setClob(parameterIndex: Int, x: Clob?) {}

    override fun setClob(parameterIndex: Int, reader: Reader?, length: Long) {}

    override fun setClob(parameterIndex: Int, reader: Reader?) {}

    override fun executeQuery(): ResultSet = when (sql) {
        "SELECT `fullname` FROM `users`" -> connection.DummyResultSet(sql, arrayOf("fullname"), listOf(arrayOf("Joe Blogs")))
        else -> connection.DummyResultSet(sql)
    }

    override fun executeQuery(sql: String?): ResultSet {
        throw SQLException("ExecuteQuery on a prepared statement is invalid")
    }

    override fun setUnicodeStream(parameterIndex: Int, x: InputStream?, length: Int) {
        TODO("not implemented")
    }

    override fun setNString(parameterIndex: Int, value: String?) {
        TODO("not implemented")
    }

    override fun getMaxFieldSize(): Int {
        TODO("not implemented")
    }

    override fun setURL(parameterIndex: Int, x: URL?) {
        TODO("not implemented")
    }

    override fun getUpdateCount(): Int {
        TODO("not implemented")
    }

    override fun setRowId(parameterIndex: Int, x: RowId?) {
        TODO("not implemented")
    }

    override fun setFloat(parameterIndex: Int, x: Float) {
        TODO("not implemented")
    }

    override fun setFetchDirection(direction: Int) {
        TODO("not implemented")
    }

    override fun getFetchSize(): Int {
        TODO("not implemented")
    }

    override fun setTime(parameterIndex: Int, x: Time?) {
        TODO("not implemented")
    }

    override fun setTime(parameterIndex: Int, x: Time?, cal: Calendar?) {
        TODO("not implemented")
    }

    override fun executeBatch(): IntArray = IntArray(params.size) { 1 }

    override fun getQueryTimeout(): Int {
        TODO("not implemented")
    }

    override fun isPoolable(): Boolean {
        TODO("not implemented")
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?, length: Int) {
        TODO("not implemented")
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?, length: Long) {
        TODO("not implemented")
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?) {
        TODO("not implemented")
    }

    override fun setNCharacterStream(parameterIndex: Int, value: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun setNCharacterStream(parameterIndex: Int, value: Reader?) {
        TODO("not implemented")
    }

    override fun setInt(parameterIndex: Int, x: Int) {
        TODO("not implemented")
    }

    override fun getGeneratedKeys(): ResultSet {
        TODO("not implemented")
    }

    override fun getResultSet(): ResultSet {
        TODO("not implemented")
    }

    override fun setDouble(parameterIndex: Int, x: Double) {
        TODO("not implemented")
    }

    override fun closeOnCompletion() {
        TODO("not implemented")
    }

    override fun getParameterMetaData(): ParameterMetaData {
        TODO("not implemented")
    }

    override fun executeUpdate(): Int = params.size

    override fun executeUpdate(sql: String?): Int {
        TODO("not implemented")
    }

    override fun executeUpdate(sql: String?, autoGeneratedKeys: Int): Int {
        TODO("not implemented")
    }

    override fun executeUpdate(sql: String?, columnIndexes: IntArray?): Int {
        TODO("not implemented")
    }

    override fun executeUpdate(sql: String?, columnNames: Array<out String>?): Int {
        TODO("not implemented")
    }

    override fun <T : Any?> unwrap(iface: Class<T>?): T {

        TODO("not implemented")
    }

    override fun getMaxRows(): Int {
        TODO("not implemented")
    }

    override fun setSQLXML(parameterIndex: Int, xmlObject: SQLXML?) {
        TODO("not implemented")
    }

    override fun setBigDecimal(parameterIndex: Int, x: BigDecimal?) {
        TODO("not implemented")
    }

    override fun setString(parameterIndex: Int, x: String?) {
        while (params.size <= curParamRow) {
            params.add(mutableListOf())
        }
        val l = params[curParamRow]
        while (l.size < parameterIndex) {
            l.add(null)
        }
        l[parameterIndex - 1] = x
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?, length: Int) {
        TODO("not implemented")
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?, length: Long) {
        TODO("not implemented")
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?) {
        TODO("not implemented")
    }

    override fun setNClob(parameterIndex: Int, value: NClob?) {
        TODO("not implemented")
    }

    override fun setNClob(parameterIndex: Int, reader: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun setNClob(parameterIndex: Int, reader: Reader?) {
        TODO("not implemented")
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean = false

    override fun setNull(parameterIndex: Int, sqlType: Int) {
        TODO("not implemented")
    }

    override fun setNull(parameterIndex: Int, sqlType: Int, typeName: String?) {
        TODO("not implemented")
    }

    override fun setMaxRows(max: Int) {
        TODO("not implemented")
    }

    override fun setTimestamp(parameterIndex: Int, x: Timestamp?) {
        TODO("not implemented")
    }

    override fun setTimestamp(parameterIndex: Int, x: Timestamp?, cal: Calendar?) {
        TODO("not implemented")
    }

    override fun setEscapeProcessing(enable: Boolean) {
        TODO("not implemented")
    }

    override fun setCursorName(name: String?) {
        TODO("not implemented")
    }

    override fun execute(): Boolean {
        return true
    }

    override fun execute(sql: String?): Boolean {
        TODO("not implemented")
    }

    override fun execute(sql: String?, autoGeneratedKeys: Int): Boolean {
        TODO("not implemented")
    }

    override fun execute(sql: String?, columnIndexes: IntArray?): Boolean {
        TODO("not implemented")
    }

    override fun execute(sql: String?, columnNames: Array<out String>?): Boolean {
        TODO("not implemented")
    }

    override fun setPoolable(poolable: Boolean) {
        TODO("not implemented")
    }

    override fun setShort(parameterIndex: Int, x: Short) {
        TODO("not implemented")
    }

    override fun setFetchSize(rows: Int) {
        TODO("not implemented")
    }

    override fun clearWarnings() {
        TODO("not implemented")
    }

    override fun getMetaData(): ResultSetMetaData {
        TODO("not implemented")
    }

    override fun addBatch(): Unit {
        curParamRow++
    }

    override fun addBatch(sql: String?) {
        TODO("not implemented")
    }

    override fun setQueryTimeout(seconds: Int) {
        TODO("not implemented")
    }

    override fun getFetchDirection(): Int {
        TODO("not implemented")
    }

    override fun getResultSetHoldability(): Int {
        TODO("not implemented")
    }

    override fun setBoolean(parameterIndex: Int, x: Boolean) {
        TODO("not implemented")
    }

    override fun getMoreResults(): Boolean {
        TODO("not implemented")
    }

    override fun getMoreResults(current: Int): Boolean {
        TODO("not implemented")
    }

    override fun setByte(parameterIndex: Int, x: Byte) {
        TODO("not implemented")
    }


    class Close(private val sql: String) : Action {
        override fun toString(): String {
            return "PreparedStatement($sql).close()"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Close) return false
            return sql == other.sql
        }

        override fun hashCode(): Int {
            return sql.hashCode()
        }
    }

}