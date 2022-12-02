package com.llamalabb.phillygarbageday.android.ui.trashday

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.llamalabb.phillygarbageday.presentation.Dispatch
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenState
import com.llamalabb.phillygarbageday.presentation.trashday.TrashDayState

class TrashDayLayout(
    private val dispatch: Dispatch = { /* noop */ }
) {

    @Preview(showBackground = true)
    @Composable
    fun Render(
        @PreviewParameter(TrashDayMock::class)
        state: State<TrashDayState>
    ) {
        state.value.run {
            LazyColumn {
                if (trashPickupDay.isNotBlank()) item { TrashPickUpDay(trashPickupDay) }
            }
            CenteredLoader(isLoading)
        }
    }

    @Composable
    private fun TrashPickUpDay(trashPickUpDay: String) {
        Text(
            text = "Trash Pick Up Day: $trashPickUpDay",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }

    @Composable
    private fun CenteredLoader(isLoading: Boolean) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(Modifier.size(100.dp))
            }
        }
    }
}