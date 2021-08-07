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

import io.github.pdvrieze.kotlinsql.dml.ResultSetRow

interface BaseRowResult<D: BaseRowResult.Data<D>>: ResultSetRow<D> {

    val columnName: String
    val dataType: String
    val typeName: String
    val precision: String

    @Deprecated("Use precision as the column name, this is an alias", ReplaceWith("precision"))
    val columnSize: String get() = precision
    val decimalDigits: Short
    val pseudoColumn: PseudoColumn

    enum class PseudoColumn {
        BESTROWUNKNOWN,
        BESTROWNOTPSEUDO,
        BESTROWPSEUDO
    }

    abstract class Data<D: Data<D>>(data: BaseRowResult<*>): BaseRowResult<D> {
        override val columnName: String = data.columnName
        override val dataType: String = data.dataType
        override val typeName: String = data.typeName
        override val precision: String = data.precision
        override val decimalDigits: Short = data.decimalDigits
        override val pseudoColumn: PseudoColumn = data.pseudoColumn
    }

}
