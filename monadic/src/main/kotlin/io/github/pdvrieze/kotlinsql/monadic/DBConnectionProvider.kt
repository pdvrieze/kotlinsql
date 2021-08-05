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

package io.github.pdvrieze.kotlinsql.monadic

import io.github.pdvrieze.kotlinsql.ddl.*
import io.github.pdvrieze.kotlinsql.metadata.ColumnsResults
import io.github.pdvrieze.kotlinsql.monadic.impl.MonadicMetadataImpl
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource

interface ConnectionSourceBase<DB : Database> {
    val datasource: DataSource
    val db: DB

    fun ensureTables(retainExtraColumns: Boolean = true): DBAction<DB, Unit>

    fun Table.ensureTable(retainExtraColumns: Boolean): DBAction<DB, Any?>

    fun Table.createTransitive(
        ifNotExists: Boolean,
        pending: MutableSet<Table> = mutableSetOf(),
    ): Iterable<DBAction<DB, Any?>>

    fun Table.dropTransitive(ifExists: Boolean): DBAction<DB, Unit>

    fun or(first: Boolean, second: DBAction<DB, Boolean>): DBAction<DB, Boolean> = when {
        first -> ConstantAction(true)
        else -> second
    }

    fun or(first: DBAction<DB, Boolean>, second: DBAction<DB, Boolean>): DBAction<DB, Boolean> = GenericAction { conn ->
        first.eval(conn) { b1 -> second.eval(conn) { b2 -> b1 || b2 } }
    }

}


fun <DB: Database, R> ConnectionSource<DB>.transaction(body: TransactionBuilder<DB>.() -> R): R {
    return TransactionAction(body).commit(this)
}

@DbActionDSL
class TransactionBuilder<DB: Database>(val connection: MonadicDBConnection<DB>): DBActionReceiver<DB> {

    override val db: DB get() = connection.db

    @Suppress("UNCHECKED_CAST")
    override val metadata: MonadicMetadata<DB>
        get() = metadataInstance as MonadicMetadata<DB>

    private var commitPending = false
    fun commit() {
        commitPending = true
    }

    internal fun commitIfNeeded() {
        if (commitPending) {
            connection.rawConnection.commit()
        }
    }
}

internal abstract class ConnectionSourceImplBase<DB : Database>: ConnectionSource<DB> {

    fun hasTable(tableRef: TableRef): DBAction<DB, Boolean> {
        return metadata.getTables(tableNamePattern = tableRef._name).isNotEmpty()
    }

    override fun ensureTables(retainExtraColumns: Boolean): DBAction<DB, Unit> {

        return metadata
            .getTables(types = arrayOf("TABLE"))
            .mapEach { it.tableName }
            .flatMap { tableNames ->
                val missingTables = db._tables.associateBy { it._name }.toMutableMap()
                val tablesToVerify = ArrayList<Table>()
                val notUsedTables = mutableListOf<String>()
                for (tableName in tableNames) {
                    val table = missingTables.remove(tableName)
                    if (table!=null) {
                        tablesToVerify.add(table)
                    } else {
                        notUsedTables.add(tableName)
                    }
                }
                mutableListOf<DBAction<DB, Any?>>().apply {

                    for (tableName in tablesToVerify) {
                        add(db[tableName].ensureTable(retainExtraColumns))
                    }

                    val pendingOrExistingTables = tablesToVerify.toMutableSet()

                    for (table in missingTables.values) {
                        if (table !in pendingOrExistingTables) {
                            addAll(table.createTransitive(ifNotExists = true, pendingOrExistingTables))
                        }
                    }

                }
            }.map { Unit } // TODO have more of a return
    }

