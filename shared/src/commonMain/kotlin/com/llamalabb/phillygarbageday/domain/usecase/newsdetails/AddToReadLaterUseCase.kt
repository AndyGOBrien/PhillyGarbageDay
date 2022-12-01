package com.llamalabb.phillygarbageday.domain.usecase.newsdetails

import com.llamalabb.phillygarbageday.data.repository.IAddressRepository
import com.llamalabb.phillygarbageday.domain.domain_model.HeadlineDomainModel
import com.llamalabb.phillygarbageday.domain.domain_model.asDao

class AddToReadLaterUseCase(private val IAddressRepository: IAddressRepository) {

    suspend operator fun invoke(headlineDomainModel: HeadlineDomainModel) {
        IAddressRepository.addToReadLater(headlineDomainModel.asDao())
    }
}