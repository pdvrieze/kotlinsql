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

import java.io.InputStream
import java.io.Reader
import java.math.BigDecimal
import java.net.URL
import java.sql.*
import java.sql.Array
import java.sql.Date
import java.util.*

abstract class AbstractRecordingCallableStatement(
    connection: RecordingConnection,
    delegate: CallableStatement,
    sql: String
) : AbstractRecordingPreparedStatement<CallableStatement>(connection, delegate, sql), CallableStatement {

    // Object Initialization end
    override fun registerOutParameter(parameterIndex: Int, sqlType: Int) = record(parameterIndex, sqlType) {
        delegate.registerOutParameter(parameterIndex, sqlType)
    }

    override fun registerOutParameter(parameterIndex: Int, sqlType: Int, scale: Int) = record(parameterIndex, sqlType, scale) {
        delegate.registerOutParameter(parameterIndex, sqlType, scale)
    }

    override fun wasNull(): Boolean = record() {
        delegate.wasNull()
    }

    override fun getString(parameterIndex: Int): String? = record(parameterIndex) {
        delegate.getString(parameterIndex)
    }

    override fun getBoolean(parameterIndex: Int): Boolean = record(parameterIndex) {
        delegate.getBoolean(parameterIndex)
    }

    override fun getByte(parameterIndex: Int): Byte = record(parameterIndex) {
        delegate.getByte(parameterIndex)
    }

    override fun getShort(parameterIndex: Int): Short = record(parameterIndex) {
        delegate.getShort(parameterIndex)
    }

    override fun getInt(parameterIndex: Int): Int = record(parameterIndex) {
        delegate.getInt(parameterIndex)
    }

    override fun getLong(parameterIndex: Int): Long = record(parameterIndex) {
        delegate.getLong(parameterIndex)
    }

    override fun getFloat(parameterIndex: Int): Float = record(parameterIndex) {
        delegate.getFloat(parameterIndex)
    }

    override fun getDouble(parameterIndex: Int): Double = record(parameterIndex) {
        delegate.getDouble(parameterIndex)
    }

    @Deprecated("")
    override fun getBigDecimal(parameterIndex: Int, scale: Int): BigDecimal? = record(parameterIndex, scale) {
        delegate.getBigDecimal(parameterIndex, scale)
    }

    override fun getBytes(parameterIndex: Int): ByteArray? = record(parameterIndex) {
        delegate.getBytes(parameterIndex)
    }

    override fun getDate(parameterIndex: Int): Date? = record(parameterIndex) {
        delegate.getDate(parameterIndex)
    }

    override fun getTime(parameterIndex: Int): Time? = record(parameterIndex) {
        delegate.getTime(parameterIndex)
    }

    override fun getTimestamp(parameterIndex: Int): Timestamp? = record(parameterIndex) {
        delegate.getTimestamp(parameterIndex)
    }

    override fun getObject(parameterIndex: Int): Any? = record(parameterIndex) {
        delegate.getObject(parameterIndex)
    }

    override fun getBigDecimal(parameterIndex: Int): BigDecimal? = record(parameterIndex) {
        delegate.getBigDecimal(parameterIndex)
    }

    override fun getObject(parameterIndex: Int, map: Map<String?, Class<*>?>?): Any? = record(parameterIndex, map) {
        delegate.getObject(parameterIndex, map)
    }

    override fun getRef(parameterIndex: Int): Ref? = record(parameterIndex) {
        delegate.getRef(parameterIndex)
    }

    override fun getBlob(parameterIndex: Int): Blob? = record(parameterIndex) {
        delegate.getBlob(parameterIndex)
    }

    override fun getClob(parameterIndex: Int): Clob? = record(parameterIndex) {
        delegate.getClob(parameterIndex)
    }

    override fun getArray(parameterIndex: Int): Array? = record(parameterIndex) {
        delegate.getArray(parameterIndex)
    }

    override fun getDate(parameterIndex: Int, cal: Calendar?): Date? = record(parameterIndex, cal) {
        delegate.getDate(parameterIndex, cal)
    }

    override fun getTime(parameterIndex: Int, cal: Calendar?): Time? = record(parameterIndex, cal) {
        delegate.getTime(parameterIndex, cal)
    }

    override fun getTimestamp(parameterIndex: Int, cal: Calendar?): Timestamp? = record(parameterIndex, cal) {
        delegate.getTimestamp(parameterIndex, cal)
    }

    override fun registerOutParameter(parameterIndex: Int, sqlType: Int, typeName: String?) = record(parameterIndex, sqlType, typeName) {
        delegate.registerOutParameter(parameterIndex, sqlType, typeName)
    }

    override fun registerOutParameter(parameterName: String?, sqlType: Int) = record(parameterName, sqlType) {
        delegate.registerOutParameter(parameterName, sqlType)
    }

    override fun registerOutParameter(parameterName: String?, sqlType: Int, scale: Int) = record(parameterName, sqlType, scale) {
        delegate.registerOutParameter(parameterName, sqlType, scale)
    }

    override fun registerOutParameter(parameterName: String?, sqlType: Int, typeName: String?) = record(parameterName, sqlType, typeName) {
        delegate.registerOutParameter(parameterName, sqlType, typeName)
    }

    override fun getURL(parameterIndex: Int): URL? = record(parameterIndex) {
        delegate.getURL(parameterIndex)
    }

    override fun setURL(parameterName: String?, `val`: URL?) = record(parameterName, `val`) {
        delegate.setURL(parameterName, `val`)
    }

    override fun setNull(parameterName: String?, sqlType: Int) = record(parameterName, sqlType) {
        delegate.setNull(parameterName, sqlType)
    }

    override fun setBoolean(parameterName: String?, x: Boolean) = record(parameterName, x) {
        delegate.setBoolean(parameterName, x)
    }

    override fun setByte(parameterName: String?, x: Byte) = record(parameterName, x) {
        delegate.setByte(parameterName, x)
    }

    override fun setShort(parameterName: String?, x: Short) = record(parameterName, x) {
        delegate.setShort(parameterName, x)
    }

    override fun setInt(parameterName: String?, x: Int) = record(parameterName, x) {
        delegate.setInt(parameterName, x)
    }

    override fun setLong(parameterName: String?, x: Long) = record(parameterName, x) {
        delegate.setLong(parameterName, x)
    }

    override fun setFloat(parameterName: String?, x: Float) = record(parameterName, x) {
        delegate.setFloat(parameterName, x)
    }

    override fun setDouble(parameterName: String?, x: Double) = record(parameterName, x) {
        delegate.setDouble(parameterName, x)
    }

    override fun setBigDecimal(parameterName: String?, x: BigDecimal?) = record(parameterName, x) {
        delegate.setBigDecimal(parameterName, x)
    }

    override fun setString(parameterName: String?, x: String?) = record(parameterName, x) {
        delegate.setString(parameterName, x)
    }

    override fun setBytes(parameterName: String?, x: ByteArray?) = record(parameterName, x) {
        delegate.setBytes(parameterName, x)
    }

    override fun setDate(parameterName: String?, x: Date?) = record(parameterName, x) {
        delegate.setDate(parameterName, x)
    }

    override fun setTime(parameterName: String?, x: Time?) = record(parameterName, x) {
        delegate.setTime(parameterName, x)
    }

    override fun setTimestamp(parameterName: String?, x: Timestamp?) = record(parameterName, x) {
        delegate.setTimestamp(parameterName, x)
    }

    override fun setAsciiStream(parameterName: String?, x: InputStream?, length: Int) = record(parameterName, x, length) {
        delegate.setAsciiStream(parameterName, x, length)
    }

    override fun setBinaryStream(parameterName: String?, x: InputStream?, length: Int) = record(parameterName, x, length) {
        delegate.setBinaryStream(parameterName, x, length)
    }

    override fun setObject(parameterName: String?, x: Any?, targetSqlType: Int, scale: Int) {
        record(parameterName, x, targetSqlType, scale)
        delegate.setObject(parameterName, x, targetSqlType, scale)
    }

    override fun setObject(parameterName: String?, x: Any?, targetSqlType: Int) = record(parameterName, x, targetSqlType) {
        delegate.setObject(parameterName, x, targetSqlType)
    }

    override fun setObject(parameterName: String?, x: Any?) = record(parameterName, x) {
        delegate.setObject(parameterName, x)
    }

    override fun setCharacterStream(parameterName: String?, reader: Reader?, length: Int) = record(parameterName, reader, length) {
        delegate.setCharacterStream(parameterName, reader, length)
    }

    override fun setDate(parameterName: String?, x: Date?, cal: Calendar?) = record(parameterName, x, cal) {
        delegate.setDate(parameterName, x, cal)
    }

    override fun setTime(parameterName: String?, x: Time?, cal: Calendar?) = record(parameterName, x, cal) {
        delegate.setTime(parameterName, x, cal)
    }

    override fun setTimestamp(parameterName: String?, x: Timestamp?, cal: Calendar?) = record(parameterName, x, cal) {
        delegate.setTimestamp(parameterName, x, cal)
    }

    override fun setNull(parameterName: String?, sqlType: Int, typeName: String?) = record(parameterName, sqlType, typeName) {
        delegate.setNull(parameterName, sqlType, typeName)
    }

    override fun getString(parameterName: String?): String? = record(parameterName) {
        delegate.getString(parameterName)
    }

    override fun getBoolean(parameterName: String?): Boolean = record(parameterName) {
        delegate.getBoolean(parameterName)
    }

    override fun getByte(parameterName: String?): Byte = record(parameterName) {
        delegate.getByte(parameterName)
    }

    override fun getShort(parameterName: String?): Short = record(parameterName) {
        delegate.getShort(parameterName)
    }

    override fun getInt(parameterName: String?): Int = record(parameterName) {
        delegate.getInt(parameterName)
    }

    override fun getLong(parameterName: String?): Long = record(parameterName) {
        delegate.getLong(parameterName)
    }

    override fun getFloat(parameterName: String?): Float = record(parameterName) {
        delegate.getFloat(parameterName)
    }

    override fun getDouble(parameterName: String?): Double = record(parameterName) {
        delegate.getDouble(parameterName)
    }

    override fun getBytes(parameterName: String?): ByteArray? = record(parameterName) {
        delegate.getBytes(parameterName)
    }

    override fun getDate(parameterName: String?): Date? = record(parameterName) {
        delegate.getDate(parameterName)
    }

    override fun getTime(parameterName: String?): Time? = record(parameterName) {
        delegate.getTime(parameterName)
    }

    override fun getTimestamp(parameterName: String?): Timestamp? = record(parameterName) {
        delegate.getTimestamp(parameterName)
    }

    override fun getObject(parameterName: String?): Any? = record(parameterName) {
        delegate.getObject(parameterName)
    }

    override fun getBigDecimal(parameterName: String?): BigDecimal? = record(parameterName) {
        delegate.getBigDecimal(parameterName)
    }

    override fun getObject(parameterName: String?, map: Map<String?, Class<*>?>?): Any? = record(parameterName, map) {
        delegate.getObject(parameterName, map)
    }

    override fun getRef(parameterName: String?): Ref? = record(parameterName) {
        delegate.getRef(parameterName)
    }

    override fun getBlob(parameterName: String?): Blob? = record(parameterName) {
        delegate.getBlob(parameterName)
    }

    override fun getClob(parameterName: String?): Clob? = record(parameterName) {
        delegate.getClob(parameterName)
    }

    override fun getArray(parameterName: String?): Array? = record(parameterName) {
        delegate.getArray(parameterName)
    }

    override fun getDate(parameterName: String?, cal: Calendar?): Date? = record(parameterName, cal) {
        delegate.getDate(parameterName, cal)
    }

    override fun getTime(parameterName: String?, cal: Calendar?): Time? = record(parameterName, cal) {
        delegate.getTime(parameterName, cal)
    }

    override fun getTimestamp(parameterName: String?, cal: Calendar?): Timestamp? = record(parameterName, cal) {
        delegate.getTimestamp(parameterName, cal)
    }

    override fun getURL(parameterName: String?): URL? = record(parameterName) {
        delegate.getURL(parameterName)
    }

    override fun getRowId(parameterIndex: Int): RowId? = record(parameterIndex) {
        delegate.getRowId(parameterIndex)
    }

    override fun getRowId(parameterName: String?): RowId? = record(parameterName) {
        delegate.getRowId(parameterName)
    }

    override fun setRowId(parameterName: String?, x: RowId?) = record(parameterName, x) {
        delegate.setRowId(parameterName, x)
    }

    override fun setNString(parameterName: String?, value: String?) = record(parameterName, value) {
        delegate.setNString(parameterName, value)
    }

    override fun setNCharacterStream(parameterName: String?, value: Reader?, length: Long) = record(parameterName, value, length) {
        delegate.setNCharacterStream(parameterName, value, length)
    }

    override fun setNClob(parameterName: String?, value: NClob?) = record(parameterName, value) {
        delegate.setNClob(parameterName, value)
    }

    override fun setClob(parameterName: String?, reader: Reader?, length: Long) = record(parameterName, reader, length) {
        delegate.setClob(parameterName, reader, length)
    }

    override fun setBlob(parameterName: String?, inputStream: InputStream?, length: Long) = record(parameterName, inputStream, length) {
        delegate.setBlob(parameterName, inputStream, length)
    }

    override fun setNClob(parameterName: String?, reader: Reader?, length: Long) = record(parameterName, reader, length) {
        delegate.setNClob(parameterName, reader, length)
    }

    override fun getNClob(parameterIndex: Int): NClob? = record(parameterIndex) {
        delegate.getNClob(parameterIndex)
    }

    override fun getNClob(parameterName: String?): NClob? = record(parameterName) {
        delegate.getNClob(parameterName)
    }

    override fun setSQLXML(parameterName: String?, xmlObject: SQLXML?) = record(parameterName, xmlObject) {
        delegate.setSQLXML(parameterName, xmlObject)
    }

    override fun getSQLXML(parameterIndex: Int): SQLXML? = record(parameterIndex) {
        delegate.getSQLXML(parameterIndex)
    }

    override fun getSQLXML(parameterName: String?): SQLXML? = record(parameterName) {
        delegate.getSQLXML(parameterName)
    }

    override fun getNString(parameterIndex: Int): String? = record(parameterIndex) {
        delegate.getNString(parameterIndex)
    }

    override fun getNString(parameterName: String?): String? = record(parameterName) {
        delegate.getNString(parameterName)
    }

    override fun getNCharacterStream(parameterIndex: Int): Reader? = record(parameterIndex) {
        delegate.getNCharacterStream(parameterIndex)
    }

    override fun getNCharacterStream(parameterName: String?): Reader? = record(parameterName) {
        delegate.getNCharacterStream(parameterName)
    }

    override fun getCharacterStream(parameterIndex: Int): Reader? = record(parameterIndex) {
        delegate.getCharacterStream(parameterIndex)
    }

    override fun getCharacterStream(parameterName: String?): Reader? = record(parameterName) {
        delegate.getCharacterStream(parameterName)
    }

    override fun setBlob(parameterName: String?, x: Blob?) = record(parameterName, x) {
        delegate.setBlob(parameterName, x)
    }

    override fun setClob(parameterName: String?, x: Clob?) = record(parameterName, x) {
        delegate.setClob(parameterName, x)
    }

    override fun setAsciiStream(parameterName: String?, x: InputStream?, length: Long) = record(parameterName, x, length) {
        delegate.setAsciiStream(parameterName, x, length)
    }

    override fun setBinaryStream(parameterName: String?, x: InputStream?, length: Long) = record(parameterName, x, length) {
        delegate.setBinaryStream(parameterName, x, length)
    }

    override fun setCharacterStream(parameterName: String?, reader: Reader?, length: Long) = record(parameterName, reader, length) {
        delegate.setCharacterStream(parameterName, reader, length)
    }

    override fun setAsciiStream(parameterName: String?, x: InputStream?) = record(parameterName, x) {
        delegate.setAsciiStream(parameterName, x)
    }

    override fun setBinaryStream(parameterName: String?, x: InputStream?) = record(parameterName, x) {
        delegate.setBinaryStream(parameterName, x)
    }

    override fun setCharacterStream(parameterName: String?, reader: Reader?) = record(parameterName, reader) {
        delegate.setCharacterStream(parameterName, reader)
    }

    override fun setNCharacterStream(parameterName: String?, value: Reader?) = record(parameterName, value) {
        delegate.setNCharacterStream(parameterName, value)
    }

    override fun setClob(parameterName: String?, reader: Reader?) = record(parameterName, reader) {
        delegate.setClob(parameterName, reader)
    }

    override fun setBlob(parameterName: String?, inputStream: InputStream?) = record(parameterName, inputStream) {
        delegate.setBlob(parameterName, inputStream)
    }

    override fun setNClob(parameterName: String?, reader: Reader?) = record(parameterName, reader) {
        delegate.setNClob(parameterName, reader)
    }

    override fun <T> getObject(parameterIndex: Int, type: Class<T>?): T = record(parameterIndex, type) {
        delegate.getObject(parameterIndex, type)
    }

    override fun <T> getObject(parameterName: String?, type: Class<T>?): T = record(parameterName, type) {
        delegate.getObject(parameterName, type)
    }

    override fun setObject(parameterName: String?, x: Any?, targetSqlType: SQLType?, scaleOrLength: Int) {
        record(parameterName, x, targetSqlType, scaleOrLength)
        delegate.setObject(parameterName, x, targetSqlType, scaleOrLength)
    }

    override fun setObject(parameterName: String?, x: Any?, targetSqlType: SQLType?) = record(parameterName, x, targetSqlType) {
        delegate.setObject(parameterName, x, targetSqlType)
    }

    override fun registerOutParameter(parameterIndex: Int, sqlType: SQLType?) = record(parameterIndex, sqlType) {
        delegate.registerOutParameter(parameterIndex, sqlType)
    }

    override fun registerOutParameter(parameterIndex: Int, sqlType: SQLType?, scale: Int) = record(parameterIndex, sqlType, scale) {
        delegate.registerOutParameter(parameterIndex, sqlType, scale)
    }

    override fun registerOutParameter(parameterIndex: Int, sqlType: SQLType?, typeName: String?) = record(parameterIndex, sqlType, typeName) {
        delegate.registerOutParameter(parameterIndex, sqlType, typeName)
    }

    override fun registerOutParameter(parameterName: String?, sqlType: SQLType?) = record(parameterName, sqlType) {
        delegate.registerOutParameter(parameterName, sqlType)
    }

    override fun registerOutParameter(parameterName: String?, sqlType: SQLType?, scale: Int) = record(parameterName, sqlType, scale) {
        delegate.registerOutParameter(parameterName, sqlType, scale)
    }

    override fun registerOutParameter(parameterName: String?, sqlType: SQLType?, typeName: String?) = record(parameterName, sqlType, typeName) {
        delegate.registerOutParameter(parameterName, sqlType, typeName)
    }
}