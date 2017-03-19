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
import uk.ac.bournemouth.util.kotlin.sql.DBConnection
import java.math.BigDecimal
import java.sql.SQLWarning
import java.util.*
import kotlin.reflect.KProperty


internal val LINE_SEPARATOR: String by lazy { System.getProperty("line.separator")!! }

/**
 * Implementation for the database API
 */

internal abstract class ColumnImpl<T:Any, S: ColumnType<T, S,C>, C:Column<T,S,C>> internal constructor (
      override val table: TableRef,
      override val type: S,
      override val name: String,
      override val notnull: Boolean?,
      override val unique: Boolean,
      override val autoincrement: Boolean,
      override val default: T?,
      override val comment: String?,
      override val columnFormat: AbstractColumnConfiguration.ColumnFormat?,
      override val storageFormat: AbstractColumnConfiguration.StorageFormat?,
      override val references: ColsetRef?,
      val unsigned:Boolean = false,
      val zerofill: Boolean = false,
      val displayLength: Int = -1,
      val precision: Int = -1,
      val scale:Int = -1,
      val charset:String? = null,
      val collation:String? = null,
      val binary:Boolean = false,
      val length:Int = -1
):Column<T,S,C> {


  @Suppress("UNCHECKED_CAST")
  override fun ref():C {
    return this as C
  }

  override fun toDDL(): CharSequence {
    val result = StringBuilder()
    result.apply {
      append('`').append(name).append("` ").append(type.typeName)
      if (this@ColumnImpl.length>0) append('(').append(this@ColumnImpl.length).append(')')
      else if (displayLength>0) append('(').append(displayLength).append(')')
      if (unsigned) append(" UNSIGNED")
      if (zerofill) append(" ZEROFILL")
      if (binary) append(" BINARY")
      charset?.let { append(" CHARACTER SET ").append(it)}
      collation?.let { append(" COLLATE ").append(it)}
      notnull?.let { append(if(it) " NOT NULL" else "NULL") }
      default?.let { append(" DEFAULT ")
        if (it is CharSequence)
          append('\'').append(it).append('\'')
        else append(it)
      }
      if (autoincrement) append(" AUTO_INCREMENT")
      if (unique) append(" UNIQUE")
      comment?.let { append(" '").append(comment).append('\'') }
      columnFormat?.let { append(" COLUMN_FORMAT ").append(it.name)}
      storageFormat?.let { append(" STORAGE ").append(it.name)}
      references?.let { append(" REFERENCES ").append(toDDL(it.table._name, it.columns)) }

    }

    return result
  }

  override fun toString(): String {
    return "${javaClass.simpleName}: ${toDDL()}"
  }

  override fun matches(typeName: String,
                       size: Int,
                       notNull: Boolean?,
                       autoincrement: Boolean?,
                       default: String?,
                       comment: String?): Boolean {
    return this.type.typeName==typeName &&
          (this.length<0 || this.length==size) &&
          this.notnull==notNull &&
          this.autoincrement == autoincrement &&
          this.default == default &&
          this.comment == comment
  }

  override fun equals(other: Any?): Boolean{
    if (this === other) return true
    if (other?.javaClass != javaClass) return false

    other as ColumnImpl<*, *, *>

    if (table != other.table) return false
    if (type != other.type) return false
    if (name != other.name) return false
    if (notnull != other.notnull) return false
    if (unique != other.unique) return false
    if (autoincrement != other.autoincrement) return false
    if (default != other.default) return false
    if (comment != other.comment) return false
    if (columnFormat != other.columnFormat) return false
    if (storageFormat != other.storageFormat) return false
    if (references != other.references) return false
    if (unsigned != other.unsigned) return false
    if (zerofill != other.zerofill) return false
    if (displayLength != other.displayLength) return false
    if (precision != other.precision) return false
    if (scale != other.scale) return false
    if (charset != other.charset) return false
    if (collation != other.collation) return false
    if (binary != other.binary) return false
    if (length != other.length) return false

    return true
  }

  override fun hashCode(): Int{
    var result = table.hashCode()
    result = 31 * result + type.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + (notnull?.hashCode() ?: 0)
    result = 31 * result + unique.hashCode()
    result = 31 * result + autoincrement.hashCode()
    result = 31 * result + (default?.hashCode() ?: 0)
    result = 31 * result + (comment?.hashCode() ?: 0)
    result = 31 * result + (columnFormat?.hashCode() ?: 0)
    result = 31 * result + (storageFormat?.hashCode() ?: 0)
    result = 31 * result + (references?.hashCode() ?: 0)
    result = 31 * result + unsigned.hashCode()
    result = 31 * result + zerofill.hashCode()
    result = 31 * result + displayLength
    result = 31 * result + precision
    result = 31 * result + scale
    result = 31 * result + (charset?.hashCode() ?: 0)
    result = 31 * result + (collation?.hashCode() ?: 0)
    result = 31 * result + binary.hashCode()
    result = 31 * result + length
    return result
  }


}

