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

import uk.ac.bournemouth.util.kotlin.sql.DBConnection
import uk.ac.bournemouth.util.kotlin.sql.StatementHelper
import uk.ac.bournemouth.util.kotlin.sql.connection
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.DatabaseMethods
import uk.ac.bournemouth.util.kotlin.sql.impl.gen._Statement1
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Created by pdvrieze on 03/04/16.
 */

/**
 * This is an abstract class that contains a set of database tables.
 *
 * @property _version The version of the database schema. This can in the future be used for updating
 * @property _tables The actual tables defined in the database
 */

abstract class Database private constructor(val _version:Int, val _tables:List<Table>): DatabaseMethods() {

  companion object {
    private fun tablesFromObjects(container: KClass<out Database>): List<Table> {
      return container.nestedClasses.map { it.objectInstance as? Table }.filterNotNull()
    }
  }

  /**
   * Constructor for creating a new Database implementation.
   *
   * @param version The version of the database configuration.
   * @param block The configuration block where the database tables are added.
   */
  constructor(version:Int, block:DatabaseConfiguration.()->Unit):this(version, DatabaseConfiguration().apply(block))

  private constructor(version:Int, config:DatabaseConfiguration): this(version, config.tables)

  constructor(version:Int): this(version, mutableListOf()) {
    // This has to be initialised here as the class object is not avaiable before the super constructor is called.
    // The cast is needed as we know it is actually a mutable list.
    (_tables as MutableList<Table>).addAll(tablesFromObjects(javaClass.kotlin))
  }

  /**
   * Delegate function to be used to reference tables. Note that this requires the name of the property to match the name
   * of the table.
   */
  protected inline fun <T: ImmutableTable> ref(table: T)= TableDelegate(table)

  /**
   * Delegate function to be used to reference tables. This delegate allows for renaming, by removing the need for checking.
   */
  protected inline fun <T: ImmutableTable> rename(table: T)= TableDelegate(table, false)


  /**
   * Helper class that implements the actual delegation of table access.
   */
  protected class TableDelegate<T: ImmutableTable>(private val table: T, private var needsCheck:Boolean=true) {
    operator fun getValue(thisRef: Database, property: KProperty<*>): T {
      if (needsCheck) {
        if (table._name != property.name) throw IllegalArgumentException("The table names do not match (${table._name}, ${property.name})")
        needsCheck = false
      }
      return table
    }
  }

  operator fun get(key:String): Table {
    return _tables.find { it._name==key } ?: throw NoSuchElementException("There is no table with the key ${key}")
  }

  fun <R> connect(datasource: DataSource, block: DBConnection.()->R): R {
    val connection = datasource.connection
    try {
      connection.autoCommit=false
      val result = DBConnection(connection, this).block()
      connection.commit()
      return result
    } catch (e:Exception) {
      connection.rollback()
      throw e
    } finally {
      connection.close()
    }
  }


  abstract class WhereClause:WhereValue() {

  }

  private class WhereCombine(val left:WhereClause, val rel:String, val right:WhereClause):BooleanWhereValue() {
    override fun toSQL(prefixMap: Map<String, String>?): String {
      return "( ${left.toSQL(prefixMap)} $rel ${right.toSQL(prefixMap)} )"
    }

    override fun setParameters(statementHelper: StatementHelper, first: Int): Int {
      val i = left.setParameters(statementHelper)
      return right.setParameters(statementHelper,i)
    }
  }

  private class WhereBooleanUnary(val rel:String, val base:WhereClause):BooleanWhereValue() {
    override fun toSQL(prefixMap: Map<String, String>?)= "$rel ${base.toSQL(prefixMap)}"

    override fun setParameters(statementHelper: StatementHelper, first: Int): Int {
      return base.setParameters(statementHelper, first)
    }
  }

  private class WhereCmpCol<S1 : ColumnType<*,S1,*>, S2 : ColumnType<*,S2,*>>(val col1:ColumnRef<*,S1,*>, val cmp:SqlComparisons, val col2:ColumnRef<*,S2,*>): BooleanWhereValue() {

