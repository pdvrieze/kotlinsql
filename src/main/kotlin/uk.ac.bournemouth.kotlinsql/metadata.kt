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
import java.io.Closeable
import java.sql.*
import java.util.*

/**
 * A class to handle access to connection metadata (available through jdbc.
 */

class ConnectionMetadata(private val metadata:DatabaseMetaData) {

  @Deprecated("The connection parameter is unused", ReplaceWith("ConnectionMetadata(metadata)"))
  constructor(connection: DBConnection, metadata:DatabaseMetaData):this(metadata)

  val maxColumnsInIndex: Int get() = metadata.maxColumnsInIndex

  fun insertsAreDetected(type: Int): Boolean = metadata.insertsAreDetected(type)

  fun getAttributes(catalog: String?,
                             schemaPattern: String?,
                             typeNamePattern: String?,
                             attributeNamePattern: String?): AttributeResults {
    return AttributeResults(metadata.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern))
  }

  val supportsSubqueriesInQuantifieds:Boolean get() = metadata.supportsSubqueriesInQuantifieds()

  val supportsIntegrityEnhancementFacility: Boolean get() = metadata.supportsIntegrityEnhancementFacility()

  val supportsGetGeneratedKeys: Boolean get() = metadata.supportsGetGeneratedKeys()

  val supportsCoreSQLGrammar: Boolean get() = metadata.supportsCoreSQLGrammar()

  val supportsDataDefinitionAndDataManipulationTransactions: Boolean get() = metadata.supportsDataDefinitionAndDataManipulationTransactions()

  val supportsCatalogsInTableDefinitions: Boolean get() = metadata.supportsCatalogsInTableDefinitions()

  val supportsOpenStatementsAcrossRollback: Boolean get() = metadata.supportsOpenStatementsAcrossRollback()

  val supportsStoredFunctionsUsingCallSyntax: Boolean get() = metadata.supportsStoredFunctionsUsingCallSyntax()

  val databaseProductName: String get() = metadata.databaseProductName

  val databaseProductVersion:String get() = metadata.databaseProductVersion

  val getJDBCMajorVersion: Int get() = metadata.jdbcMajorVersion

  val maxProcedureNameLength: Int get() = metadata.maxProcedureNameLength

  val getCatalogTerm: String get() = metadata.catalogTerm

  val supportsCatalogsInDataManipulation: Boolean get() = metadata.supportsCatalogsInDataManipulation()

  val getMaxUserNameLength: Int get() = metadata.maxUserNameLength

  val timeDateFunctions: List<String> get() = metadata.timeDateFunctions.split(',')

  val autoCommitFailureClosesAllResultSets: Boolean get() = metadata.autoCommitFailureClosesAllResultSets()

  val getMaxColumnsInSelect: Int get() = metadata.maxColumnsInSelect

  val catalogs: List<String> get() = metadata.catalogs.toStrings()

  val storesLowerCaseQuotedIdentifiers: Boolean get() = metadata.storesLowerCaseQuotedIdentifiers()

  val getMaxColumnsInOrderBy: Int get() = metadata.maxColumnsInOrderBy

  val getDriverMinorVersion: Int get() = metadata.driverMinorVersion


  val allProceduresAreCallable: Boolean get() = metadata.allProceduresAreCallable()

  // TO PROPERTIES

  val allTablesAreSelectable: Boolean get() = metadata.allTablesAreSelectable()

  val URL: String get() = metadata.url

  val userName: String get() = metadata.userName

  val isReadOnly: Boolean get() = metadata.isReadOnly

  val nullsAreSortedHigh: Boolean get() = metadata.nullsAreSortedHigh()

  val nullsAreSortedLow: Boolean get() = metadata.nullsAreSortedLow()

  val nullsAreSortedAtStart: Boolean get() = metadata.nullsAreSortedAtStart()

  val nullsAreSortedAtEnd: Boolean get() = metadata.nullsAreSortedAtEnd()

  val driverName: String get() = metadata.driverName

  val driverVersion: String get() = metadata.driverVersion

  val driverMajorVersion: Int get() = metadata.driverMajorVersion

  val driverMinorVersion: Int get() = metadata.driverMinorVersion

  val usesLocalFiles: Boolean get() = metadata.usesLocalFiles()

  val usesLocalFilePerTable: Boolean get() = metadata.usesLocalFilePerTable()

  val supportsMixedCaseIdentifiers: Boolean get() = metadata.supportsMixedCaseIdentifiers()

  val storesUpperCaseIdentifiers: Boolean get() = metadata.storesUpperCaseIdentifiers()

  val storesLowerCaseIdentifiers: Boolean get() = metadata.storesLowerCaseIdentifiers()

  val storesMixedCaseIdentifiers: Boolean get() = metadata.storesMixedCaseIdentifiers()

  val supportsMixedCaseQuotedIdentifiers: Boolean get() = metadata.supportsMixedCaseQuotedIdentifiers()

  val storesUpperCaseQuotedIdentifiers: Boolean get() = metadata.storesUpperCaseQuotedIdentifiers()

  val storesMixedCaseQuotedIdentifiers: Boolean get() = metadata.storesMixedCaseQuotedIdentifiers()

  val identifierQuoteString: String get() = metadata.identifierQuoteString

  val SQLKeywords: List<String> get() = metadata.sqlKeywords.split(',')

  val numericFunctions: List<String> get() = metadata.numericFunctions.split(',')

  val stringFunctions: List<String> get() = metadata.stringFunctions.split(',')

  val systemFunctions: List<String> get() = metadata.systemFunctions.split(',')

  val searchStringEscape: String get() = metadata.searchStringEscape

  val extraNameCharacters: String get() = metadata.extraNameCharacters

  val supportsAlterTableWithAddColumn: Boolean get() = metadata.supportsAlterTableWithAddColumn()

  val supportsAlterTableWithDropColumn: Boolean get() = metadata.supportsAlterTableWithDropColumn()

  val supportsColumnAliasing: Boolean get() = metadata.supportsColumnAliasing()

  val nullPlusNonNullIsNull: Boolean get() = metadata.nullPlusNonNullIsNull()

  val supportsConvert: Boolean get() = metadata.supportsConvert()

  fun supportsConvert(fromType: Int, toType: Int): Boolean = metadata.supportsConvert(fromType, toType)

  val supportsTableCorrelationNames: Boolean get() = metadata.supportsTableCorrelationNames()

  val supportsDifferentTableCorrelationNames: Boolean get() = metadata.supportsDifferentTableCorrelationNames()

  val supportsExpressionsInOrderBy: Boolean get() = metadata.supportsExpressionsInOrderBy()

  val supportsOrderByUnrelated: Boolean get() = metadata.supportsOrderByUnrelated()

  val supportsGroupBy: Boolean get() = metadata.supportsGroupBy()

  val supportsGroupByUnrelated: Boolean get() = metadata.supportsGroupByUnrelated()

  val supportsGroupByBeyondSelect: Boolean get() = metadata.supportsGroupByBeyondSelect()

  val supportsLikeEscapeClause: Boolean get() = metadata.supportsLikeEscapeClause()

  val supportsMultipleResultSets: Boolean get() = metadata.supportsMultipleResultSets()

  val supportsMultipleTransactions: Boolean get() = metadata.supportsMultipleTransactions()

  val supportsNonNullableColumns: Boolean get() = metadata.supportsNonNullableColumns()

  val supportsMinimumSQLGrammar: Boolean get() = metadata.supportsMinimumSQLGrammar()

  val supportsExtendedSQLGrammar: Boolean get() = metadata.supportsExtendedSQLGrammar()

  val supportsANSI92EntryLevelSQL: Boolean get() = metadata.supportsANSI92EntryLevelSQL()

  val supportsANSI92IntermediateSQL: Boolean get() = metadata.supportsANSI92IntermediateSQL()

  val supportsANSI92FullSQL: Boolean get() = metadata.supportsANSI92FullSQL()

  val supportsOuterJoins: Boolean get() = metadata.supportsOuterJoins()

  val supportsFullOuterJoins: Boolean get() = metadata.supportsFullOuterJoins()

  val supportsLimitedOuterJoins: Boolean get() = metadata.supportsLimitedOuterJoins()

  val schemaTerm: String get() = metadata.schemaTerm

  val procedureTerm: String get() = metadata.procedureTerm

  val catalogTerm: String get() = metadata.catalogTerm

  val isCatalogAtStart: Boolean get() = metadata.isCatalogAtStart

  val catalogSeparator: String get() = metadata.catalogSeparator

  val supportsSchemasInDataManipulation: Boolean get() = metadata.supportsSchemasInDataManipulation()

  val supportsSchemasInProcedureCalls: Boolean get() = metadata.supportsSchemasInProcedureCalls()

  val supportsSchemasInTableDefinitions: Boolean get() = metadata.supportsSchemasInTableDefinitions()

  val supportsSchemasInIndexDefinitions: Boolean get() = metadata.supportsSchemasInIndexDefinitions()

  val supportsSchemasInPrivilegeDefinitions: Boolean get() = metadata.supportsSchemasInPrivilegeDefinitions()

  val supportsCatalogsInProcedureCalls: Boolean get() = metadata.supportsCatalogsInProcedureCalls()

  val supportsCatalogsInIndexDefinitions: Boolean get() = metadata.supportsCatalogsInIndexDefinitions()

  val supportsCatalogsInPrivilegeDefinitions: Boolean get() = metadata.supportsCatalogsInPrivilegeDefinitions()

  val supportsPositionedDelete: Boolean get() = metadata.supportsPositionedDelete()

  val supportsPositionedUpdate: Boolean get() = metadata.supportsPositionedUpdate()

  val supportsSelectForUpdate: Boolean get() = metadata.supportsSelectForUpdate()

  val supportsStoredProcedures: Boolean get() = metadata.supportsStoredProcedures()

  val supportsSubqueriesInComparisons: Boolean get() = metadata.supportsSubqueriesInComparisons()

  val supportsSubqueriesInExists: Boolean get() = metadata.supportsSubqueriesInExists()

  val supportsSubqueriesInIns: Boolean get() = metadata.supportsSubqueriesInIns()

  val supportsCorrelatedSubqueries: Boolean get() = metadata.supportsCorrelatedSubqueries()

  val supportsUnion: Boolean get() = metadata.supportsUnion()

  val supportsUnionAll: Boolean get() = metadata.supportsUnionAll()

  val supportsOpenCursorsAcrossCommit: Boolean get() = metadata.supportsOpenCursorsAcrossCommit()

  val supportsOpenCursorsAcrossRollback: Boolean get() = metadata.supportsOpenCursorsAcrossRollback()

  val supportsOpenStatementsAcrossCommit: Boolean get() = metadata.supportsOpenStatementsAcrossCommit()

  val maxBinaryLiteralLength: Int get() = metadata.maxBinaryLiteralLength

  val maxCharLiteralLength: Int get() = metadata.maxCharLiteralLength

  val maxColumnNameLength: Int get() = metadata.maxColumnNameLength

  val maxColumnsInGroupBy: Int get() = metadata.maxColumnsInGroupBy

  val maxColumnsInOrderBy: Int get() = metadata.maxColumnsInOrderBy

  val maxColumnsInSelect: Int get() = metadata.maxColumnsInSelect

  val maxColumnsInTable: Int get() = metadata.maxColumnsInTable

  val maxConnections: Int get() = metadata.maxConnections

  val maxCursorNameLength: Int get() = metadata.maxCursorNameLength

  val maxIndexLength: Int get() = metadata.maxIndexLength

  val maxSchemaNameLength: Int get() = metadata.maxSchemaNameLength

  val maxCatalogNameLength: Int get() = metadata.maxCatalogNameLength

  val maxRowSize: Int get() = metadata.maxRowSize

  val doesMaxRowSizeIncludeBlobs: Boolean get() = metadata.doesMaxRowSizeIncludeBlobs()

  val maxStatementLength: Int get() = metadata.maxStatementLength

  val maxStatements: Int get() = metadata.maxStatements

  val maxTableNameLength: Int get() = metadata.maxTableNameLength

  val maxTablesInSelect: Int get() = metadata.maxTablesInSelect

  val maxUserNameLength: Int get() = metadata.maxUserNameLength

  val defaultTransactionIsolation: Int get() = metadata.defaultTransactionIsolation

  val supportsTransactions: Boolean get() = metadata.supportsTransactions()

  fun supportsTransactionIsolationLevel(level: Int): Boolean = metadata.supportsTransactionIsolationLevel(level)

  val supportsDataManipulationTransactionsOnly: Boolean get() = metadata.supportsDataManipulationTransactionsOnly()

  val dataDefinitionCausesTransactionCommit: Boolean get() = metadata.dataDefinitionCausesTransactionCommit()

  val dataDefinitionIgnoredInTransactions: Boolean get() = metadata.dataDefinitionIgnoredInTransactions()

  fun getProcedures(catalog: String, schemaPattern: String, procedureNamePattern: String): ProcedureResults {
    return ProcedureResults(metadata.getProcedures(catalog, schemaPattern, procedureNamePattern))
  }

  fun getProcedureColumns(catalog: String, schemaPattern: String, procedureNamePattern: String, columnNamePattern: String): ProcedureColumnResults {
    return ProcedureColumnResults(metadata.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern))
  }

  fun getTables(catalog: String? = null, schemaPattern: String? = null, tableNamePattern: String? = null, types: Array<String>? = null): TableResults {
    return TableResults(metadata.getTables(catalog, schemaPattern, tableNamePattern, types))
  }

  val schemas: SchemaResults get() = SchemaResults(metadata.schemas)

  fun getTableTypes() = metadata.tableTypes.toStrings()

  fun getColumns(catalog: String? = null, schemaPattern: String? = null, tableNamePattern: String?, columnNamePattern: String?=null): ColumnsResults {
    return ColumnsResults(metadata.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern))
  }

  fun getColumnPrivileges(catalog: String, schema: String, table: String, columnNamePattern: String): ResultSet {
    // TODO wrap
    return metadata.getColumnPrivileges(catalog, schema, table, columnNamePattern)
  }

  fun getTablePrivileges(catalog: String, schemaPattern: String, tableNamePattern: String): ResultSet {
    // TODO wrap
    return metadata.getTablePrivileges(catalog, schemaPattern, tableNamePattern)
  }

  fun getBestRowIdentifier(catalog: String, schema: String, table: String, scope: Int, nullable: Boolean): ResultSet {
    // TODO wrap
    return metadata.getBestRowIdentifier(catalog, schema, table, scope, nullable)
  }

  fun getVersionColumns(catalog: String, schema: String, table: String): ResultSet {
    // TODO wrap
    return metadata.getVersionColumns(catalog, schema, table)
  }

  fun getPrimaryKeys(catalog: String, schema: String, table: String): ResultSet {
    // TODO wrap
    return metadata.getPrimaryKeys(catalog, schema, table)
  }

  fun getImportedKeys(catalog: String, schema: String, table: String): ResultSet {
    // TODO wrap
    return metadata.getImportedKeys(catalog, schema, table)
  }

  fun getExportedKeys(catalog: String, schema: String, table: String): ResultSet {
    // TODO wrap
    return metadata.getExportedKeys(catalog, schema, table)
  }

  fun getCrossReference(parentCatalog: String, parentSchema: String, parentTable: String, foreignCatalog: String, foreignSchema: String, foreignTable: String): ResultSet {
    // TODO wrap
    return metadata.getCrossReference(parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema, foreignTable)
  }

  fun getTypeInfo(): ResultSet {
    // TODO wrap
    return metadata.typeInfo
  }

  fun getIndexInfo(catalog: String, schema: String, table: String, unique: Boolean, approximate: Boolean): ResultSet {
    // TODO wrap
    return metadata.getIndexInfo(catalog, schema, table, unique, approximate)
  }

  fun supportsResultSetConcurrency(type: Int, concurrency: Int): Boolean = metadata.supportsResultSetConcurrency(type, concurrency)

  fun ownUpdatesAreVisible(type: Int): Boolean = metadata.ownUpdatesAreVisible(type)

  fun ownDeletesAreVisible(type: Int): Boolean = metadata.ownDeletesAreVisible(type)

  fun ownInsertsAreVisible(type: Int): Boolean = metadata.ownInsertsAreVisible(type)

  fun othersUpdatesAreVisible(type: Int): Boolean = metadata.othersUpdatesAreVisible(type)

  fun othersDeletesAreVisible(type: Int): Boolean = metadata.othersDeletesAreVisible(type)

  fun othersInsertsAreVisible(type: Int): Boolean = metadata.othersInsertsAreVisible(type)

  fun updatesAreDetected(type: Int): Boolean = metadata.updatesAreDetected(type)

  fun deletesAreDetected(type: Int): Boolean = metadata.deletesAreDetected(type)

  val supportsBatchUpdates: Boolean get() = metadata.supportsBatchUpdates()

  fun getUDTs(catalog: String, schemaPattern: String, typeNamePattern: String, types: IntArray): ResultSet {
    // TODO wrap
    return metadata.getUDTs(catalog, schemaPattern, typeNamePattern, types)
  }

  val supportsSavepoints: Boolean get() = metadata.supportsSavepoints()

  val supportsNamedParameters: Boolean get() = metadata.supportsNamedParameters()

  val supportsMultipleOpenResults: Boolean get() = metadata.supportsMultipleOpenResults()

  fun getSuperTypes(catalog: String, schemaPattern: String, typeNamePattern: String): ResultSet {
    // TODO wrap
    return metadata.getSuperTypes(catalog, schemaPattern, typeNamePattern)
  }

  fun getSuperTables(catalog: String, schemaPattern: String, tableNamePattern: String): ResultSet {
    // TODO wrap
    return metadata.getSuperTables(catalog, schemaPattern, tableNamePattern)
  }

  fun supportsResultSetHoldability(holdability: Int): Boolean {
    return metadata.supportsResultSetHoldability(holdability)
  }

  val resultSetHoldability: Int get() = metadata.resultSetHoldability

  val databaseMajorVersion: Int get() = metadata.databaseMajorVersion

  val databaseMinorVersion: Int get() = metadata.databaseMinorVersion

  val JDBCMajorVersion: Int get() = metadata.jdbcMajorVersion

  val JDBCMinorVersion: Int get() = metadata.jdbcMinorVersion

  val SQLStateType: Int get() = metadata.sqlStateType

  val locatorsUpdateCopy: Boolean get() = metadata.locatorsUpdateCopy()

  val supportsStatementPooling: Boolean get() = metadata.supportsStatementPooling()

  val rowIdLifetime: RowIdLifetime get() = metadata.rowIdLifetime

  fun getSchemas(catalog: String, schemaPattern: String): ResultSet {
    return metadata.getSchemas(catalog, schemaPattern)
  }

  fun getClientInfoProperties(): ResultSet {
    return metadata.clientInfoProperties
  }

  fun getFunctions(catalog: String, schemaPattern: String, functionNamePattern: String): ResultSet {
    // TODO wrap
    return metadata.getFunctions(catalog, schemaPattern, functionNamePattern)
  }

  fun getFunctionColumns(catalog: String, schemaPattern: String, functionNamePattern: String, columnNamePattern: String): ResultSet {
    // TODO wrap
    return metadata.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern)
  }

  fun getPseudoColumns(catalog: String, schemaPattern: String, tableNamePattern: String, columnNamePattern: String): ResultSet {
    return metadata.getPseudoColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern)
  }

  val generatedKeyAlwaysReturned: Boolean get() = metadata.generatedKeyAlwaysReturned()

  val maxLogicalLobSize: Long get() = metadata.maxLogicalLobSize

  val supportsRefCursors: Boolean get() = metadata.supportsRefCursors()


}

