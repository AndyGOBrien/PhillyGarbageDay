package com.llamalabb.phillygarbageday.android.ui.addressinput

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import com.llamalabb.phillygarbageday.presentation.Dispatch
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenState


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
                item { AddressInputField(addressInput, addressInputError) }
                item { SubmitButton(addressInput) }
                item { TrashPickUpDay(trashPickUpDay) }
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
    private fun AddressInputField(value: String, error: String? = null) {
        OutlinedTextField(
            value = value,
            onValueChange = { dispatch(UserInputAddressText(it)) },
            label = { Text("Address") },
            supportingText = { error?.let{ Text(it) } },
            isError = !error.isNullOrBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }

    @Composable
    private fun SubmitButton(addressInput: String) {
        Button(
            onClick = { dispatch(UserTappedLoadData(addressInput)) },
            shape = ShapeDefaults.ExtraSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text("Submit")
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