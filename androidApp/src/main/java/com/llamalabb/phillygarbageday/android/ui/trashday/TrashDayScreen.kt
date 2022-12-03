package com.llamalabb.phillygarbageday.android.ui.trashday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.llamalabb.phillygarbageday.android.ui.destinations.AddressInputScreenDestination
import com.llamalabb.phillygarbageday.android.ui.destinations.TrashDayScreenDestination
import com.llamalabb.phillygarbageday.presentation.GenericUiEvent
import com.llamalabb.phillygarbageday.presentation.addressinput.Navigation
import com.llamalabb.phillygarbageday.presentation.trashday.TrashDayViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun TrashDayScreen(
    navigator: DestinationsNavigator,
    viewModel: TrashDayViewModel = getViewModel()
) {
    val layoutManager = TrashDayLayout(viewModel::dispatch)
    layoutManager.Render(viewModel.state.collectAsState())

    val uiEventFlow = viewModel.uiEvent.collectAsState(GenericUiEvent.None)
    when (uiEventFlow.value) {
        is Navigation.ShowAddressInput -> navigator.navigate(AddressInputScreenDestination)
    }
}