enum class ResultSetType {
  TYPE_FORWARD_ONLY,
  TYPE_SCROLL_INSENSITIVE,
  TYPE_SCROLL_SENSITIVE
}

fun fetchDirection(sqlValue: Int) = FetchDirection.values().first { it.sqlValue==sqlValue }

enum class FetchDirection(val sqlValue:Int) {
  FETCH_FORWARD(ResultSet.FETCH_FORWARD),
  FETCH_REVERSE(ResultSet.FETCH_REVERSE),
  FETCH_UNKNOWN(ResultSet.FETCH_UNKNOWN)
}

abstract class AbstractReadonlyResultSet(protected val resultSet: ResultSet) : Closeable, AutoCloseable {
  fun beforeFirst() = resultSet.beforeFirst()

  override fun close() = resultSet.close()

  fun getWarnings(): Iterator<SQLWarning> = WarningIterator(resultSet.warnings)

  val isFirst: Boolean get() = resultSet.isFirst

  val isLast: Boolean get() = resultSet.isLast

  fun last() = resultSet.last()

  val isAfterLast: Boolean get() = resultSet.isAfterLast

  fun relative(rows: Int) = resultSet.relative(rows)

  fun absolute(row: Int) = resultSet.absolute(row)

  fun next() = resultSet.next()

