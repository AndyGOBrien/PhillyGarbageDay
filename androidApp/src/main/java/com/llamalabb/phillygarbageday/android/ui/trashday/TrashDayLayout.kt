package com.llamalabb.phillygarbageday.android.ui.trashday

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.llamalabb.phillygarbageday.android.ui.theme.TrashTheme
import com.llamalabb.phillygarbageday.android.ui.theme.defaultTextStyle
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
                item { Title(streetAddress) }
                item { TrashPickUpDay(trashPickupDay) }
            }
            CenteredLoader(isLoading)
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun Title(address: String?) {
        address?.let {
            TextButton(
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .wrapContentWidth()
            ) {
                Text(
                    text = it,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = defaultTextStyle,
                    textAlign = TextAlign.Center,
                )
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

    @Composable
    private fun TrashPickUpDay(trashPickUpDay: String?) {
        trashPickUpDay?.let {
            Text(
                text = "Trash Pick Up Day: $it",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
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