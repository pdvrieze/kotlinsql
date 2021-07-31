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

package uk.ac.bournemouth.kotlinsql.sql

import uk.ac.bournemouth.kotlinsql.*
import uk.ac.bournemouth.kotlinsql.impl.UnmanagedSql
import uk.ac.bournemouth.kotlinsql.impl.gen.DatabaseMethods
import uk.ac.bournemouth.kotlinsql.metadata.SafeDatabaseMetaData
import uk.ac.bournemouth.kotlinsql.metadata.forEach
import uk.ac.bournemouth.kotlinsql.sql.impl.DDLPreparedStatementHelper
import uk.ac.bournemouth.kotlinsql.util.WarningIterator
import java.sql.*
import java.util.*

open class DBConnection<DB: Database> constructor(val rawConnection: Connection, val db: DB): DatabaseMethods {

    /** @see [Connection.getAutoCommit] */
    var autoCommit: Boolean
        get() = rawConnection.autoCommit
        set(value) {
            rawConnection.autoCommit = value
        }

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
     *
     * @exception SQLException if a database access error occurs or
     * *            this method is called on a closed connection
     *
     * @see SQLWarning
     */
    val warningsIt: Iterator<SQLWarning> get() = WarningIterator(rawConnection.warnings)

    val warnings: Sequence<SQLWarning>
        get() = object : Sequence<SQLWarning> {
            override fun iterator(): Iterator<SQLWarning> = warningsIt
        }

    override fun get(key: TableRef): Table = db.get(key)

    @Suppress("DEPRECATION")
    inline fun <R> use(block: (DBConnection<DB>) -> R): R = useHelper({ it.rawConnection.close() }) {
        return transaction(block)
    }

    fun <R> raw(block: (Connection) -> R): R = block(rawConnection)

