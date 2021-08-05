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

@file:Suppress("ClassName", "FunctionName", "FunctionName")

package io.github.pdvrieze.kotlinsql.direct

import io.github.pdvrieze.kotlinsql.ddl.*
import io.github.pdvrieze.kotlinsql.dml.*
import io.github.pdvrieze.kotlinsql.dml.impl._BaseInsert
import io.github.pdvrieze.kotlinsql.dml.impl._Statement1Base
import io.github.pdvrieze.kotlinsql.dml.impl._UpdateBase
import io.github.pdvrieze.kotlinsql.dml.impl._UpdateStatement
import io.github.pdvrieze.kotlinsql.metadata.closingForEach
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource


internal fun <T> SelectStatement.executeHelper(
    connection: DBConnection<*>,
    block: T,
    invokeHelper: (ResultSet, T) -> Unit,
): Boolean {
    return connection.prepareStatement(toSQL()) {
        val select: SelectStatement = this@executeHelper
        select.setParams(this)
        execute { rs ->
            if (rs.next()) {
                do {
                    invokeHelper(rs, block)
                } while (rs.next())
                true
            } else {
                false
            }
        }
    }
}

/**
 * Get a single element or a null value according to the conversion function passed. This will throw if the result count
 * is larger than 1.
 *
 * @param connection The connection to use
 * @param resultHandler The function that transforms the query result into a result object.
 *
 * @throws SQLException If there are more results than expected, or if there is an underlying SQL error.
 */
fun <R> SelectStatement.getSingleListOrNull(
    connection: DBConnection<*>,
    resultHandler: (List<Column<*, *, *>>, List<Any?>) -> R,
): R? {
    return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute { rs ->
            if (!rs.next()) return null
            val columns = select.columns

            val data = columns.mapIndexed { i, column -> column.type.fromResultSet(rs, i + 1) }
            val result = resultHandler(columns.toList(), data)
            if (rs.next()) throw SQLException("More results than expected")
            return result
        }
    }
}

@Suppress("unused")
        /**
         * Get a single result item, but don't accept an empty result. Otherwise this is the same as [getSingleListOrNull].
         *
         * @see getSingleListOrNull
         */
fun <R> SelectStatement.getSingleList(
    connection: DBConnection<*>,
    resultHandler: (List<Column<*, *, *>>, List<Any?>) -> R,
): R {
    return getSingleListOrNull(connection, resultHandler) ?: throw NoSuchElementException()
}


private fun ColumnRef<*, *, *>.name(prefixMap: Map<String, String>?): String {
    return prefixMap?.run { get(table._name)?.let { "$it.`$name`" } } ?: "`$name`"
}

private fun TableRef.name(prefixMap: Map<String, String>?): String {
    return prefixMap?.let { map -> map[this._name]?.let { "`$_name` AS $it" } } ?: "`$_name`"
}

@Deprecated("Use companion member", ReplaceWith("IColumnType.fromSqlType(sqlType)"))
fun columnType(sqlType: Int): IColumnType<*, *, *> = IColumnType.fromSqlType(sqlType)

fun createUpdateStatement(update: _UpdateBase, where: WhereClause?): UpdatingStatement =
    if (where == null) update else _UpdateStatement(update, where)

internal fun createTablePrefixMap(tableNames: SortedSet<String>): Map<String, String>? {

    fun uniquePrefix(string: String, usedPrefixes: Set<String>): String {
        for (i in 1..(string.length - 1)) {
            string.substring(0, i).let {
                if (it !in usedPrefixes) return it
            }
        }
        for (i in 1..Int.MAX_VALUE) {
            (string + i.toString()).let {
                if (it !in usedPrefixes) return it
            }
        }
        throw IllegalArgumentException("No unique prefix could be found")
    }

    if (tableNames.size <= 1) return null

    return tableNames.let {
        val seen = mutableSetOf<String>()
        it.associateTo(sortedMapOf<String, String>()) { name ->
            name to uniquePrefix(name, seen).apply {
                seen.add(this)
            }
        }
    }
}

inline fun <DB : Database, R> DB.connect(datasource: DataSource, block: DBConnection<DB>.() -> R): R {
    val connection = datasource.connection
    var doCommit = true
    try {
        return DBConnection(connection, this).block()
    } catch (e: Exception) {
        try {
            connection.rollback()
        } finally {
            connection.close()
            doCommit = false
        }
        throw e
    } finally {
        if (doCommit) connection.commit()
        connection.close()
    }
}

