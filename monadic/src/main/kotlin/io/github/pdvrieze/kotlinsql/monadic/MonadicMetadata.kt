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
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.metadata.impl.RawResultSetWrapper
import io.github.pdvrieze.kotlinsql.metadata.*
import java.sql.RowIdLifetime

interface MonadicMetadata<DB : Database> {
    val maxColumnsInIndex: ValueMetadataAction<DB, Int>
    val supportsSubqueriesInQuantifieds: ValueMetadataAction<DB, Boolean>
    val supportsIntegrityEnhancementFacility: ValueMetadataAction<DB, Boolean>
    val supportsGetGeneratedKeys: ValueMetadataAction<DB, Boolean>
    val supportsCoreSQLGrammar: ValueMetadataAction<DB, Boolean>
    val supportsDataDefinitionAndDataManipulationTransactions: ValueMetadataAction<DB, Boolean>
    val supportsCatalogsInTableDefinitions: ValueMetadataAction<DB, Boolean>
    val supportsOpenStatementsAcrossRollback: ValueMetadataAction<DB, Boolean>
    val supportsStoredFunctionsUsingCallSyntax: ValueMetadataAction<DB, Boolean>
    val databaseProductName: ValueMetadataAction<DB, String>
    val databaseProductVersion: ValueMetadataAction<DB, String>
    val getJDBCMajorVersion: ValueMetadataAction<DB, Int>
    val maxProcedureNameLength: ValueMetadataAction<DB, Int>
    val getCatalogTerm: ValueMetadataAction<DB, String>
    val supportsCatalogsInDataManipulation: ValueMetadataAction<DB, Boolean>
    val getMaxUserNameLength: ValueMetadataAction<DB, Int>
    val timeDateFunctions: ValueMetadataAction<DB, List<String>>
    val autoCommitFailureClosesAllResultSets: ValueMetadataAction<DB, Boolean>
    val getMaxColumnsInSelect: ValueMetadataAction<DB, Int>
    val catalogs: ValueMetadataAction<DB, List<String>>
    val storesLowerCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
    val getMaxColumnsInOrderBy: ValueMetadataAction<DB, Int>
    val getDriverMinorVersion: ValueMetadataAction<DB, Int>
    val allProceduresAreCallable: ValueMetadataAction<DB, Boolean>
    val allTablesAreSelectable: ValueMetadataAction<DB, Boolean>

    @Suppress("PropertyName")
    val URL: ValueMetadataAction<DB, String>
    val userName: ValueMetadataAction<DB, String>
    val isReadOnly: ValueMetadataAction<DB, Boolean>
    val nullsAreSortedHigh: ValueMetadataAction<DB, Boolean>
    val nullsAreSortedLow: ValueMetadataAction<DB, Boolean>
    val nullsAreSortedAtStart: ValueMetadataAction<DB, Boolean>
    val nullsAreSortedAtEnd: ValueMetadataAction<DB, Boolean>
    val driverName: ValueMetadataAction<DB, String>
    val driverVersion: ValueMetadataAction<DB, String>
    val driverMajorVersion: ValueMetadataAction<DB, Int>
    val driverMinorVersion: ValueMetadataAction<DB, Int>
    val usesLocalFiles: ValueMetadataAction<DB, Boolean>
    val usesLocalFilePerTable: ValueMetadataAction<DB, Boolean>
    val supportsMixedCaseIdentifiers: ValueMetadataAction<DB, Boolean>
    val storesUpperCaseIdentifiers: ValueMetadataAction<DB, Boolean>
    val storesLowerCaseIdentifiers: ValueMetadataAction<DB, Boolean>
    val storesMixedCaseIdentifiers: ValueMetadataAction<DB, Boolean>
    val supportsMixedCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
    val storesUpperCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
    val storesMixedCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
    val identifierQuoteString: ValueMetadataAction<DB, String>

