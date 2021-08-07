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

package io.github.pdvrieze.kotlinsql.metadata.impl

import io.github.pdvrieze.kotlinsql.UnmanagedSql
import io.github.pdvrieze.kotlinsql.metadata.AttributeResults
import java.sql.ResultSet

@Suppress("unused")
@OptIn(UnmanagedSql::class)
internal class AttributeResultsImpl
@UnmanagedSql
constructor(attributes: ResultSet) : DataResultsImpl<AttributeResults, AttributeResults.Data>(attributes),
                                     AttributeResults {

    private val idxAttrDef by lazyColIdx("ATTR_DEF")
    private val idxAttrName by lazyColIdx("ATTR_NAME")
    private val idxAttrSize by lazyColIdx("ATTR_SIZE")
    private val idxAttrTypeName by lazyColIdx("ATTR_TYPE_NAME")
    private val idxDecimalDigits by lazyColIdx("DECIMAL_DIGITS")
    private val idxNumPrecRadix by lazyColIdx("NUM_REC_RADIX")
    private val idxTypeCat by lazyColIdx("TYPE_CAT")
    private val idxTypeSchem by lazyColIdx("TYPE_SCHEM")

    override val attrDefault: String? get() = resultSet.getString(idxAttrDef)
    override val attrName: String get() = resultSet.getString(idxAttrName)
    override val attrSize: Int get() = resultSet.getInt(idxAttrSize)
    override val attrTypeName: String? get() = resultSet.getString(idxAttrTypeName)
    override val decimalDigits: Int get() = resultSet.getInt(idxDecimalDigits)
    override val numPrecRadix: Int get() = resultSet.getInt(idxNumPrecRadix)
    override val typeCatalog: String? get() = resultSet.getString(idxTypeCat)
    override val typeScheme: String? get() = resultSet.getString(idxTypeSchem)

    override fun toList(): List<AttributeResults.Data> =
        toListImpl { AttributeResults.Data(it) }
}