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
import uk.ac.bournemouth.kotlinsql.Database.*
import uk.ac.bournemouth.kotlinsql.IColumnType
import java.sql.SQLException

class _Statement1<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>>(select:_Select1<T1,S1,C1>, where:WhereClause):
                      _Statement1Base<T1,S1,C1>(select,where),
                      Select1<T1,S1,C1> {

}

class _Statement2<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>>(override val select:_Select2<T1,S1,C1,T2,S2,C2>, where:WhereClause):
                      _StatementBase(where),
                      Select2<T1,S1,C1,T2,S2,C2> {

  data class Result<out T1, out T2>(val col1:T1?, val col2:T2?)

}

class _Statement3<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                  T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>>(override val select:_Select3<T1,S1,C1,T2,S2,C2,T3,S3,C3>, where:WhereClause):
                      _StatementBase(where),
                      Select3<T1,S1,C1,T2,S2,C2,T3,S3,C3> {

  data class Result<out T1, out T2, out T3>(val col1:T1?, val col2:T2?, val col3:T3?)

}

class _Statement4<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                  T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
                  T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>>(override val select:_Select4<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4>, where:WhereClause):
                      _StatementBase(where),
                      Select4<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4> {

  data class Result<out T1, out T2, out T3, out T4>(val col1:T1?, val col2:T2?, val col3:T3?, val col4:T4?)

}

class _Statement5<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                  T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
                  T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
                  T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>>(override val select:_Select5<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5>, where:WhereClause):
                      _StatementBase(where),
                      Select5<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5> {

  data class Result<out T1, out T2, out T3, out T4, out T5>(val col1:T1?, val col2:T2?, val col3:T3?, val col4:T4?, val col5:T5?)

}

class _Statement6<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                  T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
                  T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
                  T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
                  T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>>(override val select:_Select6<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6>, where:WhereClause):
                      _StatementBase(where),
                      Select6<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6> {

  data class Result<out T1, out T2, out T3, out T4, out T5, out T6>(val col1:T1?, val col2:T2?, val col3:T3?, val col4:T4?, val col5:T5?, val col6:T6?)

}

class _Statement7<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                  T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
                  T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
                  T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
                  T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
                  T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>>(override val select:_Select7<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7>, where:WhereClause):
                      _StatementBase(where),
                      Select7<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7> {

  data class Result<out T1, out T2, out T3, out T4, out T5, out T6, out T7>(val col1:T1?, val col2:T2?, val col3:T3?, val col4:T4?, val col5:T5?, val col6:T6?, val col7:T7?)

}

class _Statement8<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                  T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
                  T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
                  T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
                  T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
                  T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
                  T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>>(override val select:_Select8<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8>, where:WhereClause):
                      _StatementBase(where),
                      Select8<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8> {

  data class Result<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8>(val col1:T1?, val col2:T2?, val col3:T3?, val col4:T4?, val col5:T5?, val col6:T6?, val col7:T7?, val col8:T8?)

}

class _Statement9<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                  T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
                  T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
                  T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
                  T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
                  T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
                  T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
                  T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>>(override val select:_Select9<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8,T9,S9,C9>, where:WhereClause):
                      _StatementBase(where),
                      Select9<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8,T9,S9,C9> {

  data class Result<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9>(val col1:T1?, val col2:T2?, val col3:T3?, val col4:T4?, val col5:T5?, val col6:T6?, val col7:T7?, val col8:T8?, val col9:T9?)

}

class _Statement10<T1:Any, S1:IColumnType<T1,S1,C1>, C1: Column<T1, S1, C1>,
                  T2:Any, S2:IColumnType<T2,S2,C2>, C2: Column<T2, S2, C2>,
                  T3:Any, S3:IColumnType<T3,S3,C3>, C3: Column<T3, S3, C3>,
                  T4:Any, S4:IColumnType<T4,S4,C4>, C4: Column<T4, S4, C4>,
                  T5:Any, S5:IColumnType<T5,S5,C5>, C5: Column<T5, S5, C5>,
                  T6:Any, S6:IColumnType<T6,S6,C6>, C6: Column<T6, S6, C6>,
                  T7:Any, S7:IColumnType<T7,S7,C7>, C7: Column<T7, S7, C7>,
                  T8:Any, S8:IColumnType<T8,S8,C8>, C8: Column<T8, S8, C8>,
                  T9:Any, S9:IColumnType<T9,S9,C9>, C9: Column<T9, S9, C9>,
                  T10:Any, S10:IColumnType<T10,S10,C10>, C10: Column<T10, S10, C10>>(override val select:_Select10<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8,T9,S9,C9,T10,S10,C10>, where:WhereClause):
                      _StatementBase(where),
                      Select10<T1,S1,C1,T2,S2,C2,T3,S3,C3,T4,S4,C4,T5,S5,C5,T6,S6,C6,T7,S7,C7,T8,S8,C8,T9,S9,C9,T10,S10,C10> {

  data class Result<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8, out T9, out T10>(val col1:T1?, val col2:T2?, val col3:T3?, val col4:T4?, val col5:T5?, val col6:T6?, val col7:T7?, val col8:T8?, val col9:T9?, val col10:T10?)

}
