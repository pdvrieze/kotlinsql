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

package io.github.pdvrieze.kotlinsql.metadata.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.metadata.KeysResult
import io.github.pdvrieze.kotlinsql.metadata.values.KeyDeferrability
import io.github.pdvrieze.kotlinsql.metadata.values.KeyRule
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
internal class KeysResultImpl @UnmanagedSql constructor(resultSet: ResultSet) :
    AbstractMetadataResultSet<KeysResult, KeysResult.Data>(resultSet),
    KeysResult {

    private val idxDeleterule by lazyColIdx("DELETE_RULE")
    private val idxDeferrability by lazyColIdx("DEFERRABILITY")

    private val idxFkColName by lazyColIdx("FKCOLUMN_NAME")
    private val idxFkName by lazyColIdx("FK_NAME")
    private val idxFkTableCat by lazyColIdx("FKTABLE_CAT")
    private val idxFkTableName by lazyColIdx("FKTABLE_NAME")
    private val idxFkTableSchem by lazyColIdx("FKTABLE_SCHEM")

    private val idxKeySeq by lazyColIdx("KEY_SEQ")

    private val idxPkColName by lazyColIdx("PKCOLUMN_NAME")
    private val idxPkName by lazyColIdx("PK_NAME")
    private val idxPkTableCat by lazyColIdx("PKTABLE_CAT")
    private val idxPkTableName by lazyColIdx("PKTABLE_NAME")
    private val idxPkTableSchem by lazyColIdx("PKTABLE_SCHEM")

    private val idxUpdateRule by lazyColIdx("UPDATE_RULE")

    override val deferrability: KeyDeferrability get() = KeyDeferrability.from(resultSet.getShort(idxDeferrability))
    override val deleteRule: KeyRule get() = KeyRule.from(resultSet.getShort(idxDeleterule))

    override val fkColumnName: String get() = resultSet.getString(idxFkColName)
    override val fkName: String get() = resultSet.getString(idxFkName)
    override val fkTableCat: String? get() = resultSet.getString(idxFkTableName)
    override val fkTableName: String get() = resultSet.getString(idxFkTableName)
    override val fkTableSchema: String? get() = resultSet.getString(idxFkTableSchem)

    override val keySeq: Short get() = resultSet.getShort(idxKeySeq)

    override val pkColumnName: String get() = resultSet.getString(idxPkColName)
    override val pkName: String get() = resultSet.getString(idxPkName)
    override val pkTableCat: String? get() = resultSet.getString(idxPkTableName)
    override val pkTableName: String get() = resultSet.getString(idxPkTableName)
    override val pkTableSchema: String? get() = resultSet.getString(idxPkTableSchem)

    override val updateRule: KeyRule get() = KeyRule.from(resultSet.getShort(idxUpdateRule))

    override fun toList(): List<KeysResult.Data> = toListImpl { KeysResult.Data(it) }

}