    @Suppress("PropertyName")
    val SQLKeywords: ValueMetadataAction<DB, List<String>>
    val numericFunctions: ValueMetadataAction<DB, List<String>>
    val stringFunctions: ValueMetadataAction<DB, List<String>>
    val systemFunctions: ValueMetadataAction<DB, List<String>>
    val searchStringEscape: ValueMetadataAction<DB, String>
    val extraNameCharacters: ValueMetadataAction<DB, String>
    val supportsAlterTableWithAddColumn: ValueMetadataAction<DB, Boolean>
    val supportsAlterTableWithDropColumn: ValueMetadataAction<DB, Boolean>
    val supportsColumnAliasing: ValueMetadataAction<DB, Boolean>
    val nullPlusNonNullIsNull: ValueMetadataAction<DB, Boolean>
    val supportsConvert: ValueMetadataAction<DB, Boolean>
    val supportsTableCorrelationNames: ValueMetadataAction<DB, Boolean>
    val supportsDifferentTableCorrelationNames: ValueMetadataAction<DB, Boolean>
    val supportsExpressionsInOrderBy: ValueMetadataAction<DB, Boolean>
    val supportsOrderByUnrelated: ValueMetadataAction<DB, Boolean>
    val supportsGroupBy: ValueMetadataAction<DB, Boolean>
    val supportsGroupByUnrelated: ValueMetadataAction<DB, Boolean>
    val supportsGroupByBeyondSelect: ValueMetadataAction<DB, Boolean>
    val supportsLikeEscapeClause: ValueMetadataAction<DB, Boolean>
    val supportsMultipleResultSets: ValueMetadataAction<DB, Boolean>
    val supportsMultipleTransactions: ValueMetadataAction<DB, Boolean>
    val supportsNonNullableColumns: ValueMetadataAction<DB, Boolean>
    val supportsMinimumSQLGrammar: ValueMetadataAction<DB, Boolean>
    val supportsExtendedSQLGrammar: ValueMetadataAction<DB, Boolean>
    val supportsANSI92EntryLevelSQL: ValueMetadataAction<DB, Boolean>
    val supportsANSI92IntermediateSQL: ValueMetadataAction<DB, Boolean>
    val supportsANSI92FullSQL: ValueMetadataAction<DB, Boolean>
    val supportsOuterJoins: ValueMetadataAction<DB, Boolean>
    val supportsFullOuterJoins: ValueMetadataAction<DB, Boolean>
    val supportsLimitedOuterJoins: ValueMetadataAction<DB, Boolean>
    val schemaTerm: ValueMetadataAction<DB, String>
    val procedureTerm: ValueMetadataAction<DB, String>
    val catalogTerm: ValueMetadataAction<DB, String>
    val isCatalogAtStart: ValueMetadataAction<DB, Boolean>
    val catalogSeparator: ValueMetadataAction<DB, String>
    val supportsSchemasInDataManipulation: ValueMetadataAction<DB, Boolean>
    val supportsSchemasInProcedureCalls: ValueMetadataAction<DB, Boolean>
    val supportsSchemasInTableDefinitions: ValueMetadataAction<DB, Boolean>
    val supportsSchemasInIndexDefinitions: ValueMetadataAction<DB, Boolean>
    val supportsSchemasInPrivilegeDefinitions: ValueMetadataAction<DB, Boolean>
    val supportsCatalogsInProcedureCalls: ValueMetadataAction<DB, Boolean>
    val supportsCatalogsInIndexDefinitions: ValueMetadataAction<DB, Boolean>
    val supportsCatalogsInPrivilegeDefinitions: ValueMetadataAction<DB, Boolean>
    val supportsPositionedDelete: ValueMetadataAction<DB, Boolean>
    val supportsPositionedUpdate: ValueMetadataAction<DB, Boolean>
    val supportsSelectForUpdate: ValueMetadataAction<DB, Boolean>
    val supportsStoredProcedures: ValueMetadataAction<DB, Boolean>
    val supportsSubqueriesInComparisons: ValueMetadataAction<DB, Boolean>
    val supportsSubqueriesInExists: ValueMetadataAction<DB, Boolean>
    val supportsSubqueriesInIns: ValueMetadataAction<DB, Boolean>
    val supportsCorrelatedSubqueries: ValueMetadataAction<DB, Boolean>
    val supportsUnion: ValueMetadataAction<DB, Boolean>
    val supportsUnionAll: ValueMetadataAction<DB, Boolean>
    val supportsOpenCursorsAcrossCommit: ValueMetadataAction<DB, Boolean>
    val supportsOpenCursorsAcrossRollback: ValueMetadataAction<DB, Boolean>
    val supportsOpenStatementsAcrossCommit: ValueMetadataAction<DB, Boolean>
    val maxBinaryLiteralLength: ValueMetadataAction<DB, Int>
    val maxCharLiteralLength: ValueMetadataAction<DB, Int>
    val maxColumnNameLength: ValueMetadataAction<DB, Int>
    val maxColumnsInGroupBy: ValueMetadataAction<DB, Int>
    val maxColumnsInOrderBy: ValueMetadataAction<DB, Int>
    val maxColumnsInSelect: ValueMetadataAction<DB, Int>
    val maxColumnsInTable: ValueMetadataAction<DB, Int>
    val maxConnections: ValueMetadataAction<DB, Int>
    val maxCursorNameLength: ValueMetadataAction<DB, Int>
    val maxIndexLength: ValueMetadataAction<DB, Int>
    val maxSchemaNameLength: ValueMetadataAction<DB, Int>
    val maxCatalogNameLength: ValueMetadataAction<DB, Int>
    val maxRowSize: ValueMetadataAction<DB, Int>
    val doesMaxRowSizeIncludeBlobs: ValueMetadataAction<DB, Boolean>
    val maxStatementLength: ValueMetadataAction<DB, Int>
    val maxStatements: ValueMetadataAction<DB, Int>
    val maxTableNameLength: ValueMetadataAction<DB, Int>
    val maxTablesInSelect: ValueMetadataAction<DB, Int>
    val maxUserNameLength: ValueMetadataAction<DB, Int>
    val defaultTransactionIsolation: ValueMetadataAction<DB, Int>
    val supportsTransactions: ValueMetadataAction<DB, Boolean>
    val supportsDataManipulationTransactionsOnly: ValueMetadataAction<DB, Boolean>
    val dataDefinitionCausesTransactionCommit: ValueMetadataAction<DB, Boolean>
    val dataDefinitionIgnoredInTransactions: ValueMetadataAction<DB, Boolean>
    val schemas: ResultSetMetadataAction<DB, SchemaResults>
    val supportsBatchUpdates: ValueMetadataAction<DB, Boolean>
    val supportsSavepoints: ValueMetadataAction<DB, Boolean>
    val supportsNamedParameters: ValueMetadataAction<DB, Boolean>
    val supportsMultipleOpenResults: ValueMetadataAction<DB, Boolean>
    val resultSetHoldability: ValueMetadataAction<DB, Int>
    val databaseMajorVersion: ValueMetadataAction<DB, Int>
    val databaseMinorVersion: ValueMetadataAction<DB, Int>

