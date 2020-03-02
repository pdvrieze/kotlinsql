/*
 * Copyright (c) 2020.
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
 * Created by pdvrieze on 01/03/2020.
 */

package kotlinsql.builder

import java.io.Writer

@Suppress("unused")
class GenerateConnectionSource {
    fun doGenerate(output: Writer, input: Any) {
        val count = input as Int

        output.apply {
            appendCopyright()
            appendln()
            appendln("package uk.ac.bournemouth.util.kotlin.sql.impl.gen")
            appendln()
            appendln("import uk.ac.bournemouth.kotlinsql.Column")
            appendln("import uk.ac.bournemouth.kotlinsql.executeHelper")
            appendln("import uk.ac.bournemouth.kotlinsql.Database")
            appendln("import uk.ac.bournemouth.kotlinsql.Database.*")
            appendln("import uk.ac.bournemouth.kotlinsql.IColumnType")
            appendln("import uk.ac.bournemouth.util.kotlin.sql.DBContext")
            appendln("import uk.ac.bournemouth.util.kotlin.sql.impl.DBConnection2")
            appendln("import uk.ac.bournemouth.util.kotlin.sql.impl.DBAction2")
            appendln("import java.sql.SQLException")
            appendln("import javax.sql.DataSource")

            appendln("interface ConnectionSource<DB : Database> : DBContext<DB> {")
            appendln()
            appendln("    val datasource: DataSource")
            for (n in 1..count) {
                append("\n    ")
                generateSelectSignature(n)
                appendln()
            }
            appendln()
            appendln("    fun <O> DBAction2<O>.commit() {")
            appendln("        commit(this@ConnectionSource)")
            appendln("    }")
            appendln("}") // interface
            appendln()
            appendln("private class ConnectionSourceImpl<DB : Database>(")
            appendln("    override val db: DB,")
            appendln("    override val datasource: DataSource")
            appendln("                                                 ) : ConnectionSource<DB> {")

            for (n in 1..count) {
                appendln()
                append("\n    override ")
                generateSelectSignature(n)
                appendln(" {")
                append("        return DBAction2.Select(db.SELECT(")
                (1..n).joinTo(this){ n -> "col$n"}
                appendln("))")
                appendln("    }")
            }


            appendln("}")

            appendln()
            appendln("fun <DB : Database, R> DB.withSource(datasource: DataSource, action: ConnectionSource<DB>.() -> R): R {")
            appendln("    return ConnectionSourceImpl(this, datasource).action()")
            appendln("}")

            /*

private class ConnectionSourceImpl<DB : Database>(
    override val db: DB,
    override val datasource: DataSource
                                                 ) : ConnectionSource<DB> {
    override fun <T1 : Any, S1 : IColumnType<T1, S1, C1>, C1 : Column<T1, S1, C1>> SELECT(col1: C1): DBAction2.Select<Database.Select1<T1, S1, C1>> {
        return DBAction2.Select(db.SELECT(col1))
    }
}

             */

//
//
//
//            fun <O> DBAction2<O>.commit() {
//                commit(this@ConnectionSource)
//            }
//
//        }

/*

            for (n in 1..count) {
                appendln()
                append("class _Statement$n<")
                (1..n).joinToString(",\n                  ") { m -> "T$m:Any, S$m:IColumnType<T$m,S$m,C$m>, C$m: Column<T$m, S$m, C$m>" }.apply { append(this) }
                append(">(${if (n==1) "" else "override val "}select:_Select$n<")
                (1..n).joinTo(this, ",") { m -> "T$m,S$m,C$m" }
                appendln(">, where:WhereClause):")
                append("                      ")
                when(n) {
                    1    -> appendln("_Statement1Base<T1,S1,C1>(select,where),")
                    else -> appendln("_StatementBase(where),")
                }
                append("                      Select$n<")
                (1..n).joinTo(this, ",") { m -> "T$m,S$m,C$m" }
                appendln("> {")
                if (n>1) {
                    appendln()
                    append("  data class Result<")
                    (1..n).joinTo(this) { "out T$it" }
                    append(">(")
                    (1..n).joinTo(this) { "val col$it:T$it?" }
                    appendln(")")
                }

                appendln()
                append("  override fun execute(connection:DBConnection2<*>, block: (")
                (1..n).joinToString(",") {m -> "T$m?"}.apply { append(this) }
                appendln(")->Unit):Boolean {")
                appendln("    return executeHelper(connection, block) { rs, block2 ->")
                append("      block2(")
                (1..n).joinToString(",\n${" ".repeat(13)}") { m -> "select.col$m.type.fromResultSet(rs, $m)" }.apply { append(this) }
//        if (n==1) {
//          append("select.col1.type.fromResultSet(rs, 1)")
//        } else {
//        }
                appendln(')')
                appendln("    }")
                appendln("  }")

                if (n>1) {
                    appendln()
                    append("  override fun getSingle(connection:DBConnection2<*>)")
                    append(" = getSingle(connection) { ")
                    (1..n).joinTo(this,",") { "p$it" }
                    append(" -> Result(")
                    (1..n).joinTo(this,",") { "p$it" }
                    appendln(")}")

                    appendln()
                    append("  override fun <R> getSingle(connection:DBConnection2<*>, factory:")
                    appendFactorySignature(n)
                    appendln("):R? {")
                    appendln("    return connection.prepareStatement(toSQL()) {")
                    appendln("      setParams(this)")
                    appendln("      execute { rs ->")
                    appendln("        if (rs.next()) {")
                    appendln("          if (!rs.isLast) throw SQLException(\"Multiple results found, where only one or none expected\")")
                    append("          factory(")
                    (1..n).joinTo(this,",\n${" ".repeat(18)}") { m -> "select.col$m.type.fromResultSet(rs, $m)" }
                    appendln(")")

                    appendln("        } else null ")
                    appendln("      }")
                    appendln("    }")
                    appendln("  }")
                }

                appendln()
                if (n==1) {
                    appendln("  override fun getList(connection: DBConnection2<*>): List<T1?> {")
                    appendln("    val result=mutableListOf<T1?>()")
                    append("    execute(connection) { ")
                    (1..n).joinToString { "p$it" }.apply { append(this) }
                    append(" -> result.add(")
                    (1..n).joinToString { "p$it" }.apply { append(this) }
                    appendln(") }")
                } else {
                    append("  override fun <R> getList(connection: DBConnection2<*>, factory:")
                    appendFactorySignature(n)
                    appendln("): List<R> {")
                    appendln("    val result=mutableListOf<R>()")
                    append("    execute(connection) { ")
                    (1..n).joinToString { "p$it" }.apply { append(this) }
                    append(" -> result.add(factory(")
                    (1..n).joinToString { "p$it" }.apply { append(this) }
                    appendln(")) }")
                }
                appendln("    return result")
                appendln("  }")

                appendln()

                appendln("}")
            }
        }
*/
        }
    }

    private fun Writer.generateSelectSignature(n: Int, baseIndent: String = "    ") {
        append("fun <")
        (1..n).joinTo(this, ",\n${baseIndent}     ") { n ->
            "T$n:Any, S$n: IColumnType<T$n, S$n, C$n>, C$n: Column<T$n, S$n, C$n>"
        }
        appendln(">")

        append("${baseIndent}    SELECT(")
        (1..n).joinTo(this) { n -> "col$n: C$n" }
        if (n == 1) {
            append("): DBAction2.Select<Database.Select1<")
        } else {
            append("): DBAction2.Select<Select$n<\n${baseIndent}            ")
        }
        (1..n).joinTo(this, ",\n${baseIndent}            ") { n ->
            "T$n, S$n, C$n"
        }
        append(">>")
    }

}

