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

@file:Suppress("unused")

package io.github.pdvrieze.kotlinsql.direct

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

/**
 * Executes the given [block] function on this resource and then closes it down correctly whether an exception
 * is thrown or not.
 *
 * @param block a function to process this closable resource.
 * @return the result of [block] function on this closable resource.
 */
inline fun <T : Connection, R> T.use(block: (T) -> R) = useHelper({ it.close() }, block)

@Suppress("unused")
inline fun <T : Connection, R> T.useTransacted(block: (T) -> R): R = useHelper({ it.close() }) {
    it.autoCommit = false
    try {
        val result = block(it)
        it.commit()
        return result
    } catch (e: Exception) {
        it.rollback()
        throw e
    }

}

@PublishedApi
internal inline fun <T, R> T.useHelper(close: (T) -> Unit, block: (T) -> R): R {
    var closed = false
    try {
        return block(this)
    } catch (e: Exception) {
        closed = true
        try {
            close(this)
        } catch (closeException: Exception) {
            // drop for now.
        }
        throw e
    } finally {
        if (!closed) {
            close(this)
        }
    }
}

inline fun <T : Statement, R> T.use(block: (T) -> R) = useHelper({ it.close() }, block)
inline fun <T : ResultSet, R> T.use(block: (T) -> R) = useHelper({ it.close() }, block)
