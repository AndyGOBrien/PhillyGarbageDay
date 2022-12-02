package com.llamalabb.phillygarbageday.android.ui.trashday

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenState
import com.llamalabb.phillygarbageday.presentation.trashday.TrashDayState


class TrashDayMock : PreviewParameterProvider<State<TrashDayState>> {
    override val values: Sequence<State<TrashDayState>> =
        listOf(
            getInitialState(),
            getLoadedState(),
        ).asSequence()

    private fun getInitialState() = mutableStateOf(TrashDayState())

    private fun getLoadedState() = mutableStateOf(
        TrashDayState(
            isLoading = false,
            trashPickupDay = "Wednesday"
        )
    )
}