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

package uk.ac.bournemouth.util.kotlin.sql

import uk.ac.bournemouth.kotlinsql.ConnectionMetadata
import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.TableRef
import uk.ac.bournemouth.kotlinsql.WarningIterator
import java.sql.*
import java.util.*
import java.util.concurrent.Executor
import javax.sql.DataSource

/**
 * Create a new connection to the database and execute the block body on that connection. Close it after use.
 */
inline fun <R> DataSource.connection(db: Database, block: (DBConnection) -> R): R =
    this.connection.use {
      return DBConnection(connection, db).let(block)
    }


//inline fun <R> DataSource.connection(username: String, password: String, block: (DBConnection) -> R) = getConnection(username, password).use { connection(it, block) }

open class DBConnection constructor(val rawConnection: Connection, val db: Database) {

  init {
    rawConnection.autoCommit = false
  }

  fun <R> raw(block: (Connection) -> R): R = block(rawConnection)
  fun <R> use(block: (DBConnection) -> R): R = useHelper({ it.rawConnection.close() }) {
    return transaction(block)
  }

  fun <R> transaction(block: (DBConnection) -> R):R {
    rawConnection.autoCommit=false
    val savePoint = rawConnection.setSavepoint()
    try {
      return block(this).apply { commit() }
    } catch (e:Exception) {
      rawConnection.rollback(savePoint)
      throw e
    }
  }



  /**
   * @see [Connection.commit]
   */
  fun commit() = rawConnection.commit()

  fun getMetaData() = ConnectionMetadata(rawConnection.metaData)

  private inline fun prepareCall(sql: String) = rawConnection.prepareCall(sql)

  /** @see [Connection.getAutoCommit] */
  var autoCommit: Boolean
    get() = rawConnection.autoCommit
    set(value) { rawConnection.autoCommit = value }

  @Throws(SQLException::class)
  fun rollback() = rawConnection.rollback()

  @Throws(SQLException::class)
  fun isClosed(): Boolean = rawConnection.isClosed

  //======================================================================
  // Advanced features:

  @Throws(SQLException::class)
  fun setReadOnly(readOnly: Boolean) {
    rawConnection.isReadOnly = readOnly
  }

  @Throws(SQLException::class)
  fun isReadOnly(): Boolean = rawConnection.isReadOnly

  @Throws(SQLException::class)
  fun setCatalog(catalog: String) {
    rawConnection.catalog = catalog
  }

  @Throws(SQLException::class)
  fun getCatalog(): String = rawConnection.catalog

  @Deprecated("Don't use this, just use Connection's version", replaceWith = ReplaceWith("Connection.TRANSACTION_NONE", "java.sql.Connection"))
  val TRANSACTION_NONE = Connection.TRANSACTION_NONE

  @Deprecated("Don't use this, just use Connection's version", replaceWith = ReplaceWith("Connection.TRANSACTION_READ_UNCOMMITTED", "java.sql.Connection"))
  val TRANSACTION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED

  @Deprecated("Don't use this, just use Connection's version", replaceWith = ReplaceWith("Connection.TRANSACTION_READ_COMMITTED", "java.sql.Connection"))
  val TRANSACTION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED

  @Deprecated("Don't use this, just use Connection's version", replaceWith = ReplaceWith("Connection.TRANSACTION_REPEATABLE_READ", "java.sql.Connection"))
  val TRANSACTION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ

  @Deprecated("Don't use this, just use Connection's version", replaceWith = ReplaceWith("Connection.TRANSACTION_SERIALIZABLE", "java.sql.Connection"))
  val TRANSACTION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE

  fun TransactionIsolation(jdbcValue:Int):TransactionIsolation {
    return DBConnection.TransactionIsolation.values().first { it.intValue==jdbcValue }
  }

