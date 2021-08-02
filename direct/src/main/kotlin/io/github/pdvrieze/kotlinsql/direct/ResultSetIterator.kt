/*
 * Copyright (c) 2020.
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

package io.github.pdvrieze.kotlinsql.direct

import java.sql.ResultSet

class ResultSetIterator(private val resultSet: ResultSet) : ListIterator<ResultSet> {
    override fun hasNext(): Boolean {
        return !(resultSet.isAfterLast || resultSet.isLast)
    }

    override fun next(): ResultSet {
        if (!resultSet.next()) {
            throw NoSuchElementException()
        }
        return resultSet
    }

    override fun hasPrevious(): Boolean {
        return !(resultSet.isBeforeFirst || resultSet.isFirst)
    }

    override fun previous(): ResultSet {
        if (!resultSet.previous()) {
            throw NoSuchElementException()
        }
        return resultSet
    }

    override fun nextIndex(): Int {
        return resultSet.row
    }

    override fun previousIndex(): Int {
        return resultSet.row - 2
    }
}