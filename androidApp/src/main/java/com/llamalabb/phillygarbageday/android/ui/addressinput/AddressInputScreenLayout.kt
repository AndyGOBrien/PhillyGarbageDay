package com.llamalabb.phillygarbageday.android.ui.addressinput

import Dispatch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenState
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressItem


@OptIn(ExperimentalMaterial3Api::class)
class AddressInputScreenLayout(
    private val dispatch: Dispatch = { /* noop */ }
) {

    @Preview(showBackground = true)
    @Composable
    fun Layout(
        @PreviewParameter(AddressInputScreenMock::class)
        state: State<AddressInputScreenState>
    ) {
        state.value.run {
            LazyColumn {
                item { AddressInputField(addressInput) }
                item { ClearContentButton() }
                item { LoadDataButton(addressInput) }
                item { TrashPickUpDay(trashPickUpDay) }
                item { addresses.forEach { RecentAddressItem(it) } }
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
    private fun AddressInputField(value: String) {
        OutlinedTextField(
            value = value,
            onValueChange = { dispatch(UserInputAddressText(it)) },
            label = { Text("Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }

    @Composable
    private fun ClearContentButton() {
        Button(
            onClick = { dispatch(UserTappedClearContent) },
            shape = ShapeDefaults.ExtraSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text("Clear Content")
        }
    }

    @Composable
    private fun LoadDataButton(addressInput: String) {
        Button(
            onClick = { dispatch(UserTappedLoadData(addressInput)) },
            shape = ShapeDefaults.ExtraSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text("Load Data")
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

    @Composable
    private fun RecentAddressItem(addressItem: AddressItem) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp),
            onClick = { dispatch(UserTappedAddressItem(addressItem)) }
        ) {
            ConstraintLayout(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                val (startText, endText, bottomText) = createRefs()

                Text(
                    text = addressItem.streetAddress,
                    modifier = Modifier
                        .constrainAs(startText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    text = addressItem.zipCode,
                    modifier = Modifier
                        .constrainAs(endText) {
                            top.linkTo(startText.top)
                            bottom.linkTo(startText.bottom)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    text = addressItem.distance,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .constrainAs(bottomText) {
                            top.linkTo(startText.bottom)
                            start.linkTo(startText.start)
                        }
                )
            }
        }
    }
}