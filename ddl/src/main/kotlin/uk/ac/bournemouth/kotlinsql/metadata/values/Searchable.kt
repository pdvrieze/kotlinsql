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

enum class Searchable(@Suppress("unused") val sqlValue: Short) {
    TYPE_PRED_NONE(DatabaseMetaData.typePredNone.toShort()),
    TYPE_PRED_CHAR(DatabaseMetaData.typePredChar.toShort()),
    TYPE_PRED_BASIC(DatabaseMetaData.typePredBasic.toShort()),
    TYPE_SEARCHABLE(DatabaseMetaData.typeSearchable.toShort()),
    ;

    companion object {
        @JvmStatic
        fun from(value: Short): Searchable {
            return when (value.toInt()) {
                DatabaseMetaData.typePredNone   -> TYPE_PRED_NONE
                DatabaseMetaData.typePredChar   -> TYPE_PRED_CHAR
                DatabaseMetaData.typePredBasic  -> TYPE_PRED_BASIC
                DatabaseMetaData.typeSearchable -> TYPE_SEARCHABLE
                else                            -> throw IllegalArgumentException(
                        "The value $this does not represent a valid searchability value")
            }
        }
    }
}