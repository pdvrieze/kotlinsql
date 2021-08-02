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
class GenerateSelectClasses {
  fun doGenerate(output: Writer, input: Any) {
    val count = input as Int
    output.apply {
      appendCopyright()
      appendLine()
      appendLine("package io.github.pdvrieze.kotlinsql.impl.gen")
      appendLine()
      appendLine("import java.sql.SQLException")
      appendLine()
      appendLine("import io.github.pdvrieze.kotlinsql.Column")
      appendLine("import io.github.pdvrieze.kotlinsql.Database")
      appendLine("import io.github.pdvrieze.kotlinsql.Database.*")
      appendLine("import io.github.pdvrieze.kotlinsql.IColumnType")
//      appendLine("import io.github.pdvrieze.kotlinsql.sql.NonMonadicApi")
//      appendLine("import io.github.pdvrieze.kotlinsql.executeHelper")
//      appendLine("import io.github.pdvrieze.kotlinsql.sequenceHelper")

      for (n in 2..count) {
        appendLine()
        append("interface Select$n<")
        (1..n).joinToString(",\n               ") { m -> "T$m: Any, S$m: IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }
          .apply { append(this) }
        appendLine(">:SelectStatement {")
        appendLine()
        append("  override val select: _Select$n<")
        (1..n).joinTo(this, ",") { m -> "T$m,S$m,C$m" }
        appendLine(">")
        appendLine()
/*
        append("  fun <R>getList(connection: MonadicDBConnection<*>, factory:")
        appendFactorySignature(n)
        appendLine("): List<R>")
        if (n > 1) {
          appendLine()
          append("  fun getSingle(connection: MonadicDBConnection<*>): _Statement$n.Result<")
          (1..n).joinTo(this, ",") { m -> "T$m" }
          appendLine(">?")

          appendLine()
          append("  fun <R> getSingle(connection: MonadicDBConnection<*>, factory:")
          appendFactorySignature(n)
          appendLine("):R?")

          appendLine()
          append("  fun execute(connection: MonadicDBConnection<*>, block: (")
          (1..n).joinToString(",") { m -> "T$m?" }.apply { append(this) }
          appendLine(")->Unit):Boolean")
        }
*/

        appendLine("}")

        if (n > 1) {
/*
          appendLine()

          append("fun <DB: Database, In, ")
          (1..n).joinTo(this, ", ") { m -> "T$m: Any, C$m: Column<T$m, *, C$m>" }
          append(", R> EmptyDBTransaction<DB, In>.sequence(queryGen: DB.(In)->Select$n<")
          (1..n).joinTo(this, ", ") { m -> "T$m, *, C$m" }
          append(">, combine: (")
          (1..n).joinTo(this, ", ") { m -> "T$m?" }
          appendLine(") -> R): EvaluatableDBTransaction<DB, Sequence<R>> {")

          appendLine("    return sequenceHelper(queryGen) { query, rs ->")

          appendLine("        val s = query.select")

          append("        combine(")
          (1..n).joinTo(this, ", ") { m -> "s.col$m.fromResultSet(rs, $m)" }
          appendLine(") }")

          appendLine("}")
*/
        }
        appendLine()
        appendLine("@Suppress(\"UNCHECKED_CAST\")")
        append("class _Select$n<")
        (1..n).joinToString(",\n               ") { m -> "T$m:Any, S$m:IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }
          .apply { append(this) }

        append(">(")
        append((1..n).joinToString { m -> "col$m: C$m" })
        append("):\n      _BaseSelect(")
        append((1..n).joinToString { m -> "col$m" })
        appendLine("),")
        (1..n).joinTo(this, ", ", "      Select$n<", "> {") { m -> "T$m, S$m, C$m" }
        appendLine()
        appendLine()
        append("    override val select: _Select$n<")
        (1..n).joinTo(this, ",") { m -> "T$m,S$m,C$m" }
        appendLine("> get() = this")

        appendLine()
        append("    override fun WHERE(config: _Where.() -> WhereClause?): Select$n<")
        (1..n).joinTo(this) { m -> "T$m, S$m, C$m" }

        appendLine("> =")
        appendLine("        _Where().config()?.let { _Statement$n(this, it) } ?: this")

/*
        appendLine()
        append("    override fun execute(connection:MonadicDBConnection<*>, block: (")
        (1..n).joinToString(",") { m -> "T$m?" }.apply { append(this) }
        appendLine(")->Unit):Boolean {")
        appendLine("        return executeHelper(connection, block) { rs, block2 ->")
        append("            block2(")
        (1..n).joinToString(",\n${" ".repeat(19)}") { m -> "col$m.type.fromResultSet(rs, $m)" }.apply { append(this) }
//        if (n==1) {
//          append("select.col1.type.fromResultSet(rs, 1)")
//        } else {
//        }
        appendLine(')')
        appendLine("        }")
        appendLine("    }")

        appendLine()
        append("    override fun <R>getList(connection: MonadicDBConnection<*>, factory:")
        appendFactorySignature(n)
        appendLine("): List<R> {")
        appendLine("        val result=mutableListOf<R>()")
        append("        execute(connection) { ")
        (1..n).joinToString { "p$it" }.apply { append(this) }
        append(" -> result.add(factory(")
        (1..n).joinToString { "p$it" }.apply { append(this) }
        appendLine(")) }")
        appendLine("        return result")
        appendLine("    }")

        if (n > 1) {
          appendLine()
          appendLine("    @NonMonadicApi")
          append("    override fun getSingle(connection: MonadicDBConnection<*>)")
          append(" = getSingle(connection) { ")
          (1..n).joinTo(this, ",") { "p$it" }
          append(" -> _Statement$n.Result(")
          (1..n).joinTo(this, ",") { "p$it" }
          appendLine(")}")


          appendLine()
          appendLine("    @NonMonadicApi")
          append("    override fun <R> getSingle(connection: MonadicDBConnection<*>, factory:")
          appendFactorySignature(n)
          appendLine("):R? {")
          appendLine("      return connection.prepareStatement(toSQL()) {")
          appendLine("        setParams(this)")
          appendLine("        execute { rs ->")
          appendLine("          if (rs.next()) {")
          appendLine("            if (!rs.isLast) throw SQLException(\"Multiple results found, where only one or none expected\")")
          append("            factory(")
          (1..n).joinTo(this, ",\n${" ".repeat(18)}") { m -> "select.col$m.type.fromResultSet(rs, $m)" }
          appendLine(")")

          appendLine("          } else null ")
          appendLine("        }")
          appendLine("      }")

          appendLine("    }")
        }
*/

        appendLine()
        for (m in 1..n) {
          appendLine("    val col$m: C$m get() = columns[${m - 1}] as C$m")
        }

        appendLine("}")

      }
    }
  }
}
