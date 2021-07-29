/*
 * Copyright (c) 2017.
 *
 * This file is part of ProcessManager.
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

@Suppress("unused")
class GenerateStatementsKt {
  fun doGenerate(output: Writer, input: Any) {
    val count = input as Int

    output.apply {
      appendCopyright()
      appendLine()
      appendLine("package uk.ac.bournemouth.kotlinsql.impl.gen")
      appendLine()
      appendLine("import uk.ac.bournemouth.kotlinsql.Column")
//      appendLine("import uk.ac.bournemouth.kotlinsql.executeHelper")
//      appendln("import uk.ac.bournemouth.kotlinsql.Database")
      appendLine("import uk.ac.bournemouth.kotlinsql.Database.*")
      appendLine("import uk.ac.bournemouth.kotlinsql.IColumnType")
//      appendLine("import uk.ac.bournemouth.kotlinsql.sql.NonMonadicApi")
      appendLine("import java.sql.SQLException")

      for (n in 1..count) {
        appendLine()
        append("class _Statement$n<")
        (1..n).joinToString(",\n                  ") { m -> "T$m:Any, S$m:IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }
          .apply { append(this) }
        append(">(${if (n == 1) "" else "override val "}select:_Select$n<")
        (1..n).joinTo(this, ",") { m -> "T$m,S$m,C$m" }
        appendLine(">, where:WhereClause):")
        append("                      ")
        when (n) {
          1    -> appendLine("_Statement1Base<T1,S1,C1>(select,where),")
          else -> appendLine("_StatementBase(where),")
        }
        append("                      Select$n<")
        (1..n).joinTo(this, ",") { m -> "T$m,S$m,C$m" }
        appendLine("> {")
        if (n > 1) {
          appendLine()
          append("  data class Result<")
          (1..n).joinTo(this) { "out T$it" }
          append(">(")
          (1..n).joinTo(this) { "val col$it:T$it?" }
          appendLine(")")
        }

/*
        appendLine()
        append("  override fun execute(connection:MonadicDBConnection<*>, block: (")
        (1..n).joinToString(",") { m -> "T$m?" }.apply { append(this) }
        appendLine(")->Unit):Boolean {")
        appendLine("    return executeHelper(connection, block) { rs, block2 ->")
        append("      block2(")
        (1..n).joinToString(",\n${" ".repeat(13)}") { m -> "select.col$m.type.fromResultSet(rs, $m)" }
          .apply { append(this) }
//        if (n==1) {
//          append("select.col1.type.fromResultSet(rs, 1)")
//        } else {
//        }
        appendLine(')')
        appendLine("    }")
        appendLine("  }")

        if (n > 1) {
          appendLine()
          appendLine("  @NonMonadicApi")
          append("  override fun getSingle(connection:MonadicDBConnection<*>)")
          append(" = getSingle(connection) { ")
          (1..n).joinTo(this, ",") { "p$it" }
          append(" -> Result(")
          (1..n).joinTo(this, ",") { "p$it" }
          appendLine(")}")

          appendLine()
          appendLine("  @NonMonadicApi")
          append("  override fun <R> getSingle(connection:MonadicDBConnection<*>, factory:")
          appendFactorySignature(n)
          appendLine("):R? {")
          appendLine("    return connection.prepareStatement(toSQL()) {")
          appendLine("      setParams(this)")
          appendLine("      execute { rs ->")
          appendLine("        if (rs.next()) {")
          appendLine("          if (!rs.isLast) throw SQLException(\"Multiple results found, where only one or none expected\")")
          append("          factory(")
          (1..n).joinTo(this, ",\n${" ".repeat(18)}") { m -> "select.col$m.type.fromResultSet(rs, $m)" }
          appendLine(")")

          appendLine("        } else null ")
          appendLine("      }")
          appendLine("    }")
          appendLine("  }")
        }

        appendLine()
        if (n == 1) {
          appendLine("  override fun getList(connection: MonadicDBConnection<*>): List<T1?> {")
          appendLine("    val result=mutableListOf<T1?>()")
          append("    execute(connection) { ")
          (1..n).joinToString { "p$it" }.apply { append(this) }
          append(" -> result.add(")
          (1..n).joinToString { "p$it" }.apply { append(this) }
          appendLine(") }")
        } else {
          append("  override fun <R> getList(connection: MonadicDBConnection<*>, factory:")
          appendFactorySignature(n)
          appendLine("): List<R> {")
          appendLine("    val result=mutableListOf<R>()")
          append("    execute(connection) { ")
          (1..n).joinToString { "p$it" }.apply { append(this) }
          append(" -> result.add(factory(")
          (1..n).joinToString { "p$it" }.apply { append(this) }
          appendLine(")) }")
        }
        appendLine("    return result")
        appendLine("  }")
*/

        appendLine()

        appendLine("}")
      }
    }
  }

}

internal fun Appendable.appendFactorySignature(n: Int) {
  append("(")
  (1..n).joinToString(",") { "T$it?" }.apply { append(this) }
  append(")->R")
}
