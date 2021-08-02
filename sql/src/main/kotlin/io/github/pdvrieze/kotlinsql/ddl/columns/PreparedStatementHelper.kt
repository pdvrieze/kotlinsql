/*
 * Copyright (c) 2016.
 *
 * This file is part of ProcessManager.
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

@file:Suppress("ClassName", "unused")

package io.github.pdvrieze.kotlinsql.ddl.columns

import java.io.InputStream
import java.math.BigDecimal
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

interface PreparedStatementHelper {
    @Suppress("UNUSED_PARAMETER")
    fun setArray(pos: Int, sqlType: Int, value: BooleanArray?)
    fun setBlob(pos: Int, sqlType: Int, value: InputStream?)
    fun setBoolean(pos: Int, sqlType: Int, value: Boolean?)
    fun setByte(pos: Int, sqlType: Int, value: Byte?)
    fun setBytes(pos: Int, sqlType: Int, value: ByteArray?)
    fun setDecimal(pos: Int, sqlType: Int, value: BigDecimal?)
    fun setDate(pos: Int, sqlType: Int, value: Date?)
    fun setDouble(pos: Int, sqlType: Int, value: Double?)
    fun setFloat(pos: Int, sqlType: Int, value: Float?)
    fun setInt(pos: Int, sqlType: Int, value: Int?)
    fun setLong(pos: Int, sqlType: Int, value: Long?)
    fun setNumeric(pos: Int, sqlType: Int, value: BigDecimal?)
    fun setShort(pos: Int, sqlType: Int, value: Short?)
    fun setString(pos: Int, sqlType: Int, value: String?)
    fun setTime(pos: Int, sqlType: Int, value: Time?)
    fun setTimestamp(pos: Int, sqlType: Int, value: Timestamp?)

}

