package com.llamalabb.phillygarbageday.presentation.trashday

import com.llamalabb.phillygarbageday.domain.domain_model.Holiday

data class TrashDayState (
    val isLoading: Boolean = true,
    val streetAddress: String = "",
    val trashPickupDay: String = "",
    val holidays: List<Holiday> = emptyList()
)