package com.llamalabb.phillygarbageday.data.remote.dto

import com.llamalabb.phillygarbageday.domain.domain_model.AddressData
import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class AddressDataDTO(
    val features: List<Feature>? = null
)

@kotlinx.serialization.Serializable
data class Feature(
    val properties: Properties
)

@kotlinx.serialization.Serializable
data class Properties(
    @SerialName("rubbish_recycle_day")
    val rubbishRecycleDay: String
)

fun AddressDataDTO.asDomainModel() = AddressData(
    when(features?.first()?.properties?.rubbishRecycleDay) {
        "SUN" -> DayOfWeek.SUNDAY
        "MON" -> DayOfWeek.MONDAY
        "TUE" -> DayOfWeek.TUESDAY
        "WED" -> DayOfWeek.WEDNESDAY
        "THU" -> DayOfWeek.THURSDAY
        "FRI" -> DayOfWeek.FRIDAY
        "SAT" -> DayOfWeek.SATURDAY
        else -> null
    }
)