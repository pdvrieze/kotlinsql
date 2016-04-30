/*
 * Copyright (c) 2016.
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

/**
 * Created by pdvrieze on 04/04/16.
 */

package kotlinsql.builder

import java.io.Writer

class GenerateSelectClasses {
  fun doGenerate(output: Writer, input: Any) {
    val count = input as Int
    output.apply {
      appendCopyright()
      appendln()
      appendln("package uk.ac.bournemouth.util.kotlin.sql.impl.gen")
      appendln()
      appendln("import uk.ac.bournemouth.kotlinsql.Column")
      appendln("import uk.ac.bournemouth.kotlinsql.Database")
      appendln("import uk.ac.bournemouth.kotlinsql.Database.*")
      appendln("import uk.ac.bournemouth.kotlinsql.IColumnType")
      appendln("import uk.ac.bournemouth.util.kotlin.sql.DBConnection")
      appendln("import uk.ac.bournemouth.kotlinsql.executeHelper")
      for (n in 2..count) {
        appendln()
        appendln("@Suppress(\"UNCHECKED_CAST\")")
        append("class _Select$n<")
        (1..n).joinToString(",\n               ") { m -> "T$m:Any, S$m:IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }.apply { append(this) }

        append(">(")
        append((1..n).joinToString { m -> "col$m: C$m" })
        append("):\n      _BaseSelect(")
        append((1..n).joinToString { m -> "col$m" })
        appendln("){")
        appendln("    override fun WHERE(config: _Where.() -> WhereClause) =")
        appendln("        _Statement$n(this, _Where().config())")

        appendln()
        append("    fun execute(connection:DBConnection, block: (")
        (1..n).joinToString(",") {m -> "T$m?"}.apply { append(this) }
        appendln(")->Unit):Boolean {")
        appendln("        return executeHelper(connection, block) { rs, block ->")
        append("            block(")
        (1..n).joinToString(",\n${" ".repeat(12)}") { m -> "col$m.type.fromResultSet(rs, $m)" }.apply { append(this) }
//        if (n==1) {
//          append("select.col1.type.fromResultSet(rs, 1)")
//        } else {
//        }
        appendln(')')
        appendln("        }")
        appendln("    }")

        appendln()
        append("    fun <R>getList(connection: DBConnection, factory:")
        appendFactorySignature(n)
        appendln("): List<R> {")
        appendln("        val result=mutableListOf<R>()")
        append("        execute(connection) { ")
        (1..n).joinToString { "p$it" }.apply { append(this) }
        append(" -> result.add(factory(")
        (1..n).joinToString { "p$it" }.apply { append(this) }
        appendln(")) }")
        appendln("        return result")
        appendln("    }")


        appendln()
        for(m in 1..n) {
          appendln("    val col$m: C$m get() = columns[$m] as C$m")
        }

        appendln("}")

      }
    }
  }
}