  fun first() = resultSet.first()

  val row: Int get() = resultSet.row

  val type: ResultSetType get() = when(resultSet.type) {
    ResultSet.TYPE_FORWARD_ONLY -> ResultSetType.TYPE_FORWARD_ONLY
    ResultSet.TYPE_SCROLL_INSENSITIVE -> ResultSetType.TYPE_SCROLL_INSENSITIVE
    ResultSet.TYPE_SCROLL_SENSITIVE -> ResultSetType.TYPE_SCROLL_SENSITIVE
    else -> throw SQLException("Unexpected type found")
  }

  fun afterLast() = resultSet.afterLast()

  var fetchSize:Int
    get() = resultSet.fetchSize
    set(rows) { resultSet.fetchSize=rows }

  val holdability:Int get() = resultSet.holdability

  fun previous() = resultSet.previous()

  val isClosed: Boolean get() = resultSet.isClosed

  fun getCursorName():String = resultSet.cursorName

  val isBeforeFirst: Boolean get() = resultSet.isBeforeFirst

  fun refreshRow() = resultSet.refreshRow()

  val concurrency: Int get() = resultSet.concurrency

  fun moveToCurrentRow() = resultSet.moveToCurrentRow()

  fun clearWarnings() = resultSet.clearWarnings()

  val metaData: WrappedResultSetMetaData get() = WrappedResultSetMetaData(resultSet.metaData)

