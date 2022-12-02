package com.llamalabb.phillygarbageday.presentation

sealed class GenericUiEvent : BaseUiEvent {
    object None : GenericUiEvent()
}
