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

package uk.ac.bournemouth.kotlinsql.columns.impl

import uk.ac.bournemouth.kotlinsql.Table
import uk.ac.bournemouth.kotlinsql.TableRef
import uk.ac.bournemouth.kotlinsql.columns.*

internal class LengthCharColumnImpl<S : LengthCharColumnType<S>>
constructor(
    table: TableRef,
    name: String,
    configuration: LengthCharColumnConfiguration<S>,
) : ColumnImpl<String, S, LengthCharColumn<S>>(
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
    length = configuration.length,
    binary = configuration.binary,
    charset = configuration.charset,
    collation = configuration.collation
), LengthCharColumn<S> {

    init {
        if (length < 1) {
            throw IllegalArgumentException("Lengths must be at least 1 and specified")
        }
    }

    override fun copyConfiguration(newName: String?, owner: Table) =
        LengthCharColumnConfiguration(
            type,
            newName ?: name,
            length
        )
}