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

package uk.ac.bournemouth.kotlinsql.monadic

import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.metadata.impl.RawResultSetWrapper
import uk.ac.bournemouth.kotlinsql.metadata.*
import uk.ac.bournemouth.util.kotlin.sql.impl.DBAction2
import java.sql.RowIdLifetime

interface MonadicMetadata<DB : Database> {
    val maxColumnsInIndex: DBAction2.ValueMetadata<DB, Int>
    val supportsSubqueriesInQuantifieds: DBAction2.ValueMetadata<DB, Boolean>
    val supportsIntegrityEnhancementFacility: DBAction2.ValueMetadata<DB, Boolean>
    val supportsGetGeneratedKeys: DBAction2.ValueMetadata<DB, Boolean>
    val supportsCoreSQLGrammar: DBAction2.ValueMetadata<DB, Boolean>
    val supportsDataDefinitionAndDataManipulationTransactions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsCatalogsInTableDefinitions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsOpenStatementsAcrossRollback: DBAction2.ValueMetadata<DB, Boolean>
    val supportsStoredFunctionsUsingCallSyntax: DBAction2.ValueMetadata<DB, Boolean>
    val databaseProductName: DBAction2.ValueMetadata<DB, String>
    val databaseProductVersion: DBAction2.ValueMetadata<DB, String>
    val getJDBCMajorVersion: DBAction2.ValueMetadata<DB, Int>
    val maxProcedureNameLength: DBAction2.ValueMetadata<DB, Int>
    val getCatalogTerm: DBAction2.ValueMetadata<DB, String>
    val supportsCatalogsInDataManipulation: DBAction2.ValueMetadata<DB, Boolean>
    val getMaxUserNameLength: DBAction2.ValueMetadata<DB, Int>
    val timeDateFunctions: DBAction2.ValueMetadata<DB, List<String>>
    val autoCommitFailureClosesAllResultSets: DBAction2.ValueMetadata<DB, Boolean>
    val getMaxColumnsInSelect: DBAction2.ValueMetadata<DB, Int>
    val catalogs: DBAction2.ValueMetadata<DB, List<String>>
    val storesLowerCaseQuotedIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
    val getMaxColumnsInOrderBy: DBAction2.ValueMetadata<DB, Int>
    val getDriverMinorVersion: DBAction2.ValueMetadata<DB, Int>
    val allProceduresAreCallable: DBAction2.ValueMetadata<DB, Boolean>
    val allTablesAreSelectable: DBAction2.ValueMetadata<DB, Boolean>

    @Suppress("PropertyName")
    val URL: DBAction2.ValueMetadata<DB, String>
    val userName: DBAction2.ValueMetadata<DB, String>
    val isReadOnly: DBAction2.ValueMetadata<DB, Boolean>
    val nullsAreSortedHigh: DBAction2.ValueMetadata<DB, Boolean>
    val nullsAreSortedLow: DBAction2.ValueMetadata<DB, Boolean>
    val nullsAreSortedAtStart: DBAction2.ValueMetadata<DB, Boolean>
    val nullsAreSortedAtEnd: DBAction2.ValueMetadata<DB, Boolean>
    val driverName: DBAction2.ValueMetadata<DB, String>
    val driverVersion: DBAction2.ValueMetadata<DB, String>
    val driverMajorVersion: DBAction2.ValueMetadata<DB, Int>
    val driverMinorVersion: DBAction2.ValueMetadata<DB, Int>
    val usesLocalFiles: DBAction2.ValueMetadata<DB, Boolean>
    val usesLocalFilePerTable: DBAction2.ValueMetadata<DB, Boolean>
    val supportsMixedCaseIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
    val storesUpperCaseIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
    val storesLowerCaseIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
    val storesMixedCaseIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
    val supportsMixedCaseQuotedIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
    val storesUpperCaseQuotedIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
    val storesMixedCaseQuotedIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
    val identifierQuoteString: DBAction2.ValueMetadata<DB, String>

