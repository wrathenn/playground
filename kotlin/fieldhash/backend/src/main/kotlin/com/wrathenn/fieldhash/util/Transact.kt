package com.wrathenn.fieldhash.util

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.inTransactionUnchecked

fun <T> Jdbi.transact(f: context (Handle) () -> T): T {
    return this.inTransactionUnchecked(f)
}
