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

package uk.ac.bournemouth.util.kotlin.sql

import java.math.BigDecimal
import java.sql.*
import java.util.*

@Suppress("NOTHING_TO_INLINE")
class StatementHelper constructor(val statement: PreparedStatement, private val queryString:String) : PreparedStatement by statement {
  /**
   * Get raw access to the underlying prepared statement. This should normally not be needed, but is available when it is.
   */
  inline fun <R> raw(block: (PreparedStatement) -> R): R = block(statement)

  @Deprecated("Use withResultSet")
  override fun getResultSet(): ResultSet? {
    throw UnsupportedOperationException()
  }

  @Deprecated("Use withGeneratedKeys")
  override fun getGeneratedKeys(): ResultSet? {
    throw UnsupportedOperationException()
  }

  @Deprecated("Use Execute with lambda instead", replaceWith = ReplaceWith("execute"))
  override fun executeQuery(p0: String?): ResultSet? {
    throw UnsupportedOperationException()
  }

  @get:Deprecated("Use the function access. This is not a value, but recreated every time.", ReplaceWith("warningsIterator()"))
  val warningsIt: Iterator<SQLWarning>
    get() = warningsIterator()

  private fun warningsIterator(): Iterator<SQLWarning> = object : AbstractIterator<SQLWarning>() {
    override fun computeNext() {
      val w = statement.warnings
      if (w != null) {
        setNext(w)
      } else {
        done()
      }
    }
  }

  @Suppress("unused", "FunctionName")
  @Deprecated("Use the columntype instead. This doesn't do nulls", ReplaceWith("ColumnType::setParam(this, index, value)"), level = DeprecationLevel.ERROR)
  fun <T> setParam_(index: Int, value: T) = when (value) {
    null -> setNull(index, Types.NULL)
    is Int -> setParam(index, value)
    is Long -> setParam(index, value)
    is String -> setParam(index, value)
    is Boolean -> setParam(index, value)
    is Byte -> setParam(index, value)
    is Short -> setParam(index, value)
    is BigDecimal -> setBigDecimal(index, value)
    is ByteArray -> statement.setBytes(index,value)
    else -> throw UnsupportedOperationException("Not possible to set this value ($value) of type ${(value as Any).javaClass.simpleName}")
  }


  fun setParam(index: Int, value: Int?) = if (value == null) setNull(index, Types.INTEGER) else setInt(index, value)
  fun setParam(index: Int, value: Long?) = if (value == null) setNull(index, Types.BIGINT) else setLong(index, value)
  fun setParam(index: Int, value: CharSequence?) = if (value == null) setNull(index, Types.VARCHAR) else setString(index, value.toString())
  fun setParam(index: Int, value: Boolean?) = if (value == null) setNull(index, Types.BOOLEAN) else setBoolean(index, value)
  fun setParam(index: Int, value: Byte?) = if (value == null) setNull(index, Types.TINYINT) else setByte(index, value)
  fun setParam(index: Int, value: Short?) = if (value == null) setNull(index, Types.SMALLINT) else setShort(index, value)

  @Suppress("unused", "NOTHING_TO_INLINE")
  class ParamHelper_(val sh: StatementHelper) {

    var index = 2
    inline operator fun Int?.unaryPlus(): Unit = sh.setParam(index++, this)
    inline operator fun Long?.unaryPlus(): Unit = sh.setParam(index++, this)
    inline operator fun String?.unaryPlus(): Unit = sh.setParam(index++, this)
    inline operator fun Boolean?.unaryPlus(): Unit = sh.setParam(index++, this)
    inline operator fun Byte?.unaryPlus(): Unit = sh.setParam(index++, this)
    inline operator fun Short?.unaryPlus(): Unit = sh.setParam(index++, this)

    inline operator fun plus(value: Int?): ParamHelper_ { sh.setParam(index++, value); return this }
    inline operator fun plus(value: Long?): ParamHelper_ { sh.setParam(index++, value); return this }
    inline operator fun plus(value: String?): ParamHelper_ { sh.setParam(index++, value); return this }
    inline operator fun plus(value: Boolean?): ParamHelper_ { sh.setParam(index++, value); return this }
    inline operator fun plus(value: Byte?): ParamHelper_ { sh.setParam(index++, value); return this }
    inline operator fun plus(value: Short?): ParamHelper_ { sh.setParam(index++, value); return this }

    inline operator fun plus(intValue: Int): ParamHelper_ { sh.setInt(index++, intValue); return this }
    inline operator fun plus(longValue: Long): ParamHelper_ { sh.setLong(index++, longValue); return this }
      @JvmName("plusNotNull")
    inline operator fun plus(stringValue: String): ParamHelper_ { sh.setString(index++, stringValue); return this }
    inline operator fun plus(booleanValue: Boolean): ParamHelper_ { sh.setBoolean(index++, booleanValue); return this }
    inline operator fun plus(byteValue: Byte): ParamHelper_ { sh.setByte(index++, byteValue); return this }
    inline operator fun plus(shortValue: Short): ParamHelper_ { sh.setShort(index++, shortValue); return this }
  }

//  inline fun <R> params(block: ParamHelper_.() -> R) = ParamHelper_(this).block()

