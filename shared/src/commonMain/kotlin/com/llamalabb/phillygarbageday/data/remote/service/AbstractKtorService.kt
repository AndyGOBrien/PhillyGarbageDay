package com.llamalabb.phillygarbageday.data.remote.service

import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import com.llamalabb.phillygarbageday.data.remote.dto.HeadlinesDTO

abstract class AbstractKtorService {
    abstract suspend fun getHeadLines(
        pageSize: Int,
        page: Int,
        country: String,
    ): HeadlinesDTO

    abstract suspend fun getAddressDetails(
        address: String
    ): AddressDataDTO
}

