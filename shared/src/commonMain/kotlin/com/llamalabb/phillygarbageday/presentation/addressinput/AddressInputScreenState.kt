package com.llamalabb.phillygarbageday.presentation.addressinput

data class AddressInputScreenState (
    val isLoading: Boolean = false,
    val addressInput: String = "",
    val addressInputError: String? = null,
    val trashPickUpDay: String = "",
)