  var fetchDirection : FetchDirection
    get() = fetchDirection(resultSet.fetchDirection)
    set(value) { resultSet.fetchDirection = value.sqlValue }
}

enum class Nullable(val sqlValue: Short) {
  NO_NULLS(DatabaseMetaData.attributeNoNulls),
  NULLABLE(DatabaseMetaData.attributeNullable),
  NULLABLE_UNKNOWN(DatabaseMetaData.attributeNullableUnknown);

  companion object {
    @JvmStatic
    fun from(value:Short):Nullable {
      return values().first { value==it.sqlValue }
    }
  }
}

private fun ResultSet.toStrings():List<String> {
  return ArrayList<String>().apply {
    try {
      while(next()) {
        add(getString(1))
      }
    } finally {
      this@toStrings.close()
    }

  }
}

abstract class DataResults(rs:ResultSet) : AbstractReadonlyResultSet(rs) {
  private val idxDataType by lazy { resultSet.findColumn("DATA_TYPE") }
  private val idxTypeName by lazy { resultSet.findColumn("TYPE_NAME") }
  private val idxNullable by lazy { resultSet.findColumn("NULLABLE") }
  private val idxRemarks by lazy { resultSet.findColumn("REMARKS") }
  private val idxCharOctetLength by lazy { resultSet.findColumn("CHAR_OCTET_LENGTH") }
  private val idxOrdinalPosition by lazy { resultSet.findColumn("ORDINAL_POSITION") }
  private val idxIsNullable by lazy { resultSet.findColumn("IS_NULLABLE") }

