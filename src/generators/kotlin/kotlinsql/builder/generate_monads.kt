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

            appendln("@DbActionDSL")
            appendln("interface ConnectionSource<DB : Database> : ConnectionSourceBase<DB>, DBContext<DB> {")
            appendln()
            appendln("    override val datasource: DataSource")
            appendln()
            appendln("    fun <O> DBAction2<O>.commit(): O {")
            appendln("        return commit(this@ConnectionSource)")
            appendln("    }")
            for (operation in Operation.values()) {
                for (n in 1..count) {
                    append("\n    ")
                    generateOperationSignature(n, operation)
                    appendln()
                }
            }
            appendln("}") // interface
            appendln()
            appendln("private class ConnectionSourceImpl<DB : Database>(")
            appendln("    override val db: DB,")
            appendln("    override val datasource: DataSource")
            appendln("                                                 ) : ConnectionSourceImplBase<DB>() {")

            for (op in Operation.values()) {
                for (n in 1..count) {
                    appendln()
                    append("\n    override ")
                    generateOperationSignature(n, op)
                    appendln(" {")
                    append("        return DBAction2.${op.action}(db.${op.name}(")
                    (1..n).joinTo(this) { n -> "col$n" }
                    appendln("))")
                    appendln("    }")
                }
            }


            appendln("}")

            appendln()
            appendln("operator fun <DB : Database, R> DB.invoke(datasource: DataSource, action: ConnectionSource<DB>.() -> R): R {")
            appendln("    return ConnectionSourceImpl(this, datasource).action()")
            appendln("}")

            for(op in arrayOf(Operation.INSERT)) {
                for (n in 1..count) {
                    appendln()

                    generateColumnOperationSignature(
                        n,
                        "VALUES",
                        "T",
                        "DBAction2.Insert<_Insert$n<",
                        "DBAction2.InsertCommon<_Insert$n<",
                        nullableParam=true
                                                    )
                    appendln("{")
                    append("    return DBAction2.Insert(insert.VALUES(")
                    (1..n).joinTo(this) {"col$it"}
                    appendln("))")
                    appendln("}")
                    /*
fun <T1:Any, S1: IColumnType<T1, S1, C1>, C1: Column<T1, S1, C1>>
        DBAction2.InsertStart<_Insert1<T1, S1, C1>>.VALUES(col1: T1): DBAction2.Insert<_Insert1<T1, S1, C1>> {
    return DBAction2.Insert(this.insert.VALUES(col1))
}
                     */
                }
            }
        }
    }

    private fun Writer.generateOperationSignature(n: Int, op: Operation, baseIndent: String = "    ") {
        if (n == 1 && op == Operation.SELECT) {
            generateColumnOperationSignature(n, op.name, "C", "DBAction2.Select<Database.Select1<")
        } else {
            generateColumnOperationSignature(n, op.name, "C", "DBAction2.${op.action}<${op.base}$n<")
        }
    }

}

private enum class Operation(val action: String, val base: String = action) {
    SELECT("Select"),
    INSERT("InsertStart", "_Insert"),
    INSERT_OR_UPDATE("InsertStart","_Insert"),
//    UPDATE("Update")
}

