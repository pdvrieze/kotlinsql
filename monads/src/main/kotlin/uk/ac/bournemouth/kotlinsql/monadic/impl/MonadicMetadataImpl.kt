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

package uk.ac.bournemouth.kotlinsql.monadic.impl

import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.metadata.impl.RawResultSetWrapper
import uk.ac.bournemouth.kotlinsql.metadata.*
import uk.ac.bournemouth.kotlinsql.monadic.MonadicMetadata
import uk.ac.bournemouth.kotlinsql.monadic.ResultSetMetadataAction
import uk.ac.bournemouth.kotlinsql.monadic.ValueMetadataAction
import java.sql.RowIdLifetime

internal class MonadicMetadataImpl<DB: Database> : MonadicMetadata<DB> {

    override val maxColumnsInIndex: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxColumnsInIndex }
    override val supportsSubqueriesInQuantifieds: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSubqueriesInQuantifieds }
    override val supportsIntegrityEnhancementFacility: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsIntegrityEnhancementFacility }
    override val supportsGetGeneratedKeys: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsGetGeneratedKeys }
    override val supportsCoreSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsCoreSQLGrammar }
    override val supportsDataDefinitionAndDataManipulationTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsDataDefinitionAndDataManipulationTransactions }
    override val supportsCatalogsInTableDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsCatalogsInTableDefinitions }
    override val supportsOpenStatementsAcrossRollback: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsOpenStatementsAcrossRollback }
    override val supportsStoredFunctionsUsingCallSyntax: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsStoredFunctionsUsingCallSyntax }
    override val databaseProductName: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.databaseProductName }
    override val databaseProductVersion: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.databaseProductVersion }
    override val getJDBCMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.getJDBCMajorVersion }
    override val maxProcedureNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxProcedureNameLength }
    override val getCatalogTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.getCatalogTerm }
    override val supportsCatalogsInDataManipulation: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsCatalogsInDataManipulation }
    override val getMaxUserNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.getMaxUserNameLength }
    override val timeDateFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it: SafeDatabaseMetaData -> it.timeDateFunctions }
    override val autoCommitFailureClosesAllResultSets: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.autoCommitFailureClosesAllResultSets }
    override val getMaxColumnsInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.getMaxColumnsInSelect }
    override val catalogs: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it: SafeDatabaseMetaData -> it.catalogs }
    override val storesLowerCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.storesLowerCaseQuotedIdentifiers }
    override val getMaxColumnsInOrderBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.getMaxColumnsInOrderBy }
    override val getDriverMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.getDriverMinorVersion }
    override val allProceduresAreCallable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.allProceduresAreCallable }
    override val allTablesAreSelectable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.allTablesAreSelectable }
    override val URL: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.URL }
    override val userName: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.userName }
    override val isReadOnly: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.isReadOnly }
    override val nullsAreSortedHigh: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.nullsAreSortedHigh }
    override val nullsAreSortedLow: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.nullsAreSortedLow }
    override val nullsAreSortedAtStart: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.nullsAreSortedAtStart }
    override val nullsAreSortedAtEnd: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.nullsAreSortedAtEnd }
    override val driverName: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.driverName }
    override val driverVersion: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.driverVersion }
    override val driverMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.driverMajorVersion }
    override val driverMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.driverMinorVersion }
    override val usesLocalFiles: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.usesLocalFiles }
    override val usesLocalFilePerTable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.usesLocalFilePerTable }
    override val supportsMixedCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsMixedCaseIdentifiers }
    override val storesUpperCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.storesUpperCaseIdentifiers }
    override val storesLowerCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.storesLowerCaseIdentifiers }
    override val storesMixedCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.storesMixedCaseIdentifiers }
    override val supportsMixedCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsMixedCaseQuotedIdentifiers }
    override val storesUpperCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.storesUpperCaseQuotedIdentifiers }
    override val storesMixedCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.storesMixedCaseQuotedIdentifiers }
    override val identifierQuoteString: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.identifierQuoteString }
    override val SQLKeywords: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it: SafeDatabaseMetaData -> it.SQLKeywords }
    override val numericFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it: SafeDatabaseMetaData -> it.numericFunctions }
    override val stringFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it: SafeDatabaseMetaData -> it.stringFunctions }
    override val systemFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it: SafeDatabaseMetaData -> it.systemFunctions }
    override val searchStringEscape: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.searchStringEscape }
    override val extraNameCharacters: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.extraNameCharacters }
    override val supportsAlterTableWithAddColumn: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsAlterTableWithAddColumn }
    override val supportsAlterTableWithDropColumn: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsAlterTableWithDropColumn }
    override val supportsColumnAliasing: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsColumnAliasing }
    override val nullPlusNonNullIsNull: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.nullPlusNonNullIsNull }
    override val supportsConvert: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsConvert }
    override val supportsTableCorrelationNames: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsTableCorrelationNames }
    override val supportsDifferentTableCorrelationNames: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsDifferentTableCorrelationNames }
    override val supportsExpressionsInOrderBy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsExpressionsInOrderBy }
    override val supportsOrderByUnrelated: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsOrderByUnrelated }
    override val supportsGroupBy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsGroupBy }
    override val supportsGroupByUnrelated: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsGroupByUnrelated }
    override val supportsGroupByBeyondSelect: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsGroupByBeyondSelect }
    override val supportsLikeEscapeClause: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsLikeEscapeClause }
    override val supportsMultipleResultSets: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsMultipleResultSets }
    override val supportsMultipleTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsMultipleTransactions }
    override val supportsNonNullableColumns: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsNonNullableColumns }
    override val supportsMinimumSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsMinimumSQLGrammar }
    override val supportsExtendedSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsExtendedSQLGrammar }
    override val supportsANSI92EntryLevelSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsANSI92EntryLevelSQL }
    override val supportsANSI92IntermediateSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsANSI92IntermediateSQL }
    override val supportsANSI92FullSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsANSI92FullSQL }
    override val supportsOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsOuterJoins }
    override val supportsFullOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsFullOuterJoins }
    override val supportsLimitedOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsLimitedOuterJoins }
    override val schemaTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.schemaTerm }
    override val procedureTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.procedureTerm }
    override val catalogTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.catalogTerm }
    override val isCatalogAtStart: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.isCatalogAtStart }
    override val catalogSeparator: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it: SafeDatabaseMetaData -> it.catalogSeparator }
    override val supportsSchemasInDataManipulation: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSchemasInDataManipulation }
    override val supportsSchemasInProcedureCalls: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSchemasInProcedureCalls }
    override val supportsSchemasInTableDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSchemasInTableDefinitions }
    override val supportsSchemasInIndexDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSchemasInIndexDefinitions }
    override val supportsSchemasInPrivilegeDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSchemasInPrivilegeDefinitions }
    override val supportsCatalogsInProcedureCalls: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsCatalogsInProcedureCalls }
    override val supportsCatalogsInIndexDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsCatalogsInIndexDefinitions }
    override val supportsCatalogsInPrivilegeDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsCatalogsInPrivilegeDefinitions }
    override val supportsPositionedDelete: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsPositionedDelete }
    override val supportsPositionedUpdate: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsPositionedUpdate }
    override val supportsSelectForUpdate: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSelectForUpdate }
    override val supportsStoredProcedures: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsStoredProcedures }
    override val supportsSubqueriesInComparisons: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSubqueriesInComparisons }
    override val supportsSubqueriesInExists: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSubqueriesInExists }
    override val supportsSubqueriesInIns: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSubqueriesInIns }
    override val supportsCorrelatedSubqueries: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsCorrelatedSubqueries }
    override val supportsUnion: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsUnion }
    override val supportsUnionAll: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsUnionAll }
    override val supportsOpenCursorsAcrossCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsOpenCursorsAcrossCommit }
    override val supportsOpenCursorsAcrossRollback: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsOpenCursorsAcrossRollback }
    override val supportsOpenStatementsAcrossCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsOpenStatementsAcrossCommit }
    override val maxBinaryLiteralLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxBinaryLiteralLength }
    override val maxCharLiteralLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxCharLiteralLength }
    override val maxColumnNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxColumnNameLength }
    override val maxColumnsInGroupBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxColumnsInGroupBy }
    override val maxColumnsInOrderBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxColumnsInOrderBy }
    override val maxColumnsInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxColumnsInSelect }
    override val maxColumnsInTable: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxColumnsInTable }
    override val maxConnections: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxConnections }
    override val maxCursorNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxCursorNameLength }
    override val maxIndexLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxIndexLength }
    override val maxSchemaNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxSchemaNameLength }
    override val maxCatalogNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxCatalogNameLength }
    override val maxRowSize: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxRowSize }
    override val doesMaxRowSizeIncludeBlobs: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.doesMaxRowSizeIncludeBlobs }
    override val maxStatementLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxStatementLength }
    override val maxStatements: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxStatements }
    override val maxTableNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxTableNameLength }
    override val maxTablesInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxTablesInSelect }
    override val maxUserNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.maxUserNameLength }
    override val defaultTransactionIsolation: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.defaultTransactionIsolation }
    override val supportsTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsTransactions }
    override val supportsDataManipulationTransactionsOnly: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsDataManipulationTransactionsOnly }
    override val dataDefinitionCausesTransactionCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.dataDefinitionCausesTransactionCommit }
    override val dataDefinitionIgnoredInTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.dataDefinitionIgnoredInTransactions }
    override val schemas: ResultSetMetadataAction<DB, SchemaResults>
        get() = ResultSetMetadataAction<DB, SchemaResults> { it: SafeDatabaseMetaData -> it.schemas }
    override val supportsBatchUpdates: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsBatchUpdates }
    override val supportsSavepoints: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsSavepoints }
    override val supportsNamedParameters: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsNamedParameters }
    override val supportsMultipleOpenResults: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsMultipleOpenResults }
    override val resultSetHoldability: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.resultSetHoldability }
    override val databaseMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.databaseMajorVersion }
    override val databaseMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.databaseMinorVersion }
    override val JDBCMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.JDBCMajorVersion }
    override val JDBCMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.JDBCMinorVersion }
    override val SQLStateType: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it: SafeDatabaseMetaData -> it.SQLStateType }
    override val locatorsUpdateCopy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.locatorsUpdateCopy }
    override val supportsStatementPooling: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsStatementPooling }
    override val rowIdLifetime: ValueMetadataAction<DB, RowIdLifetime>
        get() = ValueMetadataAction<DB, RowIdLifetime> { it: SafeDatabaseMetaData -> it.rowIdLifetime }
    override val generatedKeyAlwaysReturned: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.generatedKeyAlwaysReturned }
    override val maxLogicalLobSize: ValueMetadataAction<DB, Long>
        get() = ValueMetadataAction<DB, Long> { it: SafeDatabaseMetaData -> it.maxLogicalLobSize }
    override val supportsRefCursors: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData -> it.supportsRefCursors }

    override fun insertsAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.insertsAreDetected(type)
        }
    }

    override fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?
    ): ResultSetMetadataAction<DB, AttributeResults> {
        return ResultSetMetadataAction<DB, AttributeResults> { it: SafeDatabaseMetaData ->
            it.getAttributes(
                catalog,
                schemaPattern,
                typeNamePattern,
                attributeNamePattern
            )
        }
    }

    override fun supportsConvert(
        fromType: Int,
        toType: Int
    ): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.supportsConvert(fromType,
                               toType)
        }
    }

    override fun supportsTransactionIsolationLevel(level: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.supportsTransactionIsolationLevel(level)
        }
    }

    override fun getProcedures(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String
    ): ResultSetMetadataAction<DB, ProcedureResults> {
        return ResultSetMetadataAction<DB, ProcedureResults> { it: SafeDatabaseMetaData ->
            it.getProcedures(catalog,
                             schemaPattern,
                             procedureNamePattern)
        }
    }

    override fun getProcedureColumns(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
        columnNamePattern: String
    ): ResultSetMetadataAction<DB, ProcedureColumnResults> {
        return ResultSetMetadataAction<DB, ProcedureColumnResults> { it: SafeDatabaseMetaData ->
            it.getProcedureColumns(
                catalog,
                schemaPattern,
                procedureNamePattern,
                columnNamePattern
            )
        }
    }

    override fun getTables(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        types: Array<String>?
    ): ResultSetMetadataAction<DB, TableMetadataResults> {
        return ResultSetMetadataAction<DB, TableMetadataResults> { it: SafeDatabaseMetaData ->
            it.getTables(catalog,
                         schemaPattern,
                         tableNamePattern,
                         types)
        }
    }

    override val tableTypes: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it: SafeDatabaseMetaData -> it.tableTypes }

    override fun getColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
    ): ResultSetMetadataAction<DB, ColumnsResults> {
        return ResultSetMetadataAction<DB, ColumnsResults> { it: SafeDatabaseMetaData ->
            it.getColumns(
                catalog,
                schemaPattern,
                tableNamePattern,
                columnNamePattern
            )
        }
    }

    override fun getColumnPrivileges(
        catalog: String,
        schema: String,
        table: String,
        columnNamePattern: String
    ): ResultSetMetadataAction<DB, ColumnPrivilegesResult> {
        return ResultSetMetadataAction<DB, ColumnPrivilegesResult> { it: SafeDatabaseMetaData ->
            it.getColumnPrivileges(catalog,
                                   schema,
                                   table,
                                   columnNamePattern)
        }
    }

    override fun getTablePrivileges(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
    ): ResultSetMetadataAction<DB, TablePrivilegesResult> {
        return ResultSetMetadataAction<DB, TablePrivilegesResult> { it: SafeDatabaseMetaData ->
            it.getTablePrivileges(catalog,
                                  schemaPattern,
                                  tableNamePattern)
        }
    }

    override fun getBestRowIdentifier(
        catalog: String,
        schema: String,
        table: String,
        scope: Int,
        nullable: Boolean
    ): ResultSetMetadataAction<DB, BestRowIdentifierResult> {
        return ResultSetMetadataAction<DB, BestRowIdentifierResult> { it: SafeDatabaseMetaData ->
            it.getBestRowIdentifier(catalog,
                                    schema,
                                    table,
                                    scope,
                                    nullable)
        }
    }

    override fun getVersionColumns(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataAction<DB, VersionColumnsResult> {
        return ResultSetMetadataAction<DB, VersionColumnsResult> { it: SafeDatabaseMetaData ->
            it.getVersionColumns(catalog,
                                 schema,
                                 table)
        }
    }

    override fun getPrimaryKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataAction<DB, PrimaryKeyResults> {
        return ResultSetMetadataAction<DB, PrimaryKeyResults> { it: SafeDatabaseMetaData ->
            it.getPrimaryKeys(catalog,
                              schema,
                              table)
        }
    }

    override fun getImportedKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataAction<DB, KeysResult> {
        return ResultSetMetadataAction<DB, KeysResult> { it: SafeDatabaseMetaData ->
            it.getImportedKeys(catalog,
                               schema,
                               table)
        }
    }

    override fun getExportedKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataAction<DB, KeysResult> {
        return ResultSetMetadataAction<DB, KeysResult> { it: SafeDatabaseMetaData ->
            it.getExportedKeys(catalog,
                               schema,
                               table)
        }
    }

    override fun getCrossReference(
        parentCatalog: String,
        parentSchema: String,
        parentTable: String,
        foreignCatalog: String,
        foreignSchema: String,
        foreignTable: String
    ): ResultSetMetadataAction<DB, KeysResult> {
        return ResultSetMetadataAction<DB, KeysResult> { it: SafeDatabaseMetaData ->
            it.getCrossReference(
                parentCatalog,
                parentSchema,
                parentTable,
                foreignCatalog,
                foreignSchema,
                foreignTable
            )
        }
    }

    override fun getTypeInfo(): ResultSetMetadataAction<DB, TypeInfoResults> {
        return ResultSetMetadataAction<DB, TypeInfoResults> { it: SafeDatabaseMetaData -> it.getTypeInfo() }
    }

    override fun getUnsafeIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean
    ): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> { it: SafeDatabaseMetaData ->
            RawResultSetWrapper(
                it.getUnsafeIndexInfo(
                    catalog,
                    schema,
                    table,
                    unique,
                    approximate
                )
            )
        }
    }

    override fun supportsResultSetConcurrency(
        type: Int,
        concurrency: Int
    ): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.supportsResultSetConcurrency(type,
                                            concurrency)
        }
    }

    override fun ownUpdatesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.ownUpdatesAreVisible(type)
        }
    }

    override fun ownDeletesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.ownDeletesAreVisible(type)
        }
    }

    override fun ownInsertsAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.othersInsertsAreVisible(type)
        }
    }

    override fun othersUpdatesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.othersUpdatesAreVisible(type)
        }
    }

    override fun othersDeletesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.othersDeletesAreVisible(type)
        }
    }

    override fun othersInsertsAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.othersInsertsAreVisible(type)
        }
    }

    override fun updatesAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.updatesAreDetected(type)
        }
    }

    override fun deletesAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.deletesAreDetected(type)
        }
    }

    override fun getUDTs(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String,
        types: IntArray
    ): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> { it: SafeDatabaseMetaData ->
            RawResultSetWrapper(
                it.getUDTs(
                    catalog,
                    schemaPattern,
                    typeNamePattern,
                    types
                )
            )
        }
    }

    override fun getSuperTypes(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String
    ): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> { it: SafeDatabaseMetaData ->
            RawResultSetWrapper(
                it.getSuperTypes(
                    catalog,
                    schemaPattern,
                    typeNamePattern
                )
            )
        }
    }

    override fun getSuperTables(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
    ): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> { it: SafeDatabaseMetaData ->
            RawResultSetWrapper(
                it.getSuperTables(
                    catalog,
                    schemaPattern,
                    tableNamePattern
                )
            )
        }
    }

    override fun supportsResultSetHoldability(holdability: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> { it: SafeDatabaseMetaData ->
            it.supportsResultSetHoldability(holdability)
        }
    }

    override fun getSchemas(
        catalog: String,
        schemaPattern: String
    ): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> { it: SafeDatabaseMetaData ->
            RawResultSetWrapper(it.getSchemas(catalog, schemaPattern))
        }
    }

    override fun getClientInfoProperties(): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> { it: SafeDatabaseMetaData ->
            RawResultSetWrapper(it.getClientInfoProperties())
        }
    }

    override fun getFunctions(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String
    ): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> { it: SafeDatabaseMetaData ->
            RawResultSetWrapper(
                it.getFunctions(
                    catalog,
                    schemaPattern,
                    functionNamePattern
                )
            )
        }
    }

    override fun getFunctionColumns(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String,
        columnNamePattern: String
    ): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> { it: SafeDatabaseMetaData ->
            RawResultSetWrapper(
                it.getFunctionColumns(
                    catalog,
                    schemaPattern,
                    functionNamePattern,
                    columnNamePattern
                )
            )
        }
    }

    override fun getPseudoColumns(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        columnNamePattern: String
    ): ResultSetMetadataAction<DB, RawResultSetWrapper> {
        return ResultSetMetadataAction<DB, RawResultSetWrapper> {
 it: SafeDatabaseMetaData ->
            RawResultSetWrapper(
                it.getPseudoColumns(
                    catalog,
                    schemaPattern,
                    tableNamePattern,
                    columnNamePattern
                )
            )
        }
    }
}