    @Suppress("PropertyName")
    val SQLKeywords: DBAction2.ValueMetadata<DB, List<String>>
    val numericFunctions: DBAction2.ValueMetadata<DB, List<String>>
    val stringFunctions: DBAction2.ValueMetadata<DB, List<String>>
    val systemFunctions: DBAction2.ValueMetadata<DB, List<String>>
    val searchStringEscape: DBAction2.ValueMetadata<DB, String>
    val extraNameCharacters: DBAction2.ValueMetadata<DB, String>
    val supportsAlterTableWithAddColumn: DBAction2.ValueMetadata<DB, Boolean>
    val supportsAlterTableWithDropColumn: DBAction2.ValueMetadata<DB, Boolean>
    val supportsColumnAliasing: DBAction2.ValueMetadata<DB, Boolean>
    val nullPlusNonNullIsNull: DBAction2.ValueMetadata<DB, Boolean>
    val supportsConvert: DBAction2.ValueMetadata<DB, Boolean>
    val supportsTableCorrelationNames: DBAction2.ValueMetadata<DB, Boolean>
    val supportsDifferentTableCorrelationNames: DBAction2.ValueMetadata<DB, Boolean>
    val supportsExpressionsInOrderBy: DBAction2.ValueMetadata<DB, Boolean>
    val supportsOrderByUnrelated: DBAction2.ValueMetadata<DB, Boolean>
    val supportsGroupBy: DBAction2.ValueMetadata<DB, Boolean>
    val supportsGroupByUnrelated: DBAction2.ValueMetadata<DB, Boolean>
    val supportsGroupByBeyondSelect: DBAction2.ValueMetadata<DB, Boolean>
    val supportsLikeEscapeClause: DBAction2.ValueMetadata<DB, Boolean>
    val supportsMultipleResultSets: DBAction2.ValueMetadata<DB, Boolean>
    val supportsMultipleTransactions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsNonNullableColumns: DBAction2.ValueMetadata<DB, Boolean>
    val supportsMinimumSQLGrammar: DBAction2.ValueMetadata<DB, Boolean>
    val supportsExtendedSQLGrammar: DBAction2.ValueMetadata<DB, Boolean>
    val supportsANSI92EntryLevelSQL: DBAction2.ValueMetadata<DB, Boolean>
    val supportsANSI92IntermediateSQL: DBAction2.ValueMetadata<DB, Boolean>
    val supportsANSI92FullSQL: DBAction2.ValueMetadata<DB, Boolean>
    val supportsOuterJoins: DBAction2.ValueMetadata<DB, Boolean>
    val supportsFullOuterJoins: DBAction2.ValueMetadata<DB, Boolean>
    val supportsLimitedOuterJoins: DBAction2.ValueMetadata<DB, Boolean>
    val schemaTerm: DBAction2.ValueMetadata<DB, String>
    val procedureTerm: DBAction2.ValueMetadata<DB, String>
    val catalogTerm: DBAction2.ValueMetadata<DB, String>
    val isCatalogAtStart: DBAction2.ValueMetadata<DB, Boolean>
    val catalogSeparator: DBAction2.ValueMetadata<DB, String>
    val supportsSchemasInDataManipulation: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSchemasInProcedureCalls: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSchemasInTableDefinitions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSchemasInIndexDefinitions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSchemasInPrivilegeDefinitions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsCatalogsInProcedureCalls: DBAction2.ValueMetadata<DB, Boolean>
    val supportsCatalogsInIndexDefinitions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsCatalogsInPrivilegeDefinitions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsPositionedDelete: DBAction2.ValueMetadata<DB, Boolean>
    val supportsPositionedUpdate: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSelectForUpdate: DBAction2.ValueMetadata<DB, Boolean>
    val supportsStoredProcedures: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSubqueriesInComparisons: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSubqueriesInExists: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSubqueriesInIns: DBAction2.ValueMetadata<DB, Boolean>
    val supportsCorrelatedSubqueries: DBAction2.ValueMetadata<DB, Boolean>
    val supportsUnion: DBAction2.ValueMetadata<DB, Boolean>
    val supportsUnionAll: DBAction2.ValueMetadata<DB, Boolean>
    val supportsOpenCursorsAcrossCommit: DBAction2.ValueMetadata<DB, Boolean>
    val supportsOpenCursorsAcrossRollback: DBAction2.ValueMetadata<DB, Boolean>
    val supportsOpenStatementsAcrossCommit: DBAction2.ValueMetadata<DB, Boolean>
    val maxBinaryLiteralLength: DBAction2.ValueMetadata<DB, Int>
    val maxCharLiteralLength: DBAction2.ValueMetadata<DB, Int>
    val maxColumnNameLength: DBAction2.ValueMetadata<DB, Int>
    val maxColumnsInGroupBy: DBAction2.ValueMetadata<DB, Int>
    val maxColumnsInOrderBy: DBAction2.ValueMetadata<DB, Int>
    val maxColumnsInSelect: DBAction2.ValueMetadata<DB, Int>
    val maxColumnsInTable: DBAction2.ValueMetadata<DB, Int>
    val maxConnections: DBAction2.ValueMetadata<DB, Int>
    val maxCursorNameLength: DBAction2.ValueMetadata<DB, Int>
    val maxIndexLength: DBAction2.ValueMetadata<DB, Int>
    val maxSchemaNameLength: DBAction2.ValueMetadata<DB, Int>
    val maxCatalogNameLength: DBAction2.ValueMetadata<DB, Int>
    val maxRowSize: DBAction2.ValueMetadata<DB, Int>
    val doesMaxRowSizeIncludeBlobs: DBAction2.ValueMetadata<DB, Boolean>
    val maxStatementLength: DBAction2.ValueMetadata<DB, Int>
    val maxStatements: DBAction2.ValueMetadata<DB, Int>
    val maxTableNameLength: DBAction2.ValueMetadata<DB, Int>
    val maxTablesInSelect: DBAction2.ValueMetadata<DB, Int>
    val maxUserNameLength: DBAction2.ValueMetadata<DB, Int>
    val defaultTransactionIsolation: DBAction2.ValueMetadata<DB, Int>
    val supportsTransactions: DBAction2.ValueMetadata<DB, Boolean>
    val supportsDataManipulationTransactionsOnly: DBAction2.ValueMetadata<DB, Boolean>
    val dataDefinitionCausesTransactionCommit: DBAction2.ValueMetadata<DB, Boolean>
    val dataDefinitionIgnoredInTransactions: DBAction2.ValueMetadata<DB, Boolean>
    val schemas: DBAction2.ResultSetMetadata<DB, SchemaResults>
    val supportsBatchUpdates: DBAction2.ValueMetadata<DB, Boolean>
    val supportsSavepoints: DBAction2.ValueMetadata<DB, Boolean>
    val supportsNamedParameters: DBAction2.ValueMetadata<DB, Boolean>
    val supportsMultipleOpenResults: DBAction2.ValueMetadata<DB, Boolean>
    val resultSetHoldability: DBAction2.ValueMetadata<DB, Int>
    val databaseMajorVersion: DBAction2.ValueMetadata<DB, Int>
    val databaseMinorVersion: DBAction2.ValueMetadata<DB, Int>

