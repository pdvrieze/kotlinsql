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

package kotlinsql.builder


fun Appendable.generateColumnOperationSignature(
    noColumns: Int,
    funName: String,
    paramTypeBase: String,
    retType: String,
    receiver: String = "",
    retT: Boolean = true,
    retS: Boolean = true,
    retC: Boolean = true,
    baseIndent: String = "    ",
    nullableParam: Boolean = false
                                               ) {

    append("fun <")
    (1..noColumns).joinTo(this, ",\n${baseIndent}     ") { n ->
        "T$n:Any, S$n: IColumnType<T$n, S$n, C$n>, C$n: Column<T$n, S$n, C$n>"
    }
    appendln(">")

    append("${baseIndent}    ")
    if (receiver.isNotEmpty()) {
        appendGenericType(receiver, noColumns, baseIndent, retT, retS, retC)
        append('.')
    }
    append("${funName}(")
    (1..noColumns).joinTo(this) { n -> "col$n: ${paramTypeBase}$n${if (nullableParam) "?" else ""}" }
    append("): ")
    appendGenericType(retType, noColumns, baseIndent, retT, retS, retC)
}

fun Appendable.appendGenericType(
    type: String,
    noColumns: Int,
    baseIndent: String,
    retT: Boolean = true,
    retS: Boolean = true,
    retC: Boolean = true
                                ) {
    append(type)
    val numGenerics = type.fold(0) { acc, ch ->
        acc + when (ch) {
            '<'  -> 1
            '>'  -> -1
            else -> 0
        }
    }
    if (numGenerics > 0) {
        (1..noColumns).joinTo(this, ",\n${baseIndent}            ") { n ->
            sequence {
                if (retT || (!retS && !retC)) yield("T$n")
                if (retS) yield("S$n")
                if (retC) yield("C$n")
            }.joinToString()
        }
        for (i in 1..numGenerics) {
            append(">")
        }
    }
}
