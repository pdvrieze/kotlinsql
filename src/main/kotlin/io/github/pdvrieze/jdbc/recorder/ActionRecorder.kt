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

package io.github.pdvrieze.jdbc.recorder

import io.github.pdvrieze.jdbc.recorder.actions.Action
import io.github.pdvrieze.jdbc.recorder.actions.StringAction
import java.lang.Exception

abstract class ActionRecorder {

    protected inline fun <R> record(args: Array<Any?> = emptyArray(), crossinline action: () -> R): R {
        return action().also { recordRes2(it, args) }
    }

    protected inline fun <R> record(crossinline action: () -> R): R {
        return action().also { recordRes2(it, emptyArray()) }
    }

    protected inline fun <R> record(arg1: Any?, crossinline action: () -> R): R {
        return action().also {
            recordRes2(it, arrayOf(arg1))
        }
    }

    protected inline fun <R> record(arg1: Any?, arg2: Any?, crossinline action: () -> R): R {
        return action().also { recordRes2(it, arrayOf(arg1, arg2)) }
    }

    protected inline fun <R> record(arg1: Any?, arg2: Any?, arg3: Any?, crossinline action: () -> R): R {
        return action().also { recordRes2(it, arrayOf(arg1, arg2, arg3)) }
    }

    protected inline fun <R> record(arg1: Any?, arg2: Any?, arg3: Any?, arg4: Any?, crossinline action: () -> R): R {
        return action().also { recordRes2(it, arrayOf(arg1, arg2, arg3)) }
    }

    protected abstract fun recordAction(action: Action)

    open protected fun <R> recordRes(result: R, vararg args: Any?): R {
        val calledFunction = Exception().stackTrace[1].methodName

        val ac = when(result) {
            Unit -> StringAction("$this.$calledFunction(${args.joinToString{it.stringify()}})")
            else -> StringAction("$this.$calledFunction(${args.joinToString{it.stringify()}}) -> ${result.stringify()}")
        }
        recordAction(ac)
        if (result is Action) {
            recordAction(result)
        }
        return result
    }

    open protected fun <R> recordRes2(result: R, args: Array<Any?>): R {
        val calledFunction = Exception().stackTrace[2].methodName
        val ac = when(result) {
            Unit -> StringAction("$this.$calledFunction(${args.joinToString{it.stringify()}})")
            else -> StringAction("$this.$calledFunction(${args.joinToString{it.stringify()}}) -> ${result.stringify()}")
        }
        recordAction(ac)
        if (result is Action) {
            recordAction(result)
        }
        return result
    }

    open protected fun record(vararg args: Any?) {
        val calledFunction = Exception().stackTrace[1].methodName
        val ac = StringAction("$this.$calledFunction(${args.joinToString()})")
        recordAction(ac)
    }

}