    override fun toSQL(prefixMap:Map<String,String>?)= "${col1.name(prefixMap)} $cmp ${col2.name(prefixMap)}"

    override fun setParameters(statementHelper: StatementHelper, first: Int) = first
  }

  private class WhereCmpParam<T:Any, S: IColumnType<T, S, *>>(val ref:ColumnRef<T,S,*>, val cmp:SqlComparisons, val value: T?): BooleanWhereValue() {
    override fun toSQL(prefixMap:Map<String,String>?) = "`${ref.name}` $cmp ?"

    override fun setParameters(statementHelper: StatementHelper, first: Int):Int {
      ref.type.setParam(statementHelper, first, value)
      return first+1
    }
  }

  class _Where {

    infix fun <T:Any, S: IColumnType<T, S, *>> ColumnRef<T, S, *>.eq(value: T): BooleanWhereValue = WhereCmpParam(this, SqlComparisons.eq, value)
    infix fun <T:Any, S: IColumnType<T, S, *>> ColumnRef<T, S, *>.ne(value: T): BooleanWhereValue = WhereCmpParam(this, SqlComparisons.ne, value)
    infix fun <T:Any, S: IColumnType<T, S, *>> ColumnRef<T, S, *>.lt(value: T): BooleanWhereValue = WhereCmpParam(this, SqlComparisons.lt, value)
    infix fun <T:Any, S: IColumnType<T, S, *>> ColumnRef<T, S, *>.le(value: T): BooleanWhereValue = WhereCmpParam(this, SqlComparisons.le, value)
    infix fun <T:Any, S: IColumnType<T, S, *>> ColumnRef<T, S, *>.gt(value: T): BooleanWhereValue = WhereCmpParam(this, SqlComparisons.gt, value)
    infix fun <T:Any, S: IColumnType<T, S, *>> ColumnRef<T, S, *>.ge(value: T): BooleanWhereValue = WhereCmpParam(this, SqlComparisons.ge, value)

    infix fun <S1 : ColumnType<*,S1,*>, S2 : ColumnType<*,S2,*>> ColumnRef<*, S1, *>.eq(other: ColumnRef<*,S2,*>): BooleanWhereValue = WhereCmpCol(this, SqlComparisons.eq, other)
    infix fun <S1 : ColumnType<*,S1,*>, S2 : ColumnType<*,S2,*>> ColumnRef<*, S1, *>.ne(other: ColumnRef<*,S2,*>): BooleanWhereValue = WhereCmpCol(this, SqlComparisons.ne, other)
    infix fun <S1 : ColumnType<*,S1,*>, S2 : ColumnType<*,S2,*>> ColumnRef<*, S1, *>.lt(other: ColumnRef<*,S2,*>): BooleanWhereValue = WhereCmpCol(this, SqlComparisons.lt, other)
    infix fun <S1 : ColumnType<*,S1,*>, S2 : ColumnType<*,S2,*>> ColumnRef<*, S1, *>.le(other: ColumnRef<*,S2,*>): BooleanWhereValue = WhereCmpCol(this, SqlComparisons.le, other)
    infix fun <S1 : ColumnType<*,S1,*>, S2 : ColumnType<*,S2,*>> ColumnRef<*, S1, *>.gt(other: ColumnRef<*,S2,*>): BooleanWhereValue = WhereCmpCol(this, SqlComparisons.gt, other)
    infix fun <S1 : ColumnType<*,S1,*>, S2 : ColumnType<*,S2,*>> ColumnRef<*, S1, *>.ge(other: ColumnRef<*,S2,*>): BooleanWhereValue = WhereCmpCol(this, SqlComparisons.ge, other)

    infix fun WhereClause.AND(other:WhereClause):WhereClause = WhereCombine(this, "AND", other)
    infix fun WhereClause.OR(other:WhereClause):WhereClause = WhereCombine(this, "OR", other)
    infix fun WhereClause.XOR(other:WhereClause):WhereClause = WhereCombine(this, "XOR", other)

    fun NOT(other:WhereClause):WhereClause = WhereBooleanUnary("NOT", other)

