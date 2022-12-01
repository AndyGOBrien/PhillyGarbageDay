package com.llamalabb.phillygarbageday.data.remote.service

import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import com.llamalabb.phillygarbageday.data.remote.dto.HeadlinesDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class PhillyApiService(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : IPhillyApiService {

    private val apikey = "a52b414d7a4e496a81b9787ebf8993f2"
    override suspend fun getHeadLines(
        pageSize: Int,
        page: Int,
        country: String
    ): HeadlinesDTO = httpClient.get("$baseUrl/${EndPoints.HEADLINES}") {
        header("x-api-key", apikey)
        parameter("country", country)
        parameter("pageSize", pageSize)
        parameter("page", page)
    }.body()

    override suspend fun getAddressDetails(
        address: String
    ): AddressDataDTO = httpClient.get("$baseUrl/${EndPoints.ADDRESSES}") {
        url {
            appendEncodedPathSegments(address)
        }
    }.body()

}

