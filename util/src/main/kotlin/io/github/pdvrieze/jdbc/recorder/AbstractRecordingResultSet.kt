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

import io.github.pdvrieze.jdbc.recorder.actions.ResultSetClose
import io.github.pdvrieze.jdbc.recorder.actions.StringAction
import java.io.InputStream
import java.io.Reader
import java.lang.Exception
import java.math.BigDecimal
import java.net.URL
import java.sql.*
import java.sql.Array as SqlArray
import java.sql.Date
import java.util.*

abstract class AbstractRecordingResultSet(
    delegate: ResultSet,
    val query: String
) : WrappingActionRecorder<ResultSet>(delegate), ResultSet {

    protected val columnNames = arrayOfNulls<String>(delegate.metaData.columnCount)

    protected inline fun <R> recordGet(columnNo: Int, crossinline action: () -> R): R {
        return action().also {
            val p = columnNames[columnNo-1]?.let { n -> "$columnNo ~> $n"} ?: columnNo.toString()
            recordRes3(it, p)
        }
    }

    protected inline fun <R> recordGet(columnNo: Int, arg2: Any?, crossinline action: () -> R): R {
        return action().also {
            val p = columnNames[columnNo-1]?.let { n -> "$columnNo ~> $n"} ?: columnNo.toString()
            recordRes3(it, p, arg2)
        }
    }

    protected fun <R> recordRes3(result: R, columnIndex: String, arg2: Any? = Unit): R {
        val calledFunction = Exception().stackTrace[2].methodName
        val arg2s = if (arg2==Unit) "" else ", ${arg2.stringify()}"
        val ac = when {
            result == Unit -> StringAction("$this.$calledFunction($columnIndex$arg2s)")
            else           -> StringAction("$this.$calledFunction($columnIndex$arg2s) -> ${result.stringify()}")
        }
        recordAction(ac)
        return result
    }

    protected fun recordUpdate(columnNo: Int, vararg args: Any?) {
        val p = columnNames[columnNo-1]?.let { n -> "$columnNo -> $n"} ?: columnNo.toString()
        val calledFunction = Exception().stackTrace[1].methodName
        val ac = StringAction("$this.$calledFunction($p, ${args.joinToString()})")
        recordAction(ac)
    }

    override fun absolute(row: Int): Boolean = record(row) {
        delegate.absolute(row)
    }

    override fun afterLast() {
        record()
        delegate.afterLast()
    }

    override fun beforeFirst() {
        record()
        delegate.beforeFirst()
    }

    override fun cancelRowUpdates() {
        record()
        delegate.cancelRowUpdates()
    }

    override fun clearWarnings() {
        record()
        delegate.clearWarnings()
    }

    override fun close() {
        recordAction(ResultSetClose)
    }

    override fun deleteRow() {
        record()
        delegate.deleteRow()
    }

    override fun findColumn(columnLabel: String): Int = record(columnLabel) {
        delegate.findColumn(columnLabel).also { columnNames[it-1] = columnLabel }
    }

    override fun first(): Boolean = record {
        delegate.first()
    }

    override fun getArray(columnIndex: Int): SqlArray = recordGet(columnIndex) {
        delegate.getArray(columnIndex)
    }

    override fun getArray(columnLabel: String?): SqlArray = record(columnLabel) {
        delegate.getArray(columnLabel)
    }

    override fun getAsciiStream(columnIndex: Int): InputStream = recordGet(columnIndex) {
        delegate.getAsciiStream(columnIndex)
    }

    override fun getAsciiStream(columnLabel: String?): InputStream = record(columnLabel) {
        delegate.getAsciiStream(columnLabel)
    }

    override fun getBigDecimal(columnIndex: Int, scale: Int): BigDecimal = recordGet(columnIndex, scale) {
        @Suppress("DEPRECATION")
        delegate.getBigDecimal(columnIndex, scale)
    }

    override fun getBigDecimal(columnLabel: String?, scale: Int): BigDecimal = record(columnLabel, scale) {
        @Suppress("DEPRECATION")
        delegate.getBigDecimal(columnLabel, scale)
    }

    override fun getBigDecimal(columnIndex: Int): BigDecimal = recordGet(columnIndex) {
        delegate.getBigDecimal(columnIndex)
    }

    override fun getBigDecimal(columnLabel: String?): BigDecimal = record(columnLabel) {
        delegate.getBigDecimal(columnLabel)
    }

    override fun getBinaryStream(columnIndex: Int): InputStream = recordGet(columnIndex) {
        delegate.getBinaryStream(columnIndex)
    }

    override fun getBinaryStream(columnLabel: String?): InputStream = record(columnLabel) {
        delegate.getBinaryStream(columnLabel)
    }

    override fun getBlob(columnIndex: Int): Blob = recordGet(columnIndex) {
        delegate.getBlob(columnIndex)
    }

    override fun getBlob(columnLabel: String?): Blob = record(columnLabel) {
        delegate.getBlob(columnLabel)
    }

    override fun getBoolean(columnIndex: Int): Boolean = recordGet(columnIndex) {
        delegate.getBoolean(columnIndex)
    }

    override fun getBoolean(columnLabel: String?): Boolean = record(columnLabel) {
        delegate.getBoolean(columnLabel)
    }

    override fun getByte(columnIndex: Int): Byte = recordGet(columnIndex) {
        delegate.getByte(columnIndex)
    }

    override fun getByte(columnLabel: String?): Byte = record(columnLabel) {
        delegate.getByte(columnLabel)
    }

    override fun getBytes(columnIndex: Int): ByteArray = recordGet(columnIndex) {
        delegate.getBytes(columnIndex)
    }

    override fun getBytes(columnLabel: String?): ByteArray = record(columnLabel) {
        delegate.getBytes(columnLabel)
    }

    override fun getCharacterStream(columnIndex: Int): Reader = recordGet(columnIndex) {
        delegate.getCharacterStream(columnIndex)
    }

    override fun getCharacterStream(columnLabel: String?): Reader = record(columnLabel) {
        delegate.getCharacterStream(columnLabel)
    }

    override fun getClob(columnIndex: Int): Clob = recordGet(columnIndex) {
        delegate.getClob(columnIndex)
    }

    override fun getClob(columnLabel: String?): Clob = record(columnLabel) {
        delegate.getClob(columnLabel)
    }

    override fun getConcurrency(): Int = record {
        delegate.concurrency
    }

    override fun getCursorName(): String = record {
        delegate.cursorName
    }

    override fun getDate(columnIndex: Int): Date = recordGet(columnIndex) {
        delegate.getDate(columnIndex)
    }

    override fun getDate(columnLabel: String?): Date = record(columnLabel) {
        delegate.getDate(columnLabel)
    }

    override fun getDate(columnIndex: Int, cal: Calendar?): Date = recordGet(columnIndex, cal) {
        delegate.getDate(columnIndex, cal)
    }

    override fun getDate(columnLabel: String?, cal: Calendar?): Date = record(columnLabel, cal) {
        delegate.getDate(columnLabel, cal)
    }

    override fun getDouble(columnIndex: Int): Double = recordGet(columnIndex) {
        delegate.getDouble(columnIndex)
    }

    override fun getDouble(columnLabel: String?): Double = record(columnLabel) {
        delegate.getDouble(columnLabel)
    }

    override fun getFetchDirection(): Int = record {
        delegate.fetchDirection
    }

    override fun getFetchSize(): Int = record {
        delegate.fetchSize
    }

    override fun getFloat(columnIndex: Int): Float = recordGet(columnIndex) {
        delegate.getFloat(columnIndex)
    }

    override fun getFloat(columnLabel: String?): Float = record(columnLabel) {
        delegate.getFloat(columnLabel)
    }

    override fun getHoldability(): Int = record {
        delegate.holdability
    }

    override fun getInt(columnIndex: Int): Int = recordGet(columnIndex) {
        delegate.getInt(columnIndex)
    }

    override fun getInt(columnLabel: String): Int = record(columnLabel){
        delegate.getInt(columnLabel)
    }

    override fun getLong(columnIndex: Int): Long = recordGet(columnIndex) {
        delegate.getLong(columnIndex)
    }

    override fun getLong(columnLabel: String?): Long = record(columnLabel) {
        delegate.getLong(columnLabel)
    }

    abstract override fun getMetaData(): AbstractRecordingResultsetMetaData

    override fun getNCharacterStream(columnIndex: Int): Reader = recordGet(columnIndex) {
        delegate.getNCharacterStream(columnIndex)
    }

    override fun getNCharacterStream(columnLabel: String?): Reader = record(columnLabel) {
        delegate.getNCharacterStream(columnLabel)
    }

    override fun getNClob(columnIndex: Int): NClob = recordGet(columnIndex) {
        delegate.getNClob(columnIndex)
    }

    override fun getNClob(columnLabel: String?): NClob = record(columnLabel) {
        delegate.getNClob(columnLabel)
    }

    override fun getNString(columnIndex: Int): String = recordGet(columnIndex) {
        delegate.getNString(columnIndex)
    }

    override fun getNString(columnLabel: String?): String = record(columnLabel) {
        delegate.getNString(columnLabel)
    }

    override fun getObject(columnIndex: Int): Any = recordGet(columnIndex) {
        delegate.getObject(columnIndex)
    }

    override fun getObject(columnLabel: String?): Any = record(columnLabel) {
        delegate.getObject(columnLabel)
    }

    override fun getObject(columnIndex: Int, map: MutableMap<String, Class<*>>): Any = recordGet(columnIndex, map) {
        delegate.getObject(columnIndex, map)
    }

    override fun getObject(columnLabel: String?, map: MutableMap<String, Class<*>>): Any = record(columnLabel, map) {
        delegate.getObject(columnLabel, map)
    }

    override fun <T : Any?> getObject(columnIndex: Int, type: Class<T>?): T = recordGet(columnIndex, type) {
        delegate.getObject(columnIndex, type)
    }

    override fun <T : Any?> getObject(columnLabel: String?, type: Class<T>?): T = record(columnLabel, type) {
        delegate.getObject(columnLabel, type)
    }

    override fun getRef(columnIndex: Int): Ref = recordGet(columnIndex) {
        delegate.getRef(columnIndex)
    }

    override fun getRef(columnLabel: String?): Ref = record(columnLabel) {
        delegate.getRef(columnLabel)
    }

    override fun getRow(): Int = record {
        delegate.row
    }

    override fun getRowId(columnIndex: Int): RowId = recordGet(columnIndex) {
        delegate.getRowId(columnIndex)
    }

    override fun getRowId(columnLabel: String?): RowId = record(columnLabel) {
        delegate.getRowId(columnLabel)
    }

    override fun getShort(columnIndex: Int): Short = recordGet(columnIndex) {
        delegate.getShort(columnIndex)
    }

    override fun getShort(columnLabel: String?): Short = record(columnLabel) {
        delegate.getShort(columnLabel)
    }

    override fun getSQLXML(columnIndex: Int): SQLXML = recordGet(columnIndex) {
        delegate.getSQLXML(columnIndex)
    }

    override fun getSQLXML(columnLabel: String?): SQLXML = record(columnLabel) {
        delegate.getSQLXML(columnLabel)
    }

    override fun getStatement(): Statement = record {
        delegate.statement
    }

    override fun getString(columnIndex: Int): String? = recordGet(columnIndex) {
        delegate.getString(columnIndex)
    }

    override fun getString(columnLabel: String): String? = record {
        delegate.getString(columnLabel)
    }

    override fun getTime(columnIndex: Int): Time = recordGet(columnIndex) {
        delegate.getTime(columnIndex)
    }

    override fun getTime(columnLabel: String?): Time = record(columnLabel) {
        delegate.getTime(columnLabel)
    }

    override fun getTime(columnIndex: Int, cal: Calendar?): Time = recordGet(columnIndex, cal) {
        delegate.getTime(columnIndex, cal)
    }

    override fun getTime(columnLabel: String?, cal: Calendar?): Time = record(columnLabel, cal) {
        delegate.getTime(columnLabel, cal)
    }

    override fun getTimestamp(columnIndex: Int): Timestamp = recordGet(columnIndex) {
        delegate.getTimestamp(columnIndex)
    }

    override fun getTimestamp(columnLabel: String?): Timestamp = record(columnLabel) {
        delegate.getTimestamp(columnLabel)
    }

    override fun getTimestamp(columnIndex: Int, cal: Calendar?): Timestamp = recordGet(columnIndex, cal) {
        delegate.getTimestamp(columnIndex, cal)
    }

    override fun getTimestamp(columnLabel: String?, cal: Calendar?): Timestamp = record(columnLabel, cal) {
        delegate.getTimestamp(columnLabel, cal)
    }

    override fun getType(): Int = record {
        delegate.type
    }

    override fun getUnicodeStream(columnIndex: Int): InputStream = recordGet(columnIndex) {
        @Suppress("DEPRECATION")
        delegate.getUnicodeStream(columnIndex)
    }

    override fun getUnicodeStream(columnLabel: String?): InputStream = record(columnLabel) {
        @Suppress("DEPRECATION")
        delegate.getUnicodeStream(columnLabel)
    }

    override fun getURL(columnIndex: Int): URL = recordGet(columnIndex) {
        delegate.getURL(columnIndex)
    }

    override fun getURL(columnLabel: String?): URL = record(columnLabel) {
        delegate.getURL(columnLabel)
    }

    override fun getWarnings(): SQLWarning = record {
        delegate.warnings
    }

    override fun insertRow() {
        record()
        delegate.insertRow()
    }

    override fun isAfterLast(): Boolean = record {
        delegate.isAfterLast
    }

    override fun isBeforeFirst(): Boolean = record {
        delegate.isBeforeFirst
    }

    override fun isClosed(): Boolean = record {
        delegate.isClosed
    }

    override fun isFirst(): Boolean = record {
        delegate.isFirst
    }

    override fun isLast(): Boolean = record {
        delegate.isLast
    }

    override fun last(): Boolean = record {
        delegate.last()
    }

    override fun moveToCurrentRow() {
        record()
        delegate.moveToCurrentRow()
    }

    override fun moveToInsertRow() {
        record()
        delegate.moveToInsertRow()
    }

    override fun relative(rows: Int): Boolean = record(rows) {
        delegate.relative(rows)
    }

    override fun next(): Boolean = record {
        delegate.next()
    }

    override fun previous(): Boolean = record {
        delegate.previous()
    }

    override fun refreshRow() {
        record()
        delegate.refreshRow()
    }

    override fun rowDeleted(): Boolean = record {
        delegate.rowDeleted()
    }

    override fun rowInserted(): Boolean = record {
        delegate.rowInserted()
    }

    override fun rowUpdated(): Boolean = record {
        delegate.rowUpdated()
    }

    override fun setFetchDirection(direction: Int) {
        record(direction)
        delegate.fetchDirection = direction
    }

    override fun setFetchSize(rows: Int) {
        record(rows)
        delegate.fetchSize = rows
    }

    override fun updateArray(columnIndex: Int, x: SqlArray?) {
        recordUpdate(columnIndex, x)
        delegate.updateArray(columnIndex, x)
    }

    override fun updateArray(columnLabel: String?, x: SqlArray?) {
        record(columnLabel, x)
        delegate.updateArray(columnLabel, x)
    }

    override fun updateAsciiStream(columnIndex: Int, x: InputStream?, length: Int) {
        recordUpdate(columnIndex, x, length)
        delegate.updateAsciiStream(columnIndex, x, length)
    }

    override fun updateAsciiStream(columnLabel: String?, x: InputStream?, length: Int) {
        record(columnLabel, x, length)
        delegate.updateAsciiStream(columnLabel, x, length)
    }

    override fun updateAsciiStream(columnIndex: Int, x: InputStream?, length: Long) {
        recordUpdate(columnIndex, x, length)
        delegate.updateAsciiStream(columnIndex, x, length)
    }

    override fun updateAsciiStream(columnLabel: String?, x: InputStream?, length: Long) {
        record(columnLabel, x, length)
        delegate.updateAsciiStream(columnLabel, x, length)
    }

    override fun updateAsciiStream(columnIndex: Int, x: InputStream?) {
        recordUpdate(columnIndex, x)
        delegate.updateAsciiStream(columnIndex, x)
    }

    override fun updateAsciiStream(columnLabel: String?, x: InputStream?) {
        record(columnLabel, x)
        delegate.updateAsciiStream(columnLabel, x)
    }

    override fun updateBigDecimal(columnIndex: Int, x: BigDecimal?) {
        recordUpdate(columnIndex, x)
        delegate.updateBigDecimal(columnIndex, x)
    }

    override fun updateBigDecimal(columnLabel: String?, x: BigDecimal?) {
        record(columnLabel, x)
        delegate.updateBigDecimal(columnLabel, x)
    }

    override fun updateBinaryStream(columnIndex: Int, x: InputStream?, length: Int) {
        recordUpdate(columnIndex, x, length)
        delegate.updateBinaryStream(columnIndex, x, length)
    }

    override fun updateBinaryStream(columnLabel: String?, x: InputStream?, length: Int) {
        record(columnLabel, x, length)
        delegate.updateBinaryStream(columnLabel, x, length)
    }

    override fun updateBinaryStream(columnIndex: Int, x: InputStream?, length: Long) {
        recordUpdate(columnIndex, x, length)
        delegate.updateBinaryStream(columnIndex, x, length)
    }

    override fun updateBinaryStream(columnLabel: String?, x: InputStream?, length: Long) {
        record(columnLabel, x, length)
        delegate.updateBinaryStream(columnLabel, x, length)
    }

    override fun updateBinaryStream(columnIndex: Int, x: InputStream?) {
        recordUpdate(columnIndex, x)
        delegate.updateBinaryStream(columnIndex, x)
    }

    override fun updateBinaryStream(columnLabel: String?, x: InputStream?) {
        record(columnLabel, x)
        delegate.updateBinaryStream(columnLabel, x)
    }

    override fun updateBlob(columnIndex: Int, x: Blob?) {
        recordUpdate(columnIndex, x)
        delegate.updateBlob(columnIndex, x)
    }

    override fun updateBlob(columnLabel: String?, x: Blob?) {
        record(columnLabel, x)
        delegate.updateBlob(columnLabel, x)
    }

    override fun updateBlob(columnIndex: Int, inputStream: InputStream?, length: Long) {
        recordUpdate(columnIndex, inputStream, length)
        delegate.updateBlob(columnIndex, inputStream, length)
    }

    override fun updateBlob(columnLabel: String?, inputStream: InputStream?, length: Long) {
        record(columnLabel, inputStream, length)
        delegate.updateBlob(columnLabel, inputStream, length)
    }

    override fun updateBlob(columnIndex: Int, inputStream: InputStream?) {
        recordUpdate(columnIndex, inputStream)
        delegate.updateBlob(columnIndex, inputStream)
    }

    override fun updateBlob(columnLabel: String?, inputStream: InputStream?) {
        record(columnLabel, inputStream)
        delegate.updateBlob(columnLabel, inputStream)
    }

    override fun updateBoolean(columnIndex: Int, x: Boolean) {
        recordUpdate(columnIndex, x)
        delegate.updateBoolean(columnIndex, x)
    }

    override fun updateBoolean(columnLabel: String?, x: Boolean) {
        record(columnLabel, x)
        delegate.updateBoolean(columnLabel, x)
    }

    override fun updateByte(columnIndex: Int, x: Byte) {
        recordUpdate(columnIndex, x)
        delegate.updateByte(columnIndex, x)
    }

    override fun updateByte(columnLabel: String?, x: Byte) {
        record(columnLabel, x)
        delegate.updateByte(columnLabel, x)
    }

    override fun updateBytes(columnIndex: Int, x: ByteArray?) {
        recordUpdate(columnIndex, x)
        delegate.updateBytes(columnIndex, x)
    }

    override fun updateBytes(columnLabel: String?, x: ByteArray?) {
        record(columnLabel, x)
        delegate.updateBytes(columnLabel, x)
    }

    override fun updateCharacterStream(columnIndex: Int, x: Reader?, length: Int) {
        recordUpdate(columnIndex, x, length)
        delegate.updateCharacterStream(columnIndex, x, length)
    }

    override fun updateCharacterStream(columnLabel: String?, reader: Reader?, length: Int) {
        record(columnLabel, reader, length)
        delegate.updateCharacterStream(columnLabel, reader, length)
    }

    override fun updateCharacterStream(columnIndex: Int, x: Reader?, length: Long) {
        recordUpdate(columnIndex, x, length)
        delegate.updateCharacterStream(columnIndex, x, length)
    }

    override fun updateCharacterStream(columnLabel: String?, reader: Reader?, length: Long) {
        record(columnLabel, reader, length)
        delegate.updateCharacterStream(columnLabel, reader, length)
    }

    override fun updateCharacterStream(columnIndex: Int, x: Reader?) {
        recordUpdate(columnIndex, x)
        delegate.updateCharacterStream(columnIndex, x)
    }

    override fun updateCharacterStream(columnLabel: String?, reader: Reader?) {
        record(columnLabel, reader)
        delegate.updateCharacterStream(columnLabel, reader)
    }

    override fun updateClob(columnIndex: Int, x: Clob?) {
        recordUpdate(columnIndex, x)
        delegate.updateClob(columnIndex, x)
    }

    override fun updateClob(columnLabel: String?, x: Clob?) {
        record(columnLabel, x)
        delegate.updateClob(columnLabel, x)
    }

    override fun updateClob(columnIndex: Int, reader: Reader?, length: Long) {
        recordUpdate(columnIndex, reader, length)
        delegate.updateClob(columnIndex, reader, length)
    }

    override fun updateClob(columnLabel: String?, reader: Reader?, length: Long) {
        record(columnLabel, reader, length)
        delegate.updateClob(columnLabel, reader, length)
    }

    override fun updateClob(columnIndex: Int, reader: Reader?) {
        recordUpdate(columnIndex, reader)
        delegate.updateClob(columnIndex, reader)
    }

    override fun updateClob(columnLabel: String?, reader: Reader?) {
        record(columnLabel, reader)
        delegate.updateClob(columnLabel, reader)
    }

    override fun updateDate(columnIndex: Int, x: Date?) {
        recordUpdate(columnIndex, x)
        delegate.updateDate(columnIndex, x)
    }

    override fun updateDate(columnLabel: String?, x: Date?) {
        record(columnLabel, x)
        delegate.updateDate(columnLabel, x)
    }

    override fun updateDouble(columnIndex: Int, x: Double) {
        recordUpdate(columnIndex, x)
        delegate.updateDouble(columnIndex, x)
    }

    override fun updateDouble(columnLabel: String?, x: Double) {
        record(columnLabel, x)
        delegate.updateDouble(columnLabel, x)
    }

    override fun updateFloat(columnIndex: Int, x: Float) {
        recordUpdate(columnIndex, x)
        delegate.updateFloat(columnIndex, x)
    }

    override fun updateFloat(columnLabel: String?, x: Float) {
        record(columnLabel, x)
        delegate.updateFloat(columnLabel, x)
    }

    override fun updateInt(columnIndex: Int, x: Int) {
        recordUpdate(columnIndex, x)
        delegate.updateInt(columnIndex, x)
    }

    override fun updateInt(columnLabel: String?, x: Int) {
        record(columnLabel, x)
        delegate.updateInt(columnLabel, x)
    }

    override fun updateLong(columnIndex: Int, x: Long) {
        recordUpdate(columnIndex, x)
        delegate.updateLong(columnIndex, x)
    }

    override fun updateLong(columnLabel: String?, x: Long) {
        record(columnLabel, x)
        delegate.updateLong(columnLabel, x)
    }

    override fun updateNCharacterStream(columnIndex: Int, x: Reader?, length: Long) {
        recordUpdate(columnIndex, x, length)
        delegate.updateNCharacterStream(columnIndex, x, length)
    }

    override fun updateNCharacterStream(columnLabel: String?, reader: Reader?, length: Long) {
        record(columnLabel, reader, length)
        delegate.updateNCharacterStream(columnLabel, reader, length)
    }

    override fun updateNCharacterStream(columnIndex: Int, x: Reader?) {
        recordUpdate(columnIndex, x)
        delegate.updateNCharacterStream(columnIndex, x)
    }

    override fun updateNCharacterStream(columnLabel: String?, reader: Reader?) {
        record(columnLabel, reader)
        delegate.updateNCharacterStream(columnLabel, reader)
    }

    override fun updateNClob(columnIndex: Int, nClob: NClob?) {
        recordUpdate(columnIndex, nClob)
        delegate.updateNClob(columnIndex, nClob)
    }

    override fun updateNClob(columnLabel: String?, nClob: NClob?) {
        record(columnLabel, nClob)
        delegate.updateNClob(columnLabel, nClob)
    }

    override fun updateNClob(columnIndex: Int, reader: Reader?, length: Long) {
        recordUpdate(columnIndex, reader, length)
        delegate.updateNClob(columnIndex, reader, length)
    }

    override fun updateNClob(columnLabel: String?, reader: Reader?, length: Long) {
        record(columnLabel, reader, length)
        delegate.updateNClob(columnLabel, reader, length)
    }

    override fun updateNClob(columnIndex: Int, reader: Reader?) {
        recordUpdate(columnIndex, reader)
        delegate.updateNClob(columnIndex, reader)
    }

    override fun updateNClob(columnLabel: String?, reader: Reader?) {
        record(columnLabel, reader)
        delegate.updateNClob(columnLabel, reader)
    }

    override fun updateNString(columnIndex: Int, nString: String?) {
        recordUpdate(columnIndex, nString)
        delegate.updateNString(columnIndex, nString)
    }

    override fun updateNString(columnLabel: String?, nString: String?) {
        record(columnLabel, nString)
        delegate.updateNString(columnLabel, nString)
    }

    override fun updateNull(columnIndex: Int) {
        recordUpdate(columnIndex)
        delegate.updateNull(columnIndex)
    }

    override fun updateNull(columnLabel: String?) {
        record(columnLabel)
        delegate.updateNull(columnLabel)
    }

    override fun updateObject(columnIndex: Int, x: Any?, scaleOrLength: Int) {
        recordUpdate(columnIndex, x, scaleOrLength)
        delegate.updateObject(columnIndex, x, scaleOrLength)
    }

    override fun updateObject(columnLabel: String?, x: Any?, scaleOrLength: Int) {
        record(columnLabel, x, scaleOrLength)
        delegate.updateObject(columnLabel, x, scaleOrLength)
    }

    override fun updateObject(columnIndex: Int, x: Any?) {
        recordUpdate(columnIndex, x)
        delegate.updateObject(columnIndex, x)
    }

    override fun updateObject(columnLabel: String?, x: Any?) {
        record(columnLabel, x)
        delegate.updateObject(columnLabel, x)
    }

    override fun updateRef(columnIndex: Int, x: Ref?) {
        recordUpdate(columnIndex, x)
        delegate.updateRef(columnIndex, x)
    }

    override fun updateRef(columnLabel: String?, x: Ref?) {
        record(columnLabel, x)
        delegate.updateRef(columnLabel, x)
    }

    override fun updateRow() {
        record()
        delegate.updateRow()
    }

    override fun updateRowId(columnIndex: Int, x: RowId?) {
        recordUpdate(columnIndex, x)
        delegate.updateRowId(columnIndex, x)
    }

    override fun updateRowId(columnLabel: String?, x: RowId?) {
        record(columnLabel, x)
        delegate.updateRowId(columnLabel, x)
    }

    override fun updateShort(columnIndex: Int, x: Short) {
        recordUpdate(columnIndex, x)
        delegate.updateShort(columnIndex, x)
    }

    override fun updateShort(columnLabel: String?, x: Short) {
        record(columnLabel, x)
        delegate.updateShort(columnLabel, x)
    }

    override fun updateSQLXML(columnIndex: Int, xmlObject: SQLXML?) {
        recordUpdate(columnIndex, xmlObject)
        delegate.updateSQLXML(columnIndex, xmlObject)
    }

    override fun updateSQLXML(columnLabel: String?, xmlObject: SQLXML?) {
        record(columnLabel, xmlObject)
        delegate.updateSQLXML(columnLabel, xmlObject)
    }

    override fun updateString(columnIndex: Int, x: String?) {
        recordUpdate(columnIndex, x)
        delegate.updateString(columnIndex, x)
    }

    override fun updateString(columnLabel: String?, x: String?) {
        record(columnLabel, x)
        delegate.updateString(columnLabel, x)
    }

    override fun updateTime(columnIndex: Int, x: Time?) {
        recordUpdate(columnIndex, x)
        delegate.updateTime(columnIndex, x)
    }

    override fun updateTime(columnLabel: String?, x: Time?) {
        record(columnLabel, x)
        delegate.updateTime(columnLabel, x)
    }

    override fun updateTimestamp(columnIndex: Int, x: Timestamp?) {
        recordUpdate(columnIndex, x)
        delegate.updateTimestamp(columnIndex, x)
    }

    override fun updateTimestamp(columnLabel: String?, x: Timestamp?) {
        record(columnLabel, x)
        delegate.updateTimestamp(columnLabel, x)
    }

    override fun wasNull(): Boolean = record {
        delegate.wasNull()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractRecordingResultSet) return false

        if (query != other.query) return false

        return true
    }

    override fun hashCode(): Int {
        return query.hashCode()
    }

    override fun toString(): String {
        return "ResultSet($query)"
    }

}