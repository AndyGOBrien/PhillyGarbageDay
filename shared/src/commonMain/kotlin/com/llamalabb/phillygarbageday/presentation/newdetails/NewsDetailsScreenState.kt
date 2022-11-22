package com.llamalabb.phillygarbageday.presentation.newdetails

sealed interface NewsDetailsScreenState {
    object Idle : NewsDetailsScreenState
    object SavingForLater : NewsDetailsScreenState
    object Success : NewsDetailsScreenState
}