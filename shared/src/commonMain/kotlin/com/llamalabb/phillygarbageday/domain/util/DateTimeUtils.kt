package com.llamalabb.phillygarbageday.domain.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayAt

fun Clock.localToday() = todayAt(TimeZone.currentSystemDefault())
fun LocalDate.plusDays(days: Int) = plus(days, DateTimeUnit.DAY)
fun LocalDate.minusDays(days: Int) = minus(days, DateTimeUnit.DAY)
fun LocalDate.startOfWeek() = minusDays(dayOfWeek.isoDayNumber)
fun LocalDate.fullDateFormat() = "${dayOfWeek.name.firstCharCapital()}, " +
        "${month.name.firstCharCapital()} ${dayOfMonth.ordinal()}"