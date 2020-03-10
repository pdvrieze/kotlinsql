/*
 * Copyright (c) 2020.
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

import uk.ac.bournemouth.util.kotlin.sql.impl.DBAction2.ResultSetMetadata
import uk.ac.bournemouth.util.kotlin.sql.impl.DBAction2.ValueMetadata
import java.sql.ResultSet
import java.sql.RowIdLifetime

interface MonadicMetadata<DB : Database> {
    val maxColumnsInIndex: ValueMetadata<DB, Int>
    val supportsSubqueriesInQuantifieds: ValueMetadata<DB, Boolean>
    val supportsIntegrityEnhancementFacility: ValueMetadata<DB, Boolean>
    val supportsGetGeneratedKeys: ValueMetadata<DB, Boolean>
    val supportsCoreSQLGrammar: ValueMetadata<DB, Boolean>
    val supportsDataDefinitionAndDataManipulationTransactions: ValueMetadata<DB, Boolean>
    val supportsCatalogsInTableDefinitions: ValueMetadata<DB, Boolean>
    val supportsOpenStatementsAcrossRollback: ValueMetadata<DB, Boolean>
    val supportsStoredFunctionsUsingCallSyntax: ValueMetadata<DB, Boolean>
    val databaseProductName: ValueMetadata<DB, String>
    val databaseProductVersion: ValueMetadata<DB, String>
    val getJDBCMajorVersion: ValueMetadata<DB, Int>
    val maxProcedureNameLength: ValueMetadata<DB, Int>
    val getCatalogTerm: ValueMetadata<DB, String>
    val supportsCatalogsInDataManipulation: ValueMetadata<DB, Boolean>
    val getMaxUserNameLength: ValueMetadata<DB, Int>
    val timeDateFunctions: ValueMetadata<DB, List<String>>
    val autoCommitFailureClosesAllResultSets: ValueMetadata<DB, Boolean>
    val getMaxColumnsInSelect: ValueMetadata<DB, Int>
    val catalogs: ValueMetadata<DB, List<String>>
    val storesLowerCaseQuotedIdentifiers: ValueMetadata<DB, Boolean>
    val getMaxColumnsInOrderBy: ValueMetadata<DB, Int>
    val getDriverMinorVersion: ValueMetadata<DB, Int>
    val allProceduresAreCallable: ValueMetadata<DB, Boolean>
    val allTablesAreSelectable: ValueMetadata<DB, Boolean>

    @Suppress("PropertyName")
    val URL: ValueMetadata<DB, String>
    val userName: ValueMetadata<DB, String>
    val isReadOnly: ValueMetadata<DB, Boolean>
    val nullsAreSortedHigh: ValueMetadata<DB, Boolean>
    val nullsAreSortedLow: ValueMetadata<DB, Boolean>
    val nullsAreSortedAtStart: ValueMetadata<DB, Boolean>
    val nullsAreSortedAtEnd: ValueMetadata<DB, Boolean>
    val driverName: ValueMetadata<DB, String>
    val driverVersion: ValueMetadata<DB, String>
    val driverMajorVersion: ValueMetadata<DB, Int>
    val driverMinorVersion: ValueMetadata<DB, Int>
    val usesLocalFiles: ValueMetadata<DB, Boolean>
    val usesLocalFilePerTable: ValueMetadata<DB, Boolean>
    val supportsMixedCaseIdentifiers: ValueMetadata<DB, Boolean>
    val storesUpperCaseIdentifiers: ValueMetadata<DB, Boolean>
    val storesLowerCaseIdentifiers: ValueMetadata<DB, Boolean>
    val storesMixedCaseIdentifiers: ValueMetadata<DB, Boolean>
    val supportsMixedCaseQuotedIdentifiers: ValueMetadata<DB, Boolean>
    val storesUpperCaseQuotedIdentifiers: ValueMetadata<DB, Boolean>
    val storesMixedCaseQuotedIdentifiers: ValueMetadata<DB, Boolean>
    val identifierQuoteString: ValueMetadata<DB, String>

    @Suppress("PropertyName")
    val SQLKeywords: ValueMetadata<DB, List<String>>
    val numericFunctions: ValueMetadata<DB, List<String>>
    val stringFunctions: ValueMetadata<DB, List<String>>
    val systemFunctions: ValueMetadata<DB, List<String>>
    val searchStringEscape: ValueMetadata<DB, String>
    val extraNameCharacters: ValueMetadata<DB, String>
    val supportsAlterTableWithAddColumn: ValueMetadata<DB, Boolean>
    val supportsAlterTableWithDropColumn: ValueMetadata<DB, Boolean>
    val supportsColumnAliasing: ValueMetadata<DB, Boolean>
    val nullPlusNonNullIsNull: ValueMetadata<DB, Boolean>
    val supportsConvert: ValueMetadata<DB, Boolean>
    val supportsTableCorrelationNames: ValueMetadata<DB, Boolean>
    val supportsDifferentTableCorrelationNames: ValueMetadata<DB, Boolean>
    val supportsExpressionsInOrderBy: ValueMetadata<DB, Boolean>
    val supportsOrderByUnrelated: ValueMetadata<DB, Boolean>
    val supportsGroupBy: ValueMetadata<DB, Boolean>
    val supportsGroupByUnrelated: ValueMetadata<DB, Boolean>
    val supportsGroupByBeyondSelect: ValueMetadata<DB, Boolean>
    val supportsLikeEscapeClause: ValueMetadata<DB, Boolean>
    val supportsMultipleResultSets: ValueMetadata<DB, Boolean>
    val supportsMultipleTransactions: ValueMetadata<DB, Boolean>
    val supportsNonNullableColumns: ValueMetadata<DB, Boolean>
    val supportsMinimumSQLGrammar: ValueMetadata<DB, Boolean>
    val supportsExtendedSQLGrammar: ValueMetadata<DB, Boolean>
    val supportsANSI92EntryLevelSQL: ValueMetadata<DB, Boolean>
    val supportsANSI92IntermediateSQL: ValueMetadata<DB, Boolean>
    val supportsANSI92FullSQL: ValueMetadata<DB, Boolean>
    val supportsOuterJoins: ValueMetadata<DB, Boolean>
    val supportsFullOuterJoins: ValueMetadata<DB, Boolean>
    val supportsLimitedOuterJoins: ValueMetadata<DB, Boolean>
    val schemaTerm: ValueMetadata<DB, String>
    val procedureTerm: ValueMetadata<DB, String>
    val catalogTerm: ValueMetadata<DB, String>
    val isCatalogAtStart: ValueMetadata<DB, Boolean>
    val catalogSeparator: ValueMetadata<DB, String>
    val supportsSchemasInDataManipulation: ValueMetadata<DB, Boolean>
    val supportsSchemasInProcedureCalls: ValueMetadata<DB, Boolean>
    val supportsSchemasInTableDefinitions: ValueMetadata<DB, Boolean>
    val supportsSchemasInIndexDefinitions: ValueMetadata<DB, Boolean>
    val supportsSchemasInPrivilegeDefinitions: ValueMetadata<DB, Boolean>
    val supportsCatalogsInProcedureCalls: ValueMetadata<DB, Boolean>
    val supportsCatalogsInIndexDefinitions: ValueMetadata<DB, Boolean>
    val supportsCatalogsInPrivilegeDefinitions: ValueMetadata<DB, Boolean>
    val supportsPositionedDelete: ValueMetadata<DB, Boolean>
    val supportsPositionedUpdate: ValueMetadata<DB, Boolean>
    val supportsSelectForUpdate: ValueMetadata<DB, Boolean>
    val supportsStoredProcedures: ValueMetadata<DB, Boolean>
    val supportsSubqueriesInComparisons: ValueMetadata<DB, Boolean>
    val supportsSubqueriesInExists: ValueMetadata<DB, Boolean>
    val supportsSubqueriesInIns: ValueMetadata<DB, Boolean>
    val supportsCorrelatedSubqueries: ValueMetadata<DB, Boolean>
    val supportsUnion: ValueMetadata<DB, Boolean>
    val supportsUnionAll: ValueMetadata<DB, Boolean>
    val supportsOpenCursorsAcrossCommit: ValueMetadata<DB, Boolean>
    val supportsOpenCursorsAcrossRollback: ValueMetadata<DB, Boolean>
    val supportsOpenStatementsAcrossCommit: ValueMetadata<DB, Boolean>
    val maxBinaryLiteralLength: ValueMetadata<DB, Int>
    val maxCharLiteralLength: ValueMetadata<DB, Int>
    val maxColumnNameLength: ValueMetadata<DB, Int>
    val maxColumnsInGroupBy: ValueMetadata<DB, Int>
    val maxColumnsInOrderBy: ValueMetadata<DB, Int>
    val maxColumnsInSelect: ValueMetadata<DB, Int>
    val maxColumnsInTable: ValueMetadata<DB, Int>
    val maxConnections: ValueMetadata<DB, Int>
    val maxCursorNameLength: ValueMetadata<DB, Int>
    val maxIndexLength: ValueMetadata<DB, Int>
    val maxSchemaNameLength: ValueMetadata<DB, Int>
    val maxCatalogNameLength: ValueMetadata<DB, Int>
    val maxRowSize: ValueMetadata<DB, Int>
    val doesMaxRowSizeIncludeBlobs: ValueMetadata<DB, Boolean>
    val maxStatementLength: ValueMetadata<DB, Int>
    val maxStatements: ValueMetadata<DB, Int>
    val maxTableNameLength: ValueMetadata<DB, Int>
    val maxTablesInSelect: ValueMetadata<DB, Int>
    val maxUserNameLength: ValueMetadata<DB, Int>
    val defaultTransactionIsolation: ValueMetadata<DB, Int>
    val supportsTransactions: ValueMetadata<DB, Boolean>
    val supportsDataManipulationTransactionsOnly: ValueMetadata<DB, Boolean>
    val dataDefinitionCausesTransactionCommit: ValueMetadata<DB, Boolean>
    val dataDefinitionIgnoredInTransactions: ValueMetadata<DB, Boolean>
    val schemas: ResultSetMetadata<DB, SchemaResults>
    val supportsBatchUpdates: ValueMetadata<DB, Boolean>
    val supportsSavepoints: ValueMetadata<DB, Boolean>
    val supportsNamedParameters: ValueMetadata<DB, Boolean>
    val supportsMultipleOpenResults: ValueMetadata<DB, Boolean>
    val resultSetHoldability: ValueMetadata<DB, Int>
    val databaseMajorVersion: ValueMetadata<DB, Int>
    val databaseMinorVersion: ValueMetadata<DB, Int>

    @Suppress("PropertyName")
    val JDBCMajorVersion: ValueMetadata<DB, Int>

    @Suppress("PropertyName")
    val JDBCMinorVersion: ValueMetadata<DB, Int>

    @Suppress("PropertyName")
    val SQLStateType: ValueMetadata<DB, Int>
    val locatorsUpdateCopy: ValueMetadata<DB, Boolean>
    val supportsStatementPooling: ValueMetadata<DB, Boolean>
    val rowIdLifetime: ValueMetadata<DB, RowIdLifetime>
    val generatedKeyAlwaysReturned: ValueMetadata<DB, Boolean>
    val maxLogicalLobSize: ValueMetadata<DB, Long>
    val supportsRefCursors: ValueMetadata<DB, Boolean>
    fun insertsAreDetected(type: Int): ValueMetadata<DB, Boolean>
    fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?
                     ): ResultSetMetadata<DB, AttributeResults>

    fun supportsConvert(fromType: Int, toType: Int): ValueMetadata<DB, Boolean>
    fun supportsTransactionIsolationLevel(level: Int): ValueMetadata<DB, Boolean>
    fun getProcedures(catalog: String, schemaPattern: String, procedureNamePattern: String): ResultSetMetadata<DB, ProcedureResults>
    fun getProcedureColumns(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
        columnNamePattern: String
                           ): ResultSetMetadata<DB, ProcedureColumnResults>

    fun getTables(
        catalog: String? = null,
        schemaPattern: String? = null,
        tableNamePattern: String? = null,
        types: Array<String>? = null
                 ): ResultSetMetadata<DB, TableResults>

    val tableTypes: ValueMetadata<DB, List<String>>
    fun getColumns(
        catalog: String? = null,
        schemaPattern: String? = null,
        tableNamePattern: String?,
        columnNamePattern: String? = null
                  ): ResultSetMetadata<DB, ColumnsResults>

    fun getColumnPrivileges(
        catalog: String,
        schema: String,
        table: String,
        columnNamePattern: String
                           ): ResultSetMetadata<DB, ColumnPrivilegesResult>

    fun getTablePrivileges(catalog: String, schemaPattern: String, tableNamePattern: String): ResultSetMetadata<DB, TablePrivilegesResult>
    fun getBestRowIdentifier(
        catalog: String,
        schema: String,
        table: String,
        scope: Int,
        nullable: Boolean
                            ): ResultSetMetadata<DB, BestRowIdentifierResult>

    fun getVersionColumns(catalog: String, schema: String, table: String): ResultSetMetadata<DB, VersionColumnsResult>
    fun getPrimaryKeys(catalog: String, schema: String, table: String): ResultSetMetadata<DB, PrimaryKeyResults>
    fun getImportedKeys(catalog: String, schema: String, table: String): ResultSetMetadata<DB, KeysResult>
    fun getExportedKeys(catalog: String, schema: String, table: String): ResultSetMetadata<DB, KeysResult>
    fun getCrossReference(
        parentCatalog: String,
        parentSchema: String,
        parentTable: String,
        foreignCatalog: String,
        foreignSchema: String,
        foreignTable: String
                         ): ResultSetMetadata<DB, KeysResult>

    fun getTypeInfo(): ResultSetMetadata<DB, TypeInfo>
    fun getUnsafeIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean
                          ): ResultSetMetadata<DB, RawResultSetWrapper>

    fun supportsResultSetConcurrency(type: Int, concurrency: Int): ValueMetadata<DB, Boolean>
    fun ownUpdatesAreVisible(type: Int): ValueMetadata<DB, Boolean>
    fun ownDeletesAreVisible(type: Int): ValueMetadata<DB, Boolean>
    fun ownInsertsAreVisible(type: Int): ValueMetadata<DB, Boolean>
    fun othersUpdatesAreVisible(type: Int): ValueMetadata<DB, Boolean>
    fun othersDeletesAreVisible(type: Int): ValueMetadata<DB, Boolean>
    fun othersInsertsAreVisible(type: Int): ValueMetadata<DB, Boolean>
    fun updatesAreDetected(type: Int): ValueMetadata<DB, Boolean>
    fun deletesAreDetected(type: Int): ValueMetadata<DB, Boolean>
    fun getUDTs(catalog: String, schemaPattern: String, typeNamePattern: String, types: IntArray): ResultSetMetadata<DB, RawResultSetWrapper>
    fun getSuperTypes(catalog: String, schemaPattern: String, typeNamePattern: String): ResultSetMetadata<DB, RawResultSetWrapper>
    fun getSuperTables(catalog: String, schemaPattern: String, tableNamePattern: String): ResultSetMetadata<DB, RawResultSetWrapper>
    fun supportsResultSetHoldability(holdability: Int): ValueMetadata<DB, Boolean>
    fun getSchemas(catalog: String, schemaPattern: String): ResultSetMetadata<DB, RawResultSetWrapper>
    fun getClientInfoProperties(): ResultSetMetadata<DB, RawResultSetWrapper>
    fun getFunctions(catalog: String, schemaPattern: String, functionNamePattern: String): ResultSetMetadata<DB, RawResultSetWrapper>
    fun getFunctionColumns(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String,
        columnNamePattern: String
                          ): ResultSetMetadata<DB, RawResultSetWrapper>

    fun getPseudoColumns(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        columnNamePattern: String
                        ): ResultSetMetadata<DB, RawResultSetWrapper>
}

class RawResultSetWrapper(resultSet: ResultSet): AbstractMetadataResultSet(resultSet)

internal class MonadicMetadataImpl<DB: Database> : MonadicMetadata<DB> {

    override val maxColumnsInIndex: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxColumnsInIndex }
    override val supportsSubqueriesInQuantifieds: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSubqueriesInQuantifieds }
    override val supportsIntegrityEnhancementFacility: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsIntegrityEnhancementFacility }
    override val supportsGetGeneratedKeys: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsGetGeneratedKeys }
    override val supportsCoreSQLGrammar: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsCoreSQLGrammar }
    override val supportsDataDefinitionAndDataManipulationTransactions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsDataDefinitionAndDataManipulationTransactions }
    override val supportsCatalogsInTableDefinitions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsCatalogsInTableDefinitions }
    override val supportsOpenStatementsAcrossRollback: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsOpenStatementsAcrossRollback }
    override val supportsStoredFunctionsUsingCallSyntax: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsStoredFunctionsUsingCallSyntax }
    override val databaseProductName: ValueMetadata<DB, String>
        get() = ValueMetadata { it.databaseProductName }
    override val databaseProductVersion: ValueMetadata<DB, String>
        get() = ValueMetadata { it.databaseProductVersion }
    override val getJDBCMajorVersion: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.getJDBCMajorVersion }
    override val maxProcedureNameLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxProcedureNameLength }
    override val getCatalogTerm: ValueMetadata<DB, String>
        get() = ValueMetadata { it.getCatalogTerm }
    override val supportsCatalogsInDataManipulation: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsCatalogsInDataManipulation }
    override val getMaxUserNameLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.getMaxUserNameLength }
    override val timeDateFunctions: ValueMetadata<DB, List<String>>
        get() = ValueMetadata { it.timeDateFunctions }
    override val autoCommitFailureClosesAllResultSets: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.autoCommitFailureClosesAllResultSets }
    override val getMaxColumnsInSelect: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.getMaxColumnsInSelect }
    override val catalogs: ValueMetadata<DB, List<String>>
        get() = ValueMetadata { it.catalogs }
    override val storesLowerCaseQuotedIdentifiers: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.storesLowerCaseQuotedIdentifiers }
    override val getMaxColumnsInOrderBy: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.getMaxColumnsInOrderBy }
    override val getDriverMinorVersion: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.getDriverMinorVersion }
    override val allProceduresAreCallable: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.allProceduresAreCallable }
    override val allTablesAreSelectable: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.allTablesAreSelectable }
    override val URL: ValueMetadata<DB, String>
        get() = ValueMetadata { it.URL }
    override val userName: ValueMetadata<DB, String>
        get() = ValueMetadata { it.userName }
    override val isReadOnly: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.isReadOnly }
    override val nullsAreSortedHigh: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.nullsAreSortedHigh }
    override val nullsAreSortedLow: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.nullsAreSortedLow }
    override val nullsAreSortedAtStart: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.nullsAreSortedAtStart }
    override val nullsAreSortedAtEnd: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.nullsAreSortedAtEnd }
    override val driverName: ValueMetadata<DB, String>
        get() = ValueMetadata { it.driverName }
    override val driverVersion: ValueMetadata<DB, String>
        get() = ValueMetadata { it.driverVersion }
    override val driverMajorVersion: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.driverMajorVersion }
    override val driverMinorVersion: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.driverMinorVersion }
    override val usesLocalFiles: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.usesLocalFiles }
    override val usesLocalFilePerTable: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.usesLocalFilePerTable }
    override val supportsMixedCaseIdentifiers: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsMixedCaseIdentifiers }
    override val storesUpperCaseIdentifiers: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.storesUpperCaseIdentifiers }
    override val storesLowerCaseIdentifiers: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.storesLowerCaseIdentifiers }
    override val storesMixedCaseIdentifiers: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.storesMixedCaseIdentifiers }
    override val supportsMixedCaseQuotedIdentifiers: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsMixedCaseQuotedIdentifiers }
    override val storesUpperCaseQuotedIdentifiers: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.storesUpperCaseQuotedIdentifiers }
    override val storesMixedCaseQuotedIdentifiers: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.storesMixedCaseQuotedIdentifiers }
    override val identifierQuoteString: ValueMetadata<DB, String>
        get() = ValueMetadata { it.identifierQuoteString }
    override val SQLKeywords: ValueMetadata<DB, List<String>>
        get() = ValueMetadata { it.SQLKeywords }
    override val numericFunctions: ValueMetadata<DB, List<String>>
        get() = ValueMetadata { it.numericFunctions }
    override val stringFunctions: ValueMetadata<DB, List<String>>
        get() = ValueMetadata { it.stringFunctions }
    override val systemFunctions: ValueMetadata<DB, List<String>>
        get() = ValueMetadata { it.systemFunctions }
    override val searchStringEscape: ValueMetadata<DB, String>
        get() = ValueMetadata { it.searchStringEscape }
    override val extraNameCharacters: ValueMetadata<DB, String>
        get() = ValueMetadata { it.extraNameCharacters }
    override val supportsAlterTableWithAddColumn: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsAlterTableWithAddColumn }
    override val supportsAlterTableWithDropColumn: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsAlterTableWithDropColumn }
    override val supportsColumnAliasing: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsColumnAliasing }
    override val nullPlusNonNullIsNull: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.nullPlusNonNullIsNull }
    override val supportsConvert: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsConvert }
    override val supportsTableCorrelationNames: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsTableCorrelationNames }
    override val supportsDifferentTableCorrelationNames: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsDifferentTableCorrelationNames }
    override val supportsExpressionsInOrderBy: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsExpressionsInOrderBy }
    override val supportsOrderByUnrelated: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsOrderByUnrelated }
    override val supportsGroupBy: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsGroupBy }
    override val supportsGroupByUnrelated: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsGroupByUnrelated }
    override val supportsGroupByBeyondSelect: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsGroupByBeyondSelect }
    override val supportsLikeEscapeClause: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsLikeEscapeClause }
    override val supportsMultipleResultSets: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsMultipleResultSets }
    override val supportsMultipleTransactions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsMultipleTransactions }
    override val supportsNonNullableColumns: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsNonNullableColumns }
    override val supportsMinimumSQLGrammar: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsMinimumSQLGrammar }
    override val supportsExtendedSQLGrammar: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsExtendedSQLGrammar }
    override val supportsANSI92EntryLevelSQL: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsANSI92EntryLevelSQL }
    override val supportsANSI92IntermediateSQL: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsANSI92IntermediateSQL }
    override val supportsANSI92FullSQL: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsANSI92FullSQL }
    override val supportsOuterJoins: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsOuterJoins }
    override val supportsFullOuterJoins: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsFullOuterJoins }
    override val supportsLimitedOuterJoins: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsLimitedOuterJoins }
    override val schemaTerm: ValueMetadata<DB, String>
        get() = ValueMetadata { it.schemaTerm }
    override val procedureTerm: ValueMetadata<DB, String>
        get() = ValueMetadata { it.procedureTerm }
    override val catalogTerm: ValueMetadata<DB, String>
        get() = ValueMetadata { it.catalogTerm }
    override val isCatalogAtStart: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.isCatalogAtStart }
    override val catalogSeparator: ValueMetadata<DB, String>
        get() = ValueMetadata { it.catalogSeparator }
    override val supportsSchemasInDataManipulation: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSchemasInDataManipulation }
    override val supportsSchemasInProcedureCalls: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSchemasInProcedureCalls }
    override val supportsSchemasInTableDefinitions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSchemasInTableDefinitions }
    override val supportsSchemasInIndexDefinitions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSchemasInIndexDefinitions }
    override val supportsSchemasInPrivilegeDefinitions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSchemasInPrivilegeDefinitions }
    override val supportsCatalogsInProcedureCalls: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsCatalogsInProcedureCalls }
    override val supportsCatalogsInIndexDefinitions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsCatalogsInIndexDefinitions }
    override val supportsCatalogsInPrivilegeDefinitions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsCatalogsInPrivilegeDefinitions }
    override val supportsPositionedDelete: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsPositionedDelete }
    override val supportsPositionedUpdate: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsPositionedUpdate }
    override val supportsSelectForUpdate: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSelectForUpdate }
    override val supportsStoredProcedures: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsStoredProcedures }
    override val supportsSubqueriesInComparisons: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSubqueriesInComparisons }
    override val supportsSubqueriesInExists: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSubqueriesInExists }
    override val supportsSubqueriesInIns: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSubqueriesInIns }
    override val supportsCorrelatedSubqueries: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsCorrelatedSubqueries }
    override val supportsUnion: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsUnion }
    override val supportsUnionAll: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsUnionAll }
    override val supportsOpenCursorsAcrossCommit: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsOpenCursorsAcrossCommit }
    override val supportsOpenCursorsAcrossRollback: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsOpenCursorsAcrossRollback }
    override val supportsOpenStatementsAcrossCommit: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsOpenStatementsAcrossCommit }
    override val maxBinaryLiteralLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxBinaryLiteralLength }
    override val maxCharLiteralLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxCharLiteralLength }
    override val maxColumnNameLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxColumnNameLength }
    override val maxColumnsInGroupBy: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxColumnsInGroupBy }
    override val maxColumnsInOrderBy: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxColumnsInOrderBy }
    override val maxColumnsInSelect: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxColumnsInSelect }
    override val maxColumnsInTable: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxColumnsInTable }
    override val maxConnections: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxConnections }
    override val maxCursorNameLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxCursorNameLength }
    override val maxIndexLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxIndexLength }
    override val maxSchemaNameLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxSchemaNameLength }
    override val maxCatalogNameLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxCatalogNameLength }
    override val maxRowSize: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxRowSize }
    override val doesMaxRowSizeIncludeBlobs: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.doesMaxRowSizeIncludeBlobs }
    override val maxStatementLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxStatementLength }
    override val maxStatements: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxStatements }
    override val maxTableNameLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxTableNameLength }
    override val maxTablesInSelect: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxTablesInSelect }
    override val maxUserNameLength: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.maxUserNameLength }
    override val defaultTransactionIsolation: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.defaultTransactionIsolation }
    override val supportsTransactions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsTransactions }
    override val supportsDataManipulationTransactionsOnly: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsDataManipulationTransactionsOnly }
    override val dataDefinitionCausesTransactionCommit: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.dataDefinitionCausesTransactionCommit }
    override val dataDefinitionIgnoredInTransactions: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.dataDefinitionIgnoredInTransactions }
    override val schemas: ResultSetMetadata<DB, SchemaResults>
        get() = ResultSetMetadata { it.schemas }
    override val supportsBatchUpdates: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsBatchUpdates }
    override val supportsSavepoints: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsSavepoints }
    override val supportsNamedParameters: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsNamedParameters }
    override val supportsMultipleOpenResults: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsMultipleOpenResults }
    override val resultSetHoldability: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.resultSetHoldability }
    override val databaseMajorVersion: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.databaseMajorVersion }
    override val databaseMinorVersion: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.databaseMinorVersion }
    override val JDBCMajorVersion: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.JDBCMajorVersion }
    override val JDBCMinorVersion: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.JDBCMinorVersion }
    override val SQLStateType: ValueMetadata<DB, Int>
        get() = ValueMetadata { it.SQLStateType }
    override val locatorsUpdateCopy: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.locatorsUpdateCopy }
    override val supportsStatementPooling: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsStatementPooling }
    override val rowIdLifetime: ValueMetadata<DB, RowIdLifetime>
        get() = ValueMetadata { it.rowIdLifetime }
    override val generatedKeyAlwaysReturned: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.generatedKeyAlwaysReturned }
    override val maxLogicalLobSize: ValueMetadata<DB, Long>
        get() = ValueMetadata { it.maxLogicalLobSize }
    override val supportsRefCursors: ValueMetadata<DB, Boolean>
        get() = ValueMetadata { it.supportsRefCursors }

    override fun insertsAreDetected(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.insertsAreDetected(type) }
    }

    override fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?
                              ): ResultSetMetadata<DB, AttributeResults> {
        return ResultSetMetadata { it.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern) }
    }

    override fun supportsConvert(fromType: Int, toType: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.supportsConvert(fromType, toType) }
    }

    override fun supportsTransactionIsolationLevel(level: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.supportsTransactionIsolationLevel(level) }
    }

    override fun getProcedures(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String
                              ): ResultSetMetadata<DB, ProcedureResults> {
        return ResultSetMetadata { it.getProcedures(catalog, schemaPattern, procedureNamePattern) }
    }

    override fun getProcedureColumns(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
        columnNamePattern: String
                                    ): ResultSetMetadata<DB, ProcedureColumnResults> {
        return ResultSetMetadata { it.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern) }
    }

    override fun getTables(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        types: Array<String>?
                          ): ResultSetMetadata<DB, TableResults> {
        return ResultSetMetadata { it.getTables(catalog, schemaPattern, tableNamePattern, types) }
    }

    override val tableTypes: ValueMetadata<DB, List<String>>
        get() = ValueMetadata { it.tableTypes }

    override fun getColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
                           ): ResultSetMetadata<DB, ColumnsResults> {
        return ResultSetMetadata { it.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern) }
    }

    override fun getColumnPrivileges(
        catalog: String,
        schema: String,
        table: String,
        columnNamePattern: String
                                    ): ResultSetMetadata<DB, ColumnPrivilegesResult> {
        return ResultSetMetadata { it.getColumnPrivileges(catalog, schema, table, columnNamePattern) }
    }

    override fun getTablePrivileges(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
                                   ): ResultSetMetadata<DB, TablePrivilegesResult> {
        return ResultSetMetadata { it.getTablePrivileges(catalog, schemaPattern, tableNamePattern) }
    }

    override fun getBestRowIdentifier(
        catalog: String,
        schema: String,
        table: String,
        scope: Int,
        nullable: Boolean
                                     ): ResultSetMetadata<DB, BestRowIdentifierResult> {
        return ResultSetMetadata { it.getBestRowIdentifier(catalog, schema, table, scope, nullable) }
    }

    override fun getVersionColumns(
        catalog: String,
        schema: String,
        table: String
                                  ): ResultSetMetadata<DB, VersionColumnsResult> {
        return ResultSetMetadata { it.getVersionColumns(catalog, schema, table) }
    }

    override fun getPrimaryKeys(
        catalog: String,
        schema: String,
        table: String
                               ): ResultSetMetadata<DB, PrimaryKeyResults> {
        return ResultSetMetadata { it.getPrimaryKeys(catalog, schema, table) }
    }

    override fun getImportedKeys(catalog: String, schema: String, table: String): ResultSetMetadata<DB, KeysResult> {
        return ResultSetMetadata { it.getImportedKeys(catalog, schema, table) }
    }

    override fun getExportedKeys(catalog: String, schema: String, table: String): ResultSetMetadata<DB, KeysResult> {
        return ResultSetMetadata { it.getExportedKeys(catalog, schema, table) }
    }

    override fun getCrossReference(
        parentCatalog: String,
        parentSchema: String,
        parentTable: String,
        foreignCatalog: String,
        foreignSchema: String,
        foreignTable: String
                                  ): ResultSetMetadata<DB, KeysResult> {
        return ResultSetMetadata { it.getCrossReference(parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema, foreignTable) }
    }

    override fun getTypeInfo(): ResultSetMetadata<DB, TypeInfo> {
        return ResultSetMetadata { it.getTypeInfo() }
    }

    override fun getUnsafeIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean
                                   ): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getUnsafeIndexInfo(catalog, schema, table, unique, approximate)) }
    }

    override fun supportsResultSetConcurrency(type: Int, concurrency: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.supportsResultSetConcurrency(type, concurrency) }
    }

    override fun ownUpdatesAreVisible(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.ownUpdatesAreVisible(type) }
    }

    override fun ownDeletesAreVisible(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.ownDeletesAreVisible(type) }
    }

    override fun ownInsertsAreVisible(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.othersInsertsAreVisible(type) }
    }

    override fun othersUpdatesAreVisible(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.othersUpdatesAreVisible(type) }
    }

    override fun othersDeletesAreVisible(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.othersDeletesAreVisible(type) }
    }

    override fun othersInsertsAreVisible(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.othersInsertsAreVisible(type) }
    }

    override fun updatesAreDetected(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.updatesAreDetected(type) }
    }

    override fun deletesAreDetected(type: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.deletesAreDetected(type) }
    }

    override fun getUDTs(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String,
        types: IntArray
                        ): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getUDTs(catalog, schemaPattern, typeNamePattern, types)) }
    }

    override fun getSuperTypes(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String
                              ): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getSuperTypes(catalog, schemaPattern, typeNamePattern)) }
    }

    override fun getSuperTables(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
                               ): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getSuperTables(catalog, schemaPattern, tableNamePattern)) }
    }

    override fun supportsResultSetHoldability(holdability: Int): ValueMetadata<DB, Boolean> {
        return ValueMetadata { it.supportsResultSetHoldability(holdability) }
    }

    override fun getSchemas(catalog: String, schemaPattern: String): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getSchemas(catalog, schemaPattern)) }
    }

    override fun getClientInfoProperties(): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getClientInfoProperties()) }
    }

    override fun getFunctions(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String
                             ): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getFunctions(catalog, schemaPattern, functionNamePattern)) }
    }

    override fun getFunctionColumns(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String,
        columnNamePattern: String
                                   ): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern)) }
    }

    override fun getPseudoColumns(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        columnNamePattern: String
                                 ): ResultSetMetadata<DB, RawResultSetWrapper> {
        return ResultSetMetadata { RawResultSetWrapper(it.getPseudoColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern)) }
    }
}