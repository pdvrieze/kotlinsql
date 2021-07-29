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

@file:Suppress("ClassName")

package uk.ac.bournemouth.kotlinsql

import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.AbstractCharColumnConfiguration.LengthCharColumnConfiguration
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.AbstractNumberColumnConfiguration.DecimalColumnConfiguration
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.LengthColumnConfiguration
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.NormalColumnConfiguration
import uk.ac.bournemouth.kotlinsql.ColumnType.*

/**
 * A base class for table declarations. Users of the code are expected to use this with a configuration closure to create
 * database tables. for typed columns to be available they need to be declared using `by <type>` or `by <name>`.
 *
 * A sample use is:
 * ```
 *    object peopleTable:[Mutable]("people", "ENGINE=InnoDB CHARSET=utf8") {
 *      val firstname by [VARCHAR]("firstname", 50)
 *      val surname by [VARCHAR]("familyname", 30) { NOT_NULL }
 *      val birthdate by [DATE]("birthdate")
 *
 *      override fun init() {
 *        [PRIMARY_KEY](firstname, familyname)
 *      }
 *    }
 * ```
 *
 * This class uses a small amount of reflection on construction to make the magic work.
 *
 * @property _cols The list of columns in the table.
 * @property _primaryKey The primary key of the table (if defined)
 * @property _foreignKeys The foreign keys defined on the table.
 * @property _uniqueKeys Unique keys / uniqueness constraints defined on the table
 * @property _indices Additional indices defined on the table
 * @property _extra Extra table configuration to be appended after the definition. This contains information such as the
 *                  engine or charset to use.
 */
// Note that the overloadResolver parameter on the primary constructor is there purely to fix overload resolving
@Suppress("NOTHING_TO_INLINE", "unused", "MemberVisibilityCanBePrivate", "FunctionName", "PropertyName",
          "PrivatePropertyName")
