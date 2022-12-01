package com.llamalabb.phillygarbageday.data.repository

import com.llamalabb.phillygarbageday.data.local.dao.HeadlineDAO
import com.llamalabb.phillygarbageday.data.local.service.IAddressRealmService
import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import com.llamalabb.phillygarbageday.data.remote.service.IPhillyApiService


class AddressRepository(
    private val ktorService: IPhillyApiService,
    private val realmService: IAddressRealmService
) : IAddressRepository {


    override suspend fun getAllHeadlines(
        page: Int,
        pageSize: Int,
        country: String
    ) = ktorService.getHeadLines(
        pageSize = pageSize,
        page = page,
        country = country

    )

    override suspend fun addToReadLater(headlineDAO: HeadlineDAO) {
        realmService.addToReadLater(headlineDAO)
    }

    override suspend fun getAllReadLater() = realmService.getReadLater()

    override suspend fun getAddressInfo(address: String): AddressDataDTO =
        ktorService.getAddressDetails(address)

}



