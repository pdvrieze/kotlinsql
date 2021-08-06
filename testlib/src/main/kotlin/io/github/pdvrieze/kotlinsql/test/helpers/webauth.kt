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

package io.github.pdvrieze.kotlinsql.test.helpers

import io.github.pdvrieze.jdbc.recorder.actions.StringAction
import io.github.pdvrieze.kotlinsql.ddl.Database
import io.github.pdvrieze.kotlinsql.ddl.MutableTable
import io.github.pdvrieze.kotlinsql.ddl.columns.LengthCharColumn
import io.github.pdvrieze.kotlinsql.ddl.columns.LengthCharColumnType


/**
 * Code definition of the webauth database.
 */

const val EXTRACONF = "ENGINE=InnoDB CHARSET=utf8"

object WebAuthDB : Database(1) {

    object users : MutableTable(EXTRACONF) {
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

    object roles : MutableTable(EXTRACONF) {
        val role by VARCHAR("role", 30) { NOT_NULL }
        val description by VARCHAR("description", 120) { NOT_NULL }

        override fun init() {
            PRIMARY_KEY(role)
        }
    }

    object user_roles : MutableTable(EXTRACONF) {
        val user by VARCHAR("user", 30) { NOT_NULL; BINARY }
        val role by VARCHAR("role", 30) { NOT_NULL }

        override fun init() {
            PRIMARY_KEY(user, role)
            FOREIGN_KEY(user).REFERENCES(users.user)
            FOREIGN_KEY(role).REFERENCES(roles.role)
        }
    }

    object tokens : MutableTable(EXTRACONF) {
        val tokenid by INT("tokenid") { NOT_NULL; AUTO_INCREMENT }
        val user by reference(users.user) { NOT_NULL; BINARY }
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

    object app_perms : MutableTable(EXTRACONF) {
        val user by reference(users.user) { NOT_NULL; BINARY }
        val app by VARCHAR("app", 50) { NOT_NULL }
        override fun init() {
            PRIMARY_KEY(user, app)
            FOREIGN_KEY(user).REFERENCES(users.user)
        }
    }

    object pubkeys : MutableTable(EXTRACONF) {
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

    object challenges : MutableTable(EXTRACONF) {
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

object TestActions {

    val CREATE_USERS = StringAction("""|RecordingPreparedStatement("CREATE TABLE `users` (
        |  `user` VARCHAR(30) BINARY NOT NULL,
        |  `fullname` VARCHAR(80),
        |  `alias` VARCHAR(80),
        |  `password` VARCHAR(40) BINARY,
        |  `resettoken` VARCHAR(20) BINARY,
        |  `resettime` DATETIME,
        |  PRIMARY KEY (`user`)
        |) ENGINE=InnoDB CHARSET=utf8;").execute() -> true""".trimMargin())

    val CREATE_USER_ROLES = StringAction("""|RecordingPreparedStatement("CREATE TABLE `user_roles` (
        |  `user` VARCHAR(30) BINARY NOT NULL,
        |  `role` VARCHAR(30) NOT NULL,
        |  PRIMARY KEY (`user`, `role`),
        |  FOREIGN KEY (`user`) REFERENCES users (`user`),
        |  FOREIGN KEY (`role`) REFERENCES roles (`role`)
        |) ENGINE=InnoDB CHARSET=utf8;").execute() -> true""".trimMargin())

    val CREATE_PUBKEYS = StringAction("""|RecordingPreparedStatement("CREATE TABLE `pubkeys` (
        |  `keyid` INT NOT NULL AUTO_INCREMENT,
        |  `user` VARCHAR(30) BINARY NOT NULL,
        |  `appname` VARCHAR(80),
        |  `pubkey` MEDIUMTEXT BINARY NOT NULL,
        |  `lastUse` BIGINT,
        |  PRIMARY KEY (`keyid`),
        |  FOREIGN KEY (`user`) REFERENCES users (`user`)
        |) ENGINE=InnoDB CHARSET=utf8;").execute() -> true""".trimMargin())

    val CREATE_TOKENS = StringAction("""|RecordingPreparedStatement("CREATE TABLE `tokens` (
        |  `tokenid` INT NOT NULL AUTO_INCREMENT,
        |  `user` VARCHAR(30) BINARY NOT NULL,
        |  `ip` VARCHAR(45) NOT NULL,
        |  `keyid` INT,
        |  `token` VARCHAR(45) BINARY NOT NULL,
        |  `epoch` BIGINT NOT NULL,
        |  PRIMARY KEY (`tokenid`),
        |  FOREIGN KEY (`user`) REFERENCES users (`user`),
        |  FOREIGN KEY (`keyid`) REFERENCES pubkeys (`keyid`)
        |) ENGINE=InnoDB CHARSET=utf8;").execute() -> true""".trimMargin())

    val CREATE_APP_PERMS = StringAction("""|RecordingPreparedStatement("CREATE TABLE `app_perms` (
        |  `user` VARCHAR(30) BINARY NOT NULL,
        |  `app` VARCHAR(50) NOT NULL,
        |  PRIMARY KEY (`user`, `app`),
        |  FOREIGN KEY (`user`) REFERENCES users (`user`)
        |) ENGINE=InnoDB CHARSET=utf8;").execute() -> true""".trimMargin())

    val CREATE_CHALLENGES = StringAction("""|RecordingPreparedStatement("CREATE TABLE `challenges` (
        |  `keyid` INT NOT NULL,
        |  `challenge` VARCHAR(100) BINARY NOT NULL,
        |  `requestip` VARCHAR(45) NOT NULL,
        |  `epoch` BIGINT,
        |  PRIMARY KEY (`keyid`, `requestip`),
        |  FOREIGN KEY (`keyid`) REFERENCES pubkeys (`keyid`)
        |) ENGINE=InnoDB CHARSET=utf8;").execute() -> true""".trimMargin())
}