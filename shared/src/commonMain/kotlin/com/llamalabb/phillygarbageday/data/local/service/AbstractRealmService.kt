package com.llamalabb.phillygarbageday.data.local.service

import com.llamalabb.phillygarbageday.data.local.dao.HeadlineDAO

abstract class AbstractRealmService{
    abstract suspend fun addToReadLater(headlineDAO: HeadlineDAO)

    abstract suspend fun getReadLater(): List<HeadlineDAO>
}