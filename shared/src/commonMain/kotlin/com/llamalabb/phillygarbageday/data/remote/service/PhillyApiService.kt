package com.llamalabb.phillygarbageday.data.remote.service

import com.llamalabb.phillygarbageday.data.remote.dto.HolidaysDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class PhillyApiService(
    private val httpClient: HttpClient
) : IPhillyApiService {

    companion object {
        private const val BASE_URL = "https://api.phila.gov"
        private const val ADDRESS_ENDPOINT = "ais/v1/addresses"
        private const val TRASH_DAY_ENDPOINT = "phila/trashday/v1"
    }

    override suspend fun getAddressDetailsHttpResponse(address: String): HttpResponse =
        httpClient.get("${BASE_URL}/${ADDRESS_ENDPOINT}") {
            url { appendEncodedPathSegments(address) }
        }

    override suspend fun getHolidaysDTO(): HolidaysDTO =
        httpClient.get("${BASE_URL}/${TRASH_DAY_ENDPOINT}").body()

}
