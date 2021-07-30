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

import uk.ac.bournemouth.kotlinsql.Column
import uk.ac.bournemouth.kotlinsql.ColumnRef
import uk.ac.bournemouth.kotlinsql.Table
import uk.ac.bournemouth.kotlinsql.queries.impl._BaseInsert
import uk.ac.bournemouth.kotlinsql.IColumnType

class _Insert1<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>): _BaseInsert(table, update, col1) {

  fun VALUES(col1: T1?) =
    this.apply{batch.add(_InsertValues1(col1))}

  inner class _InsertValues1(col1: T1?):_BaseInsertValues(col1)
}

class _Insert2<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>): _BaseInsert(table, update, col1,col2) {

  fun VALUES(col1: T1?, col2: T2?) =
    this.apply{batch.add(_InsertValues2(col1, col2))}

  inner class _InsertValues2(col1: T1?, col2: T2?):_BaseInsertValues(col1, col2)
}

class _Insert3<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>,
                           col3: ColumnRef<T3,S3,C3>): _BaseInsert(table, update, col1,col2,col3) {

  fun VALUES(col1: T1?, col2: T2?, col3: T3?) =
    this.apply{batch.add(_InsertValues3(col1, col2, col3))}

  inner class _InsertValues3(col1: T1?, col2: T2?, col3: T3?):_BaseInsertValues(col1, col2, col3)
}

class _Insert4<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>,
                           col3: ColumnRef<T3,S3,C3>,
                           col4: ColumnRef<T4,S4,C4>): _BaseInsert(table, update, col1,col2,col3,col4) {

  fun VALUES(col1: T1?, col2: T2?, col3: T3?, col4: T4?) =
    this.apply{batch.add(_InsertValues4(col1, col2, col3, col4))}

  inner class _InsertValues4(col1: T1?, col2: T2?, col3: T3?, col4: T4?):_BaseInsertValues(col1, col2, col3, col4)
}

class _Insert5<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>,
                           col3: ColumnRef<T3,S3,C3>,
                           col4: ColumnRef<T4,S4,C4>,
                           col5: ColumnRef<T5,S5,C5>): _BaseInsert(table, update, col1,col2,col3,col4,col5) {

  fun VALUES(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?) =
    this.apply{batch.add(_InsertValues5(col1, col2, col3, col4, col5))}

  inner class _InsertValues5(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?):_BaseInsertValues(col1, col2, col3, col4, col5)
}

class _Insert6<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>,
                           col3: ColumnRef<T3,S3,C3>,
                           col4: ColumnRef<T4,S4,C4>,
                           col5: ColumnRef<T5,S5,C5>,
                           col6: ColumnRef<T6,S6,C6>): _BaseInsert(table, update, col1,col2,col3,col4,col5,col6) {

  fun VALUES(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?) =
    this.apply{batch.add(_InsertValues6(col1, col2, col3, col4, col5, col6))}

  inner class _InsertValues6(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?):_BaseInsertValues(col1, col2, col3, col4, col5, col6)
}

class _Insert7<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>,
                           col3: ColumnRef<T3,S3,C3>,
                           col4: ColumnRef<T4,S4,C4>,
                           col5: ColumnRef<T5,S5,C5>,
                           col6: ColumnRef<T6,S6,C6>,
                           col7: ColumnRef<T7,S7,C7>): _BaseInsert(table, update, col1,col2,col3,col4,col5,col6,col7) {

  fun VALUES(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?, col7: T7?) =
    this.apply{batch.add(_InsertValues7(col1, col2, col3, col4, col5, col6, col7))}

  inner class _InsertValues7(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?, col7: T7?):_BaseInsertValues(col1, col2, col3, col4, col5, col6, col7)
}

class _Insert8<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
               T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>,
                           col3: ColumnRef<T3,S3,C3>,
                           col4: ColumnRef<T4,S4,C4>,
                           col5: ColumnRef<T5,S5,C5>,
                           col6: ColumnRef<T6,S6,C6>,
                           col7: ColumnRef<T7,S7,C7>,
                           col8: ColumnRef<T8,S8,C8>): _BaseInsert(table, update, col1,col2,col3,col4,col5,col6,col7,col8) {

  fun VALUES(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?, col7: T7?, col8: T8?) =
    this.apply{batch.add(_InsertValues8(col1, col2, col3, col4, col5, col6, col7, col8))}

  inner class _InsertValues8(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?, col7: T7?, col8: T8?):_BaseInsertValues(col1, col2, col3, col4, col5, col6, col7, col8)
}

class _Insert9<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
               T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
               T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
               T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
               T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
               T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
               T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
               T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
               T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>,
                           col3: ColumnRef<T3,S3,C3>,
                           col4: ColumnRef<T4,S4,C4>,
                           col5: ColumnRef<T5,S5,C5>,
                           col6: ColumnRef<T6,S6,C6>,
                           col7: ColumnRef<T7,S7,C7>,
                           col8: ColumnRef<T8,S8,C8>,
                           col9: ColumnRef<T9,S9,C9>): _BaseInsert(table, update, col1,col2,col3,col4,col5,col6,col7,col8,col9) {

  fun VALUES(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?, col7: T7?, col8: T8?, col9: T9?) =
    this.apply{batch.add(_InsertValues9(col1, col2, col3, col4, col5, col6, col7, col8, col9))}

  inner class _InsertValues9(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?, col7: T7?, col8: T8?, col9: T9?):_BaseInsertValues(col1, col2, col3, col4, col5, col6, col7, col8, col9)
}

class _Insert10<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
                T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
                T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
                T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
                T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
                T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
                T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>,
                T10:Any, S10:IColumnType<T10,S10,C10>, C10: Column<T10, S10, C10>>
      internal constructor(table: Table, update:Boolean, col1: ColumnRef<T1,S1,C1>,
                           col2: ColumnRef<T2,S2,C2>,
                           col3: ColumnRef<T3,S3,C3>,
                           col4: ColumnRef<T4,S4,C4>,
                           col5: ColumnRef<T5,S5,C5>,
                           col6: ColumnRef<T6,S6,C6>,
                           col7: ColumnRef<T7,S7,C7>,
                           col8: ColumnRef<T8,S8,C8>,
                           col9: ColumnRef<T9,S9,C9>,
                           col10: ColumnRef<T10,S10,C10>): _BaseInsert(table, update, col1,col2,col3,col4,col5,col6,col7,col8,col9,col10) {

  fun VALUES(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?, col7: T7?, col8: T8?, col9: T9?, col10: T10?) =
    this.apply{batch.add(_InsertValues10(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10))}

  inner class _InsertValues10(col1: T1?, col2: T2?, col3: T3?, col4: T4?, col5: T5?, col6: T6?, col7: T7?, col8: T8?, col9: T9?, col10: T10?):_BaseInsertValues(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10)
}
