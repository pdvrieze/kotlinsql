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

import uk.ac.bournemouth.kotlinsql.columns.*
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

/**
 * The main class that caries a lot of the load for the class.
 */
@Suppress("NOTHING_TO_INLINE", "unused", "FunctionName")
class TableConfiguration(override val _name:String, val extra:String?=null): TableRef {

  val cols = mutableListOf<Column<*, *, *>>()
  var primaryKey: List<ColumnRef<*, *, *>>? = null
  val foreignKeys = mutableListOf<ForeignKey>()
  val uniqueKeys = mutableListOf<List<ColumnRef<*, *, *>>>()
  val indices = mutableListOf<List<ColumnRef<*, *, *>>>()

  inline fun <T :Any, S: IColumnType<T, S, C>, C: Column<T, S, C>, CONF_T : AbstractColumnConfiguration<T, S, C, CONF_T>> CONF_T.add(block: CONF_T.() ->Unit): ColumnRef<T, S, C> {
    val newColumn:C = apply(block).newColumn(this@TableConfiguration)
    cols.add(newColumn)
    return newColumn.ref()
  }

  // @formatter:off
  /* Versions with configuration closure. */
  fun BIT(name:String, block: NormalColumnConfiguration<Boolean, SimpleColumnType.BIT_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.BIT_T, name).add(block)
  fun BIT(name:String, length:Int, block: BaseLengthColumnConfiguration<BooleanArray, LengthColumnType.BITFIELD_T, LengthColumn<BooleanArray,LengthColumnType.BITFIELD_T>>.() -> Unit) = LengthColumnConfiguration( name, LengthColumnType.BITFIELD_T, length).add( block)
  fun TINYINT(name: String, block: NumberColumnConfiguration<Byte, NumericColumnType.TINYINT_T>.() -> Unit) = NumberColumnConfiguration( NumericColumnType.TINYINT_T, name).add( block)
  fun SMALLINT(name: String, block: NumberColumnConfiguration<Short, NumericColumnType.SMALLINT_T>.() -> Unit) = NumberColumnConfiguration( NumericColumnType.SMALLINT_T, name).add( block)
  fun MEDIUMINT(name: String, block: NumberColumnConfiguration<Int, NumericColumnType.MEDIUMINT_T>.() -> Unit) = NumberColumnConfiguration( NumericColumnType.MEDIUMINT_T, name).add( block)
  fun INT(name: String, block: NumberColumnConfiguration<Int, NumericColumnType.INT_T>.() -> Unit) = NumberColumnConfiguration( NumericColumnType.INT_T, name).add( block)
  fun BIGINT(name: String, block: NumberColumnConfiguration<Long, NumericColumnType.BIGINT_T>.() -> Unit) = NumberColumnConfiguration( NumericColumnType.BIGINT_T, name).add( block)
  fun FLOAT(name: String, block: NumberColumnConfiguration<Float, NumericColumnType.FLOAT_T>.() -> Unit) = NumberColumnConfiguration( NumericColumnType.FLOAT_T, name).add( block)
  fun DOUBLE(name: String, block: NumberColumnConfiguration<Double, NumericColumnType.DOUBLE_T>.() -> Unit) = NumberColumnConfiguration( NumericColumnType.DOUBLE_T, name).add( block)
  fun DECIMAL(name: String, precision: Int = -1, scale: Int = -1, block: DecimalColumnConfiguration<DecimalColumnType.DECIMAL_T>.() -> Unit) = DecimalColumnConfiguration(
      DecimalColumnType.DECIMAL_T, name, precision, scale).add(block)
  fun NUMERIC(name: String, precision: Int = -1, scale: Int = -1, block: DecimalColumnConfiguration<DecimalColumnType.NUMERIC_T>.() -> Unit) = DecimalColumnConfiguration(
      DecimalColumnType.NUMERIC_T, name, precision, scale).add(block)
  fun DATE(name: String, block: NormalColumnConfiguration<Date, SimpleColumnType.DATE_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.DATE_T, name).add( block)
  fun TIME(name: String, block: NormalColumnConfiguration<Time, SimpleColumnType.TIME_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.TIME_T, name).add( block)
  fun TIMESTAMP(name: String, block: NormalColumnConfiguration<Timestamp, SimpleColumnType.TIMESTAMP_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.TIMESTAMP_T, name).add( block)
  fun DATETIME(name: String, block: NormalColumnConfiguration<Timestamp, SimpleColumnType.DATETIME_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.DATETIME_T, name).add( block)
  fun YEAR(name: String, block: NormalColumnConfiguration<Date, SimpleColumnType.YEAR_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.YEAR_T, name).add( block)
  fun CHAR(name: String, length: Int = -1, block: LengthCharColumnConfiguration<LengthCharColumnType.CHAR_T>.() -> Unit) = LengthCharColumnConfiguration(
      LengthCharColumnType.CHAR_T, name, length).add(block)
  fun VARCHAR(name: String, length: Int, block: LengthCharColumnConfiguration<LengthCharColumnType.VARCHAR_T>.() -> Unit) = LengthCharColumnConfiguration(
      LengthCharColumnType.VARCHAR_T, name, length).add(block)
  fun BINARY(name: String, length: Int, block: BaseLengthColumnConfiguration<ByteArray, LengthColumnType.BINARY_T, LengthColumn<ByteArray, LengthColumnType.BINARY_T>>.() -> Unit) = LengthColumnConfiguration( name, LengthColumnType.BINARY_T, length).add( block)
  fun VARBINARY(name: String, length: Int, block: BaseLengthColumnConfiguration<ByteArray, LengthColumnType.VARBINARY_T, LengthColumn<ByteArray, LengthColumnType.VARBINARY_T>>.() -> Unit) = LengthColumnConfiguration( name, LengthColumnType.VARBINARY_T, length).add( block)
  fun TINYBLOB(name: String, block: NormalColumnConfiguration<ByteArray, SimpleColumnType.TINYBLOB_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.TINYBLOB_T, name).add( block)
  fun BLOB(name: String, block: NormalColumnConfiguration<ByteArray, SimpleColumnType.BLOB_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.BLOB_T, name).add( block)
  fun MEDIUMBLOB(name: String, block: NormalColumnConfiguration<ByteArray, SimpleColumnType.MEDIUMBLOB_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.MEDIUMBLOB_T, name).add( block)
  fun LONGBLOB(name: String, block: NormalColumnConfiguration<ByteArray, SimpleColumnType.LONGBLOB_T>.() -> Unit) = NormalColumnConfiguration(  SimpleColumnType.LONGBLOB_T, name).add( block)
  fun TINYTEXT(name: String, block: CharColumnConfiguration<CharColumnType.TINYTEXT_T>.() -> Unit) = CharColumnConfiguration(
      CharColumnType.TINYTEXT_T,
      name).add(block)
  fun TEXT(name: String, block: CharColumnConfiguration<CharColumnType.TEXT_T>.() -> Unit) = CharColumnConfiguration(CharColumnType.TEXT_T,
                                                                                                      name).add(block)
  fun MEDIUMTEXT(name: String, block: CharColumnConfiguration<CharColumnType.MEDIUMTEXT_T>.() -> Unit) = CharColumnConfiguration(
      CharColumnType.MEDIUMTEXT_T,
      name).add(block)
  fun LONGTEXT(name: String, block: CharColumnConfiguration<CharColumnType.LONGTEXT_T>.() -> Unit) = CharColumnConfiguration(
      CharColumnType.LONGTEXT_T,
      name).add(block)