    @Suppress("PropertyName")
    val JDBCMajorVersion: DBAction2.ValueMetadata<DB, Int>

    @Suppress("PropertyName")
    val JDBCMinorVersion: DBAction2.ValueMetadata<DB, Int>

    @Suppress("PropertyName")
    val SQLStateType: DBAction2.ValueMetadata<DB, Int>
    val locatorsUpdateCopy: DBAction2.ValueMetadata<DB, Boolean>
    val supportsStatementPooling: DBAction2.ValueMetadata<DB, Boolean>
    val rowIdLifetime: DBAction2.ValueMetadata<DB, RowIdLifetime>
    val generatedKeyAlwaysReturned: DBAction2.ValueMetadata<DB, Boolean>
    val maxLogicalLobSize: DBAction2.ValueMetadata<DB, Long>
    val supportsRefCursors: DBAction2.ValueMetadata<DB, Boolean>
    fun insertsAreDetected(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?
                     ): DBAction2.ResultSetMetadata<DB, AttributeResults>

    fun supportsConvert(fromType: Int, toType: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun supportsTransactionIsolationLevel(level: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun getProcedures(catalog: String, schemaPattern: String, procedureNamePattern: String): DBAction2.ResultSetMetadata<DB, ProcedureResults>
    fun getProcedureColumns(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
        columnNamePattern: String
                           ): DBAction2.ResultSetMetadata<DB, ProcedureColumnResults>

    fun getTables(
        catalog: String? = null,
        schemaPattern: String? = null,
        tableNamePattern: String? = null,
        types: Array<String>? = null
                 ): DBAction2.ResultSetMetadata<DB, TableMetadataResults>

    val tableTypes: DBAction2.ValueMetadata<DB, List<String>>
    fun getColumns(
        catalog: String? = null,
        schemaPattern: String? = null,
        tableNamePattern: String?,
        columnNamePattern: String? = null
                  ): DBAction2.ResultSetMetadata<DB, ColumnsResults>

    fun getColumnPrivileges(
        catalog: String,
        schema: String,
        table: String,
        columnNamePattern: String
                           ): DBAction2.ResultSetMetadata<DB, ColumnPrivilegesResult>

    fun getTablePrivileges(catalog: String, schemaPattern: String, tableNamePattern: String): DBAction2.ResultSetMetadata<DB, TablePrivilegesResult>
    fun getBestRowIdentifier(
        catalog: String,
        schema: String,
        table: String,
        scope: Int,
        nullable: Boolean
                            ): DBAction2.ResultSetMetadata<DB, BestRowIdentifierResult>

    fun getVersionColumns(catalog: String, schema: String, table: String): DBAction2.ResultSetMetadata<DB, VersionColumnsResult>
    fun getPrimaryKeys(catalog: String, schema: String, table: String): DBAction2.ResultSetMetadata<DB, PrimaryKeyResults>
    fun getImportedKeys(catalog: String, schema: String, table: String): DBAction2.ResultSetMetadata<DB, KeysResult>
    fun getExportedKeys(catalog: String, schema: String, table: String): DBAction2.ResultSetMetadata<DB, KeysResult>
    fun getCrossReference(
        parentCatalog: String,
        parentSchema: String,
        parentTable: String,
        foreignCatalog: String,
        foreignSchema: String,
        foreignTable: String
                         ): DBAction2.ResultSetMetadata<DB, KeysResult>

    fun getTypeInfo(): DBAction2.ResultSetMetadata<DB, TypeInfoResults>
    fun getUnsafeIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean
                          ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>

    fun supportsResultSetConcurrency(type: Int, concurrency: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun ownUpdatesAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun ownDeletesAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun ownInsertsAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun othersUpdatesAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun othersDeletesAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun othersInsertsAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun updatesAreDetected(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun deletesAreDetected(type: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun getUDTs(catalog: String, schemaPattern: String, typeNamePattern: String, types: IntArray): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>
    fun getSuperTypes(catalog: String, schemaPattern: String, typeNamePattern: String): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>
    fun getSuperTables(catalog: String, schemaPattern: String, tableNamePattern: String): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>
    fun supportsResultSetHoldability(holdability: Int): DBAction2.ValueMetadata<DB, Boolean>
    fun getSchemas(catalog: String, schemaPattern: String): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>
    fun getClientInfoProperties(): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>
    fun getFunctions(catalog: String, schemaPattern: String, functionNamePattern: String): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>
    fun getFunctionColumns(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String,
        columnNamePattern: String
                          ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>

    fun getPseudoColumns(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        columnNamePattern: String
                        ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper>
}