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

package io.github.pdvrieze.jdbc.recorder

import io.github.pdvrieze.jdbc.recorder.RecordingConnection.RecordingResultSet
import java.sql.DatabaseMetaData
import java.sql.ResultSet
import java.sql.RowIdLifetime

abstract class AbstractRecordingMetadata(delegate: DatabaseMetaData) : WrappingActionRecorder<DatabaseMetaData>(delegate), DatabaseMetaData {
    abstract override fun getConnection(): RecordingConnection

    override fun supportsSubqueriesInQuantifieds(): Boolean = record {
        delegate.supportsSubqueriesInQuantifieds()
    }

    override fun supportsGetGeneratedKeys(): Boolean = record {
        delegate.supportsGetGeneratedKeys()
    }

    override fun supportsCoreSQLGrammar(): Boolean = record {
        delegate.supportsCoreSQLGrammar()
    }

    override fun getMaxColumnsInIndex(): Int = record {
        delegate.maxColumnsInIndex
    }

    override fun insertsAreDetected(type: Int): Boolean = record(type) {
        delegate.insertsAreDetected(type)
    }

    override fun supportsIntegrityEnhancementFacility(): Boolean = record {
        delegate.supportsIntegrityEnhancementFacility()
    }

    override fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?
    ): ResultSet = record(catalog, schemaPattern, typeNamePattern, attributeNamePattern) {
        connection.RecordingResultSet(
            delegate.getAttributes(
                catalog,
                schemaPattern,
                typeNamePattern,
                attributeNamePattern
            )
        )
    }

    override fun getDatabaseProductVersion(): String = record {
        delegate.databaseProductVersion
    }

    override fun supportsOpenStatementsAcrossRollback(): Boolean = record {
        delegate.supportsOpenStatementsAcrossRollback()
    }

    override fun getDatabaseProductName(): String = record {
        delegate.databaseProductName
    }

    override fun getMaxProcedureNameLength(): Int = record {
        delegate.maxProcedureNameLength
    }

    override fun getCatalogTerm(): String = record {
        delegate.catalogTerm
    }

    override fun supportsCatalogsInDataManipulation(): Boolean = record {
        delegate.supportsCatalogsInDataManipulation()
    }

    override fun getMaxUserNameLength(): Int = record {
        delegate.maxUserNameLength
    }

    override fun getJDBCMajorVersion(): Int = record {
        delegate.jdbcMajorVersion
    }

    override fun getTimeDateFunctions(): String = record {
        delegate.timeDateFunctions
    }

    override fun supportsStoredFunctionsUsingCallSyntax(): Boolean = record {
        delegate.supportsStoredFunctionsUsingCallSyntax()
    }

    override fun autoCommitFailureClosesAllResultSets(): Boolean = record {
        delegate.autoCommitFailureClosesAllResultSets()
    }

    override fun getMaxColumnsInSelect(): Int = record {
        delegate.maxColumnsInSelect
    }

    override fun getCatalogs(): ResultSet = record {
        connection.RecordingResultSet(delegate.catalogs)
    }

    override fun storesLowerCaseQuotedIdentifiers(): Boolean = record {
        delegate.storesLowerCaseQuotedIdentifiers()
    }

    override fun supportsDataDefinitionAndDataManipulationTransactions(): Boolean = record {
        delegate.supportsDataDefinitionAndDataManipulationTransactions()
    }

    override fun supportsCatalogsInTableDefinitions(): Boolean = record {
        delegate.supportsCatalogsInTableDefinitions()
    }

    override fun getMaxColumnsInOrderBy(): Int = record {
        delegate.maxColumnsInOrderBy
    }

    override fun getDriverMinorVersion(): Int = record {
        delegate.driverMinorVersion
    }

    override fun storesUpperCaseIdentifiers(): Boolean = record {
        delegate.storesUpperCaseIdentifiers()
    }

    override fun nullsAreSortedLow(): Boolean = record {
        delegate.nullsAreSortedLow()
    }

    override fun supportsSchemasInIndexDefinitions(): Boolean = record {
        delegate.supportsSchemasInIndexDefinitions()
    }

    override fun getMaxStatementLength(): Int = record {
        delegate.maxStatementLength
    }

    override fun supportsTransactions(): Boolean = record {
        delegate.supportsTransactions()
    }

    override fun supportsResultSetConcurrency(type: Int, concurrency: Int): Boolean = record(type, concurrency) {
        delegate.supportsResultSetConcurrency(type, concurrency)
    }

    override fun isReadOnly(): Boolean = record {
        delegate.isReadOnly
    }

    override fun usesLocalFiles(): Boolean = record {
        delegate.usesLocalFiles()
    }

    override fun supportsResultSetType(type: Int): Boolean = record(type) {
        delegate.supportsResultSetType(type)
    }

    override fun getMaxConnections(): Int = record {
        delegate.maxConnections
    }

    override fun getTables(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        types: Array<out String>?
    ): ResultSet = record(arrayOf(catalog, schemaPattern, tableNamePattern, types)) {
        val rs = delegate.getTables(catalog, schemaPattern, tableNamePattern, types)
        connection.RecordingResultSet(rs, "getTables()")
    }

    override fun supportsMultipleResultSets(): Boolean = record {
        delegate.supportsMultipleResultSets()
    }

    override fun dataDefinitionIgnoredInTransactions(): Boolean = record {
        delegate.dataDefinitionIgnoredInTransactions()
    }

    override fun getFunctions(catalog: String?, schemaPattern: String?, functionNamePattern: String?): ResultSet =
        record(catalog, schemaPattern, functionNamePattern) {
            connection.RecordingResultSet(delegate.getFunctions(catalog, schemaPattern, functionNamePattern))
        }

    override fun getSearchStringEscape(): String = record {
        delegate.searchStringEscape
    }

    override fun supportsGroupBy(): Boolean = record {
        delegate.supportsGroupBy()
    }

    override fun getMaxTableNameLength(): Int = record {
        delegate.maxTableNameLength
    }

    override fun dataDefinitionCausesTransactionCommit(): Boolean = record {
        delegate.dataDefinitionCausesTransactionCommit()
    }

    override fun supportsOpenStatementsAcrossCommit(): Boolean = record {
        delegate.supportsOpenStatementsAcrossCommit()
    }

    override fun ownInsertsAreVisible(type: Int): Boolean = record(type) {
        delegate.ownInsertsAreVisible(type)
    }

    override fun getSchemaTerm(): String = record {
        delegate.schemaTerm
    }

    override fun isCatalogAtStart(): Boolean = record {
        delegate.isCatalogAtStart
    }

    override fun getFunctionColumns(
        catalog: String?,
        schemaPattern: String?,
        functionNamePattern: String?,
        columnNamePattern: String?
    ): ResultSet = record(catalog, schemaPattern, functionNamePattern, columnNamePattern) {
        connection.RecordingResultSet(
            delegate.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern)
        )
    }

    override fun supportsTransactionIsolationLevel(level: Int): Boolean = record(level) {
        delegate.supportsTransactionIsolationLevel(level)
    }

    override fun nullsAreSortedAtStart(): Boolean = record {
        delegate.nullsAreSortedAtStart()
    }

    override fun getPrimaryKeys(catalog: String?, schema: String?, table: String?): RecordingResultSet =
        record(catalog, schema, table) {
            connection.RecordingResultSet(delegate.getPrimaryKeys(catalog, schema, table))
        }

    override fun getProcedureTerm(): String = record {
        delegate.procedureTerm
    }

    override fun supportsANSI92IntermediateSQL(): Boolean = record {
        delegate.supportsANSI92IntermediateSQL()
    }

    override fun getDatabaseMajorVersion(): Int = record {
        delegate.databaseMajorVersion
    }

    override fun supportsOuterJoins(): Boolean = record {
        delegate.supportsOuterJoins()
    }

    override fun supportsLikeEscapeClause(): Boolean = record {
        delegate.supportsLikeEscapeClause()
    }

    override fun supportsPositionedUpdate(): Boolean = record {
        delegate.supportsPositionedUpdate()
    }

    override fun supportsMixedCaseIdentifiers(): Boolean = record {
        delegate.supportsMixedCaseIdentifiers()
    }

    override fun supportsLimitedOuterJoins(): Boolean = record {
        delegate.supportsLimitedOuterJoins()
    }

    override fun getSQLStateType(): Int = record {
        delegate.sqlStateType
    }

    override fun getSystemFunctions(): String = record {
        delegate.systemFunctions
    }

    override fun getMaxRowSize(): Int = record {
        delegate.maxRowSize
    }

    override fun supportsOpenCursorsAcrossRollback(): Boolean = record {
        delegate.supportsOpenCursorsAcrossRollback()
    }

    override fun getTableTypes(): RecordingResultSet = record {
        connection.RecordingResultSet(delegate.tableTypes)
    }

    override fun getMaxTablesInSelect(): Int = record {
        delegate.maxTablesInSelect
    }

    override fun nullsAreSortedHigh(): Boolean = record {
        delegate.nullsAreSortedHigh()
    }

    override fun getURL(): String = record {
        delegate.url
    }

    override fun supportsNamedParameters(): Boolean = record {
        delegate.supportsNamedParameters()
    }

    override fun supportsConvert(): Boolean = record {
        delegate.supportsConvert()
    }

    override fun supportsConvert(fromType: Int, toType: Int): Boolean = record(fromType, toType) {
        delegate.supportsConvert(fromType, toType)
    }

    override fun getMaxStatements(): Int = record {
        delegate.maxStatements
    }

    override fun getProcedureColumns(
        catalog: String?,
        schemaPattern: String?,
        procedureNamePattern: String?,
        columnNamePattern: String?
    ): RecordingResultSet = record(catalog, schemaPattern, procedureNamePattern, columnNamePattern) {
        connection.RecordingResultSet(
            delegate.getProcedureColumns(
                catalog,
                schemaPattern,
                procedureNamePattern,
                columnNamePattern
            )
        )
    }

    override fun allTablesAreSelectable(): Boolean = record {
        delegate.allTablesAreSelectable()
    }

    override fun getJDBCMinorVersion(): Int = record {
        delegate.jdbcMinorVersion
    }

    override fun getCatalogSeparator(): String = record {
        delegate.catalogSeparator
    }

    override fun getSuperTypes(catalog: String?, schemaPattern: String?, typeNamePattern: String?): RecordingResultSet =
        record(catalog, schemaPattern, typeNamePattern) {
            connection.RecordingResultSet(delegate.getSuperTypes(catalog, schemaPattern, typeNamePattern))
        }

    override fun getMaxBinaryLiteralLength(): Int = record {
        delegate.maxBinaryLiteralLength
    }

    override fun getTypeInfo(): RecordingResultSet = record {
        connection.RecordingResultSet(delegate.typeInfo)
    }

    override fun getVersionColumns(catalog: String?, schema: String?, table: String?): RecordingResultSet =
        record(catalog, schema, table) {
            connection.RecordingResultSet(delegate.getVersionColumns(catalog, schema, table))
        }

    override fun supportsMultipleOpenResults(): Boolean = record {
        delegate.supportsMultipleOpenResults()
    }

    override fun deletesAreDetected(type: Int): Boolean = record(type) {
        delegate.deletesAreDetected(type)
    }

    override fun getDatabaseMinorVersion(): Int = record {
        delegate.databaseMinorVersion
    }

    override fun supportsMinimumSQLGrammar(): Boolean = record {
        delegate.supportsMinimumSQLGrammar()
    }

    override fun getMaxColumnsInGroupBy(): Int = record {
        delegate.maxColumnsInGroupBy
    }

    override fun getNumericFunctions(): String = record {
        delegate.numericFunctions
    }

    override fun getExtraNameCharacters(): String = record {
        delegate.extraNameCharacters
    }

    override fun getMaxCursorNameLength(): Int = record {
        delegate.maxCursorNameLength
    }

    override fun nullsAreSortedAtEnd(): Boolean = record {
        delegate.nullsAreSortedAtEnd()
    }

    override fun supportsSchemasInDataManipulation(): Boolean = record {
        delegate.supportsSchemasInDataManipulation()
    }

    override fun getSchemas(): RecordingResultSet = record {
        connection.RecordingResultSet(delegate.schemas)
    }

    override fun getSchemas(catalog: String?, schemaPattern: String?): RecordingResultSet = record(catalog, schemaPattern) {
        connection.RecordingResultSet(delegate.getSchemas(catalog, schemaPattern))
    }

    override fun supportsCorrelatedSubqueries(): Boolean = record {
        delegate.supportsCorrelatedSubqueries()
    }

    override fun getDefaultTransactionIsolation(): Int = record {
        delegate.defaultTransactionIsolation
    }

    override fun locatorsUpdateCopy(): Boolean = record {
        delegate.locatorsUpdateCopy()
    }

    override fun getColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
    ): RecordingResultSet = record(arrayOf(catalog, schemaPattern, tableNamePattern, columnNamePattern)) {
        connection.RecordingResultSet(
            delegate.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern),
            "metadata.getColumns()"
        )
    }

    override fun getCrossReference(
        parentCatalog: String?,
        parentSchema: String?,
        parentTable: String?,
        foreignCatalog: String?,
        foreignSchema: String?,
        foreignTable: String?
    ): RecordingResultSet = record(parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema, foreignTable) {
        connection.RecordingResultSet(
            delegate.getCrossReference(
                parentCatalog,
                parentSchema,
                parentTable,
                foreignCatalog,
                foreignSchema,
                foreignTable
            )
        )
    }

    override fun ownDeletesAreVisible(type: Int): Boolean = record(type) {
        delegate.ownDeletesAreVisible(type)
    }

    override fun othersUpdatesAreVisible(type: Int): Boolean = record(type) {
        delegate.othersUpdatesAreVisible(type)
    }

    override fun supportsStatementPooling(): Boolean = record {
        delegate.supportsStatementPooling()
    }

    override fun storesLowerCaseIdentifiers(): Boolean = record {
        delegate.storesLowerCaseIdentifiers()
    }

    override fun supportsCatalogsInIndexDefinitions(): Boolean = record {
        delegate.supportsCatalogsInIndexDefinitions()
    }

    override fun ownUpdatesAreVisible(type: Int): Boolean = record(type) {
        delegate.ownUpdatesAreVisible(type)
    }

    override fun getUDTs(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        types: IntArray?
    ): RecordingResultSet =
        record(catalog, schemaPattern, typeNamePattern, types) {
            connection.RecordingResultSet(
                delegate.getUDTs(
                    catalog,
                    schemaPattern,
                    typeNamePattern,
                    types
                )
            )
        }

    override fun getStringFunctions(): String = record {
        delegate.stringFunctions
    }

    override fun getMaxColumnsInTable(): Int = record {
        delegate.maxColumnsInTable
    }

    override fun supportsColumnAliasing(): Boolean = record {
        delegate.supportsColumnAliasing()
    }

    override fun supportsSchemasInProcedureCalls(): Boolean = record {
        delegate.supportsSchemasInProcedureCalls()
    }

    override fun getClientInfoProperties(): RecordingResultSet = record {
        connection.RecordingResultSet(delegate.clientInfoProperties)
    }

    override fun usesLocalFilePerTable(): Boolean = record {
        delegate.usesLocalFilePerTable()
    }

    override fun getIdentifierQuoteString(): String = record {
        delegate.identifierQuoteString
    }

    override fun supportsFullOuterJoins(): Boolean = record {
        delegate.supportsFullOuterJoins()
    }

    override fun supportsOrderByUnrelated(): Boolean = record {
        delegate.supportsOrderByUnrelated()
    }

    override fun supportsSchemasInTableDefinitions(): Boolean = record {
        delegate.supportsSchemasInTableDefinitions()
    }

    override fun supportsCatalogsInProcedureCalls(): Boolean = record {
        delegate.supportsCatalogsInProcedureCalls()
    }

    override fun getUserName(): String = record {
        delegate.userName
    }

    override fun getBestRowIdentifier(
        catalog: String?,
        schema: String?,
        table: String?,
        scope: Int,
        nullable: Boolean
    ): RecordingResultSet = record(catalog, schema, table, scope, nullable) {
        connection.RecordingResultSet(delegate.getBestRowIdentifier(catalog, schema, table, scope, nullable))
    }

    override fun supportsTableCorrelationNames(): Boolean = record {
        delegate.supportsTableCorrelationNames()
    }

    override fun getMaxIndexLength(): Int = record {
        delegate.maxIndexLength
    }

    override fun supportsSubqueriesInExists(): Boolean = record {
        delegate.supportsSubqueriesInExists()
    }

    override fun getMaxSchemaNameLength(): Int = record {
        delegate.maxSchemaNameLength
    }

    override fun supportsANSI92EntryLevelSQL(): Boolean = record {
        delegate.supportsANSI92EntryLevelSQL()
    }

    override fun getDriverVersion(): String = record {
        delegate.driverVersion
    }

    override fun getPseudoColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
    ): RecordingResultSet = record(catalog, schemaPattern, tableNamePattern, columnNamePattern) {
        connection.RecordingResultSet(
            delegate.getPseudoColumns(
                catalog,
                schemaPattern,
                tableNamePattern,
                columnNamePattern
            )
        )
    }

    override fun supportsMixedCaseQuotedIdentifiers(): Boolean = record {
        delegate.supportsMixedCaseQuotedIdentifiers()
    }

    override fun getProcedures(catalog: String?, schemaPattern: String?, procedureNamePattern: String?): RecordingResultSet =
        record(catalog, schemaPattern, procedureNamePattern) {
            connection.RecordingResultSet(delegate.getProcedures(catalog, schemaPattern, procedureNamePattern))
        }

    override fun getDriverMajorVersion(): Int = record {
        delegate.driverMajorVersion
    }

    override fun supportsANSI92FullSQL(): Boolean = record {
        delegate.supportsANSI92FullSQL()
    }

    override fun supportsAlterTableWithAddColumn(): Boolean = record {
        delegate.supportsAlterTableWithAddColumn()
    }

    override fun supportsResultSetHoldability(holdability: Int): Boolean = record(holdability) {
        delegate.supportsResultSetHoldability(holdability)
    }

    override fun getColumnPrivileges(
        catalog: String?,
        schema: String?,
        table: String?,
        columnNamePattern: String?
    ): RecordingResultSet = record(catalog, schema, table, columnNamePattern) {
        connection.RecordingResultSet(delegate.getColumnPrivileges(catalog, schema, table, columnNamePattern))
    }

    override fun getImportedKeys(catalog: String?, schema: String?, table: String?): RecordingResultSet =
        record(catalog, schema, table) {
            connection.RecordingResultSet(delegate.getImportedKeys(catalog, schema, table))
        }

    override fun supportsUnionAll(): Boolean = record {
        delegate.supportsUnionAll()
    }

    override fun getRowIdLifetime(): RowIdLifetime = record {
        delegate.rowIdLifetime
    }

    override fun getDriverName(): String = record {
        delegate.driverName
    }

    override fun doesMaxRowSizeIncludeBlobs(): Boolean = record {
        delegate.doesMaxRowSizeIncludeBlobs()
    }

    override fun supportsGroupByUnrelated(): Boolean = record {
        delegate.supportsGroupByUnrelated()
    }

    override fun getIndexInfo(
        catalog: String?,
        schema: String?,
        table: String?,
        unique: Boolean,
        approximate: Boolean
    ): RecordingResultSet = record(catalog, schema, table, unique, approximate) {
        connection.RecordingResultSet(delegate.getIndexInfo(catalog, schema, table, unique, approximate))
    }

    override fun supportsSubqueriesInIns(): Boolean = record {
        delegate.supportsSubqueriesInIns()
    }

    override fun supportsStoredProcedures(): Boolean = record {
        delegate.supportsStoredProcedures()
    }

    override fun getExportedKeys(catalog: String?, schema: String?, table: String?): RecordingResultSet =
        record(catalog, schema, table) {
            connection.RecordingResultSet(delegate.getExportedKeys(catalog, schema, table))
        }

    override fun supportsPositionedDelete(): Boolean = record {
        delegate.supportsPositionedDelete()
    }

    override fun supportsAlterTableWithDropColumn(): Boolean = record {
        delegate.supportsAlterTableWithDropColumn()
    }

    override fun supportsExpressionsInOrderBy(): Boolean = record {
        delegate.supportsExpressionsInOrderBy()
    }

    override fun getMaxCatalogNameLength(): Int = record {
        delegate.maxCatalogNameLength
    }

    override fun supportsExtendedSQLGrammar(): Boolean = record {
        delegate.supportsExtendedSQLGrammar()
    }

    override fun othersInsertsAreVisible(type: Int): Boolean = record(type) {
        delegate.othersInsertsAreVisible(type)
    }

    override fun updatesAreDetected(type: Int): Boolean = record(type) {
        delegate.updatesAreDetected(type)
    }

    override fun supportsDataManipulationTransactionsOnly(): Boolean = record {
        delegate.supportsDataManipulationTransactionsOnly()
    }

    override fun supportsSubqueriesInComparisons(): Boolean = record {
        delegate.supportsSubqueriesInComparisons()
    }

    override fun supportsSavepoints(): Boolean = record {
        delegate.supportsSavepoints()
    }

    override fun getSQLKeywords(): String = record {
        delegate.sqlKeywords
    }

    override fun getMaxColumnNameLength(): Int = record {
        delegate.maxColumnNameLength
    }

    override fun nullPlusNonNullIsNull(): Boolean = record {
        delegate.nullPlusNonNullIsNull()
    }

    override fun supportsGroupByBeyondSelect(): Boolean = record {
        delegate.supportsGroupByBeyondSelect()
    }

    override fun supportsCatalogsInPrivilegeDefinitions(): Boolean = record {
        delegate.supportsCatalogsInPrivilegeDefinitions()
    }

    override fun allProceduresAreCallable(): Boolean = record {
        delegate.allProceduresAreCallable()
    }

    override fun getSuperTables(catalog: String?, schemaPattern: String?, tableNamePattern: String?): RecordingResultSet =
        record(catalog, schemaPattern, tableNamePattern) {
            connection.RecordingResultSet(delegate.getSuperTables(catalog, schemaPattern, tableNamePattern))
        }

    override fun generatedKeyAlwaysReturned(): Boolean = record {
        delegate.generatedKeyAlwaysReturned()
    }

    override fun storesUpperCaseQuotedIdentifiers(): Boolean = record {
        delegate.storesUpperCaseQuotedIdentifiers()
    }

    override fun getMaxCharLiteralLength(): Int = record {
        delegate.maxCharLiteralLength
    }

    override fun othersDeletesAreVisible(type: Int): Boolean = record(type) {
        delegate.othersDeletesAreVisible(type)
    }

    override fun supportsNonNullableColumns(): Boolean = record {
        delegate.supportsNonNullableColumns()
    }

    override fun supportsUnion(): Boolean = record {
        delegate.supportsUnion()
    }

    override fun supportsDifferentTableCorrelationNames(): Boolean = record {
        delegate.supportsDifferentTableCorrelationNames()
    }

    override fun supportsSchemasInPrivilegeDefinitions(): Boolean = record {
        delegate.supportsSchemasInPrivilegeDefinitions()
    }

    override fun supportsSelectForUpdate(): Boolean = record {
        delegate.supportsSelectForUpdate()
    }

    override fun supportsMultipleTransactions(): Boolean = record {
        delegate.supportsMultipleTransactions()
    }

    override fun storesMixedCaseQuotedIdentifiers(): Boolean = record {
        delegate.storesMixedCaseQuotedIdentifiers()
    }

    override fun supportsOpenCursorsAcrossCommit(): Boolean = record {
        delegate.supportsOpenCursorsAcrossCommit()
    }

    override fun storesMixedCaseIdentifiers(): Boolean = record {
        delegate.storesMixedCaseIdentifiers()
    }

    override fun getTablePrivileges(catalog: String?, schemaPattern: String?, tableNamePattern: String?): RecordingResultSet =
        record(catalog, schemaPattern, tableNamePattern) {
            connection.RecordingResultSet(delegate.getTablePrivileges(catalog, schemaPattern, tableNamePattern))
        }

    override fun supportsBatchUpdates(): Boolean = record {
        delegate.supportsBatchUpdates()
    }

    override fun getResultSetHoldability(): Int = record {
        delegate.resultSetHoldability
    }

    override fun toString(): String {
        return "<metadata>"
    }
}

private fun Array<String>.dataArray(vararg data: Pair<String, Any?>): Array<Any?> {
    return Array(size) { idx ->
        val colName = get(idx)
        data.firstOrNull { it.first == colName }?.second
    }
}