  /* Versions without configuration closure */
  fun BIT(name: String) = NormalColumnConfiguration( SimpleColumnType.BIT_T, name).add {}
  fun BIT(name:String, length:Int) = LengthColumnConfiguration(name, LengthColumnType.BITFIELD_T, length).add {}
  fun TINYINT(name: String) = NumberColumnConfiguration(NumericColumnType.TINYINT_T, name).add {}
  fun SMALLINT(name:String) = NumberColumnConfiguration(NumericColumnType.SMALLINT_T, name).add {}
  fun MEDIUMINT(name:String) = NumberColumnConfiguration(NumericColumnType.MEDIUMINT_T, name).add {}
  fun INT(name: String) = NumberColumnConfiguration(NumericColumnType.INT_T, name).add {}
  fun BIGINT(name:String) = NumberColumnConfiguration(NumericColumnType.BIGINT_T, name).add {}
  fun FLOAT(name:String) = NumberColumnConfiguration(NumericColumnType.FLOAT_T, name).add {}
  fun DOUBLE(name:String) = NumberColumnConfiguration(NumericColumnType.DOUBLE_T, name).add {}
  fun DECIMAL(name:String, precision:Int=-1, scale:Int=-1) = DecimalColumnConfiguration(DecimalColumnType.DECIMAL_T, name, precision,
                                                                                        scale).add {}
  fun NUMERIC(name: String, precision: Int = -1, scale: Int = -1) = DecimalColumnConfiguration(DecimalColumnType.NUMERIC_T, name,
                                                                                               precision, scale).add {}
  fun DATE(name: String) = NormalColumnConfiguration( SimpleColumnType.DATE_T, name).add {}
  fun TIME(name:String) = NormalColumnConfiguration( SimpleColumnType.TIME_T, name).add {}
  fun TIMESTAMP(name:String) = NormalColumnConfiguration( SimpleColumnType.TIMESTAMP_T, name).add {}
  fun DATETIME(name: String) = NormalColumnConfiguration( SimpleColumnType.DATETIME_T, name).add {}
  fun YEAR(name:String) = NormalColumnConfiguration( SimpleColumnType.YEAR_T, name).add {}
  fun CHAR(name:String, length:Int = -1) = LengthCharColumnConfiguration(LengthCharColumnType.CHAR_T, name, length).add {}
  fun VARCHAR(name: String, length: Int) = LengthCharColumnConfiguration(LengthCharColumnType.VARCHAR_T, name, length).add {}
  fun BINARY(name: String, length: Int) = LengthColumnConfiguration(name, LengthColumnType.BINARY_T, length).add {}
  fun VARBINARY(name: String, length: Int) = LengthColumnConfiguration(name, LengthColumnType.VARBINARY_T, length).add {}
  fun TINYBLOB(name: String) = NormalColumnConfiguration( SimpleColumnType.TINYBLOB_T, name).add {}
  fun BLOB(name:String) = NormalColumnConfiguration( SimpleColumnType.BLOB_T, name).add {}
  fun MEDIUMBLOB(name:String) = NormalColumnConfiguration( SimpleColumnType.MEDIUMBLOB_T, name).add {}
  fun LONGBLOB(name: String) = NormalColumnConfiguration( SimpleColumnType.LONGBLOB_T, name).add {}
  fun TINYTEXT(name:String) = CharColumnConfiguration(CharColumnType.TINYTEXT_T, name).add {}
  fun TEXT(name:String) = CharColumnConfiguration(CharColumnType.TEXT_T, name).add {}
  fun MEDIUMTEXT(name:String) = CharColumnConfiguration(CharColumnType.MEDIUMTEXT_T, name).add {}
  fun LONGTEXT(name: String) = CharColumnConfiguration(CharColumnType.LONGTEXT_T, name).add {}

