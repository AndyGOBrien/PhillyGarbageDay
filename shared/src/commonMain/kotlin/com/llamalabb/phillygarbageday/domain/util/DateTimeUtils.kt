package com.llamalabb.phillygarbageday.domain.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt

fun Clock.localToday() = todayAt(TimeZone.currentSystemDefault())