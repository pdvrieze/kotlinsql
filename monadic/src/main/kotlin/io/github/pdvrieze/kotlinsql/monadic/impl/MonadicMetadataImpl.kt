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

package io.github.pdvrieze.kotlinsql.monadic.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.metadata.*
import io.github.pdvrieze.kotlinsql.metadata.impl.RawResultSetWrapper
import io.github.pdvrieze.kotlinsql.monadic.MonadicMetadata
import io.github.pdvrieze.kotlinsql.monadic.ResultSetMetadataAction
import io.github.pdvrieze.kotlinsql.monadic.ResultSetMetadataActionImpl
import io.github.pdvrieze.kotlinsql.monadic.ValueMetadataAction
import java.sql.RowIdLifetime

internal class MonadicMetadataImpl<DB: Database> : MonadicMetadata<DB> {

    override val maxColumnsInIndex: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxColumnsInIndex }
    override val supportsSubqueriesInQuantifieds: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSubqueriesInQuantifieds }
    override val supportsIntegrityEnhancementFacility: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsIntegrityEnhancementFacility }
    override val supportsGetGeneratedKeys: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsGetGeneratedKeys }
    override val supportsCoreSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsCoreSQLGrammar }
    override val supportsDataDefinitionAndDataManipulationTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsDataDefinitionAndDataManipulationTransactions }
    override val supportsCatalogsInTableDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsCatalogsInTableDefinitions }
    override val supportsOpenStatementsAcrossRollback: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsOpenStatementsAcrossRollback }
    override val supportsStoredFunctionsUsingCallSyntax: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsStoredFunctionsUsingCallSyntax }
    override val databaseProductName: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.databaseProductName }
    override val databaseProductVersion: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.databaseProductVersion }
    override val getJDBCMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.getJDBCMajorVersion }
    override val maxProcedureNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxProcedureNameLength }
    override val getCatalogTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.getCatalogTerm }
    override val supportsCatalogsInDataManipulation: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsCatalogsInDataManipulation }
    override val getMaxUserNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.getMaxUserNameLength }
    override val timeDateFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it.timeDateFunctions }
    override val autoCommitFailureClosesAllResultSets: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.autoCommitFailureClosesAllResultSets }
    override val getMaxColumnsInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.getMaxColumnsInSelect }
    override val catalogs: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it.catalogs }
    override val storesLowerCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.storesLowerCaseQuotedIdentifiers }
    override val getMaxColumnsInOrderBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.getMaxColumnsInOrderBy }
    override val getDriverMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.getDriverMinorVersion }
    override val allProceduresAreCallable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.allProceduresAreCallable }
    override val allTablesAreSelectable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.allTablesAreSelectable }
    override val URL: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.URL }
    override val userName: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.userName }
    override val isReadOnly: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.isReadOnly }
    override val nullsAreSortedHigh: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.nullsAreSortedHigh }
    override val nullsAreSortedLow: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.nullsAreSortedLow }
    override val nullsAreSortedAtStart: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.nullsAreSortedAtStart }
    override val nullsAreSortedAtEnd: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.nullsAreSortedAtEnd }
    override val driverName: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.driverName }
    override val driverVersion: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.driverVersion }
    override val driverMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.driverMajorVersion }
    override val driverMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.driverMinorVersion }
    override val usesLocalFiles: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.usesLocalFiles }
    override val usesLocalFilePerTable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.usesLocalFilePerTable }
    override val supportsMixedCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsMixedCaseIdentifiers }
    override val storesUpperCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.storesUpperCaseIdentifiers }
    override val storesLowerCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.storesLowerCaseIdentifiers }
    override val storesMixedCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.storesMixedCaseIdentifiers }
    override val supportsMixedCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsMixedCaseQuotedIdentifiers }
    override val storesUpperCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.storesUpperCaseQuotedIdentifiers }
    override val storesMixedCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.storesMixedCaseQuotedIdentifiers }
    override val identifierQuoteString: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.identifierQuoteString }
    override val SQLKeywords: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it.SQLKeywords }
    override val numericFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it.numericFunctions }
    override val stringFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it.stringFunctions }
    override val systemFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it.systemFunctions }
    override val searchStringEscape: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.searchStringEscape }
    override val extraNameCharacters: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.extraNameCharacters }
    override val supportsAlterTableWithAddColumn: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsAlterTableWithAddColumn }
    override val supportsAlterTableWithDropColumn: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsAlterTableWithDropColumn }
    override val supportsColumnAliasing: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsColumnAliasing }
    override val nullPlusNonNullIsNull: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.nullPlusNonNullIsNull }
    override val supportsConvert: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsConvert }
    override val supportsTableCorrelationNames: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsTableCorrelationNames }
    override val supportsDifferentTableCorrelationNames: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsDifferentTableCorrelationNames }
    override val supportsExpressionsInOrderBy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsExpressionsInOrderBy }
    override val supportsOrderByUnrelated: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsOrderByUnrelated }
    override val supportsGroupBy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsGroupBy }
    override val supportsGroupByUnrelated: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsGroupByUnrelated }
    override val supportsGroupByBeyondSelect: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsGroupByBeyondSelect }
    override val supportsLikeEscapeClause: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsLikeEscapeClause }
    override val supportsMultipleResultSets: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsMultipleResultSets }
    override val supportsMultipleTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsMultipleTransactions }
    override val supportsNonNullableColumns: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsNonNullableColumns }
    override val supportsMinimumSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsMinimumSQLGrammar }
    override val supportsExtendedSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsExtendedSQLGrammar }
    override val supportsANSI92EntryLevelSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsANSI92EntryLevelSQL }
    override val supportsANSI92IntermediateSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsANSI92IntermediateSQL }
    override val supportsANSI92FullSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsANSI92FullSQL }
    override val supportsOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsOuterJoins }
    override val supportsFullOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsFullOuterJoins }
    override val supportsLimitedOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsLimitedOuterJoins }
    override val schemaTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.schemaTerm }
    override val procedureTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.procedureTerm }
    override val catalogTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.catalogTerm }
    override val isCatalogAtStart: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.isCatalogAtStart }
    override val catalogSeparator: ValueMetadataAction<DB, String>
        get() = ValueMetadataAction<DB, String> { it.catalogSeparator }
    override val supportsSchemasInDataManipulation: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSchemasInDataManipulation }
    override val supportsSchemasInProcedureCalls: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSchemasInProcedureCalls }
    override val supportsSchemasInTableDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSchemasInTableDefinitions }
    override val supportsSchemasInIndexDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSchemasInIndexDefinitions }
    override val supportsSchemasInPrivilegeDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSchemasInPrivilegeDefinitions }
    override val supportsCatalogsInProcedureCalls: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsCatalogsInProcedureCalls }
    override val supportsCatalogsInIndexDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsCatalogsInIndexDefinitions }
    override val supportsCatalogsInPrivilegeDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsCatalogsInPrivilegeDefinitions }
    override val supportsPositionedDelete: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsPositionedDelete }
    override val supportsPositionedUpdate: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsPositionedUpdate }
    override val supportsSelectForUpdate: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSelectForUpdate }
    override val supportsStoredProcedures: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsStoredProcedures }
    override val supportsSubqueriesInComparisons: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSubqueriesInComparisons }
    override val supportsSubqueriesInExists: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSubqueriesInExists }
    override val supportsSubqueriesInIns: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSubqueriesInIns }
    override val supportsCorrelatedSubqueries: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsCorrelatedSubqueries }
    override val supportsUnion: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsUnion }
    override val supportsUnionAll: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsUnionAll }
    override val supportsOpenCursorsAcrossCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsOpenCursorsAcrossCommit }
    override val supportsOpenCursorsAcrossRollback: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsOpenCursorsAcrossRollback }
    override val supportsOpenStatementsAcrossCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsOpenStatementsAcrossCommit }
    override val maxBinaryLiteralLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxBinaryLiteralLength }
    override val maxCharLiteralLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxCharLiteralLength }
    override val maxColumnNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxColumnNameLength }
    override val maxColumnsInGroupBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxColumnsInGroupBy }
    override val maxColumnsInOrderBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxColumnsInOrderBy }
    override val maxColumnsInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxColumnsInSelect }
    override val maxColumnsInTable: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxColumnsInTable }
    override val maxConnections: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxConnections }
    override val maxCursorNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxCursorNameLength }
    override val maxIndexLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxIndexLength }
    override val maxSchemaNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxSchemaNameLength }
    override val maxCatalogNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxCatalogNameLength }
    override val maxRowSize: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxRowSize }
    override val doesMaxRowSizeIncludeBlobs: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.doesMaxRowSizeIncludeBlobs }
    override val maxStatementLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxStatementLength }
    override val maxStatements: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxStatements }
    override val maxTableNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxTableNameLength }
    override val maxTablesInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxTablesInSelect }
    override val maxUserNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.maxUserNameLength }
    override val defaultTransactionIsolation: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.defaultTransactionIsolation }
    override val supportsTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsTransactions }
    override val supportsDataManipulationTransactionsOnly: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsDataManipulationTransactionsOnly }
    override val dataDefinitionCausesTransactionCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.dataDefinitionCausesTransactionCommit }
    override val dataDefinitionIgnoredInTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.dataDefinitionIgnoredInTransactions }

    @OptIn(UnmanagedSql::class)
    override val schemas: ResultSetMetadataAction<DB, SchemaResults>
        get() = ResultSetMetadataActionImpl<DB, SchemaResults> { it.schemas }
    override val supportsBatchUpdates: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsBatchUpdates }
    override val supportsSavepoints: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsSavepoints }
    override val supportsNamedParameters: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsNamedParameters }
    override val supportsMultipleOpenResults: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsMultipleOpenResults }
    override val resultSetHoldability: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.resultSetHoldability }
    override val databaseMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.databaseMajorVersion }
    override val databaseMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.databaseMinorVersion }
    override val JDBCMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.JDBCMajorVersion }
    override val JDBCMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.JDBCMinorVersion }
    override val SQLStateType: ValueMetadataAction<DB, Int>
        get() = ValueMetadataAction<DB, Int> { it.SQLStateType }
    override val locatorsUpdateCopy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.locatorsUpdateCopy }
    override val supportsStatementPooling: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsStatementPooling }
    override val rowIdLifetime: ValueMetadataAction<DB, RowIdLifetime>
        get() = ValueMetadataAction<DB, RowIdLifetime> { it.rowIdLifetime }
    override val generatedKeyAlwaysReturned: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.generatedKeyAlwaysReturned }
    override val maxLogicalLobSize: ValueMetadataAction<DB, Long>
        get() = ValueMetadataAction<DB, Long> { it.maxLogicalLobSize }
    override val supportsRefCursors: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataAction<DB, Boolean> { it.supportsRefCursors }

    override fun insertsAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.insertsAreDetected(type)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?
    ): ResultSetMetadataActionImpl<DB, AttributeResults> {
        return ResultSetMetadataActionImpl<DB, AttributeResults> {
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
        return ValueMetadataAction<DB, Boolean> {
            it.supportsConvert(fromType,
                               toType)
        }
    }

    override fun supportsTransactionIsolationLevel(level: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.supportsTransactionIsolationLevel(level)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getProcedures(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String
    ): ResultSetMetadataActionImpl<DB, ProcedureResults> {
        return ResultSetMetadataActionImpl<DB, ProcedureResults> {
            it.getProcedures(catalog,
                             schemaPattern,
                             procedureNamePattern)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getProcedureColumns(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
        columnNamePattern: String
    ): ResultSetMetadataActionImpl<DB, ProcedureColumnResults> {
        return ResultSetMetadataActionImpl<DB, ProcedureColumnResults> {
            it.getProcedureColumns(
                catalog,
                schemaPattern,
                procedureNamePattern,
                columnNamePattern
            )
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getTables(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        types: Array<String>?
    ): ResultSetMetadataActionImpl<DB, TableMetadataResults> {
        return ResultSetMetadataActionImpl<DB, TableMetadataResults> {
            it.getTables(catalog,
                         schemaPattern,
                         tableNamePattern,
                         types)
        }
    }

    override val tableTypes: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataAction<DB, List<String>> { it.tableTypes }

    @OptIn(UnmanagedSql::class)
    override fun getColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
    ): ResultSetMetadataActionImpl<DB, ColumnsResults> {
        return ResultSetMetadataActionImpl<DB, ColumnsResults> {
            it.getColumns(
                catalog,
                schemaPattern,
                tableNamePattern,
                columnNamePattern
            )
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getColumnPrivileges(
        catalog: String,
        schema: String,
        table: String,
        columnNamePattern: String
    ): ResultSetMetadataActionImpl<DB, ColumnPrivilegesResult> {
        return ResultSetMetadataActionImpl<DB, ColumnPrivilegesResult> {
            it.getColumnPrivileges(catalog,
                                   schema,
                                   table,
                                   columnNamePattern)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getTablePrivileges(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
    ): ResultSetMetadataActionImpl<DB, TablePrivilegesResult> {
        return ResultSetMetadataActionImpl<DB, TablePrivilegesResult> {
            it.getTablePrivileges(catalog,
                                  schemaPattern,
                                  tableNamePattern)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getBestRowIdentifier(
        catalog: String,
        schema: String,
        table: String,
        scope: Int,
        nullable: Boolean
    ): ResultSetMetadataActionImpl<DB, BestRowIdentifierResult> {
        return ResultSetMetadataActionImpl<DB, BestRowIdentifierResult> {
            it.getBestRowIdentifier(catalog, schema, table, scope, nullable)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getVersionColumns(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataActionImpl<DB, VersionColumnsResult> {
        return ResultSetMetadataActionImpl<DB, VersionColumnsResult> {
            it.getVersionColumns(catalog, schema, table)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getPrimaryKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataActionImpl<DB, PrimaryKeyResults> {
        return ResultSetMetadataActionImpl<DB, PrimaryKeyResults> {
            it.getPrimaryKeys(catalog, schema, table)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getImportedKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataActionImpl<DB, KeysResult> {
        return ResultSetMetadataActionImpl<DB, KeysResult> {
            it.getImportedKeys(catalog, schema, table)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getExportedKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataActionImpl<DB, KeysResult> {
        return ResultSetMetadataActionImpl<DB, KeysResult> {
            it.getExportedKeys(catalog, schema, table)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getCrossReference(
        parentCatalog: String,
        parentSchema: String,
        parentTable: String,
        foreignCatalog: String,
        foreignSchema: String,
        foreignTable: String
    ): ResultSetMetadataActionImpl<DB, KeysResult> {
        return ResultSetMetadataActionImpl<DB, KeysResult> {
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

    @OptIn(UnmanagedSql::class)
    override fun getTypeInfo(): ResultSetMetadataActionImpl<DB, TypeInfoResults> {
        return ResultSetMetadataActionImpl<DB, TypeInfoResults> { it.getTypeInfo() }
    }

    @UnmanagedSql
    override fun getUnsafeIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
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
        return ValueMetadataAction<DB, Boolean> {
            it.supportsResultSetConcurrency(type,
                                            concurrency)
        }
    }

    override fun ownUpdatesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.ownUpdatesAreVisible(type)
        }
    }

    override fun ownDeletesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.ownDeletesAreVisible(type)
        }
    }

    override fun ownInsertsAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.othersInsertsAreVisible(type)
        }
    }

    override fun othersUpdatesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.othersUpdatesAreVisible(type)
        }
    }

    override fun othersDeletesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.othersDeletesAreVisible(type)
        }
    }

    override fun othersInsertsAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.othersInsertsAreVisible(type)
        }
    }

    override fun updatesAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.updatesAreDetected(type)
        }
    }

    override fun deletesAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.deletesAreDetected(type)
        }
    }

    @UnmanagedSql
    override fun getUDTs(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String,
        types: IntArray
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
            RawResultSetWrapper(it.getUDTs(catalog, schemaPattern, typeNamePattern, types))
        }
    }

    @UnmanagedSql
    override fun getSuperTypes(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
            RawResultSetWrapper(it.getSuperTypes(catalog, schemaPattern, typeNamePattern))
        }
    }

    @UnmanagedSql
    override fun getSuperTables(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
            RawResultSetWrapper(
                it.getSuperTables(catalog, schemaPattern, tableNamePattern)
            )
        }
    }

    override fun supportsResultSetHoldability(holdability: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataAction<DB, Boolean> {
            it.supportsResultSetHoldability(holdability)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getSchemas(
        catalog: String,
        schemaPattern: String
    ): ResultSetMetadataActionImpl<DB, SchemaResults> {
        return ResultSetMetadataActionImpl<DB, SchemaResults> {
            it.getSchemas(catalog, schemaPattern)
        }
    }

    @UnmanagedSql
    override fun getClientInfoProperties(): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
            RawResultSetWrapper(it.getClientInfoProperties())
        }
    }

    @UnmanagedSql
    override fun getFunctions(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
            RawResultSetWrapper(
                it.getFunctions(catalog, schemaPattern, functionNamePattern)
            )
        }
    }

    @UnmanagedSql
    override fun getFunctionColumns(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String,
        columnNamePattern: String
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
            RawResultSetWrapper(
                it.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern)
            )
        }
    }

    @UnmanagedSql
    override fun getPseudoColumns(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        columnNamePattern: String
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {

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