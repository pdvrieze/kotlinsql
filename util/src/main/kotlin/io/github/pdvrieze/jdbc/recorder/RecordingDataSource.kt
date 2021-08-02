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

package io.github.pdvrieze.jdbc.recorder

import io.github.pdvrieze.kotlinsql.ddl.Database
import java.io.PrintWriter
import java.sql.Connection
import java.util.logging.Logger
import javax.sql.DataSource

class RecordingDataSource(val delegate: DataSource, val db: Database? = null): DataSource {
    private var logWriter: PrintWriter = PrintWriter(System.out)
    private val rootLogger = Logger.getAnonymousLogger()

    override fun setLogWriter(out: PrintWriter) {
        delegate.logWriter = out
    }

    override fun getParentLogger(): Logger {
        return delegate.parentLogger
    }

    override fun setLoginTimeout(seconds: Int) {
        delegate.loginTimeout = seconds
    }

    final override fun isWrapperFor(iface: Class<*>): Boolean {
        return iface.isInstance(delegate) || delegate.isWrapperFor(iface)
    }

    final override fun <T : Any?> unwrap(iface: Class<T>): T = when {
        iface.isInstance(delegate) -> iface.cast(delegate)
        else                       -> delegate.unwrap(iface)
    }

    override fun getLogWriter(): PrintWriter {
        return logWriter
    }

    override fun getConnection(): Connection {
        return RecordingConnection(delegate.connection, db)
    }

    override fun getConnection(username: String?, password: String?): Connection {
        return RecordingConnection(delegate.getConnection(username, password), db)
    }

    override fun getLoginTimeout(): Int {
        return delegate.loginTimeout
    }
}