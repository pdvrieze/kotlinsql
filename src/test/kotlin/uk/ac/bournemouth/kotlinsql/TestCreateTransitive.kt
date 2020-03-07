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

package uk.ac.bournemouth.kotlinsql

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.ac.bournemouth.kotlinsql.test.AbstractDummyPreparedStatement
import uk.ac.bournemouth.kotlinsql.test.AbstractDummyResultSet
import uk.ac.bournemouth.kotlinsql.test.DummyConnection
import uk.ac.bournemouth.kotlinsql.test.DummyDataSource
import uk.ac.bournemouth.util.kotlin.sql.impl.map
import uk.ac.bournemouth.util.kotlin.sql.impl.invoke

class TestCreateTransitive {

    @Test
    fun testFakeQuery() {
        val source = DummyDataSource()
        val names = WebAuthDB(source) {
            SELECT(users.fullname)
                .map { seq -> seq.toList() }
                .commit()
        }

        val c = source.lastConnection!!
        val q = "SELECT `fullname` FROM `users`"
        val expectedActions = listOf(
            DummyConnection.SetAutoCommit.FALSE,
//            DummyConnection.DummySavePoint(1),
            c.DummyPreparedStatement(q),
            c.DummyResultSet(q),
            AbstractDummyResultSet.Close,
            AbstractDummyPreparedStatement.Close(q),
//            DummyConnection.ReleaseSavepoint(1),
            DummyConnection.Commit,
            DummyConnection.StringAction("Connection.close()")
                                    )

        val filterText = listOf("getAutoCommit()", "isAfterLast()", "isLast()")
        val filteredActions =
            c.actions.filter { action ->
                action !is DummyConnection.StringAction ||
                        filterText.none { t -> t in action.string }
            }
        assertEquals(expectedActions, filteredActions)
    }

    @Test
    fun testCreateTransitive() {
        val source = DummyDataSource()
        val x = WebAuthDB(source) {
            ensureTables()
        }
    }
}