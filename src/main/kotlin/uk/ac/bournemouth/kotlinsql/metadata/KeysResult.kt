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

package uk.ac.bournemouth.kotlinsql.metadata

import uk.ac.bournemouth.kotlinsql.metadata.values.KeyDeferrability
import uk.ac.bournemouth.kotlinsql.metadata.values.KeyRule
import java.sql.ResultSet

@Suppress("unused")
class KeysResult(resultSet: ResultSet) : AbstractMetadataResultSet(resultSet) {
    private val idxPkTableCat by lazyColIdx("PKTABLE_CAT")
    private val idxPkTableSchem by lazyColIdx("PKTABLE_SCHEM")
    private val idxPkTableName by lazyColIdx("PKTABLE_NAME")
    private val idxPkColName by lazyColIdx("PKCOLUMN_NAME")
    private val idxFkTableCat by lazyColIdx("FKTABLE_CAT")
    private val idxFkTableSchem by lazyColIdx("FKTABLE_SCHEM")
    private val idxFkTableName by lazyColIdx("FKTABLE_NAME")
    private val idxFkColName by lazyColIdx("FKCOLUMN_NAME")
    private val idxKeySeq by lazyColIdx("KEY_SEQ")
    private val idxUpdateRule by lazyColIdx("UPDATE_RULE")
    private val idxDeleterule by lazyColIdx("DELETE_RULE")
    private val idxFkName by lazyColIdx("FK_NAME")
    private val idxPkName by lazyColIdx("PK_NAME")
    private val idxDeferrability by lazyColIdx("DEFERRABILITY")

    val pkTableCat: String? get() = resultSet.getString(idxPkTableName)
    val pkTableSchema: String? get() = resultSet.getString(idxPkTableSchem)
    val pkTableName: String get() = resultSet.getString(idxPkTableName)
    val pkColumnName: String get() = resultSet.getString(idxPkColName)
    val fkTableCat: String? get() = resultSet.getString(idxFkTableName)
    val fkTableSchema: String? get() = resultSet.getString(idxFkTableSchem)
    val fkTableName: String get() = resultSet.getString(idxFkTableName)
    val fkColumnName: String get() = resultSet.getString(idxFkColName)
    val keySeq: Short get() = resultSet.getShort(idxKeySeq)
    val updateRule: KeyRule get() = KeyRule.from(resultSet.getShort(idxUpdateRule))
    val deleteRule: KeyRule get() = KeyRule.from(resultSet.getShort(idxDeleterule))
    val fkName: String get() = resultSet.getString(idxFkName)
    val pkName: String get() = resultSet.getString(idxPkName)
    val deferrability: KeyDeferrability get() = KeyDeferrability.from(resultSet.getShort(idxDeferrability))
}