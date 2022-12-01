package com.llamalabb.phillygarbageday.data.repository

import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO


interface IAddressRepository {
    suspend fun getAddressInfo(address: String): AddressDataDTO
}