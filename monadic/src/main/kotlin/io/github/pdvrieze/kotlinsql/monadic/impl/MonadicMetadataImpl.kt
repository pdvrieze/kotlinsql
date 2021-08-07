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
import io.github.pdvrieze.kotlinsql.monadic.actions.*
import io.github.pdvrieze.kotlinsql.monadic.actions.impl.*
import java.sql.RowIdLifetime

internal class MonadicMetadataImpl<DB: Database> : MonadicMetadata<DB> {

    override val maxColumnsInIndex: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxColumnsInIndex }
    override val supportsSubqueriesInQuantifieds: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSubqueriesInQuantifieds }
    override val supportsIntegrityEnhancementFacility: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsIntegrityEnhancementFacility }
    override val supportsGetGeneratedKeys: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsGetGeneratedKeys }
    override val supportsCoreSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsCoreSQLGrammar }
    override val supportsDataDefinitionAndDataManipulationTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsDataDefinitionAndDataManipulationTransactions }
    override val supportsCatalogsInTableDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsCatalogsInTableDefinitions }
    override val supportsOpenStatementsAcrossRollback: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsOpenStatementsAcrossRollback }
    override val supportsStoredFunctionsUsingCallSyntax: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsStoredFunctionsUsingCallSyntax }
    override val databaseProductName: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.databaseProductName }
    override val databaseProductVersion: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.databaseProductVersion }
    override val getJDBCMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.getJDBCMajorVersion }
    override val maxProcedureNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxProcedureNameLength }
    override val getCatalogTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.getCatalogTerm }
    override val supportsCatalogsInDataManipulation: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsCatalogsInDataManipulation }
    override val getMaxUserNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.getMaxUserNameLength }
    override val timeDateFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataActionImpl { it.timeDateFunctions }
    override val autoCommitFailureClosesAllResultSets: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.autoCommitFailureClosesAllResultSets }
    override val getMaxColumnsInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.getMaxColumnsInSelect }
    override val catalogs: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataActionImpl { it.catalogs }
    override val storesLowerCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.storesLowerCaseQuotedIdentifiers }
    override val getMaxColumnsInOrderBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.getMaxColumnsInOrderBy }
    override val getDriverMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.getDriverMinorVersion }
    override val allProceduresAreCallable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.allProceduresAreCallable }
    override val allTablesAreSelectable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.allTablesAreSelectable }
    override val URL: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.URL }
    override val userName: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.userName }
    override val isReadOnly: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.isReadOnly }
    override val nullsAreSortedHigh: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.nullsAreSortedHigh }
    override val nullsAreSortedLow: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.nullsAreSortedLow }
    override val nullsAreSortedAtStart: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.nullsAreSortedAtStart }
    override val nullsAreSortedAtEnd: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.nullsAreSortedAtEnd }
    override val driverName: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.driverName }
    override val driverVersion: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.driverVersion }
    override val driverMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.driverMajorVersion }
    override val driverMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.driverMinorVersion }
    override val usesLocalFiles: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.usesLocalFiles }
    override val usesLocalFilePerTable: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.usesLocalFilePerTable }
    override val supportsMixedCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsMixedCaseIdentifiers }
    override val storesUpperCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.storesUpperCaseIdentifiers }
    override val storesLowerCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.storesLowerCaseIdentifiers }
    override val storesMixedCaseIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.storesMixedCaseIdentifiers }
    override val supportsMixedCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsMixedCaseQuotedIdentifiers }
    override val storesUpperCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.storesUpperCaseQuotedIdentifiers }
    override val storesMixedCaseQuotedIdentifiers: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.storesMixedCaseQuotedIdentifiers }
    override val identifierQuoteString: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.identifierQuoteString }
    override val SQLKeywords: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataActionImpl { it.SQLKeywords }
    override val numericFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataActionImpl { it.numericFunctions }
    override val stringFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataActionImpl { it.stringFunctions }
    override val systemFunctions: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataActionImpl { it.systemFunctions }
    override val searchStringEscape: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.searchStringEscape }
    override val extraNameCharacters: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.extraNameCharacters }
    override val supportsAlterTableWithAddColumn: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsAlterTableWithAddColumn }
    override val supportsAlterTableWithDropColumn: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsAlterTableWithDropColumn }
    override val supportsColumnAliasing: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsColumnAliasing }
    override val nullPlusNonNullIsNull: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.nullPlusNonNullIsNull }
    override val supportsConvert: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsConvert }
    override val supportsTableCorrelationNames: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsTableCorrelationNames }
    override val supportsDifferentTableCorrelationNames: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsDifferentTableCorrelationNames }
    override val supportsExpressionsInOrderBy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsExpressionsInOrderBy }
    override val supportsOrderByUnrelated: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsOrderByUnrelated }
    override val supportsGroupBy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsGroupBy }
    override val supportsGroupByUnrelated: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsGroupByUnrelated }
    override val supportsGroupByBeyondSelect: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsGroupByBeyondSelect }
    override val supportsLikeEscapeClause: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsLikeEscapeClause }
    override val supportsMultipleResultSets: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsMultipleResultSets }
    override val supportsMultipleTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsMultipleTransactions }
    override val supportsNonNullableColumns: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsNonNullableColumns }
    override val supportsMinimumSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsMinimumSQLGrammar }
    override val supportsExtendedSQLGrammar: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsExtendedSQLGrammar }
    override val supportsANSI92EntryLevelSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsANSI92EntryLevelSQL }
    override val supportsANSI92IntermediateSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsANSI92IntermediateSQL }
    override val supportsANSI92FullSQL: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsANSI92FullSQL }
    override val supportsOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsOuterJoins }
    override val supportsFullOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsFullOuterJoins }
    override val supportsLimitedOuterJoins: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsLimitedOuterJoins }
    override val schemaTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.schemaTerm }
    override val procedureTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.procedureTerm }
    override val catalogTerm: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.catalogTerm }
    override val isCatalogAtStart: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.isCatalogAtStart }
    override val catalogSeparator: ValueMetadataAction<DB, String>
        get() = ValueMetadataActionImpl { it.catalogSeparator }
    override val supportsSchemasInDataManipulation: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSchemasInDataManipulation }
    override val supportsSchemasInProcedureCalls: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSchemasInProcedureCalls }
    override val supportsSchemasInTableDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSchemasInTableDefinitions }
    override val supportsSchemasInIndexDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSchemasInIndexDefinitions }
    override val supportsSchemasInPrivilegeDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSchemasInPrivilegeDefinitions }
    override val supportsCatalogsInProcedureCalls: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsCatalogsInProcedureCalls }
    override val supportsCatalogsInIndexDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsCatalogsInIndexDefinitions }
    override val supportsCatalogsInPrivilegeDefinitions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsCatalogsInPrivilegeDefinitions }
    override val supportsPositionedDelete: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsPositionedDelete }
    override val supportsPositionedUpdate: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsPositionedUpdate }
    override val supportsSelectForUpdate: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSelectForUpdate }
    override val supportsStoredProcedures: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsStoredProcedures }
    override val supportsSubqueriesInComparisons: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSubqueriesInComparisons }
    override val supportsSubqueriesInExists: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSubqueriesInExists }
    override val supportsSubqueriesInIns: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSubqueriesInIns }
    override val supportsCorrelatedSubqueries: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsCorrelatedSubqueries }
    override val supportsUnion: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsUnion }
    override val supportsUnionAll: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsUnionAll }
    override val supportsOpenCursorsAcrossCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsOpenCursorsAcrossCommit }
    override val supportsOpenCursorsAcrossRollback: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsOpenCursorsAcrossRollback }
    override val supportsOpenStatementsAcrossCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsOpenStatementsAcrossCommit }
    override val maxBinaryLiteralLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxBinaryLiteralLength }
    override val maxCharLiteralLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxCharLiteralLength }
    override val maxColumnNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxColumnNameLength }
    override val maxColumnsInGroupBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxColumnsInGroupBy }
    override val maxColumnsInOrderBy: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxColumnsInOrderBy }
    override val maxColumnsInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxColumnsInSelect }
    override val maxColumnsInTable: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxColumnsInTable }
    override val maxConnections: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxConnections }
    override val maxCursorNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxCursorNameLength }
    override val maxIndexLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxIndexLength }
    override val maxSchemaNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxSchemaNameLength }
    override val maxCatalogNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxCatalogNameLength }
    override val maxRowSize: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxRowSize }
    override val doesMaxRowSizeIncludeBlobs: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.doesMaxRowSizeIncludeBlobs }
    override val maxStatementLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxStatementLength }
    override val maxStatements: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxStatements }
    override val maxTableNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxTableNameLength }
    override val maxTablesInSelect: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxTablesInSelect }
    override val maxUserNameLength: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.maxUserNameLength }
    override val defaultTransactionIsolation: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.defaultTransactionIsolation }
    override val supportsTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsTransactions }
    override val supportsDataManipulationTransactionsOnly: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsDataManipulationTransactionsOnly }
    override val dataDefinitionCausesTransactionCommit: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.dataDefinitionCausesTransactionCommit }
    override val dataDefinitionIgnoredInTransactions: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.dataDefinitionIgnoredInTransactions }

    @OptIn(UnmanagedSql::class)
    override val schemas: ResultSetMetadataAction<DB, SchemaResults>
        get() = ResultSetMetadataActionImpl<DB, SchemaResults> { it.schemas }
    override val supportsBatchUpdates: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsBatchUpdates }
    override val supportsSavepoints: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsSavepoints }
    override val supportsNamedParameters: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsNamedParameters }
    override val supportsMultipleOpenResults: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsMultipleOpenResults }
    override val resultSetHoldability: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.resultSetHoldability }
    override val databaseMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.databaseMajorVersion }
    override val databaseMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.databaseMinorVersion }
    override val JDBCMajorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.JDBCMajorVersion }
    override val JDBCMinorVersion: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.JDBCMinorVersion }
    override val SQLStateType: ValueMetadataAction<DB, Int>
        get() = ValueMetadataActionImpl { it.SQLStateType }
    override val locatorsUpdateCopy: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.locatorsUpdateCopy }
    override val supportsStatementPooling: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsStatementPooling }
    override val rowIdLifetime: ValueMetadataAction<DB, RowIdLifetime>
        get() = ValueMetadataActionImpl { it.rowIdLifetime }
    override val generatedKeyAlwaysReturned: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.generatedKeyAlwaysReturned }
    override val maxLogicalLobSize: ValueMetadataAction<DB, Long>
        get() = ValueMetadataActionImpl { it.maxLogicalLobSize }
    override val supportsRefCursors: ValueMetadataAction<DB, Boolean>
        get() = ValueMetadataActionImpl { it.supportsRefCursors }

    override fun insertsAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
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
        return ValueMetadataActionImpl {
            it.supportsConvert(fromType, toType)
        }
    }

    override fun supportsTransactionIsolationLevel(level: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.supportsTransactionIsolationLevel(level)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getProcedures(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String
    ): ResultSetMetadataActionImpl<DB, ProcedureResults> {
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
            it.getTables(catalog,
                         schemaPattern,
                         tableNamePattern,
                         types)
        }
    }

    override val tableTypes: ValueMetadataAction<DB, List<String>>
        get() = ValueMetadataActionImpl { it.tableTypes }

    @OptIn(UnmanagedSql::class)
    override fun getColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
    ): ResultSetMetadataActionImpl<DB, ColumnsResults> {
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
            it.getBestRowIdentifier(catalog, schema, table, scope, nullable)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getVersionColumns(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataActionImpl<DB, VersionColumnsResult> {
        return ResultSetMetadataActionImpl {
            it.getVersionColumns(catalog, schema, table)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getPrimaryKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataActionImpl<DB, PrimaryKeyResults> {
        return ResultSetMetadataActionImpl {
            it.getPrimaryKeys(catalog, schema, table)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getImportedKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataActionImpl<DB, KeysResult> {
        return ResultSetMetadataActionImpl {
            it.getImportedKeys(catalog, schema, table)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getExportedKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSetMetadataActionImpl<DB, KeysResult> {
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl { it.getTypeInfo() }
    }

    @UnmanagedSql
    override fun getUnsafeIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl {
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
        return ValueMetadataActionImpl {
            it.supportsResultSetConcurrency(type,
                                            concurrency)
        }
    }

    override fun ownUpdatesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.ownUpdatesAreVisible(type)
        }
    }

    override fun ownDeletesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.ownDeletesAreVisible(type)
        }
    }

    override fun ownInsertsAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.othersInsertsAreVisible(type)
        }
    }

    override fun othersUpdatesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.othersUpdatesAreVisible(type)
        }
    }

    override fun othersDeletesAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.othersDeletesAreVisible(type)
        }
    }

    override fun othersInsertsAreVisible(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.othersInsertsAreVisible(type)
        }
    }

    override fun updatesAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.updatesAreDetected(type)
        }
    }

    override fun deletesAreDetected(type: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
            RawResultSetWrapper(it.getUDTs(catalog, schemaPattern, typeNamePattern, types))
        }
    }

    @UnmanagedSql
    override fun getSuperTypes(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl {
            RawResultSetWrapper(it.getSuperTypes(catalog, schemaPattern, typeNamePattern))
        }
    }

    @UnmanagedSql
    override fun getSuperTables(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl {
            RawResultSetWrapper(
                it.getSuperTables(catalog, schemaPattern, tableNamePattern)
            )
        }
    }

    override fun supportsResultSetHoldability(holdability: Int): ValueMetadataAction<DB, Boolean> {
        return ValueMetadataActionImpl {
            it.supportsResultSetHoldability(holdability)
        }
    }

    @OptIn(UnmanagedSql::class)
    override fun getSchemas(
        catalog: String,
        schemaPattern: String
    ): ResultSetMetadataActionImpl<DB, SchemaResults> {
        return ResultSetMetadataActionImpl {
            it.getSchemas(catalog, schemaPattern)
        }
    }

    @UnmanagedSql
    override fun getClientInfoProperties(): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl {
            RawResultSetWrapper(it.getClientInfoProperties())
        }
    }

    @UnmanagedSql
    override fun getFunctions(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String
    ): ResultSetMetadataActionImpl<DB, RawResultSetWrapper> {
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {
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
        return ResultSetMetadataActionImpl {

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