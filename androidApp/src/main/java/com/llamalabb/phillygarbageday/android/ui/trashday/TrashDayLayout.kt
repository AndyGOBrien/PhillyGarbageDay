package com.llamalabb.phillygarbageday.android.ui.trashday

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.llamalabb.phillygarbageday.presentation.Dispatch
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import com.llamalabb.phillygarbageday.presentation.trashday.TrashDayAction
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
                item { Title(streetAddress) }
                item { TrashPickUpDay(nextTrashDay) }
            }
            CenteredLoader(isLoading)
        }
    }

    @Composable
    private fun Title(address: String?) {
        address?.let {
            TextButton(
                onClick = { dispatch(TrashDayAction.UserTappedEditAddress) },
                contentPadding = PaddingValues(start = 16.dp),
                shape = ShapeDefaults.Small,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .wrapContentWidth()
            ) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(CenterVertically)
                    )
                }
            }
        }
    }

    @Composable
    private fun TrashPickUpDay(trashPickUpDay: String?) {
        trashPickUpDay?.let { trashDay ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "Your next trash day:",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)) {
                        Text(
                            text = trashDay,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
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