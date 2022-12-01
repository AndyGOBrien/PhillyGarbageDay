package com.llamalabb.phillygarbageday.data.repository

import com.llamalabb.phillygarbageday.data.local.dao.HeadlineDAO
import com.llamalabb.phillygarbageday.data.local.service.AbstractRealmService
import com.llamalabb.phillygarbageday.data.remote.dto.AddressDataDTO
import com.llamalabb.phillygarbageday.data.remote.service.AbstractKtorService


class ImplRepository(
    private val ktorService: AbstractKtorService,
    private val realmService: AbstractRealmService
) : AbstractRepository() {


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




