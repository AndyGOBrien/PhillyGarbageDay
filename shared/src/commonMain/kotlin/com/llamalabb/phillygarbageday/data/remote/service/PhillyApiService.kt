package com.llamalabb.phillygarbageday.data.remote.service

import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class PhillyApiService(
    private val httpClient: HttpClient
) : IPhillyApiService {

    companion object {
        private const val BASE_URL = "https://api.phila.gov/ais/v1"
        const val ADDRESS_ENDPOINT = "addresses"
    }

    override suspend fun getAddressDetails(
        address: String
    ): AddressDataDTO = httpClient.get("${BASE_URL}/${ADDRESS_ENDPOINT}") {
        url { appendEncodedPathSegments(address) }
    }.body()

}