  val dataType:IColumnType<*,*,*> get() = columnType(resultSet.getInt(idxDataType))
  val typeName:String get() = resultSet.getString(idxTypeName)
  val nullable:Nullable get() = Nullable.from(resultSet.getInt(idxNullable).toShort())
  val remarks:String? get() = resultSet.getString(idxRemarks).let { if (it.isNullOrEmpty())null else it }
  val charOctetLength:Int get() = resultSet.getInt(idxCharOctetLength)
  val ordinalPosition:Int get() = resultSet.getInt(idxOrdinalPosition)
  val isNullable:Boolean? get() = resultSet.optionalBoolean(idxIsNullable)

}

private fun ResultSet.optionalBoolean(pos:Int) = getString(pos).let { v -> when(v){
  "YES" -> true
  "NO" -> false
  "" -> null
  else -> throw SQLException("Unexpected value for boolean: $v")
} }

class AttributeResults(attributes: ResultSet) : DataResults(attributes) {
  private val idxTypeCat by lazy { resultSet.findColumn("TYPE_CAT") }
  private val idxTypeSchem by lazy { resultSet.findColumn("TYPE_SCHEM") }
  private val idxAttrName by lazy { resultSet.findColumn("ATTR_NAME") }
  private val idxAttrTypeName by lazy { resultSet.findColumn("ATTR_TYPE_NAME") }
  private val idxAttrSize by lazy { resultSet.findColumn("ATTR_SIZE") }
  private val idxDecimalDigits by lazy { resultSet.findColumn("DECIMAL_DIGITS") }
  private val idxNumPrecRadix by lazy { resultSet.findColumn("NUM_REC_RADIX") }
  private val idxAttrDef by lazy { resultSet.findColumn("ATTR_DEF") }

