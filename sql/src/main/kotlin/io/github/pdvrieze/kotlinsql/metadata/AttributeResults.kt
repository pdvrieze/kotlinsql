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

package io.github.pdvrieze.kotlinsql.metadata

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
class AttributeResults
    @UnmanagedSql
    constructor(attributes: ResultSet) : DataResults<AttributeResults>(attributes) {
    private val idxAttrDef by lazyColIdx("ATTR_DEF")
    private val idxAttrName by lazyColIdx("ATTR_NAME")
    private val idxAttrSize by lazyColIdx("ATTR_SIZE")
    private val idxAttrTypeName by lazyColIdx("ATTR_TYPE_NAME")
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    private val idxNumPrecRadix by lazyColIdx("NUM_REC_RADIX")
    private val idxTypeCat by lazyColIdx("TYPE_CAT")
    private val idxTypeSchem by lazyColIdx("TYPE_SCHEM")

    val attrDefault: String? get() = resultSet.getString(idxAttrDef)
    val attrName: String get() = resultSet.getString(idxAttrName)
    val attrSize: Int get() = resultSet.getInt(idxAttrSize)
    val attrTypeName: String? get() = resultSet.getString(idxAttrTypeName)
    val decimalDigits: Int get() = resultSet.getInt(idxDecimalDigits)
    val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
    val typeCatalog: String? get() = resultSet.getString(idxTypeCat)
    val typeScheme: String? get() = resultSet.getString(idxTypeSchem)

    public override fun data(): Data = Data(this)

    class Data(result: AttributeResults): DataResults.Data(result) {
        val attrDefault: String? = result.attrDefault
        val attrName: String = result.attrName
        val attrSize: Int = result.attrSize
        val attrTypeName: String? = result.attrTypeName
        val decimalDigits: Int = result.decimalDigits
        val numPrecRadix: Int = result.numPrecRadix
        val typeCatalog: String? = result.typeCatalog
        val typeScheme: String? = result.typeScheme
    }
}