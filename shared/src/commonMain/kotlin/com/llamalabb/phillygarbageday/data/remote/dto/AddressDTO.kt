package com.llamalabb.phillygarbageday.data.remote.dto

import com.llamalabb.phillygarbageday.domain.domain_model.AddressInfo
import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class AddressDTO(
    val features: List<Feature>
)

@kotlinx.serialization.Serializable
data class Feature(
    val properties: Properties
)

@kotlinx.serialization.Serializable
data class Properties(
    @SerialName("rubbish_recycle_day")
    val rubbishRecycleDay: String,
    @SerialName("street_address")
    val streetAddress: String
)

fun AddressDTO.asDomainModel() = AddressInfo(
    streetAddress = features.first().properties.streetAddress,
    garbageDay = when(features.first().properties.rubbishRecycleDay) {
        "SUN" -> DayOfWeek.SUNDAY
        "MON" -> DayOfWeek.MONDAY
        "TUE" -> DayOfWeek.TUESDAY
        "WED" -> DayOfWeek.WEDNESDAY
        "THU" -> DayOfWeek.THURSDAY
        "FRI" -> DayOfWeek.FRIDAY
        "SAT" -> DayOfWeek.SATURDAY
        else -> throw DayOfWeekDoesNotExist()
    }
)

class DayOfWeekDoesNotExist : Exception()