  val typeCatalog:String? get() = resultSet.getString(idxTypeCat)
  val typeScheme:String? get() = resultSet.getString(idxTypeSchem)
  val attrName:String get() = resultSet.getString(idxAttrName)
  val attrTypeName:String? get() = resultSet.getString(idxAttrTypeName)
  val attrSize:Int get() = resultSet.getInt(idxAttrSize)
  val decimalDigits:Int get() = resultSet.getInt(idxDecimalDigits)
  val numPrecRadix:Int get() = resultSet.getInt(idxNumPrecRadix)
  val attrDefault:String? get() = resultSet.getString(idxAttrDef)

}

class ProcedureColumnResults(rs:ResultSet) : DataResults(rs) {
  private val idxProcedureCat by lazy { resultSet.findColumn("PROCEDURE_CAT") }
  private val idxProcedureSchem by lazy { resultSet.findColumn("PROCEDURE_SCHEM") }
  private val idxProcedureName by lazy { resultSet.findColumn("PROCEDURE_NAME") }

  private val idxColumnName by lazy { resultSet.findColumn("COLUMN_NAME") }
  private val idxColumnType by lazy { resultSet.findColumn("COLUMN_TYPE") }
  private val idxPrecision by lazy { resultSet.findColumn("PRECISION") }
  private val idxLength by lazy { resultSet.findColumn("LENGTH") }
  private val idxScale by lazy { resultSet.findColumn("SCALE") }
  private val idxRadix by lazy { resultSet.findColumn("RADIX") }

