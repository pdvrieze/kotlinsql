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

@Suppress("unused")
enum class KeyDeferrability(val sqlValue: Short) {
    IMPORTEDKEYINITIALLYDEFERRED(DatabaseMetaData.importedKeyInitiallyDeferred.toShort()),
    IMPORTEDKEYINITIALLYIMMEDIATE(DatabaseMetaData.importedKeyInitiallyImmediate.toShort()),
    IMPORTEDKEYNOTDEFERRABLE(DatabaseMetaData.importedKeyNotDeferrable.toShort());

    companion object {
        @JvmStatic
        fun from(value: Short) = when (value.toInt()) {
            DatabaseMetaData.importedKeyInitiallyDeferred  -> IMPORTEDKEYINITIALLYDEFERRED
            DatabaseMetaData.importedKeyInitiallyImmediate -> IMPORTEDKEYINITIALLYIMMEDIATE
            DatabaseMetaData.importedKeyNotDeferrable      -> IMPORTEDKEYNOTDEFERRABLE
            else                                           -> throw IllegalArgumentException(
                    "The value $this does not represent a valid key deferrability value")
        }
    }
}