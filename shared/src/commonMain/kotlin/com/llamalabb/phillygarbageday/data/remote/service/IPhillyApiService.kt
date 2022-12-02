package com.llamalabb.phillygarbageday.data.remote.service

import com.llamalabb.phillygarbageday.data.remote.dto.HolidaysDTO
import io.ktor.client.statement.*

interface IPhillyApiService {

    suspend fun getAddressDetailsHttpResponse(address: String): HttpResponse

    suspend fun getHolidaysDTO(): HolidaysDTO

}

