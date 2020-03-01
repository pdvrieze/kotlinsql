/*
 * Copyright (c) 2016. 
 *
 * This file is part of ProcessManager.
 *
 * ProcessManager is free software: you can redistribute it and/or modify it under the terms of version 3 of the 
 * GNU Lesser General Public License as published by the Free Software Foundation.
 *
 * ProcessManager is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with ProcessManager.  If not,
 * see <http://www.gnu.org/licenses/>.
 */

package uk.ac.bournemouth.kotlinsql


import uk.ac.bournemouth.kotlinsql.Database
import uk.ac.bournemouth.kotlinsql.MutableTable
import uk.ac.bournemouth.util.kotlin.sql.DBContext

/**
 * Code definition of the webauth database.
 */

const val EXTRACONF="ENGINE=InnoDB CHARSET=utf8"

object WebAuthDB: Database(1) {

  object users: MutableTable(EXTRACONF) {
    val user by VARCHAR("user", 30) { NOT_NULL; BINARY }
    val fullname by VARCHAR("fullname", 80)
    val alias by VARCHAR("alias", 80)
    val password by VARCHAR("password", 40) { BINARY }
    val resettoken by VARCHAR("resettoken", 20) { BINARY }
    val resettime by DATETIME("resettime")

    override fun init() {
      PRIMARY_KEY(user)
    }
  }

  object roles: MutableTable(EXTRACONF) {
    val role by VARCHAR("role", 30) { NOT_NULL }
    val description by VARCHAR("description", 120) { NOT_NULL }

    override fun init() {
      PRIMARY_KEY(role)
    }
  }

  object user_roles: MutableTable(EXTRACONF) {
    val user by VARCHAR("user", 30) { NOT_NULL; BINARY }
    val role by VARCHAR("role", 30) { NOT_NULL }

    override fun init() {
      PRIMARY_KEY(user, role)
      FOREIGN_KEY(user).REFERENCES(users.user)
      FOREIGN_KEY(role).REFERENCES(roles.role)
    }
  }

  object tokens: MutableTable(EXTRACONF) {
    val tokenid by INT("tokenid") { NOT_NULL; AUTO_INCREMENT }
    val user by reference(users.user) { NOT_NULL; BINARY}
    val ip by VARCHAR("ip", 45) { NOT_NULL }
    val keyid by reference(pubkeys.keyid)
    val token by VARCHAR("token", 45) { NOT_NULL; BINARY }
    val epoch by BIGINT("epoch") { NOT_NULL }

    override fun init() {
      PRIMARY_KEY(tokenid)
      FOREIGN_KEY(user).REFERENCES(users.user)
      FOREIGN_KEY(keyid).REFERENCES(pubkeys.keyid)
    }
  }

  object app_perms: MutableTable(EXTRACONF) {
    val user by reference(users.user) { NOT_NULL; BINARY }
    val app by VARCHAR("app", 50) { NOT_NULL }
    override fun init() {
      PRIMARY_KEY (user, app)
      FOREIGN_KEY(user).REFERENCES(users.user)
    }
  }

  object pubkeys: MutableTable(EXTRACONF) {
    val keyid by INT("keyid") { NOT_NULL; AUTO_INCREMENT }
    val user by reference(users.user) { NOT_NULL; BINARY }
    val appname by VARCHAR("appname", 80)
    val pubkey by MEDIUMTEXT("pubkey") { BINARY; NOT_NULL }
    val lastUse by BIGINT("lastUse")
    override fun init() {
      PRIMARY_KEY(keyid)
      FOREIGN_KEY(user).REFERENCES(users.user)
    }
  }

  object challenges: MutableTable(EXTRACONF) {
    val keyid by reference(pubkeys.keyid) { NOT_NULL }
    val challenge by VARCHAR("challenge", 100) { NOT_NULL; BINARY }
    val requestip by VARCHAR("requestip", 45) { NOT_NULL }
    val epoch by BIGINT("epoch")
    override fun init() {
      PRIMARY_KEY(keyid, requestip)
      FOREIGN_KEY(keyid).REFERENCES(pubkeys.keyid)
    }
  }

}

val DBContext<WebAuthDB>.users get() = WebAuthDB.users
val DBContext<WebAuthDB>.roles get() = WebAuthDB.roles
val DBContext<WebAuthDB>.user_roles get() = WebAuthDB.user_roles
val DBContext<WebAuthDB>.tokens get() = WebAuthDB.tokens
val DBContext<WebAuthDB>.app_perms get() = WebAuthDB.app_perms
val DBContext<WebAuthDB>.pubkeys get() = WebAuthDB.pubkeys
val DBContext<WebAuthDB>.challenges get() = WebAuthDB.challenges