internal class NormalColumnImpl<T:Any, S: SimpleColumnType<T, S>>(table: TableRef,
                                                                  name: String,
                                                                  configuration: NormalColumnConfiguration<T, S>):
      ColumnImpl<T, S, SimpleColumn<T, S>> (table = table,
                                            type = configuration.type,
                                            name = name,
                                            notnull = configuration.notnull,
                                            unique = configuration.unique,
                                            autoincrement = configuration.autoincrement,
                                            default = configuration.default,
                                            comment = configuration.comment,
                                            columnFormat = configuration.columnFormat,
                                            storageFormat = configuration.storageFormat,
                                            references = configuration.references), SimpleColumn<T, S> {
  override fun copyConfiguration(newName:String?, owner: Table) = NormalColumnConfiguration(type, newName ?: name)
}

internal class LengthColumnImpl<T:Any, S: LengthColumnType<T, S>>(table: TableRef,
                                                                  name: String,
                                                                  configuration: LengthColumnConfiguration<T, S>):
      ColumnImpl<T,S, LengthColumn<T,S>>(table=table,
                                         type=configuration.type,
                                         name=name,
                                         notnull=configuration.notnull,
                                         unique=configuration.unique,
                                         autoincrement=configuration.autoincrement,
                                         default=configuration.default,
                                         comment=configuration.comment,
                                         columnFormat=configuration.columnFormat,
                                         storageFormat=configuration.storageFormat,
                                         references=configuration.references,
                                         length = configuration.length), LengthColumn<T,S> {
  init {
    if (length<1) { throw IllegalArgumentException("Lengths must be at least 1 and specified")}
  }

  override fun copyConfiguration(newName:String?, owner: Table) = LengthColumnConfiguration(newName ?: name, type,
                                                                                            length)
}

internal class NumberColumnImpl<T:Any, S: NumericColumnType<T, S>>(table: TableRef,
                                                                   name: String,
                                                                   configuration: NumberColumnConfiguration<T, S>):
      ColumnImpl<T, S, NumericColumn<T, S>> (table = table,
                                             type = configuration.type,
                                             name = name,
                                             notnull = configuration.notnull,
                                             unique = configuration.unique,
                                             autoincrement = configuration.autoincrement,
                                             default = configuration.default,
                                             comment = configuration.comment,
                                             columnFormat = configuration.columnFormat,
                                             storageFormat = configuration.storageFormat,
                                             references = configuration.references,
                                             unsigned = configuration.unsigned,
                                             zerofill = configuration.zerofill,
                                             displayLength = configuration.displayLength), NumericColumn<T, S> {
  override fun copyConfiguration(newName:String?, owner: Table) = NumberColumnConfiguration(type, newName ?: name)
}


internal class CharColumnImpl<S: CharColumnType<S>>(table: TableRef,
                                                    name: String,
                                                    configuration: CharColumnConfiguration<S>):
      ColumnImpl<String, S, CharColumn<S>> (table = table,
                                          type = configuration.type,
                                          name = name,
                                          notnull = configuration.notnull,
                                          unique = configuration.unique,
                                          autoincrement = configuration.autoincrement,
                                          default = configuration.default,
                                          comment = configuration.comment,
                                          columnFormat = configuration.columnFormat,
                                          storageFormat = configuration.storageFormat,
                                          references = configuration.references,
                                          binary = configuration.binary,
                                          charset = configuration.charset,
                                          collation = configuration.collation), CharColumn<S> {
  override fun copyConfiguration(newName:String?, owner: Table) = CharColumnConfiguration(type, newName ?: name)
}


