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
            appendln("import uk.ac.bournemouth.util.kotlin.sql.impl.ConnectionSourceImplBase")
            appendln("import uk.ac.bournemouth.util.kotlin.sql.impl.ConnectionSourceBase")
            appendln("import uk.ac.bournemouth.util.kotlin.sql.impl.DbActionDSL")
            appendln("import uk.ac.bournemouth.util.kotlin.sql.impl.DBAction2")
            appendln("import uk.ac.bournemouth.util.kotlin.sql.impl.DBConnection2")
            appendln("import java.sql.SQLException")
            appendln("import javax.sql.DataSource")

            appendln()
            appendln("@DbActionDSL")
            appendln("interface DBActionable {")
            for (n in 1..count) {
                append("\n    ")
                generateSelectSignature(n)
                appendln()
            }
            appendln("}")
            appendln()

            appendln("interface ConnectionSource<DB : Database> : ConnectionSourceBase<DB>, DBContext<DB>, DBActionable {")
            appendln()
            appendln("    override val datasource: DataSource")
            appendln()
            appendln("    fun <O> DBAction2<O>.commit() {")
            appendln("        commit(this@ConnectionSource)")
            appendln("    }")
            appendln("}") // interface
            appendln()
            appendln("private class ConnectionSourceImpl<DB : Database>(")
            appendln("    override val db: DB,")
            appendln("    override val datasource: DataSource")
            appendln("                                                 ) : ConnectionSourceImplBase<DB>() {")

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
            appendln("operator fun <DB : Database, R> DB.invoke(datasource: DataSource, action: ConnectionSource<DB>.() -> R): R {")
            appendln("    return ConnectionSourceImpl(this, datasource).action()")
            appendln("}")
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

