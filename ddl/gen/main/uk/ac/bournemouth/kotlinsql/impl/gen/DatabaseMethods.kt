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

import uk.ac.bournemouth.kotlinsql.queries.*
import uk.ac.bournemouth.kotlinsql.queries.impl.*
import uk.ac.bournemouth.kotlinsql.Column
import uk.ac.bournemouth.kotlinsql.IColumnType
import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.TableRef
import uk.ac.bournemouth.kotlinsql.Table

interface DatabaseMethods {
    operator fun get(key:TableRef):Table

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>>
        SELECT(col1: C1): _Select1<T1, S1, C1> =
            _Select1(col1)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>>
        SELECT(col1: C1, col2: C2): _Select2<T1, S1, C1, T2, S2, C2> =
            _Select2(col1, col2)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>>
        SELECT(col1: C1, col2: C2, col3: C3): _Select3<T1, S1, C1, T2, S2, C2, T3, S3, C3> =
            _Select3(col1, col2, col3)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>>
        SELECT(col1: C1, col2: C2, col3: C3, col4: C4): _Select4<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4> =
            _Select4(col1, col2, col3, col4)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>>
        SELECT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5): _Select5<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5> =
            _Select5(col1, col2, col3, col4, col5)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>>
        SELECT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6): _Select6<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6> =
            _Select6(col1, col2, col3, col4, col5, col6)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
         T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>>
        SELECT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7): _Select7<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7> =
            _Select7(col1, col2, col3, col4, col5, col6, col7)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
         T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
         T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>>
        SELECT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8): _Select8<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8> =
            _Select8(col1, col2, col3, col4, col5, col6, col7, col8)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
          T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
          T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
          T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
          T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
          T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
          T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
          T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
          T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>>
        SELECT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8, col9: C9): _Select9<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9> =
            _Select9(col1, col2, col3, col4, col5, col6, col7, col8, col9)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
          T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
          T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
          T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
          T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
          T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
          T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
          T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
          T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>,
          T10:Any, S10:IColumnType<T10,S10,C10>, C10: Column<T10, S10, C10>>
        SELECT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8, col9: C9, col10: C10): _Select10<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9, T10, S10, C10> =
            _Select10(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>>
        INSERT(col1: C1): _Insert1<T1, S1, C1> =
            _Insert1(get(col1.table), false, col1)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>>
        INSERT(col1: C1, col2: C2): _Insert2<T1, S1, C1, T2, S2, C2> =
            _Insert2(get(col1.table), false, col1, col2)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>>
        INSERT(col1: C1, col2: C2, col3: C3): _Insert3<T1, S1, C1, T2, S2, C2, T3, S3, C3> =
            _Insert3(get(col1.table), false, col1, col2, col3)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>>
        INSERT(col1: C1, col2: C2, col3: C3, col4: C4): _Insert4<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4> =
            _Insert4(get(col1.table), false, col1, col2, col3, col4)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>>
        INSERT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5): _Insert5<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5> =
            _Insert5(get(col1.table), false, col1, col2, col3, col4, col5)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>>
        INSERT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6): _Insert6<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6> =
            _Insert6(get(col1.table), false, col1, col2, col3, col4, col5, col6)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
         T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>>
        INSERT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7): _Insert7<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7> =
            _Insert7(get(col1.table), false, col1, col2, col3, col4, col5, col6, col7)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
         T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
         T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>>
        INSERT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8): _Insert8<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8> =
            _Insert8(get(col1.table), false, col1, col2, col3, col4, col5, col6, col7, col8)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
          T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
          T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
          T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
          T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
          T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
          T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
          T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
          T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>>
        INSERT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8, col9: C9): _Insert9<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9> =
            _Insert9(get(col1.table), false, col1, col2, col3, col4, col5, col6, col7, col8, col9)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
          T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
          T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
          T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
          T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
          T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
          T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
          T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
          T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>,
          T10:Any, S10:IColumnType<T10,S10,C10>, C10: Column<T10, S10, C10>>
        INSERT(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8, col9: C9, col10: C10): _Insert10<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9, T10, S10, C10> =
            _Insert10(get(col1.table), false, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>>
        INSERT_OR_UPDATE(col1: C1): _Insert1<T1, S1, C1> =
            _Insert1(get(col1.table), true, col1)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>>
        INSERT_OR_UPDATE(col1: C1, col2: C2): _Insert2<T1, S1, C1, T2, S2, C2> =
            _Insert2(get(col1.table), true, col1, col2)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>>
        INSERT_OR_UPDATE(col1: C1, col2: C2, col3: C3): _Insert3<T1, S1, C1, T2, S2, C2, T3, S3, C3> =
            _Insert3(get(col1.table), true, col1, col2, col3)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>>
        INSERT_OR_UPDATE(col1: C1, col2: C2, col3: C3, col4: C4): _Insert4<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4> =
            _Insert4(get(col1.table), true, col1, col2, col3, col4)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>>
        INSERT_OR_UPDATE(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5): _Insert5<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5> =
            _Insert5(get(col1.table), true, col1, col2, col3, col4, col5)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>>
        INSERT_OR_UPDATE(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6): _Insert6<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6> =
            _Insert6(get(col1.table), true, col1, col2, col3, col4, col5, col6)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
         T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>>
        INSERT_OR_UPDATE(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7): _Insert7<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7> =
            _Insert7(get(col1.table), true, col1, col2, col3, col4, col5, col6, col7)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
         T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
         T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
         T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
         T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
         T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
         T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
         T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>>
        INSERT_OR_UPDATE(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8): _Insert8<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8> =
            _Insert8(get(col1.table), true, col1, col2, col3, col4, col5, col6, col7, col8)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
          T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
          T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
          T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
          T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
          T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
          T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
          T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
          T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>>
        INSERT_OR_UPDATE(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8, col9: C9): _Insert9<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9> =
            _Insert9(get(col1.table), true, col1, col2, col3, col4, col5, col6, col7, col8, col9)

    fun <T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
          T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
          T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
          T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
          T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
          T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
          T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
          T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
          T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>,
          T10:Any, S10:IColumnType<T10,S10,C10>, C10: Column<T10, S10, C10>>
        INSERT_OR_UPDATE(col1: C1, col2: C2, col3: C3, col4: C4, col5: C5, col6: C6, col7: C7, col8: C8, col9: C9, col10: C10): _Insert10<T1, S1, C1, T2, S2, C2, T3, S3, C3, T4, S4, C4, T5, S5, C5, T6, S6, C6, T7, S7, C7, T8, S8, C8, T9, S9, C9, T10, S10, C10> =
            _Insert10(get(col1.table), true, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10)
}
