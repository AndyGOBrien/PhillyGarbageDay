package com.llamalabb.phillygarbageday.domain.usecase.newsdetails

import com.llamalabb.phillygarbageday.data.repository.AbstractRepository
import com.llamalabb.phillygarbageday.domain.domain_model.HeadlineDomainModel
import com.llamalabb.phillygarbageday.domain.domain_model.asDao

class AddToReadLaterUseCase(private val abstractRepository: AbstractRepository) {

    suspend operator fun invoke(headlineDomainModel: HeadlineDomainModel) {
        abstractRepository.addToReadLater(headlineDomainModel.asDao())
    }
}