    override fun Table.ensureTable(retainExtraColumns: Boolean): DBAction<DB, Any?> {
        return metadata
            .getColumns(tableNamePattern = _name)
            .mapEach(ColumnsResults::data)
            .flatMap { existingColumns ->
                val extraColumns = ArrayList<String>()
                val missingColumns = mutableMapOf<String, Column<*, *, *>>()
                _cols.associateByTo(missingColumns) { it.name.lowercase() }

                val actions = mutableListOf<DBAction<DB, Any?>>()

                for (actualColumn in existingColumns) {
                    val colName = actualColumn.columnName
                    val colType = actualColumn.dataType
                    val col = column(colName)
                    missingColumns.remove(colName.lowercase())
                    if (col != null) {
                        if (!col.matches(actualColumn)) {
                            val action = GenericAction<DB, Int> { conn ->
                                try {
                                    conn.prepareStatement("ALTER TABLE $_name MODIFY COLUMN ${col.toDDL()}") {
                                        statement.executeUpdate()
                                    }
                                } catch (e: SQLException) {
                                    throw SQLException(
                                        "Failure updating table $_name column $colName from $colType to ${col.type}", e
                                    )
                                }
                            }
                            actions.add(action)
                        }
                    } else {
                        extraColumns.add(colName)
                    }
                }

                if (!retainExtraColumns) {
                    for (extraColumn in extraColumns) {
                        actions.add(GenericAction { conn ->
                            conn.prepareStatement("ALTER TABLE $_name DROP COLUMN `$extraColumn`") {
                                statement.executeUpdate()
                            }
                        })
                    }
                }
                for (missingColumn in missingColumns.values) {
                    actions.add(GenericAction{ conn ->
                        conn.prepareStatement("ALTER TABLE $_name ADD COLUMN ${missingColumn.toDDL()}") {
                            statement.executeUpdate()
                        }
                    })
                }

                actions
            }

    }

    override fun Table.createTransitive(
        ifNotExists: Boolean,
        pending: MutableSet<Table>
    ): Iterable<DBAction<DB, Any?>> {
        val neededTables = (_cols.asSequence().mapNotNull { col -> col.references?.table } +
                _foreignKeys.asSequence().mapNotNull(io.github.pdvrieze.kotlinsql.ddl.ForeignKey::toTable))
            .map { db[it._name] }
            .filter { it !in pending }
            .toList()

        val operations = mutableListOf<DBAction<DB, Any?>>()
        for (table in neededTables) {
            pending.add(table)
            operations.addAll(table.createTransitive(ifNotExists, pending))
        }
        pending.add(this)
        if (ifNotExists) {
            operations.add(CREATE_TABLE_IF_NOT_EXISTS)
        } else {
            operations.add(CREATE_TABLE)
        }

        return operations
    }

    override fun Table.dropTransitive(ifExists: Boolean): DBAction<DB, Unit> {
        fun tableReferencesThis(table: Table): Boolean {
            return table._foreignKeys.any { fk -> fk.toTable._name == _name }
        }

        return this@ConnectionSourceImplBase.or(!ifExists, hasTable(this)).flatMap {
            val actions = mutableListOf<DBAction<DB, Any?>>()

            db._tables
                .filter(::tableReferencesThis)
                .forEach {
                    actions.add(it.dropTransitive(true))
                }

            actions.add(GenericAction { conn ->
                conn.prepareStatement("DROP TABLE $_name") { statement.execute() }
            })

            actions
        }.map { Unit }
    }

    @Suppress("UNCHECKED_CAST")
    override val metadata: MonadicMetadata<DB>
        get() = metadataInstance as MonadicMetadata<DB>


    @Suppress("UNCHECKED_CAST")
    private val Table.CREATE_TABLE: DBAction<DB, Boolean>
        get() = GenericAction { conn -> // TODO use better action
            conn.prepareStatement(buildString { appendDDL(this) }) { statement.execute() }
        }


    @Suppress("UNCHECKED_CAST")
    private val Table.CREATE_TABLE_IF_NOT_EXISTS: DBAction<DB, List<Boolean>>
        get() {
            return hasTable(this).flatMap { hasTable ->
                when (hasTable) {
                    false -> listOf(GenericAction<DB, Boolean> { conn -> // TODO use better action
                        conn.prepareStatement(buildString { appendDDL(this) }) { statement.execute() }
                    })
                    else -> emptyList<DBAction<DB, Boolean>>()
                }
            }
        }

}

private val metadataInstance = MonadicMetadataImpl<Database>()

internal inline fun <DB: Database, R> ConnectionSource<DB>.use(action: MonadicDBConnection<DB>.() -> R):R {
    var doCommit = true
    val conn = MonadicDBConnection(datasource.connection, db)
    try {
        return conn.action()
    } catch (e: Exception) {
        try {
            conn.rawConnection.rollback()
        } finally {
            conn.close()
            doCommit = false
        }
        throw e
    } finally {
        if (doCommit) conn.rawConnection.commit()
        conn.close()
    }
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <DB : Database, R> DB.invoke(datasource: DataSource, noinline action: ConnectionSource<DB>.() -> R): R {
    return generatedInvoke(datasource, action)
}
