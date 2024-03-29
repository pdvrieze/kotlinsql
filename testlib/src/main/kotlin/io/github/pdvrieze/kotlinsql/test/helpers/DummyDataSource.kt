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

package io.github.pdvrieze.kotlinsql.test.helpers

import io.github.pdvrieze.jdbc.recorder.RecordingConnection
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.ddl.Table
import java.io.PrintWriter
import java.sql.Connection
import java.sql.SQLException
import java.util.logging.Logger
import javax.sql.DataSource

class DummyDataSource(val db: Database? = null): DataSource {
    private var logWriter: PrintWriter = PrintWriter(System.out)
    private val rootLogger = Logger.getAnonymousLogger()
    var tables: List<Table> = emptyList()

    var lastConnection: RecordingConnection? = null

    override fun setLogWriter(out: PrintWriter) {
        logWriter = out
    }

    override fun getParentLogger(): Logger {
        return rootLogger
    }

    override fun setLoginTimeout(seconds: Int) {
        TODO("not implemented")
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean {
        return false
    }

    override fun getLogWriter(): PrintWriter {
        return logWriter
    }

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        throw SQLException("Not implemented / relevant")
    }

    override fun getConnection(): Connection {
        val dummyCon = DummyConnection(db).also {
            if (tables.isNotEmpty()) it.tables = tables
        }
        lastConnection = RecordingConnection(dummyCon, db)
        return lastConnection!!
    }

    override fun getConnection(username: String?, password: String?): Connection {
        return getConnection()
    }

    override fun getLoginTimeout(): Int {
        TODO("not implemented")
    }

    fun setTables(vararg tables: Table) {
        this.tables =tables.toList()
    }
}