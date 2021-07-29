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

@file:Suppress("FunctionName", "unused")

package uk.ac.bournemouth.kotlinsql

import uk.ac.bournemouth.kotlinsql.columns.ColumnType
import uk.ac.bournemouth.kotlinsql.impl.AbstractTable


/**
 * A base class for table declarations. Users of the code are expected to use this with a configuration closure to create
 * database tables. for typed columns to be available they need to be declared using `by [type]` or `by [name]`.
 *
 * A sample use is:
 * ```
 *    object peopleTable:[ImmutableTable]("people", "ENGINE=InnoDB CHARSET=utf8", {
 *      val firstname = [VARCHAR]("firstname", 50)
 *      val familyname = [VARCHAR]("familyname", 30) { NOT_NULL }
 *      [DATE]("birthdate")
 *      [PRIMARY_KEY](firstname, familyname)
 *    }) {
 *      val firstname by [type]([VARCHAR_T])
 *      val surname by [name]("familyname", [VARCHAR_T])
 *      val birthdate by [type]([DATE_T])
 *    }
 * ```
 *
 * Note that the definition of typed fields through delegates is mainly to aid programmatic access. Values within the
 * configurator body are only visible there, and mainly serve to make definition of keys easy. Values that are not used
 * (such as birthdate) do not need to be stored. Their mere declaration adds them to the table configuration.
 *
 * @property _cols The list of columns in the table.
 * @property _primaryKey The primary key of the table (if defined)
 * @property _foreignKeys The foreign keys defined on the table.
 * @property _uniqueKeys Unique keys / uniqueness constraints defined on the table
 * @property _indices Additional indices defined on the table
 * @property _extra Extra table configuration to be appended after the definition. This contains information such as the
 *                  engine or charset to use.
 */
abstract class ImmutableTable private constructor(override val _name: String,
                                                  override val _cols: List<Column<*,*,*>>,
                                                  override val _primaryKey: List<Column<*,*,*>>?,
                                                  override val _foreignKeys: List<ForeignKey>,
                                                  override val _uniqueKeys: List<List<Column<*,*,*>>>,
                                                  override val _indices: List<List<Column<*,*,*>>>,
                                                  override val _extra: String?) : AbstractTable() {

  @Suppress("unused")
  private constructor(c: TableConfiguration):this(c._name, c.cols, c.primaryKey?.let {c.cols.resolveAll(it)}, c.foreignKeys, c.uniqueKeys.map {c.cols.resolveAll(it)}, c.indices.map {c.cols.resolveAll(it)}, c.extra)

  /**
   * The main use of this class is through inheriting this constructor.
   */
  constructor(name:String, extra: String? = null, block: TableConfiguration.()->Unit):
      this(TableConfiguration(name, extra).apply(block))

  protected fun <T:Any, S: ColumnType<T, S, C>, C:Column<T,S,C>> type(type: ColumnType<T, S, C>) = TypeFieldAccessor(
        type)

}

