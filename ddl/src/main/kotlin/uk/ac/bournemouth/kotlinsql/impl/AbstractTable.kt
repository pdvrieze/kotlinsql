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

    /**
     * Field accessor/delegate implementation that uses the property name.
     */
    open class TypeFieldAccessor<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>>(val type: IColumnType<T, S, C>) :
        Table.FieldAccessor<T, S, C> {
        private var value: C? = null
        open fun name(property: KProperty<*>) = property.name
        override operator fun getValue(thisRef: Table, property: KProperty<*>): C {
            if (value == null) {
                val field = thisRef.column(property.name)
                    ?: throw IllegalArgumentException("There is no field with the given name ${property.name}")
                value = type.cast(field)
            }
            return value!!
        }
    }

    /** Property delegator to access database columns by name and type. */
    @PublishedApi
    internal fun <T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>> name(
        name: String,
        type: IColumnType<T, S, C>,
    ) =
        NamedFieldAccessor(name, type)

    @PublishedApi
    internal class NamedFieldAccessor<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>>
    constructor(
        val name: String,
        type: IColumnType<T, S, C>,
    ) : TypeFieldAccessor<T, S, C>(type) {
        override fun name(property: KProperty<*>): String = this.name
    }

    override fun appendDDL(appendable: Appendable) {
        appendable.appendLine("CREATE TABLE `$_name` (")
        sequenceOf(_cols.asSequence().map { it.toDDL() },
                   _primaryKey?.let { sequenceOf(columnListToDDL("PRIMARY KEY", it)) },
                   _indices.asSequence().map { columnListToDDL("INDEX", it) },
                   _uniqueKeys.asSequence().map { columnListToDDL("UNIQUE", it) },
                   _foreignKeys.asSequence().map { it.toDDL() })
            .filterNotNull()
            .flatten()
            .joinTo(appendable, ",$LINE_SEPARATOR  ", "  ")
        appendable.appendLine().append(')')
        _extra?.let { appendable.append(' ').append(_extra) }
        appendable.append(';')
    }

}