package com.llamalabb.phillygarbageday.data.repository

import com.llamalabb.phillygarbageday.data.local.service.IAddressRealmService
import com.llamalabb.phillygarbageday.data.remote.dto.AddressDTO
import com.llamalabb.phillygarbageday.data.remote.dto.asDomainModel
import com.llamalabb.phillygarbageday.data.remote.dto.toDomainModel
import com.llamalabb.phillygarbageday.data.remote.service.IPhillyApiService
import com.llamalabb.phillygarbageday.domain.domain_model.AddressInfo
import com.llamalabb.phillygarbageday.domain.domain_model.Holiday
import io.ktor.client.call.*
import io.ktor.http.*


class TrashDayRepository(
    private val ktorService: IPhillyApiService,
    private val realmService: IAddressRealmService
) : ITrashDayRepository {

    override suspend fun fetchAddressInfo(): AddressInfo {
        return realmService.getStreetAddress().let {
            ktorService.getAddressDetailsHttpResponse(it ?: throw NoAddressCacheFound())
                .body<AddressDTO>()
                .asDomainModel()
        }
    }

    override suspend fun validateAddress(address: String): Boolean {
        val response = ktorService.getAddressDetailsHttpResponse(address)
        val isValid = response.status.isSuccess()

        if (!isValid) return false

        val normalizedAddress =
            response.body<AddressDTO>().features.first().properties.streetAddress

        realmService.saveStreetAddress(normalizedAddress)

        return true
    }

    override suspend fun fetchHolidays(): List<Holiday> =
        ktorService.getHolidaysDTO().toDomainModel()

}

class NoAddressCacheFound: Exception()