inline operator fun <DB : Database, R> DB.invoke(datasource: DataSource, block: DB.(DBConnection<DB>) -> R): R {
    val connection = datasource.connection
    var doCommit = true
    try {
        return block(DBConnection(connection, this))
    } catch (e: Exception) {
        try {
            connection.rollback()
        } finally {
            connection.close()
            doCommit = false
        }
        throw e
    } finally {
        if (doCommit) connection.commit()
        connection.close()
    }
}

fun <DB : Database> DB.ensureTables(connection: DBConnection<DB>, retainExtraColumns: Boolean = true) {
    val missingTables = _tables.associateBy { it._name }.toMutableMap()
    val tablesToVerify = ArrayList<Table>()
    val notUsedTables = mutableListOf<String>()

    for (tableData in connection.getMetaData().getTables(types = arrayOf("TABLE"))) {
        val tableName = tableData.tableName

        when (val table = missingTables.remove(tableName)) {
            null -> notUsedTables.add(tableName)
            else -> tablesToVerify.add(table)
        }
    }

    connection.apply {
        for (table in tablesToVerify) {
            table.ensureTable(retainExtraColumns)
        }

        val pendingOrExistingTables = tablesToVerify.toMutableSet()

        for (table in missingTables.values) {
            table.createTransitive(ifNotExists = true, pendingOrExistingTables)
        }
    }

}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        _Statement1Base<T1, S1, C1>.getSingleOrNull(
    connection: DBConnection<DB>,
): T1? {
    return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute { rs ->
            if (rs.next()) {
                if (!rs.isLast) throw SQLException("Multiple results found, where only one or none expected")
                select.col1.type.fromResultSet(rs, 1)
            } else {
                null
            }
        }
    }
}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        _Statement1Base<T1, S1, C1>.hasRows(connection: DBConnection<DB>): Boolean {
    return connection.prepareStatement(toSQL()) {
        setParams(this)
        executeHasRows()
    }
}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        _Statement1Base<T1, S1, C1>.getSingle(connection: DBConnection<DB>): T1 {
    return getSingleOrNull(connection) ?: throw java.util.NoSuchElementException()
}

fun <DB : Database> _UpdateStatement.excuteUpdate(connection: DBConnection<DB>): Int {
    return connection.prepareStatement(toSQL()) {
        setParams(this)
        executeUpdate()
    }
}

/**
 * Execute the statement.
 * @param connection The connection object to use for the query
 * @return The amount of rows changed
 * @see java.sql.PreparedStatement.executeUpdate
 */
fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        Select1<T1, S1, C1>.execute(connection: DBConnection<DB>): Boolean {
    return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute()
    }
}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        Select1<T1, S1, C1>.execute(connection: DBConnection<DB>, block: (T1?) -> Unit): Boolean {
    return executeHelper(connection, block) { rs, block2 ->
        block2(col1.type.fromResultSet(rs, 1))
    }
}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        Select1<T1, S1, C1>.getSingle(connection: DBConnection<DB>): T1 {
    return getSingleOrNull(connection) ?: throw java.util.NoSuchElementException()
}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        Select1<T1, S1, C1>.getSingleOrNull(connection: DBConnection<DB>): T1? {
    return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute { rs ->
            if (rs.next()) {
                if (!rs.isLast) throw SQLException("Multiple results found, where only one or none expected")
                select.col1.type.fromResultSet(rs, 1)
            } else {
                null
            }
        }
    }
}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        Select1<T1, S1, C1>.hasRows(connection: DBConnection<DB>): Boolean {
    return connection.prepareStatement(toSQL()) {
        setParams(this)
        executeHasRows()
    }
}

fun <DB : Database> SelectStatement.executeList(
    connection: DBConnection<DB>,
    resultHandler: (List<Column<*, *, *>>, List<Any?>) -> Unit,
): Boolean {
    val columns = select.columns.asList()
    return executeHelper(connection, resultHandler) { rs, block ->
        val data = select.columns.mapIndexed { i, column -> column.type.fromResultSet(rs, i + 1) }
        block(columns, data)
    }
}