    @Suppress("PropertyName")
    val JDBCMajorVersion: ValueMetadataAction<DB, Int>

    @Suppress("PropertyName")
    val JDBCMinorVersion: ValueMetadataAction<DB, Int>

    @Suppress("PropertyName")
    val SQLStateType: ValueMetadataAction<DB, Int>
    val locatorsUpdateCopy: ValueMetadataAction<DB, Boolean>
    val supportsStatementPooling: ValueMetadataAction<DB, Boolean>
    val rowIdLifetime: ValueMetadataAction<DB, RowIdLifetime>
    val generatedKeyAlwaysReturned: ValueMetadataAction<DB, Boolean>
    val maxLogicalLobSize: ValueMetadataAction<DB, Long>
    val supportsRefCursors: ValueMetadataAction<DB, Boolean>
    fun insertsAreDetected(type: Int): ValueMetadataAction<DB, Boolean>
    fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?,
    ): ResultSetMetadataAction<DB, AttributeResults>

    fun supportsConvert(
        fromType: Int,
        toType: Int,
    ): ValueMetadataAction<DB, Boolean>

    fun supportsTransactionIsolationLevel(level: Int): ValueMetadataAction<DB, Boolean>
    fun getProcedures(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
    ): ResultSetMetadataAction<DB, ProcedureResults>

    fun getProcedureColumns(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
        columnNamePattern: String,
    ): ResultSetMetadataAction<DB, ProcedureColumnResults>

    fun getTables(
        catalog: String? = null,
        schemaPattern: String? = null,
        tableNamePattern: String? = null,
        types: Array<String>? = null,
    ): ResultSetMetadataAction<DB, TableMetadataResults>

    val tableTypes: ValueMetadataAction<DB, List<String>>
    fun getColumns(
        catalog: String? = null,
        schemaPattern: String? = null,
        tableNamePattern: String?,
        columnNamePattern: String? = null,
    ): ResultSetMetadataAction<DB, ColumnsResults>

    fun getColumnPrivileges(
        catalog: String,
        schema: String,
        table: String,
        columnNamePattern: String,
    ): ResultSetMetadataAction<DB, ColumnPrivilegesResult>

    fun getTablePrivileges(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
    ): ResultSetMetadataAction<DB, TablePrivilegesResult>

    fun getBestRowIdentifier(
        catalog: String,
        schema: String,
        table: String,
        scope: Int,
        nullable: Boolean,
    ): ResultSetMetadataAction<DB, BestRowIdentifierResult>

    fun getVersionColumns(
        catalog: String,
        schema: String,
        table: String,
    ): ResultSetMetadataAction<DB, VersionColumnsResult>

    fun getPrimaryKeys(
        catalog: String,
        schema: String,
        table: String,
    ): ResultSetMetadataAction<DB, PrimaryKeyResults>

    fun getImportedKeys(
        catalog: String,
        schema: String,
        table: String,
    ): ResultSetMetadataAction<DB, KeysResult>

    fun getExportedKeys(
        catalog: String,
        schema: String,
        table: String,
    ): ResultSetMetadataAction<DB, KeysResult>

    fun getCrossReference(
        parentCatalog: String,
        parentSchema: String,
        parentTable: String,
        foreignCatalog: String,
        foreignSchema: String,
        foreignTable: String,
    ): ResultSetMetadataAction<DB, KeysResult>

    fun getTypeInfo(): ResultSetMetadataAction<DB, TypeInfoResults>

    @UnmanagedSql
    fun getUnsafeIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean,
    ): ResultSetMetadataAction<DB, RawResultSetWrapper>

    fun supportsResultSetConcurrency(
        type: Int,
        concurrency: Int,
    ): ValueMetadataAction<DB, Boolean>

    fun ownUpdatesAreVisible(type: Int): ValueMetadataAction<DB, Boolean>
    fun ownDeletesAreVisible(type: Int): ValueMetadataAction<DB, Boolean>
    fun ownInsertsAreVisible(type: Int): ValueMetadataAction<DB, Boolean>
    fun othersUpdatesAreVisible(type: Int): ValueMetadataAction<DB, Boolean>
    fun othersDeletesAreVisible(type: Int): ValueMetadataAction<DB, Boolean>
    fun othersInsertsAreVisible(type: Int): ValueMetadataAction<DB, Boolean>
    fun updatesAreDetected(type: Int): ValueMetadataAction<DB, Boolean>
    fun deletesAreDetected(type: Int): ValueMetadataAction<DB, Boolean>

    @UnmanagedSql
    fun getUDTs(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String,
        types: IntArray,
    ): ResultSetMetadataAction<DB, RawResultSetWrapper>

    @UnmanagedSql
    fun getSuperTypes(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String,
    ): ResultSetMetadataAction<DB, RawResultSetWrapper>

    @UnmanagedSql
    fun getSuperTables(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
    ): ResultSetMetadataAction<DB, RawResultSetWrapper>

    fun supportsResultSetHoldability(holdability: Int): ValueMetadataAction<DB, Boolean>

    fun getSchemas(
        catalog: String,
        schemaPattern: String,
    ): ResultSetMetadataAction<DB, SchemaResults>

    @UnmanagedSql
    fun getClientInfoProperties(): ResultSetMetadataAction<DB, RawResultSetWrapper>

    @UnmanagedSql
    fun getFunctions(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String,
    ): ResultSetMetadataAction<DB, RawResultSetWrapper>

    @UnmanagedSql
    fun getFunctionColumns(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String,
        columnNamePattern: String,
    ): ResultSetMetadataAction<DB, RawResultSetWrapper>

    @UnmanagedSql
    fun getPseudoColumns(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        columnNamePattern: String,
    ): ResultSetMetadataAction<DB, RawResultSetWrapper>
}