  private val idxColumnDef by lazy { resultSet.findColumn("COLUMN_DEF") }
  private val idxSpecificName by lazy { resultSet.findColumn("SPECIFIC_NAME") }



  val procedureCatalog:String? get() = resultSet.getString(idxProcedureCat)
  val procedureScheme:String? get() = resultSet.getString(idxProcedureSchem)
  val procedureName:String get() = resultSet.getString(idxProcedureName)
  val columnName:String get() = resultSet.getString(idxColumnName)
  val columnType:String get() = resultSet.getString(idxColumnType)
  val precision:Int get() = resultSet.getInt(idxPrecision)
  val length:Int get() = resultSet.getInt(idxLength)
  val scale:Short? get() = resultSet.getShort(idxScale).let { result -> if (resultSet.wasNull()) null else result }
  val radix:Short get() = resultSet.getShort(idxRadix)

  val columnDef:String? get() = resultSet.getString(idxColumnDef)

  val specificName:String get() = resultSet.getString(idxSpecificName)

}

class ProcedureResults(attributes: ResultSet) : AbstractReadonlyResultSet(attributes) {

  fun procedureType(sqlValue:Short) = ProcedureType.values().first { it.sqlValue==sqlValue }

  enum class ProcedureType(val sqlValue: Short) {
    PROCEDURE_RESULT_UNKNOWN(DatabaseMetaData.procedureResultUnknown.toShort()),
    PROCEDURE_NO_RESULT(DatabaseMetaData.procedureNoResult.toShort()),
    PROCEDURE_RETURNS_RESULT(DatabaseMetaData.procedureReturnsResult.toShort())
  }

  private val idxProcedureCat by lazy { resultSet.findColumn("PROCEDURE_CAT") }
  private val idxProcedureSchem by lazy { resultSet.findColumn("PROCEDURE_SCHEM") }
  private val idxProcedureName by lazy { resultSet.findColumn("PROCEDURE_NAME") }
  private val idxSpecificName by lazy { resultSet.findColumn("SPECIFIC_NAME") }
  private val idxProcedureType by lazy { resultSet.findColumn("PROCEDURE_TYPE") }
  private val idxRemarks by lazy { resultSet.findColumn("REMARKS") }

  val procedureCatalog:String? get() = resultSet.getString(idxProcedureCat)
  val procedureScheme:String? get() = resultSet.getString(idxProcedureSchem)
  val procedureName:String get() = resultSet.getString(idxProcedureName)
  val specificName:String get() = resultSet.getString(idxSpecificName)
  val procedureType:ProcedureType get() = procedureType(resultSet.getShort(idxProcedureType))
  val remarks:String? get() = resultSet.getString(idxRemarks)

}

class TableResults(rs:ResultSet) : AbstractReadonlyResultSet(rs) {
  enum class RefGeneration {
    SYSTEM,
    USER,
    DERIVED
  }

