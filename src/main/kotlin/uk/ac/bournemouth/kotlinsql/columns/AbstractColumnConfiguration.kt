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

@file:Suppress("unused", "PropertyName", "FunctionName")

package uk.ac.bournemouth.kotlinsql.columns

import uk.ac.bournemouth.kotlinsql.*
import uk.ac.bournemouth.kotlinsql.columns.impl.CharColumnImpl
import uk.ac.bournemouth.kotlinsql.ColumnConfiguration.ColumnFormat
import uk.ac.bournemouth.kotlinsql.ColumnConfiguration.StorageFormat
import uk.ac.bournemouth.kotlinsql.columns.impl.DecimalColumnImpl
import uk.ac.bournemouth.kotlinsql.columns.impl.LengthCharColumnImpl
import uk.ac.bournemouth.kotlinsql.columns.impl.LengthColumnImpl
import uk.ac.bournemouth.kotlinsql.columns.impl.NormalColumnImpl
import uk.ac.bournemouth.kotlinsql.columns.impl.NumberColumnImpl
import uk.ac.bournemouth.kotlinsql.columns.ColumnType.*
import java.math.BigDecimal

/**
 * Class to abstract the column configuration of a resultset
 */
@Suppress("unused")
abstract class AbstractColumnConfiguration<T : Any, S : IColumnType<T, S, C>, C : Column<T, S, C>, out CONF_T : Any> :
    ColumnConfiguration<T, S, C, CONF_T> {
    final override var name: String?
    final override val type: S
    final override var notnull: Boolean?
    final override var unique: Boolean
    final override var autoincrement: Boolean
    final override var default: T?
    final override var comment: String?
    final override var columnFormat: ColumnFormat?
    final override var storageFormat: StorageFormat?
    final override var references: ColsetRef?

    constructor(type: S, name: String? = null) {
        this.name = name
        this.type = type
        this.notnull = null
        this.unique = false
        this.autoincrement = false
        this.default = null
        this.comment = null
        this.columnFormat = null
        this.storageFormat = null
        this.references = null
    }

    constructor(orig: AbstractColumnConfiguration<T, S, C, CONF_T>, newName: String? = null) {
        name = newName
        type = orig.type
        notnull = orig.notnull
        unique = orig.unique
        autoincrement = orig.autoincrement
        default = orig.default
        comment = orig.comment
        columnFormat = orig.columnFormat
        storageFormat = orig.storageFormat
        references = orig.references
    }

    protected inline val effectiveName: String
        get() = name ?: throw NullPointerException("database columns must have names")

    class NormalColumnConfiguration<T : Any, S : SimpleColumnType<T, S>> :
        AbstractColumnConfiguration<T, S, SimpleColumn<T, S>, NormalColumnConfiguration<T, S>> {

        constructor(type: S, name: String? = null) : super(type, name)

        constructor(orig: NormalColumnConfiguration<T, S>, newName: String? = null) : super(orig, newName)

        override fun newColumn(table: TableRef): SimpleColumn<T, S> = NormalColumnImpl(table, effectiveName, this)

        override fun copy(newName: String?) = NormalColumnConfiguration(this, newName)
    }

    sealed class AbstractNumberColumnConfiguration<T : Any, S : INumericColumnType<T, S, C>, C : INumericColumn<T, S, C>, out CONF_T : Any> :
        AbstractColumnConfiguration<T, S, C, CONF_T> {
        constructor(type: S, name: String? = null) : super(type, name) {
            this.displayLength = -1
        }

        constructor(orig: AbstractNumberColumnConfiguration<T, S, C, CONF_T>, newName: String? = null) : super(
            orig, newName
        ) {
            unsigned = orig.unsigned
            zerofill = orig.zerofill
            displayLength = orig.displayLength
        }


        var unsigned: Boolean = false
        var zerofill: Boolean = false
        var displayLength: Int

        val UNSIGNED: Unit
            get() {
                unsigned = true
            }

        val ZEROFILL: Unit
            get() {
                unsigned = true
            }

        class NumberColumnConfiguration<T : Any, S : NumericColumnType<T, S>> :
            AbstractNumberColumnConfiguration<T, S, NumericColumn<T, S>, NumberColumnConfiguration<T, S>> {
            constructor(type: S, name: String? = null) : super(type, name)
            constructor(
                orig: NumberColumnConfiguration<T, S>,
                newName: String? = null
            ) : super(orig, newName)

            override fun newColumn(table: TableRef): NumericColumn<T, S> = NumberColumnImpl(table, effectiveName, this)

            override fun copy(newName: String?) = NumberColumnConfiguration(this, newName)
        }

        class DecimalColumnConfiguration<S : DecimalColumnType<S>> :
            AbstractNumberColumnConfiguration<BigDecimal, S, DecimalColumn<S>, DecimalColumnConfiguration<S>> {
            val precision: Int
            val scale: Int

            constructor(type: S, name: String? = null, precision: Int = defaultPrecision, scale: Int = defaultScale) :
                    super(type, name) {
                this.precision = precision
                this.scale = scale
            }

            constructor(orig: DecimalColumnConfiguration<S>, newName: String? = null) : super(orig, newName) {
                precision = orig.precision
                scale = orig.scale
            }

            override fun newColumn(table: TableRef): DecimalColumn<S> = DecimalColumnImpl(table, effectiveName, this)
            override fun copy(newName: String?) = DecimalColumnConfiguration(this, newName)

            companion object {
                const val defaultPrecision = 10
                const val defaultScale = 0
            }
        }

    }

    sealed class AbstractCharColumnConfiguration<S : ICharColumnType<S, C>, C : ICharColumn<S, C>, out CONF_T : Any> :
        AbstractColumnConfiguration<String, S, C, CONF_T> {
        var charset: String? = null

        var collation: String? = null
        var binary: Boolean = false

        constructor(type: S, name: String? = null) : super(type, name)
        constructor(orig: AbstractCharColumnConfiguration<S, C, CONF_T>, newName: String?) : super(orig, newName) {
            charset = orig.charset
            collation = orig.collation
            binary = orig.binary
        }


        val BINARY: Unit
            get() {
                binary = true
            }

        fun CHARACTER_SET(charset: String) {
            this.charset = charset
        }

        fun COLLATE(collation: String) {
            this.collation = collation
        }

        class CharColumnConfiguration<S : CharColumnType<S>> :
            AbstractCharColumnConfiguration<S, CharColumn<S>, CharColumnConfiguration<S>> {
            constructor(type: S, name: String? = null) : super(type, name)
            constructor(orig: CharColumnConfiguration<S>, newName: String? = null) : super(orig, newName)

            override fun newColumn(table: TableRef): CharColumn<S> = CharColumnImpl(table, effectiveName, this)
            override fun copy(newName: String?) = CharColumnConfiguration(this, newName)
        }

        class LengthCharColumnConfiguration<S : LengthCharColumnType<S>> :
            AbstractCharColumnConfiguration<S, LengthCharColumn<S>, LengthCharColumnConfiguration<S>>,
            BaseLengthColumnConfiguration<String, S, LengthCharColumn<S>> {
            override val length: Int

            constructor(type: S, name: String? = null, length: Int) : super(type, name) {
                this.length = length
            }

            constructor(orig: LengthCharColumnConfiguration<S>, newName: String? = null) : super(orig, newName) {
                this.length = orig.length
            }

            override fun newColumn(table: TableRef): LengthCharColumn<S> =
                LengthCharColumnImpl(table, effectiveName, this)

            override fun copy(newName: String?) = LengthCharColumnConfiguration(this, newName)
        }
    }

    interface BaseLengthColumnConfiguration<T : Any, S : ILengthColumnType<T, S, C>, C : ILengthColumn<T, S, C>> {
        val length: Int
    }

    class LengthColumnConfiguration<T : Any, S : LengthColumnType<T, S>> :
        AbstractColumnConfiguration<T, S, LengthColumn<T, S>, LengthColumnConfiguration<T, S>>,
        BaseLengthColumnConfiguration<T, S, LengthColumn<T, S>> {
        override val length: Int

        constructor(name: String? = null, type: S, length: Int) : super(type, name) {
            this.length = length
        }

        constructor(orig: LengthColumnConfiguration<T, S>, newName: String? = null) : super(orig, newName) {
            length = orig.length
        }

        override fun newColumn(table: TableRef): LengthColumn<T, S> = LengthColumnImpl(table, effectiveName, this)
        override fun copy(newName: String?) = LengthColumnConfiguration(this, newName)
    }
}
