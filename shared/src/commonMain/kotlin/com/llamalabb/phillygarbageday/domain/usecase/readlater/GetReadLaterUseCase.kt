package com.llamalabb.phillygarbageday.domain.usecase.readlater

import com.llamalabb.phillygarbageday.data.local.dao.asDomainModel
import com.llamalabb.phillygarbageday.data.repository.AbstractRepository
import kotlinx.coroutines.flow.flow

class GetReadLaterUseCase(private val repository: AbstractRepository) {

    operator fun invoke() = flow {
        val response = repository.getAllReadLater().asDomainModel()

        emit(response)
    }
}