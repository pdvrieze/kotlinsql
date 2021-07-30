/*
 * Copyright (c) 2021.
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

package uk.ac.bournemouth.kotlinsql.queries.impl

import uk.ac.bournemouth.kotlinsql.ColumnRef
import uk.ac.bournemouth.kotlinsql.IColumnType
import uk.ac.bournemouth.kotlinsql.impl.SqlComparisons
import uk.ac.bournemouth.kotlinsql.queries.*
import uk.ac.bournemouth.kotlinsql.columns.*

@Suppress("PropertyName")
class _Where {

    infix fun <T : Any, S : IColumnType<T, S, *>> ColumnRef<T, S, *>.eq(value: T): BooleanWhereValue =
        WhereCmpParam(this, SqlComparisons.eq, value)

    infix fun <T : Any, S : IColumnType<T, S, *>> ColumnRef<T, S, *>.ne(value: T): BooleanWhereValue =
        WhereCmpParam(this, SqlComparisons.ne, value)

    infix fun <T : Any, S : IColumnType<T, S, *>> ColumnRef<T, S, *>.lt(value: T): BooleanWhereValue =
        WhereCmpParam(this, SqlComparisons.lt, value)

    infix fun <T : Any, S : IColumnType<T, S, *>> ColumnRef<T, S, *>.le(value: T): BooleanWhereValue =
        WhereCmpParam(this, SqlComparisons.le, value)

    infix fun <T : Any, S : IColumnType<T, S, *>> ColumnRef<T, S, *>.gt(value: T): BooleanWhereValue =
        WhereCmpParam(this, SqlComparisons.gt, value)

    infix fun <T : Any, S : IColumnType<T, S, *>> ColumnRef<T, S, *>.ge(value: T): BooleanWhereValue =
        WhereCmpParam(this, SqlComparisons.ge, value)

    infix fun <S1 : IColumnType<*, S1, *>, S2 : IColumnType<*, S2, *>> ColumnRef<*, S1, *>.eq(other: ColumnRef<*, S2, *>): BooleanWhereValue =
        WhereCmpCol(this, SqlComparisons.eq, other)

    infix fun <S1 : IColumnType<*, S1, *>, S2 : IColumnType<*, S2, *>> ColumnRef<*, S1, *>.ne(other: ColumnRef<*, S2, *>): BooleanWhereValue =
        WhereCmpCol(this, SqlComparisons.ne, other)

    infix fun <S1 : IColumnType<*, S1, *>, S2 : IColumnType<*, S2, *>> ColumnRef<*, S1, *>.lt(other: ColumnRef<*, S2, *>): BooleanWhereValue =
        WhereCmpCol(this, SqlComparisons.lt, other)

    infix fun <S1 : IColumnType<*, S1, *>, S2 : IColumnType<*, S2, *>> ColumnRef<*, S1, *>.le(other: ColumnRef<*, S2, *>): BooleanWhereValue =
        WhereCmpCol(this, SqlComparisons.le, other)

    infix fun <S1 : IColumnType<*, S1, *>, S2 : IColumnType<*, S2, *>> ColumnRef<*, S1, *>.gt(other: ColumnRef<*, S2, *>): BooleanWhereValue =
        WhereCmpCol(this, SqlComparisons.gt, other)

    infix fun <S1 : IColumnType<*, S1, *>, S2 : IColumnType<*, S2, *>> ColumnRef<*, S1, *>.ge(other: ColumnRef<*, S2, *>): BooleanWhereValue =
        WhereCmpCol(this, SqlComparisons.ge, other)

    infix fun WhereClause?.AND(other: WhereClause?): WhereClause? {
        return when {
            this == null -> other
            other == null -> this
            else -> WhereCombine(this, "AND", other)
        }
    }

    infix fun WhereClause.OR(other: WhereClause): WhereClause = WhereCombine(this, "OR", other)
    infix fun WhereClause.XOR(other: WhereClause): WhereClause = WhereCombine(this, "XOR", other)

    infix fun <C : ICharColumn<*, C>> ColumnRef<*, *, C>.LIKE(pred: String): WhereClause {
        return WhereLike(this, pred)
    }

    fun NOT(other: WhereClause): WhereClause = WhereBooleanUnary("NOT", other)

    infix fun ColumnRef<*, *, *>.IS(ref: RefWhereValue) = WhereEq(this, "IS", ref)
    infix fun ColumnRef<*, *, *>.IS_NOT(ref: RefWhereValue) = WhereEq(this, "IS NOT", ref)

    //    fun ISNOT(other:WhereClause):WhereClause = WhereUnary("NOT", other)

    val TRUE: BooleanWhereValue = object : ConstBooleanWhereValue() {
        override fun toSQL(prefixMap: Map<String, String>?) = "TRUE"
    }
    val FALSE: BooleanWhereValue = object : ConstBooleanWhereValue() {
        override fun toSQL(prefixMap: Map<String, String>?) = "FALSE"
    }
    val UNKNOWN: BooleanWhereValue = object : ConstBooleanWhereValue() {
        override fun toSQL(prefixMap: Map<String, String>?) = "UNKNOWN"
    }
    val NULL: RefWhereValue = object : RefWhereValue() {
        override fun toSQL(prefixMap: Map<String, String>?) = "NULL"
        override fun setParameters(statementHelper: PreparedStatementHelper, first: Int) = first
    }
}