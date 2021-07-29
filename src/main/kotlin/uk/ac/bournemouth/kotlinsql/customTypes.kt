/*
 * Copyright (c) 2017.
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

package uk.ac.bournemouth.kotlinsql

import uk.ac.bournemouth.util.kotlin.sql.StatementHelper
import java.sql.ResultSet
import java.sql.Types
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Support for custom types that do automatic mapping
 */

inline fun <reified U : Any,
        T : Any,
        S : IColumnType<T, S, C>,
        C : Column<T, S, C>,
        CONF_T : ColumnConfiguration<T, S, C, CONF_T>>
        customType(createConfiguration: SqlTypesMixin.() -> CONF_T,
                   noinline toDB: (U) -> T,
                   noinline fromDb: (T) -> U) =
        CustomColumnType(U::class, SingleColumnType.createConfiguration(), toDB, fromDb)

@Suppress("MemberVisibilityCanBePrivate")
class CustomColumnType<U : Any,
        T : Any,
        S : IColumnType<T, S, C>,
        C : Column<T, S, C>,
        CONF_T : ColumnConfiguration<T, S, C, CONF_T>>(

        override val type: KClass<U>,
        val baseConfiguration: CONF_T,
        val toDB: (U) -> T,
        val fromDb: (T) -> U) : IColumnType<U, CustomColumnType<U, T, S, C, CONF_T>, CustomColumnType<U, T, S, C, CONF_T>.CustomColumn> {
    override val javaType: Int get() = Types.OTHER

    val baseColumnType = baseConfiguration.type

    inner class CustomColumn(table: TableRef, baseConfiguration: ColumnConfiguration<T, S, C, *>) :
            Column<U, CustomColumnType<U, T, S, C, CONF_T>, CustomColumnType<U, T, S, C, CONF_T>.CustomColumn> {

        val baseColumn: Column<T, S, C> = baseConfiguration.newColumn(table)

        override val table: TableRef get() = baseColumn.table
        override val name: String get() = baseColumn.name
        override val notnull: Boolean? get() = baseColumn.notnull

        override fun ref(): ColumnRef<U, CustomColumnType<U, T, S, C, CONF_T>, CustomColumnType<U, T, S, C, CONF_T>.CustomColumn> = this

        override val type get() = this@CustomColumnType

        override val unique: Boolean get() = baseColumn.unique
        override val autoincrement: Boolean get() = baseColumn.autoincrement

        override val default: U? get() = baseColumn.default?.let(fromDb)
        override val comment: String? get() = baseColumn.comment
        override val storageFormat get() = baseColumn.storageFormat
        override val columnFormat get() = baseColumn.columnFormat

        override val references get() = baseColumn.references

        override fun toDDL() = baseColumn.toDDL()

        @Suppress("UNCHECKED_CAST")
        override fun copyConfiguration(newName: String?, owner: Table) =
                CustomColumnConfiguration(baseColumn.copyConfiguration(newName, owner) as CONF_T, this@CustomColumnType)

        override fun toString(): String {
            return "Custom column $name type:$type, underlying: $baseColumn"
        }
    }

    override val typeName: String get() = baseColumnType.typeName

    override fun newConfiguration(refColumn: CustomColumn): ColumnConfiguration<U, CustomColumnType<U, T, S, C, CONF_T>, CustomColumn, *> = CustomColumnConfiguration(
            baseConfiguration.copy(newName = refColumn.name), this@CustomColumnType)

    override fun fromResultSet(rs: ResultSet, pos: Int): U? = baseColumnType.fromResultSet(rs, pos)?.let(fromDb)

    override fun setParam(statementHelper: StatementHelper, pos: Int, value: U?): Unit = baseColumnType.setParam(
            statementHelper, pos, value?.let(toDB))

    operator fun provideDelegate(thisRef: MutableTable,
                                 property: KProperty<*>): Table.FieldAccessor<U, CustomColumnType<U, T, S, C, CONF_T>, CustomColumnType<U, T, S, C, CONF_T>.CustomColumn> {
        return thisRef.add(CustomColumn(thisRef, baseConfiguration.copy(property.name)))
    }

    operator fun invoke(configurator: CONF_T.() -> Unit) = CustomColumnConfiguration(
            baseConfiguration.copy().apply(configurator), this@CustomColumnType)

    override fun toString(): String {
        return "Custom Type $typeName (${type.java.simpleName})"
    }
}

@Suppress("MemberVisibilityCanBePrivate")
class CustomColumnConfiguration<U : Any,
        T : Any,
        S : IColumnType<T, S, C>,
        C : Column<T, S, C>,
        CONF_T : ColumnConfiguration<T, S, C, CONF_T>>(val baseConfiguration: CONF_T,
                                                       override val type: CustomColumnType<U, T, S, C, CONF_T>) :
        ColumnConfiguration<U, CustomColumnType<U, T, S, C, CONF_T>, CustomColumnType<U, T, S, C, CONF_T>.CustomColumn, CustomColumnConfiguration<U, T, S, C, CONF_T>> {

    override fun newColumn(table: TableRef) = type.CustomColumn(table, baseConfiguration)

    override fun copy(newName: String?) = CustomColumnConfiguration(baseConfiguration.copy(newName), type)

    override var name: String?
        get() = baseConfiguration.name
        set(value) {
            baseConfiguration.name = value
        }

    override var notnull: Boolean?
        get() = baseConfiguration.notnull
        set(value) {
            baseConfiguration.notnull = value
        }

    override var unique: Boolean
        get() = baseConfiguration.unique
        set(value) {
            baseConfiguration.unique = value
        }

    override var autoincrement: Boolean
        get() = baseConfiguration.autoincrement
        set(value) {
            baseConfiguration.autoincrement = value
        }

    override var default: U?
        get() = baseConfiguration.default?.let { type.fromDb(it) }
        set(value) {
            baseConfiguration.default = value?.let { type.toDB(it) }
        }

    override var comment: String?
        get() = baseConfiguration.comment
        set(value) {
            baseConfiguration.comment = value
        }

    override var columnFormat: ColumnConfiguration.ColumnFormat?
        get() = baseConfiguration.columnFormat
        set(value) {
            baseConfiguration.columnFormat = value
        }

    override var storageFormat: ColumnConfiguration.StorageFormat?
        get() = baseConfiguration.storageFormat
        set(value) {
            baseConfiguration.storageFormat = value
        }

    override var references: ColsetRef?
        get() = baseConfiguration.references
        set(value) {
            baseConfiguration.references = value
        }
}

object SingleColumnType : SqlTypesMixin
