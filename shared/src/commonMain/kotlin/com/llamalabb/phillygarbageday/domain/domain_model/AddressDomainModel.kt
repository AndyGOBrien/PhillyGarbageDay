package com.llamalabb.phillygarbageday.domain.domain_model

data class AddressData(
    val garbageDay: DayOfWeek?
)

enum class DayOfWeek {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;
}