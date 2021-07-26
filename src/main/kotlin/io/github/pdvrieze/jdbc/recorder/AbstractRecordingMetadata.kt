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

import uk.ac.bournemouth.kotlinsql.ILengthColumn
import java.sql.DatabaseMetaData
import java.sql.ResultSet
import java.sql.RowIdLifetime

abstract class AbstractRecordingMetadata(val delegate: DatabaseMetaData) : ActionRecorder(), DatabaseMetaData {
    abstract override fun getConnection(): RecordingConnection

    override fun supportsSubqueriesInQuantifieds(): Boolean {
        TODO("not implemented")
    }

    override fun supportsGetGeneratedKeys(): Boolean {
        TODO("not implemented")
    }

    override fun supportsCoreSQLGrammar(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxColumnsInIndex(): Int {
        TODO("not implemented")
    }

    override fun insertsAreDetected(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun supportsIntegrityEnhancementFacility(): Boolean {
        TODO("not implemented")
    }

    override fun getAttributes(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        attributeNamePattern: String?
                              ): ResultSet {
        TODO("not implemented")
    }

    override fun getDatabaseProductVersion(): String {
        TODO("not implemented")
    }

    override fun supportsOpenStatementsAcrossRollback(): Boolean {
        TODO("not implemented")
    }

    override fun getDatabaseProductName(): String {
        TODO("not implemented")
    }

    override fun getMaxProcedureNameLength(): Int {
        TODO("not implemented")
    }

    override fun getCatalogTerm(): String {
        TODO("not implemented")
    }

    override fun supportsCatalogsInDataManipulation(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxUserNameLength(): Int {
        TODO("not implemented")
    }

    override fun getJDBCMajorVersion(): Int {
        TODO("not implemented")
    }

    override fun getTimeDateFunctions(): String {
        TODO("not implemented")
    }

    override fun supportsStoredFunctionsUsingCallSyntax(): Boolean {
        TODO("not implemented")
    }

    override fun autoCommitFailureClosesAllResultSets(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxColumnsInSelect(): Int {
        TODO("not implemented")
    }

    override fun getCatalogs(): ResultSet {
        TODO("not implemented")
    }

    override fun storesLowerCaseQuotedIdentifiers(): Boolean {
        TODO("not implemented")
    }

    override fun supportsDataDefinitionAndDataManipulationTransactions(): Boolean {
        TODO("not implemented")
    }

    override fun supportsCatalogsInTableDefinitions(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxColumnsInOrderBy(): Int {
        TODO("not implemented")
    }

    override fun getDriverMinorVersion(): Int {
        TODO("not implemented")
    }

    override fun storesUpperCaseIdentifiers(): Boolean {
        TODO("not implemented")
    }

    override fun nullsAreSortedLow(): Boolean {
        TODO("not implemented")
    }

    override fun supportsSchemasInIndexDefinitions(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxStatementLength(): Int {
        TODO("not implemented")
    }

    override fun supportsTransactions(): Boolean {
        TODO("not implemented")
    }

    override fun supportsResultSetConcurrency(type: Int, concurrency: Int): Boolean {
        TODO("not implemented")
    }

    override fun isReadOnly(): Boolean {
        TODO("not implemented")
    }

    override fun usesLocalFiles(): Boolean {
        TODO("not implemented")
    }

    override fun supportsResultSetType(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun getMaxConnections(): Int {
        TODO("not implemented")
    }

    override fun getTables(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        types: Array<out String>?
                          ): ResultSet = record(arrayOf(catalog, schemaPattern, tableNamePattern, types)){
        val columns = arrayOf(
            "TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "TABLE_TYPE", "REMARKS",
            "TYPE_CAT", "TYPE_SCHEM", "TYPE_NAME", "SELF_REFERENCING_COL_NAME", "REF_GENERATION"
                             )
        val data = connection.tables.map { table ->
            columns.dataArray(
                "TABLE_NAME" to table._name,
                "TABLE_TYPE" to "TABLE",
                "REMARKS" to ""
                             )
        }
        val rs = delegate.getTables(catalog, schemaPattern, tableNamePattern, types)
        connection.RecordingResultSet(rs, "getTables()", columns, data)
    }

    override fun supportsMultipleResultSets(): Boolean {
        TODO("not implemented")
    }

    override fun dataDefinitionIgnoredInTransactions(): Boolean {
        TODO("not implemented")
    }

    override fun getFunctions(catalog: String?, schemaPattern: String?, functionNamePattern: String?): ResultSet {
        TODO("not implemented")
    }

    override fun getSearchStringEscape(): String {
        TODO("not implemented")
    }

    override fun supportsGroupBy(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxTableNameLength(): Int {
        TODO("not implemented")
    }

    override fun dataDefinitionCausesTransactionCommit(): Boolean {
        TODO("not implemented")
    }

    override fun supportsOpenStatementsAcrossCommit(): Boolean {
        TODO("not implemented")
    }

    override fun ownInsertsAreVisible(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun getSchemaTerm(): String {
        TODO("not implemented")
    }

    override fun isCatalogAtStart(): Boolean {
        TODO("not implemented")
    }

    override fun getFunctionColumns(
        catalog: String?,
        schemaPattern: String?,
        functionNamePattern: String?,
        columnNamePattern: String?
                                   ): ResultSet {
        TODO("not implemented")
    }

    override fun supportsTransactionIsolationLevel(level: Int): Boolean {
        TODO("not implemented")
    }

    override fun nullsAreSortedAtStart(): Boolean {
        TODO("not implemented")
    }

    override fun getPrimaryKeys(catalog: String?, schema: String?, table: String?): ResultSet {
        TODO("not implemented")
    }

    override fun getProcedureTerm(): String {
        TODO("not implemented")
    }

    override fun supportsANSI92IntermediateSQL(): Boolean {
        TODO("not implemented")
    }

    override fun getDatabaseMajorVersion(): Int {
        TODO("not implemented")
    }

    override fun supportsOuterJoins(): Boolean {
        TODO("not implemented")
    }

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        TODO("not implemented")
    }

    override fun supportsLikeEscapeClause(): Boolean {
        TODO("not implemented")
    }

    override fun supportsPositionedUpdate(): Boolean {
        TODO("not implemented")
    }

    override fun supportsMixedCaseIdentifiers(): Boolean {
        TODO("not implemented")
    }

    override fun supportsLimitedOuterJoins(): Boolean {
        TODO("not implemented")
    }

    override fun getSQLStateType(): Int {
        TODO("not implemented")
    }

    override fun getSystemFunctions(): String {
        TODO("not implemented")
    }

    override fun getMaxRowSize(): Int {
        TODO("not implemented")
    }

    override fun supportsOpenCursorsAcrossRollback(): Boolean {
        TODO("not implemented")
    }

    override fun getTableTypes(): ResultSet {
        TODO("not implemented")
    }

    override fun getMaxTablesInSelect(): Int {
        TODO("not implemented")
    }

    override fun nullsAreSortedHigh(): Boolean {
        TODO("not implemented")
    }

    override fun getURL(): String {
        TODO("not implemented")
    }

    override fun supportsNamedParameters(): Boolean {
        TODO("not implemented")
    }

    override fun supportsConvert(): Boolean {
        TODO("not implemented")
    }

    override fun supportsConvert(fromType: Int, toType: Int): Boolean {
        TODO("not implemented")
    }

    override fun getMaxStatements(): Int {
        TODO("not implemented")
    }

    override fun getProcedureColumns(
        catalog: String?,
        schemaPattern: String?,
        procedureNamePattern: String?,
        columnNamePattern: String?
                                    ): ResultSet {
        TODO("not implemented")
    }

    override fun allTablesAreSelectable(): Boolean {
        TODO("not implemented")
    }

    override fun getJDBCMinorVersion(): Int {
        TODO("not implemented")
    }

    override fun getCatalogSeparator(): String {
        TODO("not implemented")
    }

    override fun getSuperTypes(catalog: String?, schemaPattern: String?, typeNamePattern: String?): ResultSet {
        TODO("not implemented")
    }

    override fun getMaxBinaryLiteralLength(): Int {
        TODO("not implemented")
    }

    override fun getTypeInfo(): ResultSet {
        TODO("not implemented")
    }

    override fun getVersionColumns(catalog: String?, schema: String?, table: String?): ResultSet {
        TODO("not implemented")
    }

    override fun supportsMultipleOpenResults(): Boolean {
        TODO("not implemented")
    }

    override fun deletesAreDetected(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun getDatabaseMinorVersion(): Int {
        TODO("not implemented")
    }

    override fun supportsMinimumSQLGrammar(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxColumnsInGroupBy(): Int {
        TODO("not implemented")
    }

    override fun getNumericFunctions(): String {
        TODO("not implemented")
    }

    override fun getExtraNameCharacters(): String {
        TODO("not implemented")
    }

    override fun getMaxCursorNameLength(): Int {
        TODO("not implemented")
    }

    override fun nullsAreSortedAtEnd(): Boolean {
        TODO("not implemented")
    }

    override fun supportsSchemasInDataManipulation(): Boolean {
        TODO("not implemented")
    }

    override fun getSchemas(): ResultSet {
        TODO("not implemented")
    }

    override fun getSchemas(catalog: String?, schemaPattern: String?): ResultSet {
        TODO("not implemented")
    }

    override fun supportsCorrelatedSubqueries(): Boolean {
        TODO("not implemented")
    }

    override fun getDefaultTransactionIsolation(): Int {
        TODO("not implemented")
    }

    override fun locatorsUpdateCopy(): Boolean {
        TODO("not implemented")
    }

    override fun getColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
                           ): ResultSet = record(arrayOf(catalog, schemaPattern, tableNamePattern, columnNamePattern)){
        val columns = arrayOf(
            "TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "COLUMN_NAME", "DATA_TYPE",
            "TYPE_NAME", "COLUMN_SIZE", "BUFFER_LENGTH", "DECIMAL_DIGITS", "NUM_PREC_RADIX",
            "NULLABLE", "REMARKS", "COLUMN_DEF", "SQL_DATA_TYPE", "SQL_DATETIME_SUB",
            "CHAR_OCTET_LENGTH", "ORDINAL_POSITION", "IS_NULLABLE", "SCOPE_CATALOG", "SCOPE_SCHEMA",
            "SCOPE_TABLE", "SOURCE_DATA_TYPE", "IS_AUTOINCREMENT", "IS_GENERATEDCOLUMN"
                             )
        val data = connection.tables.flatMap { table ->
            if (tableNamePattern != null && tableNamePattern != table._name) {
                emptyList()
            } else {
                table._cols.map { column ->
                    columns.dataArray(
                        "TABLE_NAME" to table._name,
                        "COLUMN_NAME" to column.name,
                        "DATA_TYPE" to column.type.javaType,
                        "TYPE_NAME" to column.type.typeName,
                        "COLUMN_SIZE" to ((column as? ILengthColumn)?.length ?: 0),
                        "NULLABLE" to when (column.notnull) {
                            true  -> DatabaseMetaData.columnNullable
                            false -> DatabaseMetaData.columnNoNulls
                            else  -> DatabaseMetaData.columnNullableUnknown
                        },
                        "IS_NULLABLE" to column.notnull?.let{ !it }.toOptionalBoolean(),
                        "IS_AUTOINCREMENT" to column.autoincrement.toOptionalBoolean(),
                        "IS_GENERATEDCOLUMN" to "NO"
                                     )
                }
            }
        }
        val rs = delegate.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern)
        connection.RecordingResultSet(rs, "metadata.getColumns()", columns, data)
    }

    private fun Boolean?.toOptionalBoolean(): String {
        return when (this) {
            true  -> "YES"
            false -> "NO"
            else  -> ""
        }
    }

    override fun getCrossReference(
        parentCatalog: String?,
        parentSchema: String?,
        parentTable: String?,
        foreignCatalog: String?,
        foreignSchema: String?,
        foreignTable: String?
                                  ): ResultSet {
        TODO("not implemented")
    }

    override fun ownDeletesAreVisible(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun othersUpdatesAreVisible(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun supportsStatementPooling(): Boolean {
        TODO("not implemented")
    }

    override fun storesLowerCaseIdentifiers(): Boolean {
        TODO("not implemented")
    }

    override fun supportsCatalogsInIndexDefinitions(): Boolean {
        TODO("not implemented")
    }

    override fun ownUpdatesAreVisible(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun getUDTs(
        catalog: String?,
        schemaPattern: String?,
        typeNamePattern: String?,
        types: IntArray?
                        ): ResultSet {
        TODO("not implemented")
    }

    override fun getStringFunctions(): String {
        TODO("not implemented")
    }

    override fun getMaxColumnsInTable(): Int {
        TODO("not implemented")
    }

    override fun supportsColumnAliasing(): Boolean {
        TODO("not implemented")
    }

    override fun supportsSchemasInProcedureCalls(): Boolean {
        TODO("not implemented")
    }

    override fun getClientInfoProperties(): ResultSet {
        TODO("not implemented")
    }

    override fun usesLocalFilePerTable(): Boolean {
        TODO("not implemented")
    }

    override fun getIdentifierQuoteString(): String {
        TODO("not implemented")
    }

    override fun supportsFullOuterJoins(): Boolean {
        TODO("not implemented")
    }

    override fun supportsOrderByUnrelated(): Boolean {
        TODO("not implemented")
    }

    override fun supportsSchemasInTableDefinitions(): Boolean {
        TODO("not implemented")
    }

    override fun supportsCatalogsInProcedureCalls(): Boolean {
        TODO("not implemented")
    }

    override fun getUserName(): String {
        TODO("not implemented")
    }

    override fun getBestRowIdentifier(
        catalog: String?,
        schema: String?,
        table: String?,
        scope: Int,
        nullable: Boolean
                                     ): ResultSet {
        TODO("not implemented")
    }

    override fun supportsTableCorrelationNames(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxIndexLength(): Int {
        TODO("not implemented")
    }

    override fun supportsSubqueriesInExists(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxSchemaNameLength(): Int {
        TODO("not implemented")
    }

    override fun supportsANSI92EntryLevelSQL(): Boolean {
        TODO("not implemented")
    }

    override fun getDriverVersion(): String {
        TODO("not implemented")
    }

    override fun getPseudoColumns(
        catalog: String?,
        schemaPattern: String?,
        tableNamePattern: String?,
        columnNamePattern: String?
                                 ): ResultSet {
        TODO("not implemented")
    }

    override fun supportsMixedCaseQuotedIdentifiers(): Boolean {
        TODO("not implemented")
    }

    override fun getProcedures(catalog: String?, schemaPattern: String?, procedureNamePattern: String?): ResultSet {
        TODO("not implemented")
    }

    override fun getDriverMajorVersion(): Int {
        TODO("not implemented")
    }

    override fun supportsANSI92FullSQL(): Boolean {
        TODO("not implemented")
    }

    override fun supportsAlterTableWithAddColumn(): Boolean {
        TODO("not implemented")
    }

    override fun supportsResultSetHoldability(holdability: Int): Boolean {
        TODO("not implemented")
    }

    override fun getColumnPrivileges(
        catalog: String?,
        schema: String?,
        table: String?,
        columnNamePattern: String?
                                    ): ResultSet {
        TODO("not implemented")
    }

    override fun getImportedKeys(catalog: String?, schema: String?, table: String?): ResultSet {
        TODO("not implemented")
    }

    override fun supportsUnionAll(): Boolean {
        TODO("not implemented")
    }

    override fun getRowIdLifetime(): RowIdLifetime {
        TODO("not implemented")
    }

    override fun getDriverName(): String {
        TODO("not implemented")
    }

    override fun doesMaxRowSizeIncludeBlobs(): Boolean {
        TODO("not implemented")
    }

    override fun supportsGroupByUnrelated(): Boolean {
        TODO("not implemented")
    }

    override fun getIndexInfo(
        catalog: String?,
        schema: String?,
        table: String?,
        unique: Boolean,
        approximate: Boolean
                             ): ResultSet {
        TODO("not implemented")
    }

    override fun supportsSubqueriesInIns(): Boolean {
        TODO("not implemented")
    }

    override fun supportsStoredProcedures(): Boolean {
        TODO("not implemented")
    }

    override fun getExportedKeys(catalog: String?, schema: String?, table: String?): ResultSet {
        TODO("not implemented")
    }

    override fun supportsPositionedDelete(): Boolean {
        TODO("not implemented")
    }

    override fun supportsAlterTableWithDropColumn(): Boolean {
        TODO("not implemented")
    }

    override fun supportsExpressionsInOrderBy(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxCatalogNameLength(): Int {
        TODO("not implemented")
    }

    override fun supportsExtendedSQLGrammar(): Boolean {
        TODO("not implemented")
    }

    override fun othersInsertsAreVisible(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun updatesAreDetected(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun supportsDataManipulationTransactionsOnly(): Boolean {
        TODO("not implemented")
    }

    override fun supportsSubqueriesInComparisons(): Boolean {
        TODO("not implemented")
    }

    override fun supportsSavepoints(): Boolean {
        TODO("not implemented")
    }

    override fun getSQLKeywords(): String {
        TODO("not implemented")
    }

    override fun getMaxColumnNameLength(): Int {
        TODO("not implemented")
    }

    override fun nullPlusNonNullIsNull(): Boolean {
        TODO("not implemented")
    }

    override fun supportsGroupByBeyondSelect(): Boolean {
        TODO("not implemented")
    }

    override fun supportsCatalogsInPrivilegeDefinitions(): Boolean {
        TODO("not implemented")
    }

    override fun allProceduresAreCallable(): Boolean {
        TODO("not implemented")
    }

    override fun getSuperTables(catalog: String?, schemaPattern: String?, tableNamePattern: String?): ResultSet {
        TODO("not implemented")
    }

    override fun generatedKeyAlwaysReturned(): Boolean {
        TODO("not implemented")
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean {
        TODO("not implemented")
    }

    override fun storesUpperCaseQuotedIdentifiers(): Boolean {
        TODO("not implemented")
    }

    override fun getMaxCharLiteralLength(): Int {
        TODO("not implemented")
    }

    override fun othersDeletesAreVisible(type: Int): Boolean {
        TODO("not implemented")
    }

    override fun supportsNonNullableColumns(): Boolean {
        TODO("not implemented")
    }

    override fun supportsUnion(): Boolean {
        TODO("not implemented")
    }

    override fun supportsDifferentTableCorrelationNames(): Boolean {
        TODO("not implemented")
    }

    override fun supportsSchemasInPrivilegeDefinitions(): Boolean {
        TODO("not implemented")
    }

    override fun supportsSelectForUpdate(): Boolean {
        TODO("not implemented")
    }

    override fun supportsMultipleTransactions(): Boolean {
        TODO("not implemented")
    }

    override fun storesMixedCaseQuotedIdentifiers(): Boolean {
        TODO("not implemented")
    }

    override fun supportsOpenCursorsAcrossCommit(): Boolean {
        TODO("not implemented")
    }

    override fun storesMixedCaseIdentifiers(): Boolean {
        TODO("not implemented")
    }

    override fun getTablePrivileges(catalog: String?, schemaPattern: String?, tableNamePattern: String?): ResultSet {
        TODO("not implemented")
    }

    override fun supportsBatchUpdates(): Boolean {
        TODO("not implemented")
    }

    override fun getResultSetHoldability(): Int {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "<metadata>"
    }
}

private fun Array<String>.dataArray(vararg data: Pair<String, Any?>): Array<Any?> {
    return Array(size) { idx ->
        val colName = get(idx)
        data.firstOrNull { it.first == colName }?.second ?: null
    }
}
