package com.llamalabb.phillygarbageday.data.repository

import com.llamalabb.phillygarbageday.data.local.dao.HeadlineDAO
import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import com.llamalabb.phillygarbageday.data.remote.dto.HeadlinesDTO


interface IAddressRepository {

    suspend fun getAllHeadlines(
        page: Int,
        pageSize: Int,
        country: String
    ): HeadlinesDTO

    suspend fun addToReadLater(headlineDAO: HeadlineDAO)
    suspend fun getAllReadLater(): List<HeadlineDAO>
    suspend fun getAddressInfo(address: String): AddressDataDTO
}