fun <DB : Database>
        Select.executeList(
    connection: DBConnection<DB>,
    resultHandler: (List<Column<*, *, *>>, List<Any?>) -> Unit,
): Boolean {
    val columns = select.columns.asList()
    return executeHelper(connection, resultHandler) { rs, block ->
        val data = select.columns.mapIndexed { i, column -> column.type.fromResultSet(rs, i + 1) }
        block(columns, data)
    }
}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>, R>
        Select1<T1, S1, C1>.getList(connection: DBConnection<DB>, factory: (T1?) -> R): List<R> {
    val result = mutableListOf<R>()
    execute(connection) { p1 -> result.add(factory(p1)) }
    return result
}

fun <DB : Database, T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>
        Select1<T1, S1, C1>.getList(connection: DBConnection<DB>): List<T1?> {
    val result = mutableListOf<T1?>()
    execute(connection) { p1 -> result.add(p1) }
    return result
}

fun <DB : Database> Delete.executeUpdate(connection: DBConnection<DB>): Int {
    return connection.prepareStatement(toSQL(createTablePrefixMap())) {
        executeUpdate()
    }
}

//fun <DB: Database, T : Any, R> Insert.executeSeq(connection: DBConnection<DB>, key: Column<T, *, *>, autogeneratedKeys: (T?) -> R): Sequence<R>
//fun <DB: Database, T : Any> Insert.executeSeq(connection: DBConnection<DB>, key: Column<T, *, *>): Sequence<T>

fun <DB : Database, T : Any, R> Insert.execute(
    connection: DBConnection<DB>,
    key: Column<T, *, *>,
    autogeneratedKeys: (T?) -> R,
): List<R> =
    executeSeq(connection, key, autogeneratedKeys).toList()

fun <DB : Database, T : Any> Insert.execute(connection: DBConnection<DB>, key: Column<T, *, *>): List<T> =
    executeSeq(connection, key).toList()

/**
 * Execute the statement. If the table has an  autoincrement column and no autoincrement column was specified,
 * the generated values will be recorded, otherwise not.
 * @param connection The connection object to use for the query
 * @return The amount of rows changed
 * @see java.sql.PreparedStatement.executeUpdate
 */
fun <DB : Database> _BaseInsert.executeUpdate(connection: DBConnection<DB>): Int {
    if (batch.isEmpty()) {
        return 0
    }
    val query = toSQL()
    try {
        return connection.prepareStatement(query) {
            for (valueSet in batch) {
                valueSet.setParams(this)
                addBatch()
            }
            executeBatch().filter { it >= 0 }.sum()
        }
    } catch (e: SQLException) {
        throw SQLException("Failure to update:\n  QUERY: $query", e)
    }
}

/**
 * Execute the statement.
 * @param connection The connection object to use for the query
 * @return A list of the results.
 * @see java.sql.PreparedStatement.executeUpdate
 */
fun <DB : Database, T : Any, R> Insert.executeSeq(
    connection: DBConnection<DB>,
    key: Column<T, *, *>,
    autogeneratedKeys: (T?) -> R,
): Sequence<R> {
    if (batch.isEmpty()) {
        throw IllegalStateException("There are no values to add")
    }
    return connection.prepareStatement(toSQL(), true) {
        for (valueSet in batch) {
            valueSet.setParams(this)
            addBatch()
        }
        when (executeUpdate()) {
            0    -> emptySequence()
            else -> withGeneratedKeys { rs ->
                sequence {
                    while (rs.next()) {
                        yield(autogeneratedKeys(key.fromResultSet(rs, 1)))
                    }
                }
            }
        }
    }
}

/**
 * Execute the statement.
 * @param connection The connection object to use for the query
 * @return A list with the resulting rows.
 * @see java.sql.PreparedStatement.executeUpdate
 */
fun <DB : Database, T : Any> Insert.executeSeq(connection: DBConnection<DB>, key: Column<T, *, *>): Sequence<T> {
    if (batch.isEmpty()) {
        throw IllegalStateException("There are no values to add")
    }
    return connection.prepareStatement(toSQL()) {
        for (valueSet in batch) {
            valueSet.setParams(this)
            addBatch()
        }
        withGeneratedKeys { rs ->
            sequence {
                while (rs.next()) {
                    yield(key.fromResultSet(rs, 1)!!)
                }
            }
        }
    }
}
