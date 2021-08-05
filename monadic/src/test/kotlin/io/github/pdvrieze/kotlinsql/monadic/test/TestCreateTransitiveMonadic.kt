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

package io.github.pdvrieze.kotlinsql.monadic.test

import io.github.pdvrieze.jdbc.recorder.RecordingConnection
import io.github.pdvrieze.jdbc.recorder.RecordingConnection.RecordingPreparedStatement
import io.github.pdvrieze.jdbc.recorder.actions.*
import io.github.pdvrieze.kotlinsql.direct.test.users
import io.github.pdvrieze.kotlinsql.monadic.*
import io.github.pdvrieze.kotlinsql.monadic.invoke
import io.github.pdvrieze.kotlinsql.test.helpers.DummyConnection
import io.github.pdvrieze.kotlinsql.test.helpers.DummyConnection.DummyPreparedStatement
import io.github.pdvrieze.kotlinsql.test.helpers.DummyDataSource
import io.github.pdvrieze.kotlinsql.test.helpers.TestActions
import io.github.pdvrieze.kotlinsql.test.helpers.WebAuthDB
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.sql.Wrapper
import java.util.stream.Collectors.toList

class TestCreateTransitiveMonadic {

    @Test
    fun testFakeQuery() {
        val source = DummyDataSource(WebAuthDB)
        val names:List<String> = WebAuthDB(source) {
            SELECT(users.fullname).transform().commit()
        }
        val expectedNames= arrayOf("Joe Blogs")

        assertArrayEquals(expectedNames, names.toTypedArray())

        val c = source.lastConnection!!
        val dc = c.unwrap<DummyConnection>()
        val q = "SELECT `fullname` FROM `users`"
        val expectedActions = listOf(
            SetAutoCommit(false),
            dc.DummyPreparedStatement(q),
            dc.DummyResultSet(q),
            StringAction("""ResultSet().next() -> true"""),
            StringAction("""ResultSet().getString(1) -> "Joe Blogs""""),
            ResultSetClose,
            StatementClose(q),
            Commit,
            ConnectionClose
        )

        val filterText = listOf("getAutoCommit()", "isAfterLast()", "isLast()")
        val filteredActions = c.getFilteredActions()
        c.actions.filter { action ->
            action !is StringAction ||
                    filterText.none { t -> t in action.string }
        }
        assertEquals(expectedActions.joinToString(",\n"), filteredActions.joinToString(",\n"))
        assertArrayEquals(expectedActions.toTypedArray(), filteredActions.toTypedArray())
    }

    @Test
    fun testInsert() {
        val source = DummyDataSource(WebAuthDB)
        val insertCount: IntArray = WebAuthDB(source) {
//            transaction {
                INSERT(users.user, users.fullname, users.alias)
                    .VALUES("jdoe", "John Doe", "John")
                    .VALUES("janie", "Jane Doe", "Jane")
                    .commit()
//            }
        }

        val c = source.lastConnection!!
        val dc = c.unwrap(DummyConnection::class.java)
        assertEquals(2, insertCount.size)

        val q = "INSERT INTO `users` (`user`, `fullname`, `alias`) VALUES (?, ?, ?)"
        val psstr = "RecordingPreparedStatement(\"$q\")"
        val expectedActions = listOf(
            SetAutoCommit.FALSE,
            dc.DummyPreparedStatement(q),
            StringAction("$psstr.setString(1, \"jdoe\")"),
            StringAction("$psstr.setString(2, \"John Doe\")"),
            StringAction("$psstr.setString(3, \"John\")"),
            StringAction("$psstr.addBatch()"),
            StringAction("$psstr.setString(1, \"janie\")"),
            StringAction("$psstr.setString(2, \"Jane Doe\")"),
            StringAction("$psstr.setString(3, \"Jane\")"),
            StringAction("$psstr.addBatch()"),
            StringAction("$psstr.executeBatch() -> [1, 1]"),
            StatementClose(q),
            Commit,
            ConnectionClose
        )

        val filteredActions = c.getFilteredActions()

        assertEquals(expectedActions.joinToString(",\n"), filteredActions.joinToString(",\n"))
        assertArrayEquals(expectedActions.toTypedArray(), filteredActions.toTypedArray())

    }

    @Test
    fun testCreateTransitive() {
        val source = DummyDataSource()
        source.setTables(WebAuthDB.roles)
        val tableNames = WebAuthDB(source) {
            metadata.getTables().mapEach { it.tableName }
                .commit()
        }
        assertEquals(listOf("roles"), tableNames)
        WebAuthDB(source) {
            ensureTables()
                .commit()
        }
        val filteredActions = source.lastConnection!!.getFilteredActions()

        val expectedActions = listOf<Action>(
            SetAutoCommit.FALSE,
            StringAction("""RecordingConnection(null).getMetaData() -> <metadata>"""),
            StringAction("""<metadata>.getTables(null, null, null, ["TABLE"]) -> ResultSet(getTables())"""),
            StringAction("""ResultSet(getTables()).next() -> true"""),
            StringAction("""ResultSet(getTables()).findColumn("TABLE_NAME") -> 3"""),
            StringAction("""ResultSet(getTables()).getString(3) -> "roles""""),
            StringAction("""ResultSet(getTables()).next() -> false"""),
            ResultSetClose,
            StringAction("""RecordingConnection(null).getMetaData() -> <metadata>"""),
            StringAction("""<metadata>.getColumns(null, null, "roles", null) -> ResultSet(metadata.getColumns())"""),
            StringAction("""ResultSet(metadata.getColumns()).next() -> true"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("CHAR_OCTET_LENGTH") -> 16"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(16) -> 0"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("DATA_TYPE") -> 5"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(5) -> 12"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("IS_NULLABLE") -> 18"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(18) -> "NO""""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("NULLABLE") -> 11"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(11) -> 1"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("ORDINAL_POSITION") -> 17"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(17) -> 0"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("REMARKS") -> 12"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(12) -> null"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("TYPE_NAME") -> 6"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(6) -> "VARCHAR""""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("COLUMN_DEF") -> 13"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(13) -> null"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("COLUMN_NAME") -> 4"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(4) -> "role""""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("COLUMN_SIZE") -> 7"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(7) -> 30"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("DECIMAL_DIGITS") -> 9"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(9) -> 0"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("IS_AUTOINCREMENT") -> 23"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(23) -> "NO""""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("IS_GENERATEDCOLUMN") -> 24"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(24) -> "NO""""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("NUM_PREC_RADIX") -> 10"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(10) -> 0"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("TABLE_CAT") -> 1"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(1) -> null"""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("TABLE_NAME") -> 3"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(3) -> "roles""""),
            StringAction("""ResultSet(metadata.getColumns()).findColumn("TABLE_SCHEM") -> 2"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(2) -> null"""),
            StringAction("""ResultSet(metadata.getColumns()).next() -> true"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(16) -> 0"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(5) -> 12"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(18) -> "NO""""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(11) -> 1"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(17) -> 0"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(12) -> null"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(6) -> "VARCHAR""""),
            StringAction("""ResultSet(metadata.getColumns()).getString(13) -> null"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(4) -> "description""""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(7) -> 120"""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(9) -> 0"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(23) -> "NO""""),
            StringAction("""ResultSet(metadata.getColumns()).getString(24) -> "NO""""),
            StringAction("""ResultSet(metadata.getColumns()).getInt(10) -> 0"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(1) -> null"""),
            StringAction("""ResultSet(metadata.getColumns()).getString(3) -> "roles""""),
            StringAction("""ResultSet(metadata.getColumns()).getString(2) -> null"""),
            StringAction("""ResultSet(metadata.getColumns()).next() -> false"""),
            ResultSetClose,

            StringAction("""RecordingConnection(null).getMetaData() -> <metadata>"""),
            StringAction("""<metadata>.getTables(null, null, "users", null) -> ResultSet(getTables())"""),
            StringAction("""ResultSet(getTables()).next() -> false"""),
            ResultSetClose,
            TestActions.CREATE_USERS,

            StringAction("""RecordingConnection(null).getMetaData() -> <metadata>"""),
            StringAction("""<metadata>.getTables(null, null, "user_roles", null) -> ResultSet(getTables())"""),
            StringAction("""ResultSet(getTables()).next() -> false"""),
            ResultSetClose,
            TestActions.CREATE_USER_ROLES,

            StringAction("""RecordingConnection(null).getMetaData() -> <metadata>"""),
            StringAction("""<metadata>.getTables(null, null, "pubkeys", null) -> ResultSet(getTables())"""),
            StringAction("""ResultSet(getTables()).next() -> false"""),
            ResultSetClose,
            TestActions.CREATE_PUBKEYS,

            StringAction("""RecordingConnection(null).getMetaData() -> <metadata>"""),
            StringAction("""<metadata>.getTables(null, null, "tokens", null) -> ResultSet(getTables())"""),
            StringAction("""ResultSet(getTables()).next() -> false"""),
            ResultSetClose,
            TestActions.CREATE_TOKENS,

            StringAction("""RecordingConnection(null).getMetaData() -> <metadata>"""),
            StringAction("""<metadata>.getTables(null, null, "app_perms", null) -> ResultSet(getTables())"""),
            StringAction("""ResultSet(getTables()).next() -> false"""),
            ResultSetClose,
            TestActions.CREATE_APP_PERMS,

            StringAction("""RecordingConnection(null).getMetaData() -> <metadata>"""),
            StringAction("""<metadata>.getTables(null, null, "challenges", null) -> ResultSet(getTables())"""),
            StringAction("""ResultSet(getTables()).next() -> false"""),
            ResultSetClose,
            TestActions.CREATE_CHALLENGES,

            Commit,
            ConnectionClose
        )
        assertEquals(expectedActions.joinToString(",\n"), filteredActions.joinToString(",\n"))
        assertArrayEquals(expectedActions.toTypedArray(), filteredActions.toTypedArray())
//        assertEquals(expectedActions, actualActions)
    }

    companion object {

        private fun RecordingConnection.getFilteredActions(
            filterText: List<String> = listOf("getAutoCommit()", "isAfterLast()", "isLast()"),
        ): List<Action> {
            return actions.filter { action ->
                when {
                    action is StringAction && filterText.any { it in action.string } -> false

                    action is RecordingPreparedStatement &&
                            action.unwrap<DummyPreparedStatement>().sql.startsWith("CREATE TABLE") -> false

                    action is StatementClose &&
                            action.query?.startsWith("CREATE TABLE") == true -> false

                    else -> true
                }
            }.map {
                when {
                    it is Wrapper && it.isWrapperFor(Action::class.java)
                         -> it.unwrap(Action::class.java)

                    else -> it
                }
            }
        }

    }
}

inline fun <reified T> Wrapper.unwrap(): T = unwrap(T::class.java)