package com.llamalabb.phillygarbageday.presentation.readlater

import com.llamalabb.phillygarbageday.domain.domain_model.HeadlineDomainModel

public sealed interface ReadLaterState {
    object Loading : ReadLaterState
    data class Error(val message: String) : ReadLaterState
    data class Success(val headlines: List<HeadlineDomainModel>) : ReadLaterState
}