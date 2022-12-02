package com.llamalabb.phillygarbageday.domain.domain_model

import kotlinx.datetime.LocalDate

data class Holiday(
    val label: String,
    val date: LocalDate
)