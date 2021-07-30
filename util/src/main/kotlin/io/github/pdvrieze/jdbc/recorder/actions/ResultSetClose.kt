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

package io.github.pdvrieze.jdbc.recorder.actions

import io.github.pdvrieze.jdbc.recorder.AbstractRecordingStatement
import io.github.pdvrieze.jdbc.recorder.escape

object ResultSetClose : Action {
    override fun toString(): String = "ResultSetClose"
}

object ConnectionClose: Action {
    override fun toString(): String = "Connection.close()"
}

class StatementClose(val query: String?): Action {
    override fun toString(): String = "Statement(${query?.escape() ?: ""}).close()"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StatementClose) return false

        if (query != other.query) return false

        return true
    }

    override fun hashCode(): Int {
        return query?.hashCode() ?: 0
    }
}
