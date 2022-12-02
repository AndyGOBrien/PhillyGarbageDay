package com.llamalabb.phillygarbageday.android.ui.trashday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.llamalabb.phillygarbageday.presentation.trashday.TrashDayViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun TrashDayScreen(
    viewModel: TrashDayViewModel = getViewModel()
) {
    val layoutManager = TrashDayLayout(viewModel::dispatch)
    layoutManager.Render(viewModel.state.collectAsState())
}