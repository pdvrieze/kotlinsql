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

@Suppress("unused")
class GenerateDatabaseBaseKt {
  fun doGenerate(output: Writer, input: Any) {
    val count =  input as Int
    output.apply {

      appendCopyright()
      appendln()
      appendln("package uk.ac.bournemouth.util.kotlin.sql.impl.gen")
      appendln()
      appendln("import uk.ac.bournemouth.kotlinsql.Column")
      appendln("import uk.ac.bournemouth.kotlinsql.IColumnType")
      appendln("import uk.ac.bournemouth.kotlinsql.Database")
      appendln("import uk.ac.bournemouth.kotlinsql.TableRef")
      appendln("import uk.ac.bournemouth.kotlinsql.Table")
      appendln()
      appendln("abstract class DatabaseMethods {")
//      appendln("  companion object {")

      appendln("abstract operator fun get(key:TableRef):Table")

      appendFunctionGroup("SELECT","_Select", count)
      appendFunctionGroup("INSERT","_Insert", count)
      appendFunctionGroup("INSERT_OR_UPDATE","_Insert", count)

//      appendln("  }")
      appendln("}")
    }
  }

  private fun Writer.appendFunctionGroup(funName: String, className: String, count: Int) {
    for (n in 1..count) {
      appendln()
  //        appendln("    @JvmStatic")
      append("    fun <")

      run {
        val indent = " ".repeat(if (n < 9) 9 else 10)
        (1..n).joinToString(",\n$indent") { m -> "T$m:Any, S$m:IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }.apply {
          append(this)
        }
      }
      append("> $funName(")
      (1..n).joinToString { m -> "col$m: C$m" }.apply { append(this) }
      appendln(")=")
      if (className=="_Insert") {
        val update = funName=="INSERT_OR_UPDATE"
        append("            $className$n(get(col1.table), $update, ")
      } else if (n == 1 && funName=="SELECT") {
        append("            Database.$className$n(")
      } else {
        append("            $className$n(")
      }
      (1..n).joinToString { m -> "col$m" }.apply { append(this) }
      appendln(")")
    }
  }
}
