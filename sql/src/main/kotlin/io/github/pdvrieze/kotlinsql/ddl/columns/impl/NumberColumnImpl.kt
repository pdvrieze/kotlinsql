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

package io.github.pdvrieze.kotlinsql.ddl.columns.impl

import io.github.pdvrieze.kotlinsql.ddl.Table
import io.github.pdvrieze.kotlinsql.ddl.TableRef
import io.github.pdvrieze.kotlinsql.ddl.columns.*

internal class NumberColumnImpl<T : Any, S : NumericColumnType<T, S>>(
    table: TableRef,
    name: String,
    configuration: NumberColumnConfiguration<T, S>
) : ColumnImpl<T, S, NumericColumn<T, S>>(
    table = table,
    type = configuration.type,
    name = name,
    notnull = configuration.notnull,
    unique = configuration.unique,
    autoincrement = configuration.autoincrement,
    default = configuration.default,
    comment = configuration.comment,
    columnFormat = configuration.columnFormat,
    storageFormat = configuration.storageFormat,
    references = configuration.references,
    unsigned = configuration.unsigned,
    zerofill = configuration.zerofill,
    displayLength = configuration.displayLength
), NumericColumn<T, S> {

    override fun copyConfiguration(newName: String?, owner: Table) =
        NumberColumnConfiguration(type, newName ?: name)

}