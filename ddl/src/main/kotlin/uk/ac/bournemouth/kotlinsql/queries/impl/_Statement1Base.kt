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

package uk.ac.bournemouth.kotlinsql.queries.impl

import uk.ac.bournemouth.kotlinsql.Column
import uk.ac.bournemouth.kotlinsql.IColumnType
import uk.ac.bournemouth.kotlinsql.queries.Select1
import uk.ac.bournemouth.kotlinsql.queries.WhereClause

abstract class _Statement1Base<T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>>(
    override val select: _Select1<T1, S1, C1>,
    where: WhereClause,
) : _StatementBase(where), Select1<T1, S1, C1> {

    /**
     * Get a single (optional) result
     */

}