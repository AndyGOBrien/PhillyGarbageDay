package com.llamalabb.phillygarbageday.presentation.trashday

import com.llamalabb.phillygarbageday.data.repository.ITrashDayRepository
import com.llamalabb.phillygarbageday.domain.domain_model.Holiday
import com.llamalabb.phillygarbageday.domain.util.fullDateFormat
import com.llamalabb.phillygarbageday.domain.util.localToday
import com.llamalabb.phillygarbageday.domain.util.minusDays
import com.llamalabb.phillygarbageday.domain.util.ordinal
import com.llamalabb.phillygarbageday.domain.util.plusDays
import com.llamalabb.phillygarbageday.domain.util.startOfWeek
import com.llamalabb.phillygarbageday.presentation.BaseAction
import com.llamalabb.phillygarbageday.presentation.BaseUiEvent
import com.llamalabb.phillygarbageday.presentation.addressinput.Navigation
import com.llamalabb.phillygarbageday.presentation.trashday.TrashDayAction.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.*

class TrashDayViewModel(private val repo: ITrashDayRepository) : ViewModel() {

    private val _state = MutableStateFlow(TrashDayState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BaseUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        dispatch(LoadData)
    }

    fun dispatch(action: BaseAction) {
        reduce(action, _state.value)
        launchSideEffects(action)
    }

    private fun dispatchUiEvent(uiEvent: BaseUiEvent) {
        viewModelScope.launch { _uiEvent.emit(uiEvent) }
    }

    /** Reducers */
    //region Reducers

    private fun reduce(action: BaseAction, state: TrashDayState) {
        _state.value = state.copy(
            isLoading = reduceIsLoading(action, state),
            nextTrashDay = reduceNextTrashDay(action, state),
            holidays = reduceHolidays(action, state),
            streetAddress = reduceStreetAddress(action, state),
        )
    }

    private fun reduceIsLoading(
        action: BaseAction,
        state: TrashDayState
    ): Boolean = when (action) {
        is LoadData -> true
        is LoadDataSuccess -> false
        is LoadDataFailure -> false
        else -> state.isLoading
    }

    private fun reduceNextTrashDay(
        action: BaseAction,
        state: TrashDayState
    ): String? = when (action) {
        is LoadDataSuccess -> action.nextGarbageDay.fullDateFormat()
        is LoadDataFailure -> null
        else -> state.nextTrashDay
    }

    private fun reduceHolidays(
        action: BaseAction,
        state: TrashDayState,
    ): List<Holiday> = when (action) {
        is LoadDataSuccess -> action.holidays
        is LoadDataFailure -> emptyList()
        else -> state.holidays
    }

    private fun reduceStreetAddress(
        action: BaseAction,
        state: TrashDayState
    ): String? = when (action) {
        is LoadDataSuccess -> action.streetAddress
        is LoadDataFailure -> null
        else -> state.streetAddress
    }

    //endregion

    /** Side Effects */
    //region Side Effects

    private fun launchSideEffects(action: BaseAction) {
        when (action) {
            is LoadData -> loadDataSideEffect()
            is UserTappedEditAddress -> dispatchUiEvent(Navigation.ShowAddressInput)
            else -> Unit
        }
    }

    private fun loadDataSideEffect() {
        viewModelScope.launch {
            try {
                val addressInfo = repo.fetchAddressInfo()
                val holidays = repo.fetchHolidays()
                val today = Clock.System.localToday()

                val streetAddress = addressInfo.streetAddress
                val remainingHolidays = holidays.filter { it.date >= today.startOfWeek() }
                val nextGarbageDay = getNextTrashDay(addressInfo.collectionDay, remainingHolidays)
                dispatch(LoadDataSuccess(streetAddress, nextGarbageDay, remainingHolidays))
            } catch (e: Exception) {
                dispatch(LoadDataFailure(e))
            }
        }
    }

    //endregion

    /** Helper Functions */

    private fun getNextTrashDay(rawTrashDay: DayOfWeek, holidays: List<Holiday>): LocalDate {

        var trashDate = Clock.System.localToday()
        while (trashDate.dayOfWeek != rawTrashDay) { trashDate = trashDate.plusDays(1) }
        val startOfWeek = trashDate.minusDays(trashDate.dayOfWeek.isoDayNumber)
        holidays.forEach { holiday ->
            if (holiday.date in startOfWeek..trashDate) trashDate = trashDate.plusDays(1)
        }

        return trashDate
    }

}

sealed class TrashDayAction : BaseAction {
    object LoadData : TrashDayAction()
    data class LoadDataSuccess(
        val streetAddress: String,
        val nextGarbageDay: LocalDate,
        val holidays: List<Holiday>
    ) : TrashDayAction()

    data class LoadDataFailure(val error: Exception) : TrashDayAction()
    object UserTappedEditAddress : TrashDayAction()
}