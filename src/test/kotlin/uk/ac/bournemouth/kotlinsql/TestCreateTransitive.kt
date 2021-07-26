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

import io.github.pdvrieze.jdbc.recorder.AbstractRecordingPreparedStatement
import io.github.pdvrieze.jdbc.recorder.DummyDataSource
import io.github.pdvrieze.jdbc.recorder.actions.Close
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.ac.bournemouth.kotlinsql.test.DummyConnection
import uk.ac.bournemouth.util.kotlin.sql.impl.gen.VALUES
import uk.ac.bournemouth.util.kotlin.sql.impl.invoke
import uk.ac.bournemouth.util.kotlin.sql.impl.map

class TestCreateTransitive {

    @Test
    fun testFakeQuery() {
        val source = DummyDataSource(WebAuthDB)
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
            Close,
            AbstractRecordingPreparedStatement.Close(q),
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
    fun testInsert() {
        val source = DummyDataSource(WebAuthDB)
        val insertCount: Int = WebAuthDB(source) {
            INSERT(users.user, users.fullname, users.alias)
                .VALUES("jdoe", "John Doe", "John")
                .VALUES("janie", "Jane Doe", "Jane")
                .commit()
        }

        val c = source.lastConnection!!
        assertEquals(2,insertCount)

        val q = "INSERT INTO `users` (`user`, `fullname`, `alias`) VALUES (?, ?, ?)"
        val psstr = "DummyPreparedStatement(\"$q\")"
        val expectedActions = listOf(
            DummyConnection.SetAutoCommit.FALSE,
            c.DummyPreparedStatement(q),
            DummyConnection.StringAction("$psstr.setString(1, \"jdoe\")"),
            DummyConnection.StringAction("$psstr.setString(2, \"John Doe\")"),
            DummyConnection.StringAction("$psstr.setString(3, \"John\")"),
            DummyConnection.StringAction("$psstr.addBatch()"),
            DummyConnection.StringAction("$psstr.setString(1, \"janie\")"),
            DummyConnection.StringAction("$psstr.setString(2, \"Jane Doe\")"),
            DummyConnection.StringAction("$psstr.setString(3, \"Jane\")"),
            DummyConnection.StringAction("$psstr.addBatch()"),
            DummyConnection.StringAction("$psstr.executeBatch() -> [1, 1]"),
            AbstractRecordingPreparedStatement.Close(q),
            DummyConnection.Commit,
            DummyConnection.StringAction("Connection.close()")
                                    )

        val filteredActions = c.getFilteredActions()

        assertEquals(expectedActions, filteredActions)

    }

    @Test
    fun testCreateTransitive() {
        val source = DummyDataSource()
        source.setTables(WebAuthDB.roles)
        WebAuthDB(source) {
            ensureTables()
        }
        val actualActions = source.lastConnection!!.getFilteredActions()

        val expectedActions = listOf<DummyConnection.Action>(
            DummyConnection.SetAutoCommit.FALSE,
            DummyConnection.Commit,
            DummyConnection.StringAction("Connection.close()")
                                                            )
        assertEquals(expectedActions, actualActions)
    }

    companion object {

        private fun DummyConnection.getFilteredActions(): List<DummyConnection.Action> {
            val filterText = listOf("getAutoCommit()", "isAfterLast()", "isLast()")
            return actions.filter { action ->
                action !is DummyConnection.StringAction ||
                        filterText.none { t -> t in action.string }
            }
        }

    }
}