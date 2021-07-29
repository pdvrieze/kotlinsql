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

import uk.ac.bournemouth.kotlinsql.columns.AbstractColumnConfiguration
import uk.ac.bournemouth.kotlinsql.columns.ColumnType
import uk.ac.bournemouth.kotlinsql.columns.LengthColumn
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

/**
 * The main class that caries a lot of the load for the class.
 */
@Suppress("NOTHING_TO_INLINE", "unused")
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
  fun BIT(name:String, block: AbstractColumnConfiguration.NormalColumnConfiguration<Boolean, ColumnType.SimpleColumnType.BIT_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.BIT_T, name).add(block)
  fun BIT(name:String, length:Int, block: AbstractColumnConfiguration.BaseLengthColumnConfiguration<BooleanArray, ColumnType.LengthColumnType.BITFIELD_T, LengthColumn<BooleanArray,ColumnType.LengthColumnType.BITFIELD_T>>.() -> Unit) = AbstractColumnConfiguration.LengthColumnConfiguration( name, ColumnType.LengthColumnType.BITFIELD_T, length).add( block)
  fun TINYINT(name: String, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration<Byte, ColumnType.NumericColumnType.TINYINT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration( ColumnType.NumericColumnType.TINYINT_T, name).add( block)
  fun SMALLINT(name: String, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration<Short, ColumnType.NumericColumnType.SMALLINT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration( ColumnType.NumericColumnType.SMALLINT_T, name).add( block)
  fun MEDIUMINT(name: String, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration<Int, ColumnType.NumericColumnType.MEDIUMINT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration( ColumnType.NumericColumnType.MEDIUMINT_T, name).add( block)
  fun INT(name: String, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration<Int, ColumnType.NumericColumnType.INT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration( ColumnType.NumericColumnType.INT_T, name).add( block)
  fun BIGINT(name: String, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration<Long, ColumnType.NumericColumnType.BIGINT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration( ColumnType.NumericColumnType.BIGINT_T, name).add( block)
  fun FLOAT(name: String, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration<Float, ColumnType.NumericColumnType.FLOAT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration( ColumnType.NumericColumnType.FLOAT_T, name).add( block)
  fun DOUBLE(name: String, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration<Double, ColumnType.NumericColumnType.DOUBLE_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration( ColumnType.NumericColumnType.DOUBLE_T, name).add( block)
  fun DECIMAL(name: String, precision: Int = -1, scale: Int = -1, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.DecimalColumnConfiguration<ColumnType.DecimalColumnType.DECIMAL_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.DecimalColumnConfiguration(
      ColumnType.DecimalColumnType.DECIMAL_T, name, precision, scale).add(block)
  fun NUMERIC(name: String, precision: Int = -1, scale: Int = -1, block: AbstractColumnConfiguration.AbstractNumberColumnConfiguration.DecimalColumnConfiguration<ColumnType.DecimalColumnType.NUMERIC_T>.() -> Unit) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.DecimalColumnConfiguration(
      ColumnType.DecimalColumnType.NUMERIC_T, name, precision, scale).add(block)
  fun DATE(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<Date, ColumnType.SimpleColumnType.DATE_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.DATE_T, name).add( block)
  fun TIME(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<Time, ColumnType.SimpleColumnType.TIME_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.TIME_T, name).add( block)
  fun TIMESTAMP(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<Timestamp, ColumnType.SimpleColumnType.TIMESTAMP_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.TIMESTAMP_T, name).add( block)
  fun DATETIME(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<Timestamp, ColumnType.SimpleColumnType.DATETIME_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.DATETIME_T, name).add( block)
  fun YEAR(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<Date, ColumnType.SimpleColumnType.YEAR_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.YEAR_T, name).add( block)
  fun CHAR(name: String, length: Int = -1, block: AbstractColumnConfiguration.AbstractCharColumnConfiguration.LengthCharColumnConfiguration<ColumnType.LengthCharColumnType.CHAR_T>.() -> Unit) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.LengthCharColumnConfiguration(
      ColumnType.LengthCharColumnType.CHAR_T, name, length).add(block)
  fun VARCHAR(name: String, length: Int, block: AbstractColumnConfiguration.AbstractCharColumnConfiguration.LengthCharColumnConfiguration<ColumnType.LengthCharColumnType.VARCHAR_T>.() -> Unit) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.LengthCharColumnConfiguration(
      ColumnType.LengthCharColumnType.VARCHAR_T, name, length).add(block)
  fun BINARY(name: String, length: Int, block: AbstractColumnConfiguration.BaseLengthColumnConfiguration<ByteArray, ColumnType.LengthColumnType.BINARY_T, LengthColumn<ByteArray, ColumnType.LengthColumnType.BINARY_T>>.() -> Unit) = AbstractColumnConfiguration.LengthColumnConfiguration( name, ColumnType.LengthColumnType.BINARY_T, length).add( block)
  fun VARBINARY(name: String, length: Int, block: AbstractColumnConfiguration.BaseLengthColumnConfiguration<ByteArray, ColumnType.LengthColumnType.VARBINARY_T, LengthColumn<ByteArray, ColumnType.LengthColumnType.VARBINARY_T>>.() -> Unit) = AbstractColumnConfiguration.LengthColumnConfiguration( name, ColumnType.LengthColumnType.VARBINARY_T, length).add( block)
  fun TINYBLOB(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<ByteArray, ColumnType.SimpleColumnType.TINYBLOB_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.TINYBLOB_T, name).add( block)
  fun BLOB(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<ByteArray, ColumnType.SimpleColumnType.BLOB_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.BLOB_T, name).add( block)
  fun MEDIUMBLOB(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<ByteArray, ColumnType.SimpleColumnType.MEDIUMBLOB_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.MEDIUMBLOB_T, name).add( block)
  fun LONGBLOB(name: String, block: AbstractColumnConfiguration.NormalColumnConfiguration<ByteArray, ColumnType.SimpleColumnType.LONGBLOB_T>.() -> Unit) = AbstractColumnConfiguration.NormalColumnConfiguration(  ColumnType.SimpleColumnType.LONGBLOB_T, name).add( block)
  fun TINYTEXT(name: String, block: AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration<ColumnType.CharColumnType.TINYTEXT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration(
      ColumnType.CharColumnType.TINYTEXT_T,
      name).add(block)
  fun TEXT(name: String, block: AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration<ColumnType.CharColumnType.TEXT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration(ColumnType.CharColumnType.TEXT_T,
                                                                                                      name).add(block)
  fun MEDIUMTEXT(name: String, block: AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration<ColumnType.CharColumnType.MEDIUMTEXT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration(
      ColumnType.CharColumnType.MEDIUMTEXT_T,
      name).add(block)
  fun LONGTEXT(name: String, block: AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration<ColumnType.CharColumnType.LONGTEXT_T>.() -> Unit) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration(
      ColumnType.CharColumnType.LONGTEXT_T,
      name).add(block)

  /* Versions without configuration closure */
  fun BIT(name: String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.BIT_T, name).add {}
  fun BIT(name:String, length:Int) = AbstractColumnConfiguration.LengthColumnConfiguration(name, ColumnType.LengthColumnType.BITFIELD_T, length).add {}
  fun TINYINT(name: String) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration(ColumnType.NumericColumnType.TINYINT_T, name).add {}
  fun SMALLINT(name:String) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration(ColumnType.NumericColumnType.SMALLINT_T, name).add {}
  fun MEDIUMINT(name:String) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration(ColumnType.NumericColumnType.MEDIUMINT_T, name).add {}
  fun INT(name: String) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration(ColumnType.NumericColumnType.INT_T, name).add {}
  fun BIGINT(name:String) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration(ColumnType.NumericColumnType.BIGINT_T, name).add {}
  fun FLOAT(name:String) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration(ColumnType.NumericColumnType.FLOAT_T, name).add {}
  fun DOUBLE(name:String) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.NumberColumnConfiguration(ColumnType.NumericColumnType.DOUBLE_T, name).add {}
  fun DECIMAL(name:String, precision:Int=-1, scale:Int=-1) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.DecimalColumnConfiguration(ColumnType.DecimalColumnType.DECIMAL_T, name, precision,
                                                                                        scale).add {}
  fun NUMERIC(name: String, precision: Int = -1, scale: Int = -1) = AbstractColumnConfiguration.AbstractNumberColumnConfiguration.DecimalColumnConfiguration(ColumnType.DecimalColumnType.NUMERIC_T, name,
                                                                                               precision, scale).add {}
  fun DATE(name: String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.DATE_T, name).add {}
  fun TIME(name:String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.TIME_T, name).add {}
  fun TIMESTAMP(name:String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.TIMESTAMP_T, name).add {}
  fun DATETIME(name: String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.DATETIME_T, name).add {}
  fun YEAR(name:String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.YEAR_T, name).add {}
  fun CHAR(name:String, length:Int = -1) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.LengthCharColumnConfiguration(ColumnType.LengthCharColumnType.CHAR_T, name, length).add {}
  fun VARCHAR(name: String, length: Int) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.LengthCharColumnConfiguration(ColumnType.LengthCharColumnType.VARCHAR_T, name, length).add {}
  fun BINARY(name: String, length: Int) = AbstractColumnConfiguration.LengthColumnConfiguration(name, ColumnType.LengthColumnType.BINARY_T, length).add {}
  fun VARBINARY(name: String, length: Int) = AbstractColumnConfiguration.LengthColumnConfiguration(name, ColumnType.LengthColumnType.VARBINARY_T, length).add {}
  fun TINYBLOB(name: String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.TINYBLOB_T, name).add {}
  fun BLOB(name:String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.BLOB_T, name).add {}
  fun MEDIUMBLOB(name:String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.MEDIUMBLOB_T, name).add {}
  fun LONGBLOB(name: String) = AbstractColumnConfiguration.NormalColumnConfiguration( ColumnType.SimpleColumnType.LONGBLOB_T, name).add {}
  fun TINYTEXT(name:String) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration(ColumnType.CharColumnType.TINYTEXT_T, name).add {}
  fun TEXT(name:String) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration(ColumnType.CharColumnType.TEXT_T, name).add {}
  fun MEDIUMTEXT(name:String) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration(ColumnType.CharColumnType.MEDIUMTEXT_T, name).add {}
  fun LONGTEXT(name: String) = AbstractColumnConfiguration.AbstractCharColumnConfiguration.CharColumnConfiguration(ColumnType.CharColumnType.LONGTEXT_T, name).add {}

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
      configuration.foreignKeys.add(ForeignKey(listOf(col1, col2, col3, col4, col5, col6), ref1.table, listOf(ref1, ref2, ref3, ref4, ref5, ref6).apply { forEach { require(it.table==ref1.table) } }))
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
      configuration.foreignKeys.add(ForeignKey(listOf(col1, col2, col3, col4, col5), ref1.table, listOf(ref1, ref2, ref3, ref4, ref5).apply { forEach { require(it.table==ref1.table) } }))
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
      configuration.foreignKeys.add(ForeignKey(listOf(col1, col2, col3), ref1.table, listOf(ref1, ref2, ref3).apply { forEach { require(it.table==ref1.table) } }))
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