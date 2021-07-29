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
import uk.ac.bournemouth.util.kotlin.sql.impl.DBAction2
import java.sql.RowIdLifetime

internal class MonadicMetadataImpl<DB: Database> : MonadicMetadata<DB> {

    override val maxColumnsInIndex: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxColumnsInIndex }
    override val supportsSubqueriesInQuantifieds: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSubqueriesInQuantifieds }
    override val supportsIntegrityEnhancementFacility: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsIntegrityEnhancementFacility }
    override val supportsGetGeneratedKeys: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsGetGeneratedKeys }
    override val supportsCoreSQLGrammar: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsCoreSQLGrammar }
    override val supportsDataDefinitionAndDataManipulationTransactions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsDataDefinitionAndDataManipulationTransactions }
    override val supportsCatalogsInTableDefinitions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsCatalogsInTableDefinitions }
    override val supportsOpenStatementsAcrossRollback: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsOpenStatementsAcrossRollback }
    override val supportsStoredFunctionsUsingCallSyntax: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsStoredFunctionsUsingCallSyntax }
    override val databaseProductName: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.databaseProductName }
    override val databaseProductVersion: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.databaseProductVersion }
    override val getJDBCMajorVersion: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.getJDBCMajorVersion }
    override val maxProcedureNameLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxProcedureNameLength }
    override val getCatalogTerm: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.getCatalogTerm }
    override val supportsCatalogsInDataManipulation: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsCatalogsInDataManipulation }
    override val getMaxUserNameLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.getMaxUserNameLength }
    override val timeDateFunctions: DBAction2.ValueMetadata<DB, List<String>>
        get() = DBAction2.ValueMetadata { it.timeDateFunctions }
    override val autoCommitFailureClosesAllResultSets: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.autoCommitFailureClosesAllResultSets }
    override val getMaxColumnsInSelect: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.getMaxColumnsInSelect }
    override val catalogs: DBAction2.ValueMetadata<DB, List<String>>
        get() = DBAction2.ValueMetadata { it.catalogs }
    override val storesLowerCaseQuotedIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.storesLowerCaseQuotedIdentifiers }
    override val getMaxColumnsInOrderBy: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.getMaxColumnsInOrderBy }
    override val getDriverMinorVersion: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.getDriverMinorVersion }
    override val allProceduresAreCallable: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.allProceduresAreCallable }
    override val allTablesAreSelectable: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.allTablesAreSelectable }
    override val URL: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.URL }
    override val userName: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.userName }
    override val isReadOnly: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.isReadOnly }
    override val nullsAreSortedHigh: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.nullsAreSortedHigh }
    override val nullsAreSortedLow: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.nullsAreSortedLow }
    override val nullsAreSortedAtStart: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.nullsAreSortedAtStart }
    override val nullsAreSortedAtEnd: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.nullsAreSortedAtEnd }
    override val driverName: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.driverName }
    override val driverVersion: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.driverVersion }
    override val driverMajorVersion: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.driverMajorVersion }
    override val driverMinorVersion: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.driverMinorVersion }
    override val usesLocalFiles: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.usesLocalFiles }
    override val usesLocalFilePerTable: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.usesLocalFilePerTable }
    override val supportsMixedCaseIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsMixedCaseIdentifiers }
    override val storesUpperCaseIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.storesUpperCaseIdentifiers }
    override val storesLowerCaseIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.storesLowerCaseIdentifiers }
    override val storesMixedCaseIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.storesMixedCaseIdentifiers }
    override val supportsMixedCaseQuotedIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsMixedCaseQuotedIdentifiers }
    override val storesUpperCaseQuotedIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.storesUpperCaseQuotedIdentifiers }
    override val storesMixedCaseQuotedIdentifiers: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.storesMixedCaseQuotedIdentifiers }
    override val identifierQuoteString: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.identifierQuoteString }
    override val SQLKeywords: DBAction2.ValueMetadata<DB, List<String>>
        get() = DBAction2.ValueMetadata { it.SQLKeywords }
    override val numericFunctions: DBAction2.ValueMetadata<DB, List<String>>
        get() = DBAction2.ValueMetadata { it.numericFunctions }
    override val stringFunctions: DBAction2.ValueMetadata<DB, List<String>>
        get() = DBAction2.ValueMetadata { it.stringFunctions }
    override val systemFunctions: DBAction2.ValueMetadata<DB, List<String>>
        get() = DBAction2.ValueMetadata { it.systemFunctions }
    override val searchStringEscape: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.searchStringEscape }
    override val extraNameCharacters: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.extraNameCharacters }
    override val supportsAlterTableWithAddColumn: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsAlterTableWithAddColumn }
    override val supportsAlterTableWithDropColumn: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsAlterTableWithDropColumn }
    override val supportsColumnAliasing: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsColumnAliasing }
    override val nullPlusNonNullIsNull: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.nullPlusNonNullIsNull }
    override val supportsConvert: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsConvert }
    override val supportsTableCorrelationNames: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsTableCorrelationNames }
    override val supportsDifferentTableCorrelationNames: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsDifferentTableCorrelationNames }
    override val supportsExpressionsInOrderBy: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsExpressionsInOrderBy }
    override val supportsOrderByUnrelated: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsOrderByUnrelated }
    override val supportsGroupBy: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsGroupBy }
    override val supportsGroupByUnrelated: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsGroupByUnrelated }
    override val supportsGroupByBeyondSelect: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsGroupByBeyondSelect }
    override val supportsLikeEscapeClause: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsLikeEscapeClause }
    override val supportsMultipleResultSets: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsMultipleResultSets }
    override val supportsMultipleTransactions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsMultipleTransactions }
    override val supportsNonNullableColumns: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsNonNullableColumns }
    override val supportsMinimumSQLGrammar: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsMinimumSQLGrammar }
    override val supportsExtendedSQLGrammar: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsExtendedSQLGrammar }
    override val supportsANSI92EntryLevelSQL: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsANSI92EntryLevelSQL }
    override val supportsANSI92IntermediateSQL: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsANSI92IntermediateSQL }
    override val supportsANSI92FullSQL: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsANSI92FullSQL }
    override val supportsOuterJoins: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsOuterJoins }
    override val supportsFullOuterJoins: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsFullOuterJoins }
    override val supportsLimitedOuterJoins: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsLimitedOuterJoins }
    override val schemaTerm: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.schemaTerm }
    override val procedureTerm: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.procedureTerm }
    override val catalogTerm: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.catalogTerm }
    override val isCatalogAtStart: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.isCatalogAtStart }
    override val catalogSeparator: DBAction2.ValueMetadata<DB, String>
        get() = DBAction2.ValueMetadata { it.catalogSeparator }
    override val supportsSchemasInDataManipulation: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSchemasInDataManipulation }
    override val supportsSchemasInProcedureCalls: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSchemasInProcedureCalls }
    override val supportsSchemasInTableDefinitions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSchemasInTableDefinitions }
    override val supportsSchemasInIndexDefinitions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSchemasInIndexDefinitions }
    override val supportsSchemasInPrivilegeDefinitions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSchemasInPrivilegeDefinitions }
    override val supportsCatalogsInProcedureCalls: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsCatalogsInProcedureCalls }
    override val supportsCatalogsInIndexDefinitions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsCatalogsInIndexDefinitions }
    override val supportsCatalogsInPrivilegeDefinitions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsCatalogsInPrivilegeDefinitions }
    override val supportsPositionedDelete: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsPositionedDelete }
    override val supportsPositionedUpdate: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsPositionedUpdate }
    override val supportsSelectForUpdate: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSelectForUpdate }
    override val supportsStoredProcedures: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsStoredProcedures }
    override val supportsSubqueriesInComparisons: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSubqueriesInComparisons }
    override val supportsSubqueriesInExists: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSubqueriesInExists }
    override val supportsSubqueriesInIns: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSubqueriesInIns }
    override val supportsCorrelatedSubqueries: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsCorrelatedSubqueries }
    override val supportsUnion: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsUnion }
    override val supportsUnionAll: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsUnionAll }
    override val supportsOpenCursorsAcrossCommit: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsOpenCursorsAcrossCommit }
    override val supportsOpenCursorsAcrossRollback: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsOpenCursorsAcrossRollback }
    override val supportsOpenStatementsAcrossCommit: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsOpenStatementsAcrossCommit }
    override val maxBinaryLiteralLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxBinaryLiteralLength }
    override val maxCharLiteralLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxCharLiteralLength }
    override val maxColumnNameLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxColumnNameLength }
    override val maxColumnsInGroupBy: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxColumnsInGroupBy }
    override val maxColumnsInOrderBy: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxColumnsInOrderBy }
    override val maxColumnsInSelect: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxColumnsInSelect }
    override val maxColumnsInTable: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxColumnsInTable }
    override val maxConnections: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxConnections }
    override val maxCursorNameLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxCursorNameLength }
    override val maxIndexLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxIndexLength }
    override val maxSchemaNameLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxSchemaNameLength }
    override val maxCatalogNameLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxCatalogNameLength }
    override val maxRowSize: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxRowSize }
    override val doesMaxRowSizeIncludeBlobs: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.doesMaxRowSizeIncludeBlobs }
    override val maxStatementLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxStatementLength }
    override val maxStatements: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxStatements }
    override val maxTableNameLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxTableNameLength }
    override val maxTablesInSelect: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxTablesInSelect }
    override val maxUserNameLength: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.maxUserNameLength }
    override val defaultTransactionIsolation: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.defaultTransactionIsolation }
    override val supportsTransactions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsTransactions }
    override val supportsDataManipulationTransactionsOnly: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsDataManipulationTransactionsOnly }
    override val dataDefinitionCausesTransactionCommit: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.dataDefinitionCausesTransactionCommit }
    override val dataDefinitionIgnoredInTransactions: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.dataDefinitionIgnoredInTransactions }
    override val schemas: DBAction2.ResultSetMetadata<DB, SchemaResults>
        get() = DBAction2.ResultSetMetadata { it.schemas }
    override val supportsBatchUpdates: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsBatchUpdates }
    override val supportsSavepoints: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsSavepoints }
    override val supportsNamedParameters: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsNamedParameters }
    override val supportsMultipleOpenResults: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsMultipleOpenResults }
    override val resultSetHoldability: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.resultSetHoldability }
    override val databaseMajorVersion: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.databaseMajorVersion }
    override val databaseMinorVersion: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.databaseMinorVersion }
    override val JDBCMajorVersion: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.JDBCMajorVersion }
    override val JDBCMinorVersion: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.JDBCMinorVersion }
    override val SQLStateType: DBAction2.ValueMetadata<DB, Int>
        get() = DBAction2.ValueMetadata { it.SQLStateType }
    override val locatorsUpdateCopy: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.locatorsUpdateCopy }
    override val supportsStatementPooling: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsStatementPooling }
    override val rowIdLifetime: DBAction2.ValueMetadata<DB, RowIdLifetime>
        get() = DBAction2.ValueMetadata { it.rowIdLifetime }
    override val generatedKeyAlwaysReturned: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.generatedKeyAlwaysReturned }
    override val maxLogicalLobSize: DBAction2.ValueMetadata<DB, Long>
        get() = DBAction2.ValueMetadata { it.maxLogicalLobSize }
    override val supportsRefCursors: DBAction2.ValueMetadata<DB, Boolean>
        get() = DBAction2.ValueMetadata { it.supportsRefCursors }

    override fun insertsAreDetected(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.insertsAreDetected(type) }
    }

    override fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?
                              ): DBAction2.ResultSetMetadata<DB, AttributeResults> {
        return DBAction2.ResultSetMetadata {
            it.getAttributes(
                catalog,
                schemaPattern,
                typeNamePattern,
                attributeNamePattern
            )
        }
    }

    override fun supportsConvert(fromType: Int, toType: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.supportsConvert(fromType, toType) }
    }

    override fun supportsTransactionIsolationLevel(level: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.supportsTransactionIsolationLevel(level) }
    }

    override fun getProcedures(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String
                              ): DBAction2.ResultSetMetadata<DB, ProcedureResults> {
        return DBAction2.ResultSetMetadata { it.getProcedures(catalog, schemaPattern, procedureNamePattern) }
    }

    override fun getProcedureColumns(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
        columnNamePattern: String
                                    ): DBAction2.ResultSetMetadata<DB, ProcedureColumnResults> {
        return DBAction2.ResultSetMetadata {
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
                          ): DBAction2.ResultSetMetadata<DB, TableMetadataResults> {
        return DBAction2.ResultSetMetadata { it.getTables(catalog, schemaPattern, tableNamePattern, types) }
    }

    override val tableTypes: DBAction2.ValueMetadata<DB, List<String>>
        get() = DBAction2.ValueMetadata { it.tableTypes }

    override fun getColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
                           ): DBAction2.ResultSetMetadata<DB, ColumnsResults> {
        return DBAction2.ResultSetMetadata {
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
                                    ): DBAction2.ResultSetMetadata<DB, ColumnPrivilegesResult> {
        return DBAction2.ResultSetMetadata { it.getColumnPrivileges(catalog, schema, table, columnNamePattern) }
    }

    override fun getTablePrivileges(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
                                   ): DBAction2.ResultSetMetadata<DB, TablePrivilegesResult> {
        return DBAction2.ResultSetMetadata { it.getTablePrivileges(catalog, schemaPattern, tableNamePattern) }
    }

    override fun getBestRowIdentifier(
        catalog: String,
        schema: String,
        table: String,
        scope: Int,
        nullable: Boolean
                                     ): DBAction2.ResultSetMetadata<DB, BestRowIdentifierResult> {
        return DBAction2.ResultSetMetadata { it.getBestRowIdentifier(catalog, schema, table, scope, nullable) }
    }

    override fun getVersionColumns(
        catalog: String,
        schema: String,
        table: String
                                  ): DBAction2.ResultSetMetadata<DB, VersionColumnsResult> {
        return DBAction2.ResultSetMetadata { it.getVersionColumns(catalog, schema, table) }
    }

    override fun getPrimaryKeys(
        catalog: String,
        schema: String,
        table: String
                               ): DBAction2.ResultSetMetadata<DB, PrimaryKeyResults> {
        return DBAction2.ResultSetMetadata { it.getPrimaryKeys(catalog, schema, table) }
    }

    override fun getImportedKeys(catalog: String, schema: String, table: String): DBAction2.ResultSetMetadata<DB, KeysResult> {
        return DBAction2.ResultSetMetadata { it.getImportedKeys(catalog, schema, table) }
    }

    override fun getExportedKeys(catalog: String, schema: String, table: String): DBAction2.ResultSetMetadata<DB, KeysResult> {
        return DBAction2.ResultSetMetadata { it.getExportedKeys(catalog, schema, table) }
    }

    override fun getCrossReference(
        parentCatalog: String,
        parentSchema: String,
        parentTable: String,
        foreignCatalog: String,
        foreignSchema: String,
        foreignTable: String
                                  ): DBAction2.ResultSetMetadata<DB, KeysResult> {
        return DBAction2.ResultSetMetadata {
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

    override fun getTypeInfo(): DBAction2.ResultSetMetadata<DB, TypeInfoResults> {
        return DBAction2.ResultSetMetadata { it.getTypeInfo() }
    }

    override fun getUnsafeIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean
                                   ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata {
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

    override fun supportsResultSetConcurrency(type: Int, concurrency: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.supportsResultSetConcurrency(type, concurrency) }
    }

    override fun ownUpdatesAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.ownUpdatesAreVisible(type) }
    }

    override fun ownDeletesAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.ownDeletesAreVisible(type) }
    }

    override fun ownInsertsAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.othersInsertsAreVisible(type) }
    }

    override fun othersUpdatesAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.othersUpdatesAreVisible(type) }
    }

    override fun othersDeletesAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.othersDeletesAreVisible(type) }
    }

    override fun othersInsertsAreVisible(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.othersInsertsAreVisible(type) }
    }

    override fun updatesAreDetected(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.updatesAreDetected(type) }
    }

    override fun deletesAreDetected(type: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.deletesAreDetected(type) }
    }

    override fun getUDTs(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String,
        types: IntArray
                        ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata {
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
                              ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata {
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
                               ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata {
            RawResultSetWrapper(
                it.getSuperTables(
                    catalog,
                    schemaPattern,
                    tableNamePattern
                )
            )
        }
    }

    override fun supportsResultSetHoldability(holdability: Int): DBAction2.ValueMetadata<DB, Boolean> {
        return DBAction2.ValueMetadata { it.supportsResultSetHoldability(holdability) }
    }

    override fun getSchemas(catalog: String, schemaPattern: String): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata { RawResultSetWrapper(it.getSchemas(catalog, schemaPattern)) }
    }

    override fun getClientInfoProperties(): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata { RawResultSetWrapper(it.getClientInfoProperties()) }
    }

    override fun getFunctions(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String
                             ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata {
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
                                   ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata {
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
                                 ): DBAction2.ResultSetMetadata<DB, RawResultSetWrapper> {
        return DBAction2.ResultSetMetadata {
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