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

import java.sql.Savepoint

open class Rollback(val savePoint: String?) : Action {
    constructor(savePoint: Savepoint): this(savePoint.savepointName)

    companion object: Rollback(null)

    override fun toString(): String {
        return "Rollback(savePoint=$savePoint)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rollback) return false

        if (savePoint != other.savePoint) return false

        return true
    }

    override fun hashCode(): Int {
        return savePoint?.hashCode() ?: 0
    }

}