package com.llamalabb.phillygarbageday.data.local.service

import com.llamalabb.phillygarbageday.data.local.dao.HeadlineDAO

interface IAddressRealmService{
    suspend fun addToReadLater(headlineDAO: HeadlineDAO)

    suspend fun getReadLater(): List<HeadlineDAO>
}