    @Suppress("DEPRECATION")
    inline fun <R> transaction(block: DBConnection<DB>.() -> R): R {
        rawConnection.autoCommit = false
        val savePoint = rawConnection.setSavepoint()
        try {
            return block().apply {
                releaseSavepoint(savePoint)
                commit()
            }
        } catch (e: Exception) {
            rawConnection.rollback(savePoint)
            throw e
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun prepareCall(sql: String) = rawConnection.prepareCall(sql)

    /**
     * @see [Connection.commit]
     */
    fun commit() = rawConnection.commit()

    fun getMetaData() = SafeDatabaseMetaData(rawConnection.metaData)

    @Throws(SQLException::class)
    fun rollback() = rawConnection.rollback()

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

    @Throws(SQLException::class)
    fun setSavepoint(): Savepoint = rawConnection.setSavepoint()

    @Throws(SQLException::class)
    fun setSavepoint(name: String): Savepoint = rawConnection.setSavepoint(name)

    @Throws(SQLException::class)
    fun rollback(savepoint: Savepoint) = rawConnection.rollback(savepoint)

    @Throws(SQLException::class)
    fun releaseSavepoint(savepoint: Savepoint) = rawConnection.releaseSavepoint(savepoint)

    @Throws(SQLException::class)
    fun createClob(): Clob = rawConnection.createClob()

    @Throws(SQLException::class)
    fun createBlob(): Blob = rawConnection.createBlob()

    @Throws(SQLException::class)
    fun createNClob(): NClob = rawConnection.createNClob()

    @Throws(SQLException::class)
    fun createSQLXML(): SQLXML = rawConnection.createSQLXML()

    @Throws(SQLException::class)
    fun createArrayOf(typeName: String, elements: Array<Any>): java.sql.Array? =
        rawConnection.createArrayOf(typeName, elements)

    @Throws(SQLException::class)
    fun createStruct(typeName: String, attributes: Array<Any>): Struct =
        rawConnection.createStruct(typeName, attributes)

    @OptIn(UnmanagedSql::class)
    fun hasTable(tableRef: TableRef): Boolean {
        return getMetaData().getTables(null, null, tableRef._name, null).use { rs ->
            rs.next()
        }
    }

    fun <R> withMetaData(action: SafeDatabaseMetaData.() -> R): R {
        return SafeDatabaseMetaData(rawConnection.metaData)
            .action()
    }

    /**
     * @see [Connection.prepareStatement]
     */
    inline fun <R> prepareStatement(sql: String, block: DDLPreparedStatementHelper.() -> R) =
        rawConnection.prepareStatement(sql).use {
            DDLPreparedStatementHelper(it, sql).block()
        }

    @Throws(SQLException::class)
    inline fun <R> prepareStatement(
        sql: String, resultSetType: Int,
        resultSetConcurrency: Int, block: DDLPreparedStatementHelper.() -> R
    ): R {
        return rawConnection.prepareStatement(sql, resultSetType, resultSetConcurrency)
            .use { DDLPreparedStatementHelper(it, sql).block() }
    }

    @Throws(SQLException::class)
    inline fun <R> prepareStatement(
        sql: String, resultSetType: Int,
        resultSetConcurrency: Int, resultSetHoldability: Int, block: DDLPreparedStatementHelper.() -> R
    ): R {
        return rawConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability)
            .use { DDLPreparedStatementHelper(it, sql).block() }
    }

    @Throws(SQLException::class)
    inline fun <R> prepareStatement(sql: String, autoGeneratedKeys: Boolean, block: DDLPreparedStatementHelper.() -> R): R {
        val autoGeneratedKeysFlag =
            if (autoGeneratedKeys) Statement.RETURN_GENERATED_KEYS else Statement.NO_GENERATED_KEYS
        return rawConnection.prepareStatement(sql, autoGeneratedKeysFlag)
            .use { DDLPreparedStatementHelper(it, sql).block() }
    }

    @Throws(SQLException::class)
    inline fun <R> prepareStatement(sql: String, autoGeneratedKeys: Int, block: DDLPreparedStatementHelper.() -> R): R {
        return rawConnection.prepareStatement(sql, autoGeneratedKeys)
            .use { DDLPreparedStatementHelper(it, sql).block() }
    }

    @Throws(SQLException::class)
    fun <R> prepareStatement(sql: String, columnIndexes: IntArray, block: DDLPreparedStatementHelper.() -> R): R {
        return rawConnection.prepareStatement(sql, columnIndexes)
            .use { DDLPreparedStatementHelper(it, sql).block() }
    }

    @Throws(SQLException::class)
    fun <R> prepareStatement(sql: String, columnNames: Array<out String>, block: DDLPreparedStatementHelper.() -> R): R {
        return rawConnection.prepareStatement(sql, columnNames)
            .use { DDLPreparedStatementHelper(it, sql).block() }
    }

    fun Table.createTransitive(
        ifNotExists: Boolean,
        pending: MutableCollection<Table> = mutableSetOf()
    ) {
        val tableNames = (_cols.asSequence().mapNotNull { col -> col.references?.table } +
                _foreignKeys.asSequence().mapNotNull(ForeignKey::toTable)).toList()

        val neededTables = tableNames
            .map { db[it._name] }
            .filter { it !in pending }
            .toSet()

        for (table in neededTables) {
            pending.add(table)
            table.createTransitive(true, pending)
        }
        pending.add(this)
        if (! ifNotExists  || ! hasTable(this)) {
            prepareStatement(buildString { appendDDL(this) }) {
                execute()
            }
        }
    }

    fun Table.dropTransitive(ifExists: Boolean) {
        fun tableReferencesThis(table: Table): Boolean {
            return table._foreignKeys.any { fk -> fk.toTable._name == _name }
        }
        if (!ifExists || hasTable(this)) {
            // Create all tables this one depends on
            db._tables
                .filter(::tableReferencesThis)
                .forEach {
                    it.dropTransitive(true)
                }

            prepareStatement("DROP TABLE $_name") { execute() }
        }
    }

    fun Table.ensureTable(retainExtraColumns: Boolean) {
        val extraColumns = ArrayList<String>()
        val missingColumns = mutableMapOf<String, Column<*, *, *>>()
        _cols.associateByTo(missingColumns) { it.name.toLowerCase(Locale.ENGLISH) }

        withMetaData {
            (getColumns(null, null, _name, null)).forEach { rs ->
                val colName = rs.columnName
                val colType = rs.dataType
                val col = column(colName)
                missingColumns.remove(colName.toLowerCase(Locale.ENGLISH))
                if (col != null) {
                    val columnCorrect = col.matches(
                        rs.typeName, rs.columnSize, rs.isNullable?.not(),
                        rs.isAutoIncrement, rs.columnDefault, rs.remarks
                    )
                    if (!columnCorrect) {
                        try {
                            prepareStatement("ALTER TABLE $_name MODIFY COLUMN ${col.toDDL()}") {
                                executeUpdate()
                            }
                        } catch (e: SQLException) {
                            throw SQLException(
                                "Failure updating table $_name column $colName from $colType to ${col.type}", e
                            )
                        }
                    }
                } else {
                    extraColumns.add(colName)
                }

            }
        }

        if (!retainExtraColumns) {
            for (extraColumn in extraColumns) {
                prepareStatement("ALTER TABLE $_name DROP COLUMN `$extraColumn`") {
                    executeUpdate()
                }
            }
        }

        for (missingColumn in missingColumns.values) {
            prepareStatement("ALTER TABLE $_name ADD COLUMN ${missingColumn.toDDL()}") {
                executeUpdate()
            }
        }

    }


}