internal class LengthCharColumnImpl<S: LengthCharColumnType<S>>(table: TableRef,
                                                                name: String,
                                                                configuration: LengthCharColumnConfiguration<S>):
      ColumnImpl<String, S, LengthCharColumn<S>> (table = table,
                                                type = configuration.type,
                                                name = name,
                                                notnull = configuration.notnull,
                                                unique = configuration.unique,
                                                autoincrement = configuration.autoincrement,
                                                default = configuration.default,
                                                comment = configuration.comment,
                                                columnFormat = configuration.columnFormat,
                                                storageFormat = configuration.storageFormat,
                                                references = configuration.references,
                                                length = configuration.length,
                                                binary = configuration.binary,
                                                charset = configuration.charset,
                                                collation = configuration.collation), LengthCharColumn<S> {
  init {
    if (length<1) { throw IllegalArgumentException("Lengths must be at least 1 and specified")}
  }

  override fun copyConfiguration(newName:String?, owner: Table) = LengthCharColumnConfiguration(type, newName ?: name,
                                                                                                length)
}


internal class DecimalColumnImpl<S: DecimalColumnType<S>>(table: TableRef,
                                                          name: String,
                                                          configuration: DecimalColumnConfiguration<S>):
      ColumnImpl<BigDecimal, S, DecimalColumn<S>> (table = table,
                                             type = configuration.type,
                                             name = name,
                                             notnull = configuration.notnull,
                                             unique = configuration.unique,
                                             autoincrement = configuration.autoincrement,
                                             default = configuration.default,
                                             comment = configuration.comment,
                                             columnFormat = configuration.columnFormat,
                                             storageFormat = configuration.storageFormat,
                                             references = configuration.references,
                                             unsigned = configuration.unsigned,
                                             zerofill = configuration.zerofill,
                                             displayLength = configuration.displayLength,
                                             precision = configuration.precision,
                                             scale = configuration.scale), DecimalColumn<S> {
  override fun copyConfiguration(newName:String?, owner: Table) = DecimalColumnConfiguration(type, newName ?: name,
                                                                                             precision, scale)
}

class CountColumn(val colRef:ColumnRef<*,*,*>): NumericColumn<Int, NumericColumnType.INT_T> {
  override val table: TableRef
    get() = colRef.table
  override val name: String
    get() = "COUNT( ${colRef.name} )"
  override val type: NumericColumnType.INT_T
    get() = NumericColumnType.INT_T
  override val notnull: Boolean? get() = null
  override val unique: Boolean get() = false
  override val autoincrement: Boolean get() = false
  override val default: Int? get() = null
  override val comment: String? get() = null
  override val columnFormat: ColumnFormat? get() = null
  override val storageFormat: StorageFormat? get() = null
  override val references: ColsetRef? get() = null
  override val unsigned: Boolean get() = true
  override val zerofill: Boolean get() = false
  override val displayLength: Int get() = 11

  override fun copyConfiguration(newName: String?, owner: Table): NumberColumnConfiguration<Int, NumericColumnType.INT_T> {
    throw UnsupportedOperationException()
  }

  override fun ref(): ColumnRef<Int, NumericColumnType.INT_T, NumericColumn<Int, NumericColumnType.INT_T>> {
    throw UnsupportedOperationException()
  }

  override fun toDDL(): CharSequence {
    throw UnsupportedOperationException()
  }

  override fun matches(typeName: String, size: Int, notNull: Boolean?, autoincrement: Boolean?, default: String?, comment: String?): Boolean {
    return false
  }
}

class TableRefImpl(override val _name: String) : TableRef


abstract class AbstractTable: Table {

  companion object {

    fun List<Column<*,*,*>>.resolve(ref: ColumnRef<*,*,*>) = find { it.name == ref.name } ?: throw java.util.NoSuchElementException(
          "No column with the name ${ref.name} could be found")

    fun List<Column<*,*,*>>.resolveAll(refs: List<ColumnRef<*,*,*>>) = refs.map { resolve(it) }

    fun List<Column<*,*,*>>.resolveAll(refs: Array<out ColumnRef<*,*,*>>) = refs.map { resolve(it) }

    fun List<Column<*,*,*>>.resolveAll(refs: Sequence<ColumnRef<*,*,*>>) = refs.map { resolve(it) }

  }

  override fun resolve(ref: ColumnRef<*,*,*>) : Column<*,*,*> = (_cols.find {it.name==ref.name}) !!

  override fun ref(): TableRef = TableRefImpl(_name)

  override fun column(name:String) = _cols.firstOrNull {it.name==name}

  operator fun getValue(thisRef: ImmutableTable, property: KProperty<*>): Column<*,*,*> {
    return column(property.name)!!
  }

