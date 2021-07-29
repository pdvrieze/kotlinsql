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

package uk.ac.bournemouth.kotlinsql

import uk.ac.bournemouth.util.kotlin.sql.use
import java.io.Closeable
import java.sql.*
import java.util.*

/**
 * A class to handle access to connection metadata (available through jdbc.
 */
@Suppress("unused")
class ConnectionMetadata(private val metadata: DatabaseMetaData) {

    val maxColumnsInIndex: Int get() = metadata.maxColumnsInIndex

    fun insertsAreDetected(type: Int): Boolean = metadata.insertsAreDetected(type)

    fun getAttributes(catalog: String?,
                      schemaPattern: String?,
                      typeNamePattern: String?,
                      attributeNamePattern: String?): AttributeResults {
        return AttributeResults(metadata.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern))
    }

    val supportsSubqueriesInQuantifieds: Boolean get() = metadata.supportsSubqueriesInQuantifieds()

    val supportsIntegrityEnhancementFacility: Boolean get() = metadata.supportsIntegrityEnhancementFacility()

    val supportsGetGeneratedKeys: Boolean get() = metadata.supportsGetGeneratedKeys()

    val supportsCoreSQLGrammar: Boolean get() = metadata.supportsCoreSQLGrammar()

    val supportsDataDefinitionAndDataManipulationTransactions: Boolean get() = metadata.supportsDataDefinitionAndDataManipulationTransactions()

    val supportsCatalogsInTableDefinitions: Boolean get() = metadata.supportsCatalogsInTableDefinitions()

    val supportsOpenStatementsAcrossRollback: Boolean get() = metadata.supportsOpenStatementsAcrossRollback()

    val supportsStoredFunctionsUsingCallSyntax: Boolean get() = metadata.supportsStoredFunctionsUsingCallSyntax()

    val databaseProductName: String get() = metadata.databaseProductName

    val databaseProductVersion: String get() = metadata.databaseProductVersion

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

    @Suppress("PropertyName")
    val URL: String
        get() = metadata.url

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

    @Suppress("PropertyName")
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

    fun getProcedureColumns(catalog: String,
                            schemaPattern: String,
                            procedureNamePattern: String,
                            columnNamePattern: String): ProcedureColumnResults {
        return ProcedureColumnResults(
                metadata.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern))
    }

    fun getTables(catalog: String? = null,
                  schemaPattern: String? = null,
                  tableNamePattern: String? = null,
                  types: Array<String>? = null): TableResults {
        return TableResults(metadata.getTables(catalog, schemaPattern, tableNamePattern, types))
    }

    val schemas: SchemaResults get() = SchemaResults(metadata.schemas)

    val tableTypes get() = metadata.tableTypes.toStrings()

    fun getColumns(catalog: String? = null,
                   schemaPattern: String? = null,
                   tableNamePattern: String?,
                   columnNamePattern: String? = null): ColumnsResults {
        return ColumnsResults(metadata.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern))
    }

    fun getColumnPrivileges(catalog: String,
                            schema: String,
                            table: String,
                            columnNamePattern: String): ColumnPrivilegesResult {
        return ColumnPrivilegesResult(metadata.getColumnPrivileges(catalog, schema, table, columnNamePattern))
    }

    fun getTablePrivileges(catalog: String, schemaPattern: String, tableNamePattern: String): TablePrivilegesResult {
        return TablePrivilegesResult(metadata.getTablePrivileges(catalog, schemaPattern, tableNamePattern))
    }

    fun getBestRowIdentifier(catalog: String,
                             schema: String,
                             table: String,
                             scope: Int,
                             nullable: Boolean): BestRowIdentifierResult {
        return BestRowIdentifierResult(metadata.getBestRowIdentifier(catalog, schema, table, scope, nullable))
    }

    fun getVersionColumns(catalog: String, schema: String, table: String): VersionColumnsResult {
        return VersionColumnsResult(metadata.getVersionColumns(catalog, schema, table))
    }

    fun getPrimaryKeys(catalog: String, schema: String, table: String): PrimaryKeyResults {
        return PrimaryKeyResults(metadata.getPrimaryKeys(catalog, schema, table))
    }

    fun getImportedKeys(catalog: String, schema: String, table: String): KeysResult {
        return KeysResult(metadata.getImportedKeys(catalog, schema, table))
    }

    fun getExportedKeys(catalog: String, schema: String, table: String): KeysResult {
        return KeysResult(metadata.getExportedKeys(catalog, schema, table))
    }

    fun getCrossReference(parentCatalog: String,
                          parentSchema: String,
                          parentTable: String,
                          foreignCatalog: String,
                          foreignSchema: String,
                          foreignTable: String): KeysResult {
        return KeysResult(
                metadata.getCrossReference(parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema,
                                           foreignTable))
    }

    fun getTypeInfo(): TypeInfo {
        return TypeInfo(metadata.typeInfo)
    }

    fun getUnsafeIndexInfo(catalog: String,
                           schema: String,
                           table: String,
                           unique: Boolean,
                           approximate: Boolean): ResultSet {
        // TODO wrap
        return metadata.getIndexInfo(catalog, schema, table, unique, approximate)
    }

    fun supportsResultSetConcurrency(type: Int, concurrency: Int): Boolean = metadata.supportsResultSetConcurrency(type,
                                                                                                                   concurrency)

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

    @Suppress("PropertyName")
    val JDBCMajorVersion: Int get() = metadata.jdbcMajorVersion

    @Suppress("PropertyName")
    val JDBCMinorVersion: Int get() = metadata.jdbcMinorVersion

    @Suppress("PropertyName")
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

    fun getFunctionColumns(catalog: String,
                           schemaPattern: String,
                           functionNamePattern: String,
                           columnNamePattern: String): ResultSet {
        // TODO wrap
        return metadata.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern)
    }

    fun getPseudoColumns(catalog: String,
                         schemaPattern: String,
                         tableNamePattern: String,
                         columnNamePattern: String): ResultSet {
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

fun fetchDirection(sqlValue: Int) = FetchDirection.values().first { it.sqlValue == sqlValue }

enum class FetchDirection(val sqlValue: Int) {
    FETCH_FORWARD(ResultSet.FETCH_FORWARD),
    FETCH_REVERSE(ResultSet.FETCH_REVERSE),
    FETCH_UNKNOWN(ResultSet.FETCH_UNKNOWN)
}

abstract class AbstractMetadataResultSet(protected val resultSet: ResultSet) : Closeable, AutoCloseable {
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

    val type: ResultSetType
        get() = when (resultSet.type) {
            ResultSet.TYPE_FORWARD_ONLY       -> ResultSetType.TYPE_FORWARD_ONLY
            ResultSet.TYPE_SCROLL_INSENSITIVE -> ResultSetType.TYPE_SCROLL_INSENSITIVE
            ResultSet.TYPE_SCROLL_SENSITIVE   -> ResultSetType.TYPE_SCROLL_SENSITIVE
            else                              -> throw SQLException("Unexpected type found")
        }

    fun afterLast() = resultSet.afterLast()

    var fetchSize: Int
        get() = resultSet.fetchSize
        set(rows) {
            resultSet.fetchSize = rows
        }

    val holdability: Int get() = resultSet.holdability

    fun previous() = resultSet.previous()

    val isClosed: Boolean get() = resultSet.isClosed

    fun getCursorName(): String = resultSet.cursorName

    val isBeforeFirst: Boolean get() = resultSet.isBeforeFirst

    fun refreshRow() = resultSet.refreshRow()

    val concurrency: Int get() = resultSet.concurrency

    fun moveToCurrentRow() = resultSet.moveToCurrentRow()

    fun clearWarnings() = resultSet.clearWarnings()

    val metaData: WrappedResultSetMetaData get() = WrappedResultSetMetaData(resultSet.metaData)

    var fetchDirection: FetchDirection
        get() = fetchDirection(resultSet.fetchDirection)
        set(value) {
            resultSet.fetchDirection = value.sqlValue
        }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun lazyColIdx(name: String) = lazy { resultSet.findColumn(name) }
}

enum class Nullable(val sqlValue: Short) {
    NO_NULLS(DatabaseMetaData.attributeNoNulls),
    NULLABLE(DatabaseMetaData.attributeNullable),
    NULLABLE_UNKNOWN(DatabaseMetaData.attributeNullableUnknown);

    companion object {
        @JvmStatic
        fun from(value: Short): Nullable {
            return values().first { value == it.sqlValue }
        }
    }
}

enum class Searchable(@Suppress("unused") val sqlValue: Short) {
    TYPE_PRED_NONE(DatabaseMetaData.typePredNone.toShort()),
    TYPE_PRED_CHAR(DatabaseMetaData.typePredChar.toShort()),
    TYPE_PRED_BASIC(DatabaseMetaData.typePredBasic.toShort()),
    TYPE_SEARCHABLE(DatabaseMetaData.typeSearchable.toShort()),
    ;

    companion object {
        @JvmStatic
        fun from(value: Short): Searchable {
            return when (value.toInt()) {
                DatabaseMetaData.typePredNone   -> Searchable.TYPE_PRED_NONE
                DatabaseMetaData.typePredChar   -> Searchable.TYPE_PRED_CHAR
                DatabaseMetaData.typePredBasic  -> Searchable.TYPE_PRED_BASIC
                DatabaseMetaData.typeSearchable -> Searchable.TYPE_SEARCHABLE
                else                            -> throw IllegalArgumentException(
                        "The value $this does not represent a valid searchability value")
            }
        }
    }
}

private fun ResultSet.toStrings(): List<String> {
    return use {
        ArrayList<String>().apply {
            while (next()) {
                add(getString(1))
            }
        }

    }
}

@Suppress("unused")
abstract class DataResults(rs: ResultSet) : AbstractMetadataResultSet(rs) {
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxTypeName by lazyColIdx("TYPE_NAME")
    private val idxNullable by lazyColIdx("NULLABLE")
    private val idxRemarks by lazyColIdx("REMARKS")
    private val idxCharOctetLength by lazyColIdx("CHAR_OCTET_LENGTH")
    private val idxOrdinalPosition by lazyColIdx("ORDINAL_POSITION")
    private val idxIsNullable by lazyColIdx("IS_NULLABLE")

    val dataType: IColumnType<*, *, *> get() = columnType(resultSet.getInt(idxDataType))
    val typeName: String get() = resultSet.getString(idxTypeName)
    val nullable: Nullable get() = Nullable.from(resultSet.getInt(idxNullable).toShort())
    val remarks: String? get() = resultSet.getString(idxRemarks).let { if (it.isNullOrEmpty()) null else it }
    val charOctetLength: Int get() = resultSet.getInt(idxCharOctetLength)
    val ordinalPosition: Int get() = resultSet.getInt(idxOrdinalPosition)
    val isNullable: Boolean? get() = resultSet.optionalBoolean(idxIsNullable)

}

private fun ResultSet.optionalBoolean(pos: Int) = getString(pos).let { v ->
    when (v) {
        "YES" -> true
        "NO"  -> false
        ""    -> null
        else  -> throw SQLException("Unexpected value for boolean: $v")
    }
}

@Suppress("unused")
class AttributeResults(attributes: ResultSet) : DataResults(attributes) {
    private val idxTypeCat by lazyColIdx("TYPE_CAT")
    private val idxTypeSchem by lazyColIdx("TYPE_SCHEM")
    private val idxAttrName by lazyColIdx("ATTR_NAME")
    private val idxAttrTypeName by lazyColIdx("ATTR_TYPE_NAME")
    private val idxAttrSize by lazyColIdx("ATTR_SIZE")
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    private val idxNumPrecRadix by lazyColIdx("NUM_REC_RADIX")
    private val idxAttrDef by lazyColIdx("ATTR_DEF")

    val typeCatalog: String? get() = resultSet.getString(idxTypeCat)
    val typeScheme: String? get() = resultSet.getString(idxTypeSchem)
    val attrName: String get() = resultSet.getString(idxAttrName)
    val attrTypeName: String? get() = resultSet.getString(idxAttrTypeName)
    val attrSize: Int get() = resultSet.getInt(idxAttrSize)
    val decimalDigits: Int get() = resultSet.getInt(idxDecimalDigits)
    val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
    val attrDefault: String? get() = resultSet.getString(idxAttrDef)

}

@Suppress("unused")
class ProcedureColumnResults(rs: ResultSet) : DataResults(rs) {
    private val idxProcedureCat by lazyColIdx("PROCEDURE_CAT")
    private val idxProcedureSchem by lazyColIdx("PROCEDURE_SCHEM")
    private val idxProcedureName by lazyColIdx("PROCEDURE_NAME")

    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    private val idxColumnType by lazyColIdx("COLUMN_TYPE")
    private val idxPrecision by lazyColIdx("PRECISION")
    private val idxLength by lazyColIdx("LENGTH")
    private val idxScale by lazyColIdx("SCALE")
    private val idxRadix by lazyColIdx("RADIX")

    private val idxColumnDef by lazyColIdx("COLUMN_DEF")
    private val idxSpecificName by lazyColIdx("SPECIFIC_NAME")


    val procedureCatalog: String? get() = resultSet.getString(idxProcedureCat)
    val procedureScheme: String? get() = resultSet.getString(idxProcedureSchem)
    val procedureName: String get() = resultSet.getString(idxProcedureName)
    val columnName: String get() = resultSet.getString(idxColumnName)
    val columnType: String get() = resultSet.getString(idxColumnType)
    val precision: Int get() = resultSet.getInt(idxPrecision)
    val length: Int get() = resultSet.getInt(idxLength)
    val scale: Short? get() = resultSet.getShort(idxScale).let { result -> if (resultSet.wasNull()) null else result }
    val radix: Short get() = resultSet.getShort(idxRadix)

    val columnDef: String? get() = resultSet.getString(idxColumnDef)

    val specificName: String get() = resultSet.getString(idxSpecificName)

}

@Suppress("unused")
class ProcedureResults(attributes: ResultSet) : AbstractMetadataResultSet(attributes) {

    private fun procedureType(sqlValue: Short) = ProcedureType.values().first { it.sqlValue == sqlValue }

    enum class ProcedureType(val sqlValue: Short) {
        PROCEDURE_RESULT_UNKNOWN(DatabaseMetaData.procedureResultUnknown.toShort()),
        PROCEDURE_NO_RESULT(DatabaseMetaData.procedureNoResult.toShort()),
        PROCEDURE_RETURNS_RESULT(DatabaseMetaData.procedureReturnsResult.toShort())
    }

    private val idxProcedureCat by lazyColIdx("PROCEDURE_CAT")
    private val idxProcedureSchem by lazyColIdx("PROCEDURE_SCHEM")
    private val idxProcedureName by lazyColIdx("PROCEDURE_NAME")
    private val idxSpecificName by lazyColIdx("SPECIFIC_NAME")
    private val idxProcedureType by lazyColIdx("PROCEDURE_TYPE")
    private val idxRemarks by lazyColIdx("REMARKS")

    val procedureCatalog: String? get() = resultSet.getString(idxProcedureCat)
    val procedureScheme: String? get() = resultSet.getString(idxProcedureSchem)
    val procedureName: String get() = resultSet.getString(idxProcedureName)
    val specificName: String get() = resultSet.getString(idxSpecificName)
    val procedureType: ProcedureType get() = procedureType(resultSet.getShort(idxProcedureType))
    val remarks: String? get() = resultSet.getString(idxRemarks)

}

@Suppress("unused")
class TableResults(rs: ResultSet) : TableMetaResultBase(rs) {
    enum class RefGeneration {
        SYSTEM,
        USER,
        DERIVED
    }

    private val idxTableType by lazyColIdx("TABLE_TYPE")
    private val idxRemarks by lazyColIdx("REMARKS")
    private val idxTypeCat by lazyColIdx("TYPE_CAT")
    private val idxTypeSchem by lazyColIdx("TYPE_SCHEM")
    private val idxTypeName by lazyColIdx("TYPE_NAME")
    private val idxSelfReferencingColName by lazyColIdx("SELF_REFERENCING_COL_NAME")
    private val idxRefGeneration by lazyColIdx("REF_GENERATION")

    val tableType: String get() = resultSet.getString(idxTableType)
    val remarks: String? get() = resultSet.getString(idxRemarks)
    val typeCatalog: String? get() = resultSet.getString(idxTypeCat)
    val typeScheme: String? get() = resultSet.getString(idxTypeSchem)
    val typeName: String? get() = resultSet.getString(idxTypeName)
    val selfReferencingColName: String? get() = resultSet.getString(idxSelfReferencingColName)
    val refGeneration: RefGeneration? get() = resultSet.getString(idxRefGeneration)?.let { RefGeneration.valueOf(it) }
}

open class SchemaResults(rs: ResultSet) : AbstractMetadataResultSet(rs) {
    private val idxTableCat by lazyColIdx("TABLE_CAT")
    val tableCatalog: String? get() = resultSet.getString(idxTableCat)

    private val idxTableSchem by lazyColIdx("TABLE_SCHEM")
    val tableScheme: String? get() = resultSet.getString(idxTableSchem)
}

abstract class TableMetaResultBase(rs: ResultSet) : SchemaResults(rs) {
    private val idxTableName by lazyColIdx("TABLE_NAME")
    val tableName: String get() = resultSet.getString(idxTableName)
}

@Suppress("unused")
class ColumnsResults(rs: ResultSet) : DataResults(rs) {
    //TableMetaResultBase
    private val idxTableCat by lazyColIdx("TABLE_CAT")
    val tableCatalog: String? get() = resultSet.getString(idxTableCat)

    private val idxTableSchem by lazyColIdx("TABLE_SCHEM")
    val tableScheme: String? get() = resultSet.getString(idxTableSchem)
    private val idxTableName by lazyColIdx("TABLE_NAME")
    val tableName: String get() = resultSet.getString(idxTableName)
    private val idxTableType by lazyColIdx("TABLE_TYPE")
    val tableType: String get() = resultSet.getString(idxTableType)
    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    val columnName: String get() = resultSet.getString(idxColumnName)
    private val idxColumnSize by lazyColIdx("COLUMN_SIZE")
    val columnSize: Int get() = resultSet.getInt(idxColumnSize)
    private val idxBufferLength by lazyColIdx("BUFFER_LENGTH")
    val bufferLength: String get() = resultSet.getString(idxBufferLength)
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    val decimalDigits: Int get() = resultSet.getInt(idxDecimalDigits)
    private val idxNumPrecRadix by lazyColIdx("NUM_REC_RADIX")
    val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
    private val idxColumnDef by lazyColIdx("COLUMN_DEF")
    val columnDefault: String? get() = resultSet.getString(idxColumnDef)
    private val idxIsAutoIncrement by lazyColIdx("IS_AUTOINCREMENT")
    val isAutoIncrement: Boolean? get() = resultSet.optionalBoolean(idxIsAutoIncrement)
    private val idxIsGeneratedColumn by lazyColIdx("IS_GENERATEDCOLUMN")
    val isGeneratedColumn: Boolean? get() = resultSet.optionalBoolean(idxIsGeneratedColumn)
}

@Suppress("unused")
abstract class TableColumnResultBase(rs: ResultSet) : TableMetaResultBase(rs) {
    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    val columnName: String get() = resultSet.getString(idxColumnName)
}

@Suppress("unused")
class ColumnPrivilegesResult(privileges: ResultSet) : TableColumnResultBase(privileges) {
    private val idxGrantor: Int by lazyColIdx("GRANTOR")
    val grantor get():String? = resultSet.getString(idxGrantor)
    private val idxGrantee: Int by lazyColIdx("GRANTEE")
    val grantee get(): String = resultSet.getString(idxGrantee)
    private val idxPrivilege: Int by lazyColIdx("PRIVILEGE")
    val privilege get():String = resultSet.getString(idxPrivilege)

    private val idxIsGrantable: Int by lazyColIdx("IS_GRANTABLE")

    val isGrantable
        get():Boolean? = resultSet.getString(
                idxIsGrantable)?.let { if (it == "YES") true else if (it == "NO") false else null }
}

@Suppress("unused")
class TablePrivilegesResult(privileges: ResultSet) : TableMetaResultBase(privileges) {
    private val idxGrantor: Int by lazyColIdx("GRANTOR")
    val grantor get():String? = resultSet.getString(idxGrantor)
    private val idxGrantee: Int by lazyColIdx("GRANTEE")
    val grantee get(): String = resultSet.getString(idxGrantee)
    private val idxPrivilege: Int by lazyColIdx("PRIVILEGE")
    val privilege get():String = resultSet.getString(idxPrivilege)
    private val idxIsGrantable: Int by lazyColIdx("IS_GRANTABLE")
}

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class AbstractRowResult(rs: ResultSet) : AbstractMetadataResultSet(rs) {
    enum class PseudoColumn {
        BESTROWUNKNOWN,
        BESTROWNOTPSEUDO,
        BESTROWPSEUDO
    }

    private val idxColumnName by lazyColIdx("COLUMN_NAME")
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxTypeName by lazyColIdx("TYPE_NAME")
    private val idxColumnSize by lazyColIdx("COLUMN_SIZE")
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    private val idxPseudoColumn by lazyColIdx("PSEUDO_COLUMN")

    val columnName: String get() = resultSet.getString(idxColumnName)
    val dataType: String get() = resultSet.getString(idxDataType)
    val typeName: String get() = resultSet.getString(idxTypeName)
    val precision: String get() = resultSet.getString(idxColumnSize)
    @Deprecated("Use precision as the column name, this is an alias", ReplaceWith("precision"))
    inline val columnSize get() = precision
    val decimalDigits: Short get() = resultSet.getShort(idxDecimalDigits)

    val pseudoColumn: PseudoColumn = when (resultSet.getShort(idxPseudoColumn).toInt()) {
        DatabaseMetaData.bestRowUnknown   -> PseudoColumn.BESTROWUNKNOWN
        DatabaseMetaData.bestRowPseudo    -> PseudoColumn.BESTROWPSEUDO
        DatabaseMetaData.bestRowNotPseudo -> PseudoColumn.BESTROWNOTPSEUDO
        else                              -> throw IllegalArgumentException(
                "Unexpected pseudoColumn value ${resultSet.getShort(idxPseudoColumn)}")
    }

}

@Suppress("unused")
class BestRowIdentifierResult(rs: ResultSet) : AbstractRowResult(rs) {
    enum class Scope {
        BESTROWTEMPORARY,
        BESTROWTRANSACTION,
        BESTROWSESSION
    }

    private val idxScope by lazyColIdx("SCOPE")

    val scope: Scope
        get() = when (resultSet.getShort(idxScope).toInt()) {
            DatabaseMetaData.bestRowTemporary   -> Scope.BESTROWTEMPORARY
            DatabaseMetaData.bestRowTransaction -> Scope.BESTROWTRANSACTION
            DatabaseMetaData.bestRowSession     -> Scope.BESTROWSESSION
            else                                -> throw IllegalArgumentException(
                    "Unexpected scope value ${resultSet.getShort(idxScope)}")
        }
}

@Suppress("unused")
class VersionColumnsResult(rs: ResultSet) : AbstractRowResult(rs) {
    private val idxBufferSize by lazyColIdx("BUFFER_LENGTH")

    val bufferSize get() = resultSet.getInt(idxBufferSize)
}

@Suppress("unused")
class PrimaryKeyResults(rs: ResultSet) : TableColumnResultBase(rs) {
    private val idxKeySeq by lazyColIdx("KEY_SEQ")
    private val idxPkName by lazyColIdx("PK_NAME")

    val keySeq: Short get() = resultSet.getShort(idxKeySeq)
    val pkName: String? get() = resultSet.getString(idxPkName)

}

@Suppress("unused")
enum class KeyRule(val sqlValue: Short) {
    IMPORTEDKEYNOACTION(DatabaseMetaData.importedKeyNoAction.toShort()),
    IMPORTEDKEYCASCADE(DatabaseMetaData.importedKeyCascade.toShort()),
    IMPORTEDKEYSETNULL(DatabaseMetaData.importedKeySetNull.toShort()),
    IMPORTEDKEYSETDEFAULT(DatabaseMetaData.importedKeySetDefault.toShort()),
    IMPORTEDKEYRESTRICT(DatabaseMetaData.importedKeyRestrict.toShort());

    companion object {
        @JvmStatic
        fun from(value: Short) = when (value.toInt()) {
            DatabaseMetaData.importedKeyNoAction   -> KeyRule.IMPORTEDKEYNOACTION
            DatabaseMetaData.importedKeyCascade    -> KeyRule.IMPORTEDKEYCASCADE
            DatabaseMetaData.importedKeySetNull    -> KeyRule.IMPORTEDKEYSETNULL
            DatabaseMetaData.importedKeySetDefault -> KeyRule.IMPORTEDKEYSETDEFAULT
            DatabaseMetaData.importedKeyRestrict   -> KeyRule.IMPORTEDKEYRESTRICT
            else                                   -> throw IllegalArgumentException(
                    "The value $this does not represent a valid key rule")
        }
    }
}

@Suppress("unused")
enum class KeyDeferrability(val sqlValue: Short) {
    IMPORTEDKEYINITIALLYDEFERRED(DatabaseMetaData.importedKeyInitiallyDeferred.toShort()),
    IMPORTEDKEYINITIALLYIMMEDIATE(DatabaseMetaData.importedKeyInitiallyImmediate.toShort()),
    IMPORTEDKEYNOTDEFERRABLE(DatabaseMetaData.importedKeyNotDeferrable.toShort());

    companion object {
        @JvmStatic
        fun from(value: Short) = when (value.toInt()) {
            DatabaseMetaData.importedKeyInitiallyDeferred  -> KeyDeferrability.IMPORTEDKEYINITIALLYDEFERRED
            DatabaseMetaData.importedKeyInitiallyImmediate -> KeyDeferrability.IMPORTEDKEYINITIALLYIMMEDIATE
            DatabaseMetaData.importedKeyNotDeferrable      -> KeyDeferrability.IMPORTEDKEYNOTDEFERRABLE
            else                                           -> throw IllegalArgumentException(
                    "The value $this does not represent a valid key deferrability value")
        }
    }
}

@Suppress("unused")
class KeysResult(resultSet: ResultSet) : AbstractMetadataResultSet(resultSet) {
    private val idxPkTableCat by lazyColIdx("PKTABLE_CAT")
    private val idxPkTableSchem by lazyColIdx("PKTABLE_SCHEM")
    private val idxPkTableName by lazyColIdx("PKTABLE_NAME")
    private val idxPkColName by lazyColIdx("PKCOLUMN_NAME")
    private val idxFkTableCat by lazyColIdx("FKTABLE_CAT")
    private val idxFkTableSchem by lazyColIdx("FKTABLE_SCHEM")
    private val idxFkTableName by lazyColIdx("FKTABLE_NAME")
    private val idxFkColName by lazyColIdx("FKCOLUMN_NAME")
    private val idxKeySeq by lazyColIdx("KEY_SEQ")
    private val idxUpdateRule by lazyColIdx("UPDATE_RULE")
    private val idxDeleterule by lazyColIdx("DELETE_RULE")
    private val idxFkName by lazyColIdx("FK_NAME")
    private val idxPkName by lazyColIdx("PK_NAME")
    private val idxDeferrability by lazyColIdx("DEFERRABILITY")

    val pkTableCat: String? get() = resultSet.getString(idxPkTableName)
    val pkTableSchema: String? get() = resultSet.getString(idxPkTableSchem)
    val pkTableName: String get() = resultSet.getString(idxPkTableName)
    val pkColumnName: String get() = resultSet.getString(idxPkColName)
    val fkTableCat: String? get() = resultSet.getString(idxFkTableName)
    val fkTableSchema: String? get() = resultSet.getString(idxFkTableSchem)
    val fkTableName: String get() = resultSet.getString(idxFkTableName)
    val fkColumnName: String get() = resultSet.getString(idxFkColName)
    val keySeq: Short get() = resultSet.getShort(idxKeySeq)
    val updateRule: KeyRule get() = KeyRule.from(resultSet.getShort(idxUpdateRule))
    val deleteRule: KeyRule get() = KeyRule.from(resultSet.getShort(idxDeleterule))
    val fkName: String get() = resultSet.getString(idxFkName)
    val pkName: String get() = resultSet.getString(idxPkName)
    val deferrability: KeyDeferrability get() = KeyDeferrability.from(resultSet.getShort(idxDeferrability))
}

@Suppress("unused")
class TypeInfo(resultSet: ResultSet) : AbstractMetadataResultSet(resultSet) {
    private val idxTypeName by lazyColIdx("TYPE_NAME")
    private val idxDataType by lazyColIdx("DATA_TYPE")
    private val idxPrecision by lazyColIdx("PRECISION")
    private val idxLiteralPrefix by lazyColIdx("LITERAL_PREFIX")
    private val idxLiteralSuffix by lazyColIdx("LITERAL_SUFFIX")
    private val idxCreateParams by lazyColIdx("CREATE_PARAMS")
    private val idxNullable by lazyColIdx("NULLABLE")
    private val idxCaseSensitive by lazyColIdx("CASE_SENSITIVE")
    private val idxSearchable by lazyColIdx("SEARCHABLE")
    private val idxUnsignedAttribute by lazyColIdx("UNSIGNED_ATTRIBUTE")
    private val idxFixedPrecScale by lazyColIdx("FIXED_PREC_SCALE")
    private val idxAutoIncrement by lazyColIdx("AUTO_INCREMENT")
    private val idxLocalTypeName by lazyColIdx("LOCAL_TYPE_NAME")
    private val idxMinimumScale by lazyColIdx("MINIMUM_SCALE")
    private val idxMaximumScale by lazyColIdx("MAXIMUM_SCALE")
    private val idxNumPrecRadix by lazyColIdx("NUM_PREC_RADIX")

    val typeName: String get() = resultSet.getString(idxTypeName)
    val dataType: IColumnType<*, *, *> get() = columnType(resultSet.getInt(idxDataType))
    val precision: Int get() = resultSet.getInt(idxPrecision)
    val literalPrefix: String? get() = resultSet.getString(idxLiteralPrefix)
    val literalSuffix: String? get() = resultSet.getString(idxLiteralSuffix)
    val createParams: String? get() = resultSet.getString(idxCreateParams)
    val nullable: Nullable get() = Nullable.from(resultSet.getShort(idxNullable))
    val caseSensitive: Boolean get() = resultSet.getBoolean(idxCaseSensitive)
    val searchable: Searchable get() = Searchable.from(resultSet.getShort(idxSearchable))
    val unsignedAttribute: Boolean get() = resultSet.getBoolean(idxUnsignedAttribute)
    val fixedPrecScale: Boolean get() = resultSet.getBoolean(idxFixedPrecScale)
    val autoIncrement: Boolean get() = resultSet.getBoolean(idxAutoIncrement)
    val localTypeName: String get() = resultSet.getString(idxLocalTypeName)
    val minimumScale: Short get() = resultSet.getShort(idxMinimumScale)
    val maximumScale: Short get() = resultSet.getShort(idxMaximumScale)
    val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
}

@Suppress("unused")
class WrappedResultSetMetaData(private val metadata: ResultSetMetaData)