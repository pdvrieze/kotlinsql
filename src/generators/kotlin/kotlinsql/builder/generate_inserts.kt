/*
 * Copyright (c) 2016.
 *
 * This file is part of ProcessManager.
 *
 * ProcessManager is free software: you can redistribute it and/or modify it under the terms of version 3 of the
 * GNU Lesser General Public License as published by the Free Software Foundation.
 *
 * ProcessManager is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with kotlinsql.  If not,
 * see <http://www.gnu.org/licenses/>.
 */

/**
 * Created by pdvrieze on 04/04/16.
 */

package kotlinsql.builder

import java.io.Writer

internal fun indent(n:Int, baseLen:Int) = " ".repeat(when (n) {
  in 0..9 -> baseLen
  in 10..99 -> baseLen+1
  else -> baseLen+2
})

@Suppress("unused")
class GenerateInsertsKt {
  fun doGenerate(output: Writer, input: Any) {
    val count = input as Int
    output.apply {
      appendCopyright()
      appendLine()
      appendLine(
        """
        package uk.ac.bournemouth.util.kotlin.sql.impl.gen

        import uk.ac.bournemouth.kotlinsql.Column
        import uk.ac.bournemouth.kotlinsql.ColumnRef
        import uk.ac.bournemouth.kotlinsql.Table
        import uk.ac.bournemouth.kotlinsql.Database._BaseInsert
        import uk.ac.bournemouth.kotlinsql.IColumnType
      """.trimIndent()
      )

      for (n in 1..count) {
        appendLine()
        append("class _Insert$n<")
        (1..n).joinTo(
          output,
          ",\n${indent(n, 15)}"
        ) { m -> "T$m:Any, S$m:IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }
        appendLine(">")
        append("      internal constructor(table: Table, update:Boolean, ")
        (1..n).joinTo(output, ",\n${indent(1, 27)}") { m -> "col$m: ColumnRef<T$m,S$m,C$m>" }
        append("): _BaseInsert(table, update, ")
        (1..n).joinTo(output, ",") { m -> "col$m" }
        appendLine(") {")
        appendLine()
        append("  fun VALUES(")
        (1..n).joinTo(output) { m -> "col$m: T$m?" }
        appendLine(") =")
        append("    this.apply{batch.add(_InsertValues$n(")
        (1..n).joinTo(output) { m -> "col$m" }
        appendLine("))}")
        appendLine()

        append("  inner class _InsertValues$n(")
        (1..n).joinTo(output) { m -> "col$m: T$m?" }
        append("):_BaseInsertValues(")
        (1..n).joinTo(output) { m -> "col$m" }
        append(")")

        appendLine()
        appendLine("}")

      }

    }
  }
}
