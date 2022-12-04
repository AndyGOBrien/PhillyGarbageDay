package com.llamalabb.phillygarbageday.domain.util

fun <T> concatenate(vararg lists: List<T>): List<T> {
    val result: MutableList<T> = ArrayList()
    for (list in lists) {
        result += list
    }
    return result
}

fun Int.ordinal() = this.toUInt().let {
    "$it" + when {
        (it % 100U in 11U..13U) -> "th"
        (it % 10U) == 1U -> "st"
        (it % 10U) == 2U -> "nd"
        (it % 10U) == 3U -> "rd"
        else -> "th"
    }
}

fun String.firstCharCapital() = lowercase().replaceFirstChar { it.uppercase() }