  private val idxTableCat by lazy { resultSet.findColumn("TABLE_CAT") }
  private val idxTableSchem by lazy { resultSet.findColumn("TABLE_SCHEM") }
  private val idxTableName by lazy { resultSet.findColumn("TABLE_NAME") }
  private val idxTableType by lazy { resultSet.findColumn("TABLE_TYPE") }
  private val idxRemarks by lazy { resultSet.findColumn("REMARKS") }
  private val idxTypeCat by lazy { resultSet.findColumn("TYPE_CAT") }
  private val idxTypeSchem by lazy { resultSet.findColumn("TYPE_SCHEM") }
  private val idxTypeName by lazy { resultSet.findColumn("TYPE_NAME") }
  private val idxSelfReferencingColName by lazy { resultSet.findColumn("SELF_REFERENCING_COL_NAME") }
  private val idxRefGeneration by lazy { resultSet.findColumn("REF_GENERATION") }

  val tableCatalog:String? get() = resultSet.getString(idxTableCat)
  val tableScheme:String? get() = resultSet.getString(idxTableSchem)
  val tableName:String get() = resultSet.getString(idxTableName)
  val tableType:String get() = resultSet.getString(idxTableType)
  val remarks:String? get() = resultSet.getString(idxRemarks)
  val typeCatalog:String? get() = resultSet.getString(idxTypeCat)
  val typeScheme:String? get() = resultSet.getString(idxTypeSchem)
  val typeName:String? get() = resultSet.getString(idxTypeName)
  val selfReferencingColName:String? get() = resultSet.getString(idxSelfReferencingColName)
  val refGeneration:RefGeneration? get() = resultSet.getString(idxRefGeneration)?.let { RefGeneration.valueOf(it) }
}

class SchemaResults(rs:ResultSet) : AbstractReadonlyResultSet(rs) {
  private val idxTableCat by lazy { resultSet.findColumn("TABLE_CAT") }
  val tableCatalog:String? get() = resultSet.getString(idxTableCat)

  private val idxTableSchem by lazy { resultSet.findColumn("TABLE_SCHEM") }
  val tableScheme:String? get() = resultSet.getString(idxTableSchem)
}

class ColumnsResults(rs:ResultSet) : DataResults(rs) {
  private val idxTableCat by lazy { resultSet.findColumn("TABLE_CAT") }
  val tableCatalog:String? get() = resultSet.getString(idxTableCat)
  private val idxTableSchem by lazy { resultSet.findColumn("TABLE_SCHEM") }
  val tableScheme:String? get() = resultSet.getString(idxTableSchem)
  private val idxTableName by lazy { resultSet.findColumn("TABLE_NAME") }
  val tableName:String get() = resultSet.getString(idxTableName)
  private val idxTableType by lazy { resultSet.findColumn("TABLE_TYPE") }
  val tableType:String get() = resultSet.getString(idxTableType)
  private val idxColumnName by lazy { resultSet.findColumn("COLUMN_NAME") }
  val columnName:String get() = resultSet.getString(idxColumnName)
  private val idxColumnSize by lazy { resultSet.findColumn("COLUMN_SIZE") }
  val columnSize:Int get() = resultSet.getInt(idxColumnSize)
  private val idxBufferLength by lazy { resultSet.findColumn("BUFFER_LENGTH") }
  val bufferLength:String get() = resultSet.getString(idxBufferLength)
  private val idxDecimalDigits by lazy { resultSet.findColumn("DECIMAL_DIGITS") }
  val decimalDigits:Int get() = resultSet.getInt(idxDecimalDigits)
  private val idxNumPrecRadix by lazy { resultSet.findColumn("NUM_REC_RADIX") }
  val numPrecRadix:Int get() = resultSet.getInt(idxNumPrecRadix)
  private val idxColumnDef by lazy { resultSet.findColumn("COLUMN_DEF") }
  val columnDefault:String? get() = resultSet.getString(idxColumnDef)
  private val idxIsAutoIncrement by lazy { resultSet.findColumn("IS_AUTOINCREMENT") }
  val isAutoIncrement:Boolean? get() = resultSet.optionalBoolean(idxIsAutoIncrement)
  private val idxIsGeneratedColumn by lazy { resultSet.findColumn("IS_GENERATEDCOLUMN") }
  val isGeneratedColumn:Boolean? get() = resultSet.optionalBoolean(idxIsGeneratedColumn)
}

class WrappedResultSetMetaData(private val metadata:ResultSetMetaData)