  open class TypeFieldAccessor<T:Any, S: IColumnType<T, S, C>, C:Column<T,S,C>>(val type: IColumnType<T, S, C>): Table.FieldAccessor<T, S, C> {
    private var value: C? = null
    open fun name(property: kotlin.reflect.KProperty<*>) = property.name
    override operator fun getValue(thisRef: Table, property: kotlin.reflect.KProperty<*>): C {
      if (value==null) {
        val field = thisRef.column(property.name) ?: throw IllegalArgumentException("There is no field with the given name ${property.name}")
        value = type.cast(field)
      }
      return value!!
    }
  }

  /** Property delegator to access database columns by name and type. */
  @PublishedApi
  internal fun <T:Any, S: IColumnType<T, S, C>, C: Column<T,S,C>> name(name:String, type: IColumnType<T, S,C>) =
      NamedFieldAccessor(name, type)

  @PublishedApi
  internal class NamedFieldAccessor<T:Any, S: IColumnType<T, S, C>, C:Column<T,S,C>>(val name:String, type: IColumnType<T, S, C>): TypeFieldAccessor<T, S, C>(type) {
    override fun name(property: kotlin.reflect.KProperty<*>): String = this.name
  }

  override fun appendDDL(appendable: Appendable) {
    appendable.appendln("CREATE TABLE `$_name` (")
    sequenceOf(_cols.asSequence().map {it.toDDL()},
               _primaryKey?.let {sequenceOf( toDDL("PRIMARY KEY", it))},
               _indices.asSequence().map { toDDL("INDEX", it)},
               _uniqueKeys.asSequence().map { toDDL("UNIQUE", it) },
               _foreignKeys.asSequence().map { it.toDDL() })
          .filterNotNull()
          .flatten()
          .joinTo(appendable, ",$LINE_SEPARATOR  ", "  ")
    appendable.appendln().append(')')
    _extra?.let { appendable.append(' ').append(_extra)}
    appendable.append(';')
  }

  override fun createTransitive(connection: DBConnection, ifNotExists: Boolean) {
    if (ifNotExists && connection.hasTable(this)) return // Make sure to check first to prevent loops

    val db = connection.db
    val neededTables = (_cols.asSequence().mapNotNull { col -> col.references?.table } +
          _foreignKeys.asSequence().mapNotNull(ForeignKey::toTable)).map { db[it._name] }.toSet()

    neededTables.forEach { if (it!=this) it.createTransitive(connection, true) }

    connection.prepareStatement(buildString { appendDDL(this) }) {
      execute()
    }
  }

  override fun dropTransitive(connection: DBConnection, ifExists: Boolean) {
    fun tableReferencesThis(table:Table): Boolean {
      return table._foreignKeys.any { fk -> fk.toTable._name == _name }
    }

    if (ifExists && ! connection.hasTable(this)) return
    val db = connection.db

    db._tables.filter(::tableReferencesThis).forEach { it.dropTransitive(connection, true) }

    connection.prepareStatement("DROP TABLE $_name") { execute() }
  }

  override fun ensureTable(connection: DBConnection, retainExtraColumns: Boolean) {
    val extraColumns = ArrayList<String>()
    val missingColumns = HashSet<Column<*,*,*>>(_cols)


    connection.getMetaData().getColumns(null, null, _name, null).use { rs ->
      while (rs.next()) {
        val colName = rs.columnName
        val col = column(colName)
        missingColumns.remove(col)
        if (col!=null) {
          val columnCorrect = col.matches(rs.typeName, rs.columnSize, rs.isNullable?.not(), rs.isAutoIncrement, rs.columnDefault, rs.remarks)
          if (columnCorrect == false) {
            connection.prepareStatement("ALTER TABLE $_name MODIFY COLUMN ${col.toDDL()}") {
              executeUpdate()
            }
          }
        } else {
          extraColumns.add(colName)
        }
      }
    }

    if (! retainExtraColumns) {
      for (extraColumn in extraColumns) {
        connection.prepareStatement("ALTER TABLE $_name DROP COLUMN `$extraColumn`") {
          executeUpdate()
        }
      }
    }

    for(missingColumn in missingColumns) {
      connection.prepareStatement("ALTER TABLE $_name ADD COLUMN ${missingColumn.toDDL()}") {
        executeUpdate()
      }
    }
  }
}


internal fun toDDL(first:CharSequence, cols: List<ColumnRef<*,*,*>>):CharSequence {
  return cols.joinToString("`, `", "$first (`","`)") { it.name }
}

class WarningIterator(initial: SQLWarning?) :AbstractIterator<SQLWarning>() {
  var current = initial
  override fun computeNext() {
    val w = current
    if (w != null) {
      setNext(w)
      current = w.nextWarning
    } else {
      done()
    }
  }
}