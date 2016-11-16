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

import uk.ac.bournemouth.kotlinsql.ColumnType.NumericColumnType.INT_T
import uk.ac.bournemouth.util.kotlin.sql.DBConnection
import uk.ac.bournemouth.util.kotlin.sql.StatementHelper
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.DatabaseMethods
import uk.ac.bournemouth.util.kotlin.sql.impl.gen._Statement1
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.memberProperties

/**
 * This is an abstract class that contains a set of database tables.
 *
 * @property _version The version of the database schema. This can in the future be used for updating
 * @property _tables The actual tables defined in the database
 */

@Suppress("unused")
abstract class Database constructor(val _version:Int): DatabaseMethods() {

  companion object {

    fun createUpdateStatement(update: _UpdateBase, where:WhereClause?):UpdatingStatement =
          if (where==null) update else _UpdateStatement(update, where)

    private fun tablesFromObjects(container: KClass<out Database>): List<Table> {
      return container.nestedClasses.map { it.objectInstance as? Table }.filterNotNull()
    }


    internal fun createTablePrefixMap(tableNames: SortedSet<String>): Map<String, String>? {

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

    private fun tablesFromProperties(db: Database): List<Table> {

      fun isTable(property:KProperty<*>): Boolean {
        return Table::class.java.isAssignableFrom(property.javaGetter?.returnType ?: property.javaField?.type)
      }

      return db.javaClass.kotlin.memberProperties.asSequence()
            .filter (::isTable)
            .map { property -> (property.get(db)) as Table }
            .toList()
    }
  }

  val _tables:List<Table> by lazy {
    val result = ArrayList<Table>()
    result.addAll(tablesFromObjects(this.javaClass.kotlin))
    tablesFromProperties(this)
          .filter { table -> ! result.any { table._name == it._name} }
          .forEach { result.add(it) }
    result
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
  protected class TableDelegate<out T: ImmutableTable>(private val table: T, private var needsCheck:Boolean=true) {
    operator fun getValue(thisRef: Database, property: KProperty<*>): T {
      if (needsCheck) {
        if (table._name != property.name) throw IllegalArgumentException("The table names do not match (${table._name}, ${property.name})")
        needsCheck = false
      }
      return table
    }
  }

  operator fun get(key:String): Table {
    return _tables.find { it._name==key } ?: throw NoSuchElementException("There is no table with the key $key")
  }

  override operator fun get(key:TableRef) = get(key._name)

  inline fun <R> connect(datasource: DataSource, block: DBConnection.()->R): R {
    val connection = datasource.connection
    var doCommit = true
    try {
      return DBConnection(connection, this).block()
    } catch (e:Exception) {
      try {
        connection.rollback()
      } finally {
        connection.close()
        doCommit = false
      }
      throw e
    } finally {
      if (doCommit) connection.commit()
      connection.close()
    }
  }

  fun ensureTables(connection:DBConnection, retainExtraColumns: Boolean = true) {
    val missingTables = _tables.map { it._name }.toMutableSet()
    val tablesToVerify = ArrayList<String>()
    connection.getMetaData().getTables(null, null, null, arrayOf("TABLE")).use { rs ->
      while (rs.next()) {
        val tableName = rs.tableName
        missingTables.remove(tableName)
        tablesToVerify.add(tableName)
      }
    }

    for(tableName in tablesToVerify) {
      get(tableName).ensureTable(connection, retainExtraColumns)
    }

    for(tableName in missingTables) {
      get(tableName).createTransitive(connection, true)
    }
  }

  abstract class WhereClause:WhereValue()

  private class WhereCombine(val left:WhereClause, val rel:String, val right:WhereClause):BooleanWhereValue() {
    override fun toSQL(prefixMap: Map<String, String>?): String {
      return "( ${left.toSQL(prefixMap)} $rel ${right.toSQL(prefixMap)} )"
    }

    override fun setParameters(statementHelper: StatementHelper, first: Int): Int {
      val i = left.setParameters(statementHelper, first)
      return right.setParameters(statementHelper, i)
    }
  }

  private class WhereBooleanUnary(val rel:String, val base:WhereClause):BooleanWhereValue() {
    override fun toSQL(prefixMap: Map<String, String>?)= "$rel ${base.toSQL(prefixMap)}"

    override fun setParameters(statementHelper: StatementHelper, first: Int): Int {
      return base.setParameters(statementHelper, first)
    }
  }

  private class WhereLike<C : ICharColumn<*,C>>(val col:ColumnRef<*,*,C>, val predicate:String): BooleanWhereValue() {

    override fun toSQL(prefixMap:Map<String,String>?)= "${col.name(prefixMap)} LIKE ?"

    override fun setParameters(statementHelper: StatementHelper, first: Int): Int {
      statementHelper.setString(first, predicate)
      return first+1
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

    infix fun WhereClause?.AND(other:WhereClause?):WhereClause? {
      return if (this==null) {
        other
      } else if (other==null) {
        this
      } else {
        WhereCombine(this, "AND", other)
      }
    }
    infix fun WhereClause.OR(other:WhereClause):WhereClause = WhereCombine(this, "OR", other)
    infix fun WhereClause.XOR(other:WhereClause):WhereClause = WhereCombine(this, "XOR", other)

    infix fun <C:ICharColumn<*,C>> ColumnRef<*, *, C>.LIKE(pred:String):WhereClause {
      return WhereLike(this, pred)
    }

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

  interface Statement {
    fun createTablePrefixMap(): Map<String, String>?
    fun toSQL(prefixMap: Map<String, String>?):String
  }

  interface ParameterizedStatement: Statement {

    /** Set the parameter values */
    fun setParams(statementHelper: StatementHelper, first: Int = 1): Int

  }

  interface OldStatement2 {
    /** Generate the SQL corresponding to the statement.*/
    fun toSQL(): String
  }

  interface Query:Statement

  interface ParameterizedQuery: Query, ParameterizedStatement

  interface UpdatingStatement:Statement {
    fun executeUpdate(connection: DBConnection):Int
  }


  interface SelectStatement: ParameterizedQuery /*, OldStatement*/ {
    val select:Select

    fun toSQL():String

    fun executeList(connection: DBConnection,
                resultHandler: (List<Column<*, *, *>>, List<Any?>) -> Unit): Boolean
  }

  abstract class WhereValue internal constructor() {
    abstract fun toSQL(prefixMap: Map<String, String>?):String
    abstract fun setParameters(statementHelper: StatementHelper, first:Int=1):Int
  }

  abstract class BooleanWhereValue:WhereClause()

  abstract class ConstBooleanWhereValue:BooleanWhereValue() {
    override fun setParameters(statementHelper: StatementHelper, first:Int) = first
  }

  abstract class RefWhereValue:WhereValue()

  abstract class _Statement1Base<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>>(override val select:_Select1<T1,S1,C1>, where:WhereClause):_StatementBase(where), Select1<T1,S1,C1> {

    /**
     * Get a single (optional) result
     */
    override fun getSingleOrNull(connection:DBConnection): T1? {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute { rs ->
          if (rs.next()) {
            if (!rs.isLast) throw SQLException("Multiple results found, where only one or none expected")
            select.col1.type.fromResultSet(rs, 1)
          } else {
            null
          }
        }
      }
    }

    override fun hasRows(connection: DBConnection): Boolean {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        executeHasRows()
      }
    }

    override fun getSingle(connection: DBConnection): T1 {
      return getSingleOrNull(connection) ?: throw NoSuchElementException()
    }

  }

  class _UpdateStatement(val update: _UpdateBase, val where: WhereClause): UpdatingStatement, ParameterizedStatement {
    fun toSQL(): String {
      return toSQL(createTablePrefixMap())
    }

    override fun createTablePrefixMap() = null

    override fun toSQL(prefixMap: Map<String, String>?): String {
      return "${update.toSQL(prefixMap)} WHERE ${where.toSQL(prefixMap)}"
    }

    override fun setParams(statementHelper: StatementHelper, first: Int):Int {
      val next = update.setParams(statementHelper, first)
      return where.setParameters(statementHelper, next)
    }

    /**
     * Execute the statement.
     * @param connection The connection object to use for the query
     * @return The amount of rows changed
     * @see java.sql.PreparedStatement.executeUpdate
     */
    override fun executeUpdate(connection: DBConnection):Int {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        executeUpdate()
      }
    }

  }

  abstract class _StatementBase(val where:WhereClause): SelectStatement, ParameterizedStatement {
    override fun toSQL() = toSQL(createTablePrefixMap())

    override fun createTablePrefixMap() = select.createTablePrefixMap()

    override fun toSQL(prefixMap: Map<String, String>?): String {
      return "${select.toSQL(prefixMap)} WHERE ${where.toSQL(prefixMap)}"
    }

    override fun setParams(statementHelper: StatementHelper, first: Int) =
          where.setParameters(statementHelper, first)

    override fun executeList(connection:DBConnection, resultHandler: (List<Column<*,*,*>>, List<Any?>)->Unit):Boolean {
      return executeHelper(connection, resultHandler) { rs, block ->
        val data = select.columns.mapIndexed { i, column -> column.type.fromResultSet(rs, i+1) }
        block(select.columns.asList(), data)
      }
    }
  }

  class _ListSelectStatement(override val select: _ListSelect, where: WhereClause): _StatementBase(where)

  class WhereEq(val left:ColumnRef<*,*,*>, val rel:String, val right:RefWhereValue):BooleanWhereValue() {
    override fun toSQL(prefixMap: Map<String, String>?)="${left.name(prefixMap)} $rel ${right.toSQL(prefixMap)}"
    override fun setParameters(statementHelper: StatementHelper, first: Int): Int {
      return right.setParameters(statementHelper, first)
    }
  }

  interface Select: SelectStatement {
    val columns:Array<out Column<*,*,*>>
    fun WHERE(config: _Where.() -> WhereClause?): SelectStatement
  }

  class _ListSelect(columns:List<Column<*,*,*>>):_BaseSelect(*columns.toTypedArray()) {
    override fun WHERE(config: _Where.() -> WhereClause?): SelectStatement {
      return _Where().config()?.let {_ListSelectStatement(this, it) } ?: this
    }
  }

  abstract class _BaseSelect(vararg override val columns:Column<*,*,*>):SelectStatement, Select {
    override val select: Select get() = this

    override fun toSQL(): String {
      val tableNames = tableNames()
      return if (tableNames.size>1) toSQL(createTablePrefixMap(tableNames)) else toSQL(null)
    }

    override fun toSQL(prefixMap:Map<String,String>?): String {
      if (prefixMap!=null) {
        return toSQLMultipleTables(prefixMap)
      } else {
        return columns.joinToString("`, `", "SELECT `", "` FROM ${columns[0].table._name}") { it.name }
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

    override fun executeList(connection: DBConnection,
                         resultHandler: (List<Column<*, *, *>>, List<Any?>) -> Unit):Boolean {
      return executeHelper(connection, resultHandler) { rs, block ->
        val data = select.columns.mapIndexed { i, column -> column.type.fromResultSet(rs, i+1) }
        block(select.columns.asList(), data)
      }
    }

    /**
     * Execute the statement.
     * @param connection The connection object to use for the query
     * @return The amount of rows changed
     * @see java.sql.PreparedStatement.executeUpdate
     */
    fun execute(connection: DBConnection):Boolean {
      connection.prepareStatement(toSQL()) {
        setParams(this)
        return execute()
      }
    }

  }


  interface Select1<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>>:SelectStatement {
    override val select: _Select1<T1, S1, C1>

    fun getSingle(connection: DBConnection): T1
    fun getSingleOrNull(connection: DBConnection): T1?
    fun getList(connection: DBConnection): List<T1?>

    @Deprecated("This can be done outside the function", ReplaceWith("getList(connection).filterNotNull()"), DeprecationLevel.ERROR)
    fun getSafeList(connection: DBConnection): List<T1> = getList(connection).filterNotNull()

    fun execute(connection:DBConnection, block: (T1?)->Unit):Boolean
    fun  hasRows(connection: DBConnection): Boolean

  }


  class _Select1<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1,C1>>(val col1:C1):Select, Select1<T1,S1,C1> {
    override val select: _Select1<T1,S1,C1> get() = this

    override val columns: Array<out Column<*, *, *>> get() = arrayOf<Column<*,*,*>>(col1)

    override fun WHERE(config: _Where.() -> WhereClause?): Select1<T1, S1, C1> {
      return _Where().config()?.let { _Statement1(this, it) } ?: this
    }

    override fun createTablePrefixMap(): Map<String, String>? = null

    override fun toSQL(): String = "SELECT `${col1.name}` FROM `${col1.table._name}`"
    override fun toSQL(prefixMap: Map<String, String>?) = toSQL()

    override fun setParams(statementHelper: StatementHelper, first: Int) = first

    /**
     * Execute the statement.
     * @param connection The connection object to use for the query
     * @return The amount of rows changed
     * @see java.sql.PreparedStatement.executeUpdate
     */
    fun execute(connection: DBConnection):Boolean {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute()
      }
    }

    override fun execute(connection:DBConnection, block: (T1?)->Unit):Boolean {
      return executeHelper(connection, block) { rs, block ->
        block(col1.type.fromResultSet(rs, 1))
      }
    }

    override fun getSingleOrNull(connection: DBConnection): T1? {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        execute { rs ->
          if (rs.next()) {
            if (!rs.isLast) throw SQLException("Multiple results found, where only one or none expected")
            select.col1.type.fromResultSet(rs, 1)
          } else {
            null
          }
        }
      }
    }

    override fun hasRows(connection: DBConnection): Boolean {
      return connection.prepareStatement(toSQL()) {
        setParams(this)
        executeHasRows()
      }
    }

    override fun getSingle(connection: DBConnection): T1 {
      return getSingleOrNull(connection) ?: throw NoSuchElementException()
    }

    override fun executeList(connection: DBConnection, resultHandler: (List<Column<*, *, *>>, List<Any?>) -> Unit):Boolean {
      return executeHelper(connection, resultHandler) { rs, block ->
        val data = select.columns.mapIndexed { i, column -> column.type.fromResultSet(rs, i+1) }
        block(select.columns.asList(), data)
      }
    }

    fun <R>getList(connection: DBConnection, factory:(T1?)->R): List<R> {
      val result=mutableListOf<R>()
      execute(connection) { p1 -> result.add(factory(p1)) }
      return result
    }

    override fun getList(connection: DBConnection): List<T1?> {
      val result=mutableListOf<T1?>()
      execute(connection) { p1 -> result.add(p1) }
      return result
    }
  }

  abstract class _UpdateBase internal constructor(val table:TableRef): UpdatingStatement, ParameterizedStatement {
    override fun createTablePrefixMap() = null

    fun WHERE(config: _Where.() -> WhereClause?) = createUpdateStatement(this, _Where().config())

    override fun setParams(statementHelper: StatementHelper, first: Int) = first
  }

  class _Delete internal constructor(table:TableRef): _UpdateBase(table) {

    override fun toSQL(prefixMap: Map<String, String>?): String {
      return "DELETE FROM ${table._name}"
    }

    override fun executeUpdate(connection: DBConnection):Int {
      connection.prepareStatement(toSQL(createTablePrefixMap())) {
        return executeUpdate()
      }
    }
  }

  class _Update internal constructor(val sets:List<_Set<*,*,*>>, table:TableRef): _UpdateBase(table) {
    override fun toSQL(prefixMap: Map<String, String>?): String {
      return sets.joinToString(", ", "UPDATE ${table._name} SET ") { set -> "${set.column.name} = ?"}
    }

    override fun setParams(statementHelper: StatementHelper, first: Int): Int {
      sets.forEachIndexed { i, set ->
        set.setParam(statementHelper, first+i)
      }
      return first+sets.size
    }

    override fun executeUpdate(connection: DBConnection): Int {
      throw UnsupportedOperationException()
    }
  }

  data class _Set<T:Any, S: IColumnType<T,S,C>, C:Column<T,S,C>>(val column:ColumnRef<T,S,C>, val value:T?) {
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

  interface Insert:UpdatingStatement {
    fun toSQL():String

    fun <T:Any, S:IColumnType<T, S, *>, R> execute(connection: DBConnection, key: Column<T,S,*>, autogeneratedKeys: (T?) -> R): List<R>
    fun <T:Any, S:IColumnType<T, S, *>> execute(connection: DBConnection, key: Column<T,S,*>):List<T?>
  }

  abstract class _BaseInsert(val table:Table, val update:Boolean, vararg val columns:ColumnRef<*,*,*>):Insert {
    override fun createTablePrefixMap(): Map<String, String>? {
      return null
    }

    protected val batch = mutableListOf<_BaseInsertValues>()

    override fun toSQL() =
          toSQL(createTablePrefixMap())

    override fun toSQL(prefixMap: Map<String, String>?): String {
      return buildString {
        columns.joinTo(this, ", ", "INSERT INTO ${table.name(prefixMap)} (", ")") { col -> col.name(prefixMap) }
        (1..columns.size).joinTo(this, prefix= " VALUES (", postfix=")") { "?" }
        if (update) {
          val primaryKey = table._primaryKey
          val updateColumns = columns.asSequence()
                .filter { primaryKey?.let { pk -> it !in pk } ?: true }.toList()
          if (updateColumns.isEmpty()) { throw UnsupportedOperationException("An insert that only touches the primary key cannot be an update."); }
          append(" ON DUPLICATE KEY UPDATE ")
          updateColumns.asSequence()
                .map { it.name(prefixMap)}
                .joinTo(this) { "$it = VALUES($it)" }
        }
      }
    }

    abstract inner class _BaseInsertValues(vararg val values:Any?) {

      fun setParams(statementHelper: StatementHelper, first: Int = 1):Int {
        for(i in columns.indices) {
          columns[i].type.castSetParam(statementHelper, first+i, values[i])
        }
        return first+columns.size
      }

      override fun toString() = values.joinToString(prefix = "( ", postfix = " )", transform = Any?::toString)
    }

    /**
     * Execute the statement. If the table has an  autoincrement column and no autoincrement column was specified,
     * the generated values will be recorded, otherwise not.
     * @param connection The connection object to use for the query
     * @return The amount of rows changed
     * @see java.sql.PreparedStatement.executeUpdate
     */
    override fun executeUpdate(connection: DBConnection):Int {
      if (batch.isEmpty()) { return 0 }
      val query = toSQL()
      try {
        connection.prepareStatement(query) {
          for (valueSet in batch) {
            valueSet.setParams(this)
            addBatch()
          }
          return executeBatch().filter { it >= 0 }.sum()
        }
      } catch (e: SQLException) {
        throw SQLException("Failure to update:\n  QUERY: $query", e)
      }
    }

    /**
     * Execute the statement.
     * @param connection The connection object to use for the query
     * @return A list of the results.
     * @see java.sql.PreparedStatement.executeUpdate
     */
    override fun <T:Any, S:IColumnType<T, S, *>, R> execute(connection: DBConnection, key: Column<T,S,*>, autogeneratedKeys: (T?)->R):List<R> {
      if (batch.isEmpty()) { throw IllegalStateException("There are no values to add") }
      connection.prepareStatement(toSQL(), true) {
        for(valueSet in batch) {
          valueSet.setParams(this)
          addBatch()
        }
        val count = executeUpdate()
        if (count>0) {
          withGeneratedKeys { rs ->
            return mutableListOf<R>().apply {
              while (rs.next()) {
                add(autogeneratedKeys(key.type.fromResultSet(rs, 1)))
              }
            }

          }
        } else {
          return emptyList()
        }
      }
    }

    /**
     * Execute the statement.
     * @param connection The connection object to use for the query
     * @return A list with the resulting rows.
     * @see java.sql.PreparedStatement.executeUpdate
     */
    override fun <T:Any, S:IColumnType<T, S, *>> execute(connection: DBConnection, key: Column<T,S,*>):List<T> {
      if (batch.isEmpty()) { throw IllegalStateException("There are no values to add") }
      connection.prepareStatement(toSQL()) {
        for(valueSet in batch) {
          valueSet.setParams(this)
          addBatch()
        }
        withGeneratedKeys { rs ->
          return mutableListOf<T>().apply {
            while (rs.next()) {
              add(key.type.fromResultSet(rs, 1)!!)
            }
          }

        }
      }
    }
  }

  fun DELETE_FROM(table: Table) = _Delete(table)

  fun UPDATE(config:_UpdateBuilder.()->Unit)= _UpdateBuilder().apply(config).build()

  fun COUNT(col:ColumnRef<*,*,*>): NumericColumn<Int, INT_T> {
    return CountColumn(col)
  }

  fun SELECT(columns: List<Column<*,*,*>>) = _ListSelect(columns)

}

internal fun <T> Database.SelectStatement.executeHelper(connection: DBConnection, block: T, invokeHelper:(ResultSet, T)->Unit):Boolean {
  return connection.prepareStatement(toSQL()) {
    val select: Database.SelectStatement = this@executeHelper
    select.setParams(this)
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

/**
 * Get a single element or a null value according to the conversion function passed. This will throw if the result count
 * is larger than 1.
 *
 * @param connection The connection to use
 * @param resultHandler The function that transforms the query result into a result object.
 *
 * @throws SQLException If there are more results than expected, or if there is an underlying SQL error.
 */
fun <R> Database.SelectStatement.getSingleListOrNull(connection: DBConnection,
                                                     resultHandler: (List<Column<*, *, *>>, List<Any?>) -> R):R? {
  connection.prepareStatement(toSQL()) {
    setParams(this)
    execute { rs ->
      if(!rs.next()) return null
      val columns = select.columns

      val data = columns.mapIndexed { i, column -> column.type.fromResultSet(rs, i + 1) }
      val result = resultHandler(columns.toList(), data)
      if (rs.next()) throw SQLException("More results than expected")
      return result
    }
  }
}

@Suppress("unused")
    /**
 * Get a single result item, but don't accept an empty result. Otherwise this is the same as [getSingleListOrNull].
 *
 * @see getSingleListOrNull
 */
fun <R> Database.SelectStatement.getSingleList(connection: DBConnection,
                                               resultHandler: (List<Column<*, *, *>>, List<Any?>) -> R):R {
  return getSingleListOrNull(connection, resultHandler) ?: throw NoSuchElementException()
}


private fun ColumnRef<*,*,*>.name(prefixMap: Map<String, String>?) : String {
  return prefixMap?.let { prefixMap[table._name]?.let { "$it.`$name`" } } ?: "`$name`"
}

private fun TableRef.name(prefixMap: Map<String, String>?) : String {
  return prefixMap?.let { prefixMap[this._name]?.let { "`$_name` AS $it" } } ?: "`$_name`"
}

/** A reference to a table. */
interface TableRef {
  /** The name of the table. */
  val _name:String
}
