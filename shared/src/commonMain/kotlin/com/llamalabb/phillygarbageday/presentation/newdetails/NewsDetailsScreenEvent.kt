package com.llamalabb.phillygarbageday.presentation.newdetails

import com.llamalabb.phillygarbageday.domain.domain_model.HeadlineDomainModel

sealed interface NewsDetailsScreenEvent{

    data class SaveForLater(val headlineDomainModel: HeadlineDomainModel) : NewsDetailsScreenEvent

}