    infix fun ColumnRef<*,*,*>.IS(ref:RefWhereValue) = WhereEq(this,"IS",ref)
    infix fun ColumnRef<*,*,*>.IS_NOT(ref:RefWhereValue) = WhereEq(this,"IS NOT",ref)

    //    fun ISNOT(other:WhereClause):WhereClause = WhereUnary("NOT", other)

    val TRUE:BooleanWhereValue=object:ConstBooleanWhereValue() { override fun toSQL(prefixMap: Map<String, String>?)= "TRUE" }
    val FALSE:BooleanWhereValue=object:ConstBooleanWhereValue() { override fun toSQL(prefixMap: Map<String, String>?)= "FALSE" }
    val UNKNOWN:BooleanWhereValue=object:ConstBooleanWhereValue() { override fun toSQL(prefixMap: Map<String, String>?)= "UNKNOWN" }
    val NULL:RefWhereValue=object:RefWhereValue() {
      override fun toSQL(prefixMap: Map<String, String>?)= "NULL"
      override fun setParameters(statementHelper: StatementHelper, first: Int) = first
    }
  }

  interface  Statement {
    /** Generate the SQL corresponding to the statement.*/
    fun toSQL(): String

    /** Set the parameter values */
    fun setParams(statementHelper: StatementHelper, first: Int =1): Int
  }

  interface SelectStatement:Statement {
    val select:Select
  }

  abstract class WhereValue internal constructor() {
    abstract fun toSQL(prefixMap: Map<String, String>?):String
    abstract fun setParameters(statementHelper: StatementHelper, first:Int=1):Int
  }

  abstract class BooleanWhereValue:WhereClause() {
  }

  abstract class ConstBooleanWhereValue:BooleanWhereValue() {
    override fun setParameters(statementHelper: StatementHelper, first:Int) = first
  }

  abstract class RefWhereValue:WhereValue()

  abstract class _Statement1Base<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>>(override val select:_Select1<T1,S1,C1>, where:WhereClause):_StatementBase(where) {

    /**
     * Get a single (optional) result
     */
    fun getSingle(connection:DBConnection): T1? {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute { rs ->
          if (rs.next()) {
            if (!rs.isLast()) throw SQLException("Multiple results found, where only one or none expected")
            select.col1.type.fromResultSet(rs, 1)
          } else {
            null
          }
        }
      }
    }

    /**
     * Get a list of all values from the query. This can include null values.
     */
    fun getList(connection:DBConnection): List<T1?> {
      val result=mutableListOf<T1?>()
      executeHelper(connection, Unit) {rs, b -> result.add(select.col1.type.fromResultSet(rs,1))}
      return result

    }

