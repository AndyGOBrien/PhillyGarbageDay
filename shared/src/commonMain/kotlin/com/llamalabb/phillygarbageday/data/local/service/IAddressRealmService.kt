package com.llamalabb.phillygarbageday.data.local.service

interface IAddressRealmService{
    suspend fun saveStreetAddress(address: String)

    suspend fun getStreetAddress(): String?
}