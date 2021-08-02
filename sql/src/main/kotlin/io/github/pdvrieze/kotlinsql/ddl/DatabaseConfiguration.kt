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

package io.github.pdvrieze.kotlinsql.ddl

@Suppress("unused")
class DatabaseConfiguration {

  @Suppress("ClassName")
  private class __AnonymousTable(name:String, extra: String?, block: TableConfiguration.()->Unit): ImmutableTable(name, extra, block)

  val tables = mutableListOf<ImmutableTable>()

  fun table(name:String, extra: String? = null, block: TableConfiguration.()->Unit): TableRef {
    return __AnonymousTable(name, extra, block)
  }

  @Suppress("NOTHING_TO_INLINE")
  inline fun table(t: ImmutableTable): TableRef {
    tables.add(t); return t.ref()
  }

}