    /** Get a list of all non-null values resulting from the query.*/
    fun getSafeList(connection:DBConnection): List<T1> {
      val result=mutableListOf<T1>()
      executeHelper(connection, Unit) {rs, b -> select.col1.type.fromResultSet(rs,1)?.let { result.add(it) } }
      return result

    }

  }

  class _UpdateStatement(val update: _UpdateBase, val where: WhereClause): Statement {
    override fun toSQL(): String {
      return "${update.toSQL()} WHERE ${where.toSQL(null)}"
    }

    override fun setParams(statementHelper: StatementHelper, first: Int):Int {
      val next = update.setParams(statementHelper, first)
      return where.setParameters(statementHelper, next)
    }

    /**
     * Execute the statement.
     * @param The connection object to use for the query
     * @return The amount of rows changed
     * @see java.sql.PreparedStatement.executeUpdate
     */
    fun execute(connection: DBConnection):Int {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        executeUpdate()
      }
    }

  }

  abstract class _StatementBase(val where:WhereClause): SelectStatement {
    override fun toSQL(): String {
      val prefixMap = select.createTablePrefixMap()
      return "${select.toSQL(prefixMap)} WHERE ${where.toSQL(prefixMap)}"
    }

    override fun setParams(statementHelper: StatementHelper, first: Int) =
          where.setParameters(statementHelper, first)

  }

  class WhereEq(val left:ColumnRef<*,*,*>, val rel:String, val right:RefWhereValue):BooleanWhereValue() {
    override fun toSQL(prefixMap: Map<String, String>?)="${left.name(prefixMap)} $rel ${right.toSQL(prefixMap)}"
    override fun setParameters(statementHelper: StatementHelper, first: Int): Int {
      return right.setParameters(statementHelper, first)
    }
  }

  interface Select: SelectStatement {
    fun createTablePrefixMap(): Map<String, String>?
    fun toSQL(prefixMap:Map<String,String>?): String
    fun WHERE(config: _Where.() -> WhereClause): SelectStatement
  }

  abstract class _BaseSelect(vararg val columns:Column<*,*,*>):Select {
    override val select: Select get() = this

    override fun toSQL(): String {
      val tableNames = tableNames()
      return if (tableNames.size>1) toSQL(createTablePrefixMap(tableNames)) else toSQL(null)
    }

    override fun toSQL(prefixMap:Map<String,String>?): String {
      if (prefixMap!=null) {
        return toSQLMultipleTables(prefixMap)
      } else {
        return columns.joinToString("`, `", "SELECT `", "`") { it.name }
      }
    }

    override fun setParams(statementHelper: StatementHelper, first: Int) = first

    private fun tableNames() = columns.map { it.table._name }.toSortedSet()

    private fun toSQLMultipleTables(prefixMap:Map<String,String>): String {

      return buildString {
        append("SELECT ")
        columns.joinTo(this, ", ") {column -> column.name(prefixMap)}
        append(" FROM ")
        prefixMap.entries.joinTo(this, ", `", "`") { pair -> "${pair.key}` AS ${pair.value}"}
      }
    }


    override fun createTablePrefixMap() = createTablePrefixMap(tableNames())

    private fun createTablePrefixMap(tableNames: SortedSet<String>): Map<String, String>? {

      fun uniquePrefix(string:String, usedPrefixes:Set<String>):String {
        for(i in 1..(string.length-1)) {
          string.substring(0,i).let {
            if (it !in usedPrefixes) return it
          }
        }
        for(i in 1..Int.MAX_VALUE) {
          (string + i.toString()).let {
            if (it !in usedPrefixes) return it
          }
        }
        throw IllegalArgumentException("No unique prefix could be found")
      }

      if (tableNames.size<=1) return null

      return tableNames.let {
        val seen = mutableSetOf<String>()
        it.associateTo(sortedMapOf<String,String>()) { name -> name to uniquePrefix(name, seen).apply { seen.add(this) } }
      }
    }

    /**
     * Execute the statement.
     * @param The connection object to use for the query
     * @return The amount of rows changed
     * @see java.sql.PreparedStatement.executeUpdate
     */
    fun execute(connection: DBConnection):Boolean {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute()
      }
    }

  }

  class _Select1<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1,C1>>(val col1:C1):Select {
    override val select: Select get() = this

    override fun WHERE(config: _Where.() -> WhereClause): _Statement1<T1, S1, C1> {
      return _Statement1(this, _Where().config())
    }

    override fun createTablePrefixMap(): Map<String, String>? = null

    override fun toSQL(): String = "SELECT `${col1.name}` FROM `${col1.table._name}`"
    override fun toSQL(prefixMap: Map<String, String>?) = toSQL()

    override fun setParams(statementHelper: StatementHelper, first: Int) = first

    /**
     * Execute the statement.
     * @param The connection object to use for the query
     * @return The amount of rows changed
     * @see java.sql.PreparedStatement.executeUpdate
     */
    fun execute(connection: DBConnection):Boolean {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute()
      }
    }

    fun execute(connection:DBConnection, block: (T1?)->Unit):Boolean {
      return executeHelper(connection, block) { rs, block ->
        block(col1.type.fromResultSet(rs, 1))
      }
    }

    fun <R>getList(connection: DBConnection, factory:(T1?)->R): List<R> {
      val result=mutableListOf<R>()
      execute(connection) { p1 -> result.add(factory(p1)) }
      return result
    }

    fun getList(connection: DBConnection): List<T1?> {
      val result=mutableListOf<T1?>()
      execute(connection) { p1 -> result.add(p1) }
      return result
    }

  }

  abstract class _UpdateBase internal constructor(val table:TableRef): Statement {
    fun WHERE(config: _Where.() -> WhereClause) = _UpdateStatement(this, _Where().config())


    override fun setParams(statementHelper: StatementHelper, first: Int) = first
  }

  class _Delete internal constructor(table:TableRef): _UpdateBase(table) {
    override fun toSQL(): String {
      return "DELETE FROM ${table._name}"
    }
  }

  class _Update internal constructor(val sets:List<_Set<*,*,*>>, table:TableRef): _UpdateBase(table) {
    override fun toSQL(): String {
      return sets.joinToString(", ", "UPDATE ${table._name} SET ") { set -> "${set.column.name} = ?"}
    }

    override fun setParams(statementHelper: StatementHelper, first: Int): Int {
      sets.forEachIndexed { i, set ->
        set.setParam(statementHelper, first+i)
      }
      return first+sets.size
    }
  }

  class _Set<T:Any, S: IColumnType<T,S,C>, C:Column<T,S,C>>(val column:ColumnRef<T,S,C>, val value:T?) {
    fun setParam(statementHelper: StatementHelper, pos:Int) {
      column.type.setParam(statementHelper, pos, value)
    }
  }

  class _UpdateBuilder internal constructor() {
    internal val sets = mutableListOf<_Set<*,*,*>>()

    fun <T:Any, S: IColumnType<T,S,C>, C:Column<T,S,C>> SET(column:ColumnRef<T,S,C>, value:T?) {
      sets.add(_Set(column, value))
    }

    fun build() = _Update(sets, sets.first().column.table)
  }


  interface Insert {
    fun toSQL():String
  }

  abstract class _BaseInsert(vararg val columns:ColumnRef<*,*,*>):Insert {
    override fun toSQL() =
          columns.joinToString(", ", "INSERT INTO ${columns[1].table._name} (", ")") { col -> col.name }

    abstract inner class _BaseInsertValues(vararg val values:Any?): Statement {
      override fun toSQL(): String {
        return buildString {
          append(this@_BaseInsert.toSQL())
          append(" VALUES (")
          (1..values.size).joinTo(this) { "?" }
          append(')')
        }
      }

      override fun setParams(statementHelper: StatementHelper, first: Int):Int {
        for(i in columns.indices) {
          columns[i].type.castSetParam(statementHelper, first+i, values[i])
        }
        return first+columns.size
      }


      /**
       * Execute the statement.
       * @param connection The connection object to use for the query
       * @return The amount of rows changed
       * @see java.sql.PreparedStatement.executeUpdate
       */
      fun execute(connection: DBConnection):Int {
        return connection.prepareStatement(toSQL()) {
          setParams(this)
          executeUpdate()
        }
      }
    }

  }

  fun DELETE_FROM(table: Table) = _Delete(table)

  fun UPDATE(config:_UpdateBuilder.()->Unit)= _UpdateBuilder().apply(config).build()


}

internal fun <T> Database.SelectStatement.executeHelper(connection: DBConnection, block: T, invokeHelper:(ResultSet, T)->Unit):Boolean {
  return connection.prepareStatement(toSQL()) {
    setParams(this)
    execute { rs ->
      if (rs.next()) {
        do {
          invokeHelper(rs, block)
        } while (rs.next())
        true
      } else {
        false
      }
    }
  }
}

enum class SqlComparisons(val str:String) {
  eq("="),
  ne("!="),
  lt("<"),
  le("<="),
  gt(">"),
  ge(">=");

  override fun toString() = str
}

private fun ColumnRef<*,*,*>.name(prefixMap: Map<String, String>?) : String {
  return prefixMap?.let { prefixMap[table._name]?.let { "${it}.`${name}`" } } ?: "`${name}`"
}

/** A reference to a table. */
interface TableRef {
  /** The name of the table. */
  val _name:String
}
