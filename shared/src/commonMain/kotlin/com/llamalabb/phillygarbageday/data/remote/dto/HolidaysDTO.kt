package com.llamalabb.phillygarbageday.data.remote.dto

import com.llamalabb.phillygarbageday.domain.domain_model.Holiday
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class HolidaysDTO(
    val holidays: List<HolidayDTO>
)

@kotlinx.serialization.Serializable
data class HolidayDTO(
    @SerialName("holiday_label")
    val holidayLabel: String,
    @SerialName("start_date")
    val startDate: String
)

fun HolidaysDTO.toDomainModel() = this.holidays.map {
    Holiday(
        label = it.holidayLabel,
        date = LocalDate.parse(it.startDate)
    )
}