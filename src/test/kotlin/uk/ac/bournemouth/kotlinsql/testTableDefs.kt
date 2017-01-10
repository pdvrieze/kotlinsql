/*
 * Copyright (c) 2016.
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

package uk.ac.bournemouth.kotlinsql

import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import java.util.*

/**
 * Created by pdvrieze on 02/04/16.
 */
class testTableDefs {
  object db:Database( 1)

  @Test
  fun testSimpleDefine() {
    val test = object: MutableTable("Testq", null) {

      val bit by BIT("bit")

      override fun init() {
        throw UnsupportedOperationException()
      }

    }
    assertEquals(test.bit.name, "bit")
  }

  @Test
  fun testAnonymousDefine() {
    val test = object: MutableTable("Testq", null) {

      val bit by BIT

      override fun init() {
        throw UnsupportedOperationException()
      }

    }
    assertEquals(test.bit.name, "bit")
  }

  @Test
  fun testCustomType() {
    val X_UUID = customType({ VARCHAR(36)}, UUID::toString, UUID::fromString)
    val test = object: MutableTable("Testq", null) {

      val uuid by X_UUID

      override fun init() {
        throw UnsupportedOperationException()
      }

    }
    assertEquals(test.uuid.name, "uuid")
    assertEquals(test.uuid.table._name, "Testq")
    val uuidString = "c5321579-770a-4d72-a6fe-71c92ad32c71"
    val myUUID = UUID.fromString(uuidString)
    assertEquals(test.uuid.type.fromDb(uuidString), myUUID)
    assertEquals(test.uuid.type.toDB(myUUID), uuidString)
  }

  @Test
  fun testMakeSQL() {
    val table = object: MutableTable("TestMakeSQL", null) {
      val index by INT("index") { AUTO_INCREMENT }
      val name by VARCHAR("name", 20)

      override fun init() {
        PRIMARY_KEY(index)
      }
    }

    val statement = db.SELECT(table.name).WHERE {  table.index eq 1 }
    assertEquals(statement.toSQL(), "SELECT `name` FROM `TestMakeSQL` WHERE `index` = ?")

  }

  @Test
  fun testMakeSQL2() {
    val persons = object: MutableTable("persons", null) {
      val index by INT("index") { AUTO_INCREMENT }
      val name by VARCHAR("name", 20)

      override fun init() {
        PRIMARY_KEY(index)
      }
    }

    val emails = object: MutableTable("emails", null) {
      val index by reference(persons.index) { AUTO_INCREMENT }
      val email by VARCHAR(50)

      override fun init() {
        PRIMARY_KEY(index, email)
        FOREIGN_KEY(index).REFERENCES(persons.index)
      }
    }

    val statement = db.SELECT(persons.name, emails.email).WHERE { persons.index eq emails.index }
    assertEquals(statement.toSQL(), "SELECT p.`name`, e.`email` FROM `emails` AS e, `persons` AS p WHERE p.`index` = e.`index`")

  }

}