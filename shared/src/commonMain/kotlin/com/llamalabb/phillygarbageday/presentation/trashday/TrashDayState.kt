package com.llamalabb.phillygarbageday.presentation.trashday

import com.llamalabb.phillygarbageday.domain.domain_model.Holiday

data class TrashDayState (
    val isLoading: Boolean = true,
    val streetAddress: String? = null,
    val nextTrashDay: String? = null,
    val holidays: List<Holiday> = emptyList()
)