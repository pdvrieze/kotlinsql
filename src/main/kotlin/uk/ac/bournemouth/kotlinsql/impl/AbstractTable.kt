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

package uk.ac.bournemouth.kotlinsql.impl

import uk.ac.bournemouth.kotlinsql.*
import uk.ac.bournemouth.kotlinsql.sql.DBAction
import uk.ac.bournemouth.kotlinsql.sql.EvaluatableDBTransaction
import uk.ac.bournemouth.kotlinsql.monadic.EmptyDBTransaction
import uk.ac.bournemouth.kotlinsql.sql.hasTable
import java.sql.SQLException
import java.util.*
import kotlin.reflect.KProperty

abstract class AbstractTable : Table {

    @Suppress("MemberVisibilityCanBePrivate")
    companion object {

        fun List<Column<*, *, *>>.resolve(ref: ColumnRef<*, *, *>) = find { it.name == ref.name }
            ?: throw NoSuchElementException(
                "No column with the name ${ref.name} could be found"
            )

        fun List<Column<*, *, *>>.resolveAll(refs: List<ColumnRef<*, *, *>>) = refs.map { resolve(it) }

        fun List<Column<*, *, *>>.resolveAll(refs: Array<out ColumnRef<*, *, *>>) = refs.map { resolve(it) }

        fun List<Column<*, *, *>>.resolveAll(refs: Sequence<ColumnRef<*, *, *>>) = refs.map { resolve(it) }

    }

    override fun resolve(ref: ColumnRef<*, *, *>): Column<*, *, *> = (_cols.find { it.name == ref.name })!!

    override fun ref(): TableRef = TableRefImpl(_name)

    override fun column(name: String) = name.toLowerCase(Locale.ENGLISH).let { nameLc ->
        _cols.firstOrNull { it.name.toLowerCase(Locale.ENGLISH) == nameLc }
    }

    operator fun getValue(thisRef: ImmutableTable, property: KProperty<*>): Column<*, *, *> {
        return column(property.name)!!
    }

    open class TypeFieldAccessor<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>>(val type: IColumnType<T, S, C>) :
        Table.FieldAccessor<T, S, C> {
        private var value: C? = null
        open fun name(property: KProperty<*>) = property.name
        override operator fun getValue(thisRef: Table, property: KProperty<*>): C {
            if (value == null) {
                val field = thisRef.column(property.name) ?: throw IllegalArgumentException(
                    "There is no field with the given name ${property.name}"
                )
                value = type.cast(field)
            }
            return value!!
        }
    }

    /** Property delegator to access database columns by name and type. */
    @PublishedApi
    internal fun <T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>> name(
        name: String,
        type: IColumnType<T, S, C>
    ) =
        NamedFieldAccessor(name, type)

    @PublishedApi
    internal class NamedFieldAccessor<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>>(
        val name: String,
        type: IColumnType<T, S, C>
    ) : TypeFieldAccessor<T, S, C>(
        type
    ) {
        override fun name(property: KProperty<*>): String = this.name
    }

    override fun appendDDL(appendable: Appendable) {
        appendable.appendLine("CREATE TABLE `$_name` (")
        sequenceOf(_cols.asSequence().map { it.toDDL() },
                   _primaryKey?.let { sequenceOf(toDDL("PRIMARY KEY", it)) },
                   _indices.asSequence().map { toDDL("INDEX", it) },
                   _uniqueKeys.asSequence().map { toDDL("UNIQUE", it) },
                   _foreignKeys.asSequence().map { it.toDDL() })
            .filterNotNull()
            .flatten()
            .joinTo(appendable, ",$LINE_SEPARATOR  ", "  ")
        appendable.appendLine().append(')')
        _extra?.let { appendable.append(' ').append(_extra) }
        appendable.append(';')
    }

