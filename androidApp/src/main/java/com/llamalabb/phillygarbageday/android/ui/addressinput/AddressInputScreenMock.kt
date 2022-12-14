package com.llamalabb.phillygarbageday.android.ui.addressinput

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenState


class AddressInputScreenMock : PreviewParameterProvider<State<AddressInputScreenState>> {
    override val values: Sequence<State<AddressInputScreenState>> =
        listOf(
            getInitialState(),
            getLoadingState(),
            getLoadedState(),
        ).asSequence()

    private fun getInitialState() = mutableStateOf(AddressInputScreenState())

    private fun getLoadingState() = mutableStateOf(AddressInputScreenState(isLoading = true))

    private fun getLoadedState() = mutableStateOf(
        AddressInputScreenState(
            isLoading = false,
        )
    )
}