  fun INDEX(col1: ColumnRef<*,*,*>, vararg cols: ColumnRef<*,*,*>) { indices.add(mutableListOf(col1).apply { addAll(cols) })}
  fun UNIQUE(col1: ColumnRef<*,*,*>, vararg cols: ColumnRef<*,*,*>) { uniqueKeys.add(mutableListOf(col1).apply { addAll(cols) })}
  fun PRIMARY_KEY(col1: ColumnRef<*,*,*>, vararg cols: ColumnRef<*,*,*>) { primaryKey = mutableListOf(col1).apply { addAll(cols) }}

  @Suppress("ClassName")
  class __FOREIGN_KEY__6<T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                         T3:Any, S3: IColumnType<T3,S3,C3>, C3:Column<T3,S3,C3>,
                         T4:Any, S4: IColumnType<T4,S4,C4>, C4:Column<T4,S4,C4>,
                         T5:Any, S5: IColumnType<T5,S5,C5>, C5:Column<T5,S5,C5>,
                         T6:Any, S6: IColumnType<T6,S6,C6>, C6:Column<T6,S6,C6>>(val configuration:TableConfiguration,
                                                                                    val col1:ColumnRef<T1, S1, C1>,
                                                                                    val col2:ColumnRef<T2, S2, C2>,
                                                                                    val col3:ColumnRef<T3, S3, C3>,
                                                                                    val col4:ColumnRef<T4, S4, C4>,
                                                                                    val col5:ColumnRef<T5, S5, C5>,
                                                                                    val col6:ColumnRef<T6, S6, C6>) {
    inline fun REFERENCES(ref1:ColumnRef<T1, S1, C1>,
                          ref2:ColumnRef<T2, S2, C2>,
                          ref3:ColumnRef<T3, S3, C3>,
                          ref4:ColumnRef<T4, S4, C4>,
                          ref5:ColumnRef<T5, S5, C5>,
                          ref6:ColumnRef<T6, S6, C6>) {
      configuration.foreignKeys.add(ForeignKey(listOf(col1, col2, col3, col4, col5, col6), ref1.table, listOf(ref1, ref2, ref3, ref4, ref5, ref6).onEach { require(it.table==ref1.table) }))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>,
              T3:Any, S3: IColumnType<T3,S3,C3>, C3:Column<T3,S3,C3>,
              T4:Any, S4: IColumnType<T4,S4,C4>, C4:Column<T4,S4,C4>,
              T5:Any, S5: IColumnType<T5,S5,C5>, C5:Column<T5,S5,C5>,
              T6:Any, S6: IColumnType<T6,S6,C6>, C6:Column<T6,S6,C6>> FOREIGN_KEY(col1: ColumnRef<T1, S1, C1>, col2:ColumnRef<T2, S2, C2>, col3:ColumnRef<T3, S3, C3>, col4: ColumnRef<T4, S4, C4>, col5:ColumnRef<T5, S5, C5>, col6:ColumnRef<T6, S6, C6>) =
      __FOREIGN_KEY__6(this, col1,col2,col3,col4,col5,col6)


  @Suppress("ClassName")
  class __FOREIGN_KEY__5<T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>,
                         T3:Any, S3: IColumnType<T3,S3,C3>, C3:Column<T3,S3,C3>,
                         T4:Any, S4: IColumnType<T4,S4,C4>, C4:Column<T4,S4,C4>,
                         T5:Any, S5: IColumnType<T5,S5,C5>, C5:Column<T5,S5,C5>>(val configuration:TableConfiguration, val col1:ColumnRef<T1, S1, C1>, val col2:ColumnRef<T2, S2, C2>, val col3:ColumnRef<T3, S3, C3>, val col4:ColumnRef<T4, S4, C4>, val col5:ColumnRef<T5, S5, C5>) {
    inline fun REFERENCES(ref1:ColumnRef<T1, S1, C1>, ref2:ColumnRef<T2, S2, C2>, ref3:ColumnRef<T3, S3, C3>, ref4:ColumnRef<T4, S4, C4>, ref5:ColumnRef<T5, S5, C5>) {
      configuration.foreignKeys.add(ForeignKey(listOf(col1, col2, col3, col4, col5), ref1.table, listOf(ref1, ref2, ref3, ref4, ref5).onEach { require(it.table==ref1.table) }))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>,
              T3:Any, S3: IColumnType<T3,S3,C3>, C3:Column<T3,S3,C3>,
              T4:Any, S4: IColumnType<T4,S4,C4>, C4:Column<T4,S4,C4>,
              T5:Any, S5: IColumnType<T5,S5,C5>, C5:Column<T5,S5,C5>> FOREIGN_KEY(col1: ColumnRef<T1, S1, C1>, col2:ColumnRef<T2, S2, C2>, col3:ColumnRef<T3, S3, C3>, col4: ColumnRef<T4, S4, C4>, col5:ColumnRef<T5, S5, C5>) =
      __FOREIGN_KEY__5(this, col1,col2,col3,col4,col5)

  @Suppress("ClassName")
  class __FOREIGN_KEY__4<T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>,
                         T3:Any, S3: IColumnType<T3,S3,C3>, C3:Column<T3,S3,C3>,
                         T4:Any, S4: IColumnType<T4,S4,C4>, C4:Column<T4,S4,C4>>(val configuration:TableConfiguration, val col1:ColumnRef<T1, S1, C1>, val col2:ColumnRef<T2, S2, C2>, val col3:ColumnRef<T3, S3, C3>, val col4:ColumnRef<T4, S4, C4>) {
    inline fun REFERENCES(ref1:ColumnRef<T1, S1, C1>, ref2:ColumnRef<T2, S2, C2>, ref3:ColumnRef<T3, S3, C3>, ref4:ColumnRef<T4, S4, C4>) {
      configuration.foreignKeys.add(ForeignKey(listOf(col1, col2, col3, col4), ref1.table, listOf(ref1, ref2, ref3, ref4)))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>,
              T3:Any, S3: IColumnType<T3,S3,C3>, C3:Column<T3,S3,C3>,
              T4:Any, S4: IColumnType<T4,S4,C4>, C4:Column<T4,S4,C4>> FOREIGN_KEY(col1: ColumnRef<T1, S1, C1>, col2:ColumnRef<T2, S2, C2>, col3:ColumnRef<T3, S3, C3>, col4: ColumnRef<T4, S4, C4>) =
      __FOREIGN_KEY__4(this, col1,col2,col3,col4)

  @Suppress("ClassName")
  class __FOREIGN_KEY__3<T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
                         T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>,
                         T3:Any, S3: IColumnType<T3,S3,C3>, C3:Column<T3,S3,C3>>(val configuration:TableConfiguration, val col1:ColumnRef<T1, S1, C1>, val col2:ColumnRef<T2, S2, C2>, val col3:ColumnRef<T3, S3, C3>) {
    inline fun REFERENCES(ref1:ColumnRef<T1, S1, C1>, ref2:ColumnRef<T2, S2, C2>, ref3:ColumnRef<T3, S3, C3>) {
      configuration.foreignKeys.add(ForeignKey(listOf(col1, col2, col3), ref1.table, listOf(ref1, ref2, ref3).onEach { require(it.table==ref1.table) }))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
              T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>,
              T3:Any, S3: IColumnType<T3,S3,C3>, C3:Column<T3,S3,C3>> FOREIGN_KEY(col1: ColumnRef<T1, S1, C1>, col2:ColumnRef<T2, S2, C2>, col3:ColumnRef<T3, S3, C3>) =
      __FOREIGN_KEY__3(this, col1,col2,col3)

  @Suppress("ClassName")
  class __FOREIGN_KEY__2<T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
 T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>>(val configuration:TableConfiguration, val col1:ColumnRef<T1, S1, C1>, val col2:ColumnRef<T2, S2, C2>) {
    inline fun REFERENCES(ref1:ColumnRef<T1, S1, C1>, ref2:ColumnRef<T2, S2, C2>) {
      configuration.foreignKeys.add(ForeignKey(listOf(col1, col2), ref1.table, listOf(ref1, ref2).apply { require(ref2.table==ref1.table) }))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>,
 T2:Any, S2: IColumnType<T2,S2,C2>, C2:Column<T2,S2,C2>> FOREIGN_KEY(col1: ColumnRef<T1, S1, C1>, col2:ColumnRef<T2, S2, C2>) =
      __FOREIGN_KEY__2(this, col1,col2)

  @Suppress("ClassName")
  class __FOREIGN_KEY__1<T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>>(val configuration:TableConfiguration, val col1:ColumnRef<T1, S1, C1>) {
    inline fun REFERENCES(ref1:ColumnRef<T1, S1, C1>) {
      configuration.foreignKeys.add(ForeignKey(listOf(col1), ref1.table, listOf(ref1)))
    }
  }

  inline fun <T1:Any, S1: IColumnType<T1,S1,C1>, C1:Column<T1,S1,C1>> FOREIGN_KEY(col1: ColumnRef<T1, S1, C1>) =
      __FOREIGN_KEY__1(this, col1)
  // @formatter:on

}