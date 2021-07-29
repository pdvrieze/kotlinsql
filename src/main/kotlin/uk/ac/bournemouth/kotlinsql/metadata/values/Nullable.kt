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

package uk.ac.bournemouth.kotlinsql.metadata.values

import java.sql.DatabaseMetaData

enum class Nullable(val sqlValue: Short) {
    NO_NULLS(DatabaseMetaData.attributeNoNulls),
    NULLABLE(DatabaseMetaData.attributeNullable),
    NULLABLE_UNKNOWN(DatabaseMetaData.attributeNullableUnknown);

    companion object {
        @JvmStatic
        fun from(value: Short): Nullable {
            return values().first { value == it.sqlValue }
        }
    }
}