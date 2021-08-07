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
import io.github.pdvrieze.kotlinsql.ddl.IColumnType
import io.github.pdvrieze.kotlinsql.dml.ResultSetRow
import io.github.pdvrieze.kotlinsql.metadata.impl.AttributeResultsImpl
import io.github.pdvrieze.kotlinsql.metadata.impl.DataResultsImpl
import io.github.pdvrieze.kotlinsql.metadata.values.Nullable
import java.sql.ResultSet

interface DataResults<D: DataResults.Data<D>> : ResultSetRow<D> {
    val charOctetLength: Int
    val dataType: IColumnType<*, *, *>
    val isNullable: Boolean?
    val nullable: Nullable
    val ordinalPosition: Int
    val remarks: String?
    val typeName: String

    abstract class Data<D: Data<D>>(results: DataResults<*>): DataResults<D> {
        override val charOctetLength: Int = results.charOctetLength
        override val dataType: IColumnType<*, *, *> = results.dataType
        override val isNullable: Boolean? = results.isNullable
        override val nullable: Nullable = results.nullable
        override val ordinalPosition: Int = results.ordinalPosition
        override val remarks: String? = results.remarks
        override val typeName: String = results.typeName
    }
}