  enum class TransactionIsolation constructor(val intValue: Int, dummy:Unit) {
    TRANSACTION_NONE(Connection.TRANSACTION_NONE, Unit),
    TRANSACTION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED, Unit),
    TRANSACTION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED, Unit),
    TRANSACTION_REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ, Unit),
    TRANSACTION_SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE, Unit),
  }

  var transactionIsolation: TransactionIsolation
    get() = TransactionIsolation(rawConnection.transactionIsolation)
    set(value) { rawConnection.transactionIsolation = value.intValue }

  /**
   * Retrieves the first warning reported by calls on this
   * `Connection` object.  If there is more than one
   * warning, subsequent warnings will be chained to the first one
   * and can be retrieved by calling the method
   * `SQLWarning.getNextWarning` on the warning
   * that was retrieved previously.
   *
   * This method may not be
   * called on a closed connection; doing so will cause an
   * `SQLException` to be thrown.

   * Note: Subsequent warnings will be chained to this
   * SQLWarning.

   * @return the first `SQLWarning` object or `null`
   * *         if there are none
   * *
   * @exception SQLException if a database access error occurs or
   * *            this method is called on a closed connection
   * *
   * @see SQLWarning
   */
  val warningsIt: Iterator<SQLWarning> get() = WarningIterator(rawConnection.warnings)

  val warnings: Sequence<SQLWarning> get() = object: Sequence<SQLWarning> {
    override fun iterator(): Iterator<SQLWarning> = warningsIt
  }

  /**
   * Clears all warnings reported for this `Connection` object.
   * After a call to this method, the method `getWarnings`
   * returns `null` until a new warning is
   * reported for this `Connection` object.

   * @exception SQLException SQLException if a database access error occurs
   * * or this method is called on a closed connection
   */
  @Throws(SQLException::class)
  fun clearWarnings() = rawConnection.clearWarnings()


  //--------------------------JDBC 2.0-----------------------------
  /**
   * Retrieves the Map object associated with this Connection object.
   * @see [Connection.getTypeMap]
   */
  var typeMap: Map<String, Class<*>>
    get() = rawConnection.typeMap
    set(value) { rawConnection.typeMap = value}

  //--------------------------JDBC 3.0-----------------------------

  fun Holdability(jdbc:Int):Holdability {
    return Holdability.values().first { it.jdbc == jdbc }
  }

  enum class Holdability(val jdbc: Int, dummy: Unit) {
    HOLD_CURSORS_OVER_COMMIT(ResultSet.HOLD_CURSORS_OVER_COMMIT, Unit),
    CLOSE_CURSORS_AT_COMMIT(ResultSet.CLOSE_CURSORS_AT_COMMIT, Unit);
  }

  /**
   * @see [Connection.getHoldability]
   */
  var holdability:Holdability
    get() = when (rawConnection.holdability) {
      ResultSet.HOLD_CURSORS_OVER_COMMIT -> Holdability.HOLD_CURSORS_OVER_COMMIT
      ResultSet.CLOSE_CURSORS_AT_COMMIT -> Holdability.CLOSE_CURSORS_AT_COMMIT
      else -> throw IllegalArgumentException()
    }
    set(value) { rawConnection.holdability = value.jdbc }

  @Throws(SQLException::class)
  @Deprecated("Use the typed version", ReplaceWith("this.holdability = Holdability(holdability)"))
  fun setHoldability(holdability: Int) {
    rawConnection.holdability = holdability
  }

  @Throws(SQLException::class)
  @Deprecated("Use the typed version", ReplaceWith("this.holdability.jdbc"))
  fun getHoldability() = rawConnection.holdability

  @Throws(SQLException::class)
  fun setSavepoint(): Savepoint = rawConnection.setSavepoint()

  @Throws(SQLException::class)
  fun setSavepoint(name: String): Savepoint = rawConnection.setSavepoint(name)

  @Throws(SQLException::class)
  fun rollback(savepoint: Savepoint) = rawConnection.rollback(savepoint)

  @Throws(SQLException::class)
  fun releaseSavepoint(savepoint: Savepoint) = rawConnection.releaseSavepoint(savepoint)

  /**
   * @see [Connection.prepareStatement]
   */
  inline fun <R> prepareStatement(sql: String, block: StatementHelper.() -> R) = rawConnection.prepareStatement(sql).use {
    StatementHelper(it, sql).block()
  }

  @Throws(SQLException::class)
  fun <R> prepareStatement(sql: String, resultSetType: Int,
                           resultSetConcurrency: Int, block: StatementHelper.() -> R): R {
    return rawConnection.prepareStatement(sql, resultSetType, resultSetConcurrency).use { StatementHelper(it, sql).block() }
  }

  @Throws(SQLException::class)
  fun <R> prepareStatement(sql: String, resultSetType: Int,
                                  resultSetConcurrency: Int, resultSetHoldability: Int, block: StatementHelper.() -> R): R {
    return rawConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability).use { StatementHelper(it, sql).block() }
  }

  @Throws(SQLException::class)
  inline fun <R> prepareStatement(sql: String, autoGeneratedKeys: Boolean, block: StatementHelper.() -> R): R {
    val autoGeneratedKeysFlag = if (autoGeneratedKeys) Statement.RETURN_GENERATED_KEYS else Statement.NO_GENERATED_KEYS
    return rawConnection.prepareStatement(sql, autoGeneratedKeysFlag).use { StatementHelper(it, sql).block() }
  }

  @Throws(SQLException::class)
  inline fun <R> prepareStatement(sql: String, autoGeneratedKeys: Int, block: StatementHelper.() -> R): R {
    return rawConnection.prepareStatement(sql, autoGeneratedKeys).use { StatementHelper(it, sql).block() }
  }

  @Throws(SQLException::class)
  fun <R> prepareStatement(sql: String, columnIndexes: IntArray, block: StatementHelper.() -> R): R {
    return rawConnection.prepareStatement(sql, columnIndexes).use { StatementHelper(it, sql).block() }
  }

  @Throws(SQLException::class)
  fun <R> prepareStatement(sql: String, columnNames: Array<out String>, block: StatementHelper.() -> R): R {
    return rawConnection.prepareStatement(sql, columnNames).use { StatementHelper(it, sql).block() }
  }

  @Throws(SQLException::class)
  fun createClob(): Clob = rawConnection.createClob()

  @Throws(SQLException::class)
  fun createBlob(): Blob = rawConnection.createBlob()

  @Throws(SQLException::class)
  fun createNClob(): NClob = rawConnection.createNClob()

  @Throws(SQLException::class)
  fun createSQLXML(): SQLXML = rawConnection.createSQLXML()

  @Throws(SQLException::class)
  fun isValid(timeout: Int): Boolean = rawConnection.isValid(timeout)

  @Throws(SQLClientInfoException::class)
  fun setClientInfo(name: String, value: String) = rawConnection.setClientInfo(name, value)

  @Throws(SQLClientInfoException::class)
  fun setClientInfo(properties: Properties) {
    rawConnection.clientInfo = properties
  }

  @Throws(SQLException::class)
  fun getClientInfo(name: String): String = rawConnection.getClientInfo(name)

  @Throws(SQLException::class)
  fun getClientInfo(): Properties? = rawConnection.clientInfo

  @Throws(SQLException::class)
  fun createArrayOf(typeName: String, elements: Array<Any>): java.sql.Array? = rawConnection.createArrayOf(typeName, elements)

  @Throws(SQLException::class)
  fun createStruct(typeName: String, attributes: Array<Any>): Struct = rawConnection.createStruct(typeName, attributes)

  //--------------------------JDBC 4.1 -----------------------------

  @Throws(SQLException::class)
  fun setSchema(schema: String) {
    rawConnection.schema = schema
  }

  @Throws(SQLException::class)
  fun getSchema(): String = rawConnection.schema

  @Throws(SQLException::class)
  fun abort(executor: Executor) = rawConnection.abort(executor)

  @Throws(SQLException::class)
  fun setNetworkTimeout(executor: Executor, milliseconds: Int) = rawConnection.setNetworkTimeout(executor, milliseconds)


  @Throws(SQLException::class)
  fun getNetworkTimeout(): Int = rawConnection.networkTimeout

  fun hasTable(tableRef: TableRef): Boolean {
    return getMetaData().getTables(null, null, tableRef._name, null).use { rs->
      return rs.next()
    }
  }

}

/**
 * Executes the given [block] function on this resource and then closes it down correctly whether an exception
 * is thrown or not.
 *
 * @param block a function to process this closable resource.
 * @return the result of [block] function on this closable resource.
 */
inline fun <T : Connection, R> T.use(block: (T) -> R) = useHelper({ it.close() }, block)

inline fun <T : Connection, R> T.useTransacted(block: (T) -> R): R = useHelper({ it.close() }) {
  it.autoCommit = false
  try {
    val result = block(it)
    it.commit()
    return result
  } catch (e: Exception) {
    it.rollback()
    throw e
  }

}


inline fun  <T, R> T.useHelper(close: (T) -> Unit, block: (T) -> R): R {
  var closed = false
  try {
    return block(this)
  } catch (e: Exception) {
    closed = true
    try {
      close(this)
    } catch (closeException: Exception) {
      // drop for now.
    }
    throw e
  } finally {
    if (!closed) {
      close(this)
    }
  }
}