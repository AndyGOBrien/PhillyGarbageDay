package com.llamalabb.phillygarbageday.android.ui.trashday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.llamalabb.phillygarbageday.presentation.Dispatch
import com.llamalabb.phillygarbageday.presentation.addressinput.AddressInputScreenAction.*
import com.llamalabb.phillygarbageday.presentation.trashday.HolidayItem
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
                if (holidays.isNotEmpty()) item { RemainingHolidaysTitle() }
                holidays.forEach { item { HolidayItem(it) } }
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
                        style = MaterialTheme.typography.headlineSmall,
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
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "Your next trash day",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            Spacer(Modifier.size(8.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = trashDay,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
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

    @Composable
    private fun RemainingHolidaysTitle() {
        Text(
            text = "Remaining holidays this year",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }

    @Composable
    private fun HolidayItem(holiday: HolidayItem) {
         Card(
             colors = CardDefaults.cardColors(
                 containerColor = MaterialTheme.colorScheme.primaryContainer,
                 contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
             ),
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 16.dp, vertical = 4.dp)
         ) {
            ConstraintLayout(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                val (label, date, delay, trashDate) = createRefs()
                Text(
                    text = holiday.label,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(label) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = holiday.date,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .constrainAs(date) {
                            start.linkTo(parent.start)
                            top.linkTo(label.bottom)
                        }
                )
                Text(
                    text = "Trash day this week",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .constrainAs(delay) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(date.bottom)
                        }
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .constrainAs(trashDate) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(delay.bottom)
                    }
                ) {
                    Text(
                        text = holiday.trashDate,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

}