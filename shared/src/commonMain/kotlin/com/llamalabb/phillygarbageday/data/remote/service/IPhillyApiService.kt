package com.llamalabb.phillygarbageday.data.remote.service

import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO

interface IPhillyApiService {

    suspend fun getAddressDetails(address: String): AddressDataDTO

}

