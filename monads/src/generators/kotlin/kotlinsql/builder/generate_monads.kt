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
            appendLine()
            appendLine("package uk.ac.bournemouth.kotlinsql.monads.impl.gen")
            appendLine()
            appendLine("import uk.ac.bournemouth.kotlinsql.Column")
//            appendLine("import uk.ac.bournemouth.kotlinsql.executeHelper")
            appendLine("import uk.ac.bournemouth.kotlinsql.Database")
            appendLine("import uk.ac.bournemouth.kotlinsql.Database.*")
            appendLine("import uk.ac.bournemouth.kotlinsql.monadic.*")
            appendLine("import uk.ac.bournemouth.kotlinsql.queries.*")
            appendLine("import uk.ac.bournemouth.kotlinsql.queries.impl.*")
            appendLine("import uk.ac.bournemouth.kotlinsql.impl.gen.*")
            appendLine("import uk.ac.bournemouth.kotlinsql.IColumnType")
            appendLine("import java.sql.SQLException")
            appendLine("import javax.sql.DataSource")
            appendLine()
            appendLine("@DbActionDSL")
            appendLine("interface ConnectionSource<DB : Database> : ConnectionSourceBase<DB>, DBActionReceiver<DB> {")
            appendLine()
            appendLine("    override val datasource: DataSource")
            appendLine()
            appendLine("    fun <O> DBAction<DB, O>.commit(): O {")
            appendLine("        return commit(this@ConnectionSource)")
            appendLine("    }")
            appendLine("}")
            appendLine()
            appendLine("@DbActionDSL")
            appendLine("interface DBActionReceiver<DB: Database>: DBContext<DB> {")
            appendLine()
            appendLine("    val metadata: MonadicMetadata<DB>")
            for (op in Operation.values()) {
                for (n in 1..count) {
                    appendLine()
                    append("    ")
                    generateOperationSignature(n, op)
                    appendLine(" {")
                    if (op == Operation.SELECT) {
                        append("        return ${op.action}(${op.base}$n(")
                    } else {
                        val update = if (op == Operation.INSERT_OR_UPDATE) "true" else "false"
                        append("        return ${op.action}(${op.base}$n(db[col1.table], $update, ")
                    }
                    (1..n).joinTo(this) { m -> "col$m" }
                    appendLine("))")
                    appendLine("    }")
                }
            }
            appendLine("}") // interface
            appendLine()
            appendLine("private class ConnectionSourceImpl<DB : Database>(")
            appendLine("    override val db: DB,")
            appendLine("    override val datasource: DataSource")
            appendLine(") : ConnectionSourceImplBase<DB>() {")
//            appendln()
//            appendln("    override fun metadata(): MonadicMetadata<DB> {")
//            appendln("        return MonadicMetadataImpl()")
//            appendln("    }")


            appendLine("}")

            appendLine()
            appendLine("operator fun <DB : Database, R> DB.invoke(datasource: DataSource, action: ConnectionSource<DB>.() -> R): R {")
            appendLine("    return ConnectionSourceImpl(this, datasource).action()")
            appendLine("}")

            for (op in arrayOf(Operation.INSERT)) {
                for (n in 1..count) {
                    appendLine()

                    generateColumnOperationSignature(
                        n,
                        "VALUES",
                        "T",
                        "InsertAction<DB, _Insert$n<",
                        "InsertActionCommon<DB, _Insert$n<",
                        nullableParam = true,
                        genericPrefix = "DB: Database, "
                    )
                    appendLine("{")
                    append("    return InsertAction(insert.VALUES(")
                    (1..n).joinTo(this) { "col$it" }
                    appendLine("))")
                    appendLine("}")
                    /*
fun <T1:Any, S1: IColumnType<T1, S1, C1>, C1: Column<T1, S1, C1>>
        DBAction.InsertStart<_Insert1<T1, S1, C1>>.VALUES(col1: T1): DBAction.Insert<_Insert1<T1, S1, C1>> {
    return DBAction.Insert(this.insert.VALUES(col1))
}
                     */
                }
            }
        }
    }

    private fun Writer.generateOperationSignature(n: Int, op: Operation, baseIndent: String = "    ") {
        if (n == 1 && op == Operation.SELECT) {
            generateColumnOperationSignature(n, op.name, "C", "SelectAction<DB, Select1<", baseIndent = baseIndent)
        } else {
            generateColumnOperationSignature(n, op.name, "C", "${op.action}<DB, ${op.base}$n<", baseIndent = baseIndent)
        }
    }

}

private enum class Operation(val action: String, val base: String = action) {
    SELECT("SelectAction", "_Select"),
    INSERT("ValuelessInsertAction", "_Insert"),
    INSERT_OR_UPDATE("ValuelessInsertAction","_Insert"),
//    UPDATE("Update")
}

