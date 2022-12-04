package com.llamalabb.phillygarbageday.domain.domain_model

import kotlinx.datetime.DayOfWeek

data class AddressInfo(
    val streetAddress: String,
    val collectionDay: DayOfWeek
)