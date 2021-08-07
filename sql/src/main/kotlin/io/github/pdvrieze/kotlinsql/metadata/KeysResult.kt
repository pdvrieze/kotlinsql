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

package io.github.pdvrieze.kotlinsql.metadata

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.dml.ResultSetWrapper
import io.github.pdvrieze.kotlinsql.metadata.impl.AttributeResultsImpl
import io.github.pdvrieze.kotlinsql.metadata.impl.KeysResultImpl
import io.github.pdvrieze.kotlinsql.metadata.values.KeyDeferrability
import io.github.pdvrieze.kotlinsql.metadata.values.KeyRule
import java.sql.ResultSet

interface KeysResult : ResultSetRow<KeysResult.Data> {
    val deferrability: KeyDeferrability
    val deleteRule: KeyRule
    val fkColumnName: String
    val fkName: String
    val fkTableCat: String?
    val fkTableName: String
    val fkTableSchema: String?
    val keySeq: Short
    val pkColumnName: String
    val pkName: String
    val pkTableCat: String?
    val pkTableName: String
    val pkTableSchema: String?
    val updateRule: KeyRule

    override fun data(): Data = Data(this)

    class Data(data: KeysResult) : KeysResult {
        override val deferrability: KeyDeferrability = data.deferrability
        override val deleteRule: KeyRule = data.deleteRule
        override val fkColumnName: String = data.fkColumnName
        override val fkName: String = data.fkName
        override val fkTableCat: String? = data.fkTableCat
        override val fkTableName: String = data.fkTableName
        override val fkTableSchema: String? = data.fkTableSchema
        override val keySeq: Short = data.keySeq
        override val pkColumnName: String = data.pkColumnName
        override val pkName: String = data.pkName
        override val pkTableCat: String? = data.pkTableCat
        override val pkTableName: String = data.pkTableName
        override val pkTableSchema: String? = data.pkTableSchema
        override val updateRule: KeyRule = data.updateRule

        override fun data(): Data = this
    }

    companion object {
        @UnmanagedSql
        operator fun invoke(r: ResultSet): ResultSetWrapper<KeysResult, Data> = KeysResultImpl(r)
    }
}
