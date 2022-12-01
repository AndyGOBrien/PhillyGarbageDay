package com.llamalabb.phillygarbageday.presentation.addressinput

data class AddressInputScreenState (
    val isLoading: Boolean = false,
    val addressInput: String = "",
    val addresses: List<AddressItem> = emptyList(),
    val trashPickUpDay: String = ""
)

data class AddressItem(
    val streetAddress: String,
    val zipCode: String,
    val distance: String,
)