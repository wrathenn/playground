package com.wrathenn.fieldhash.logic.math

import arrow.core.NonEmptyList
import arrow.core.tail

fun gcd(a1: Long, a2: Long): Long {
    var num1 = a1
    var num2 = a2
    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }
    return num1
}

fun lcm(a1: Long, a2: Long): Long {
    return (a1 * a2) / gcd(a1, a2)
}

private tailrec fun lcmRec(numbers: List<Long>, res: Long): Long {
    if (numbers.isEmpty()) {
        return res
    }
    return lcmRec(numbers.tail(), lcm(numbers.first(), res))
}

fun lcm(numbers: NonEmptyList<Long>): Long {
    return lcmRec(numbers.tail, numbers.head)
}