  fun setByte(pos:Int, value: Byte?) {
    if (value ==null) statement.setNull(pos, Types.TINYINT) else statement.setByte(pos, value)
  }

  fun setShort(pos:Int, value: Short?) {
    if (value ==null) statement.setNull(pos, Types.SMALLINT) else statement.setShort(pos, value)
  }

  fun setInt(pos:Int, value: Int?) {
    if (value ==null) statement.setNull(pos, Types.INTEGER) else statement.setInt(pos, value)
  }

  fun setLong(pos:Int, value: Long?) {
    if (value ==null) statement.setNull(pos, Types.BIGINT) else statement.setLong(pos, value)
  }

  fun setFloat(pos:Int, value: Float?) {
    if (value ==null) statement.setNull(pos, Types.FLOAT) else statement.setFloat(pos, value)
  }

  fun setDouble(pos:Int, value: Double?) {
    if (value ==null) statement.setNull(pos, Types.DOUBLE) else statement.setDouble(pos, value)
  }

  fun setBoolean(pos:Int, value: Boolean?) {
    if (value ==null) statement.setNull(pos, Types.BOOLEAN) else statement.setBoolean(pos, value)
  }

  fun setDecimal(pos:Int, value: BigDecimal?) {
    if (value ==null) statement.setNull(pos, Types.DECIMAL) else statement.setBigDecimal(pos, value)
  }

  fun setNumeric(pos:Int, value: BigDecimal?) {
    if (value ==null) statement.setNull(pos, Types.NUMERIC) else statement.setBigDecimal(pos, value)
  }

  @Suppress("UNUSED_PARAMETER")
  fun setArray(pos:Int, value: BooleanArray?) {
    throw UnsupportedOperationException("Setting bit arrays is not yet supported")
  }

  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(value:Int?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(value:Long?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(value:String?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(value:Boolean?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(value:Byte?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(value:Short?):ParamHelper_ { setParam(1, value); return ParamHelper_(this) }

  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(intValue:Int):ParamHelper_ { setInt(1, intValue); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(longValue:Long):ParamHelper_ { setLong(1, longValue); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  @JvmName("paramsNotNull")
  inline fun params(stringValue:String):ParamHelper_ { setString(1, stringValue); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(booleanValue:Boolean):ParamHelper_ { setBoolean(1, booleanValue); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(byteValue:Byte):ParamHelper_ { setByte(1, byteValue); return ParamHelper_(this) }
  /** Start setting parameters. This returns a helper for adding the next ones. */
  inline fun params(shortValue:Short):ParamHelper_ { setShort(1, shortValue); return ParamHelper_(this) }

  @Suppress("MemberVisibilityCanBePrivate")
  inline fun <R> withResultSet(block: (ResultSet) -> R) = statement.resultSet.use(block)

  inline fun <R> withGeneratedKeys(block: (ResultSet) -> R) = statement.generatedKeys.use(block)

  inline fun <R> execute(block: (ResultSet) -> R) = executeQuery().use(block)


  inline val Int.i get() = this
  inline val Double.d get() = this
  @Suppress("PropertyName")
  inline val Long.L get() = this
  inline val Float.f get() = this

    /**
   * Execute the block for every result in the ResultSet. This is [execute] and [Sequence.forEach] combined.
   */
  inline fun <R> executeEach(block: (ResultSet) -> R): List<R> = execute { resultSet ->
    ArrayList<R>().apply {
      add(block(resultSet))
    }
  }

  /**
   * Get a a string as the single return column.
   */
  fun stringResult(): String? {
    execute { rs ->
      if(rs.next()) {
        return rs.getString(1)
      }
      return null
    }
  }

  fun executeHasRows(): Boolean = execute() && withResultSet { it.next() }

  override fun execute(): Boolean {
    try {
      return statement.execute()
    } catch (e:SQLException) {
      throw SQLException("Error executing statement: $queryString", e)
    }
  }

  override fun executeUpdate(): Int {
    try {
      return statement.executeUpdate()
    } catch (e:SQLException) {
      throw SQLException("Error executing statement: $queryString", e)
    }
  }

  override fun executeQuery(): ResultSet {
    try {
      return statement.executeQuery()
    } catch (e:SQLException) {
      throw SQLException("Error executing statement: $queryString", e)
    }
  }
}

inline fun <T : Statement, R> T.use(block: (T) -> R) = useHelper({ it.close() }, block)
inline fun <T : ResultSet, R> T.use(block: (T) -> R) = useHelper({ it.close() }, block)