    override fun <DB : Database> DB.createTransitive(
        ifNotExists: Boolean,
        pending: MutableCollection<Table>
    ): Iterable<DBAction<DB, Unit, Unit>> {
        val tableNames = (_cols.asSequence().mapNotNull { col -> col.references?.table } +
                _foreignKeys.asSequence().mapNotNull(ForeignKey::toTable)).toList()
        val neededTables = tableNames
            .map { this[it._name] }
            .filter { it !in pending }
            .toSet()

        val operations = mutableListOf<DBAction<DB, Unit, Unit>>()
        for (table in neededTables) {
            pending.add(table)
            operations.addAll(table.run { createTransitive(true, pending) })
        }
        pending.add(this@AbstractTable)
        if (ifNotExists) {
            operations.add(CREATE_TABLE_IF_NOT_EXISTS)
        } else {
            operations.add(CREATE_TABLE)
        }

        return operations
    }

    private val _CREATE_TABLE: DBAction<Database, Unit, Unit> = {
        connection.prepareStatement(buildString { appendDDL(this) }) {
            execute()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private val <DB : Database> DB.CREATE_TABLE: DBAction<DB, Unit, Unit>
        get() = _CREATE_TABLE as DBAction<DB, Unit, Unit>

    private val _CREATE_TABLE_IF_NOT_EXISTS: DBAction<Database, Unit, Unit> = {
        if (!hasTable(this@AbstractTable)) {
            connection.prepareStatement(buildString { appendDDL(this) }) {
                execute()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private val <DB : Database> DB.CREATE_TABLE_IF_NOT_EXISTS: DBAction<DB, Unit, Unit>
        get() = _CREATE_TABLE_IF_NOT_EXISTS as DBAction<DB, Unit, Unit>

    override fun <DB : Database> EmptyDBTransaction<DB, *>.createTransitiveOld(
        ifNotExists: Boolean,
        pending: MutableCollection<Table>
    ): EvaluatableDBTransaction<DB, Unit> = map {
        val tableNames = (_cols.asSequence().mapNotNull { col -> col.references?.table } +
                _foreignKeys.asSequence().mapNotNull(ForeignKey::toTable)).toList()
        val neededTables = tableNames
            .map { db[it._name] }
            .filter { it !in pending }
            .toSet()
        for (table in neededTables) {
            if (!ifNotExists || !hasTable(this@AbstractTable)) { // Make sure to check first to prevent loops
                pending.add(table)
                table.run { createTransitiveOld(true, pending).get() }
            }
        }
        connection.prepareStatement(buildString { appendDDL(this) }) {
            execute()
        }
        Unit
    }

    override fun <DB : Database> EmptyDBTransaction<DB, *>.dropTransitive(ifExists: Boolean): EvaluatableDBTransaction<DB, Unit> =
        map {
            fun tableReferencesThis(table: Table): Boolean {
                return table._foreignKeys.any { fk -> fk.toTable._name == _name }
            }
            if (!ifExists || hasTable(this@AbstractTable)) {
                // Create all tables this one depends on
                db._tables
                    .filter(::tableReferencesThis)
                    .forEach { it.apply { dropTransitive(true).evaluateNow() } }

                connection.prepareStatement("DROP TABLE $_name") { execute() }
            }
        }

    override fun <DB : Database> EmptyDBTransaction<DB, *>.ensureTable(retainExtraColumns: Boolean): EvaluatableDBTransaction<DB, Unit> =
        map {
            val extraColumns = ArrayList<String>()
            val missingColumns = mutableMapOf<String, Column<*, *, *>>()
            _cols.associateByTo(missingColumns) { it.name.toLowerCase(Locale.ENGLISH) }

            withMetaData {
                getColumns(null, null, _name, null).use { rs ->
                    while (rs.next()) {
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
                                    connection.prepareStatement("ALTER TABLE $_name MODIFY COLUMN ${col.toDDL()}") {
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
            }

            if (!retainExtraColumns) {
                for (extraColumn in extraColumns) {
                    connection.prepareStatement("ALTER TABLE $_name DROP COLUMN `$extraColumn`") {
                        executeUpdate()
                    }
                }
            }

            for (missingColumn in missingColumns.values) {
                connection.prepareStatement("ALTER TABLE $_name ADD COLUMN ${missingColumn.toDDL()}") {
                    executeUpdate()
                }
            }

        }

}