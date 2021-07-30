/*
 * Copyright (c) 2016-2020.
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

/*
 * This file was automatically generated to implement repetitive code.
 */
package uk.ac.bournemouth.kotlinsql.impl.gen

import java.sql.SQLException

import uk.ac.bournemouth.kotlinsql.Column
import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.queries.*
import uk.ac.bournemouth.kotlinsql.queries.impl.*
import uk.ac.bournemouth.kotlinsql.IColumnType

interface Select2<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>>:SelectStatement {

  override val select: _Select2<T1,S1,C1,T2,S2,C2>

}

@Suppress("UNCHECKED_CAST")
class _Select2<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>>(col1: C1, col2: C2):
      _BaseSelect(col1, col2),
      Select2<T1, S1, C1, T2, S2, C2> {

    override val select: _Select2<T1,S1,C1,T2,S2,C2> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select2<T1, S1, C1, T2, S2, C2> =
        _Where().config()?.let { _Statement2(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
}

interface Select3<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3: Any, S3: IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>>:SelectStatement {

  override val select: _Select3<T1,S1,C1,T2,S2,C2,T3,S3,C3>

}

@Suppress("UNCHECKED_CAST")
class _Select3<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>>(col1: C1, col2: C2, col3: C3):
      _BaseSelect(col1, col2, col3),
      Select3<T1, S1, C1, T2, S2, C2, T3, S3, C3> {

    override val select: _Select3<T1,S1,C1,T2,S2,C2,T3,S3,C3> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select3<T1, S1, C1, T2, S2, C2, T3, S3, C3> =
        _Where().config()?.let { _Statement3(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
    val col3: C3 get() = columns[2] as C3
}

interface Select4<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3: Any, S3: IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4: Any, S4: IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>>:SelectStatement {

  override val select: _Select4<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4>

}

@Suppress("UNCHECKED_CAST")
class _Select4<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>>(col1: C1, col2: C2, col3: C3, col4: C4):
      _BaseSelect(col1, col2, col3, col4),
      Select4<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4> {

    override val select: _Select4<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select4<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4> =
        _Where().config()?.let { _Statement4(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
    val col3: C3 get() = columns[2] as C3
    val col4: C4 get() = columns[3] as C4
}

interface Select5<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3: Any, S3: IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4: Any, S4: IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5: Any, S5: IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>>:SelectStatement {

  override val select: _Select5<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5>

}

@Suppress("UNCHECKED_CAST")
class _Select5<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>>(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5):
      _BaseSelect(col1, col2, col3, col4, col5),
      Select5<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5> {

    override val select: _Select5<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select5<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5> =
        _Where().config()?.let { _Statement5(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
    val col3: C3 get() = columns[2] as C3
    val col4: C4 get() = columns[3] as C4
    val col5: C5 get() = columns[4] as C5
}

interface Select6<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3: Any, S3: IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4: Any, S4: IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5: Any, S5: IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6: Any, S6: IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>>:SelectStatement {

  override val select: _Select6<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6>

}

@Suppress("UNCHECKED_CAST")
class _Select6<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>>(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6):
      _BaseSelect(col1, col2, col3, col4, col5, col6),
      Select6<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6> {

    override val select: _Select6<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select6<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6> =
        _Where().config()?.let { _Statement6(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
    val col3: C3 get() = columns[2] as C3
    val col4: C4 get() = columns[3] as C4
    val col5: C5 get() = columns[4] as C5
    val col6: C6 get() = columns[5] as C6
}

interface Select7<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3: Any, S3: IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4: Any, S4: IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5: Any, S5: IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6: Any, S6: IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7: Any, S7: IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>>:SelectStatement {

  override val select: _Select7<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7>

}

@Suppress("UNCHECKED_CAST")
class _Select7<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>>(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7):
      _BaseSelect(col1, col2, col3, col4, col5, col6, col7),
      Select7<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7> {

    override val select: _Select7<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select7<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7> =
        _Where().config()?.let { _Statement7(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
    val col3: C3 get() = columns[2] as C3
    val col4: C4 get() = columns[3] as C4
    val col5: C5 get() = columns[4] as C5
    val col6: C6 get() = columns[5] as C6
    val col7: C7 get() = columns[6] as C7
}

interface Select8<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3: Any, S3: IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4: Any, S4: IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5: Any, S5: IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6: Any, S6: IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7: Any, S7: IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
               T8: Any, S8: IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>>:SelectStatement {

  override val select: _Select8<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8>

}

@Suppress("UNCHECKED_CAST")
class _Select8<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
               T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>>(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8):
      _BaseSelect(col1, col2, col3, col4, col5, col6, col7, col8),
      Select8<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8> {

    override val select: _Select8<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select8<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8> =
        _Where().config()?.let { _Statement8(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
    val col3: C3 get() = columns[2] as C3
    val col4: C4 get() = columns[3] as C4
    val col5: C5 get() = columns[4] as C5
    val col6: C6 get() = columns[5] as C6
    val col7: C7 get() = columns[6] as C7
    val col8: C8 get() = columns[7] as C8
}

interface Select9<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3: Any, S3: IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4: Any, S4: IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5: Any, S5: IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6: Any, S6: IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7: Any, S7: IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
               T8: Any, S8: IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
               T9: Any, S9: IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>>:SelectStatement {

  override val select: _Select9<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8,T9,S9,C9>

}

@Suppress("UNCHECKED_CAST")
class _Select9<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
               T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
               T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>>(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8, col9: C9):
      _BaseSelect(col1, col2, col3, col4, col5, col6, col7, col8, col9),
      Select9<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9> {

    override val select: _Select9<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8,T9,S9,C9> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select9<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9> =
        _Where().config()?.let { _Statement9(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
    val col3: C3 get() = columns[2] as C3
    val col4: C4 get() = columns[3] as C4
    val col5: C5 get() = columns[4] as C5
    val col6: C6 get() = columns[5] as C6
    val col7: C7 get() = columns[6] as C7
    val col8: C8 get() = columns[7] as C8
    val col9: C9 get() = columns[8] as C9
}

interface Select10<T1: Any, S1: IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2: Any, S2: IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3: Any, S3: IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4: Any, S4: IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5: Any, S5: IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6: Any, S6: IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7: Any, S7: IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
               T8: Any, S8: IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
               T9: Any, S9: IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>,
               T10: Any, S10: IColumnType<T10,S10,C10>, C10: Column<T10, S10, C10>>:SelectStatement {

  override val select: _Select10<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8,T9,S9,C9,T10,S10,C10>

}

@Suppress("UNCHECKED_CAST")
class _Select10<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
               T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
               T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>,
               T10:Any, S10:IColumnType<T10,S10,C10>, C10: Column<T10, S10, C10>>(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8, col9: C9, col10: C10):
      _BaseSelect(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10),
      Select10<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9, T10, S10, C10> {

    override val select: _Select10<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8,T9,S9,C9,T10,S10,C10> get() = this

    override fun WHERE(config: _Where.() -> WhereClause?): Select10<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9, T10, S10, C10> =
        _Where().config()?.let { _Statement10(this, it) } ?: this

    val col1: C1 get() = columns[0] as C1
    val col2: C2 get() = columns[1] as C2
    val col3: C3 get() = columns[2] as C3
    val col4: C4 get() = columns[3] as C4
    val col5: C5 get() = columns[4] as C5
    val col6: C6 get() = columns[5] as C6
    val col7: C7 get() = columns[6] as C7
    val col8: C8 get() = columns[7] as C8
    val col9: C9 get() = columns[8] as C9
    val col10: C10 get() = columns[9] as C10
}
