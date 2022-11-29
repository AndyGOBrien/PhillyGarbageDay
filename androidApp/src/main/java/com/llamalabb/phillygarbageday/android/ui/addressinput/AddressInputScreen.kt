package com.llamalabb.phillygarbageday.android.ui.addressinput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun AddressInputScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: AddressInputViewModel = getViewModel()
) {
    val layoutManager = AddressInputScreenLayout(viewModel::dispatch)
    layoutManager.Layout(viewModel.state.collectAsState())
}