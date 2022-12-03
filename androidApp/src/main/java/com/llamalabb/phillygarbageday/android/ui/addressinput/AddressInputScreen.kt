package com.llamalabb.phillygarbageday.android.ui.addressinput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.llamalabb.phillygarbageday.android.ui.destinations.AddressInputScreenDestination
import com.llamalabb.phillygarbageday.android.ui.destinations.TrashDayScreenDestination
import com.llamalabb.phillygarbageday.presentation.GenericUiEvent
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputViewModel
import com.llamalabb.phillygarbageday.presentation.addressinput.Navigation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun AddressInputScreen(
    navigator: DestinationsNavigator,
    viewModel: AddressInputViewModel = getViewModel()
) {
    val layoutManager = AddressInputScreenLayout(viewModel::dispatch)
    layoutManager.Render(viewModel.state.collectAsState())

    val uiEventFlow = viewModel.uiEvent.collectAsState(GenericUiEvent.None)
    when (uiEventFlow.value) {
        is Navigation.ShowTrashDay -> {
            navigator.navigate(TrashDayScreenDestination()) {
                this.popUpTo(AddressInputScreenDestination.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

}