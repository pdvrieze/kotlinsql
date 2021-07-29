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

package uk.ac.bournemouth.kotlinsql

class ForeignKey constructor(private val fromCols:List<ColumnRef<*, *, *>>, internal val toTable: TableRef, private val toCols:List<ColumnRef<*, *, *>>) {
  internal fun toDDL(): CharSequence {
    val transform: (ColumnRef<*, *, *>) -> CharSequence = { it.name }
    val result = fromCols.joinTo(StringBuilder(), "`, `", "FOREIGN KEY (`", "`) REFERENCES ", transform = transform)
    result.append(toTable._name)
    return toCols.joinTo(result, "`, `", " (`",  "`)", transform = transform)
  }
}