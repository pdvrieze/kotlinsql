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

import io.github.pdvrieze.jdbc.recorder.actions.StringAction
import java.io.InputStream
import java.io.Reader
import java.lang.Exception
import java.math.BigDecimal
import java.net.URL
import java.sql.*
import java.sql.Date
import java.util.*

abstract class AbstractRecordingPreparedStatement<D: PreparedStatement>(
    connection: RecordingConnection,
    delegate: D,
    sql: String
) : AbstractRecordingStatement<D>(connection, delegate, sql), PreparedStatement {

    override val sql: String get() = super.sql!!

    override fun record(vararg args: Any?) {
        val calledFunction = Exception().stackTrace[1].methodName
        val ac = StringAction("\"$sql\" -- $calledFunction(${args.joinToString()})")
        recordAction(ac)
    }

    override fun addBatch(): Unit = record {
        delegate.addBatch()
    }

    override fun clearParameters() = record()

    override fun execute(): Boolean {
        return record {
            delegate.execute()
        }
    }

    override fun executeQuery(): ResultSet {
        val rs: RecordingConnection.RecordingResultSet =
            connection.RecordingResultSet(delegate.executeQuery())
        return rs.also { recordAction(it) }
    }

    override fun executeUpdate(): Int = record {
        delegate.executeUpdate()
    }

    override fun getMetaData(): ResultSetMetaData = record {
        delegate.metaData
    }

    override fun getParameterMetaData(): ParameterMetaData = record {
        delegate.parameterMetaData
    }

    override fun setRef(parameterIndex: Int, x: Ref?) {
        record(parameterIndex, x)
        delegate.setRef(parameterIndex, x)
    }

    override fun setArray(parameterIndex: Int, x: java.sql.Array?) {
        record(parameterIndex, x)
        delegate.setArray(parameterIndex, x)
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?, length: Int) {
        record(parameterIndex, x, length)
        delegate.setAsciiStream(parameterIndex, x, length)
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?, length: Long) {
        record(parameterIndex, x, length)
        delegate.setAsciiStream(parameterIndex, x, length)
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?) {
        record(parameterIndex, x)
        delegate.setAsciiStream(parameterIndex, x)
    }

    override fun setBigDecimal(parameterIndex: Int, x: BigDecimal?) {
        record(parameterIndex, x)
        delegate.setBigDecimal(parameterIndex, x)
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?, length: Int) {
        record(parameterIndex, x, length)
        delegate.setBinaryStream(parameterIndex, x, length)
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?, length: Long) {
        record(parameterIndex, x, length)
        delegate.setBinaryStream(parameterIndex, x, length)
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?) {
        record(parameterIndex, x)
        delegate.setBinaryStream(parameterIndex, x)
    }

    override fun setBlob(parameterIndex: Int, x: Blob?) = record(parameterIndex, x) {
        delegate.setBlob(parameterIndex, x)
    }

    override fun setBlob(parameterIndex: Int, inputStream: InputStream?, length: Long) =
        record(parameterIndex, inputStream, length) {
            delegate.setBlob(parameterIndex, inputStream, length)
        }

    override fun setBlob(parameterIndex: Int, inputStream: InputStream?) = record(parameterIndex, inputStream) {
        delegate.setBlob(parameterIndex, inputStream)
    }

    override fun setBoolean(parameterIndex: Int, x: Boolean) {
        record(parameterIndex, x)
        delegate.setBoolean(parameterIndex, x)
    }

    override fun setByte(parameterIndex: Int, x: Byte) {
        record(parameterIndex, x)
        delegate.setByte(parameterIndex, x)
    }

    override fun setBytes(parameterIndex: Int, x: ByteArray?) {
        record(parameterIndex, x)
        delegate.setBytes(parameterIndex, x)
    }

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?, length: Int) =
        record(parameterIndex, reader, length) {
            delegate.setCharacterStream(parameterIndex, reader, length)
        }

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?, length: Long) =
        record(parameterIndex, reader, length) {
            delegate.setCharacterStream(parameterIndex, reader, length)
        }

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?) = record(parameterIndex, reader) {
        delegate.setCharacterStream(parameterIndex, reader)
    }


    override fun setDate(parameterIndex: Int, x: Date?) = record(parameterIndex, x) {
        delegate.setDate(parameterIndex, x)
    }

    override fun setDate(parameterIndex: Int, x: Date?, cal: Calendar?) = record(parameterIndex, x, cal) {
        delegate.setDate(parameterIndex, x, cal)
    }

    override fun setClob(parameterIndex: Int, x: Clob?) = record(parameterIndex, x) {
        delegate.setClob(parameterIndex, x)
    }

    override fun setClob(parameterIndex: Int, reader: Reader?, length: Long) = record(parameterIndex, reader, length) {
        delegate.setClob(parameterIndex, reader, length)
    }

    override fun setClob(parameterIndex: Int, reader: Reader?) = record(parameterIndex, reader) {
        delegate.setClob(parameterIndex, reader)
    }

    override fun setDouble(parameterIndex: Int, x: Double) {
        record(parameterIndex, x)
        delegate.setDouble(parameterIndex, x)
    }

    override fun setFloat(parameterIndex: Int, x: Float) {
        record(parameterIndex, x)
        delegate.setFloat(parameterIndex, x)
    }

    override fun setInt(parameterIndex: Int, x: Int) {
        record(parameterIndex, x)
        delegate.setInt(parameterIndex, x)
    }

    override fun setLong(parameterIndex: Int, x: Long) {
        record(parameterIndex, x)
        delegate.setLong(parameterIndex, x)
    }

    override fun setNCharacterStream(parameterIndex: Int, value: Reader?, length: Long) {
        record(parameterIndex, value, length)
        delegate.setNCharacterStream(parameterIndex, value, length)
    }

    override fun setNCharacterStream(parameterIndex: Int, value: Reader?) {
        record(parameterIndex, value)
        delegate.setNCharacterStream(parameterIndex, value)
    }

    override fun setNClob(parameterIndex: Int, value: NClob?) {
        record(parameterIndex, value)
        delegate.setNClob(parameterIndex, value)
    }

    override fun setNClob(parameterIndex: Int, reader: Reader?, length: Long) {
        record(parameterIndex, reader, length)
        delegate.setNClob(parameterIndex, reader, length)
    }

    override fun setNClob(parameterIndex: Int, reader: Reader?) {
        record(parameterIndex, reader)
        delegate.setNClob(parameterIndex, reader)
    }

    override fun setNString(parameterIndex: Int, value: String?) {
        record(parameterIndex, value)
        delegate.setNString(parameterIndex, value)
    }

    override fun setNull(parameterIndex: Int, sqlType: Int) {
        record(parameterIndex, sqlType)
        delegate.setNull(parameterIndex, sqlType)
    }

    override fun setNull(parameterIndex: Int, sqlType: Int, typeName: String?) {
        record(parameterIndex, sqlType, typeName)
        delegate.setNull(parameterIndex, sqlType, typeName)
    }

    override fun setObject(parameterIndex: Int, x: Any?, targetSqlType: Int) {
        record(parameterIndex, x, targetSqlType)
        delegate.setObject(parameterIndex, x, targetSqlType)
    }

    override fun setObject(parameterIndex: Int, x: Any?) {
        record(parameterIndex, x)
        delegate.setObject(parameterIndex, x)
    }

    override fun setObject(parameterIndex: Int, x: Any?, targetSqlType: Int, scaleOrLength: Int) {
        record(parameterIndex, x, targetSqlType, scaleOrLength)
        delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength)
    }

    override fun setRowId(parameterIndex: Int, x: RowId?) {
        record(parameterIndex, x)
        delegate.setRowId(parameterIndex, x)
    }

    override fun setShort(parameterIndex: Int, x: Short) {
        record(parameterIndex, x)
        delegate.setShort(parameterIndex, x)
    }

    override fun setSQLXML(parameterIndex: Int, xmlObject: SQLXML?) {
        record(parameterIndex, xmlObject)
        delegate.setSQLXML(parameterIndex, xmlObject)
    }

    override fun setString(parameterIndex: Int, x: String?) = record(parameterIndex, x) {
        delegate.setString(parameterIndex, x)
    }

    override fun setTime(parameterIndex: Int, x: Time?) {
        record(parameterIndex, x)
        delegate.setTime(parameterIndex, x)
    }

    override fun setTime(parameterIndex: Int, x: Time?, cal: Calendar?) {
        record(parameterIndex, x, cal)
        delegate.setTime(parameterIndex, x, cal)
    }

    override fun setTimestamp(parameterIndex: Int, x: Timestamp?) {
        record(parameterIndex, x)
        setTimestamp(parameterIndex, x)
    }

    override fun setTimestamp(parameterIndex: Int, x: Timestamp?, cal: Calendar?) {
        record(parameterIndex, x, cal)
        setTimestamp(parameterIndex, x, cal)
    }

    override fun setURL(parameterIndex: Int, x: URL?) {
        record(parameterIndex, x)
        delegate.setURL(parameterIndex, x)
    }

    override fun setUnicodeStream(parameterIndex: Int, x: InputStream?, length: Int) {
        record(parameterIndex, x, length)
        @Suppress("DEPRECATION")
        delegate.setUnicodeStream(parameterIndex, x, length)
    }

    override fun getMoreResults(): Boolean = record {
        delegate.moreResults
    }

    override fun getMoreResults(current: Int): Boolean = record {
        delegate.getMoreResults(current)
    }

}