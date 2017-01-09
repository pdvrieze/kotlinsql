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

package uk.ac.bournemouth.kotlinsql

import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.*
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.AbstractCharColumnConfiguration.LengthCharColumnConfiguration
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.AbstractNumberColumnConfiguration.DecimalColumnConfiguration
import uk.ac.bournemouth.kotlinsql.AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration
import uk.ac.bournemouth.kotlinsql.ColumnType.*
import uk.ac.bournemouth.kotlinsql.ColumnType.CharColumnType.*
import uk.ac.bournemouth.kotlinsql.ColumnType.DecimalColumnType.DECIMAL_T
import uk.ac.bournemouth.kotlinsql.ColumnType.DecimalColumnType.NUMERIC_T
import uk.ac.bournemouth.kotlinsql.ColumnType.LengthCharColumnType.CHAR_T
import uk.ac.bournemouth.kotlinsql.ColumnType.LengthCharColumnType.VARCHAR_T
import uk.ac.bournemouth.kotlinsql.ColumnType.LengthColumnType.*
import uk.ac.bournemouth.kotlinsql.ColumnType.NumericColumnType.*
import uk.ac.bournemouth.kotlinsql.ColumnType.SimpleColumnType.*
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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
@Suppress("NOTHING_TO_INLINE", "unused")
abstract class MutableTable private constructor(name: String?,
                                                override val _extra: String?, @Suppress("UNUSED_PARAMETER") overloadResolver:Unit) : AbstractTable() {

  constructor(extra:String?=null): this(null, extra, Unit)
  constructor(name:String, extra: String?): this(name, extra, Unit)

  override val _name:String = name ?: javaClass.simpleName

  override val _cols: List<Column<*,*,*>> = mutableListOf()
  
  private var __primaryKey: List<Column<*,*,*>>? = null
  override val _primaryKey: List<Column<*,*,*>>?
    get() { doInit; return __primaryKey }
  
  private val __foreignKeys = mutableListOf<ForeignKey>()
  override val _foreignKeys: List<ForeignKey>
    get() { doInit; return __foreignKeys }

  private val __uniqueKeys= mutableListOf<List<Column<*,*,*>>>()
  override val _uniqueKeys: List<List<Column<*,*,*>>>
    get() { doInit; return __uniqueKeys }

  private val __indices = mutableListOf<List<Column<*,*,*>>>()
  override val _indices: List<List<Column<*,*,*>>>
    get() { doInit; return __indices }

  // Using lazy takes the pain out of on-demand initialisation
  private val doInit by lazy {
    init()
  }

  abstract fun init()

  @PublishedApi
  internal inline fun <T :Any, S: IColumnType<T,S,C>, C :Column<T,S,C>>
        add(column:C): Table.FieldAccessor<T,S, C> {
    return column.apply { (_cols as MutableList<Column<*,*,*>>).add(this)}.let{ name(column.name, it.type) }
  }

  // @formatter:off
  protected fun BIT(name:String?=null, block: NormalColumnConfiguration<Boolean, BIT_T>.() -> Unit) = NormalColumnConfiguration( this, name, BIT_T).apply(block)
  protected inline fun BIT(length: Int, noinline block: BaseLengthColumnConfiguration<BooleanArray, BITFIELD_T, LengthColumn<BooleanArray, BITFIELD_T>>.() -> Unit) = BIT(name=null, length = length, block = block)
  protected fun BIT(name:String?=null, length: Int, block: BaseLengthColumnConfiguration<BooleanArray, BITFIELD_T, LengthColumn<BooleanArray, BITFIELD_T>>.() -> Unit) = LengthColumnConfiguration( this, name, BITFIELD_T, length).apply(block)
  protected fun TINYINT(name:String?=null, block: NumberColumnConfiguration<Byte, TINYINT_T>.() -> Unit) = NumberColumnConfiguration( this, name, TINYINT_T).apply(block)
  protected fun SMALLINT(name:String?=null, block: NumberColumnConfiguration<Short, SMALLINT_T>.() -> Unit) = NumberColumnConfiguration( this, name, SMALLINT_T).apply(block)
  protected fun MEDIUMINT(name:String?=null, block: NumberColumnConfiguration<Int, MEDIUMINT_T>.() -> Unit) = NumberColumnConfiguration( this, name, MEDIUMINT_T).apply(block)
  protected fun INT(name:String?=null, block: NumberColumnConfiguration<Int, INT_T>.() -> Unit) = NumberColumnConfiguration( this, name, INT_T).apply(block)
  protected fun BIGINT(name:String?=null, block: NumberColumnConfiguration<Long, BIGINT_T>.() -> Unit) = NumberColumnConfiguration( this, name, BIGINT_T).apply(block)
  protected fun FLOAT(name:String?=null, block: NumberColumnConfiguration<Float, FLOAT_T>.() -> Unit) = NumberColumnConfiguration( this, name, FLOAT_T).apply(block)
  protected fun DOUBLE(name:String?=null, block: NumberColumnConfiguration<Double, DOUBLE_T>.() -> Unit) = NumberColumnConfiguration( this, name, DOUBLE_T).apply(block)
  protected inline fun DECIMAL(precision: Int = -1, scale: Int = -1, noinline block: DecimalColumnConfiguration<DECIMAL_T>.() -> Unit) = DECIMAL(name=null, precision=precision, scale=scale, block=block)
  protected fun DECIMAL(name:String?=null, precision: Int = -1, scale: Int = -1, block: DecimalColumnConfiguration<DECIMAL_T>.() -> Unit) = DecimalColumnConfiguration( this, name, DECIMAL_T, precision, scale).apply(block)
  protected inline fun NUMERIC(precision: Int = -1, scale: Int = -1, noinline block: DecimalColumnConfiguration<NUMERIC_T>.() -> Unit) = NUMERIC(name=null, precision = precision, scale=scale, block = block)
  protected fun NUMERIC(name:String?=null, precision: Int = -1, scale: Int = -1, block: DecimalColumnConfiguration<NUMERIC_T>.() -> Unit) = DecimalColumnConfiguration( this, name, NUMERIC_T, precision, scale).apply(block)
  protected fun DATE(name:String?=null, block: NormalColumnConfiguration<Date, DATE_T>.() -> Unit) = NormalColumnConfiguration( this, name, DATE_T).apply(block)
  protected fun TIME(name:String?=null, block: NormalColumnConfiguration<Time, TIME_T>.() -> Unit) = NormalColumnConfiguration( this, name, TIME_T).apply(block)
  protected fun TIMESTAMP(name:String?=null, block: NormalColumnConfiguration<Timestamp, TIMESTAMP_T>.() -> Unit) = NormalColumnConfiguration( this, name, TIMESTAMP_T).apply(block)
  protected fun DATETIME(name:String?=null, block: NormalColumnConfiguration<Timestamp, DATETIME_T>.() -> Unit) = NormalColumnConfiguration( this, name, DATETIME_T).apply(block)
  protected fun YEAR(name:String?=null, block: NormalColumnConfiguration<Date, YEAR_T>.() -> Unit) = NormalColumnConfiguration( this, name, YEAR_T).apply(block)
  protected inline fun CHAR(length: Int = -1, noinline block: LengthCharColumnConfiguration<CHAR_T>.() -> Unit) = CHAR(name=null, length=length, block = block)
  protected fun CHAR(name:String?=null, length: Int = -1, block: LengthCharColumnConfiguration<CHAR_T>.() -> Unit) = LengthCharColumnConfiguration( this, name, CHAR_T, length).apply(block)
  protected inline fun VARCHAR(length: Int, noinline block: LengthCharColumnConfiguration<VARCHAR_T>.() -> Unit) = VARCHAR(name=null, length=length, block=block)
  protected fun VARCHAR(name:String?=null, length: Int, block: LengthCharColumnConfiguration<VARCHAR_T>.() -> Unit) = LengthCharColumnConfiguration( this, name, VARCHAR_T, length).apply(block)
  protected inline fun BINARY(length: Int, noinline block: BaseLengthColumnConfiguration<ByteArray, BINARY_T, LengthColumn<ByteArray, BINARY_T>>.() -> Unit) = BINARY(name=null, length=length, block=block)
  protected fun BINARY(name:String?=null, length: Int, block: BaseLengthColumnConfiguration<ByteArray, BINARY_T, LengthColumn<ByteArray, BINARY_T>>.() -> Unit) = LengthColumnConfiguration( this, name, BINARY_T, length).apply(block)
  protected inline fun VARBINARY(length: Int, noinline block: BaseLengthColumnConfiguration<ByteArray, VARBINARY_T, LengthColumn<ByteArray, VARBINARY_T>>.() -> Unit) = VARBINARY(name=null, length=length, block=block)
  protected fun VARBINARY(name:String?=null, length: Int, block: BaseLengthColumnConfiguration<ByteArray, VARBINARY_T, LengthColumn<ByteArray, VARBINARY_T>>.() -> Unit) = LengthColumnConfiguration( this, name, VARBINARY_T, length).apply(block)
  protected fun TINYBLOB(name:String?=null, block: NormalColumnConfiguration<ByteArray, TINYBLOB_T>.() -> Unit) = NormalColumnConfiguration( this, name, TINYBLOB_T).apply(block)
  protected fun BLOB(name:String?=null, block: NormalColumnConfiguration<ByteArray, BLOB_T>.() -> Unit) = NormalColumnConfiguration( this, name, BLOB_T).apply(block)
  protected fun MEDIUMBLOB(name:String?=null, block: NormalColumnConfiguration<ByteArray, MEDIUMBLOB_T>.() -> Unit) = NormalColumnConfiguration( this, name, MEDIUMBLOB_T).apply(block)
  protected fun LONGBLOB(name:String?=null, block: NormalColumnConfiguration<ByteArray, LONGBLOB_T>.() -> Unit) = NormalColumnConfiguration( this, name, LONGBLOB_T).apply(block)
  protected fun TINYTEXT(name:String?=null, block: CharColumnConfiguration<TINYTEXT_T>.() -> Unit) = CharColumnConfiguration( this, name, TINYTEXT_T).apply(block)
  protected fun TEXT(name:String?=null, block: CharColumnConfiguration<TEXT_T>.() -> Unit) = CharColumnConfiguration( this, name, TEXT_T).apply(block)
  protected fun MEDIUMTEXT(name:String?=null, block: CharColumnConfiguration<MEDIUMTEXT_T>.() -> Unit) = CharColumnConfiguration( this, name, MEDIUMTEXT_T).apply(block)
  protected fun LONGTEXT(name:String?=null, block: CharColumnConfiguration<LONGTEXT_T>.() -> Unit) = CharColumnConfiguration( this, name, LONGTEXT_T).apply(block)

  /* Versions without configuration closure */
  protected fun BIT(name:String?=null) = NormalColumnConfiguration(this, name, BIT_T)
  protected inline fun BIT(length:Int) = BIT(name=null, length = length)
  protected fun BIT(name:String?=null, length:Int) = LengthColumnConfiguration(this, name, BITFIELD_T, length)
  protected fun TINYINT(name:String?=null) = NumberColumnConfiguration(this, name, TINYINT_T)
  protected fun SMALLINT(name:String?=null) = NumberColumnConfiguration(this, name, SMALLINT_T)
  protected fun MEDIUMINT(name:String?=null) = NumberColumnConfiguration(this, name, MEDIUMINT_T)
  protected fun INT(name:String?=null) = NumberColumnConfiguration(this, name, INT_T)
  protected fun BIGINT(name:String?=null) = NumberColumnConfiguration(this, name, BIGINT_T)
  protected fun FLOAT(name:String?=null) = NumberColumnConfiguration(this, name, FLOAT_T)
  protected fun DOUBLE(name:String?=null) = NumberColumnConfiguration(this, name, DOUBLE_T)
  protected inline fun DECIMAL(precision:Int=-1, scale:Int=-1) = DECIMAL(name = null, precision = precision, scale = scale)
  protected fun DECIMAL(name:String?=null, precision:Int=-1, scale:Int=-1) = DecimalColumnConfiguration(this, name, DECIMAL_T, precision, scale)
  protected inline fun NUMERIC(precision: Int = -1, scale: Int = -1) = NUMERIC(name = null, precision = precision, scale = scale)
  protected fun NUMERIC(name:String?=null, precision: Int = -1, scale: Int = -1) = DecimalColumnConfiguration(this, name, NUMERIC_T, precision, scale)
  protected fun DATE(name:String?=null) = NormalColumnConfiguration(this, name, DATE_T)
  protected fun TIME(name:String?=null) = NormalColumnConfiguration(this, name, TIME_T)
  protected fun TIMESTAMP(name:String?=null) = NormalColumnConfiguration(this, name, TIMESTAMP_T)
  protected fun DATETIME(name:String?=null) = NormalColumnConfiguration(this, name, DATETIME_T)
  protected fun YEAR(name:String?=null) = NormalColumnConfiguration(this, name, YEAR_T)
  protected inline fun CHAR(length: Int = -1) = CHAR(name=null, length=length)
  protected fun CHAR(name:String?=null, length: Int = -1) = LengthCharColumnConfiguration(this, name, CHAR_T, length)
  protected inline fun VARCHAR(length: Int = -1) = VARCHAR(name=null, length=length)
  protected fun VARCHAR(name:String?=null, length: Int) = LengthCharColumnConfiguration(this, name, VARCHAR_T, length)

  protected inline fun BINARY(length: Int = -1) = BINARY(name=null, length=length)
  protected fun BINARY(name:String?=null, length: Int) = LengthColumnConfiguration(this, name, BINARY_T, length)
  protected inline fun VARBINARY(length: Int = -1) = VARBINARY(name=null, length=length)
  protected fun VARBINARY(name:String?=null, length: Int) = LengthColumnConfiguration(this, name, VARBINARY_T, length)
  protected fun TINYBLOB(name:String?=null) = NormalColumnConfiguration(this, name, TINYBLOB_T)
  protected fun BLOB(name:String?=null) = NormalColumnConfiguration(this, name, BLOB_T)
  protected fun MEDIUMBLOB(name:String?=null) = NormalColumnConfiguration(this, name, MEDIUMBLOB_T)
  protected fun LONGBLOB(name:String?=null) = NormalColumnConfiguration(this, name, LONGBLOB_T)
  protected fun TINYTEXT(name:String?=null) = CharColumnConfiguration(this, name, TINYTEXT_T)
  protected fun TEXT(name:String?=null) = CharColumnConfiguration(this, name, TEXT_T)
  protected fun MEDIUMTEXT(name:String?=null) = CharColumnConfiguration(this, name, MEDIUMTEXT_T)
  protected fun LONGTEXT(name:String?=null) = CharColumnConfiguration(this, name, LONGTEXT_T)

  /* When there is no body, the configuration subtype does not matter */
  /**
   * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
   * @param other The column whose type to copy
   */
  protected fun <T:Any, S:ColumnType<T,S,C>, C:Column<T,S,C>>reference(other: C): Table.FieldAccessor<T,S,C> {
    return add(other.copyConfiguration(owner = this).newColumn())
  }

  /**
   * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
   * @param other The column whose type to copy
   */
  protected fun <T:Any, S:ColumnType<T,S,C>, C:Column<T,S,C>>reference(newName:String?=null, other: C): Table.FieldAccessor<T,S,C> {
    return add(other.copyConfiguration(newName = newName, owner = this).newColumn())
  }

  /* Otherwise, the various types need to be distinguished. The different subtypes of column are needed for overload resolution */

  /**
   * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
   * @param other The column whose type to copy
   * @param block The block to further configure the column
   */
  protected fun <S:DecimalColumnType<S>>reference(other: DecimalColumn<S>, block: DecimalColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(null, this).apply(block)
  /**
   * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
   * @param other The column whose type to copy
   * @param block The block to further configure the column
   */
  protected fun <S:LengthCharColumnType<S>>reference(other: LengthCharColumn<S>, block: LengthCharColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(null, this).apply(block)
  /**
   * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
   * @param other The column whose type to copy
   * @param block The block to further configure the column
   */
  protected fun <S:CharColumnType<S>>reference(other: CharColumn<S>, block: CharColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(null, this).apply(block)
  /**
   * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
   * @param other The column whose type to copy
   * @param block The block to further configure the column
   */
  protected fun <T:Any, S:SimpleColumnType<T,S>>reference(other: SimpleColumn<T,S>, block: NormalColumnConfiguration<T,S>.() -> Unit) = other.copyConfiguration(null, this).apply(block)
  /**
   * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
   * @param other The column whose type to copy
   * @param block The block to further configure the column
   */
  protected fun <T:Any, S:LengthColumnType<T,S>>reference(other: LengthColumn<T,S>, block: LengthColumnConfiguration<T,S>.() -> Unit) = other.copyConfiguration(null, this).apply(block)
  /**
   * Create column tht has the type of the referred to column. This does NOT copy block configuration such as AUTOINCREMENT or NULL-ability.
   * @param other The column whose type to copy
   * @param block The block to further configure the column
   */
  protected fun <T:Any, S:NumericColumnType<T,S>>reference(other: NumericColumn<T,S>, block: NumberColumnConfiguration<T,S>.() -> Unit) = other.copyConfiguration(null, this).apply(block)


  /* Otherwise, the various types need to be distinguished. The different subtypes of column are needed for overload resolution */
  protected fun <S:DecimalColumnType<S>>reference(newName:String, other: DecimalColumn<S>, block: DecimalColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(newName, this).apply(block)
  protected fun <S:LengthCharColumnType<S>>reference(newName:String, other: LengthCharColumn<S>, block: LengthCharColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(newName, this).apply(block)
  protected fun <S:CharColumnType<S>>reference(newName:String, other: CharColumn<S>, block: CharColumnConfiguration<S>.() -> Unit) = other.copyConfiguration(newName, this).apply(block)
  protected fun <T:Any, S:SimpleColumnType<T,S>>reference(newName:String, other: SimpleColumn<T,S>, block: NormalColumnConfiguration<T,S>.() -> Unit) = other.copyConfiguration(newName, this).apply(block)
  protected fun <T:Any, S:LengthColumnType<T,S>>reference(newName:String, other: LengthColumn<T,S>, block: LengthColumnConfiguration<T,S>.() -> Unit) = other.copyConfiguration(newName, this).apply(block)
  protected fun <T:Any, S:NumericColumnType<T,S>>reference(newName:String, other: NumericColumn<T,S>, block: NumberColumnConfiguration<T,S>.() -> Unit) = other.copyConfiguration(newName, this).apply(block)

  protected fun <C:Column<*,*,C>> INDEX(col1: ColumnRef<*,*,C>, vararg cols: ColumnRef<*,*,*>) {
    __indices.add(mutableListOf(resolve(col1)).apply {
        addAll(resolveAll(cols))
    })
  }

  private fun __resolve(col1:ColumnRef<*,*,*>, vararg cols: ColumnRef<*,*,*>):List<Column<*,*,*>> {
    val seq: Sequence<ColumnRef<*,*,*>> = sequenceOf(sequenceOf(col1),cols.asSequence()).flatten()
    return _cols.resolveAll(seq).toList()
  }

  protected fun <C:Column<*,*,C>> UNIQUE(col1: ColumnRef<*,*,C>, vararg cols: ColumnRef<*,*,*>) { __uniqueKeys.add(__resolve(col1, *cols))}
  protected fun PRIMARY_KEY(col1: ColumnRef<*,*,*>, vararg cols: ColumnRef<*,*,*>) { __primaryKey = __resolve(col1, *cols)}

  class __FOREIGN_KEY__6<T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>,
                         T3:Any, S3: IColumnType<T3,S3, C3>, C3: Column<T3,S3,C3>,
                         T4:Any, S4: IColumnType<T4,S4, C4>, C4: Column<T4,S4,C4>,
                         T5:Any, S5: IColumnType<T5,S5, C5>, C5: Column<T5,S5,C5>,
                         T6:Any, S6: IColumnType<T6,S6, C6>, C6: Column<T6,S6,C6>>(
        val table: MutableTable,
        val col1:ColumnRef<T1,S1,C1>,
        val col2:ColumnRef<T2,S2,C2>,
        val col3:ColumnRef<T3,S3,C3>,
        val col4:ColumnRef<T4,S4,C4>,
        val col5:ColumnRef<T5,S5,C5>,
        val col6:ColumnRef<T6,S6,C6>) {
    fun REFERENCES(ref1:ColumnRef<T1,S1,C1>,
                   ref2:ColumnRef<T2,S2,C2>,
                   ref3:ColumnRef<T3,S3,C3>,
                   ref4:ColumnRef<T4,S4,C4>,
                   ref5:ColumnRef<T5,S5,C5>,
                   ref6:ColumnRef<T6,S6,C6>) {
      (table.__foreignKeys).add(ForeignKey(listOf(col1, col2, col3, col4, col5, col6), ref1.table, listOf(ref1, ref2, ref3, ref4, ref5, ref6).apply { forEach { require(it.table==ref1.table) } }))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>,
              T3:Any, S3: IColumnType<T3,S3, C3>, C3: Column<T3,S3,C3>,
              T4:Any, S4: IColumnType<T4,S4, C4>, C4: Column<T4,S4,C4>,
              T5:Any, S5: IColumnType<T5,S5, C5>, C5: Column<T5,S5,C5>,
              T6:Any, S6: IColumnType<T6,S6, C6>, C6: Column<T6,S6,C6>> FOREIGN_KEY(
        col1: ColumnRef<T1,S1,C1>,
        col2:ColumnRef<T2,S2,C2>,
        col3:ColumnRef<T3,S3,C3>,
        col4: ColumnRef<T4,S4,C4>,
        col5:ColumnRef<T5,S5,C5>,
        col6:ColumnRef<T6,S6,C6>) =
        __FOREIGN_KEY__6(this, col1,col2,col3,col4,col5,col6)


  class __FOREIGN_KEY__5<T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>,
                         T3:Any, S3: IColumnType<T3,S3, C3>, C3: Column<T3,S3,C3>,
                         T4:Any, S4: IColumnType<T4,S4, C4>, C4: Column<T4,S4,C4>,
                         T5:Any, S5: IColumnType<T5,S5, C5>, C5: Column<T5,S5,C5>>(
        val table: MutableTable,
        val col1:ColumnRef<T1,S1,C1>,
        val col2:ColumnRef<T2,S2,C2>,
        val col3:ColumnRef<T3,S3,C3>,
        val col4:ColumnRef<T4,S4,C4>,
        val col5:ColumnRef<T5,S5,C5>) {
    fun REFERENCES(ref1:ColumnRef<T1,S1,C1>,
                   ref2:ColumnRef<T2,S2,C2>,
                   ref3:ColumnRef<T3,S3,C3>,
                   ref4:ColumnRef<T4,S4,C4>,
                   ref5:ColumnRef<T5,S5,C5>) {
      (table.__foreignKeys).add(ForeignKey(listOf(col1, col2, col3, col4, col5), ref1.table, listOf(ref1, ref2, ref3, ref4, ref5).apply { forEach { require(it.table==ref1.table) } }))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>,
              T3:Any, S3: IColumnType<T3,S3, C3>, C3: Column<T3,S3,C3>,
              T4:Any, S4: IColumnType<T4,S4, C4>, C4: Column<T4,S4,C4>,
              T5:Any, S5: IColumnType<T5,S5, C5>, C5: Column<T5,S5,C5>> FOREIGN_KEY(
        col1:ColumnRef<T1,S1,C1>,
        col2:ColumnRef<T2,S2,C2>,
        col3:ColumnRef<T3,S3,C3>,
        col4:ColumnRef<T4,S4,C4>,
        col5:ColumnRef<T5,S5,C5>) =
        __FOREIGN_KEY__5(this, col1,col2,col3,col4,col5)

  class __FOREIGN_KEY__4<T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>,
                         T3:Any, S3: IColumnType<T3,S3, C3>, C3: Column<T3,S3,C3>,
                         T4:Any, S4: IColumnType<T4,S4, C4>, C4: Column<T4,S4,C4>>(
        val table: MutableTable,
        val col1:ColumnRef<T1,S1,C1>,
        val col2:ColumnRef<T2,S2,C2>,
        val col3:ColumnRef<T3,S3,C3>,
        val col4: ColumnRef<T4, S4, C4>) {
    fun REFERENCES(ref1:ColumnRef<T1,S1,C1>,
ref2:ColumnRef<T2,S2,C2>,
ref3:ColumnRef<T3,S3,C3>,
ref4:ColumnRef<T4,S4,C4>) {
      (table.__foreignKeys).add(ForeignKey(listOf(col1, col2, col3, col4), ref1.table, listOf(ref1, ref2, ref3, ref4)))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>,
              T3:Any, S3: IColumnType<T3,S3, C3>, C3: Column<T3,S3,C3>,
              T4:Any, S4: IColumnType<T4,S4, C4>, C4: Column<T4,S4,C4>> FOREIGN_KEY(
        col1: ColumnRef<T1,S1,C1>,
        col2: ColumnRef<T2,S2,C2>,
        col3: ColumnRef<T3,S3,C3>,
        col4: ColumnRef<T4,S4,C4>) =
        __FOREIGN_KEY__4(this, col1,col2,col3,col4)

  class __FOREIGN_KEY__3<T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>,
                         T3:Any, S3: IColumnType<T3,S3, C3>, C3: Column<T3,S3,C3>>(
        val table: MutableTable,
        val col1: ColumnRef<T1,S1,C1>,
        val col2: ColumnRef<T2, S2, C2>,
        val col3: ColumnRef<T3,S3,C3>) {
    fun REFERENCES(ref1:ColumnRef<T1,S1,C1>,
                   ref2:ColumnRef<T2,S2,C2>,
                   ref3:ColumnRef<T3,S3,C3>) {
      (table.__foreignKeys).add(ForeignKey(listOf(col1, col2, col3), ref1.table, listOf(ref1, ref2, ref3).apply { forEach { require(it.table==ref1.table) } }))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>,
              T3:Any, S3: IColumnType<T3,S3, C3>, C3: Column<T3,S3,C3>> FOREIGN_KEY(
        col1:ColumnRef<T1,S1,C1>,
        col2:ColumnRef<T2,S2,C2>,
        col3:ColumnRef<T3,S3,C3>) = __FOREIGN_KEY__3(this, col1,col2,col3)

  class __FOREIGN_KEY__2<T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>>(
        val table: MutableTable,
        val col1:ColumnRef<T1,S1,C1>,
        val col2:ColumnRef<T2,S2,C2>) {
    fun REFERENCES(ref1:ColumnRef<T1,S1,C1>,
                   ref2:ColumnRef<T2,S2,C2>) {
      (table.__foreignKeys).add(ForeignKey(listOf(col1, col2), ref1.table, listOf(ref1, ref2).apply { require(ref2.table==ref1.table) }))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2, C2>, C2: Column<T2,S2,C2>> FOREIGN_KEY(col1: ColumnRef<T1,S1,C1>, col2:ColumnRef<T2,S2,C2>) =
        __FOREIGN_KEY__2(this, col1,col2)

  class __FOREIGN_KEY__1<T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>>(val table: MutableTable, val col1:ColumnRef<T1,S1,C1>) {
    fun REFERENCES(ref1:ColumnRef<T1,S1,C1>) {
      (table.__foreignKeys).add(ForeignKey(listOf(col1), ref1.table, listOf(ref1)))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1, C1>, C1: Column<T1,S1,C1>> FOREIGN_KEY(col1: ColumnRef<T1,S1,C1>) =
        __FOREIGN_KEY__1(this, col1)


  // @formatter:on
}
