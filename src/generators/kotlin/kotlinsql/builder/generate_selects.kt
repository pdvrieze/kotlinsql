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
      appendln()
      appendln("package uk.ac.bournemouth.util.kotlin.sql.impl.gen")
      appendln()
      appendln("import java.sql.SQLException")
      appendln()
      appendln("import uk.ac.bournemouth.kotlinsql.Column")
      appendln("import uk.ac.bournemouth.kotlinsql.Database")
      appendln("import uk.ac.bournemouth.kotlinsql.Database.*")
      appendln("import uk.ac.bournemouth.kotlinsql.IColumnType")
      appendln("import uk.ac.bournemouth.util.kotlin.sql.impl.DBConnection2")
      appendln("import uk.ac.bournemouth.util.kotlin.sql.DBTransaction")
      appendln("import uk.ac.bournemouth.util.kotlin.sql.DBTransactionBase")
      appendln("import uk.ac.bournemouth.kotlinsql.executeHelper")
      appendln("import uk.ac.bournemouth.kotlinsql.sequenceHelper")

      for (n in 2..count) {
        appendln()
        append("interface Select$n<")
        (1..n).joinToString(",\n               ") { m -> "T$m:Any, S$m:IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }.apply { append(this) }
        appendln(">:SelectStatement {")
        appendln()
        append("  override val select:_Select$n<")
        (1..n).joinTo(this,",") { m -> "T$m,S$m,C$m" }
        appendln(">")
        appendln()
        append("  fun <R>getList(connection: DBConnection2<*>, factory:")
        appendFactorySignature(n)
        appendln("): List<R>")
        if (n>1) {
          appendln()
          append("  fun getSingle(connection:DBConnection2<*>): _Statement$n.Result<")
          (1..n).joinTo(this,",") { m-> "T$m" }
          appendln(">?")

          appendln()
          append("  fun <R> getSingle(connection:DBConnection2<*>, factory:")
          appendFactorySignature(n)
          appendln("):R?")

          appendln()
          append("  fun execute(connection:DBConnection2<*>, block: (")
          (1..n).joinToString(",") {m -> "T$m?"}.apply { append(this) }
          appendln(")->Unit):Boolean")
        }

        appendln("}")

        if (n>1) {
          appendln()

          append("fun <DB: Database, In, ")
          (1..n).joinTo(this, ", ") { m -> "T$m: Any, C$m: Column<T$m, *, C$m>" }
          append(", R> DBTransactionBase<DB, In>.sequence(queryGen: DB.(In)->Select$n<")
          (1..n).joinTo(this, ", ") { m -> "T$m, *, C$m"}
          append(">, combine: (")
          (1..n).joinTo(this, ", ") { m -> "T$m?" }
          appendln(") -> R): DBTransaction<DB, Sequence<R>> {")

          appendln("    return sequenceHelper(queryGen) { query, rs ->")

          appendln("        val s = query.select")

          append  ("        combine(")
          (1..n).joinTo(this, ", ") { m-> "s.col$m.fromResultSet(rs, $m)" }
          appendln(") }")

          appendln("}")
          /*
        fun <DB : Database, T : Any, C : Column<T, *, C>> DBTransactionBase<DB, *>.sequence(query: Database.Select1<T, *, C>): DBTransaction<DB, Sequence<T>> {
    return sequenceHelper(query) { query, rs -> query.select.col1.fromResultSet(rs, 1)!! }
}

         */
        }
        appendln()
        appendln("@Suppress(\"UNCHECKED_CAST\")")
        append("class _Select$n<")
        (1..n).joinToString(",\n               ") { m -> "T$m:Any, S$m:IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }.apply { append(this) }

        append(">(")
        append((1..n).joinToString { m -> "col$m: C$m" })
        append("):\n      _BaseSelect(")
        append((1..n).joinToString { m -> "col$m" })
        appendln("),")
        (1..n).joinTo(this, ", ","      Select$n<", "> {") { m -> "T$m, S$m, C$m" }
        appendln()
        appendln()
        append("    override val select: _Select$n<")
        (1..n).joinTo(this, ",") { m-> "T$m,S$m,C$m"}
        appendln("> get() = this")

        appendln()
        append("    override fun WHERE(config: _Where.() -> WhereClause?):Select$n<")
        (1..n).joinTo(this) { m -> "T$m, S$m, C$m" }

        appendln("> =")
        appendln("        _Where().config()?.let { _Statement$n(this, it) } ?: this")

        appendln()
        append("    override fun execute(connection:DBConnection2<*>, block: (")
        (1..n).joinToString(",") {m -> "T$m?"}.apply { append(this) }
        appendln(")->Unit):Boolean {")
        appendln("        return executeHelper(connection, block) { rs, block2 ->")
        append("            block2(")
        (1..n).joinToString(",\n${" ".repeat(19)}") { m -> "col$m.type.fromResultSet(rs, $m)" }.apply { append(this) }
//        if (n==1) {
//          append("select.col1.type.fromResultSet(rs, 1)")
//        } else {
//        }
        appendln(')')
        appendln("        }")
        appendln("    }")

        appendln()
        append("    override fun <R>getList(connection: DBConnection2<*>, factory:")
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

        if (n>1) {
          appendln()
          append("    override fun getSingle(connection:DBConnection2<*>)")
          append(" = getSingle(connection) { ")
          (1..n).joinTo(this,",") { "p$it" }
          append(" -> _Statement$n.Result(")
          (1..n).joinTo(this,",") { "p$it" }
          appendln(")}")


          appendln()
          append("    override fun <R> getSingle(connection:DBConnection2<*>, factory:")
          appendFactorySignature(n)
          appendln("):R? {")
          appendln("      return connection.prepareStatement(toSQL()) {")
          appendln("        setParams(this)")
          appendln("        execute { rs ->")
          appendln("          if (rs.next()) {")
          appendln("            if (!rs.isLast) throw SQLException(\"Multiple results found, where only one or none expected\")")
          append("            factory(")
          (1..n).joinTo(this,",\n${" ".repeat(18)}") { m -> "select.col$m.type.fromResultSet(rs, $m)" }
          appendln(")")

          appendln("          } else null ")
          appendln("        }")
          appendln("      }")

          appendln("    }")
        }

        appendln()
        for(m in 1..n) {
          appendln("    val col$m: C$m get() = columns[${m-1}] as C$m")
        }

        appendln("}")

      }
    }
  }
}
