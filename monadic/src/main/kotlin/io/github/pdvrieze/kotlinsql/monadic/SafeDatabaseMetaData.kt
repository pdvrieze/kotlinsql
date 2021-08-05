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

package io.github.pdvrieze.kotlinsql.monadic

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.metadata.*
import java.sql.DatabaseMetaData
import java.sql.ResultSet
import java.sql.RowIdLifetime

/**
 * A class to handle access to connection metadata (available through jdbc).
 */
@Suppress("unused")
class SafeDatabaseMetaData(private val metadata: DatabaseMetaData) {

    val maxColumnsInIndex: Int get() = metadata.maxColumnsInIndex

    fun insertsAreDetected(type: Int): Boolean = metadata.insertsAreDetected(type)

    @UnmanagedSql
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

    @UnmanagedSql
    fun getProcedures(catalog: String, schemaPattern: String, procedureNamePattern: String): ProcedureResults {
        return ProcedureResults(metadata.getProcedures(catalog, schemaPattern, procedureNamePattern))
    }

    @UnmanagedSql
    fun getProcedureColumns(catalog: String,
                            schemaPattern: String,
                            procedureNamePattern: String,
                            columnNamePattern: String): ProcedureColumnResults {
        return ProcedureColumnResults(
            metadata.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern)
        )
    }

    @UnmanagedSql
    fun getTables(catalog: String? = null,
                  schemaPattern: String? = null,
                  tableNamePattern: String? = null,
                  types: Array<String>? = null): TableMetadataResults {
        return TableMetadataResults(metadata.getTables(catalog, schemaPattern, tableNamePattern, types))
    }

    @UnmanagedSql
    val schemas: SchemaResults get() = SchemaResults(metadata.schemas)

    val tableTypes get() = metadata.tableTypes.toStrings()

    @UnmanagedSql
    fun getColumns(catalog: String? = null,
                   schemaPattern: String? = null,
                   tableNamePattern: String?,
                   columnNamePattern: String? = null): ColumnsResults {
        return ColumnsResults(metadata.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern))
    }

    @UnmanagedSql
    fun getColumnPrivileges(catalog: String,
                            schema: String,
                            table: String,
                            columnNamePattern: String): ColumnPrivilegesResult {
        return ColumnPrivilegesResult(metadata.getColumnPrivileges(catalog, schema, table, columnNamePattern))
    }

    @UnmanagedSql
    fun getTablePrivileges(catalog: String, schemaPattern: String, tableNamePattern: String): TablePrivilegesResult {
        return TablePrivilegesResult(metadata.getTablePrivileges(catalog, schemaPattern, tableNamePattern))
    }

    @UnmanagedSql
    fun getBestRowIdentifier(catalog: String,
                             schema: String,
                             table: String,
                             scope: Int,
                             nullable: Boolean): BestRowIdentifierResult {
        return BestRowIdentifierResult(metadata.getBestRowIdentifier(catalog, schema, table, scope, nullable))
    }

    @UnmanagedSql
    fun getVersionColumns(catalog: String, schema: String, table: String): VersionColumnsResult {
        return VersionColumnsResult(metadata.getVersionColumns(catalog, schema, table))
    }

    @UnmanagedSql
    fun getPrimaryKeys(catalog: String, schema: String, table: String): PrimaryKeyResults {
        return PrimaryKeyResults(metadata.getPrimaryKeys(catalog, schema, table))
    }

    @UnmanagedSql
    fun getImportedKeys(catalog: String, schema: String, table: String): KeysResult {
        return KeysResult(metadata.getImportedKeys(catalog, schema, table))
    }

    @UnmanagedSql
    fun getExportedKeys(catalog: String, schema: String, table: String): KeysResult {
        return KeysResult(metadata.getExportedKeys(catalog, schema, table))
    }

    @UnmanagedSql
    fun getCrossReference(parentCatalog: String,
                          parentSchema: String,
                          parentTable: String,
                          foreignCatalog: String,
                          foreignSchema: String,
                          foreignTable: String): KeysResult {
        return KeysResult(
            metadata.getCrossReference(
                parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema,
                foreignTable
            )
        )
    }

    @UnmanagedSql
    fun getTypeInfo(): TypeInfoResults {
        return TypeInfoResults(metadata.typeInfo)
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

    @UnmanagedSql
    fun getSchemas(catalog: String, schemaPattern: String): SchemaResults {
        return SchemaResults(metadata.getSchemas(catalog, schemaPattern))
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

    private companion object {

        fun ResultSet.toStrings(): List<String> {
            return use {
                ArrayList<String>().apply {
                    while (next()) {
                        add(getString(1))
                    }
                }

            }
        }

    }

}