abstract class MutableTable private constructor(name: String?,
                                                override val _extra: String?, @Suppress(
                "UNUSED_PARAMETER") overloadResolver: Unit) : AbstractTable(), SqlTypesMixin {

    var isSealed: Boolean = false
        set(value) {
            if (field && !value) throw IllegalStateException("Unsealing tables is not legal")
        }

    constructor(extra: String? = null) : this(null, extra, Unit)
    constructor(name: String, extra: String?) : this(name, extra, Unit)

    override val _name: String = name ?: javaClass.simpleName

    override val _cols: List<Column<*, *, *>> = mutableListOf()

    private var __primaryKey: List<Column<*, *, *>>? = null
    override val _primaryKey: List<Column<*, *, *>>?
        get() {
            doInit; return __primaryKey
        }

    private val __foreignKeys = mutableListOf<ForeignKey>()
    override val _foreignKeys: List<ForeignKey>
        get() {
            doInit; return __foreignKeys
        }

    private val __uniqueKeys = mutableListOf<List<Column<*, *, *>>>()
    override val _uniqueKeys: List<List<Column<*, *, *>>>
        get() {
            doInit; return __uniqueKeys
        }

    private val __indices = mutableListOf<List<Column<*, *, *>>>()
    override val _indices: List<List<Column<*, *, *>>>
        get() {
            doInit; return __indices
        }

    // Using lazy takes the pain out of on-demand initialisation
    private val doInit by lazy {
        init()
        isSealed = true
    }

    abstract fun init()

    @PublishedApi
    internal inline fun <T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>>
            add(column: C): Table.FieldAccessor<T, S, C> {
        if (isSealed) throw IllegalStateException("The table is sealed")
        return name(column.name, column.apply { (_cols as MutableList<Column<*, *, *>>).add(this) }.type)
    }


    /* When there is no body, the configuration subtype does not matter */
    /**
     * Create column that has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     */
    protected inline fun <T : Any, S : ColumnType<T, S, C>, C : Column<T, S, C>> reference(other: C): ColumnConfiguration<T, S, C, *> {
        return other.copyConfiguration(owner = this)
    }

    /* When there is no body, the configuration subtype does not matter except for custom columns */
    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     */
    protected fun <U : Any,
            T : Any,
            S : IColumnType<T, S, C>,
            C : Column<T, S, C>,
            CONF_T : AbstractColumnConfiguration<T, S, C, CONF_T>> reference(other: CustomColumnType<U, T, S, C, CONF_T>.CustomColumn) = other.copyConfiguration(
            owner = this)

    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     */
    protected inline fun <T : Any, S : ColumnType<T, S, C>, C : Column<T, S, C>> reference(newName: String,
                                                                                           other: C): ColumnConfiguration<T, S, C, *> {
        return other.copyConfiguration(newName = newName, owner = this)
    }

    /* Otherwise, the various types need to be distinguished. The different subtypes of column are needed for overload resolution */
    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     * @param block The block to further configure the column
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <U : Any,
            T : Any,
            S : IColumnType<T, S, C>,
            C : Column<T, S, C>,
            CONF_T : AbstractColumnConfiguration<T, S, C, CONF_T>> reference(other: CustomColumnType<U, T, S, C, CONF_T>.CustomColumn,
                                                                             block: CONF_T.() -> Unit) = CustomColumnConfiguration(
            (other.baseColumn.copyConfiguration(null, this) as CONF_T).apply(block), other.type)

    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     * @param block The block to further configure the column
     */
    protected fun <S : DecimalColumnType<S>> reference(other: DecimalColumn<S>,
                                                       block: DecimalColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(
            null, this).apply(block)

    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     * @param block The block to further configure the column
     */
    protected fun <S : LengthCharColumnType<S>> reference(other: LengthCharColumn<S>,
                                                          block: LengthCharColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(
            null, this).apply(block)

    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     * @param block The block to further configure the column
     */
    protected fun <S : CharColumnType<S>> reference(other: CharColumn<S>,
                                                    block: CharColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(
            null, this).apply(block)

    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     * @param block The block to further configure the column
     */
    protected fun <T : Any, S : SimpleColumnType<T, S>> reference(other: SimpleColumn<T, S>,
                                                                  block: NormalColumnConfiguration<T, S>.() -> Unit) = other.copyConfiguration(
            null, this).apply(block)

    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     * @param block The block to further configure the column
     */
    protected fun <T : Any, S : LengthColumnType<T, S>> reference(other: LengthColumn<T, S>,
                                                                  block: LengthColumnConfiguration<T, S>.() -> Unit) = other.copyConfiguration(
            null, this).apply(block)

    /**
     * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
     * @param other The column whose type to copy
     * @param block The block to further configure the column
     */
    protected fun <T : Any, S : NumericColumnType<T, S>> reference(other: NumericColumn<T, S>,
                                                                   block: NumberColumnConfiguration<T, S>.() -> Unit) = other.copyConfiguration(
            null, this).apply(block)


    /* Otherwise, the various types need to be distinguished. The different subtypes of column are needed for overload resolution */
    @Suppress("UNCHECKED_CAST")
    protected fun <U : Any,
            T : Any,
            S : IColumnType<T, S, C>,
            C : Column<T, S, C>,
            CONF_T : AbstractColumnConfiguration<T, S, C, CONF_T>> reference(newName: String,
                                                                             other: CustomColumnType<U, T, S, C, CONF_T>.CustomColumn,
                                                                             block: CONF_T.() -> Unit) =
            CustomColumnConfiguration((other.baseColumn.copyConfiguration(newName, this) as CONF_T).apply(block), other.type)

    protected fun <S : DecimalColumnType<S>> reference(newName: String,
                                                       other: DecimalColumn<S>,
                                                       block: DecimalColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(
            newName, this).apply(block)

    protected fun <S : LengthCharColumnType<S>> reference(newName: String,
                                                          other: LengthCharColumn<S>,
                                                          block: LengthCharColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(
            newName, this).apply(block)

    protected fun <S : CharColumnType<S>> reference(newName: String,
                                                    other: CharColumn<S>,
                                                    block: CharColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(
            newName, this).apply(block)

    protected fun <T : Any, S : SimpleColumnType<T, S>> reference(newName: String,
                                                                  other: SimpleColumn<T, S>,
                                                                  block: NormalColumnConfiguration<T, S>.() -> Unit) = other.copyConfiguration(
            newName, this).apply(block)

    protected fun <T : Any, S : LengthColumnType<T, S>> reference(newName: String,
                                                                  other: LengthColumn<T, S>,
                                                                  block: LengthColumnConfiguration<T, S>.() -> Unit) = other.copyConfiguration(
            newName, this).apply(block)

    protected fun <T : Any, S : NumericColumnType<T, S>> reference(newName: String,
                                                                   other: NumericColumn<T, S>,
                                                                   block: NumberColumnConfiguration<T, S>.() -> Unit) = other.copyConfiguration(
            newName, this).apply(block)

    protected fun <C : Column<*, *, C>> INDEX(col1: ColumnRef<*, *, C>, vararg cols: ColumnRef<*, *, *>) {
        if (isSealed) throw IllegalStateException("The table is sealed")
        __indices.add(mutableListOf(resolve(col1)).apply {
            addAll(resolveAll(cols))
        })
    }

    private fun __resolve(col1: ColumnRef<*, *, *>, vararg cols: ColumnRef<*, *, *>): List<Column<*, *, *>> {
        val seq: Sequence<ColumnRef<*, *, *>> = sequenceOf(sequenceOf(col1), cols.asSequence()).flatten()
        return _cols.resolveAll(seq).toList()
    }

    protected fun <C : Column<*, *, C>> UNIQUE(col1: ColumnRef<*, *, C>, vararg cols: ColumnRef<*, *, *>) {
        __uniqueKeys.add(__resolve(col1, *cols))
    }

    protected fun PRIMARY_KEY(col1: ColumnRef<*, *, *>, vararg cols: ColumnRef<*, *, *>) {
        __primaryKey = __resolve(col1, *cols)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    class __FOREIGN_KEY__6<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>,
            T3 : Any, S3 : IColumnType<T3, S3, C3>, C3 : Column<T3, S3, C3>,
            T4 : Any, S4 : IColumnType<T4, S4, C4>, C4 : Column<T4, S4, C4>,
            T5 : Any, S5 : IColumnType<T5, S5, C5>, C5 : Column<T5, S5, C5>,
            T6 : Any, S6 : IColumnType<T6, S6, C6>, C6 : Column<T6, S6, C6>>(
            val table: MutableTable,
            val col1: ColumnRef<T1, S1, C1>,
            val col2: ColumnRef<T2, S2, C2>,
            val col3: ColumnRef<T3, S3, C3>,
            val col4: ColumnRef<T4, S4, C4>,
            val col5: ColumnRef<T5, S5, C5>,
            val col6: ColumnRef<T6, S6, C6>) {
        fun REFERENCES(ref1: ColumnRef<T1, S1, C1>,
                       ref2: ColumnRef<T2, S2, C2>,
                       ref3: ColumnRef<T3, S3, C3>,
                       ref4: ColumnRef<T4, S4, C4>,
                       ref5: ColumnRef<T5, S5, C5>,
                       ref6: ColumnRef<T6, S6, C6>) {
            (table.__foreignKeys).add(ForeignKey(listOf(col1, col2, col3, col4, col5, col6), ref1.table,
                                                 listOf(ref1, ref2, ref3, ref4, ref5, ref6).apply {
                                                     forEach {
                                                         require(it.table == ref1.table)
                                                     }
                                                 }))
        }
    }

    inline fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>,
            T3 : Any, S3 : IColumnType<T3, S3, C3>, C3 : Column<T3, S3, C3>,
            T4 : Any, S4 : IColumnType<T4, S4, C4>, C4 : Column<T4, S4, C4>,
            T5 : Any, S5 : IColumnType<T5, S5, C5>, C5 : Column<T5, S5, C5>,
            T6 : Any, S6 : IColumnType<T6, S6, C6>, C6 : Column<T6, S6, C6>> FOREIGN_KEY(
            col1: ColumnRef<T1, S1, C1>,
            col2: ColumnRef<T2, S2, C2>,
            col3: ColumnRef<T3, S3, C3>,
            col4: ColumnRef<T4, S4, C4>,
            col5: ColumnRef<T5, S5, C5>,
            col6: ColumnRef<T6, S6, C6>) =
            __FOREIGN_KEY__6(this, col1, col2, col3, col4, col5, col6)


    class __FOREIGN_KEY__5<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>,
            T3 : Any, S3 : IColumnType<T3, S3, C3>, C3 : Column<T3, S3, C3>,
            T4 : Any, S4 : IColumnType<T4, S4, C4>, C4 : Column<T4, S4, C4>,
            T5 : Any, S5 : IColumnType<T5, S5, C5>, C5 : Column<T5, S5, C5>>(
            val table: MutableTable,
            val col1: ColumnRef<T1, S1, C1>,
            val col2: ColumnRef<T2, S2, C2>,
            val col3: ColumnRef<T3, S3, C3>,
            val col4: ColumnRef<T4, S4, C4>,
            val col5: ColumnRef<T5, S5, C5>) {
        fun REFERENCES(ref1: ColumnRef<T1, S1, C1>,
                       ref2: ColumnRef<T2, S2, C2>,
                       ref3: ColumnRef<T3, S3, C3>,
                       ref4: ColumnRef<T4, S4, C4>,
                       ref5: ColumnRef<T5, S5, C5>) {
            (table.__foreignKeys).add(ForeignKey(listOf(col1, col2, col3, col4, col5), ref1.table,
                                                 listOf(ref1, ref2, ref3, ref4, ref5).apply {
                                                     forEach {
                                                         require(it.table == ref1.table)
                                                     }
                                                 }))
        }
    }

    inline fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>,
            T3 : Any, S3 : IColumnType<T3, S3, C3>, C3 : Column<T3, S3, C3>,
            T4 : Any, S4 : IColumnType<T4, S4, C4>, C4 : Column<T4, S4, C4>,
            T5 : Any, S5 : IColumnType<T5, S5, C5>, C5 : Column<T5, S5, C5>> FOREIGN_KEY(
            col1: ColumnRef<T1, S1, C1>,
            col2: ColumnRef<T2, S2, C2>,
            col3: ColumnRef<T3, S3, C3>,
            col4: ColumnRef<T4, S4, C4>,
            col5: ColumnRef<T5, S5, C5>) =
            __FOREIGN_KEY__5(this, col1, col2, col3, col4, col5)

    @Suppress("MemberVisibilityCanBePrivate")
    class __FOREIGN_KEY__4<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>,
            T3 : Any, S3 : IColumnType<T3, S3, C3>, C3 : Column<T3, S3, C3>,
            T4 : Any, S4 : IColumnType<T4, S4, C4>, C4 : Column<T4, S4, C4>>(
            val table: MutableTable,
            val col1: ColumnRef<T1, S1, C1>,
            val col2: ColumnRef<T2, S2, C2>,
            val col3: ColumnRef<T3, S3, C3>,
            val col4: ColumnRef<T4, S4, C4>) {
        fun REFERENCES(ref1: ColumnRef<T1, S1, C1>,
                       ref2: ColumnRef<T2, S2, C2>,
                       ref3: ColumnRef<T3, S3, C3>,
                       ref4: ColumnRef<T4, S4, C4>) {
            (table.__foreignKeys).add(
                    ForeignKey(listOf(col1, col2, col3, col4), ref1.table, listOf(ref1, ref2, ref3, ref4)))
        }
    }

    inline fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>,
            T3 : Any, S3 : IColumnType<T3, S3, C3>, C3 : Column<T3, S3, C3>,
            T4 : Any, S4 : IColumnType<T4, S4, C4>, C4 : Column<T4, S4, C4>> FOREIGN_KEY(
            col1: ColumnRef<T1, S1, C1>,
            col2: ColumnRef<T2, S2, C2>,
            col3: ColumnRef<T3, S3, C3>,
            col4: ColumnRef<T4, S4, C4>) =
            __FOREIGN_KEY__4(this, col1, col2, col3, col4)

    class __FOREIGN_KEY__3<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>,
            T3 : Any, S3 : IColumnType<T3, S3, C3>, C3 : Column<T3, S3, C3>>(
            val table: MutableTable,
            val col1: ColumnRef<T1, S1, C1>,
            val col2: ColumnRef<T2, S2, C2>,
            val col3: ColumnRef<T3, S3, C3>) {
        fun REFERENCES(ref1: ColumnRef<T1, S1, C1>,
                       ref2: ColumnRef<T2, S2, C2>,
                       ref3: ColumnRef<T3, S3, C3>) {
            (table.__foreignKeys).add(ForeignKey(listOf(col1, col2, col3), ref1.table, listOf(ref1, ref2, ref3).apply {
                forEach {
                    require(it.table == ref1.table)
                }
            }))
        }
    }

    inline fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>,
            T3 : Any, S3 : IColumnType<T3, S3, C3>, C3 : Column<T3, S3, C3>> FOREIGN_KEY(
            col1: ColumnRef<T1, S1, C1>,
            col2: ColumnRef<T2, S2, C2>,
            col3: ColumnRef<T3, S3, C3>) = __FOREIGN_KEY__3(this, col1, col2, col3)

    class __FOREIGN_KEY__2<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>>(
            val table: MutableTable,
            val col1: ColumnRef<T1, S1, C1>,
            val col2: ColumnRef<T2, S2, C2>) {
        fun REFERENCES(ref1: ColumnRef<T1, S1, C1>,
                       ref2: ColumnRef<T2, S2, C2>) {
            (table.__foreignKeys).add(ForeignKey(listOf(col1, col2), ref1.table,
                                                 listOf(ref1, ref2).apply { require(ref2.table == ref1.table) }))
        }
    }

    inline fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>,
            T2 : Any, S2 : IColumnType<T2, S2, C2>, C2 : Column<T2, S2, C2>> FOREIGN_KEY(col1: ColumnRef<T1, S1, C1>,
                                                                                         col2: ColumnRef<T2, S2, C2>) =
            __FOREIGN_KEY__2(this, col1, col2)

    class __FOREIGN_KEY__1<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>(val table: MutableTable,
                                                                                            val col1: ColumnRef<T1, S1, C1>) {
        fun REFERENCES(ref1: ColumnRef<T1, S1, C1>) {
            (table.__foreignKeys).add(ForeignKey(listOf(col1), ref1.table, listOf(ref1)))
        }
    }

    inline fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> FOREIGN_KEY(col1: ColumnRef<T1, S1, C1>) =
            __FOREIGN_KEY__1(this, col1)


    // @formatter:on
}
