/*
 * Copyright (c) 2020.
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

package uk.ac.bournemouth.kotlinsql.test

import io.github.pdvrieze.jdbc.recorder.actions.Action
import java.io.InputStream
import java.io.Reader
import java.math.BigDecimal
import java.net.URL
import java.sql.*
import java.sql.Date
import java.util.*
import kotlin.Any
import kotlin.Array
import kotlin.Boolean
import kotlin.Byte
import kotlin.ByteArray
import kotlin.Double
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.Short
import kotlin.String
import kotlin.TODO
import kotlin.also
import kotlin.emptyArray
import java.sql.Array as SqlArray

abstract class AbstractDummyResultSet(
    val query: String,
    private val columns: Array<String>,
    private val data: List<Array<out Any?>>
) : ResultSet, Action {
    private var wasNull: Boolean? = null

    constructor(query: String) : this(query, emptyArray(), emptyList())

    private var pos: Int = 0

    fun getData(columnIndex: Int): Any? {
        return data[pos - 1][columnIndex - 1].also { wasNull = it == null }
    }

    override fun findColumn(columnLabel: String): Int {
        return columns.indexOf(columnLabel).also { if (it < 0) throw SQLException("Column not found") } + 1
    }

    override fun getNClob(columnIndex: Int): NClob {
        TODO("not implemented")
    }

    override fun getNClob(columnLabel: String?): NClob {
        TODO("not implemented")
    }

    override fun updateNString(columnIndex: Int, nString: String?) {
        TODO("not implemented")
    }

    override fun updateNString(columnLabel: String?, nString: String?) {
        TODO("not implemented")
    }

    override fun updateBinaryStream(columnIndex: Int, x: InputStream?, length: Int) {
        TODO("not implemented")
    }

    override fun updateBinaryStream(columnLabel: String?, x: InputStream?, length: Int) {
        TODO("not implemented")
    }

    override fun updateBinaryStream(columnIndex: Int, x: InputStream?, length: Long) {
        TODO("not implemented")
    }

    override fun updateBinaryStream(columnLabel: String?, x: InputStream?, length: Long) {
        TODO("not implemented")
    }

    override fun updateBinaryStream(columnIndex: Int, x: InputStream?) {
        TODO("not implemented")
    }

    override fun updateBinaryStream(columnLabel: String?, x: InputStream?) {
        TODO("not implemented")
    }

    override fun getStatement(): Statement {
        TODO("not implemented")
    }

    override fun updateTimestamp(columnIndex: Int, x: Timestamp?) {
        TODO("not implemented")
    }

    override fun updateTimestamp(columnLabel: String?, x: Timestamp?) {
        TODO("not implemented")
    }

    override fun updateNCharacterStream(columnIndex: Int, x: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun updateNCharacterStream(columnLabel: String?, reader: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun updateNCharacterStream(columnIndex: Int, x: Reader?) {
        TODO("not implemented")
    }

    override fun updateNCharacterStream(columnLabel: String?, reader: Reader?) {
        TODO("not implemented")
    }

    override fun updateInt(columnIndex: Int, x: Int) {
        TODO("not implemented")
    }

    override fun updateInt(columnLabel: String?, x: Int) {
        TODO("not implemented")
    }

    override fun moveToInsertRow() {
        TODO("not implemented")
    }

    override fun getDate(columnIndex: Int): Date {
        TODO("not implemented")
    }

    override fun getDate(columnLabel: String?): Date {
        TODO("not implemented")
    }

    override fun getDate(columnIndex: Int, cal: Calendar?): Date {
        TODO("not implemented")
    }

    override fun getDate(columnLabel: String?, cal: Calendar?): Date {
        TODO("not implemented")
    }

    override fun getWarnings(): SQLWarning {
        TODO("not implemented")
    }

    override fun beforeFirst() {
        pos = 0
    }

    override fun close() {}

    override fun updateFloat(columnIndex: Int, x: Float) {
        TODO("not implemented")
    }

    override fun updateFloat(columnLabel: String?, x: Float) {
        TODO("not implemented")
    }

    override fun getBoolean(columnIndex: Int): Boolean {
        TODO("not implemented")
    }

    override fun getBoolean(columnLabel: String?): Boolean {
        TODO("not implemented")
    }

    override fun isFirst(): Boolean = pos == 1

    override fun getBigDecimal(columnIndex: Int, scale: Int): BigDecimal {
        TODO("not implemented")
    }

    override fun getBigDecimal(columnLabel: String?, scale: Int): BigDecimal {
        TODO("not implemented")
    }

    override fun getBigDecimal(columnIndex: Int): BigDecimal {
        TODO("not implemented")
    }

    override fun getBigDecimal(columnLabel: String?): BigDecimal {
        TODO("not implemented")
    }

    override fun updateBytes(columnIndex: Int, x: ByteArray?) {
        TODO("not implemented")
    }

    override fun updateBytes(columnLabel: String?, x: ByteArray?) {
        TODO("not implemented")
    }

    override fun isLast(): Boolean = pos == data.size

    override fun insertRow() {
        TODO("not implemented")
    }

    override fun getTime(columnIndex: Int): Time {
        TODO("not implemented")
    }

    override fun getTime(columnLabel: String?): Time {
        TODO("not implemented")
    }

    override fun getTime(columnIndex: Int, cal: Calendar?): Time {
        TODO("not implemented")
    }

    override fun getTime(columnLabel: String?, cal: Calendar?): Time {
        TODO("not implemented")
    }

    override fun rowDeleted(): Boolean {
        TODO("not implemented")
    }

    override fun last(): Boolean  {
        pos = data.size
        return data.isNotEmpty()
    }

    override fun isAfterLast(): Boolean = pos > data.size

    override fun relative(rows: Int): Boolean {
        TODO("not implemented")
    }

    override fun absolute(row: Int): Boolean {
        TODO("not implemented")
    }

    override fun getSQLXML(columnIndex: Int): SQLXML {
        TODO("not implemented")
    }

    override fun getSQLXML(columnLabel: String?): SQLXML {
        TODO("not implemented")
    }

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        TODO("not implemented")
    }

    override fun next(): Boolean {
        pos++
        return pos <= data.size
    }

    override fun getFloat(columnIndex: Int): Float {
        TODO("not implemented")
    }

    override fun getFloat(columnLabel: String?): Float {
        TODO("not implemented")
    }

    override fun wasNull(): Boolean {
        TODO("not implemented")
    }

    override fun getRow(): Int {
        TODO("not implemented")
    }

    override fun first(): Boolean {
        pos = 1
        return pos <= data.size
    }

    override fun updateAsciiStream(columnIndex: Int, x: InputStream?, length: Int) {
        TODO("not implemented")
    }

    override fun updateAsciiStream(columnLabel: String?, x: InputStream?, length: Int) {
        TODO("not implemented")
    }

    override fun updateAsciiStream(columnIndex: Int, x: InputStream?, length: Long) {
        TODO("not implemented")
    }

    override fun updateAsciiStream(columnLabel: String?, x: InputStream?, length: Long) {
        TODO("not implemented")
    }

    override fun updateAsciiStream(columnIndex: Int, x: InputStream?) {
        TODO("not implemented")
    }

    override fun updateAsciiStream(columnLabel: String?, x: InputStream?) {
        TODO("not implemented")
    }

    override fun getURL(columnIndex: Int): URL {
        TODO("not implemented")
    }

    override fun getURL(columnLabel: String?): URL {
        TODO("not implemented")
    }

    override fun updateShort(columnIndex: Int, x: Short) {
        TODO("not implemented")
    }

    override fun updateShort(columnLabel: String?, x: Short) {
        TODO("not implemented")
    }

    override fun getType(): Int {
        TODO("not implemented")
    }

    override fun updateNClob(columnIndex: Int, nClob: NClob?) {
        TODO("not implemented")
    }

    override fun updateNClob(columnLabel: String?, nClob: NClob?) {
        TODO("not implemented")
    }

    override fun updateNClob(columnIndex: Int, reader: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun updateNClob(columnLabel: String?, reader: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun updateNClob(columnIndex: Int, reader: Reader?) {
        TODO("not implemented")
    }

    override fun updateNClob(columnLabel: String?, reader: Reader?) {
        TODO("not implemented")
    }

    override fun updateRef(columnIndex: Int, x: Ref?) {
        TODO("not implemented")
    }

    override fun updateRef(columnLabel: String?, x: Ref?) {
        TODO("not implemented")
    }

    override fun updateObject(columnIndex: Int, x: Any?, scaleOrLength: Int) {
        TODO("not implemented")
    }

    override fun updateObject(columnIndex: Int, x: Any?) {
        TODO("not implemented")
    }

    override fun updateObject(columnLabel: String?, x: Any?, scaleOrLength: Int) {
        TODO("not implemented")
    }

    override fun updateObject(columnLabel: String?, x: Any?) {
        TODO("not implemented")
    }

    override fun setFetchSize(rows: Int) {
        TODO("not implemented")
    }

    override fun afterLast() {
        TODO("not implemented")
    }

    override fun updateLong(columnIndex: Int, x: Long) {
        TODO("not implemented")
    }

    override fun updateLong(columnLabel: String?, x: Long) {
        TODO("not implemented")
    }

    override fun getBlob(columnIndex: Int): Blob {
        TODO("not implemented")
    }

    override fun getBlob(columnLabel: String?): Blob {
        TODO("not implemented")
    }

    override fun updateClob(columnIndex: Int, x: Clob?) {
        TODO("not implemented")
    }

    override fun updateClob(columnLabel: String?, x: Clob?) {
        TODO("not implemented")
    }

    override fun updateClob(columnIndex: Int, reader: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun updateClob(columnLabel: String?, reader: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun updateClob(columnIndex: Int, reader: Reader?) {
        TODO("not implemented")
    }

    override fun updateClob(columnLabel: String?, reader: Reader?) {
        TODO("not implemented")
    }

    override fun getByte(columnIndex: Int): Byte {
        TODO("not implemented")
    }

    override fun getByte(columnLabel: String?): Byte {
        TODO("not implemented")
    }

    override fun getString(columnIndex: Int): String? {
        return getData(columnIndex) as String?
    }

    override fun getString(columnLabel: String): String? {
        return getString(findColumn(columnLabel))
    }

    override fun updateSQLXML(columnIndex: Int, xmlObject: SQLXML?) {
        TODO("not implemented")
    }

    override fun updateSQLXML(columnLabel: String?, xmlObject: SQLXML?) {
        TODO("not implemented")
    }

    override fun updateDate(columnIndex: Int, x: Date?) {
        TODO("not implemented")
    }

    override fun updateDate(columnLabel: String?, x: Date?) {
        TODO("not implemented")
    }

    override fun getHoldability(): Int {
        TODO("not implemented")
    }

    override fun getObject(columnIndex: Int): Any {
        TODO("not implemented")
    }

    override fun getObject(columnLabel: String?): Any {
        TODO("not implemented")
    }

    override fun getObject(columnIndex: Int, map: MutableMap<String, Class<*>>?): Any {
        TODO("not implemented")
    }

    override fun getObject(columnLabel: String?, map: MutableMap<String, Class<*>>?): Any {
        TODO("not implemented")
    }

    override fun <T : Any?> getObject(columnIndex: Int, type: Class<T>?): T {
        TODO("not implemented")
    }

    override fun <T : Any?> getObject(columnLabel: String?, type: Class<T>?): T {
        TODO("not implemented")
    }

    override fun previous(): Boolean {
        TODO("not implemented")
    }

    override fun updateDouble(columnIndex: Int, x: Double) {
        TODO("not implemented")
    }

    override fun updateDouble(columnLabel: String?, x: Double) {
        TODO("not implemented")
    }

    override fun getLong(columnIndex: Int): Long {
        TODO("not implemented")
    }

    override fun getLong(columnLabel: String?): Long {
        TODO("not implemented")
    }

    override fun getClob(columnIndex: Int): Clob {
        TODO("not implemented")
    }

    override fun getClob(columnLabel: String?): Clob {
        TODO("not implemented")
    }

    override fun updateBlob(columnIndex: Int, x: Blob?) {
        TODO("not implemented")
    }

    override fun updateBlob(columnLabel: String?, x: Blob?) {
        TODO("not implemented")
    }

    override fun updateBlob(columnIndex: Int, inputStream: InputStream?, length: Long) {
        TODO("not implemented")
    }

    override fun updateBlob(columnLabel: String?, inputStream: InputStream?, length: Long) {
        TODO("not implemented")
    }

    override fun updateBlob(columnIndex: Int, inputStream: InputStream?) {
        TODO("not implemented")
    }

    override fun updateBlob(columnLabel: String?, inputStream: InputStream?) {
        TODO("not implemented")
    }

    override fun updateByte(columnIndex: Int, x: Byte) {
        TODO("not implemented")
    }

    override fun updateByte(columnLabel: String?, x: Byte) {
        TODO("not implemented")
    }

    override fun updateRow() {
        TODO("not implemented")
    }

    override fun deleteRow() {
        TODO("not implemented")
    }

    override fun isClosed(): Boolean {
        TODO("not implemented")
    }

    override fun getNString(columnIndex: Int): String {
        TODO("not implemented")
    }

    override fun getNString(columnLabel: String?): String {
        TODO("not implemented")
    }

    override fun getCursorName(): String {
        TODO("not implemented")
    }

    override fun getArray(columnIndex: Int): SqlArray {
        TODO("not implemented")
    }

    override fun getArray(columnLabel: String?): SqlArray {
        TODO("not implemented")
    }

    override fun cancelRowUpdates() {
        TODO("not implemented")
    }

    override fun updateString(columnIndex: Int, x: String?) {
        TODO("not implemented")
    }

    override fun updateString(columnLabel: String?, x: String?) {
        TODO("not implemented")
    }

    override fun setFetchDirection(direction: Int) {
        TODO("not implemented")
    }

    override fun getFetchSize(): Int {
        TODO("not implemented")
    }

    override fun getCharacterStream(columnIndex: Int): Reader {
        TODO("not implemented")
    }

    override fun getCharacterStream(columnLabel: String?): Reader {
        TODO("not implemented")
    }

    override fun isBeforeFirst(): Boolean {
        TODO("not implemented")
    }

    override fun updateBoolean(columnIndex: Int, x: Boolean) {
        TODO("not implemented")
    }

    override fun updateBoolean(columnLabel: String?, x: Boolean) {
        TODO("not implemented")
    }

    override fun refreshRow() {
        TODO("not implemented")
    }

    override fun rowUpdated(): Boolean {
        TODO("not implemented")
    }

    override fun updateBigDecimal(columnIndex: Int, x: BigDecimal?) {
        TODO("not implemented")
    }

    override fun updateBigDecimal(columnLabel: String?, x: BigDecimal?) {
        TODO("not implemented")
    }

    override fun getShort(columnIndex: Int): Short {
        TODO("not implemented")
    }

    override fun getShort(columnLabel: String?): Short {
        TODO("not implemented")
    }

    override fun getAsciiStream(columnIndex: Int): InputStream {
        TODO("not implemented")
    }

    override fun getAsciiStream(columnLabel: String?): InputStream {
        TODO("not implemented")
    }

    override fun updateTime(columnIndex: Int, x: Time?) {
        TODO("not implemented")
    }

    override fun updateTime(columnLabel: String?, x: Time?) {
        TODO("not implemented")
    }

    override fun getTimestamp(columnIndex: Int): Timestamp {
        TODO("not implemented")
    }

    override fun getTimestamp(columnLabel: String?): Timestamp {
        TODO("not implemented")
    }

    override fun getTimestamp(columnIndex: Int, cal: Calendar?): Timestamp {
        TODO("not implemented")
    }

    override fun getTimestamp(columnLabel: String?, cal: Calendar?): Timestamp {
        TODO("not implemented")
    }

    override fun getRef(columnIndex: Int): Ref {
        TODO("not implemented")
    }

    override fun getRef(columnLabel: String?): Ref {
        TODO("not implemented")
    }

    override fun moveToCurrentRow() {
        TODO("not implemented")
    }

    override fun getConcurrency(): Int {
        TODO("not implemented")
    }

    override fun updateRowId(columnIndex: Int, x: RowId?) {
        TODO("not implemented")
    }

    override fun updateRowId(columnLabel: String?, x: RowId?) {
        TODO("not implemented")
    }

    override fun getNCharacterStream(columnIndex: Int): Reader {
        TODO("not implemented")
    }

    override fun getNCharacterStream(columnLabel: String?): Reader {
        TODO("not implemented")
    }

    override fun updateArray(columnIndex: Int, x: SqlArray?) {
        TODO("not implemented")
    }

    override fun updateArray(columnLabel: String?, x: SqlArray?) {
        TODO("not implemented")
    }

    override fun getBytes(columnIndex: Int): ByteArray {
        TODO("not implemented")
    }

    override fun getBytes(columnLabel: String?): ByteArray {
        TODO("not implemented")
    }

    override fun getDouble(columnIndex: Int): Double {
        TODO("not implemented")
    }

    override fun getDouble(columnLabel: String?): Double {
        TODO("not implemented")
    }

    override fun getUnicodeStream(columnIndex: Int): InputStream {
        TODO("not implemented")
    }

    override fun getUnicodeStream(columnLabel: String?): InputStream {
        TODO("not implemented")
    }

    override fun rowInserted(): Boolean {
        TODO("not implemented")
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean = false

    override fun getInt(columnIndex: Int): Int {
        return getData(columnIndex) as Int? ?: 0
    }

    override fun getInt(columnLabel: String): Int {
        return getInt(findColumn(columnLabel))
    }

    override fun updateNull(columnIndex: Int) {
        TODO("not implemented")
    }

    override fun updateNull(columnLabel: String?) {
        TODO("not implemented")
    }

    override fun getRowId(columnIndex: Int): RowId {
        TODO("not implemented")
    }

    override fun getRowId(columnLabel: String?): RowId {
        TODO("not implemented")
    }

    override fun clearWarnings() {
        TODO("not implemented")
    }

    override fun getMetaData(): ResultSetMetaData {
        TODO("not implemented")
    }

    override fun getBinaryStream(columnIndex: Int): InputStream {
        TODO("not implemented")
    }

    override fun getBinaryStream(columnLabel: String?): InputStream {
        TODO("not implemented")
    }

    override fun updateCharacterStream(columnIndex: Int, x: Reader?, length: Int) {
        TODO("not implemented")
    }

    override fun updateCharacterStream(columnLabel: String?, reader: Reader?, length: Int) {
        TODO("not implemented")
    }

    override fun updateCharacterStream(columnIndex: Int, x: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun updateCharacterStream(columnLabel: String?, reader: Reader?, length: Long) {
        TODO("not implemented")
    }

    override fun updateCharacterStream(columnIndex: Int, x: Reader?) {
        TODO("not implemented")
    }

    override fun updateCharacterStream(columnLabel: String?, reader: Reader?) {
        TODO("not implemented")
    }

    override fun getFetchDirection(): Int {
        TODO("not implemented")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractDummyResultSet) return false

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