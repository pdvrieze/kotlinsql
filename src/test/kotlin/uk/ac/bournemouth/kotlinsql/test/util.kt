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

package uk.ac.bournemouth.kotlinsql.test

fun Any?.stringify(): String {
    return when (this) {
        null -> "null"
        is ByteArray -> joinToString(prefix = "[", postfix = "]")
        is ShortArray -> joinToString(prefix = "[", postfix = "]")
        is CharArray -> joinToString(prefix = "[", postfix = "]")
        is IntArray -> joinToString(prefix = "[", postfix = "]")
        is LongArray -> joinToString(prefix = "[", postfix = "]")
        is FloatArray -> joinToString(prefix = "[", postfix = "]")
        is DoubleArray -> joinToString(prefix = "[", postfix = "]")
        is Array<*> -> joinToString<Any?>(prefix = "[", postfix = "]") { it.stringify() }
        is Iterable<*> -> joinToString<Any?>(prefix = "[", postfix = "]") { it.stringify() }
        is CharSequence -> "\"${escape()}\""

        else -> toString()
    }
}

fun CharSequence.escape(): String = buildString {
    for (ch in this@escape) {
        when (ch) {
            '\\' -> append("\\\\")
            '"' -> append("\\\"")
            '\'' -> append("\\'")
            else -> append(ch)
        }
    }
}