package com.llamalabb.phillygarbageday.presentation.trashday

import com.llamalabb.phillygarbageday.domain.domain_model.Holiday
import com.llamalabb.phillygarbageday.domain.util.fullDateFormat

data class TrashDayState (
    val isLoading: Boolean = true,
    val streetAddress: String? = null,
    val nextTrashDay: String? = null,
    val holidays: List<HolidayItem> = emptyList()
)

data class HolidayItem (
    val label: String,
    val date: String,
    val trashDate: String,
)