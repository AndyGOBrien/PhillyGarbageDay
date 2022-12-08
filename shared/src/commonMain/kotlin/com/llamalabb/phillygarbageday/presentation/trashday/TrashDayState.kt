package com.llamalabb.phillygarbageday.presentation.trashday

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