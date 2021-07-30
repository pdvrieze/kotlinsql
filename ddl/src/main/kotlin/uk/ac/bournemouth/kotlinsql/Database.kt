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

package uk.ac.bournemouth.kotlinsql

import uk.ac.bournemouth.kotlinsql.columns.NumericColumnType.INT_T
import uk.ac.bournemouth.kotlinsql.columns.NumericColumn
import uk.ac.bournemouth.kotlinsql.columns.impl.CountColumn
import uk.ac.bournemouth.kotlinsql.impl.gen.DatabaseMethods
import uk.ac.bournemouth.kotlinsql.queries.*
import uk.ac.bournemouth.kotlinsql.queries.impl.*
import java.lang.reflect.*
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList
import kotlin.reflect.KProperty

/**
 * This is an abstract class that contains a set of database tables.
 *
 * @property _version The version of the database schema. This can in the future be used for updating
 * @property _tables The actual tables defined in the database
 */

@Suppress("unused")
abstract class Database constructor(@Suppress("MemberVisibilityCanBePrivate") val _version: Int) : DatabaseMethods {


    @Suppress("PropertyName")
    val _tables: List<Table> by lazy {
        val result = ArrayList<Table>()
        result.addAll(tablesFromObjects(this.javaClass))
        tablesFromProperties(this)
            .filter { table -> !result.any { table._name == it._name } }
            .forEach { result.add(it) }
        result
    }

    /**
     * Delegate function to be used to reference tables. Note that this requires the name of the property to match the name
     * of the table.
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T : ImmutableTable> ref(table: T) = TableDelegate(table)

    /**
     * Delegate function to be used to reference tables. This delegate allows for renaming, by removing the need for checking.
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T : ImmutableTable> rename(table: T) = TableDelegate(table, false)


    /**
     * Helper class that implements the actual delegation of table access.
     */
    protected class TableDelegate<out T : ImmutableTable>(
        private val table: T,
        private var needsCheck: Boolean = true,
    ) {
        operator fun getValue(thisRef: Database, property: KProperty<*>): T {
            if (needsCheck) {
                if (table._name != property.name) throw IllegalArgumentException(
                    "The table names do not match (${table._name}, ${property.name})")
                needsCheck = false
            }
            return table
        }
    }

    operator fun get(key: String): Table {
        return _tables.find { it._name == key } ?: throw NoSuchElementException("There is no table with the key $key")
    }

    override operator fun get(key: TableRef) = get(key._name)


    @Suppress("FunctionName")
    fun DELETE_FROM(table: Table): Delete = _Delete(table)

    @Suppress("FunctionName")
    fun UPDATE(config: _UpdateBuilder.() -> Unit): Update = _UpdateBuilder().apply(config).build()

    @Suppress("FunctionName")
    fun COUNT(col: ColumnRef<*, *, *>): NumericColumn<Int, INT_T> {
        return CountColumn(col)
    }

    @Suppress("FunctionName")
    fun SELECT(columns: List<Column<*, *, *>>): Select = _ListSelect(columns)

    companion object {

        private val Field.isStatic: Boolean inline get() = Modifier.isStatic(modifiers)

        private fun tablesFromObjects(container: Class<out Database>): List<Table> {
            return container.classes.asSequence()
                .mapNotNull { member -> member.fields.firstOrNull { field -> field.isStatic && field.name == "INSTANCE" } }
                .mapNotNull { field -> field.get(null) }
                .map { it as Table }
                .toList()
        }


        private fun tablesFromProperties(db: Database): List<Table> {

            fun isTable(method: Method): Boolean {
                return method.parameterCount == 0 &&
                        method.name.startsWith("get") &&
                        Table::class.java.isAssignableFrom(method.returnType)
            }

            return db.javaClass.declaredMethods.asSequence()
                .filter(::isTable)
                .map { method -> (method.invoke(db)) as Table }
                .toList()
        }
    }

}
