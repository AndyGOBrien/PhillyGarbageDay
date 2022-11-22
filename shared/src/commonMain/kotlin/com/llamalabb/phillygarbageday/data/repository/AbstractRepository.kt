package com.llamalabb.phillygarbageday.data.repository

import com.llamalabb.phillygarbageday.data.local.dao.HeadlineDAO
import com.llamalabb.phillygarbageday.data.remote.dto.HeadlinesDTO


abstract class AbstractRepository {

    abstract suspend fun getAllHeadlines(
        page: Int,
        pageSize: Int,
        country: String
    ): HeadlinesDTO

    abstract suspend fun addToReadLater(headlineDAO: HeadlineDAO)
    abstract suspend fun getAllReadLater(): List<HeadlineDAO>
}