package com.llamalabb.phillygarbageday.data.remote.service

import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import com.llamalabb.phillygarbageday.data.remote.dto.HeadlinesDTO

interface IPhillyApiService {
    suspend fun getHeadLines(
        pageSize: Int,
        page: Int,
        country: String,
    ): HeadlinesDTO

    suspend fun getAddressDetails(
        address: String
    ): AddressDataDTO
}

