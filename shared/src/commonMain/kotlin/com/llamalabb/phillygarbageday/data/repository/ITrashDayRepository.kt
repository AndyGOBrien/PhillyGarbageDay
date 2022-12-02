package com.llamalabb.phillygarbageday.data.repository

import com.llamalabb.phillygarbageday.domain.domain_model.AddressInfo
import com.llamalabb.phillygarbageday.domain.domain_model.Holiday


interface ITrashDayRepository {
    suspend fun fetchAddressInfo(): AddressInfo
    suspend fun validateAddress(address: String): Boolean
    suspend fun fetchHolidays(): List<Holiday>
}