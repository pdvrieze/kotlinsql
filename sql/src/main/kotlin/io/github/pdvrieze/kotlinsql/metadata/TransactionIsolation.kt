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

package io.github.pdvrieze.kotlinsql.metadata

import java.sql.Connection

enum class TransactionIsolation constructor(val intValue: Int, @Suppress("UNUSED_PARAMETER") dummy: Unit) {
    TRANSACTION_NONE(Connection.TRANSACTION_NONE, Unit),
    TRANSACTION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED, Unit),
    TRANSACTION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED, Unit),
    TRANSACTION_REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ, Unit),
    TRANSACTION_SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE, Unit),
}